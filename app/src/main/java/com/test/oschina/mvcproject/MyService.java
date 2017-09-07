package com.test.oschina.mvcproject;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.test.oschina.mvcproject.activity.TestThreadActivity;

public class MyService extends Service {

    private String data = "默认信息";
    private boolean serverRunning = false;
    MyThread my ;    Thread thread;
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new Binder();
    }

    public class Binder extends android.os.Binder {
        public void setData(String data) {
            MyService.this.data = data;
        }

        public MyService getService() {
            return MyService.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        data = intent.getStringExtra("data");

        Log.d("dat", data+"我启动了");
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("dat,", "onCreate");
//        my = new MyThread();
//        thread = new Thread(my);
//        my.start();
        new Thread() {
            @Override
            public void run() {
                int a = 0;
                serverRunning = true;
                while (serverRunning) {
                    a++;
                    Log.d("dat,", "22" + data);
                    String str = data + a;
                    if (callback != null) {
                        callback.onDataChanger(str);
                    }
                    try {
                        sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
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
                        Log.d("dat,", "22" + data);
                        String str = data + index;
                        if (callback != null) {
                            callback.onDataChanger(str);
                        }
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
    @Override
    public void onDestroy() {
        super.onDestroy();
        serverRunning = false;
        Log.d("dat,", "onDestroy");
    }

    private Callback callback;

    public Callback getCallback() {
        return callback;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public static interface Callback {
        void onDataChanger(String msg);

    }

}