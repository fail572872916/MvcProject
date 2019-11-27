package com.example.jetpacktest.saveInstanceState;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

/**
 * 练习SaveInstance 保存数据，避免被后台因内存不足而杀死，让数据保存的更久
 */
public class InstanceViewModel extends ViewModel {
    SavedStateHandle savedStateHandle;
    public  InstanceViewModel(SavedStateHandle handle){
        this.savedStateHandle=handle;

    }
    public MutableLiveData<Integer> getMutableLiveData() {
     if(!savedStateHandle.contains(Main5Activity.KEY_NUMBER)){
         savedStateHandle.set(Main5Activity.KEY_NUMBER,0);
     }

        return savedStateHandle.getLiveData(Main5Activity.KEY_NUMBER);
    }

    public void add() {
        savedStateHandle.set(Main5Activity.KEY_NUMBER,getMutableLiveData().getValue() + 1);

    }
}
