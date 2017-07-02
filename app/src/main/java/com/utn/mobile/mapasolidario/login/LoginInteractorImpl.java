package com.utn.mobile.mapasolidario.login;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.utn.mobile.mapasolidario.User;
import com.utn.mobile.mapasolidario.event.GetUserSuccessEvent;
import com.utn.mobile.mapasolidario.event.SaveUserSuccessEvent;
import com.utn.mobile.mapasolidario.task.GetUserTask;
import com.utn.mobile.mapasolidario.task.SaveUserTask;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

public class LoginInteractorImpl implements LoginInteractor {

    Context context;
    OnGetUserFinishedListener listener;
    OnSaveUserFinishedListener saveListener;
    public LoginInteractorImpl(Context _context){
        EventBus.getDefault().register(this);
        context = _context;
    }

    @Override
    public void getUser(String id, final OnGetUserFinishedListener _listener){
        listener = _listener;
        if(id != null){
            new GetUserTask(context, id).execute();
        }
    }

    @Override
    public void saveUser(final OnSaveUserFinishedListener _listener){
        saveListener = _listener;
        new SaveUserTask(context).execute();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetUserSuccessEvent(GetUserSuccessEvent event) {
        User _user = event.getResultadoDTO();
        if(_user != null && _user.getId() == null)
            listener.onUserNotExists();
        else
            listener.onUserExists(_user);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSaveUserSuccessEvent(SaveUserSuccessEvent event) {
        User _user = event.getResultadoDTO();
        if(_user.getId() == null)
            saveListener.onUserSaved(_user);
        else
            saveListener.onUserNotSaved();
    }
}
