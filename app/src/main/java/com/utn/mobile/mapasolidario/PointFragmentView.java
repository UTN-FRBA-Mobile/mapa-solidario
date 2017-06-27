package com.utn.mobile.mapasolidario;

import android.view.View;

import com.utn.mobile.mapasolidario.dto.PuntoResponse;
import com.utn.mobile.mapasolidario.util.BaseView;
import com.utn.mobile.mapasolidario.util.FetchPuntosErrors;

import java.util.List;


/**
 * Created by dany on 25/05/2017.
 */

interface PointFragmentView extends BaseView {

    void showMessageError(FetchPuntosErrors error);

    void loadPoint(BasePoint resultadoDTO);
    void okPoint(BasePoint resultadoDTO);

    void showProgressDialog();

    void hideProgressDialog();


}
