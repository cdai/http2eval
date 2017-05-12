package com.cdai.codebase;

import com.cdai.codebase.client.Client;
import com.cdai.codebase.client.JettyClient;
import com.cdai.codebase.client.MetricClient;
import com.cdai.codebase.util.NoLogging;
import com.cdai.codebase.util.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 */
public class Tester {

    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            Utils.info("No valid arguments passed");
            return;
        }
        org.eclipse.jetty.util.log.Log.setLog(new NoLogging());

        String fileName = args[0];
        String testcase = args[1];
        Tester tester = new Tester(testcase);

        // Warm up both client and server
        Utils.info("=== %s ===", testcase);
        Utils.info("=== Warming Up ===");
        Utils.isVerbose = true;
        tester.test(1, new Report());
        Utils.isVerbose = false;
        Utils.info("");

        Utils.info("=== Start Testing ===");
        Report report = Report.load(fileName);
        Utils.info("Report reloaded: %s", report);
        tester.test(10, report);
        report.sync(fileName);
        Utils.info("Sync report data to disk file: %s", report);
    }

    private final String testcase;

    private final Client client;

    private final Browser browser;

    public Tester(String testcase) {
        try {
            this.testcase = testcase;
            this.client = new MetricClient(new JettyClient());
            this.browser = new Browser(client);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void test(int times, Report report) throws IOException {
        String server = "https://localhost:8443/";

        if (report.getObjNumChart() == null) {
            Utils.info("Not found existing report data, create a new one");
            report.setObjNumChart(new Chart());
            report.getObjNumChart().setKeys(Arrays.asList(" 4", " 8", "16", "32"));
            report.getObjNumChart().setxAxisLabel("Object number");
            report.getObjNumChart().setyAxisLabel("PLT (seconds)");

            report.setObjSizeChart(new Chart());
            report.getObjSizeChart().setKeys(Arrays.asList(" 300KB", " 600KB", "1.2MB"));
            report.getObjSizeChart().setxAxisLabel("Object size");
            report.getObjSizeChart().setyAxisLabel("PLT (seconds)");
        }

        report.getObjNumChart().addNewGroup(testcase, Arrays.asList(
                doTest(times, server, "small"),
                doTest(times, server, "small8"),
                doTest(times, server, "small16"),
                doTest(times, server, "small32")
        ));
        report.getObjSizeChart().addNewGroup(testcase, Arrays.asList(
                doTest(times, server, "big"),
                doTest(times, server, "big2"),
                doTest(times, server, "big3")
        ));
    }

    public Detail doTest(int times, String server, String resource) {
        List<MoreDetail> mores = new ArrayList<>();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            long start = System.currentTimeMillis();
            browser.loadResource(server, resource);
            mores.add(new MoreDetail(start, System.currentTimeMillis(), ((MetricClient) client).getItems()));
        }

        double pltAvg = Utils.round2decimal((System.currentTimeMillis() - startTime) / 1000.0 / times);
        Utils.info("Average time of page load for workload [%s] = %f (s)\n", resource, pltAvg);
        return new Detail(pltAvg, mores);
    }

}
