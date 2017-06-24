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
    String _id = "";
    double latitud = -34.603748;
    double longitud = -58.381533;
    String titulo = "";
    String descripcion = "";
    String tipo = "";
    String id_usuario = "";
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
    public void setId_usuario (String iden){ id_usuario = iden; }
    public void setFechaCreacion (Date fecha){ fechaCreacion = fecha;}
    public void setFechaVto(String fecha){ fechaVto = fecha;}

    public Double getLatitud (){ return this.latitud;}
    public Double getLongitud ( ){ return this.longitud;}
    public String getId(){ return this._id; }
    public int getContador () {return this.contador;}
    public PointActions getAccion ( ){ return this.accion; }
    public Date getFechaModificacion (){ return this.fechaModificacion;}
    public String getTipo (){ return this.tipo;}
    public String getTitulo () {return this.titulo;}
    public String getDescripcion ( ){ return this.descripcion;}
    public String getUsuario (){ return this.usuario;}
    public String getId_usuario (){ return this.id_usuario; }
    public Date getFechaCreacion (){ return this.fechaCreacion;}
    public String getFechaVto(){ return this.fechaVto;}

}
