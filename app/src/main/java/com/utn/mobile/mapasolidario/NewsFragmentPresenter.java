package com.utn.mobile.mapasolidario;

import android.content.Context;

import com.utn.mobile.mapasolidario.event.FetchNewsFailedEvent;
import com.utn.mobile.mapasolidario.event.FetchNewsSuccessEvent;
import com.utn.mobile.mapasolidario.task.FetchNewsTask;
import com.utn.mobile.mapasolidario.util.UiUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by svillarreal on 08/05/17.
 */

class NewsFragmentPresenter extends BasePresenter<NewsFragmentView> {

    public void fetchNews(Context context) {
        view.showProgressDialog();
        if (UiUtils.checkNetworkAvailable(context)) {
            new FetchNewsTask(context).execute();
        }else {
            view.hideProgressDialog();
        }
    }

    public void onEvent(FetchNewsFailedEvent event) {
        view.hideProgressDialog();
        view.showMessageError(event.getError());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFetchNewsSuccessEvent(FetchNewsSuccessEvent event) {
        view.hideProgressDialog();
        if (event.getResultadoDTO() != null) {
            if (event.getResultadoDTO().isEmpty()) {
                view.showMessageSinNovedades();
            } else {
                view.loadNovedadesPreNavigate(event.getResultadoDTO());
            }
        } else {
            view.showMessageSinNovedades();
        }
    }

}
