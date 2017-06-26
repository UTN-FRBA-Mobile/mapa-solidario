package com.utn.mobile.mapasolidario.event;

import com.utn.mobile.mapasolidario.util.GetUserErrors;

/**
 * Created by Juanca on 25/6/17.
 */

public class GetUserFailedEvent {

    private GetUserErrors error;

    public  GetUserFailedEvent(GetUserErrors error) {
        this.error = error;
    }

    public GetUserErrors getError() {
        return error;
    }
}
