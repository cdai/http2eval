package com.cdai.codebase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 */
public class Report implements Serializable {

    private Chart objNumChart;

    private Chart objSizeChart;

    /**
     * Read into memory from external report file
     * @param fileName
     * @return
     */
    public static Report load(String fileName) {
        if (!new File(fileName).exists()) {
            return new Report();
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            return (Report) in.readObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sync to external file
     * @param fileName
     */
    public void sync(String fileName) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Table<AvgRow> flatten() {
        List<AvgRow> rows = new ArrayList<>();
        int numSceSize = objNumChart.getLegends().size();
        int numSubSize = objNumChart.getKeys().size();
        int szSceSize = objSizeChart.getLegends().size();
        int szSubSize = objSizeChart.getKeys().size();
        for (int i = 0; i < numSceSize; i++) {
            for (int j = 0; j < numSubSize; j++) {
                rows.add(new AvgRow(
                        objNumChart.getLegends().get(i),
                        objNumChart.getKeys().get(j),
                        objNumChart.getValues().get(i).get(j).getAvg()));
            }
        }
        for (int i = 0; i < szSceSize; i++) {
            for (int j = 0; j < szSubSize; j++) {
                rows.add(new AvgRow(
                        objSizeChart.getLegends().get(i),
                        objSizeChart.getKeys().get(j),
                        objSizeChart.getValues().get(i).get(j).getAvg()));
            }
        }
        int total = numSceSize * numSubSize + szSceSize * szSubSize;
        return new Table<AvgRow>(rows, total);
    }

    public Chart getObjNumChart() {
        return objNumChart;
    }

    public void setObjNumChart(Chart objNumChart) {
        this.objNumChart = objNumChart;
    }

    public Chart getObjSizeChart() {
        return objSizeChart;
    }

    public void setObjSizeChart(Chart objSizeChart) {
        this.objSizeChart = objSizeChart;
    }

    @Override
    public String toString() {
        return "Report{" +
                "objNumChart=" + objNumChart +
                ", objSizeChart=" + objSizeChart +
                '}';
    }
}
