package com.cdai.codebase;

import java.util.List;

/**
 */
public class Table<Row> {

    private int current;

    private List<Row> rows;

    private int rowCount;

    private int total;

    public Table(List<Row> rows, int total) {
        this.current = 1;
        this.rows = rows;
        this.total = this.rowCount = total;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public List<Row> getRows() {
        return rows;
    }

    public void setRows(List<Row> rows) {
        this.rows = rows;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
