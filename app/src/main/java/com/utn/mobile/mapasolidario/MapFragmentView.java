package com.utn.mobile.mapasolidario;

import com.utn.mobile.mapasolidario.dto.PuntoResponse;
import com.utn.mobile.mapasolidario.util.BaseView;

import java.util.List;

/**
 * Created by juani on 04/06/17.
 */

interface MapFragmentView extends BaseView{

    void showProgressDialog();

    void hideProgressDialog();

    void loadPointsInMap(List<PuntoResponse> resultadoDTO);
}
