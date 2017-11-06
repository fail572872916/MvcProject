package com.test.oschina.mvcproject.base;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.test.oschina.mvcproject.IBackService;
import com.test.oschina.mvcproject.R;
import com.test.oschina.mvcproject.SocketService;

/**
 * @author Administrator
 * @name CarControl
 * @class name：com.example.administrator.carcontrol
 * @class describe
 * @time 2017-10-12 16:14
 * @change
 * @chang time
 * @class describe
 */
public class SocketBaseActivity extends AppCompatActivity {
    /***
     * 子类中完成抽象函数赋值
     实体中通过实现该全局接收器方法来处理接收到消息
     */
    public BaseMessageBackReceiver mReciver;
    private IntentFilter mIntentFilter;
    private Intent mServiceIntent;
    private LocalBroadcastManager localBroadcastManager;
    /**
     * IBackService ;
     * //通过调用该接口中的方法来实现数据发送
     */
    public IBackService iBackService;
    /**
     * 标记是否已经进行了服务绑定与全局消息注册
     */
    private boolean flag = false;
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            iBackService = IBackService.Stub.asInterface(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            iBackService = null;
        }
    };

    public void startSocket() {
        if (mReciver != null && !flag) {
            Log.d("SocketBaseActivity", "aa");
            initSocket();
            localBroadcastManager.registerReceiver(mReciver, mIntentFilter);
            bindService(mServiceIntent, conn, BIND_ABOVE_CLIENT);
            startService(mServiceIntent);
            flag = true;
        }
    }


    /**
     * 发送socket
     *
     * @param data 发送参数
     * @return ture/false;
     */
    public boolean socketSend(final String data) {
        try {
            if (iBackService == null) {
                Toast.makeText(this, R.string.server_off, Toast.LENGTH_SHORT).show();
                return false;
            } else {
                boolean isSend = iBackService.sendMessage(data);
                if (!isSend) {
                    Toast.makeText(this, R.string.link_fail, Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 发送
     *
     * @param str
     * @return
     */
    public boolean send(String str) {
        Log.d("SocketBaseActivity", str);
        try {
            iBackService.sendMessage(str);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 改变接连对象
     *
     * @param string
     */
    public void change(String string) {
        if (flag) {

            stopService(mServiceIntent);
            unbindService(conn);
            SocketService.HOST = string;
            if (localBroadcastManager == null) {
                initSocket();
            }
            localBroadcastManager.registerReceiver(mReciver, mIntentFilter);
            bindService(mServiceIntent, conn, BIND_ABOVE_CLIENT);
            startService(mServiceIntent);
            Log.d("SocketBaseActivity", "2323");
        } else {
            flag = true;
            SocketService.HOST = string;
            if (localBroadcastManager == null) {
                initSocket();
            }
            localBroadcastManager.registerReceiver(mReciver, mIntentFilter);
            bindService(mServiceIntent, conn, BIND_ABOVE_CLIENT);
            startService(mServiceIntent);
            Log.d("SocketBaseActivity", "aaa");
        }
    }

    public void stopService() {
        if (mServiceIntent != null || flag) {
            localBroadcastManager.unregisterReceiver(mReciver);
            stopService(mServiceIntent);
            if (conn != null) {
                unbindService(conn);
            }
        }
    }

    @Override
    public void onDestroy() {
        stopService();
        super.onDestroy();
    }

    public void initSocket() {
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
//        mReciver = new MessageBackReciver();
        mServiceIntent = new Intent(this, SocketService.class);
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(SocketService.HEART_BEAT_ACTION);
        mIntentFilter.addAction(SocketService.MESSAGE_ACTION);
    }

    /**
     * @author Weli
     * @time 2017-10-16  10:50
     * @describe 广播接收器
     */
    public abstract class BaseMessageBackReceiver extends BroadcastReceiver {
        /**
         * 　　register　　Receiver
         *
         * @param context
         * @param intent
         */
        @Override
        public abstract void onReceive(Context context, Intent intent);
    }
}