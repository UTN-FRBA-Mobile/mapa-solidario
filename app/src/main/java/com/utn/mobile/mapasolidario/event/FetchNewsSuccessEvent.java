package com.utn.mobile.mapasolidario.event;

import com.utn.mobile.mapasolidario.dto.NovedadResponse;

import java.util.List;

/**
 * Created by svillarreal on 13/05/17.
 */

public class FetchNewsSuccessEvent {

    private List<NovedadResponse> resultadoDTO;

    public List<NovedadResponse> getResultadoDTO() {
        return resultadoDTO;
    }

    public FetchNewsSuccessEvent setResultadoDTO(List<NovedadResponse> resultadoDTO) {
        this.resultadoDTO = resultadoDTO;
        return this;
    }

}
