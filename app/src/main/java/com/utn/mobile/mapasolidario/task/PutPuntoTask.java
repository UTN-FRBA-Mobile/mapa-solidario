package com.utn.mobile.mapasolidario.task;

import android.content.Context;

import com.google.inject.Inject;
import com.utn.mobile.mapasolidario.BasePoint;
import com.utn.mobile.mapasolidario.IRemoteService;
import com.utn.mobile.mapasolidario.event.PutPuntoSuccessEvent;

import org.greenrobot.eventbus.EventBus;

import roboguice.util.RoboAsyncTask;

/**
 * Created by dani on 11/06/17.
 */

public class PutPuntoTask extends RoboAsyncTask<BasePoint> {

    private String clave="";
    private String datos="";
    protected Context context;
    @Inject
    private IRemoteService remoteService;

    public PutPuntoTask(Context context, String id, String json) {
        super(context);
        clave = id;
        datos = json;
//        this.context = context;
//        RoboGuice.getInjector(context).injectMembers(this);
    }

    @Override
    public BasePoint call() throws Exception {
        BasePoint retVal = remoteService.putPuntoService(clave,datos);
        return retVal;
    }


    @Override
    protected void onSuccess(BasePoint response) {
        EventBus.getDefault().getDefault().post(new PutPuntoSuccessEvent().setResultadoDTO(response));
    }
}
