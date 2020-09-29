package com.passninja.exception;

public class ParsingException extends PassninjaException {

    private static final long serialVersionUID = 1L;

    public ParsingException(Exception e) {
        super(e.getMessage(), 0, e);
    }
}
