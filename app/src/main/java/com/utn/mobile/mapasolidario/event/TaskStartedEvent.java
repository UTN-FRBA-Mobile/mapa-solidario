
package com.utn.mobile.mapasolidario.event;


/**
 * Evento que se dispara cuando comienza una tarea en segundo plano.
 *
 * @author svillarreal
 */
public class TaskStartedEvent {

    private int taskId = -1;

    private int progressDialogMessageId;

    public int getTaskId() {
        return taskId;
    }

    public TaskStartedEvent setTaskId(int taskId) {
        this.taskId = taskId;
        return this;
    }

    public int getProgressDialogMessageId() {
        return progressDialogMessageId;
    }

    public TaskStartedEvent setProgressDialogMessageId(int progressDialogMessageId) {
        this.progressDialogMessageId = progressDialogMessageId;
        return this;
    }

}
