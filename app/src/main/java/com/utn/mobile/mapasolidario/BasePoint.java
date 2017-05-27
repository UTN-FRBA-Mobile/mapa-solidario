package com.utn.mobile.mapasolidario;

import com.google.android.gms.maps.model.LatLng;
import com.utn.mobile.mapasolidario.util.PointActions;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dany on 25/05/2017.
 */

public class BasePoint implements Serializable {
    LatLng ubicacion = new LatLng(-34.6183, -58.3732);
    String titulo = "";
    String descripcion = "";
    String tipo = "";
    String usuario = "";
    Date fechaCreacion = new Date();
    Date fechaModificacion =  new Date();
    int id = 0;
    PointActions accion = PointActions.CONSULTA;

    public BasePoint (){
    }

    public void setUbicacion (LatLng newubicacion){
        ubicacion = newubicacion;
    }
    public void setId (int iden){ id = iden; }
    public void setAccion (PointActions valor){ accion = valor; }
    public void setFechaModificacion (Date fecha){ fechaCreacion = fecha;}

    public void setTipo (String newtipo){        tipo = newtipo;}
    public void savePoint (String newtitulo, String newdescripcion, String newtipo, String user){
        titulo = newtitulo;
        descripcion = newdescripcion;
        tipo = newtipo;
        usuario = user;
    }

/*    public BasePoint getPoint(int newid) {
        BasePoint datosPunto = new BasePoint();
        datosPunto.ubicacion = obtenerUbicacion(newid);
        datosPunto.titulo = obtenerTitulo(newid);
        datosPunto.descripcion = obtenerDatosPunto(newid);

        return datosPunto;
    }
*/
}
