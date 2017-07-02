package com.utn.mobile.mapasolidario.user;

import com.utn.mobile.mapasolidario.dto.PuntoResponse;

import java.util.List;

/**
 * Created by gastonmazzeo on 6/25/17.
 */

public interface UserView {

    void loadPoints(List<PuntoResponse> points);
}
