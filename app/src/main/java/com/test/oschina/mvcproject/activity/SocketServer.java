package com.test.oschina.mvcproject.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.test.oschina.mvcproject.R;
import com.test.oschina.mvcproject.SocketService;
import com.test.oschina.mvcproject.adapter.ConnectionAdapter;
import com.test.oschina.mvcproject.callback.SelectedItem;

import java.util.List;
import java.util.Map;

/**
 *  @author Weli
 *  @Time 2018-01-10  14:50
 *  @describe 
 */
public class SocketServer extends SocketServerBaseActivity implements View.OnClickListener {
    private Button startService;

    private Button stopService;

    private ListView lvConnetion;
    ConnectionAdapter connectionAdapter;


    private List<Map<String, String>> listConnections;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_socket_server) ;
        mReciver= new BaseMessageBackReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (action.equals("myaction")) {
                    int messageWhat = intent.getIntExtra("messageWhat",0);
                    String a=intent.getStringExtra("address");
                    Log.d("ServerService", messageWhat+"ssssss"+a);
                    Log.d("ServerService", messageWhat+"ssssss"+a);
                }
            }
        };
        startRecive();

        startService = (Button) findViewById(R.id.start_service);
        stopService = (Button) findViewById(R.id.stop_service);
        lvConnetion= (ListView) findViewById(R.id.activity_tcpserver_lv_connection);
        startService.setOnClickListener(this);
        stopService.setOnClickListener(this);
        connectionAdapter = new ConnectionAdapter(this, listConnections, selectedItem);




    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_service:

//                startRecive();
//                Map<String, String> item = new HashMap<String, String>();
//                item.put("tag", "online");
//                item.put("msg", "");
//                item.put("address", msg);
//                listConnections.add(item);
//                connectionAdapter.notifyDataSetChanged(listConnections);
//                lvConnetion.setSelection(listConnections.size());
                break;
            case R.id.stop_service:
                close();
                break;
            default:
                break;
        }
    }


    // 选择断开连接
    private SelectedItem selectedItem = new SelectedItem() {
        @Override
        public void result(int index) {

//            LogSwitch.v(TAG, "result", "listConnections.size=" + listConnections.size() + " index=" + index);
//            // 主动断开连接
//            serverTCP.clsoeConnection(listConnections.get(index).get("address"));
//            listConnections.remove(index);
//            connectionAdapter.notifyDataSetChanged(listConnections);
//            // 移除链接,会话重置到最后一个会话
//            indexSelected = listConnections.size() - 1;
//            if (indexSelected >= 0) {
//                String address = listConnections.get(indexSelected).get("address");
//                msgAdapter.notifyDataSetChanged(mapNotes.get(address));
//            } else {
//                msgAdapter.notifyDataSetChanged(new ArrayList<Map<String, String>>());
//            }
        }
    };



}