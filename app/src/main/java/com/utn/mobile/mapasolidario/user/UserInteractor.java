package com.utn.mobile.mapasolidario.user;

import com.utn.mobile.mapasolidario.dto.PuntoResponse;

import java.util.List;

/**
 * Created by gastonmazzeo on 6/25/17.
 */

public interface UserInteractor {

    interface OnFetchUserPointsFinishedListener {
        void onSuccess(List<PuntoResponse> points);
        void onFailure();
    }

    void fetchUserPoints(final OnFetchUserPointsFinishedListener listener);

}
