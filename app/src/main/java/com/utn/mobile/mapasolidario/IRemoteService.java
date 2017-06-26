package com.utn.mobile.mapasolidario;

import com.utn.mobile.mapasolidario.dto.NovedadResponse;
import com.utn.mobile.mapasolidario.dto.PuntoResponse;
import com.utn.mobile.mapasolidario.dto.PuntoUpdate;
import com.utn.mobile.mapasolidario.dto.UserResponse;

import java.util.List;

/**
 * Servicio que centraliza las comunicaciones con el server
 * Created by svillarreal on 08/05/17.
 */
public interface IRemoteService {

    List<NovedadResponse> fetchNewsService();

    List<PuntoResponse> fetchPuntosService();

    BasePoint postPuntoService(BasePoint json);
    BasePoint putPuntoService(String id, PuntoUpdate json);
    BasePoint getPuntoService(String id);
    BasePoint putAyudaService(String id);
    UserResponse getUserService(String id);
    UserResponse postUserService(UserResponse userResponse);


}
