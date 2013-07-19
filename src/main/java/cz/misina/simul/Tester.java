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
        Worker worker = new Worker("Worker 1");
        int counter = 0;
        Task t = null;
        int taskPosition = -1;

        Random random = new Random(System.currentTimeMillis());

        while (actual.isBefore(finish)) {
            if (random.nextInt(400) == 0) {
                actualTasks.add(new Task(++counter, actual, random.nextInt(4) + 5));
            }
            if (worker.getStatus() == WorkerStatus.FREE && actualTasks.size() > 0) {
                int shift = 0;
                int i = 0;
                t = actualTasks.get(i);
                do {
                    i = actualTasks.size() - 1 - shift++;
                    t = actualTasks.get(i);
                } while (t.getTaskStatus() != TaskStatus.WAITING && shift < actualTasks.size());
                taskPosition = i;
                System.out.println("work start at " + actual + " id =  " + t.getId() + " for " + t.getDuration() + "" +
                        " minutes. Task created at " + t.getCreated());
                if (worker.getLastChange() != null) {
                    System.out.println("Worker waiting time between tasks (mins) = " +
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
                   actualTasks.remove(taskPosition);
                   System.out.println("work finished at " + actual + " with " + t.getId());

               }
            }

            actual = actual.plusSeconds(1);
        }
        System.out.println("Remaining tasks");
        for (Task tt: actualTasks) {
            System.out.println(tt.getId() + " created at " + tt.getCreated());
        }
    }
}
