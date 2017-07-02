package com.utn.mobile.mapasolidario;

import com.google.gson.Gson;
import com.google.inject.Inject;
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

    private static Gson gson;
    @Inject
    public BasePoint puntoDetalle;

    @Override
    public List<NovedadResponse> fetchNewsService() {
        List<NovedadResponse> fetchNovedadResponseList = new ArrayList<NovedadResponse>();
  //      MapaSolidarioRetrofitService retrofitService = null;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://morning-peak-11897.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MapaSolidarioRetrofitService service = retrofit.create(MapaSolidarioRetrofitService.class);
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
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://morning-peak-11897.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MapaSolidarioRetrofitService service = retrofit.create(MapaSolidarioRetrofitService.class);
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
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://morning-peak-11897.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MapaSolidarioRetrofitService service = retrofit.create(MapaSolidarioRetrofitService.class);
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
        User _user = new User();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://morning-peak-11897.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MapaSolidarioRetrofitService service = retrofit.create(MapaSolidarioRetrofitService.class);
        try {
            Response<User> userResponse = service.getUser(id).execute();
            _user = userResponse.body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return _user;
    }

    @Override
    public BasePoint getPuntoService(String id) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://morning-peak-11897.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MapaSolidarioRetrofitService service = retrofit.create(MapaSolidarioRetrofitService.class);
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
        User _u = new User();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://morning-peak-11897.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MapaSolidarioRetrofitService service = retrofit.create(MapaSolidarioRetrofitService.class);
        try {
            Response<User> userResponse = service.saveUser(json).execute();
            _u = userResponse.body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return _u;
    }

    @Override
    public BasePoint postPuntoService(BasePoint json) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://morning-peak-11897.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MapaSolidarioRetrofitService service = retrofit.create(MapaSolidarioRetrofitService.class);
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

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://morning-peak-11897.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MapaSolidarioRetrofitService service = retrofit.create(MapaSolidarioRetrofitService.class);
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

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://morning-peak-11897.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MapaSolidarioRetrofitService service = retrofit.create(MapaSolidarioRetrofitService.class);
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

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://morning-peak-11897.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MapaSolidarioRetrofitService service = retrofit.create(MapaSolidarioRetrofitService.class);
        try {
            Response<BasePoint> puntosResponse = service.deletePunto(id).execute();
            puntoDetalle = puntosResponse.body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return puntoDetalle;
    }

}
