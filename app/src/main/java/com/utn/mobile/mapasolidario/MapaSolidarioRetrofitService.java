package com.utn.mobile.mapasolidario;

import com.utn.mobile.mapasolidario.dto.NovedadResponse;
import com.utn.mobile.mapasolidario.dto.PuntoResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
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

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("points")
    Call<BasePoint> postPunto(@Body String json);

    @GET("points/{id}")
    Call<BasePoint> getPunto(@Path("id") String id);

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @PUT("points/{id}")
    Call<BasePoint> putPunto(@Path("id") String id, @Body String json);

}
