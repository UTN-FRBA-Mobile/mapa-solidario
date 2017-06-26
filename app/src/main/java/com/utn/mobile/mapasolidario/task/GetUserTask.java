package com.utn.mobile.mapasolidario.task;

import android.content.Context;

import com.google.inject.Inject;
import com.utn.mobile.mapasolidario.IRemoteService;
import com.utn.mobile.mapasolidario.dto.UserResponse;
import com.utn.mobile.mapasolidario.event.GetUserSuccessEvent;

import roboguice.util.RoboAsyncTask;
import org.greenrobot.eventbus.EventBus;


/**
 * Created by Juanca on 25/6/17.
 */

public class GetUserTask extends RoboAsyncTask<UserResponse> {

    @Inject
    private IRemoteService remoteService;

    private String user_id;
    protected Context context;

    public GetUserTask(Context context, String id) {
        super(context);
        user_id = id;
    }


    @Override
    public UserResponse call() throws Exception {
        UserResponse retVal = remoteService.getUserService(user_id);
        return retVal;
    }

    @Override
    protected void onSuccess(UserResponse respone) throws Exception {
        EventBus.getDefault().getDefault().post(new GetUserSuccessEvent().setResultadoDTO(respone));
    }
}
