package com.test.oschina.mvcproject.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class DetailPoint {

    @Property(nameInDb = "point_id")
    @Id(autoincrement = true)
    private Long pointId;

    /**
     * 行
     */
    private int rol;
    /**
     * 列
     */
    private int col;
    private int xValue;
    private int yValue;
    @Generated(hash = 1532557608)
    public DetailPoint(Long pointId, int rol, int col, int xValue, int yValue) {
        this.pointId = pointId;
        this.rol = rol;
        this.col = col;
        this.xValue = xValue;
        this.yValue = yValue;
    }
    @Generated(hash = 1245636259)
    public DetailPoint() {
    }
    public Long getPointId() {
        return this.pointId;
    }
    public void setPointId(Long pointId) {
        this.pointId = pointId;
    }
    public int getRol() {
        return this.rol;
    }
    public void setRol(int rol) {
        this.rol = rol;
    }
    public int getCol() {
        return this.col;
    }
    public void setCol(int col) {
        this.col = col;
    }
    public int getXValue() {
        return this.xValue;
    }
    public void setXValue(int xValue) {
        this.xValue = xValue;
    }
    public int getYValue() {
        return this.yValue;
    }
    public void setYValue(int yValue) {
        this.yValue = yValue;
    }



}
