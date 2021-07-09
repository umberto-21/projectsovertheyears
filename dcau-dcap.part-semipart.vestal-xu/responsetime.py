from math import ceil


def calculate_response_time (task, higher_priority_tasks):
    def get_wcet (task_i):
        if task_i.criticality == 'H':
            return task_i.hwcet
        else:
            return task_i.lwcet

    start =get_wcet (task)

    while True:
        next = sum([ceil(start / t.period) * get_wcet(t) for t in higher_priority_tasks])
        checkit =get_wcet (task) +next
        if checkit == start:
            return checkit
        elif checkit > task.period:
            return checkit
        start =checkit


def AudsleyOPA (taskset):

    prioritylevel =len (taskset)
    current =prioritylevel
    for l in range (0, prioritylevel):
        found =False
        notassigned =[t for t in taskset if t.priority == -1]
        for each in notassigned:
            responsetime =calculate_response_time (each, [x for x in notassigned if x.id != each.id])
            if responsetime <= each.deadline:
                each.priority =current
                current -=1
                each.responsetime =responsetime
                found =True
                break
        if found == False:
            return False
    return True


def check_core (core):
    core.clear_core ()
    return AudsleyOPA (core.tasks)




