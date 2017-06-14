package com.utn.mobile.mapasolidario.event;

import com.utn.mobile.mapasolidario.BasePoint;

/**
 * Created by dani on 04/06/17.
 */

public class PostPuntoSuccessEvent {

    private BasePoint resultadoDTO;

    public BasePoint getResultadoDTO() {
        return resultadoDTO;
    }

    public PostPuntoSuccessEvent setResultadoDTO(BasePoint resultadoDTO) {
        this.resultadoDTO = resultadoDTO;
        return this;
    }


}
