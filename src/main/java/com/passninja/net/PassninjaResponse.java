package com.passninja.net;

import java.util.List;
import java.util.Map;

public class PassninjaResponse<T> {

    int responseCode;
    T responseBody;
    Map<String, List<String>> responseHeaders;

    public PassninjaResponse(int responseCode, T responseBody) {
        this.responseCode = responseCode;
        this.responseBody = responseBody;
        this.responseHeaders = null;
    }

    public PassninjaResponse(int responseCode, T responseBody, Map<String, List<String>> responseHeaders) {
        this.responseCode = responseCode;
        this.responseBody = responseBody;
        this.responseHeaders = responseHeaders;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public T getResponseBody() {
        return responseBody;
    }

    public Map<String, List<String>> getResponseHeaders() {
        return responseHeaders;
    }
}
