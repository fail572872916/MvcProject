package com.test.oschina.mvcproject.checkerboard;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.test.oschina.mvcproject.MyApplication;
import com.test.oschina.mvcproject.R;
import com.test.oschina.mvcproject.utils.MyComparator;

import java.util.ArrayList;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class lattice extends View {

    private OnViewClick mViewClick;

    /**
     * 点的集合
     */
    private ArrayList<Point> mWhiteArray = new ArrayList<>();
    List<Point> linesPoint = new ArrayList<>();

    Set<Integer> lines = new TreeSet<Integer>(new MyComparator());
    /**
     * 棋盘的宽度和高度
     */
    private int mPanelWidth;
    /**
     * 每一行的高度
     */
    private float mLineHeight;
    /**
     * 设置棋盘为10*10的网格
     */
    private int MAX_LINE = 5;
    /**
     * 画笔
     */
    private Paint mPaint = new Paint();
    private Paint mPaint1 = new Paint();

    /**
     * 白棋子和黑棋子的图片
     */
    public lattice(Context context) {
        super(context);
    }

    public lattice(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //初始化画笔，获取棋子的图片等
        init();
    }

    private void init() {
        mPaint.setColor(0x88000000);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);
//        mWhitePiece = BitmapFactory.
//                decodeResource(getResources(), R.drawable.stone_w2);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        //因为要绘制正方形，所以取宽和高的最小值
        int width = Math.min(widthSize, heightSize);
        //heightMode
        if (widthMode == MeasureSpec.UNSPECIFIED) {
            width = heightSize;
            Log.d("lattice", "width:" + width);
        } else if (heightMode == MeasureSpec.UNSPECIFIED) {
            width = widthSize;
            Log.d("lattice", "width:" + width);
        }
        setMeasuredDimension(width, width);
    }

    /**
     * 当宽高确定后赋值
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mPanelWidth = getHeight();
        Log.d("lattice", "mPanelWidth:" + mPanelWidth);
        //总高度除以行数为每一行的高度
        mLineHeight = h * 1.0f / MAX_LINE;
        Log.d("lattice", "mLineHeight:" + mLineHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制棋盘
        drawBoard(canvas);
        //绘制棋子
        drawPieces(canvas);
    }

    /**
     * @param canvas
     */
    private void drawBoard(Canvas canvas) {
        int w = mPanelWidth;
        float lineHeight = mLineHeight;

        for (int i = 0; i < MAX_LINE; i++) {
            int startX = (int) (lineHeight / 2);
            int endX = (int) (w - lineHeight / 2);
            int y = (int) ((0.5 + i) * lineHeight);
            Log.d("lattice", "y:" + y);

            canvas.drawLine(startX, y, endX, y, mPaint);
            if (i == 0 || i == MAX_LINE - 1) {
                canvas.drawLine(y, startX, y, endX, mPaint);
            }
            lines.add(y);
        }
        Log.d("lattice", "linesPoint:" + linesPoint);
        Log.d("lattice", "linesPoint:" + lines);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //如果五子棋结束则不能再落字
        int action = event.getAction();
        if (action == MotionEvent.ACTION_UP) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            Log.d("lattice5", "y:" + y + "         x:" + x);
            Point p = new Point(x, y);
            //判断是否这个地方已经落过子

            getValidPoint(p);

            invalidate();
            Log.d("lattice1", "p:" + p);

        }
        //表明处理了touch事件
        return true;
    }

    /**
     * 纠正点
     *
     * @param point
     */
    private void getValidPoint(Point point) {
        if (lines.size() > 0) {
            for (Integer integer : lines) {
                if (point.y == integer) {
                    mWhiteArray.add(new Point(point.x, integer));
                    return;
                } else {
                    int minX = Collections.min(lines);
                    int maxX = Collections.max(lines);
                    Log.d("lattice3", "point.y - integer:" + (point.y - integer));
                    if (point.y - integer <= 80 && point.y - integer >= -80) {
                        getLinePoint(point, integer, minX, maxX);
                        return;
                    }
                }

            }
        }
    }


    /**
     * 取消添加点
     * @param point
     */
    public void removePoint(Point point) {
        if (mWhiteArray.size() > 0) {
            for (Point point1 : mWhiteArray) {
                if (point1.x == point.x && point.y == point1.y) {
                    mWhiteArray.remove(point);
                    invalidate();
                    break;
                }

            }
        }
    }
    /**
     * 得到线上的点
     *
     * @param point
     * @param integer
     * @param minX
     * @param maxX
     */
    private void getLinePoint(Point point, Integer integer, int minX, int maxX) {
        savePoint(point, integer, minX, maxX);

    }

    /**
     * 保存点
     *
     * @param point
     * @param integer
     * @param minX
     * @param maxX
     */
    private void savePoint(Point point, Integer integer, int minX, int maxX) {
        Point point1;
        if (point.x < minX) {
            point1 = new Point(minX, integer);
        } else if (point.x > maxX) {
            point1 = new Point(maxX, integer);
        } else {
            point1 = new Point(point.x, integer);
        }

        if (mWhiteArray.size() > 0) {
            linesPoint.clear();
            for (Point point2 : mWhiteArray) {
                if (point2.y == point1.y) {
                    linesPoint.add(point2);
                }
            }
            if (linesPoint.size() > 0) {
                for (Point point2 : linesPoint) {
                    if (point1.x < point2.x) {
                        Log.d("lattice7", "不能添加");
                        break;
                    } else {
                        mWhiteArray.add(point1);
                        if (mViewClick != null) {
                            mViewClick.onClick(point1, getTheNumberOfRows(point));
                        }
                    }
                }
            }
        } else {
            mWhiteArray.add(point1);
            if (mViewClick != null) {
                mViewClick.onClick(point1, getTheNumberOfRows(point));
            }
        }
//            mWhiteArray.add(point1);
//            if (mViewClick != null) {
//                mViewClick.onClick(point1, getTheNumberOfRows(point));
//            }

    }


    /**
     * 得到第几行
     *
     * @param point
     * @return
     */
    public int getTheNumberOfRows(Point point) {
        int i = 0;
        if (lines.size() > 0) {
            for (Integer line : lines) {
                if (line == point.y) {
                    i++;
                    break;
                }
            }
        }
        return i;
    }

    /**
     * 判断是否可以添加点
     *
     * @param point
     * @return
     */
    private boolean isAdd(Point point) {
        boolean isAdd = false;
        if (mWhiteArray.size() > 0) {
            for (Point point1 : mWhiteArray) {
                Log.d("lattice4", "point.x" + point.x +
                        "+point1.x:" + point1.x);
                if (point.y == point1.y) {
                    if (point.x - point1.x <= 12 && point.x - point1.x >= -12) {
                        isAdd = false;
                    } else {
                        isAdd = true;
                    }
                }
            }
        }
        return isAdd;
    }

    /**
     * 画出的圆与桌台号
     *
     * @param canvas
     */
    private void drawPieces(Canvas canvas) {
        for (int i = 0, n = mWhiteArray.size(); i < n; i++) {
            Point whitePoint = mWhiteArray.get(i);
            mPaint1.setColor(Color.BLUE);
            // 小圆
            canvas.drawCircle(whitePoint.x, whitePoint.y, 10, mPaint1);
            mPaint1.setColor(Color.RED);
            mPaint1.setTextSize(24);
            canvas.drawText("1号桌", whitePoint.x - 20, whitePoint.y + 40, mPaint1);
//            canvas.drawPoint(whitePoint.x, whitePoint.y, mPaint);//画一个点
        }
    }

    public void setOnViewClick(OnViewClick click) {
        this.mViewClick = click;
    }

    public interface OnViewClick {
        /**
         * 传入的点
         *
         * @param point
         * @param row   第几行
         */
        void onClick(Point point, int row);
    }

}
