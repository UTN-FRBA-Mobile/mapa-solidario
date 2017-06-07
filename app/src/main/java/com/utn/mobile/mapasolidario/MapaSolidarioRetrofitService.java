package com.utn.mobile.mapasolidario;

import com.utn.mobile.mapasolidario.dto.NovedadResponse;
import com.utn.mobile.mapasolidario.dto.PuntoResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by svillarreal on 13/05/17.
 */

public interface MapaSolidarioRetrofitService {

    @GET("news")
    Call<List<NovedadResponse>> fetchNews();

    @GET("points")
    Call<List<PuntoResponse>> fetchPuntos();

    @POST("points")
    Call<BasePoint> postPunto(@Body String json);

    @GET("points/{id}")
        //593dbf11a05a0f0004b0d9ab
    Call<BasePoint> getPunto(@Path("id") String id);

    @PUT("points/{id}")
    Call<BasePoint> putPunto(@Path("id") String id, @Body String json);
/*

    @POST("points")
    void postPunto(@Body String json,Callback callback);

    @GET("points/{id}")
        //593dbf11a05a0f0004b0d9ab
    void getPunto(@Path("id") String id, Callback callback);

    @PUT("points/{id}")
    void putPunto(@Path("id") String id, @Body String json,Callback callback);
*/
}
