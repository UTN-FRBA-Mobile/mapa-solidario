package com.utn.mobile.mapasolidario;

import com.utn.mobile.mapasolidario.dto.NovedadResponse;

import java.util.List;

/**
 * Servicio que centraliza las comunicaciones con el server
 * Created by svillarreal on 08/05/17.
 */
public interface IRemoteService {

    List<NovedadResponse> fetchNewsService();

}
