package com.example.jetpacktest.livedata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.jetpacktest.R;

public class Main2Activity extends AppCompatActivity {

    ViewModelWithLiveData viewModelWithLiveData;
    ImageButton imageButton, imageButton2;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        imageButton = findViewById(R.id.imageButton);
        imageButton2 = findViewById(R.id.imageButton2);
        textView = findViewById(R.id.textView2);
        viewModelWithLiveData = ViewModelProviders.of(this).get(ViewModelWithLiveData.class);
        //当数据发生变化时用来通知ui   owner需要具有管理对象的功能，例如activity
        viewModelWithLiveData.getNumber().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                textView.setText(String.valueOf(integer));
            }
        });
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModelWithLiveData.add(1);
            }
        });
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModelWithLiveData.add(-1);
            }
        });
    }
}
