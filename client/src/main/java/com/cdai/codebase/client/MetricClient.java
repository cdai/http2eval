package com.cdai.codebase.client;

import com.cdai.codebase.Item;
import com.cdai.codebase.request.Listener;

import java.util.ArrayList;
import java.util.List;

/**
 * Client wrapper that measures performance of delegated client
 */
public class MetricClient implements Client {

    private List<Item> items = new ArrayList<>();

    private Client client;

    public MetricClient(Client client) {
        this.client = client;
    }

    @Override
    public void start() {
        client.start();
    }

    @Override
    public String get(String uri) {
        long startTime = System.currentTimeMillis();
        String ret = client.get(uri);
        items.add(new Item(uri, startTime, System.currentTimeMillis()));
        return ret;
    }

    @Override
    public void get(String uri, Listener listener) {
        long startTime = System.currentTimeMillis();
        client.get(uri, content -> {
            items.add(new Item(uri, startTime, System.currentTimeMillis()));
            listener.onComplete(content);
        });
    }

    @Override
    public void stop() {
        client.stop();
    }

    public List<Item> getItems() {
        List<Item> clone = new ArrayList<>(items);
        items.clear();
        return clone;
    }
}
