package com.cdai.codebase;

import java.io.Serializable;

/**
 */
public class MoreDetailRow implements Serializable {

    private String uri;

    private long startTime;

    private long endTime;

    public MoreDetailRow(String uri, long startTime, long endTime) {
        this.uri = uri;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
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
