from tasksetsbinpacking import generate_cores, get_core_by_id
from responsetime import check_core
from responsetimesemip import check_dualcore_system

def help_selecting_core (cores, id):
    return [c for c in cores if c.core_id != id][0]


def help_selecting_candidates (taskset):
    def sort_by_priority (t):
        return t.priority

    lo_crit =[t for t in taskset if t.criticality == 'L' and t.migratable == False and t.priority != -1]

    lo_crit.sort (reverse=False, key=sort_by_priority)
    return lo_crit


def check_after_mig (cores, candidates):
    migratables = help_selecting_candidates(candidates)

    for m in migratables:
        m.set_migratable()
        system_status = check_dualcore_system(cores)
        if system_status == True:
            return True
        else:
            m.clear_migratable()

    return False


def semip (taskset, packingalgorithm):
    import copy

    cores =generate_cores()

    tasksetcopy =copy.deepcopy (taskset)

    for each in tasksetcopy:

        if each.criticality == 'H':

            if packingalgorithm (cores, each):
                core =get_core_by_id (each.whereis, cores)

                if not check_core (core):
                    core.remove_from_core (each)
                    sndcore =help_selecting_core (cores, core.core_id)

                    if sndcore.try_to_add (each):
                        if not check_core (sndcore):
                            return 0
                    else:
                        return 0
            else:
                return 0

        else:

            if packingalgorithm (cores, each):
                core =get_core_by_id (each.whereis, cores)
                core_copy =copy.deepcopy (core)
                if not check_core (core_copy):

                    if not check_after_mig (cores, core.tasks):
                        core.remove_from_core (each)
                        sndcore =help_selecting_core (cores, core.core_id)

                        if sndcore.try_to_add (each):
                            snd_copy =copy.deepcopy (sndcore)
                            if not check_core (snd_copy):
                                if not check_after_mig (cores, sndcore.tasks):
                                    return 0
                            else:
                                check_core (sndcore)
                        else:
                            return 0

                else:
                    check_core (core)

            else:
                return 0


    return 1


