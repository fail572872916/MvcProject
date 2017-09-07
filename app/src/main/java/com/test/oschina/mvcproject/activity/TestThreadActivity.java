package com.test.oschina.mvcproject.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.test.oschina.mvcproject.R;

public class TestThreadActivity extends AppCompatActivity implements View.OnClickListener {

    Button bt_start,bt_stop,bt_end;
    MyThread my ;    Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_thread);
        initView();
    }
    private void initView() {
        bt_end= (Button) findViewById(R.id.bt_end);
        bt_stop= (Button) findViewById(R.id.bt_stop);
        bt_start= (Button) findViewById(R.id.bt_start);
        bt_start.setOnClickListener(this);
        bt_stop.setOnClickListener(this);
        bt_end.setOnClickListener(this);
        my = new MyThread();
         thread = new Thread(my);
    }
    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.bt_start:
                thread.start();
                break;
            case  R.id.bt_stop:
                my.pauseThread();
                break;
            case R.id.bt_end:
                my.resumeThread();
                break;
            default:
                break;
        }

    }

    private class MyThread extends Thread {
        private final Object lock = new Object();
        private boolean pause = false;

        /**
         * 调用这个方法实现暂停线程
         */
        void pauseThread() {
            pause = true;
        }

        /**
         * 调用这个方法实现恢复线程的运行
         */
        void resumeThread() {
            pause = false;
            synchronized (lock) {
                lock.notifyAll();
            }
        }

        /**
         * 注意：这个方法只能在run方法里调用，不然会阻塞主线程，导致页面无响应
         */
        void onPause() {
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        @Override
        public void run() {
            super.run();
            try {
                int index = 0;
                while (true) {
                    // 让线程处于暂停等待状态
                    while (pause) {
                        onPause();
                    }
                    try {
                        Log.d("da",""+index);
                        Thread.sleep(2000);
                        ++index;
                    } catch (InterruptedException e) {
                        //捕获到异常之后，执行break跳出循环
                        break;
                    }
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }
}
