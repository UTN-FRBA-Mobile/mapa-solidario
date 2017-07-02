package com.utn.mobile.mapasolidario;

import com.utn.mobile.mapasolidario.dto.NovedadResponse;
import com.utn.mobile.mapasolidario.dto.PuntoResponse;
import com.utn.mobile.mapasolidario.dto.PuntoUpdate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by svillarreal on 13/05/17.
 */

public class RemoteServiceImpl implements IRemoteService {

    public static final String BACKEND_BASE_URL = "http://morning-peak-11897.herokuapp.com/";

    protected MapaSolidarioRetrofitService getMapaSolidarioRetrofitService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BACKEND_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(MapaSolidarioRetrofitService.class);
    }

    @Override
    public List<NovedadResponse> fetchNewsService() {
        List<NovedadResponse> fetchNovedadResponseList = new ArrayList<NovedadResponse>();
  //      MapaSolidarioRetrofitService retrofitService = null;
        MapaSolidarioRetrofitService service = getMapaSolidarioRetrofitService();
        try {
            Response<List<NovedadResponse>> newsResponse = service.fetchNews().execute();
            fetchNovedadResponseList = newsResponse.body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fetchNovedadResponseList;
    }

    @Override
    public List<PuntoResponse> fetchPuntosService() {
        List<PuntoResponse> fetchPuntosResponseList = new ArrayList<PuntoResponse>();
   //     MapaSolidarioRetrofitService retrofitService = null;
        MapaSolidarioRetrofitService service = getMapaSolidarioRetrofitService();
        try {
            Response<List<PuntoResponse>> puntosResponse = service.fetchPuntos().execute();
            fetchPuntosResponseList = puntosResponse.body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fetchPuntosResponseList;
    }

    @Override
    public List<PuntoResponse> fetchUserPointsService() {
        List<PuntoResponse> fetchUserPointsResponseList = new ArrayList<PuntoResponse>();
        MapaSolidarioRetrofitService service = getMapaSolidarioRetrofitService();
        try {
            Response<List<PuntoResponse>> puntosResponse = service.fetchUserPoints(UserProvider.get().getId()).execute();
            fetchUserPointsResponseList = puntosResponse.body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fetchUserPointsResponseList;
    }

    @Override
    public User getUserService(String id) {
        User user = new User();
        MapaSolidarioRetrofitService service = getMapaSolidarioRetrofitService();
        try {
            Response<User> userResponse = service.getUser(id).execute();
            user = userResponse.body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public BasePoint getPuntoService(String id) {

        MapaSolidarioRetrofitService service = getMapaSolidarioRetrofitService();
        BasePoint puntoDetalle = null;
        try {
            Response<BasePoint> puntosResponse = service.getPunto(id).execute();
            puntoDetalle = puntosResponse.body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return puntoDetalle;
    }

    @Override
    public User saveUserService(User json) {
        User user = new User();
        MapaSolidarioRetrofitService service = getMapaSolidarioRetrofitService();
        try {
            Response<User> userResponse = service.saveUser(json).execute();
            user = userResponse.body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public BasePoint postPuntoService(BasePoint json) {
        BasePoint puntoDetalle = null;
        MapaSolidarioRetrofitService service = getMapaSolidarioRetrofitService();
        try {
            Response<BasePoint> puntosResponse = service.postPunto(json).execute();
            puntoDetalle = puntosResponse.body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return puntoDetalle;
    }

    @Override
    public BasePoint putPuntoService(String id, PuntoUpdate json) {
        BasePoint puntoDetalle = null;
        MapaSolidarioRetrofitService service = getMapaSolidarioRetrofitService();
        try {
            Response<BasePoint> puntosResponse = service.putPunto(id,json).execute();
            puntoDetalle = puntosResponse.body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return puntoDetalle;
    }

    @Override
    public BasePoint putAyudaService(String id) {
        BasePoint puntoDetalle = null;
        MapaSolidarioRetrofitService service = getMapaSolidarioRetrofitService();
        try {
            Response<BasePoint> puntosResponse = service.putAyuda(id).execute();
            puntoDetalle = puntosResponse.body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return puntoDetalle;
    }

    @Override
    public BasePoint deletePuntoService(String id) {
        BasePoint puntoDetalle = null;
        MapaSolidarioRetrofitService service = getMapaSolidarioRetrofitService();
        try {
            Response<BasePoint> puntosResponse = service.deletePunto(id).execute();
            puntoDetalle = puntosResponse.body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return puntoDetalle;
    }

}
