package com.example.videoplayer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;

import androidx.annotation.Nullable;

import java.util.List;

/**
 * Created by Administrator on 2018/6/12.
 */

@SuppressLint("Registered")
public abstract class BaseActivity extends PemissionActivity  {

    public Context mContext;
    public String title = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            builder.detectFileUriExposure();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }


    @Override
    protected void onPause() {
        super.onPause();
    }







    public abstract void onPermissionsGranted(int requestCode, List<String> perms);

    public abstract void onPermissionsDenied(int requestCode, List<String> perms);
}
