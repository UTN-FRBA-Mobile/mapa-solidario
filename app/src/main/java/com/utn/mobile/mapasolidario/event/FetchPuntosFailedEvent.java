package com.utn.mobile.mapasolidario.event;

import com.utn.mobile.mapasolidario.util.FetchPuntosErrors;

public class FetchPuntosFailedEvent {

    private FetchPuntosErrors error;

    public FetchPuntosFailedEvent(FetchPuntosErrors error) {
        this.error = error;
    }

    public FetchPuntosErrors getError() {
        return error;
    }

}
