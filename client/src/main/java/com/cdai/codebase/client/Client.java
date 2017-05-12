package com.cdai.codebase.client;

import com.cdai.codebase.request.Listener;

/**
 * HTTP/2 client interface
 */
public interface Client {

    void start();

    String get(String uri);

    void get(String uri, Listener listener);

    void stop();
}
