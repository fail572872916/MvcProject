package com.glandroid.mysqlandroid;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.SQLException;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {


    private Connection conn;
    String name;
    String pwd1;
    String pwd2;

    EditText editText1;
    EditText editText2;
    EditText editText3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //获取数据库的连接
        new Thread(new Runnable() {
            @Override
            public void run() {
                conn = Util.openConnection(Constant.URL, Constant.USER, Constant.PASSWORD);
                Log.i("onConn", "onConn");
            }
        }).start();
        editText1 = findViewById(R.id.editText5);
        editText2 = findViewById(R.id.editText7);
        editText3 = findViewById(R.id.editText8);
        findViewById(R.id.button3).setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        name = editText1.getText().toString();
        pwd1 = editText2.getText().toString();
        pwd2 = editText3.getText().toString();
        if (!pwd1.equals(pwd2)) {
            Toast.makeText(this, "密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "请输入帐号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(pwd1)) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }

        onInsert(name,pwd1);

    }

    public void onInsert(final String name, final String pwd) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String sql = "insert into aaa values(null,'" + name +
                        "','" + pwd +
                        "','" + "" +
                        "','" + "" + "')" ;

              int a =  Util.execSQL(conn, sql);
                handler.sendEmptyMessage(a);

                Log.i("onInsert", "onInsert");
            }
        }).start();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                conn = null;
            } finally {
                conn = null;
            }
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 1:

                    Toast.makeText(RegisterActivity.this, "操作成功！", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    finish();
                    break;
                case 0:
                    Toast.makeText(RegisterActivity.this, "操作失败", Toast.LENGTH_SHORT).show();
                    break;

            }
        }
    };
}
