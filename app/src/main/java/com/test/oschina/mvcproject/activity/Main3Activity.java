package com.test.oschina.mvcproject.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.test.oschina.mvcproject.R;

public class Main3Activity extends AppCompatActivity {

    ListView lv_testList;

    String []  content = new String [] {"贝尔塞购物车","线程测试","服务测试","打印机测试"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        lv_testList= (ListView) findViewById(R.id.lv_testList);

        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,content);
      lv_testList.setAdapter(adapter);

      lv_testList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
              Toast.makeText(Main3Activity.this, "i:" + i, Toast.LENGTH_SHORT).show();
          }
      });

    }
}
