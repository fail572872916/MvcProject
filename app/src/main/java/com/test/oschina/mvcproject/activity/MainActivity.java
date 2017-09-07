package com.test.oschina.mvcproject.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.test.oschina.mvcproject.R;
import com.test.oschina.mvcproject.fragment.BlankFragment;
public class MainActivity extends AppCompatActivity {
    BlankFragment fragment1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {



        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
         fragment1 =new  BlankFragment();
        transaction.replace(R.id.myContent, fragment1);
        transaction.commit();
    }







}
