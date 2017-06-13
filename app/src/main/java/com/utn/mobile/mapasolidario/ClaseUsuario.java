package com.utn.mobile.mapasolidario;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.net.URL;

/**
 * Created by Juanca on 13/5/17.
 */
@SuppressWarnings("serial")
class ClaseUsuario implements Serializable {

    private String id;
    private String nombre;
    private String apellido;
    private String url;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }

    private String mail;
    private Bitmap imagen;
}
