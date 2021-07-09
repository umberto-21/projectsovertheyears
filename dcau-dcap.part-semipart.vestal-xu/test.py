from tasksetsgeneration import Task
from responsetime import check_core
from responsetimesemip import check_dualcore_system

taskset_0_p =[
    Task (0, 2, 'L', 1, 0, 1/2),
    Task (1, 10, 'H', 1, 2, 2/10),
    Task (2, 100, 'H', 20, 20, 20/100)
]

taskset_1_p =[
    Task (0, 2, 'L', 1, 0, 1/2),
    Task (1, 10, 'H', 1, 5, 5/10),
    Task (2, 100, 'H', 20, 20, 20/100)
]

def set_properties_taskset_p (taskset_0):
    from tasksetsbinpacking import Core

    core =Core (0)

    for each in taskset_0:
        core.add_to_core (each)

    return core


taskset_0_sp = [
    Task (0, 36, 'H', 8, 16, 16/36),
    Task (1, 12, 'H', 3, 4, 4/12),
    Task (2, 6, 'L', 1, -1, 1/6),
    Task (3, 12, 'L', 1, -1, 1/12),
    Task (4, 12, 'H', 4, 5, 5/12),
    Task (5, 56, 'H', 10, 20, 20/56),
    Task (6, 9, 'L', 1, -1, 1/9),
    Task (7, 12, 'L', 1, -1, 1/12)
]

def set_properties_taskset_sp (taskset_1):
    from tasksetsbinpacking import Core, get_core_by_id

    priorities =[7, 3, 1, 5, 4, 8, 2, 6]
    taskset_1[3].set_migratable()
    taskset_1[7].set_migratable()

    for i in range (0, 4):
        taskset_1[i].whereis =0
        taskset_1[i].priority =priorities[i]

    for i in range (4, 8):
        taskset_1[i].whereis =1
        taskset_1[i].priority =priorities[i]

    cores =[Core (0), Core(1)]

    for each in taskset_1:
        core =get_core_by_id (each.whereis, cores)
        core.add_to_core (each)

    return cores


core_0 =set_properties_taskset_p (taskset_0_p)
core_1 =set_properties_taskset_p (taskset_1_p)
cores =set_properties_taskset_sp (taskset_0_sp)

assert (check_core (core_0) == True)
assert (check_core (core_1) == False)

check_dualcore_system (cores)

assert (check_dualcore_system (cores) == True)

