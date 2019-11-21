package com.example.jetpacktest.livedata;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


/**
 * LiveData用于感知数据发生变化并通知Ui
 */
public class ViewModelWithLiveData extends ViewModel {


    private MutableLiveData<Integer> number;

    public MutableLiveData<Integer> getNumber() {

        if (number == null) {
            number = new MutableLiveData<>();
            number.setValue(0);
        }
        return number;
    }

    public void add(int p) {
        number.setValue(number.getValue() + p);

    }
}
