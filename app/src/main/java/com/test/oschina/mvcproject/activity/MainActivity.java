package com.test.oschina.mvcproject.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
