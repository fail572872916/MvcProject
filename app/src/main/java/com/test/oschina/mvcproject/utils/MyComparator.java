package com.test.oschina.mvcproject.utils;

import java.util.Comparator;

public class MyComparator implements Comparator<Integer> {


    @Override


    public int compare(Integer o1, Integer o2) {

//降序排列
        return o1 - o2;


    }


}


