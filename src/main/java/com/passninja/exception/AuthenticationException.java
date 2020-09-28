package com.passninja.exception;

public class AuthenticationException extends PassninjaException {

    private static final long serialVersionUID = 1L;

    public AuthenticationException(String message, Integer statusCode) {
        super(message, statusCode);
    }

}
