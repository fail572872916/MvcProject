package com.glandroid.mysqlandroid;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.SQLException;


public class MainActivity extends AppCompatActivity {

    private Connection conn;
    private Button onConn;
    private Button onInsert;
    private Button onDelete;
    private Button onUpdate;
    private Button onQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onConn = (Button) findViewById(R.id.onConn);
        onInsert = (Button) findViewById(R.id.onInsert);
        onDelete = (Button) findViewById(R.id.onDelete);
        onUpdate = (Button) findViewById(R.id.onUpdate);
        onQuery =  (Button) findViewById(R.id.onQuery);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if (conn != null){
            try{
                conn.close();
            } catch (SQLException e){
                conn = null;
            }finally {
                conn =null;
            }
        }
    }

    public void onConn(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                conn = Util.openConnection(Constant.URL, Constant.USER, Constant.PASSWORD);
                Log.i("onConn", "onConn");
            }
        }).start();
    }
    public void onInsert(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String sql = "insert into users values(15, 'xiaoming')";
                Util.execSQL(conn, sql);
                Log.i("onInsert", "onInsert");
            }
        }).start();
    }


    public void onDelete(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String sql = "delete from users where name='hanmeimei'";
                Util.execSQL(conn, sql);
                Log.i("onDelete", "onDelete");
            }
        }).start();
    }

    public void onUpdate(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String sql = "update users set name='lilei' where name='liyanzhen'";
                Util.execSQL(conn, sql);
                Log.i("onUpdate", "onUpdate");
            }
        }).start();
    }

    public void onQuery(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Util.query(conn, "select * from users");
                Log.i("onQuery", "onQuery");
            }
        }).start();
    }
}

