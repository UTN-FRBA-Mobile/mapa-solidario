package com.utn.mobile.mapasolidario.event;

import com.utn.mobile.mapasolidario.dto.PuntoResponse;

import java.util.List;

/**
 * Created by juani on 04/06/17.
 */

public class FetchPuntosParaListaSuccessEvent {

    private List<PuntoResponse> resultadoDTO;

    public List<PuntoResponse> getResultadoDTO() {
        return resultadoDTO;
    }

    public FetchPuntosParaListaSuccessEvent setResultadoDTO(List<PuntoResponse> resultadoDTO) {
        this.resultadoDTO = resultadoDTO;
        return this;
    }


}
