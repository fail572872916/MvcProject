package com.test.oschina.mvcproject.model;

import com.test.oschina.mvcproject.entity.Info;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/4.
 * 实现添加商品
 */

public class InfoImpl implements  InfoDao {
    @Override
    public List<Info> addInfo(Info info) {
        List<Info> list=new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            info.setMoney(i);
            info.setText("￥:");
            list.add(info);
        }
        return list;
    }

}
