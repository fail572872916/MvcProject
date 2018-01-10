package com.test.oschina.mvcproject.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.test.oschina.mvcproject.R;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    ListView lv_testList;

    String[] content = new String[]{"贝尔塞购物车", "线程测试", "服务测试", "打印机测试", "网络检测","二维码生成","键盘","复制图片到SD卡","socket服务端" +
            ""};

    List list = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        lv_testList = (ListView) findViewById(R.id.lv_testList);
        iniData();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, content);
        lv_testList.setAdapter(adapter);

        lv_testList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(HomeActivity.this, (Class<?>) list.get(i)));
            }
        });

    }

    private void iniData() {
        list.add(HomeActivity.class);
        list.add(TestThreadActivity.class);
        list.add(ServiceTestActivity.class);
        list.add(PrintActivity.class);
        list.add(CheckNetActivity.class);
        list.add(QrCodeActivity.class);
        list.add(PopBoradKeyActivity.class);
        list.add(CopyPhotoActivity.class);
        list.add(SocketServer.class);
    }
}
