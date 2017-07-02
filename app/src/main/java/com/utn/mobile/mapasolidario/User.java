package com.utn.mobile.mapasolidario;

import android.graphics.Bitmap;

import com.google.inject.Singleton;

import java.io.Serializable;

/**
 * Created by Juanca on 13/5/17.
 */
@SuppressWarnings("serial")
@Singleton
public class User implements Serializable {

    private String id;
    private String nombre;
    private String apellido;
    private String url_imagen;
    private String correo;
    private Bitmap imagen;
    private String puntuacion;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getUrl_imagen() {
        return url_imagen;
    }

    public void setUrl_imagen(String url_imagen) {
        this.url_imagen = url_imagen;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }

    public String getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(String puntuacion) {
        this.puntuacion = puntuacion;
    }
}
