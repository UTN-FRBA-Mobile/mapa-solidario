package com.utn.mobile.mapasolidario.task;

import android.content.Context;

import com.google.inject.Inject;
import com.utn.mobile.mapasolidario.BasePoint;
import com.utn.mobile.mapasolidario.IRemoteService;
import com.utn.mobile.mapasolidario.User;
import com.utn.mobile.mapasolidario.UserProvider;
import com.utn.mobile.mapasolidario.event.PostPuntoSuccessEvent;
import com.utn.mobile.mapasolidario.event.SaveUserSuccessEvent;

import org.greenrobot.eventbus.EventBus;

import roboguice.util.RoboAsyncTask;

/**
 * Created by dani on 11/06/17.
 */

public class SaveUserTask extends RoboAsyncTask<User> {

    protected Context context;
    @Inject
    private IRemoteService remoteService;

    public SaveUserTask(Context context) {
        super(context);
    }

    @Override
    public User call() throws Exception {
        User retVal = remoteService.saveUserService(UserProvider.get());
        return retVal;
    }


    @Override
    protected void onSuccess(User response) {
        EventBus.getDefault().getDefault().post(new SaveUserSuccessEvent().setResultadoDTO(response));
    }
}
