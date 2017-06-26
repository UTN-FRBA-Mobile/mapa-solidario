package com.utn.mobile.mapasolidario.event;

import com.utn.mobile.mapasolidario.User;

/**
 * Created by dani on 04/06/17.
 */

public class GetUserSuccessEvent {

    private User resultadoDTO;

    public User getResultadoDTO() {
        return resultadoDTO;
    }

    public GetUserSuccessEvent setResultadoDTO(User resultadoDTO) {
        this.resultadoDTO = resultadoDTO;
        return this;
    }


}
