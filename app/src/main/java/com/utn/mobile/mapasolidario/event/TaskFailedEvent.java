package com.utn.mobile.mapasolidario.event;

/**
 * Evento que se dispara cuando comienza una tarea en segundo plano.
 *
 * @author svillarreal
 */
public class TaskFailedEvent {

    private int errorMessageId;

    private String errorMessage;

    public int getErrorMessageId() {
        return errorMessageId;
    }

    public TaskFailedEvent setErrorMessageId(int errorMessageId) {
        this.errorMessageId = errorMessageId;
        return this;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public TaskFailedEvent setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }

}
