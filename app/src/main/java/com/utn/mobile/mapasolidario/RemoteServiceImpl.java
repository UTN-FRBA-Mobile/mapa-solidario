package com.utn.mobile.mapasolidario;

import com.google.gson.Gson;
import com.utn.mobile.mapasolidario.dto.NovedadResponse;

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

    @Override
    public List<NovedadResponse> fetchNewsService() {
        List<NovedadResponse> fetchNovedadResponseList = new ArrayList<NovedadResponse>();
        MapaSolidarioRetrofitService retrofitService = null;
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
}
