package com.cdai.codebase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 */
public class Chart implements Serializable {

    private String xAxisLabel;
    private String yAxisLabel;
    private List<String> keys = new ArrayList<>();

    private List<List<Detail>> values = new ArrayList<>();
    private List<String> legends = new ArrayList<>();

    public List<String> getKeys() {
        return keys;
    }

    public void setKeys(List<String> keys) {
        this.keys = keys;
    }

    public List<List<Detail>> getValues() {
        return values;
    }

    public void addNewGroup(String legend, List<Detail> newVals) {
        legends.add(legend);
        values.add(newVals);
    }

    public String getxAxisLabel() {
        return xAxisLabel;
    }

    public void setxAxisLabel(String xAxisLabel) {
        this.xAxisLabel = xAxisLabel;
    }

    public String getyAxisLabel() {
        return yAxisLabel;
    }

    public void setyAxisLabel(String yAxisLabel) {
        this.yAxisLabel = yAxisLabel;
    }

    public List<String> getLegends() {
        return legends;
    }

    @Override
    public String toString() {
        return "Chart{" +
                "xAxisLabel='" + xAxisLabel + '\'' +
                ", yAxisLabel='" + yAxisLabel + '\'' +
                ", keys=" + keys +
                ", values=" + values +
                ", legends=" + legends +
                '}';
    }
}
