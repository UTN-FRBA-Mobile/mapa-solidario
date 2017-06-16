package com.utn.mobile.mapasolidario;

import com.utn.mobile.mapasolidario.dto.NovedadResponse;
import com.utn.mobile.mapasolidario.dto.PuntoResponse;

import java.util.List;

/**
 * Servicio que centraliza las comunicaciones con el server
 * Created by svillarreal on 08/05/17.
 */
public interface IRemoteService {

    List<NovedadResponse> fetchNewsService();

    List<PuntoResponse> fetchPuntosService();

    BasePoint postPuntoService(String json);
    BasePoint putPuntoService(String id, String json);
    BasePoint getPuntoService(String id);
    BasePoint putAyudaService(String id);


}
