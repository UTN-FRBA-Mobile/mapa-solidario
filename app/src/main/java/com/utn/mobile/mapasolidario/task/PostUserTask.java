package com.utn.mobile.mapasolidario.task;

import android.content.Context;

import com.google.inject.Inject;
import com.utn.mobile.mapasolidario.IRemoteService;
import com.utn.mobile.mapasolidario.dto.UserResponse;
import com.utn.mobile.mapasolidario.event.PostUserSuccessEvent;

import org.greenrobot.eventbus.EventBus;

import roboguice.util.RoboAsyncTask;

/**
 * Created by Juanca on 25/6/17.
 */

public class PostUserTask extends RoboAsyncTask<UserResponse> {

    private UserResponse Usuario;
    protected Context context;
    @Inject
    private IRemoteService remoteService;

    public PostUserTask(Context context, UserResponse userResponse) {
        super(context);
        Usuario = userResponse;
    }

    @Override
    public UserResponse call() throws Exception {
        UserResponse retVal = remoteService.postUserService(Usuario);
        return retVal;
    }

    @Override
    protected void onSuccess(UserResponse response) {
        EventBus.getDefault().getDefault().post(new PostUserSuccessEvent().setResultadoDTO(response));
    }
}
