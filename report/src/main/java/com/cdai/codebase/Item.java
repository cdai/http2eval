package com.cdai.codebase;

import java.io.Serializable;

/**
 */
public class Item implements Serializable {
    private String resource;
    private long startTime;
    private long endTime;

    public Item(String resource, long startTime, long endTime) {
        this.resource = resource;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }
}
