package com.example.jetpacktest.testScore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import com.example.jetpacktest.R;
import com.example.jetpacktest.databinding.ActivityMain4Binding;

public class Main4Activity extends AppCompatActivity {

    ScoreViewModel scoreViewModel;
    ActivityMain4Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main4);
        scoreViewModel = ViewModelProviders.of(this).get(ScoreViewModel.class);
        binding.setData(scoreViewModel);
        binding.setLifecycleOwner(this);
    }
}
