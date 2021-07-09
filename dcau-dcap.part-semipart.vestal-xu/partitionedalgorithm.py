from tasksetsbinpacking import generate_cores, get_core_by_id
from responsetime import check_core
from tasksetsgeneration import coresnumber

def select_next_core (cores, core_id):
    next = 0
    for c in cores:
        if c.core_id == core_id:
            next += 1
            break
        next += 1
    return next


def partitioned (taskset, packingalgorithm):
    import copy

    cores =generate_cores ()

    tasksetcopy =copy.deepcopy (taskset)

    for each in tasksetcopy:

        if packingalgorithm (cores, each):
            core =get_core_by_id (each.whereis, cores)

            if not check_core (core):
                core.remove_from_core (each)
                next =select_next_core (cores, core.core_id)

                while next < coresnumber:
                    next_core =get_core_by_id (next, cores)
                    assert (next_core.core_id == next)

                    if next_core.try_to_add (each):
                        if check_core (next_core):
                            break
                        else:
                            next_core.remove_from_core (each)
                    next +=1

                if next == coresnumber:
                    return 0
        else:
            return 0

    return 1








