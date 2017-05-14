package com.utn.mobile.mapasolidario.model;

/**
 * Created by svillarreal on 08/05/17.
 */
public class AuthenticationResponse {

    private String token;

    private Integer id;

    private String sessionId;

    private String error;

    private String perfil;

    public AuthenticationResponse(String token, Integer id, String sessionid, String error, String perfil) {
        this.token = token;
        this.id = id;
        this.sessionId = sessionid;
        this.error = error;
        this.perfil = perfil;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

	/*public String getToken() {
        return token;
	}
	public Integer getId() {
		return id;
	}
	public String getSessionid() {
		return sessionid;
	}
	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}*/

}