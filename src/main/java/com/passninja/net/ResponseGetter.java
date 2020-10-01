package com.passninja.net;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.passninja.Passninja;
import com.passninja.exception.ApiException;
import com.passninja.exception.AuthenticationException;
import com.passninja.exception.InvalidRequestException;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.passninja.net.ApiResource.CHARSET;
import static com.passninja.net.ApiResource.RequestMethod.DELETE;
import static com.passninja.net.ApiResource.RequestMethod.POST;
import static com.passninja.net.ApiResource.RequestMethod.PUT;

public class ResponseGetter implements IResponseGetter {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_INSTANT.withZone(ZoneId.of("UTC"));
    private static final ObjectMapper MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private static final class Parameter {
        public final String key;
        public final Object value;

        public Parameter(String key, Object value) {
            this.key = key;
            this.value = value;
        }
    }

    @Override
    public <T> PassninjaResponse<T> request(
            ApiResource.RequestMethod method,
            String url,
            Map<String, Object> data,
            Map<String, Object> query,
            Class<T> clazz,
            RequestOptions options) throws AuthenticationException, ApiException, InvalidRequestException, IOException {
        return requestInternal(method, url, data, query, clazz, options);
    }

    @Override
    public <T> PassninjaResponse<T> request(
            ApiResource.RequestMethod method,
            String url,
            Map<String, Object> data,
            Class<T> clazz,
            RequestOptions options) throws AuthenticationException, ApiException, InvalidRequestException, IOException {

        return requestInternal(method, url, data, Collections.emptyMap(), clazz, options);
    }

    static Map<String, String> getHeaders(RequestOptions options) {
        Map<String, String> headers = new HashMap<String, String>();

        headers.put("x-api-key", options.getApiKey());
        headers.put("User-Agent", String.format("PassninjaJava/%s JDK/%s", Passninja.VERSION,
              System.getProperty("java.version")));

        if (options.getAccountId() != null) {
            headers.put("x-account-id", options.getAccountId());
        }

        return headers;
    }

    private static java.net.HttpURLConnection createDefaultConnection(String url, RequestOptions options)
        throws IOException {
        URL passninjaUrl = new URL(url);

        HttpURLConnection conn = (HttpURLConnection) (options.getProxy() == null ? passninjaUrl.openConnection()
              : passninjaUrl.openConnection(options.getProxy()));
        conn.setUseCaches(false);
        for (Map.Entry<String, String> header : getHeaders(options).entrySet()) {
            conn.setRequestProperty(header.getKey(), header.getValue());
        }

        return conn;
    }

    private static java.net.HttpURLConnection createGetConnection(String url, String query, RequestOptions options)
          throws IOException {
        String getUrl = query.isEmpty() ? url : String.format("%s?%s", url, query);
        java.net.HttpURLConnection conn = createDefaultConnection(getUrl, options);

        conn.setRequestMethod("GET");

        return conn;
    }

    private static java.net.HttpURLConnection createPostConnection(String url, String data,
          RequestOptions options) throws IOException {
        String getUrl = url;
        java.net.HttpURLConnection conn = createDefaultConnection(getUrl, options);

        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", String.format("application/json;charset=%s",
              ApiResource.CHARSET));

        try (OutputStream output = conn.getOutputStream()) {
            output.write(data.getBytes(ApiResource.CHARSET));
        }
        return conn;
    }

    private static java.net.HttpURLConnection createPutConnection(String url, String data,
          RequestOptions options) throws IOException {
        String getUrl = url;
        java.net.HttpURLConnection conn = createDefaultConnection(getUrl, options);

        conn.setDoOutput(true);
        conn.setRequestMethod("PUT");
        conn.setRequestProperty("Content-Type", String.format("application/json;charset=%s",
              ApiResource.CHARSET));

        try (OutputStream output = conn.getOutputStream()) {
            output.write(data.getBytes(ApiResource.CHARSET));
        }
        return conn;
    }

    private static java.net.HttpURLConnection createDeleteConnection(String url, RequestOptions options)
          throws IOException {
        java.net.HttpURLConnection conn = createDefaultConnection(url, options);

        conn.setRequestMethod("DELETE");

        return conn;
    }

    private static String createQuery(Map<String, Object> params) throws JsonProcessingException {
        if (params == null) {
            return "";
        }

        return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(params);
    }

    private static String urlEncodePair(String key, String value) throws UnsupportedEncodingException {
        return String.format("%s=%s", URLEncoder.encode(key, CHARSET), URLEncoder.encode(value, CHARSET));
    }

    private static List<Parameter> flattenParams(Map<String, Object> params) {
        return flattenParamsMap(params, null);
    }

    private static List<Parameter> flattenParamsMap(Map<String, Object> params, String keyPrefix) {
        List<Parameter> flatParams = new LinkedList<Parameter>();
        if (params == null) {
            return flatParams;
        }

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            String newPrefix = key;
            if (keyPrefix != null) {
                newPrefix = String.format("%s[%s]", keyPrefix, key);
            }

            flatParams.addAll(flattenParamsValue(value, newPrefix));
        }

        return flatParams;
    }

    @SuppressWarnings("unchecked")
    private static List<Parameter> flattenParamsValue(Object value, String keyPrefix) {
        List<Parameter> flatParams;

        if (value instanceof Map<?, ?>) {
            flatParams = flattenParamsMap((Map<String, Object>) value, keyPrefix);
        } else if (value instanceof List) {
            flatParams = new LinkedList<Parameter>();
            for (Object item : (List<Object>) value) {
                flatParams.add(new Parameter(keyPrefix + "[]", item));
            }
        } else if (value instanceof File) {
            flatParams = new LinkedList<Parameter>();
            flatParams.add(new Parameter(keyPrefix, value));
        } else if (value instanceof ZonedDateTime) {
            flatParams = new LinkedList<Parameter>();
            flatParams.add(new Parameter(keyPrefix, DATE_FORMATTER.format(((ZonedDateTime) value))));
        } else {
            flatParams = new LinkedList<Parameter>();
            flatParams.add(new Parameter(keyPrefix, value.toString()));
        }

        return flatParams;
    }

    private static <T> PassninjaResponse<T> handleConnectionResponse(HttpURLConnection conn, Class<T> clazz)
          throws IOException, InvalidRequestException, ApiException {
        int responseCode = conn.getResponseCode();

        if (responseCode >= 200 && responseCode < 300) {
            Map<String, List<String>> headers = conn.getHeaderFields();
            T value = MAPPER.readValue(conn.getInputStream(), clazz);

            return new PassninjaResponse<T>(responseCode, value, headers);
        } else if (responseCode == 422) {
            throw MAPPER.readValue(conn.getErrorStream(), InvalidRequestException.class);
        } else {
            throw MAPPER.readValue(conn.getErrorStream(), ApiException.class);
        }
    }

    private static <T> PassninjaResponse<T> makeUrlConnectionRequest(ApiResource.RequestMethod method, Class<T> clazz,
          String url, String data, RequestOptions options) throws ApiException, InvalidRequestException,
          IOException {
        java.net.HttpURLConnection conn = null;
        try {
            if (method == POST) {
                conn = createPostConnection(url, data, options);
            } else if (method == PUT) {
                conn = createPutConnection(url, data, options);
            } else if (method == DELETE) {
                conn = createDeleteConnection(url, options);
            } else {
                conn = createGetConnection(url, data, options);
            }

            return handleConnectionResponse(conn, clazz);
        } catch (IOException e) {
            throw e;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    private static <T> PassninjaResponse<T> requestInternal(ApiResource.RequestMethod method, String url,
          Map<String, Object> data, Map<String, Object> query, Class<T> clazz, RequestOptions options)
          throws AuthenticationException, ApiException,
          InvalidRequestException, IOException {
        if (options == null) {
            options = RequestOptions.getDefault();
        }

        String apiKey = options.getApiKey();
        if (apiKey == null || apiKey.trim().isEmpty()) {
            throw new AuthenticationException("Missing API Key. Make sure 'Passninja.init(<ACCOUNT_ID>, <API_KEY>)'"
                + " is called with a key from your Dashboard.", null);
        }

        String passninjaUrl = String.format("%s%s", Passninja.API_BASE_URL, url);

        String encodedData = createQuery(data);
        return makeUrlConnectionRequest(method, clazz, passninjaUrl, encodedData, options);
    }

}
