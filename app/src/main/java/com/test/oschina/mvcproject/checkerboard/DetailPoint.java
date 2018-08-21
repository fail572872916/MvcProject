package com.test.oschina.mvcproject.checkerboard;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Property;

@Entity
@Keep
public class DetailPoint {
    @Id
    @Property(nameInDb = "point_id")
    private long pointId;

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

    public long getPointId() {
        return pointId;
    }

    public void setPointId(long pointId) {
        this.pointId = pointId;
    }

    public int getRol() {
        return rol;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getxValue() {
        return xValue;
    }

    public void setxValue(int xValue) {
        this.xValue = xValue;
    }

    public int getyValue() {
        return yValue;
    }

    public void setyValue(int yValue) {
        this.yValue = yValue;
    }

    @Override
    public String toString() {
        return "DetailPoint{" +
                "pointId=" + pointId +
                ", rol=" + rol +
                ", col=" + col +
                ", xValue=" + xValue +
                ", yValue=" + yValue +
                '}';
    }
}
