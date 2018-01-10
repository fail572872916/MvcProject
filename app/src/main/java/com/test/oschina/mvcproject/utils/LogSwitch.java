package com.test.oschina.mvcproject.utils;


import android.util.Log;

import com.test.oschina.mvcproject.MyApplication;

public class LogSwitch {
    private static int log = 6;
    private static final int v = 1;
    private static final int d = 2;
    private static final int i = 3;
    private static final int w = 4;
    private static final int e = 5;
    private static final String author = "Mellow Speak-->";
    private static final String sign = "()\n-->>";

    // V级日志
    public static void v(String className, String methodName) {
        if (MyApplication.isDevelopingVersion && log > v) {
            Log.v(className, author + methodName);
        }
    }

    // V级日志
    public static void v(String className, String methodName, String describe) {
        if (MyApplication.isDevelopingVersion && log > v) {
            Log.v(className, author + methodName + sign + describe);
        }
    }

    // D级日志
    public static void d(String className, String methodName, String describe) {
        if (MyApplication.isDevelopingVersion && log > d) {
            Log.d(className, author + methodName + sign + describe);
        }
    }

    // I级日志
    public static void i(String className, String methodName, String describe) {
        if (MyApplication.isDevelopingVersion && log > i) {
            Log.i(className, author + methodName + sign + describe);
        }
    }

    // W级日志
    public static void w(String className, String methodName, String describe) {
        if (MyApplication.isDevelopingVersion && log > w) {
            Log.w(className, author + methodName + sign + describe);
        }
    }

    // E级日志
    public static void e(String className, String methodName, String describe) {
        if (MyApplication.isDevelopingVersion && log > e) {
            Log.e(className, author + methodName + sign + describe);
        }
    }

    // E级日志,带异常
    public static void e(String className, String methodName, String describe, Throwable err) {
        if (MyApplication.isDevelopingVersion && log > e) {
            Log.e(className, author + methodName + sign + describe + "↓");
            Log.e(methodName + "():", "", err);
        }
    }
}
