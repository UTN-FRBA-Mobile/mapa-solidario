package com.utn.mobile.mapasolidario;

import com.utn.mobile.mapasolidario.dto.NovedadResponse;
import com.utn.mobile.mapasolidario.dto.PuntoResponse;
import com.utn.mobile.mapasolidario.dto.PuntoUpdate;

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

    @Headers({"Accept: application/json"})
    @POST("points")
    Call<BasePoint> postPunto(@Body BasePoint json);

    @GET("points/{id}")
    Call<BasePoint> getPunto(@Path("id") String id);

    @Headers({"Accept: application/json"})
    @PUT("points/actualizarPunto/{id}")
    Call<BasePoint> putPunto(@Path("id") String id, @Body PuntoUpdate json);

    @Headers({"Content-Type: application/json"})
    @PUT("points/incrementarContador/{id}")
    Call<BasePoint> putAyuda(@Path("id") String id);

}
