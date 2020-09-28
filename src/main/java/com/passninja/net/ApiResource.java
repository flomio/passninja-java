package com.passninja.net;

import com.passninja.exception.ApiException;
import com.passninja.exception.AuthenticationException;
import com.passninja.exception.InvalidRequestException;

import java.io.IOException;
import java.util.Map;


public abstract class ApiResource {

    private static ResponseGetter responseGetter = new ResponseGetter();

    public static final String CHARSET = "UTF-8";

    public enum RequestMethod {
        GET, POST, DELETE
    }

    public static <T> PassninjaResponse<T> request(ApiResource.RequestMethod method,
          String url, Map<String, Object> params, Class<T> clazz,
          RequestOptions options) throws AuthenticationException, ApiException,
          InvalidRequestException, IOException {
        return responseGetter.request(method, url, params, clazz, options);
    }

    public static <T> PassninjaResponse<T> request(ApiResource.RequestMethod method,
          String url, Map<String, Object> params, Map<String, Object> query,
          Class<T> clazz,
          RequestOptions options) throws AuthenticationException, ApiException, InvalidRequestException, IOException {
        return responseGetter.request(method, url, params, query, clazz, options);
    }

}
