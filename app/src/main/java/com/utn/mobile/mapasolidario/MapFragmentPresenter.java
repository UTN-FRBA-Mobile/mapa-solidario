package com.utn.mobile.mapasolidario;

import android.content.Context;

import com.utn.mobile.mapasolidario.event.FetchNewsFailedEvent;
import com.utn.mobile.mapasolidario.event.FetchPuntosFailedEvent;
import com.utn.mobile.mapasolidario.event.FetchPuntosSuccessEvent;
import com.utn.mobile.mapasolidario.util.UiUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by juani on 04/06/17.
 */

public class MapFragmentPresenter extends BasePresenter<MapFragmentView> {

    public void fetchPuntos(Context context) {
        view.showProgressDialog();
        if (UiUtils.checkNetworkAvailable(context)) {
            new FetchPuntosTask(context).execute();
        }

    }

    public void onEvent(FetchPuntosFailedEvent event) {
        view.hideProgressDialog();
        view.showMessageError(event.getError());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFetchPuntosSuccessEvent(FetchPuntosSuccessEvent event) {
        view.hideProgressDialog();
        if (event.getResultadoDTO() != null) {
            if (!event.getResultadoDTO().isEmpty()) {
                view.loadPointsInMap(event.getResultadoDTO());
            }
        }
    }

}
