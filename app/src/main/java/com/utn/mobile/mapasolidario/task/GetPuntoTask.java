package com.utn.mobile.mapasolidario.task;

import android.content.Context;

import com.google.inject.Inject;
import com.utn.mobile.mapasolidario.BasePoint;
import com.utn.mobile.mapasolidario.IRemoteService;
import com.utn.mobile.mapasolidario.event.GetPuntoSuccessEvent;

import org.greenrobot.eventbus.EventBus;


import roboguice.util.RoboAsyncTask;

/**
 * Created by juani on 04/06/17.
 */

public class GetPuntoTask extends RoboAsyncTask<BasePoint> {


    @Inject
    private IRemoteService remoteService;


    public GetPuntoTask(Context context) {
        super(context);
    }

    @Override
    public BasePoint call() throws Exception {
        BasePoint retVal = remoteService.getPuntoService();
        return retVal;
    }

    @Override
    protected void onSuccess(BasePoint response) {
        EventBus.getDefault().getDefault().post(new GetPuntoSuccessEvent().setResultadoDTO(response));
    }
}
