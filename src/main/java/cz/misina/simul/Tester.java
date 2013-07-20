    package cz.misina.simul;

import org.joda.time.LocalDateTime;
import org.joda.time.Minutes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Jakub
 * Date: 18.7.13
 * Time: 21:29
 */
public class Tester {
    public static void main(String[] args) {
        LocalDateTime start = LocalDateTime.now().minusHours(13);
        LocalDateTime actual = start;
        LocalDateTime finish = LocalDateTime.now();
        List<Task> actualTasks = new ArrayList<>();
        List<Task> actualTasks2 = new ArrayList<>();
        Worker worker = new Worker("Worker 1");
        Worker worker2 = new Worker("Worker 2");
        int counter = 0;
        int counter2 = 0;
        Task t = null;
        Task t2 = null;
        int taskPosition2 = -1;

        Random random = new Random(System.currentTimeMillis());

        while (actual.isBefore(finish)) {
            if (random.nextInt(400) == 0 && actual.plusHours(1).isBefore(finish)) {
                int duration = random.nextInt(6) + 5;
                actualTasks.add(new Task(++counter, actual, duration));
                actualTasks2.add(new Task(++counter2, actual, duration));
            }
            if (worker.getStatus() == WorkerStatus.FREE && actualTasks.size() > 0) {
                t = actualTasks.get(0);
                System.out.println(actual + ": " + worker.getName() + " work start id = " + t.getId() + " for " +
                        t.getDuration() + "" + " minutes. Task created at " + t.getCreated());
                if (worker.getLastChange() != null) {
                    System.out.println(worker.getName() + "  waiting time between tasks (mins) = " +
                            -Minutes.minutesBetween(actual, worker.getLastChange()).getMinutes());
                }
                System.out.println("Task waiting time (mins) = " +
                        -Minutes.minutesBetween(actual, t.getCreated()).getMinutes());
                t.setWorkStart(actual);
                worker.startWork(t.getId(), actual);
            }
            if (worker.getStatus() == WorkerStatus.WORKING) {
                if (t.getWorkStart().plusMinutes(t.getDuration()).isBefore(actual)) {
                    worker.finishWork(actual);
                    t.setWorkFinish(actual);
                    actualTasks.remove(0);
                    System.out.println("\n" + actual + ": " + worker.getName() + " work finished id = " + t.getId() + "\n");

                }
            }

            if (worker2.getStatus() == WorkerStatus.FREE && actualTasks2.size() > 0) {
                int shift = 0;
                int i = 0;
                t2 = actualTasks2.get(i);
                do {
                    i = actualTasks2.size() - 1 - shift++;
                    t2 = actualTasks2.get(i);
                } while (t2.getTaskStatus() != TaskStatus.WAITING && shift < actualTasks2.size());
                taskPosition2 = i;
                System.out.println(actual + ": " + worker2.getName() + " work start id = " + t2.getId() + " for " +
                        t2.getDuration() + "" + " minutes. Task created at " + t2.getCreated());
                if (worker2.getLastChange() != null) {
                    System.out.println(worker2.getName() + " waiting time between tasks (mins) = " +
                            -Minutes.minutesBetween(actual, worker2.getLastChange()).getMinutes());
                }
                System.out.println("Task waiting time (mins) = " +
                        -Minutes.minutesBetween(actual, t2.getCreated()).getMinutes());
                t2.setWorkStart(actual);
                worker2.startWork(t2.getId(), actual);
            }
            if (worker2.getStatus() == WorkerStatus.WORKING) {
                if (t2.getWorkStart().plusMinutes(t2.getDuration()).isBefore(actual)) {
                    worker2.finishWork(actual);
                    t2.setWorkFinish(actual);
                    actualTasks2.remove(taskPosition2);
                    System.out.println("\n" + actual + ": " + worker2.getName() + " work finished id = " + t2.getId() + "\n");

                }
            }

            actual = actual.plusSeconds(1);
        }
        printRemainingTasks(worker.getName(), actual, actualTasks);
        printRemainingTasks(worker2.getName(), actual, actualTasks2);
    }

    private static void printRemainingTasks(String name, LocalDateTime actual, List<Task> actualTasks) {
        System.out.println("Remaining tasks for " + name);
        for (Task tt: actualTasks) {
            System.out.println(tt.getId() + " created at " + tt.getCreated() + " waited for " +
                    Minutes.minutesBetween(tt.getCreated(), actual).getMinutes() + " minutes.");
        }
    }
}
