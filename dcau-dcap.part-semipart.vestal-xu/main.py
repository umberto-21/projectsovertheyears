from tasksetsgeneration import Generate_samples
from tasksetssorting import samples_ordering_caud, samples_ordering_capd
from tasksetsbinpacking import first_fit, best_fit, worst_fit
from partitionedalgorithm import partitioned
from semipalgorithm import semip
import matplotlib.pyplot as plt

def percent (result):
    from tasksetsgeneration import samplesnumber
    return result*100/samplesnumber

def test_procedure (samples, results, scheduling):
    samples_caud =samples_ordering_caud (samples)
    samples_capd =samples_ordering_capd (samples)

    caud_ff, caud_bf, caud_wf =0, 0, 0
    capd_ff, capd_bf, capd_wf =0, 0, 0

    for each in samples_caud:
        caud_ff +=scheduling (each, first_fit)
        caud_bf +=scheduling (each, best_fit)
        caud_wf +=scheduling (each, worst_fit)

    results[0].append (percent (caud_ff))
    results[1].append (percent (caud_bf))
    results[2].append (percent (caud_wf))

    for each in samples_capd:
        capd_ff += scheduling (each, first_fit)
        capd_bf += scheduling (each, best_fit)
        capd_wf += scheduling (each, worst_fit)

    results[3].append (percent (capd_ff))
    results[4].append (percent (capd_bf))
    results[5].append (percent (capd_wf))


utilization =1.6

results = [[], [], [], [], [], []]
results_semip =[[], [], [], [], [], []]
utilization_vector =[]

while utilization <= 2.1:
    print (utilization)
    utilization_vector.append(utilization)
    samples =Generate_samples (utilization)
    test_procedure (samples, results, partitioned)
    test_procedure (samples, results_semip, semip)
    utilization +=0.028

import csv

with open ('results_partitioned.csv', 'w', newline='') as file:
    writer =csv.writer (file)
    writer.writerow (["LEVEL", "UFF", "UBF", "UWF", "PFF", "PBF", "PWF"])
    i =0
    while i < len (utilization_vector):
        writer.writerow ([utilization_vector[i], results[0][i], results[1][i], results[2][i], results[3][i], results[4][i], results[5][i]])
        i +=1

with open ('results_semip.csv', 'w', newline='') as file:
    writer =csv.writer (file)
    writer.writerow (["LEVEL", "UFF", "UBF", "UWF", "PFF", "PBF", "PWF"])
    i =0
    while i < len (utilization_vector):
        writer.writerow ([utilization_vector[i], results_semip[0][i], results_semip[1][i], results_semip[2][i], results_semip[3][i], results_semip[4][i], results_semip[5][i]])
        i +=1


plt.plot (utilization_vector, results[0], label= "CriticalAwareDescendingUtilization-FF")
plt.plot (utilization_vector, results[1], label= "CriticalAwareDescendingUtilization-BF")
plt.plot (utilization_vector, results[2], label= "CriticalAwareDescendingUtilization-WF")
plt.plot (utilization_vector, results[3], label= "CriticalAwareDescendingPeriod-FF")
plt.plot (utilization_vector, results[4], label= "CriticalAwareDescendingPeriod-BF")
plt.plot (utilization_vector, results[5], label= "CriticalAwareDescendingPeriod-WF")

plt.xlabel ("Utilization")
plt.ylabel ("Schedulable Tasksets")

plt.title ("Partitioned")
plt.legend ()
plt.show ()


plt.plot (utilization_vector, results_semip[0], label= "CriticalAwareDescendingUtilization-FF")
plt.plot (utilization_vector, results_semip[1], label= "CriticalAwareDescendingUtilization-BF")
plt.plot (utilization_vector, results_semip[2], label= "CriticalAwareDescendingUtilization-WF")
plt.plot (utilization_vector, results_semip[3], label= "CriticalAwareDescendingPeriod-FF")
plt.plot (utilization_vector, results_semip[4], label= "CriticalAwareDescendingPeriod-BF")
plt.plot (utilization_vector, results_semip[5], label= "CriticalAwareDescendingPeriod-WF")

plt.xlabel ("Utilization")
plt.ylabel ("Schedulable Tasksets")

plt.title ("Semi-partitioned")
plt.legend ()
plt.show ()




