package com.utn.mobile.mapasolidario;

import android.view.View;

import com.utn.mobile.mapasolidario.dto.NovedadResponse;
import com.utn.mobile.mapasolidario.util.BaseView;
import com.utn.mobile.mapasolidario.util.FetchNewsErrors;

import java.util.List;

/**
 * Created by svillarreal on 08/05/17.
 */

interface NewsFragmentView extends BaseView {

    void showProgressDialog();

    void hideProgressDialog();

    void showMessageError(FetchNewsErrors error);

    void showMessageSinNovedades();

    void loadNovedadesPreNavigate(List<NovedadResponse> resultadoDTO);

    void changeItemColor(View itemView);
}
