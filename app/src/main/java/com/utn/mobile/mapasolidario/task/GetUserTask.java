package com.utn.mobile.mapasolidario.task;

import android.content.Context;

import com.google.inject.Inject;
import com.utn.mobile.mapasolidario.BasePoint;
import com.utn.mobile.mapasolidario.IRemoteService;
import com.utn.mobile.mapasolidario.User;
import com.utn.mobile.mapasolidario.event.GetPuntoSuccessEvent;
import com.utn.mobile.mapasolidario.event.GetUserSuccessEvent;

import org.greenrobot.eventbus.EventBus;

import roboguice.util.RoboAsyncTask;

/**
 * Created by dani on 11/06/17.
 */

public class GetUserTask extends RoboAsyncTask<User> {

    private String clave;
    protected Context context;
    @Inject
    private IRemoteService remoteService;

    public GetUserTask(Context context, String id) {
        super(context);
        clave = id;
//        this.context = context;
//        RoboGuice.getInjector(context).injectMembers(this);
    }

    @Override
    public User call() throws Exception {
        User retVal = remoteService.getUserService(clave);
        return retVal;
    }


    @Override
    protected void onSuccess(User response) {
        EventBus.getDefault().getDefault().post(new GetUserSuccessEvent().setResultadoDTO(response));
    }
}
