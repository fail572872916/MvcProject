package com.test.oschina.mvcproject.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Toast;

import com.test.oschina.mvcproject.Keyboard;
import com.test.oschina.mvcproject.PayEditText;
import com.test.oschina.mvcproject.R;

/**
 * @author Administrator
 * @name MvcProject
 * @class name：com.test.oschina.mvcproject.utils
 * @class describe
 * @time 2017-11-08 9:47
 * @change
 * @chang time
 * @class describe
 */
public class AlertDialogUtil extends AlertDialog {
    private PayEditText payEditText;
    private Keyboard keyboard;
    static Context mContext;
    private String mPassword;
    private static final String[] KEY = new String[]{
            "1", "2", "3",
            "4", "5", "6",
            "7", "8", "9",
            "完成", "0", "删除"
    };

    public AlertDialogUtil(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_keybroad_layout);

        keyboard = (Keyboard) findViewById(R.id.KeyboardView_pay);
        payEditText = (PayEditText) findViewById(R.id.PayEditText_pay);

        setSubView();

        initEvent();
    }


    private void setSubView() {
        //设置键盘
        keyboard.setKeyboardKeys(KEY);
    }

    private void initEvent() {
        final int numLength = 11;
        final int positionNum = 9;
        keyboard.setOnClickKeyboardListener(new Keyboard.OnClickKeyboardListener() {
            @Override
            public void onKeyClick(int position, String value) {
                if (position < numLength && position != positionNum) {
                    payEditText.add(value);
                } else if (position == positionNum) {


                } else if (position == numLength) {
                    payEditText.remove();
                }
            }
        });
        /**
         * 当密码输入完成时的回调
         */
        payEditText.setOnInputFinishedListener(new PayEditText.OnInputFinishedListener() {
            @Override
            public void onInputFinished(String password) {
                Toast.makeText(mContext, password+"", Toast.LENGTH_SHORT).show();

            }
        });
    }

    protected void onTouchOutside() {
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        /* 触摸外部弹窗 */
        if (isOutOfBounds(getContext(), event)) {
            onTouchOutside();
        }
        return super.onTouchEvent(event);
    }
    private boolean isOutOfBounds(Context context, MotionEvent event) {
        final int x = (int) event.getX();
        final int y = (int) event.getY();
        final int slop = ViewConfiguration.get(context).getScaledWindowTouchSlop();
        final View decorView = getWindow().getDecorView();
        return (x < -slop) || (y < -slop) || (x > (decorView.getWidth() + slop))
                || (y > (decorView.getHeight() + slop));
    }
}
