package com.cdai.codebase;

import java.io.Serializable;

/**
 */
public class AvgRow implements Serializable {

    private String scenario;
    private String subject;
    private double plt;

    public AvgRow(String scenario, String subject, double plt) {
        this.scenario = scenario;
        this.subject = subject;
        this.plt = plt;
    }

    public String getScenario() {
        return scenario;
    }

    public void setScenario(String scenario) {
        this.scenario = scenario;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public double getPlt() {
        return plt;
    }

    public void setPlt(double plt) {
        this.plt = plt;
    }
}
