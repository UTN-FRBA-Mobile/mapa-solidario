package com.utn.mobile.mapasolidario.user;

import com.utn.mobile.mapasolidario.dto.PuntoResponse;
import com.utn.mobile.mapasolidario.util.BaseView;

import java.util.List;

/**
 * Created by gastonmazzeo on 6/25/17.
 */

public interface UserView extends BaseView{

    void loadPoints(List<PuntoResponse> points);
}
