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

import com.test.oschina.mvcproject.R;

import java.util.ArrayList;

public class lattice extends View {

    //是否结束
    private boolean mIsGameOver = false;
    private ArrayList<Point> mWhiteArray = new ArrayList<>();    //棋子只占网格宽度的3/4
    private float ratioPieceOfLineHeight = 3 * 1.0f / 4;


    //棋盘的宽度和高度，为了正方形
    private int mPanelWidth;
    //每一行的高度
    private float mLineHeight;
    //设置棋盘为10*10的网格
    private int MAX_LINE = 10;
    //画笔
    private Paint mPaint = new Paint();
    private Paint mPaint1 = new Paint();
    //白棋子和黑棋子的图片
    private Bitmap mWhitePiece;

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
        mWhitePiece = BitmapFactory.
                decodeResource(getResources(), R.drawable.stone_w2);
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

    //当宽高确定后赋值
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
            Log.d("lattice", "startX:" + startX);
            Log.d("lattice", "endX:" + endX);
            canvas.drawLine(startX, y, endX, y, mPaint);

            canvas.drawLine(y, startX, y, endX, mPaint);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //如果五子棋结束则不能再落字

        int action = event.getAction();
        if (action == MotionEvent.ACTION_UP) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            Log.d("lattice", "x:" + x);
            Log.d("lattice", "y:" + y);
            Point p = new Point(x,y);

            //判断是否这个地方已经落过子
            mWhiteArray.add(p);
            invalidate();
            Log.d("lattice1", "p:" + p);

        }
        //表明处理了touch事件
        return true;
    }


    private Point getValidPoint(int x, int y) {
        //用计算后的int值更容易判断一个点的位置是否已经下过了，防止重复
        return new Point((int) (x / mLineHeight), (int) (y / mLineHeight));
    }

    private void drawPieces(Canvas canvas) {
        for (int i = 0, n = mWhiteArray.size(); i < n; i++) {
            Point whitePoint = mWhiteArray.get(i);
            mPaint1.setColor(Color.BLUE);
            // 小圆
            canvas.drawCircle(whitePoint.x  , whitePoint.y,10,  mPaint1);


//            canvas.drawPoint(whitePoint.x, whitePoint.y, mPaint);//画一个点


        }
    }
}
