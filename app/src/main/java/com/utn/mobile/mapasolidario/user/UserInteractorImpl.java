package com.utn.mobile.mapasolidario.user;

import android.content.Context;

import com.utn.mobile.mapasolidario.dto.PuntoResponse;
import com.utn.mobile.mapasolidario.event.FetchUserPointsSuccessEvent;
import com.utn.mobile.mapasolidario.task.FetchUserPointsTask;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by gastonmazzeo on 6/25/17.
 */

public class UserInteractorImpl implements UserInteractor {

    OnFetchUserPointsFinishedListener listener;
    Context context;
    public UserInteractorImpl(Context _context){
        EventBus.getDefault().register(this);
        context = _context;
    }

    public void fetchUserPoints(OnFetchUserPointsFinishedListener _listener){
        listener = _listener;
        new FetchUserPointsTask(context).execute();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFetchUserPointsSuccessEvent(FetchUserPointsSuccessEvent event) {
        List<PuntoResponse> points = event.getResultadoDTO();
        listener.onSuccess(points);
    }
}
