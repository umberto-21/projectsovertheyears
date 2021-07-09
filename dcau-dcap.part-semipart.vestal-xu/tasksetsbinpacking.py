from tasksetsgeneration import coresnumber


class Core:
    def __init__(self, id):
        self.utilization_limit =1
        self.total_utilization =0
        self.tasks =[]
        self.core_id =id

    def add_to_core (self, task):
        task.whereis =self.core_id
        self.total_utilization += task.utilization
        self.tasks.append (task)


    def try_to_add (self, task):
        if self.total_utilization + task.utilization <= self.utilization_limit:
            task.whereis =self.core_id
            self.total_utilization +=task.utilization
            self.tasks.append (task)
            return True
        return False


    def remove_from_core (self, task):
        task.whereis =-1
        task.priority =-1
        task.responsetime =-1

        self.total_utilization -= task.utilization
        self.tasks.remove (task)


    def clear_core (self):
        for each in self.tasks:
            each.priority =-1
            each.responsetime =-1
            for i in range (0, coresnumber):
                each.responsetime_aftermig_d[i] =-1
                each.priority_aftermig_d[i] =-1


def generate_cores ():
    bins = []

    for i in range(0, coresnumber):
        bins.append(Core(i))
    return bins


def get_core_by_id (id, cores):
    return [c for c in cores if c.core_id == id][0]


def first_fit (bins, task):

    check =0
    while check < len (bins):
        assert (bins[check].core_id == check)
        check +=1

    found =False
    for b in bins:
        if b.total_utilization + task.utilization <= b.utilization_limit:
            b.add_to_core (task)
            found =True
            break
    if found == False:
        return False
    return True


def best_fit (bins, task):

    def sort_by_empty_space (bin):
        return bin.utilization_limit - bin.total_utilization

    found =False
    bins.sort (reverse=False, key=sort_by_empty_space)
    for b in bins:
        if b.total_utilization + task.utilization <= b.utilization_limit:
            b.add_to_core (task)
            found =True
            break
    if found == False:
        return False
    return True


def worst_fit (bins, task):

    def sort_by_empty_space (bin):
        return bin.utilization_limit - bin.total_utilization

    found =False
    bins.sort (reverse=True, key=sort_by_empty_space)
    for b in bins:
        if b.total_utilization + task.utilization <= b.utilization_limit:
            b.add_to_core (task)
            found =True
            break
    if found == False:
        return False
    return True







