package com.test.oschina.mvcproject.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import com.test.oschina.mvcproject.ISocketServer;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 *  @author Weli
 *  @Time 2018-01-10  14:41
 *  @describe
 */
public class ServerService extends Service {
    public final String TAG = getClass().getSimpleName();
    private Socket socket;

    private LocalBroadcastManager mLocalBroadcastManager;

    private final Map<String, SocketRunnable> mapSockets = new HashMap<String, SocketRunnable>();
    private final Map<String, String> mapAddress = new HashMap<String, String>();
    private ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    private int runnableID = 1;

    public static final String MESSAGE_ACTION = "com.test.oschina.mvcproject.service";
//    /**    回调  */
//    private ServerReceive serverReceive;
    /** socket服务端 */
    private ServerSocket serverSocket;
    /** Handler 命令*/
    public final int Handler_CreateSucs = 0x00;
    public final int Handler_CreateFail = 0x01;
    public final int Handler_NewConn = 0x02;
    public final int Handler_ConnClose = 0x03;
    public final int Handler_ServerClose = 0x04;
    public final int Handler_RcvMsg = 0x05;
    @Override
    public void onCreate() {
        super.onCreate();
        initServer(8000);
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
        Log.d(TAG, "onCreate() executed");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand() executed");
        return super.onStartCommand(intent, flags, startId);
    }

    public ISocketServer.Stub iSocketServer= new ISocketServer.Stub() {
        @Override
        public boolean send(String msg) throws RemoteException {
            return false;
        }

        @Override
        public void clsoeSocketConn(String obj) throws RemoteException {
            clsoeConnection(obj);
        }


    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        clsoeServer();
        Log.d(TAG, "onDestroy() executed");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return iSocketServer;
    }



    /**
     * @param port
     *              需要打开的端口
     * @param
     *
     */
    public void initServer(final int port) {

        // 线程池里创建服服务器
        cachedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    serverSocket = new ServerSocket(port);
                    handler.sendEmptyMessage(Handler_CreateSucs);
                    while ((socket = serverSocket.accept()) != null) {
                        String ip = socket.getInetAddress().getHostAddress();
                        int port = socket.getPort();
                        String address = ip + ":" + port;
                        Log.i(TAG, "runNew connection have came=" + address);
                        Message msg = new Message();
                        msg.what = Handler_NewConn;
                        msg.obj = address;
                        handler.sendMessage(msg);
                        // 新的连接
                        SocketRunnable runnableNew = new SocketRunnable(socket, runnableID);
                        runnableID++;
                        mapSockets.put(address, runnableNew);
                        cachedThreadPool.execute(runnableNew);
                    }
                } catch (IOException e) {
                    clsoeServer();
                    Log.e(TAG, "initServerIOException", e);
                }
            }
        });
        int amount = ((ThreadPoolExecutor) cachedThreadPool).getActiveCount();
        Log.d(TAG, "initConnectionNow the thread amount = " + amount);
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (mLocalBroadcastManager != null) {
                if (msg.obj == null) {

                    /*serverReceive.result(msg.what, null, null);*/

                    Intent intent = new Intent(MESSAGE_ACTION);
                    intent.putExtra("messageWhat", msg.what);
                    Log.d("ServerService", "msg.obj1:" + msg.obj);
                    mLocalBroadcastManager.sendBroadcast(intent);

                } else if (msg.obj instanceof byte[]) {
                    String address = null;
                    if (msg.arg1 > 0) {
                        address = mapAddress.get(msg.arg1 + "");
                    }
                    Log.d("ServerService", "msg.obj2:" + msg.obj+"___"+msg.what+"____"+mapAddress);
/*                    serverReceive.result(msg.what, address, (byte[]) msg.obj);*/

                    Intent intent = new Intent(MESSAGE_ACTION);
                    intent.putExtra("messageWhat", msg.what);
                    intent.putExtra("address", address);
                    intent.putExtra("msgByte", (byte[]) msg.obj);
                    mLocalBroadcastManager.sendBroadcast(intent);
                } else if (msg.obj instanceof String) {
                    Log.d("ServerService", "msg.obj3:" + msg.obj+"___"+msg.what+"____"+mapAddress);

                    /*serverReceive.result(msg.what, msg.obj.toString(), null);*/

                    Intent intent = new Intent(MESSAGE_ACTION);
                    intent.putExtra("messageWhat", msg.what);
                    intent.putExtra("address", (Serializable) mapAddress);

                    mLocalBroadcastManager.sendBroadcast(intent);
                }
                if (msg.what == Handler_ServerClose) {

//                    serverReceive = null;
                    mLocalBroadcastManager=null;
                }
            }
            super.handleMessage(msg);
        }
    };

    public class SocketRunnable implements Runnable {
        private final int id;
        private String TAG;
        private Socket socket;
        private String address;
        private InputStream inputStream;
        public OutputStream outputStream;

        public SocketRunnable(Socket socket, int id) {
            this.id = id;
            this.TAG = getClass().getSimpleName();
            this.socket = socket;
            String ip = socket.getInetAddress().getHostAddress();
            int port = socket.getPort();
            this.address = ip + ":" + port;
            // 关联id和address
            mapAddress.put(id + "", address);
            Log.d(TAG,"SocketRunnable mapAddress:" + mapAddress);
            try {
                this.inputStream = socket.getInputStream();
                this.outputStream = socket.getOutputStream();
            } catch (IOException e) {
                Log.e(TAG, "RunnableSocket IOException", e);
            }
        }

        @Override
        public void run() {
            try {
                byte[] buffer = new byte[8192];
                byte[] buff;
                while (inputStream != null) {
                    int length = inputStream.read(buffer);
                    if (length <= -1) {
                        // 关闭连接
                        colse();
                        break;
                    }
                    buff = new byte[length];
                    System.arraycopy(buffer, 0, buff, 0, length);
                    // Handler发送消息
                    Message msg = new Message();
                    msg.what = Handler_RcvMsg;
                    msg.obj = buff;
                    msg.arg1 = id;
                    handler.sendMessage(msg);
                }
            } catch (IOException e) {
                Log.e(TAG, "run IOException", e);
            }
        }

        /**
         * 关闭连接
         */
        public void colse() {
            Message msg = new Message();
            msg.what = Handler_ConnClose;
            msg.obj = address;
            handler.sendMessage(msg);
            try {
                if (socket != null) {
                    socket.close();
                    socket = null;
                }
            } catch (IOException e) {
                Log.e(TAG, "colse IOException", e);
            }
            try {
                if (inputStream != null) {
                    inputStream.close();
                    inputStream = null;
                }
            } catch (IOException e) {
                Log.e(TAG, "colse IOException", e);
            }
            try {
                if (outputStream != null) {
                    outputStream.close();
                    outputStream = null;
                }
            } catch (IOException e) {
                Log.e(TAG, "colse IOException", e);
            }
        }
    }


    /**
     * @param address
     *              连接地址.
     * @param buffer  需要发送的数据包
     *
     */
    public boolean sendByte(String address, byte[] buffer) {
        SocketRunnable runnable = mapSockets.get(address);
        if (runnable != null) {
            try {
                runnable.outputStream.write(buffer);
                runnable.outputStream.flush();
                return true;
            } catch (IOException e) {
                Log.e(TAG, "sendByte IOException", e);
            }
        }
        return false;
    }

    /**
     * @param address
     *              连接地址
     */
    public void clsoeConnection(String address) {
        mapSockets.get(address).colse();
        mapSockets.remove(address);
    }

    /**
     * 关闭服务器
     */
    public void clsoeServer() {
        handler.sendEmptyMessage(Handler_ServerClose);
        Iterator<String> keys = mapSockets.keySet().iterator();
        while (keys.hasNext()) {
            String key = keys.next();
            SocketRunnable runnable = mapSockets.get(key);
            runnable.colse();
        }
        mapSockets.clear();
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            Log.e(TAG, "clsoeServer serverSocket", e);
        }
    }
}
