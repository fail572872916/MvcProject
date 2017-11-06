package com.test.oschina.mvcproject.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.test.oschina.mvcproject.R;
import com.test.oschina.mvcproject.SocketService;
import com.test.oschina.mvcproject.base.SocketBaseActivity;

/**
 * @author Administrator
 * @name MvcProject
 * @class name：com.test.oschina.mvcproject.activity
 * @class describe
 * @time 2017-11-06 9:21
 * @change
 * @chang time
 * @class describe
 */
public class SocketTestActivity extends SocketBaseActivity implements View.OnClickListener {
    private static final String TAG = "SocketTestActivity";
    private Button mBtLink;
    private Button mBtBreak;
    private Button mBtChange;
    private EditText mInput;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);
        socketInit();
        initView();

    }
    private void initView() {
        mBtLink = (Button) findViewById(R.id.bt_socket_link);
        mBtBreak = (Button) findViewById(R.id.bt_socket_break);
        mBtChange = (Button) findViewById(R.id.bt_socket_change_link);
        mInput = (EditText) findViewById(R.id.ed_input);
        initListener();
    }

    private void initListener() {
        mBtLink.setOnClickListener(this);
        mBtBreak.setOnClickListener(this);
        mBtChange.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_socket_link:
                Log.d("SocketTestActivity", "ss");
                startSocket();
                break;

            case R.id.bt_socket_break:
                Log.d("SocketTestActivity", "bb");
                stopService();

                break;

            case R.id.bt_socket_change_link:
                change(mInput.getText().toString());

                break;
            default:
                break;
        }
    }

    /**
     * @author Weli
     * @time 2017-10-14  11:47
     * @describe 初始化Socket
     */
    private void socketInit() {
        //给全局消息接收器赋值，并进行消息处理
        mReciver = new BaseMessageBackReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();

                if (action.equals(SocketService.HEART_BEAT_ACTION)) {
                    Log.d(TAG, "心跳");
                } else {
                    String message = intent.getStringExtra("message");
                    Log.d(TAG, message + "_________" + System.currentTimeMillis());


                }
            }
        };
    }
}
