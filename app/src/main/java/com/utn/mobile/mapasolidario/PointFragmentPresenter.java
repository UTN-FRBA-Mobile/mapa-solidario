package com.utn.mobile.mapasolidario;

import android.content.Context;

import com.utn.mobile.mapasolidario.event.FetchPuntosFailedEvent;
import com.utn.mobile.mapasolidario.event.GetPuntoSuccessEvent;
import com.utn.mobile.mapasolidario.event.PostPuntoSuccessEvent;
import com.utn.mobile.mapasolidario.event.PutPuntoSuccessEvent;
import com.utn.mobile.mapasolidario.util.UiUtils;
import com.utn.mobile.mapasolidario.task.GetPuntoTask;
import com.utn.mobile.mapasolidario.task.PutPuntoTask;
import com.utn.mobile.mapasolidario.task.PostPuntoTask;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by dany on 25/05/2017.
 */

public class PointFragmentPresenter extends BasePresenter<PointFragmentView>{

    public void obtenerPunto(Context context,String id){
        view.showProgressDialog();
        if (UiUtils.checkNetworkAvailable(context)) {
            new GetPuntoTask(context,id).execute();
        }
    }

    public void actualizarPunto(Context context,String id, String json){
        view.showProgressDialog();
        if (UiUtils.checkNetworkAvailable(context)) {
            new PutPuntoTask(context,id,json).execute();
        }
    }
    public void guardarPunto(Context context,String json){
        view.showProgressDialog();
        if (UiUtils.checkNetworkAvailable(context)) {
            new PostPuntoTask(context,json).execute();
        }
    }


    public void onEvent(FetchPuntosFailedEvent event) {
        view.hideProgressDialog();
        view.showMessageError(event.getError());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetPuntoSuccessEvent(GetPuntoSuccessEvent event) {
        view.hideProgressDialog();
        if (event.getResultadoDTO() != null) {
          view.loadPoint(event.getResultadoDTO());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPostPuntoSuccessEvent(PostPuntoSuccessEvent event) {
        view.hideProgressDialog();
        if (event.getResultadoDTO() != null) {
            view.okPoint(event.getResultadoDTO());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPutPuntoSuccessEvent(PutPuntoSuccessEvent event) {
        view.hideProgressDialog();
        if (event.getResultadoDTO() != null) {
            view.okPoint(event.getResultadoDTO());
        }
    }

}
