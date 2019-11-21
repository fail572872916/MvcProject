package com.example.jetpacktest.databinding;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.example.jetpacktest.R;

public class Main3Activity extends AppCompatActivity {

    MyViewMoel myViewMoel;
    ActivityMain3Binding activityMain3Binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMain3Binding = DataBindingUtil.setContentView(this, R.layout.activity_main3);

        myViewMoel = ViewModelProviders.of(this).get(MyViewMoel.class);
//给dataBinding绑定数据
        activityMain3Binding.setData(myViewMoel);
        //必须写才能观察到数据的变化
        activityMain3Binding.setLifecycleOwner(this);


    }
}
