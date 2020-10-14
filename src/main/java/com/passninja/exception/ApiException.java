package com.passninja.exception;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class ApiException extends PassninjaException {

    private static final long serialVersionUID = 1L;

    @JsonCreator
    public ApiException(@JsonProperty("error") final Map<String, Object> error) {
        super(getMessage(error), getStatusCode(error));
    }

    private static String getMessage(Map<String, Object> error) {
        return Objects.isNull(error) ? "Error" : (String) error.get("message");
    }

    private static Integer getStatusCode(Map<String, Object> error) {
        return Objects.isNull(error) ? null : (Integer) error.get("status_code");
    }
}
