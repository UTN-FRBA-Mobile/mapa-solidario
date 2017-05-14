package com.utn.mobile.mapasolidario.task;

import android.content.Context;
import android.util.Log;

import com.google.inject.Inject;
import com.utn.mobile.mapasolidario.IRemoteService;
import com.utn.mobile.mapasolidario.R;
import com.utn.mobile.mapasolidario.dto.NovedadResponse;
import com.utn.mobile.mapasolidario.event.FetchNewsFailedEvent;
import com.utn.mobile.mapasolidario.event.FetchNewsSuccessEvent;
import com.utn.mobile.mapasolidario.event.TaskCompletedEvent;
import com.utn.mobile.mapasolidario.event.TaskStartedEvent;
import com.utn.mobile.mapasolidario.model.ServerConnectionException;
import com.utn.mobile.mapasolidario.util.FetchNewsErrors;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;

import roboguice.util.RoboAsyncTask;

/**
 * Created by svillarreal on 08/05/17.
 */
public class FetchNewsTask extends RoboAsyncTask<List<NovedadResponse>> {

    private static final String TAG = "FetchNewsTask";
    static HashMap<Class<? extends Exception>, Integer> exceptionsMessages = new HashMap<Class<? extends Exception>, Integer>();

    static {
        exceptionsMessages.put(ServerConnectionException.class, R.string.no_connection);
    }

    @Inject
    private IRemoteService remoteService;

    public FetchNewsTask(Context context) {
        super(context);
    }

    @Override
    protected void onPreExecute() {
        EventBus.getDefault().post(new TaskStartedEvent().setProgressDialogMessageId(R.string.fetching_news));
    }

    @Override
    public List<NovedadResponse> call() {
        List<NovedadResponse> fetchNovedadResponse = remoteService.fetchNewsService();
        return fetchNovedadResponse;
    }

    @Override
    protected void onSuccess(List<NovedadResponse> response) {
        EventBus.getDefault().getDefault().post(new FetchNewsSuccessEvent().setResultadoDTO(response));
    }

    @Override
    protected void onException(Exception e) {
        if (e instanceof ServerConnectionException) {
            EventBus.getDefault().post(new FetchNewsFailedEvent(FetchNewsErrors.PROBLEMA_SERVIDOR));
            Log.e(TAG, "Error al consumir WS de fetchNews", e);
        } else {
            EventBus.getDefault().post(new FetchNewsFailedEvent(FetchNewsErrors.PROBLEMA_BUSQUEDA));
            Log.e(TAG, "Error al consumir WS de fetchNews", e);
        }
    }

    @Override
    protected void onFinally() {
        EventBus.getDefault().post(new TaskCompletedEvent());
    }

}
