
def sort_by_utilization (task):
    return task.utilization

def sort_by_period (task):
    return task.period


def criticaly_aware_utilization_descending (taskset):
    htasks =[x for x in taskset if x.criticality == 'H']
    ltasks =[x for x in taskset if x.criticality == 'L']

    htasks.sort (reverse=True, key=sort_by_utilization)
    ltasks.sort (reverse=True, key=sort_by_utilization)

    return htasks + ltasks


def criticaly_aware_period_descending (taskset):
    htasks =[x for x in taskset if x.criticality == 'H']
    ltasks =[x for x in taskset if x.criticality == 'L']

    htasks.sort (reverse=True, key=sort_by_period)
    ltasks.sort (reverse=True, key=sort_by_period)

    return htasks + ltasks


def samples_ordering_caud (samples):
    samples_ordered =[]
    for each in samples:
        samples_ordered.append (criticaly_aware_utilization_descending (each))
    return samples_ordered


def samples_ordering_capd (samples):
    samples_ordered = []
    for each in samples:
        samples_ordered.append (criticaly_aware_period_descending (each))
    return samples_ordered
