package com.utn.mobile.mapasolidario.event;

import com.utn.mobile.mapasolidario.BasePoint;
import com.utn.mobile.mapasolidario.User;

/**
 * Created by dani on 04/06/17.
 */

public class SaveUserSuccessEvent {

    private User resultadoDTO;

    public User getResultadoDTO() {
        return resultadoDTO;
    }

    public SaveUserSuccessEvent setResultadoDTO(User resultadoDTO) {
        this.resultadoDTO = resultadoDTO;
        return this;
    }


}
