package com.cdai.codebase.util;

/**
 */
public class Utils {

    public static boolean isVerbose = false;

    public static void error(String str, Throwable t) {
        System.err.println(String.format(str, t.getMessage()));
    }

    public static void debug(String str, Object...params) {
        if (isVerbose) {
            System.out.println(String.format(str, params));
        }
    }

    public static void info(String str, Object...params) {
        System.out.println(String.format(str, params));
    }

    public static double round2decimal(double d) {
        return (double) Math.round(d * 100) / 100;
    }
}
