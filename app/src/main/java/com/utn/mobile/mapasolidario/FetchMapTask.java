package com.utn.mobile.mapasolidario;

import android.content.Context;

import com.google.inject.Inject;
import com.utn.mobile.mapasolidario.dto.NovedadResponse;
import com.utn.mobile.mapasolidario.dto.PuntoResponse;

import java.util.ArrayList;
import java.util.List;

import roboguice.util.RoboAsyncTask;

/**
 * Created by juani on 04/06/17.
 */

class FetchMapTask extends RoboAsyncTask<List<PuntoResponse>> {


    @Inject
    private IRemoteService remoteService;


    public FetchMapTask(Context context) {
        super(context);
    }

    @Override
    public List<PuntoResponse> call() throws Exception {
        List<PuntoResponse> retVal = remoteService.fetchPuntosService();
        return retVal;
    }
}
