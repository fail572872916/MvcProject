package com.example.jetpacktest.viewmodel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.jetpacktest.R;

public class MainActivity extends AppCompatActivity {

    MyViewModel myViewModel;
    TextView textView;
    Button button, button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        myViewModel = ViewModelProviders.of(this).get(MyViewModel.class);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myViewModel.value++;
                textView.setText(String.valueOf(myViewModel.value));
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myViewModel.value += 2;
                textView.setText(String.valueOf(myViewModel.value));
            }
        });
    }
}
