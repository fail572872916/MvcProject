package com.example.jetpacktest.androidviewmodule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import com.example.jetpacktest.R;
import com.example.jetpacktest.databinding.ActivityMain6Binding;

public class Main6Activity extends AppCompatActivity {

    MyViewModel myViewModel;

    ActivityMain6Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main6);
        myViewModel = ViewModelProviders.of(this, new SavedStateViewModelFactory(this)).get(MyViewModel.class);
        binding.setData(myViewModel);
        binding.setLifecycleOwner(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        myViewModel.save();
    }
}
