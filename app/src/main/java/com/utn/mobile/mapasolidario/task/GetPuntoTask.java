package com.utn.mobile.mapasolidario.task;

import android.content.Context;

import com.google.inject.Inject;
import com.utn.mobile.mapasolidario.BasePoint;
import com.utn.mobile.mapasolidario.IRemoteService;
import com.utn.mobile.mapasolidario.event.GetPuntoSuccessEvent;

import org.greenrobot.eventbus.EventBus;


import roboguice.RoboGuice;
import roboguice.util.RoboAsyncTask;

/**
 * Created by dani on 11/06/17.
 */

public class GetPuntoTask extends RoboAsyncTask<BasePoint> {

    private String clave;
    protected Context context;
    @Inject
    private IRemoteService remoteService;

    public GetPuntoTask(Context context, String id) {
        super(context);
        clave = id;
//        this.context = context;
//        RoboGuice.getInjector(context).injectMembers(this);
    }

    @Override
    public BasePoint call() throws Exception {
        BasePoint retVal = remoteService.getPuntoService(clave);
        return retVal;
    }


    @Override
    protected void onSuccess(BasePoint response) {
        EventBus.getDefault().getDefault().post(new GetPuntoSuccessEvent().setResultadoDTO(response));
    }
}
