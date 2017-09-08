package com.test.oschina.mvcproject.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.test.oschina.mvcproject.MyService;
import com.test.oschina.mvcproject.R;

/**
 * Created by Administrator on 2017/9/7.
 */

public class ServiceTestActivity extends Activity  implements View.OnClickListener, ServiceConnection {
    Button bt_start, bt_stop, bt_bind, bt_unbing, bt_sync,bt_intent;
    EditText ed_edit;
    Intent intentService;
    TextView tv;
    private MyService.Binder binder = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        initView();
    }

    private void initView() {
        bt_start = (Button) findViewById(R.id.bt_start);
        bt_stop = (Button) findViewById(R.id.bt_stop);
        bt_bind = (Button) findViewById(R.id.bt_bind);
        bt_unbing = (Button) findViewById(R.id.bt_unbing);
        bt_sync = (Button) findViewById(R.id.bt_snyc);
        bt_intent = (Button) findViewById(R.id.bt_intent);
        ed_edit = (EditText) findViewById(R.id.ed_edit);
        tv = (TextView) findViewById(R.id.tv);
        bt_bind.setOnClickListener(this);
        bt_stop.setOnClickListener(this);
        bt_unbing.setOnClickListener(this);
        bt_start.setOnClickListener(this);
        bt_sync.setOnClickListener(this);
        bt_intent.setOnClickListener(this);
        intentService = new Intent(ServiceTestActivity.this, MyService.class);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_start:
                intentService.putExtra("data", ed_edit.getText().toString());
                startService(intentService);
                break;
            case R.id.bt_stop:
                stopService(intentService);
                break;
            case R.id.bt_bind:
                bindService(intentService, this, Context.BIND_AUTO_CREATE);
                break;
            case R.id.bt_unbing:
                unbindService(this);
                break;
            case R.id.bt_snyc:
                if (binder != null) {
                    binder.setData(ed_edit.getText().toString());
                }
                break;
            case R.id.bt_intent:

                if(ed_edit.getText().toString().equals("end")){
                    Intent i= new Intent(ServiceTestActivity.this,TestThreadActivity.class);
                    startActivity(i);
                   ServiceTestActivity.this.finish();
                }else {
                    Intent i= new Intent(ServiceTestActivity.this,TestThreadActivity.class);
                    startActivity(i);
                }
                break;

            default:
                break;

        }

    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        binder = (MyService.Binder) iBinder;
        Log.d("dat,", "onServiceConnected");
        binder.getService().setCallback(new MyService.Callback() {
            @Override
            public void onDataChanger(String msg) {
                Message message = new Message();
                message.what = 1;
                Bundle b = new Bundle();
                b.putString("data", msg);
                message.setData(b);
                handler.sendMessage(message);
            }
        });

    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    String string = msg.getData().getString("data");
                    tv.setText(string);
                    break;
                default:
                    break;

            }
        }
    };
}