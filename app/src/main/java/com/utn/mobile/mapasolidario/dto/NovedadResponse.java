package com.utn.mobile.mapasolidario.dto;

import java.util.Date;

/**
 * Created by svillarreal on 08/05/17.
 */

public class NovedadResponse {

    private String user, title, address, description, id_point;
    private Date expires;

    public String getId_point() {
        return id_point;
    }

    public void setId_point(String punto) {
        this.id_point = punto;
    }


    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getExpires() {
        return expires;
    }

    public void setExpires(Date expires) {
        this.expires = expires;
    }
}
