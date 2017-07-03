package com.utn.mobile.mapasolidario.dto;

import com.utn.mobile.mapasolidario.BasePoint;
import com.utn.mobile.mapasolidario.util.PointActions;

import java.io.Serializable;
import java.util.Date;


/**
 * Created by dany on 25/05/2017.
 */

public class PuntoUpdate
        implements Serializable {
    double latitud = -34.603748;
    double longitud = -58.381533;
    String titulo = "";
    String descripcion = "";
    String tipo = "";
    String id_usuario = "";
    String usuario = "";
    String fechaCreacion = "";
    String fechaVto = "01/01/2001";
    String fechaModificacion =  "";
    PointActions accion = PointActions.CONSULTA;
    int contador = 0;

    public PuntoUpdate(BasePoint punto){

        setLatitud(punto.getLatitud());
        setLongitud(punto.getLongitud());
        setContador(punto.getContador());
        setAccion(punto.getAccion());
        setFechaModificacion(punto.getFechaModificacion());
        setTipo(punto.getTipo());
        setDescripcion(punto.getDescripcion());
        setTitulo(punto.getTitulo());
        setUsuario(punto.getUsuario());
        setId_usuario(punto.getId_usuario());
        setFechaCreacion(punto.getFechaCreacion());
        setFechaVto(punto.getFechaVto());

    }

    public void setLatitud (Double lat){latitud = lat;}
    public void setLongitud (Double lat){longitud = lat;}
    public void setContador (int puntos2) {contador = puntos2;}
    public void setAccion (PointActions valor){ accion = valor; }
    public void setFechaModificacion (String fecha){ fechaModificacion = fecha;}
    public void setTipo (String newtipo){   tipo = newtipo;}
    public void setTitulo (String newtitulo) {titulo = newtitulo;}
    public void setDescripcion (String newdescripcion ){descripcion = newdescripcion;}
    public void setUsuario (String user){usuario = user;}
    public void setId_usuario (String iden){ id_usuario = iden; }
    public void setFechaCreacion (String fecha){ fechaCreacion = fecha;}
    public void setFechaVto(String fecha){ fechaVto = fecha;}

    public Double getLatitud (){ return this.latitud;}
    public Double getLongitud ( ){ return this.longitud;}
    public int getContador () {return this.contador;}
    public PointActions getAccion ( ){ return this.accion; }
    public String getFechaModificacion (){ return this.fechaModificacion;}
    public String getTipo (){ return this.tipo;}
    public String getTitulo () {return this.titulo;}
    public String getDescripcion ( ){ return this.descripcion;}
    public String getUsuario (){ return this.usuario;}
    public String getId_usuario (){ return this.id_usuario; }
    public String getFechaCreacion (){ return this.fechaCreacion;}
    public String getFechaVto(){ return this.fechaVto;}


}
