package com.example.jetpacktest.viewmodel;

import androidx.lifecycle.ViewModel;


/**
 * ViewModel是以关联生命周期的方式来存储和管理UI相关的数据的类，即使configuration发生改变（比如旋转屏幕），数据仍然可以存在不会销毁
 *
 */
public class MyViewModel extends ViewModel {
    public   int  value=0;



}
