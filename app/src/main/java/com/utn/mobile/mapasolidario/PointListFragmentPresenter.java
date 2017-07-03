package com.utn.mobile.mapasolidario;

import android.content.Context;

import com.utn.mobile.mapasolidario.event.FetchPuntosFailedEvent;
import com.utn.mobile.mapasolidario.event.FetchPuntosParaListaSuccessEvent;
import com.utn.mobile.mapasolidario.event.FetchPuntosSuccessEvent;
import com.utn.mobile.mapasolidario.task.FetchPuntosParaListaTask;
import com.utn.mobile.mapasolidario.util.UiUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by svillarreal on 02/07/17.
 */

public class PointListFragmentPresenter extends BasePresenter<PointItemListFragmentView> {

    public void fetchPuntos(Context context) {
        view.showProgressDialog();
        if (UiUtils.checkNetworkAvailable(context)) {
            new FetchPuntosParaListaTask(context).execute();
        } else {
            view.hideProgressDialog();
        }

    }

    public void onEvent(FetchPuntosFailedEvent event) {
        view.hideProgressDialog();
        view.showMessageError(event.getError());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFetchPuntosSuccessEvent(FetchPuntosParaListaSuccessEvent event) {
        view.hideProgressDialog();
        if (event.getResultadoDTO() != null) {
            if (!event.getResultadoDTO().isEmpty()) {
                PointListProvider.set(event.getResultadoDTO());
                view.loadPoints(event.getResultadoDTO());
            }
        }
    }

}
