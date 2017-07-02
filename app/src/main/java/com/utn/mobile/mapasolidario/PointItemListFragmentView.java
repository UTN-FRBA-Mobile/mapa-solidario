package com.utn.mobile.mapasolidario;

import com.utn.mobile.mapasolidario.dto.PuntoResponse;
import com.utn.mobile.mapasolidario.util.BaseView;
import com.utn.mobile.mapasolidario.util.FetchPuntosErrors;

import java.util.List;

/**
 * Created by svillarreal on 02/07/2017.
 */

public interface PointItemListFragmentView extends BaseView {

    void showProgressDialog();

    void hideProgressDialog();

    void loadPoints(List<PuntoResponse> resultadoDTO);

    void showMessageError(FetchPuntosErrors error);
}
