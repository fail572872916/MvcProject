package com.test.oschina.mvcproject;


import android.app.Application;
import android.content.Context;



public class MyApplication extends Application {
    // 常量
    private final String TAG;

    // 变量
    private Context context;
    public static boolean isDevelopingVersion;

    public MyApplication() {
        this.TAG = getClass().getSimpleName();
    }

    @Override
    public void onCreate() {
        this.context = getApplicationContext();
        super.onCreate();

    }



}
