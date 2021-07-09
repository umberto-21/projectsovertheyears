import pynusmv
import sys
from pynusmv_lower_interface.nusmv.parser import parser 
from collections import deque

def get_model_encoded_with_bdd():
    fsm = pynusmv.glob.prop_database().master.bddFsm
    return fsm

def build_witness(fsm, reach):
    states = []
    while fsm.count_states(reach):
        state = fsm.pick_one_state(reach)
        states.append(state)
        reach = reach - state
    last_state = states[len(states) -1]
    states.reverse()
    states.append(last_state)
    witness = ()
    for i in range(0, len(states) -1):
        inputs = fsm.get_inputs_between_states(states[i], states[i+1])
        if inputs != pynusmv.dd.BDD.false():
            input = fsm.pick_one_input(inputs)
            witness += (states[i].get_str_values(),) + (input.get_str_values(),)
        else:
            witness += (states[i].get_str_values(),) + ({},)
    witness += (states[len(states) -1].get_str_values(),)
    return witness

def reactivity_spec(fsm, spec_f, spec_g):
    reach = fsm.init
    new = reach
    trans = fsm.trans
    "Loop to perform the reachability states calculation"
    while fsm.count_states(new) != 0:
        post_region = trans.post(new)
        new = post_region - reach
        reach = reach | new
    "Region of all reacheable states that satisfy `spec_f`"
    recur = reach & spec_f
    "Region that represent the transition relations"
    trans = fsm.trans
    "The point is to find a cycling execution that satisfy `spec_f` and not satisfy `spec_g`"
    while fsm.count_states(recur) != 0:
        reach = pynusmv.dd.BDD.true() - pynusmv.dd.BDD.true()
        new = trans.pre(recur) & (~spec_g) 
        while fsm.count_states(new) != 0:
            reach = reach | new
            if recur.entailed(reach):
                witness = build_witness(fsm, reach)
                return False, reach
            pre = trans.pre(new) & (~spec_g)
            new = pre - reach
        recur = recur & reach
    return True, None   

def my_check_react_spec(spec):
    "Pre-defined function to parse a reactivity formula"
    spec_parsed = parse_react(spec)
    "Check if `spec` is a reactivity formula"
    if spec_parsed == None:
        "return None if not. The algorithm stops here"
        return None
    "Otherwise get the model as bddFsm"
    fsm = get_model_encoded_with_bdd()
    "convert the specifications f and g into bdd data structure"
    spec_f = spec_to_bdd(fsm, spec_parsed[0])
    spec_g = spec_to_bdd(fsm, spec_parsed[1])
    "and call the model checking function"
    execution = reactivity_spec(fsm, spec_f, spec_g)
    "If the first element of the tuple `execution` is False"
    if execution[0] == False:
        "build the counterexample"
        witness = build_witness(fsm, execution[1])
        return False, witness
    return True, None

specTypes = {'LTLSPEC': parser.TOK_LTLSPEC, 'CONTEXT': parser.CONTEXT,
    'IMPLIES': parser.IMPLIES, 'IFF': parser.IFF, 'OR': parser.OR, 'XOR': parser.XOR, 'XNOR': parser.XNOR,
    'AND': parser.AND, 'NOT': parser.NOT, 'ATOM': parser.ATOM, 'NUMBER': parser.NUMBER, 'DOT': parser.DOT,

    'NEXT': parser.OP_NEXT, 'OP_GLOBAL': parser.OP_GLOBAL, 'OP_FUTURE': parser.OP_FUTURE,
    'UNTIL': parser.UNTIL,
    'EQUAL': parser.EQUAL, 'NOTEQUAL': parser.NOTEQUAL, 'LT': parser.LT, 'GT': parser.GT,
    'LE': parser.LE, 'GE': parser.GE, 'TRUE': parser.TRUEEXP, 'FALSE': parser.FALSEEXP
}

basicTypes = {parser.ATOM, parser.NUMBER, parser.TRUEEXP, parser.FALSEEXP, parser.DOT,
              parser.EQUAL, parser.NOTEQUAL, parser.LT, parser.GT, parser.LE, parser.GE}
booleanOp = {parser.AND, parser.OR, parser.XOR, parser.XNOR, parser.IMPLIES, parser.IFF}

def spec_to_bdd(model, spec):
    """
    Given a formula `spec` with no temporal operators, returns a BDD equivalent to
    the formula, that is, a BDD that contains all the states of `model` that
    satisfy `spec`
    """
    bddspec = pynusmv.mc.eval_simple_expression(model, str(spec))
    return bddspec
    
def is_boolean_formula(spec):
    """
    Given a formula `spec`, checks if the formula is a boolean combination of base
    formulas with no temporal operators. 
    """
    if spec.type in basicTypes:
        return True
    if spec.type == specTypes['NOT']:
        return is_boolean_formula(spec.car)
    if spec.type in booleanOp:
        return is_boolean_formula(spec.car) and is_boolean_formula(spec.cdr)
    return False
    
def check_GF_formula(spec):
    """
    Given a formula `spec` checks if the formula is of the form GF f, where f is a 
    boolean combination of base formulas with no temporal operators.
    Returns the formula f if `spec` is in the correct form, None otherwise 
    """
    # check if formula is of type GF f_i
    if spec.type != specTypes['OP_GLOBAL']:
        return False
    spec = spec.car
    if spec.type != specTypes['OP_FUTURE']:
        return False
    if is_boolean_formula(spec.car):
        return spec.car
    else:
        return None

def parse_react(spec):
    """
    Visit the syntactic tree of the formula `spec` to check if it is a reactive formula,
    that is wether the formula is of the form
    
                    GF f -> GF g
    
    where f and g are boolean combination of basic formulas.
    
    If `spec` is a reactive formula, the result is a pair where the first element is the 
    formula f and the second element is the formula g. If `spec` is not a reactive 
    formula, then the result is None.
    """
    # the root of a spec should be of type CONTEXT
    if spec.type != specTypes['CONTEXT']:
        return None
    # the right child of a context is the main formula
    spec = spec.cdr
    # the root of a reactive formula should be of type IMPLIES
    if spec.type != specTypes['IMPLIES']:
        return None
    # Check if lhs of the implication is a GF formula
    f_formula = check_GF_formula(spec.car)
    if f_formula == None:
        return None
    # Create the rhs of the implication is a GF formula
    g_formula = check_GF_formula(spec.cdr)
    if g_formula == None:
        return None
    return (f_formula, g_formula)

def check_react_spec(spec):
    """
    Return whether the loaded SMV model satisfies or not the GR(1) formula
    `spec`, that is, whether all executions of the model satisfies `spec`
    or not. 
    """
    if parse_react(spec) == None:
        return None
    return pynusmv.mc.check_explain_ltl_spec(spec)

if len(sys.argv) != 2:
    print("Usage:", sys.argv[0], "filename.smv")
    sys.exit(1)

pynusmv.init.init_nusmv()
filename = sys.argv[1]
pynusmv.glob.load_from_file(filename)
pynusmv.glob.compute_model()
type_ltl = pynusmv.prop.propTypes['LTL']
for prop in pynusmv.glob.prop_database():
    spec = prop.expr
    print(spec)
    if prop.type != type_ltl:
        print("property is not LTLSPEC, skipping")
        continue
    # res = check_react_spec(spec)
    my_res = my_check_react_spec(spec)
    # if res == None:
    #     print('Property is not a GR(1) formula, skipping')
    # if res[0] == True:
    #     print("Property is respected")
    # elif res[0] == False:
    #     print("Property is not respected")
    #     print("Counterexample:", res[1])
    if my_res == None:
        print('Property is not a GR(1) formula, skipping')    
    if my_res[0] == True:
        print("Property is respected")
    elif my_res[0] == False:
        print("Property is not respected")
        print("Counterexample:", my_res[1])
pynusmv.init.deinit_nusmv()
