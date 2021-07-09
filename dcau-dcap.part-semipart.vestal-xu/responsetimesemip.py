from math import ceil

def calculate_with_jitter (task, higher_priority_tasks, incoming):
    def get_jitter (tau):
        if tau in incoming:
            return tau.responsetime - tau.lwcet
        else: return 0

    start = task.lwcet

    while True:
        next = sum([ceil((start + get_jitter (t))/ t.period) * t.lwcet for t in higher_priority_tasks])
        checkit = task.lwcet + next
        if checkit == start:
            return checkit
        elif checkit > task.period:
            return checkit
        start = checkit


def migration_process (task, higher_priority_tasks):
    def get_wcet (tau):
        if tau.criticality == 'H': return tau.hwcet
        else: return tau.lwcet

    start =get_wcet (task)

    while True:
        locrit =[l for l in higher_priority_tasks if l.criticality == 'L' and l.migratable == False]
        hicrit =[h for h in higher_priority_tasks if h.criticality == 'H']
        migrating =[m for m in higher_priority_tasks if m.migratable == True]

        sum_locrit =sum([ceil(start/ t.period) * t.lwcet for t in locrit])
        sum_hicrit =sum([ceil(start / t.period) * t.hwcet for t in hicrit])
        sum_migrating =sum([ceil(task.responsetime / t.period) * t.lwcet for t in migrating])

        checkit = get_wcet (task) + sum_locrit + sum_hicrit + sum_migrating
        if checkit == start:
            return checkit
        elif checkit > task.period:
            return checkit
        start = checkit


def both_himode (task, higher_priority_tasks, coreid):
    start = task.hwcet

    while True:
        locrit = [l for l in higher_priority_tasks if l.criticality == 'L']
        hicrit = [h for h in higher_priority_tasks if h.criticality == 'H']

        sum_locrit = sum([ceil(task.responsetime_aftermig_d[coreid] / t.period) * t.lwcet for t in locrit])
        sum_hicrit = sum([ceil(start / t.period) * t.hwcet for t in hicrit])

        checkit = task.hwcet + sum_locrit + sum_hicrit
        if checkit == start:
            return checkit
        elif checkit > task.period:
            return checkit
        start = checkit


def RLO (task, higher_priority_tasks):
    start = task.lwcet

    while True:
        next = sum([ceil(start / t.period) * t.lwcet for t in higher_priority_tasks])
        checkit = task.lwcet + next
        if checkit == start:
            return checkit
        elif checkit > task.period:
            return checkit
        start = checkit


def StateX (taskset):
    prioritylevel =len(taskset)
    current =prioritylevel
    for l in range (0, prioritylevel):
        found =False
        notassigned =[t for t in taskset if t.priority == -1]
        for each in notassigned:
            response_time =RLO (each, [x for x in notassigned if x.id != each.id])
            if response_time <= each.deadline:
                found = True
                each.responsetime =response_time
                each.priority =current
                current -=1
                break
        if found == False:
            return False
    return True


def StateY (taskset_fst, taskset_snd, sndincoming, locrit_coreid):

    prioritylevel =len(taskset_fst)
    for l in range(0, prioritylevel):
        task_i =[t for t in taskset_fst if t.priority == l+1][0] # +1 because the priority starts from 1, not from 0
        higher_priority_tasks =[t for t in taskset_fst if t.priority < task_i.priority]
        response_time =migration_process (task_i, higher_priority_tasks)
        if response_time > task_i.deadline:
            return False

    taskset_snd_tot =taskset_snd +sndincoming
    prioritylevel =len(taskset_snd_tot)
    current =prioritylevel
    for l in range(0, prioritylevel):
        found =False
        notassigned =[t for t in taskset_snd_tot if t.priority_aftermig_d[locrit_coreid] == -1]
        for each in notassigned:
            response_time =calculate_with_jitter (each, [x for x in notassigned if x.id != each.id], sndincoming)
            if response_time <= each.deadline:
                found =True
                each.responsetime_aftermig_d[locrit_coreid] =response_time
                each.priority_aftermig_d[locrit_coreid] =current
                current -=1
                break
        if found == False:
            return False
    return True


def StateBY (taskset, coreid):

    for each in taskset:
        if each.migratable == True:
            assert (each.priority_aftermig_d[coreid] != -1)

    hicrit =[t for t in taskset if t.criticality == 'H']

    for each in hicrit:
        response_time =both_himode (each, [x for x in taskset if x.id != each.id and x.priority_aftermig_d[coreid] < each.priority_aftermig_d[coreid]], coreid)
        if response_time > each.deadline:
            return False

    return True


def check_dualcore_system (cores):
    for c in cores:
        c.clear_core ()
        stateX = StateX (c.tasks)
        if stateX == False:
            return False

    fstcore =cores[0]
    sndcore =cores[1]

    fstoutgoing =[o for o in fstcore.tasks if o.migratable == True]
    sndoutgoing =[o for o in sndcore.tasks if o.migratable == True]

    if len (fstoutgoing) != 0:
        fststateY = StateY(fstcore.tasks, sndcore.tasks, fstoutgoing, fstcore.core_id)
        if fststateY == False:
            return False

    if len (sndoutgoing) != 0:
        sndstateY = StateY(sndcore.tasks, fstcore.tasks, sndoutgoing, sndcore.core_id)
        if sndstateY == False:
            return False

    if len (fstoutgoing) != 0 and len (sndoutgoing) != 0:
        fststateBY =StateBY (fstcore.tasks + sndoutgoing, fstcore.core_id)
        if fststateBY == False:
            return False

        sndstateBY =StateBY (sndcore.tasks + fstoutgoing, sndcore.core_id)
        if sndstateBY == False:
            return False

    return True