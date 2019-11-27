package com.example.jetpacktest.saveInstanceState;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import com.example.jetpacktest.R;
import com.example.jetpacktest.databinding.ActivityMain5Binding;

public class Main5Activity extends AppCompatActivity {


    ActivityMain5Binding binding;
    InstanceViewModel instanceViewModel;
    public  static  String   KEY_NUMBER="key_number";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main5);
        instanceViewModel = ViewModelProviders.of(this, new SavedStateViewModelFactory(this)).get(InstanceViewModel.class);
        binding.setData(instanceViewModel);
        binding.setLifecycleOwner(this);
    }


}
