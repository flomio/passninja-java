package com.passninja.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.passninja.exception.ApiException;
import com.passninja.exception.AuthenticationException;
import com.passninja.net.ApiResource;
import com.passninja.net.PassninjaResponse;
import com.passninja.net.RequestOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Pass extends ApiResource {

    public static final String RESOURCE = "passes";

    @JsonProperty private final String passType;
    @JsonProperty private final String serialNumber;
    @JsonProperty private final Map<String, Object> pass;
    @JsonProperty private final Map<String, Object> urls;

    @JsonCreator
    public Pass(
            @JsonProperty("passType") final String passType,
            @JsonProperty("serialNumber") final String serialNumber,
            @JsonProperty("pass") final Map<String, Object> pass,
            @JsonProperty("urls") final Map<String, Object> urls) {
        this.passType = passType;
        this.serialNumber = serialNumber;
        this.pass = pass;
        this.urls = urls;
    }

    public String getPassType() {
        return passType;
    }

    public String getSerialNumber() {
        return serialNumber;
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
                + ", serialNumber='" + serialNumber + '\''
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


        public PassninjaResponse<Pass> create() throws ApiException, IOException, AuthenticationException {
            return create(null);
        }

        public PassninjaResponse<Pass> create(RequestOptions options) throws ApiException, IOException,
            AuthenticationException {
            return request(RequestMethod.POST, RESOURCE, params, Pass.class, options);
        }
    }

    public static PassninjaResponse<Pass> create(String passType, Map<String, Object> pass) throws ApiException,
        IOException, AuthenticationException {
        return new RequestBuilder().setPassType(passType).setPass(pass).create();
    }

    public static PassninjaResponse<Pass> get(String passType, String serialNumber) throws ApiException, IOException,
        AuthenticationException {
        return request(RequestMethod.GET, RESOURCE + "/" + passType + "/" + serialNumber, null, Pass.class, null);
    }

    public static PassninjaResponse<Pass> find(String passType) throws ApiException, IOException,
        AuthenticationException {
        return request(RequestMethod.GET, RESOURCE + "/" + passType, null, Pass.class, null);
    }

    public static PassninjaResponse<PassPayload> decrypt(String passType, String apdu) throws ApiException, IOException,
        AuthenticationException {
        Map<String, String> params = new HashMap<>();
        params.put("payload", apdu);
        return request(RequestMethod.POST, RESOURCE + "/" + passType + "/decrypt", params, PassPayload.class, null);
    }

    public static PassninjaResponse<Pass> put(String passType, String serialNumber, Map<String, Object> pass)
          throws ApiException, IOException, AuthenticationException {
        Map<String, Object> params = new HashMap<>();
        params.put("pass", pass);
        return request(RequestMethod.PUT, RESOURCE + "/" + passType + "/" + serialNumber, params, Pass.class, null);
    }

    public static PassninjaResponse<Pass> delete(String passType, String serialNumber) throws ApiException, IOException,
          AuthenticationException {
        return request(RequestMethod.DELETE, RESOURCE + "/" + passType + "/" + serialNumber, null, Pass.class, null);
    }

    public static PassninjaResponse<Pass> deleteForce(String passType, String serialNumber) throws ApiException,
          IOException, AuthenticationException {
        Map<String, Object> query = new HashMap<>();
        query.put("force", "true");
        return request(RequestMethod.DELETE, RESOURCE + "/" + passType + "/" + serialNumber, null,
            query, Pass.class, null);
    }

}
