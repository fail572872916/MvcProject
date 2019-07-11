package com.glandroid.mysqlandroid;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    private Connection conn;
    String userName;
    String userPwd;
    EditText mEtUser;
    EditText mEtPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        //获取数据库的连接
        new Thread(new Runnable() {
            @Override
            public void run() {
                conn = Util.openConnection(Constant.URL, Constant.USER, Constant.PASSWORD);
                Log.i("onConn", "onConn");
            }
        }).start();

        mEtUser = (EditText) findViewById(R.id.editText);
        mEtPwd = (EditText) findViewById(R.id.editText2);
        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.button:
                userName = mEtUser.getText().toString();
                userPwd = mEtPwd.getText().toString();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //查询数据库
                        User user = Util.query(conn, "select * from aaa where name= " + userName + " and pwd =" + userPwd);
                        if (user == null) {
                            //在线程中不能用更新ui 显示toast所以用此方法
                            handler.sendEmptyMessage(1);

                        } else {
                            //跳转到用户界面
                            Intent intent = new Intent(LoginActivity.this, UserActivity.class);
                            intent.putExtra("user", (Serializable) user);
                            startActivity(intent);
                            Log.d("LoginActivity", "user:" + user);
                        }


                    }
                }).start();
                break;
            case R.id.button2:
                startActivity( new Intent(LoginActivity.this,RegisterActivity.class));

                break;

        }

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

            Toast.makeText(LoginActivity.this, "账号或密码错误", Toast.LENGTH_SHORT).show();
        }
    };

}
