package com.cdai.codebase.controller;

import com.cdai.codebase.Chart;
import com.cdai.codebase.Detail;
import com.cdai.codebase.DetailRow;
import com.cdai.codebase.Item;
import com.cdai.codebase.MoreDetail;
import com.cdai.codebase.MoreDetailRow;
import com.cdai.codebase.Report;
import com.cdai.codebase.Table;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for visualization
 */
@Controller
public class BenchmarkController {

    @RequestMapping("/report")
    public @ResponseBody Report getReport(@RequestParam(value="id", required=false, defaultValue = "loss") String id) {
        return Report.load("report-" + id + ".dat");
    }

    @RequestMapping("/table")
    public @ResponseBody Table getTable(@RequestParam(value="id", required=false, defaultValue = "loss") String id) {
        return Report.load("report-" + id + ".dat").flatten();
    }

    @RequestMapping("/detail")
    public @ResponseBody Table getDetails(@RequestParam(value="id") String id,
                                         @RequestParam(value="scenario") int scenarioIdx,
                                         @RequestParam(value="subject") int subjectIdx) {
        Report rpt = Report.load("report-" + id + ".dat");
        List<DetailRow> rows = new ArrayList<>();
        Chart chart = subjectIdx > 4 ? rpt.getObjSizeChart() : rpt.getObjNumChart(); // refer to details.html
        int i = 0;
        for (MoreDetail more : chart.getValues().get(scenarioIdx).get(subjectIdx % 4).getMores()) {
            rows.add(new DetailRow(i++, more.getEndTime() - more.getStartTime()));
        }
        return new Table<>(rows, rows.size());
    }

    @RequestMapping("/moredetail")
    public @ResponseBody Table getMoreDetails(@RequestParam(value="id") String id,
                                         @RequestParam(value="scenario") int scenarioIdx,
                                         @RequestParam(value="subject") int subjectIdx,
                                         @RequestParam(value="rowId") int rowId) {
        Report rpt = Report.load("report-" + id + ".dat");
        List<MoreDetailRow> rows = new ArrayList<>();
        Chart chart = subjectIdx > 4 ? rpt.getObjSizeChart() : rpt.getObjNumChart(); // refer to details.html

        List<Item> items = chart.getValues().get(scenarioIdx).get(subjectIdx % 4).getMores().get(rowId).getItems();
        long base = items.get(0).getStartTime();
        for (Item item : items) {
            rows.add(new MoreDetailRow(item.getResource(), item.getStartTime() - base, item.getEndTime() - base));
        }
        return new Table<>(rows, rows.size());
    }

    @RequestMapping("/test")
    public String benchmark(Model model) {
        return "benchmark";
    }

    @RequestMapping("/test2")
    public String showDetails(Model model) {
        return "details";
    }

    @RequestMapping("/test3")
    public String showMoreDetails(Model model) {
        return "moredetails";
    }
}
