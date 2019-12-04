package com.test.oschina.mvcproject.activity;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.jakewharton.rxbinding.view.RxView;
import com.test.oschina.mvcproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

public class RxBindingActivity extends AppCompatActivity {

    @BindView(R.id.button_top_left)
    Button buttonTopLeft;
    @BindView(R.id.button_top)
    Button buttonTop;
    @BindView(R.id.button_top_right)
    Button buttonTopRight;
    @BindView(R.id.button_left)
    Button buttonLeft;
    @BindView(R.id.button_right)
    Button buttonRight;
    @BindView(R.id.button_bottom_left)
    Button buttonBottomLeft;
    @BindView(R.id.button_bottom)
    Button buttonBottom;
    @BindView(R.id.button_bottom_right)
    Button buttonBottomRight;
    @BindView(R.id.button_left_trialTeaching)
    Button buttonLeftTrialTeaching;
    @BindView(R.id.button_right_trialTeaching)
    Button buttonRightTrialTeaching;
    @BindView(R.id.button_left_90)
    Button buttonLeft90;
    @BindView(R.id.button_right_90)
    Button buttonRight90;
    private CompositeSubscription compositeSubscription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_binding);
        ButterKnife.bind(this);
        compositeSubscription = new CompositeSubscription();

//        RxView.touches(buttonTop).subscribe(envent ->{
//
//        });

        RxView.touches(buttonTop).subscribe(new Action1<MotionEvent>() {
            @Override
            public void call(MotionEvent motionEvent) { //setOnTouchListener

                Log.d("MainActivityFragment", "touch event:" + motionEvent.getAction());
            }
        });
    }



    private Rect mChangeImageBackgroundRect = null;

    //判断touch事件点是否在view范围内
    private boolean isInChangeImageZone(View view, float x, float y) {
        boolean have = false;
        if (null == mChangeImageBackgroundRect) {
            mChangeImageBackgroundRect = new Rect();
        }
        view.getDrawingRect(mChangeImageBackgroundRect);
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        mChangeImageBackgroundRect.left = location[0];
        mChangeImageBackgroundRect.top = location[1];
        mChangeImageBackgroundRect.right = mChangeImageBackgroundRect.right + location[0];
        mChangeImageBackgroundRect.bottom = mChangeImageBackgroundRect.bottom + location[1];
        have = mChangeImageBackgroundRect.contains((int) Math.ceil(x), (int) Math.ceil(y));
        Log.e("werwer", "123123");

        return have;
    }
}
