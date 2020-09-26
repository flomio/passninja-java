package com.passninja.exception;

public abstract class PassninjaException extends Exception {

    private static final long serialVersionUID = 1L;
    
    private Integer statusCode;

    public PassninjaException(String message, Integer statusCode) {
        super(message, null);
        this.statusCode = statusCode;
    }

    public PassninjaException(String message, Integer statusCode, Throwable e) {
        super(message, e);
        this.statusCode = statusCode;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

}
