package com.cdai.codebase.client;

import com.cdai.codebase.util.Utils;
import com.cdai.codebase.request.Listener;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.http.HttpMethod;
import org.eclipse.jetty.http.HttpVersion;
import org.eclipse.jetty.http2.client.HTTP2Client;
import org.eclipse.jetty.http2.client.http.HttpClientTransportOverHTTP2;
import org.eclipse.jetty.util.ssl.SslContextFactory;

/**
 * Jetty implementation
 */
public class JettyClient implements Client {

    private HttpClient client;

    public JettyClient() {
        // Load unsigned certificate
        String path = getClass().getResource("/key.jks").getPath().replaceAll("%20", " ");
        Utils.info("Path: %s", path);
        SslContextFactory sslCtxFactory = new SslContextFactory(path);
        sslCtxFactory.setKeyStorePassword("secret");

        // Create Jetty client
        client = new HttpClient(
                new HttpClientTransportOverHTTP2(new HTTP2Client()), sslCtxFactory);
        Utils.info("Start Jetty client complete");
    }

    @Override
    public void start() {
        try {
            client.start();
            Utils.debug("Start client complete");
        } catch (Exception e) {
            Utils.error("Error when starting client: %s", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public String get(String uri) {
        try {
            return client.newRequest(uri).
                    method(HttpMethod.GET).
                    version(HttpVersion.HTTP_2).
                    send().getContentAsString();
        } catch (Exception e) {
            Utils.error("Error when sending request to server: %s", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void get(String uri, Listener listener) {
        client.newRequest(uri).
                method(HttpMethod.GET).
                version(HttpVersion.HTTP_2).
                send(ret -> listener.onComplete(ret.toString()));
    }

    @Override
    public void stop() {
        try {
            client.stop();
        } catch (Exception e) {
            Utils.error("Error when stopping client: %s", e);
            throw new RuntimeException(e);
        }
    }
}
