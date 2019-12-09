package com.test.oschina.mvcproject.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.test.oschina.mvcproject.R;
import com.test.oschina.mvcproject.utils.PrintLine;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrintActivity extends AppCompatActivity implements OnClickListener {


        final String TAG = "xxl";
         List list;
         Map<String,String> map;
        Map<String,Object> dishMap;
        Map<String,Object> dishMap1;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_print);
            Button btn = (Button) findViewById(R.id.print);
            btn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            list = new ArrayList<Map<String, Object>>();
            dishMap = new HashMap<String,Object>();
            dishMap1 = new HashMap<String,Object>();
            dishMap.put("cai_name", "地三鲜");
            dishMap.put("cai_price", "20");
            dishMap1.put("cai_name", "长豆角肉末盖浇饭");
            dishMap1.put("cai_price", "10");
            list.add(dishMap);
            list.add(dishMap1);
            map =new HashMap<String,String>();
            map.put("GS_Name", "杭州友络软件有限公司");
            map.put("GS_Address",
                    "浙江-杭州，滨江区南环路3760号保亿创艺大厦1203室");
            map.put("GS_Tel", "13507115045");
            map.put("GS_Qq","138027869");
            Log.i(TAG, map.get("GS_Address"));

            SimpleDateFormat   formatter   =   new   SimpleDateFormat   ("yyyy年MM月dd日   HH:mm:ss     ");
            Date   curDate   =   new   Date(System.currentTimeMillis());//获取当前时间
            final String   str   =   formatter.format(curDate); //可以获取当前的年月时分,也可以分开写:


            new Thread(){
                @SuppressWarnings("unchecked")
                public void run() {
                    try {
                        new PrintLine().print(list,map,str);
                        Log.i(TAG, map.get("GS_Name"));
                    } catch (IOException e) {
                        Log.i(TAG, e+"");
                        e.printStackTrace();
                    }
                };


            }.start();




        }



    }