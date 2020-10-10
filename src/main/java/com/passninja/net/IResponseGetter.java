package com.passninja.net;

import com.passninja.exception.ApiException;
import com.passninja.exception.AuthenticationException;

import java.io.IOException;
import java.util.Map;

public interface IResponseGetter {
    <T> PassninjaResponse<T> request(
            ApiResource.RequestMethod method,
            String url,
            Map<String, Object> data,
            Class<T> clazz,
            RequestOptions options) throws AuthenticationException, ApiException, IOException;

    <T> PassninjaResponse<T> request(
            ApiResource.RequestMethod method,
            String url,
            Map<String, Object> data,
            Map<String, Object> query,
            Class<T> clazz,
            RequestOptions options) throws AuthenticationException, ApiException, IOException;
}
