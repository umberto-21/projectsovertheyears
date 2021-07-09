import random
from scipy.stats import loguniform

samplesnumber =10000
tasksforeachset =12
coresnumber =2

lperiod =10.0
hperiod =10000.0
percent =0.5
factor =2
hctasks =tasksforeachset*percent


class Task:
    def __init__(self, id, p, c, lw, hw, u):
        self.id =id
        self.deadline =p
        self.period =p
        self.criticality =c
        self.lwcet =lw
        self.hwcet =hw
        self.utilization =u
        self.priority =-1
        self.whereis =-1
        self.responsetime =-1
        self.migratable =False

        self.priority_aftermig_d ={}
        self.responsetime_aftermig_d ={}
        for i in range (0, coresnumber):
            self.priority_aftermig_d[i] =-1
            self.responsetime_aftermig_d[i] =-1

        self.priority_aftermig =-1
        self.responsetime_aftermig = -1

    def set_migratable (self):
        self.migratable =True

    def clear_migratable (self):
        assert (self.migratable == True)
        self.migratable =False
        for i in range (0, coresnumber):
            self.priority_aftermig_d[i] =-1
            self.responsetime_aftermig_d[i] =-1


def UUnifast_discard (ntasks, tot_u, nsets =1):
    sets =[]
    while len (sets) < nsets:
        utilizations =[]
        sumU =tot_u
        for i in range (1, ntasks):
            nextSumU =sumU *random.random () ** (1.0 / (ntasks-i))
            utilizations.append (sumU - nextSumU)
            sumU = nextSumU
        utilizations.append (sumU)

        if all (ut <= 1 for ut in utilizations):
            sets.append (utilizations)

    return sets


def Generate_periods ():
    return loguniform.rvs (lperiod, hperiod, size=tasksforeachset)


def Generate_taskset (utilization):
    taskset =[]
    taskset_utilizations =UUnifast_discard (tasksforeachset, utilization)

    for utilizations in taskset_utilizations:
        assert (all (ut <= 1 for ut in utilizations))

    for utilizations in taskset_utilizations:
        periods =Generate_periods ()
        assert (len (periods) == len (utilizations))
        for j in range (0, len (periods)):
            criticality ='H'
            lwcet =-1
            hwcet =-1

            if j < hctasks:
                hwcet =utilizations[j]*periods[j]
                lwcet =hwcet/factor
            else:
                lwcet =utilizations[j]*periods[j]
                hwcet =lwcet*factor
                criticality ='L'

            task = Task (j, periods[j], criticality, lwcet, hwcet, utilizations[j])
            taskset.append (task)

    return taskset


def Generate_samples (utilization):
    samples =[]

    for i in range (0, samplesnumber):
        samples.append (Generate_taskset(utilization))

    return samples







