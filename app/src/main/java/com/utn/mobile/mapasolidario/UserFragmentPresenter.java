package com.utn.mobile.mapasolidario;

/**
 * Created by Juanca on 25/6/17.
 */

import android.content.Context;

import com.utn.mobile.mapasolidario.dto.UserResponse;
import com.utn.mobile.mapasolidario.event.GetUserFailedEvent;
import com.utn.mobile.mapasolidario.event.GetUserSuccessEvent;
import com.utn.mobile.mapasolidario.event.PostUserSuccessEvent;
import com.utn.mobile.mapasolidario.task.GetUserTask;
import com.utn.mobile.mapasolidario.task.PostUserTask;
import com.utn.mobile.mapasolidario.util.UiUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class UserFragmentPresenter extends BasePresenter<UserFragmentView> {


    public void GetUser(Context context, String id) {
        view.showProgressDialog();
        if (UiUtils.checkNetworkAvailable(context)) {
            new GetUserTask(context, id).execute();
        }

    }

    public void PostUser(Context context,UserResponse userResponse){
        view.showProgressDialog();
        if (UiUtils.checkNetworkAvailable(context)) {
            new PostUserTask(context,userResponse).execute();
        }
    }


    public void onEvent(GetUserFailedEvent event) {
        view.hideProgressDialog();
        view.showMessageError(event.getError());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetUserSuccessEvent(GetUserSuccessEvent event) {
        view.hideProgressDialog();
        if (event.getResultadoDTO() != null) {
            view.loadUserById(event.getResultadoDTO());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPostUserSuccessEvent(PostUserSuccessEvent event) {
        view.hideProgressDialog();
        if (event.getResultadoDTO() != null) {
            view.okUser(event.getResultadoDTO());
        }
    }
}
