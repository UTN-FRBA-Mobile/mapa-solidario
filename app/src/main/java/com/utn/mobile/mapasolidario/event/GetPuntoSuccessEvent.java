package com.utn.mobile.mapasolidario.event;

import com.utn.mobile.mapasolidario.BasePoint;

/**
 * Created by dani on 04/06/17.
 */

public class GetPuntoSuccessEvent {

    private BasePoint resultadoDTO;

    public BasePoint getResultadoDTO() {
        return resultadoDTO;
    }

    public GetPuntoSuccessEvent setResultadoDTO(BasePoint resultadoDTO) {
        this.resultadoDTO = resultadoDTO;
        return this;
    }


}
