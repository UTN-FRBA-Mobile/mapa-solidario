package com.utn.mobile.mapasolidario.task;

import android.content.Context;

import com.google.inject.Inject;
import com.utn.mobile.mapasolidario.BasePoint;
import com.utn.mobile.mapasolidario.IRemoteService;
import com.utn.mobile.mapasolidario.event.PostPuntoSuccessEvent;

import org.greenrobot.eventbus.EventBus;

import roboguice.util.RoboAsyncTask;

/**
 * Created by dani on 11/06/17.
 */

public class PostPuntoTask extends RoboAsyncTask<BasePoint> {

    private BasePoint punto;
    protected Context context;
    @Inject
    private IRemoteService remoteService;

    public PostPuntoTask(Context context, BasePoint json) {
        super(context);
        punto = json;
    }

    @Override
    public BasePoint call() throws Exception {
        BasePoint retVal = remoteService.postPuntoService(punto);
        return retVal;
    }


    @Override
    protected void onSuccess(BasePoint response) {
        EventBus.getDefault().getDefault().post(new PostPuntoSuccessEvent().setResultadoDTO(response));
    }
}
