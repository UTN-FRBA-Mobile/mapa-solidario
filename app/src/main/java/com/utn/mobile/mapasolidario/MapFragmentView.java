package com.utn.mobile.mapasolidario;

import com.utn.mobile.mapasolidario.dto.PuntoResponse;
import com.utn.mobile.mapasolidario.util.BaseView;
import com.utn.mobile.mapasolidario.util.FetchPuntosErrors;

import java.util.List;

/**
 * Created by juani on 04/06/17.
 */

interface MapFragmentView extends BaseView{

    void showProgressDialog();

    void hideProgressDialog();

    void showMessageError(FetchPuntosErrors error);

    void loadPointsInMap(List<PuntoResponse> resultadoDTO);
}
