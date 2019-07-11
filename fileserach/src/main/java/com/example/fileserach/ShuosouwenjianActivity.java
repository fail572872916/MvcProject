package com.example.fileserach;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ShuosouwenjianActivity extends Activity implements OnClickListener {


    private File file;
    private String path;
    private String info="";
    private String key; //关键字

    private EditText et; // 编辑view
    private Button search_btn; // button view

    ArrayAdapter<String> adapter;
    ArrayList list = new ArrayList();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        et = (EditText) findViewById(R.id.key);
        search_btn = (Button) findViewById(R.id.button_search);
        // file = new File(Environment.getExternalStorageDirectory().getPath());
        file = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
//        info = getString(R.string.info);

        search_btn.setOnClickListener(this);

        ListView listView = (ListView) findViewById(R.id.listview);//在视图中找到ListView
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);//新建并配置ArrayAapeter
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = new Intent(ShuosouwenjianActivity.this,Main2Activity.class);
                    intent.putExtra("i",list.get(position).toString());
                    startActivity(intent);
            }
        });

    }


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        path = "";

        key = et.getText().toString();

        BrowserFile(file);
    }

    public void BrowserFile(File fileold) {
        if (key.equals("")) {
            Toast.makeText(this, getString(R.string.pleaseInput), Toast.LENGTH_LONG).show();
        } else {
            list.clear();
            search(fileold);

        }
    }


    private void search(File fileold) {
        try {
            File[] files = fileold.listFiles();
            if (files.length > 0) {
                for (int j = 0; j < files.length; j++) {
                    if (!files[j].isDirectory()) {
                        if (files[j].getName().indexOf(key) > -1) {
                            path =  files[j].getAbsolutePath();

                            if (path.equals("")) {
                                Toast.makeText(this, getString(R.string.notFound), Toast.LENGTH_SHORT).show();
                            }
                            list.add( path);
                            adapter.notifyDataSetChanged();
                            //shuju.putString(files[j].getName().toString(),files[j].getPath().toString());
                        }
                    } else {
                        this.search(files[j]);
                    }
                }
            }
        } catch (Exception e) {

        }
    }
}