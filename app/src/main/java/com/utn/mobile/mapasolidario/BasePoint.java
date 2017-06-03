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
    LatLng ubicacion = new LatLng(-34.6183, -58.3732);
    String titulo = "";
    String descripcion = "";
    String tipo = "";
    String usuario = "";
    Date fechaCreacion = new Date();
    Date fechaVto = new Date();
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
    public void setFechaModificacion (Date fecha){ fechaModificacion = fecha;}
    public void setTipo (String newtipo){   tipo = newtipo;}
    public void setTitulo (String newtitulo) {titulo = newtitulo;}
    public void setDescripcion (String newdescripcion ){descripcion = newdescripcion;}
    public void setUsuario (String user){usuario = user;}
    public void setFechaCreacion (Date fecha){ fechaCreacion = fecha;}
    public void setFechaVto(Date fecha){ fechaVto = fecha;}

}
