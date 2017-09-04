package com.test.oschina.mvcproject.model;

import com.test.oschina.mvcproject.entity.Info;

import java.util.List;

/**
 * Created by Administrator on 2017/9/4.
 * 添加商品接口
 */

public interface InfoDao {

    List<Info> addInfo(Info info);
}
