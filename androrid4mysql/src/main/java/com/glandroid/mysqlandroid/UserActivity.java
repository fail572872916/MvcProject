package com.glandroid.mysqlandroid;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.SQLException;

public class UserActivity extends AppCompatActivity implements View.OnClickListener {

    private Connection conn;
    EditText ed_account;
    EditText ed_pwd;
    EditText ed_phone;
    EditText ed_address;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        //获取数据库的连接
        new Thread(new Runnable() {
            @Override
            public void run() {
                conn = Util.openConnection(Constant.URL, Constant.USER, Constant.PASSWORD);
                Log.i("onConn", "onConn");
            }
        }).start();

        ed_account = findViewById(R.id.ed_account);
        ed_pwd = findViewById(R.id.ed_pwd);
        ed_phone = findViewById(R.id.ed_phone);
        ed_address = findViewById(R.id.ed_address);
        user = (User) getIntent().getSerializableExtra("user");
        Log.d("UserActivity", "user:" + user);
        if (user != null) {
            ed_account.setText(user.getUser());
            ed_pwd.setText(user.getPwd());
            ed_phone.setText(user.getPhone());
            ed_address.setText(user.getAdress());

        }
        findViewById(R.id.bt_delete).setOnClickListener(this);
        findViewById(R.id.bt_update).setOnClickListener(this);

        findViewById(R.id.bt_exit).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_delete:
//                给出弹框
                AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                        .setTitle("提示")
                        .setMessage("你确定要注销账户吗，注销后将无法登陆")

                        .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                onDelete();
                            }
                        });
//创建并显示
                dialog.create().show();
                break;

            case R.id.bt_update:

                String account = ed_account.getText().toString();
                String pwd = ed_account.getText().toString();
                String phone = ed_phone.getText().toString();
                String address = ed_address.getText().toString();
                //这里获取输入框的值然后判断输入值不为空，不然账号密码被清空会导致无法登陆
                if (TextUtils.isEmpty(account)) {
                    Toast.makeText(this, "请输入帐号", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (TextUtils.isEmpty(pwd)) {
                    Toast.makeText(this, "请输入帐号", Toast.LENGTH_SHORT).show();
                    break;
                }
                //执行查询方法
                onUpdate(account, pwd, phone, address);
                break;
            case R.id.bt_exit:
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
        }
    }

    /**
     * 修改用户
     */
    public void onUpdate(final String account, final String pwd, final String phone, final String address) {
        if (user != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //删除执行
                    String sql = "update   aaa  set " +
                            "name = '" + account +
                            "', pwd = '" + pwd +
                            "', phone = '" + phone +
                            "', address = '" + address +
                            "'   where  id= " + user.id;
                    int a = Util.execSQL(conn, sql);
                    //在线程中不能用更新ui 修改成功后重新登陆获取信息
                    handler.sendEmptyMessage(a);
                }
            }).start();
        } else {
            Toast.makeText(this, "用户信息异常", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 注销用户
     */
    public void onDelete() {
        if (user != null) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    //删除执行
                    String sql = "delete from aaa where id= " + user.id;
                    int a = Util.execSQL(conn, sql);
                    //在线程中不能用更新ui 显示toast所以用此方法
                    handler.sendEmptyMessage(a);

                }
            }).start();
        } else {
            Toast.makeText(this, "用户信息异常", Toast.LENGTH_SHORT).show();
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

            switch (msg.what) {
                case 1:

                    Toast.makeText(UserActivity.this, "操作成功！", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(UserActivity.this, LoginActivity.class));
                    finish();
                    break;
                case 0:
                    Toast.makeText(UserActivity.this, "操作失败", Toast.LENGTH_SHORT).show();
                    break;

            }
        }
    };
}
