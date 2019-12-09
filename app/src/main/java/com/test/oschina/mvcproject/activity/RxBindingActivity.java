package com.test.oschina.mvcproject.activity;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.jakewharton.rxbinding.view.RxView;
import com.test.oschina.mvcproject.R;

import java.util.List;
import java.util.function.BiFunction;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func8;
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

    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_binding);
        ButterKnife.bind(this);
        compositeSubscription = new CompositeSubscription();

//        RxView.touches(buttonTop).subscribe(envent ->{
//
//        });
        View mView = new View(this);


        Observable<MotionEvent> observableButtonTop = RxView.touches(buttonTopLeft);
        Observable<MotionEvent> observableButtonTopLeft = RxView.touches(buttonTop);
        Observable<MotionEvent> observableButtonTopRight = RxView.touches(buttonRight);
//        Observable<MotionEvent> observableButtonTopRight = RxView.touches(mView);
//        Observable<MotionEvent> observableButtonLeft = RxView.touches(mView);
//        Observable<MotionEvent> observableButtonRight = RxView.touches(mView);
//        Observable<MotionEvent> observableButtonBottomLeft = RxView.touches(mView);
//        Observable<MotionEvent> observableButtonBottom = RxView.touches(mView);
//        Observable<MotionEvent> observableButtonBottomRight = RxView.touches(mView);





//
//        Observable<Observable<MotionEvent>> sequence = Observable.just(observableButtonTop, observableButtonTopLeft, observableButtonTopRight, observableButtonLeft, observableButtonRight, observableButtonBottomLeft, observableButtonBottom, observableButtonBottomRight)
//        observableButtonBottomRight.subscribe(motionEvent -> { //setOnTouchListener
//            switch (motionEvent.getAction()) {
//
//
//                case MotionEvent.ACTION_MOVE:
//                    int lRawX = (int) motionEvent.getRawX();
//                    int lRawY = (int) motionEvent.getRawY();
//
//                    Log.d("RxBindingActivity", "motionEvent.getX()" + lRawX + "+motionEvent.getY():" + lRawY);
//                    boolean isExit = isInChangeImageZone(buttonTop, lRawX, lRawY);
//
//                    Log.d("RxBindingActivity", isExit + "");
//                    Log.d("MainActivityFragment", "touch event:" + motionEvent.getAction());
//
//            }
//
//        });




        // 相当于合并
//      (Observable<R>) Observable.combineLatest(observableButtonTop, observableButtonTopLeft, observableButtonTopRight, observableButtonLeft, observableButtonRight, observableButtonBottomLeft, observableButtonBottom, observableButtonBottomRight
//              );
        RxView.touches(buttonTop).subscribe(new Action1<MotionEvent>() {
            @Override
            public void call(MotionEvent motionEvent) { //setOnTouchListener
                switch (motionEvent.getAction()) {


                    case MotionEvent.ACTION_MOVE:
                        int lRawX = (int) motionEvent.getRawX();
                        int lRawY = (int) motionEvent.getRawY();

                        Log.d("RxBindingActivity", "motionEvent.getX()" + lRawX + "+motionEvent.getY():" + lRawY);
                        boolean isExit = isInChangeImageZone(buttonTop, lRawX, lRawY);

                        Log.d("RxBindingActivity", isExit + "");
                        Log.d("MainActivityFragment", "touch event:" + motionEvent.getAction());

                }

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
        Log.d("RxBindingActivity", "mChangeImageBackgroundRect.top:" + mChangeImageBackgroundRect.left);
        Log.d("RxBindingActivity", "mChangeImageBackgroundRect.top:" + mChangeImageBackgroundRect.top);
        Log.d("RxBindingActivity", "mChangeImageBackgroundRect.top:" + mChangeImageBackgroundRect.right);
        Log.d("RxBindingActivity", "mChangeImageBackgroundRect.top:" + mChangeImageBackgroundRect.top);
        Log.d("RxBindingActivity", "mChangeImageBackgroundRect.top:" + mChangeImageBackgroundRect.bottom);

        return have;
    }
}
