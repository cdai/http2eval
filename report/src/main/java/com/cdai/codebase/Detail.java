package com.cdai.codebase;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 */
public class Detail implements Serializable {

    private double avg;

    private List<MoreDetail> mores = Collections.emptyList();

    public Detail(double avg, List<MoreDetail> mores) {
        this.avg = avg;
        this.mores = mores;
    }

    public double getAvg() {
        return avg;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }

    public List<MoreDetail> getMores() {
        return mores;
    }

    public void setMores(List<MoreDetail> mores) {
        this.mores = mores;
    }
}
