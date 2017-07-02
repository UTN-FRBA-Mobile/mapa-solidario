package com.utn.mobile.mapasolidario.user;

import android.content.Context;

import com.utn.mobile.mapasolidario.dto.PuntoResponse;
import com.utn.mobile.mapasolidario.login.LoginInteractor;
import com.utn.mobile.mapasolidario.login.LoginInteractorImpl;
import com.utn.mobile.mapasolidario.login.LoginView;
import com.utn.mobile.mapasolidario.task.FetchUserPointsTask;

import java.util.List;

/**
 * Created by gastonmazzeo on 6/25/17.
 */

public class UserPresenterImpl implements UserPresenter, UserInteractor.OnFetchUserPointsFinishedListener{

    private UserView userView;
    private UserInteractor userInteractor;

    public UserPresenterImpl(UserView userView, Context context) {
        this.userView = userView;
        this.userInteractor = new UserInteractorImpl(context);
    }

    @Override
    public void fetchUserPoints(Context context) {
        userInteractor.fetchUserPoints(this);
    }

    @Override
    public void onSuccess(List<PuntoResponse> points) {
        userView.loadPoints(points);

    }

    @Override
    public void onFailure() {
    }
}
