package com.utn.mobile.mapasolidario.event;

import com.utn.mobile.mapasolidario.dto.UserResponse;

/**
 * Created by Juanca on 25/6/17.
 */

public class GetUserSuccessEvent {

    private UserResponse resultadoDTO;

    public UserResponse getResultadoDTO() {
        return resultadoDTO;
    }

    public GetUserSuccessEvent setResultadoDTO(UserResponse resultadoDTO) {
        this.resultadoDTO = resultadoDTO;
        return this;
    }
}
