package com.utn.mobile.mapasolidario;

import android.content.Context;
import android.util.Log;

import com.google.inject.Inject;
import com.utn.mobile.mapasolidario.dto.PuntoResponse;
import com.utn.mobile.mapasolidario.event.FetchPuntosSuccessEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import roboguice.util.RoboAsyncTask;

/**
 * Created by juani on 04/06/17.
 */

class FetchPuntosTask extends RoboAsyncTask<List<PuntoResponse>> {


    @Inject
    private IRemoteService remoteService;


    public FetchPuntosTask(Context context) {
        super(context);
    }

    @Override
    public List<PuntoResponse> call() throws Exception {
        List<PuntoResponse> retVal = remoteService.fetchPuntosService();
        return retVal;
    }

    @Override
    protected void onSuccess(List<PuntoResponse> response) {
        EventBus.getDefault().getDefault().post(new FetchPuntosSuccessEvent().setResultadoDTO(response));
    }
}
