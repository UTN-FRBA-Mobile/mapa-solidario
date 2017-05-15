package com.utn.mobile.mapasolidario;

import com.utn.mobile.mapasolidario.dto.NovedadResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by svillarreal on 13/05/17.
 */

public interface MapaSolidarioRetrofitService {

    @GET("news")
    Call<List<NovedadResponse>> fetchNews();
}
