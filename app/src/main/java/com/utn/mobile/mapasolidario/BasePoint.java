package com.utn.mobile.mapasolidario;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;
import com.utn.mobile.mapasolidario.util.PointActions;

import java.io.Serializable;
import java.util.Date;


/**
 * Created by dany on 25/05/2017.
 */

public class BasePoint
        implements Serializable {
//    LatLng ubicacion = new LatLng(-34.603748, -58.381533);
    String _id = "593f2678a608c10004c6b456";
    double latitud = -34.603748;
    double longitud = -58.381533;
    String titulo = "";
    String descripcion = "";
    String tipo = "";
    int id_usuario = 0;
    String usuario = "";
    Date fechaCreacion = new Date();
    String fechaVto = "01/01/2001";
    Date fechaModificacion =  new Date();
    PointActions accion = PointActions.CONSULTA;
    int contador = 0;

    public BasePoint (){
    }

    public void setLatitud (Double lat){latitud = lat;}
    public void setLongitud (Double lat){longitud = lat;}
    public void setId (String iden){ _id = iden; }
    public void setContador (int puntos2) {contador = puntos2;}
    public void setAccion (PointActions valor){ accion = valor; }
    public void setFechaModificacion (Date fecha){ fechaModificacion = fecha;}
    public void setTipo (String newtipo){   tipo = newtipo;}
    public void setTitulo (String newtitulo) {titulo = newtitulo;}
    public void setDescripcion (String newdescripcion ){descripcion = newdescripcion;}
    public void setUsuario (String user){usuario = user;}
    public void setId_usuario (int iden){ id_usuario = iden; }
    public void setFechaCreacion (Date fecha){ fechaCreacion = fecha;}
    public void setFechaVto(String fecha){ fechaVto = fecha;}

}
