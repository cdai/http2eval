package com.cdai.codebase;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 */
public class MoreDetail implements Serializable {

    private long startTime;

    private long endTime;

    private List<Item> items = Collections.emptyList();

    public MoreDetail(long startTime, long endTime, List<Item> items) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.items = items;
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

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
