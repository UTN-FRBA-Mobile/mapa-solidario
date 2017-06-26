package com.utn.mobile.mapasolidario.event;

import com.utn.mobile.mapasolidario.dto.UserResponse;

/**
 * Created by Juanca on 25/6/17.
 */

public class PostUserSuccessEvent {

    private UserResponse resultadoDTO;

    public UserResponse getResultadoDTO() {
        return resultadoDTO;
    }

    public PostUserSuccessEvent setResultadoDTO(UserResponse resultadoDTO) {
        this.resultadoDTO = resultadoDTO;
        return this;
    }
}
