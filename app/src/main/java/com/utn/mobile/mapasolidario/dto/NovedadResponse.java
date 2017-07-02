package com.utn.mobile.mapasolidario.dto;

import java.util.Date;

/**
 * Created by svillarreal on 08/05/17.
 */

public class NovedadResponse {

    private String usuario, titulo, descripcion, _id;
    private String fechaVto;

    public String get_id() {
        return _id;
    }

    public void set_id(String punto) {
        this._id = punto;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFechaVto() {
        return fechaVto;
    }

    public void setFechaVto(String fechaVto) {
        this.fechaVto = fechaVto;
    }
}
