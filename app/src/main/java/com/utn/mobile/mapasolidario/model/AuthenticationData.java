package com.utn.mobile.mapasolidario.model;


/**
 * Created by svillarreal on 08/05/17.
 */
public class AuthenticationData {

    private String username;

    private String password;

    public AuthenticationData(String password) {
        this.setPassword(password);
    }

    public AuthenticationData(String username, String password, Integer local) {
        this.setUsername(username);
        this.setPassword(password);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}