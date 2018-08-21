package com.test.oschina.mvcproject.checkerboard;

import android.graphics.Point;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.ToOne;

@Entity
@Keep
public class DeskPoint {
    @Id
    private long  deskId;

    private  int col;
    @ToOne
    private  Point point;
    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    @Override
    public String toString() {
        return "DeskPoint{" +
                "col=" + col +
                ", point=" + point +
                '}';
    }
}
