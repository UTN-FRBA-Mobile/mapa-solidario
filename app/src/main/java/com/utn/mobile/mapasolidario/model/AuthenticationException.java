package com.utn.mobile.mapasolidario.model;


import com.utn.mobile.mapasolidario.util.ErrorCodes;

/**
 * Created by svillarreal on 08/05/17.
 */
public class AuthenticationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private ErrorCodes errorCode;

    public AuthenticationException(Throwable e, ErrorCodes errorCode) {
        super(e);
        this.errorCode = errorCode;
    }

    public ErrorCodes getErrorCode() {

        return errorCode;
    }

}
