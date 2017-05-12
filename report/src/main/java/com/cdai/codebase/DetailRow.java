package com.cdai.codebase;

import java.io.Serializable;

/**
 */
public class DetailRow implements Serializable {

    private int id;

    private long elapse;

    public DetailRow(int id, long elapse) {
        this.id = id;
        this.elapse = elapse;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getElapse() {
        return elapse;
    }

    public void setElapse(long elapse) {
        this.elapse = elapse;
    }
}
