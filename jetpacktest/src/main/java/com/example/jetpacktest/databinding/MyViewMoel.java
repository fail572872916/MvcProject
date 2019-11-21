package com.example.jetpacktest.databinding;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyViewMoel extends ViewModel {
    public MutableLiveData<Integer> mutableLiveData;


    public MutableLiveData<Integer> getMutableLiveData() {
        if(mutableLiveData==null){
             mutableLiveData=new MutableLiveData<>();
             mutableLiveData.setValue(0);
        }
        return mutableLiveData;
    }

    public  void add(int p){
        mutableLiveData.setValue(mutableLiveData.getValue()+p);
    }
}
