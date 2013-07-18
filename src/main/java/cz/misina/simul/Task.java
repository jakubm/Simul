package cz.misina.simul;

import org.joda.time.LocalDateTime;

/**
 * Created with IntelliJ IDEA.
 * User: Jakub
 * Date: 18.7.13
 * Time: 21:34
 */
public class Task {
    private int id;
    private LocalDateTime created;
    private LocalDateTime workStart;
    private LocalDateTime workFinish;
    private TaskStatus taskStatus;
    private int duration;

    public Task(int id, LocalDateTime created, int duration) {
        this.id = id;
        this.created = created;
        this.taskStatus = TaskStatus.WAITING;
        this.duration = duration;
//        System.out.println("new task created (" + id + ", " + created + ", " + duration + ")");
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public LocalDateTime getWorkStart() {
        return workStart;
    }

    public void setWorkStart(LocalDateTime workStart) {
        this.workStart = workStart;
        this.taskStatus = TaskStatus.WORKED;
    }

    public LocalDateTime getWorkFinish() {
        return workFinish;
    }

    public void setWorkFinish(LocalDateTime workFinish) {
        this.workFinish = workFinish;
        this.taskStatus = TaskStatus.FINISHED;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public int getDuration() {
        return duration;
    }
}
