package com.test.oschina.mvcproject.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.test.oschina.mvcproject.ISocketServer;
import com.test.oschina.mvcproject.service.ServerService;

/**
 * @author Administrator
 * @name MvcProject
 * @class describe
 * @time 2018-01-10 16:54
 */
public class SocketServerBaseActivity extends Activity{
    public BaseMessageBackReceiver mReciver;
    private LocalBroadcastManager localBroadcastManager;
    private IntentFilter mIntentFilter;
    private Intent mServiceIntent;
    public ISocketServer iSocketServer;

 private ServiceConnection connection= new ServiceConnection() {
     @Override
     public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
         iSocketServer=ISocketServer.Stub.asInterface(iBinder);
     }

     @Override
     public void onServiceDisconnected(ComponentName componentName) {
         iSocketServer = null;
     }
 };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }
    public  void  startRecive(){
        if (mReciver != null) {
            initSocket();
            LocalBroadcastManager.getInstance(this).registerReceiver(
                    mReciver, mIntentFilter);
            bindService(mServiceIntent, connection, BIND_ABOVE_CLIENT);
            startService(mServiceIntent);
        }
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

    public void initSocket() {

        localBroadcastManager = LocalBroadcastManager.getInstance(this);
//        mReciver = new MessageBackReciver();
        mServiceIntent      = new Intent(this, ServerService.class);
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(ServerService.MESSAGE_ACTION);
    }

    public void close(){
        localBroadcastManager.unregisterReceiver(mReciver);
        stopService(mServiceIntent);
        if (connection != null) {
            unbindService(connection);
        }
    }

}
