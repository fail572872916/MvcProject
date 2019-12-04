package com.test.oschina.mvcproject;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;

import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 *  @author Weli
 *  @Time 2018-01-10  14:48
 *  @describe 
 */
public class SocketService extends Service {
    private static final String TAG = "SocketService";
    public MyCountDownTimer mc;
    /**
     * HEART_BEAT_RATE    心跳包频率
     */
    private boolean isStart = true;
    private static final long HEART_BEAT_RATE = 6 * 1000;
    private static final int TIME_OUT = 5 * 1000;
    public static String HOST = "192.168.0.108";
    public static final int PORT = 30000;
    private ReadThread mReadThread;
    public static final String MESSAGE_ACTION = "com.example.administrator.carcontrol.socket";
    public static final String HEART_BEAT_ACTION = "com.example.administrator.carcontrol.heart";
    public static final String HEART_BEAT_STRING_RECEIVE = "{\"flaglist\":null,\"flag\":null,\"socket_ip\":null,\"message\":null,\"operationCode\":null,\"heartBeat\":\"0x0F\"}";
    public static final String HEART_BEAT_STRING = "{\"flaglist\":null,\"flag\":null,\"socket_ip\":null,\"message\":null,\"operationCode\":null,\"heartBeat\":\"0x0h\"}";
    public static final String HEART_BEAT_BREAK = "";
    private long sendTime = 0L;
    /**
     * 弱引用 在引用对象的同时允许对垃圾对象进行回收
     */
    private LocalBroadcastManager mLocalBroadcastManager;
    private WeakReference<Socket> mSocket;
    private IBackService.Stub iBackService = new IBackService.Stub() {
        @Override
        public boolean sendMessage(String message) throws RemoteException {
            return sendMsg(message);
        }
    };
    public boolean linkSocket = false;
    //    private MyTask myTask;
    private InitTask initTask;

    @Override
    public IBinder onBind(Intent arg0) {
        return iBackService;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initTask = new InitTask();
        initTask.execute();
        Log.d(TAG, "create");
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
        mc = new MyCountDownTimer(99999999, 2000).getInstance();
    }

    public boolean sendMsg(String msg) {

        if (null == mSocket || null == mSocket.get()) {
            return false;
        }
        Socket soc = mSocket.get();
        try {
            if (soc != null && soc.isConnected() && !soc.isClosed()) {
                OutputStream os = soc.getOutputStream();
                String message = msg + "\r\n";
                os.write(message.getBytes());
                os.flush();
                //每次发送成数据，就改一下最后成功发送的时间，节省心跳间隔时间
                sendTime = System.currentTimeMillis();
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    int count1 = 0;
    int count2 = 0;
    int count3 = 0;

    /**
     * 初始化socket
     *
     * @throws UnknownHostException
     * @throws IOException
     */
    private void initSocket() throws IOException {
        if (mc != null) {
            mc.cancel();
            mc.start();
        }
        Log.d(TAG, "count1++:" + ++count1);
        Socket socket;
        mSocket = null;
        socket = initSocketddd();
//        if (myTask != null) {
//
//            myTask.cancel(true);
//            Log.d(TAG, "dsa:" + socket + myTask.getStatus());
//        }
        if (socket != null && socket.isConnected() && !socket.isClosed()) {
            mSocket = new WeakReference<>(socket);

            mReadThread = new ReadThread(socket);
            mReadThread.start();
//            myTask = new MyTask(socket);
//            myTask.execute("open");
//            Log.d(TAG, "我进来了" + myTask.getStatus());
            count2 = count2++;
            Log.d(TAG, "count2++:" + count2++);

            String time = String.valueOf(System.currentTimeMillis());
            sendMsg("1111" + time);
            linkSocket = true;
        } else {
            Log.d(TAG, "count3++:" + count3++);

        }
    }


    private static Socket initSocketddd() {

        Socket socket;
        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(HOST, PORT), TIME_OUT);
        } catch (UnknownHostException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
        return socket;
    }

    /**
     * 释放socket
     *
     * @param mSocket
     */
    private void releaseLastSocket(WeakReference<Socket> mSocket) {
        try {
            if (null != mSocket) {
                Socket sk = mSocket.get();
                if (sk != null && !sk.isClosed()) {
                    sk.close();

                    Log.d(TAG, "close");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    /**
     * 服务被销毁时调用
     */
    @Override
    public void onDestroy() {
        Log.e("Service", "onDestroy");
        mc.cancel();
        mHandler.removeCallbacks(mReadThread);
        if (mReadThread != null) {
            mReadThread.release();
        }
        releaseLastSocket(mSocket);

        mReadThread.interrupt();
        Log.d(TAG, "mReadThread:" + mReadThread.getState());
        if (initTask != null) {
            initTask.cancel(true);
            Log.d(TAG, "initTask.getStatus():" + initTask.getStatus());
        }
        Log.e("Service", "执行了");
        super.onDestroy();
    }
    private class InitTask extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            Log.i(TAG, "onPreExecute() called");
        }

        //doInBackground方法内部执行后台任务,不可在此方法内修改UI
        @Override
        protected Void doInBackground(String... params) {
            try {
                initSocket();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        //onPostExecute方法用于在执行完后台任务后更新UI,显示结果
        @Override
        protected void onPostExecute(Void result) {
            Log.i(TAG, "onPostExecute(Result result) called");

        }

        //onCancelled方法用于在取消执行中的任务时更改UI
        @Override
        protected void onCancelled() {
            Log.i(TAG, "onCancelled() called");
        }
    }

    private class ReadThread extends Thread {
        private WeakReference<Socket> mWeakSocket;
        private boolean isStart = true;

        public ReadThread(Socket socket) {
            mWeakSocket = new WeakReference<>(socket);
        }
        private void release() {
            isStart = false;
//            if (mWeakSocket != null) {
                releaseLastSocket(mWeakSocket);
//            }
        }
        @SuppressLint("NewApi")
        @Override
        public void run() {
            super.run();
            Socket socket = mWeakSocket.get();
            if (null != socket) {
                try {
                    InputStream is = socket.getInputStream();
                    byte[] buffer = new byte[1024 * 4];
                    int length;
                    while (!socket.isClosed() && !socket.isInputShutdown()
                            && isStart && ((length = is.read(buffer)) != -1)) {
                        if (length > 0) {
                            String message = new String(Arrays.copyOf(buffer,length)).trim();
                            Log.i(TAG, "收到服务器发送来的消息：" + message + "______________________");
                            // 收到服务器过来的消息，就通过Broadcast发送出去 处理心跳回复
                            if (message.equals(HEART_BEAT_STRING)) {
                                if (sendMsg(HEART_BEAT_STRING_RECEIVE)) {

                                }
                                Intent intent = new Intent(HEART_BEAT_ACTION);
                                mLocalBroadcastManager.sendBroadcast(intent);
                                linkSocket = true;
                            } else {
                                //其他消息回复
                                Intent intent = new Intent(MESSAGE_ACTION);
                                intent.putExtra("message", message);
                                mLocalBroadcastManager.sendBroadcast(intent);
                                linkSocket = true;
                            }
                        }
                        Thread.sleep(100);//每隔0.1秒读取一次，节省点资源
                    }
                } catch (IOException e) {
                    release();
                    releaseLastSocket(mSocket);
                    e.printStackTrace();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class MyCountDownTimer extends CountDownTimer {
        private MyCountDownTimer myCountDownTimer;

        private synchronized MyCountDownTimer getInstance() {
            if (myCountDownTimer == null) {
                myCountDownTimer = new MyCountDownTimer(99999999, 2000);
            }
            return myCountDownTimer;
        }

        /**
         * @param millisInFuture    表示以毫秒为单位 倒计时的总数
         *                          <p>
         *                          例如 millisInFuture=1000 表示1秒
         * @param countDownInterval 表示 间隔 多少微秒 调用一次 onTick 方法
         *                          <p>
         *                          例如: countDownInterval =1000 ; 表示每1000毫秒调用一次onTick()
         */
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            mc.cancel();

        }

        @Override
        public void onTick(long millisUntilFinished) {
            Log.i("MainActivity", millisUntilFinished / 1000 + "");
            if (millisUntilFinished == 0) {
                mc.cancel();
                mc.start();
            } else {
                if (millisUntilFinished % 6 == 0 && !sendMsg(HEART_BEAT_BREAK)) {
                    linkSocket = false;
                    if (!linkSocket) {
                        Log.i("MainActivity", "jjjj");
                        try {
                            Thread.sleep(3000);
                            if (initTask != null) {
                                releaseLastSocket(mSocket);
                                initTask.cancel(true);
                                Log.d(TAG, "initTask.getStatus():" + initTask.getStatus());
                                initTask = new InitTask();
                                initTask.execute();
                                mHandler.removeCallbacks(mReadThread);

                                releaseLastSocket(mSocket);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

}