package com.utn.mobile.mapasolidario.task;

import android.content.Context;

import com.google.inject.Inject;
import com.utn.mobile.mapasolidario.IRemoteService;
import com.utn.mobile.mapasolidario.dto.PuntoResponse;
import com.utn.mobile.mapasolidario.event.FetchPuntosSuccessEvent;
import com.utn.mobile.mapasolidario.event.FetchUserPointsSuccessEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import roboguice.util.RoboAsyncTask;

/**
 * Created by juani on 04/06/17.
 */

public class FetchUserPointsTask extends RoboAsyncTask<List<PuntoResponse>> {


    @Inject
    private IRemoteService remoteService;


    public FetchUserPointsTask(Context context) {
        super(context);
    }

    @Override
    public List<PuntoResponse> call() throws Exception {
        List<PuntoResponse> retVal = remoteService.fetchUserPointsService();
        return retVal;
    }

    @Override
    protected void onSuccess(List<PuntoResponse> response) {
        EventBus.getDefault().getDefault().post(new FetchUserPointsSuccessEvent().setResultadoDTO(response));
    }
}
