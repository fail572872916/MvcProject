package com.test.oschina.mvcproject.entity;

/**
 * Created by Administrator on 2017/9/4.
 * 商品表
 */

public class Info {
    private String  text;
    private   double   money;
    public Info() {
    }
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "Info{" +
                "text='" + text + '\'' +
                ", money=" + money +
                '}';
    }
}
