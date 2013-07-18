package cz.misina.simul;

import org.joda.time.LocalDateTime;

/**
 * Created with IntelliJ IDEA.
 * User: Jakub
 * Date: 18.7.13
 * Time: 21:37
 */
public class Worker {
    private String name;
    private int actualTask;
    private WorkerStatus status;
    private LocalDateTime lastChange;

    public Worker(String name) {
        this.name = name;
        this.status = WorkerStatus.FREE;
    }

    public int getActualTask() {
        return actualTask;
    }

    public WorkerStatus getStatus() {
        return status;
    }

    public void startWork(int task, LocalDateTime actual) {
        actualTask = task;
        status = WorkerStatus.WORKING;
        lastChange = actual;
    }

    public void finishWork(LocalDateTime actual) {
        actualTask = 0;
        status = WorkerStatus.FREE;
        lastChange = actual;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getLastChange() {
        return lastChange;
    }
}
