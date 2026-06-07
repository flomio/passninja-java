package com.passninja;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

/**
 * Minimal in-process HTTP server used by the tests so the suite runs offline
 * (the JDK ships com.sun.net.httpserver, so no extra dependency is needed).
 * Routes the handful of endpoints the client exercises to canned JSON.
 */
public class MockApiServer {

    private static final String PASS_JSON =
        "{\"id\":\"serial123\",\"passTemplate\":\"ptk_0x2\",\"serialNumber\":\"serial123\","
        + "\"urls\":{\"landing\":\"https://i.installpass.es/p/serial123\"}}";
    private static final String PASSES_JSON = "{\"passes\":[" + PASS_JSON + "]}";
    private static final String DECRYPT_JSON = "{\"decrypted\":\"founder-id:abc123\"}";
    private static final String TEMPLATE_JSON =
        "{\"id\":\"ptk_0x2\",\"name\":\"Starbucks Rewards\",\"pass_type_id\":\"pass.com.example\","
        + "\"platform\":\"apple\",\"style\":\"storeCard\",\"issued_pass_count\":0,"
        + "\"installed_pass_count\":0}";

    private HttpServer server;

    /**
     * Starts the server on an ephemeral port.
     *
     * @return the base URL (with trailing slash) to assign to Passninja.API_BASE_URL
     * @throws IOException if the server cannot bind
     */
    public String start() throws IOException {
        server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        server.createContext("/", exchange -> {
            String path = exchange.getRequestURI().getPath();
            String method = exchange.getRequestMethod();
            int code = 200;
            String body;
            if (method.equals("POST") && path.equals("/v1/passes")) {
                body = PASS_JSON;
            } else if (method.equals("POST") && path.matches("/v1/passes/[^/]+/decrypt")) {
                body = DECRYPT_JSON;
            } else if (method.equals("GET") && path.matches("/v1/passes/[^/]+/[^/]+")) {
                body = PASS_JSON;
            } else if (method.equals("PUT") && path.matches("/v1/passes/[^/]+/[^/]+")) {
                body = PASS_JSON;
            } else if (method.equals("GET") && path.matches("/v1/passes/[^/]+")) {
                body = PASSES_JSON;
            } else if (method.equals("GET") && path.matches("/v1/pass_templates/[^/]+")) {
                body = TEMPLATE_JSON;
            } else {
                code = 404;
                body = "{\"error\":\"not found: " + method + " " + path + "\"}";
            }
            byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(code, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        });
        server.start();
        return "http://127.0.0.1:" + server.getAddress().getPort() + "/v1/";
    }

    public void stop() {
        if (server != null) {
            server.stop(0);
        }
    }
}
