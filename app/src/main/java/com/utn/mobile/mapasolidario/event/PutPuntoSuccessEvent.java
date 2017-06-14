package com.utn.mobile.mapasolidario.event;

import com.utn.mobile.mapasolidario.BasePoint;

/**
 * Created by dani on 04/06/17.
 */

public class PutPuntoSuccessEvent {

    private BasePoint resultadoDTO;

    public BasePoint getResultadoDTO() {
        return resultadoDTO;
    }

    public PutPuntoSuccessEvent setResultadoDTO(BasePoint resultadoDTO) {
        this.resultadoDTO = resultadoDTO;
        return this;
    }


}
