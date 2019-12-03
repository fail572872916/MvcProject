package com.example.jetpacktest.androidviewmodule;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;

import com.example.jetpacktest.R;

public class MyViewModel extends AndroidViewModel {

private     SavedStateHandle savedStateHandle;
    private    String mSpNamte = getApplication().getResources().getString(R.string.sp_name);
    private   String mkey = getApplication().getResources().getString(R.string.data_key);


    public MyViewModel(@NonNull Application application, SavedStateHandle savedStateHandle) {
        super(application);
        this.savedStateHandle = savedStateHandle;
        if (!savedStateHandle.contains(mkey)) {
            load();
        }
    }

    public LiveData<Integer> getLiveDataNumber() {
        return savedStateHandle.getLiveData(mkey);
    }

    private void load() {

        SharedPreferences sharedPreferences = getApplication().getSharedPreferences(mSpNamte, Context.MODE_PRIVATE);
        int key = sharedPreferences.getInt(mkey, 0);
        savedStateHandle.set(mkey, key);

    }

    public void save() {

        SharedPreferences sharedPreferences = getApplication().getSharedPreferences(mSpNamte, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(mkey, getLiveDataNumber().getValue()==null?0: getLiveDataNumber().getValue());
        editor.apply();

    }

    public  void add(int x){
        savedStateHandle.set(mkey, getLiveDataNumber().getValue()==null?0: getLiveDataNumber().getValue() +x);

    }


}
