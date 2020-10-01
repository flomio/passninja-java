package com.passninja.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.passninja.exception.ApiException;
import com.passninja.exception.AuthenticationException;
import com.passninja.exception.InvalidRequestException;
import com.passninja.net.ApiResource;
import com.passninja.net.PassninjaResponse;
import com.passninja.net.RequestOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Pass extends ApiResource {

    public static final String RESOURCE = "passes";

    @JsonProperty private final String passType;
    @JsonProperty private final Map<String, Object> pass;
    @JsonProperty private final Map<String, Object> urls;

    @JsonCreator
    public Pass(
            @JsonProperty("passType") final String passType,
            @JsonProperty("pass") final Map<String, Object> pass,
            @JsonProperty("urls") final Map<String, Object> urls) {
        this.passType = passType;
        this.pass = pass;
        this.urls = urls;
    }

    public String getPassType() {
        return passType;
    }

    public Map<String, Object> getPass() {
        return pass;
    }

    public Map<String, Object> getUrls() {
        return urls;
    }

    @Override
    public String toString() {
        return "Pass{"
                + "passType='" + passType + '\''
                + ", pass='" + pass + '\''
                + ", urls='" + urls + '\''
                + '}';
    }

    public static final class RequestBuilder {
        private Map<String, Object> params = new HashMap<String, Object>();
        private ObjectMapper objectMapper = new ObjectMapper();

        public RequestBuilder() {
        }

        public RequestBuilder setPassType(String passType) {
            params.put("passType", passType);
            return this;
        }

        public RequestBuilder setPass(Map<String, Object> pass) {
            params.put("pass", pass);
            return this;
        }


        public PassninjaResponse<Pass> create() throws ApiException, IOException, AuthenticationException,
            InvalidRequestException {
            return create(null);
        }

        public PassninjaResponse<Pass> create(RequestOptions options) throws ApiException, IOException,
            AuthenticationException, InvalidRequestException {
            return request(RequestMethod.POST, RESOURCE, params, Pass.class, options);
        }
    }

    public static PassninjaResponse<Pass> create(String passType, Map<String, Object> pass) throws ApiException,
        IOException, AuthenticationException, InvalidRequestException {
        return new RequestBuilder().setPassType(passType).setPass(pass).create();
    }

    public static PassninjaResponse<PassCollection> get() throws ApiException, IOException, AuthenticationException,
        InvalidRequestException {
        return get(null, null);
    }

    public static PassninjaResponse<PassCollection> get(Map<String, Object> params) throws ApiException, IOException,
        AuthenticationException, InvalidRequestException {
        return get(params, null);
    }

    public static PassninjaResponse<PassCollection> get(RequestOptions options) throws ApiException, IOException,
        AuthenticationException, InvalidRequestException {
        return get(null, options);
    }

    public static PassninjaResponse<PassCollection> get(Map<String, Object> params, RequestOptions options)
        throws ApiException, IOException, AuthenticationException, InvalidRequestException {
        return request(RequestMethod.GET, RESOURCE, params, PassCollection.class, options);
    }

    public static PassninjaResponse<Pass> delete(String id) throws ApiException, IOException,
        AuthenticationException, InvalidRequestException {
        return delete(id, null);
    }

    public static PassninjaResponse<Pass> delete(String id, RequestOptions options) throws ApiException,
        IOException, AuthenticationException, InvalidRequestException {
        return request(RequestMethod.DELETE, String.format("%s/%s", RESOURCE, id), null,
            Pass.class, options);
    }

}
