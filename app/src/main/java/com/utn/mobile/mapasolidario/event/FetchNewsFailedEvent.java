package com.utn.mobile.mapasolidario.event;

import com.utn.mobile.mapasolidario.util.FetchNewsErrors;

public class FetchNewsFailedEvent {

    private FetchNewsErrors error;

    public FetchNewsFailedEvent(FetchNewsErrors error) {
        this.error = error;
    }

    public FetchNewsErrors getError() {
        return error;
    }

}
