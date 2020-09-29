package com.passninja.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.passninja.exception.ApiException;
import com.passninja.exception.AuthenticationException;
import com.passninja.exception.InvalidRequestException;
import com.passninja.exception.ParsingException;
import com.passninja.net.ApiResource;
import com.passninja.net.PassninjaResponse;
import com.passninja.net.RequestOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pass extends ApiResource {

    public static final String RESOURCE = "passes";

    public static class CustomEnvelope {

        @JsonProperty private final String id;
        @JsonProperty private final String url;
        @JsonProperty private final String object;

        @JsonCreator
        public CustomEnvelope(
                @JsonProperty("id") final String id,
                @JsonProperty("url") final String url,
                @JsonProperty("object") final String object) {
            this.id = id;
            this.url = url;
            this.object = object;
        }

        public String getId() {
            return id;
        }

        public String getUrl() {
            return url;
        }

        public String getObject() {
            return object;
        }
    }

    @JsonProperty private final String id;
    @JsonProperty private final String description;
    @JsonProperty private final String object;

    @JsonCreator
    public Pass(
            @JsonProperty("id") final String id,
            @JsonProperty("description") final String description,
            @JsonProperty("object") final String object) {
        this.id = id;
        this.description = description;
        this.object = object;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getObject() {
        return object;
    }

    @Override
    public String toString() {
        return "Pass{"
                + "id='" + id + '\''
                + ", description='" + description + '\''
                + ", object='" + object + '\''
                + '}';
    }

    public static final class RequestBuilder {
        private Map<String, Object> params = new HashMap<String, Object>();
        private ObjectMapper objectMapper = new ObjectMapper();

        public RequestBuilder() {
        }

        public RequestBuilder setDescription(String description) {
            params.put("description", description);
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
