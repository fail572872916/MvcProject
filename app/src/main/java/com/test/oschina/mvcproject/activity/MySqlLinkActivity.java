package com.test.oschina.mvcproject.activity;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.util.Log;
import android.widget.Toast;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import com.test.oschina.mvcproject.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
public class MySqlLinkActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(runnable).start();
    }
    @SuppressLint("HandlerLeak")
    Handler myHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {

            super.handleMessage(msg);
            Bundle data=new Bundle();
            data=msg.getData();
            Log.d("MainActivity", "data:" + data.get("id"));

            Toast.makeText(MySqlLinkActivity.this, data.toString(), Toast.LENGTH_SHORT).show()
            ;
        }
    };

    Runnable runnable=new Runnable() {
        private Connection con = null;

        @Override
        public void run() {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                //引用代码此处需要修改，address为数据IP，Port为端口号，DBName为数据名称，UserName为数据库登录账户，Password为数据库登录密码
                con = (Connection) DriverManager.getConnection("jdbc:mysql://192.168.0.102:3306/aaa",
                        "root","root");

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {

                e.printStackTrace();
            }

            try {
                //测试数据库连接
                testConnection(con);
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public void testConnection(Connection con) throws java.sql.SQLException, JSONException {
            //查询表名为“table_test”的所有内容
            try {
                String sql = "select * from `aaa`";
                //创建Statement
                Statement stmt = (Statement) con.createStatement();
                //ResultSet类似Cursor
                ResultSet rs = stmt.executeQuery(sql);
                Map<String, Object> message = new HashMap<String, Object>();
                JSONArray array = new JSONArray();
                // 获取列数
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();
                while (rs.next()) {
                    JSONObject      jsonObj = new JSONObject();
                    // 遍历每一列
                    for (int i = 1; i <= columnCount; i++) {
                        String columnName = metaData.getColumnLabel(i);
                        String value =rs.getString(columnName);
                        jsonObj.put(columnName, value);
                    }
                    array.put(jsonObj);
                }
//                Message msg=new Message();
//                    msg.setData(bundle);
//                    myHandler.sendMessage(msg);
                message.put("code", "0");
                message.put("message", "1");
                message.put("Gamelist_list", array);
                Log.d("MainActivity", message.toString());
                rs.close();
                stmt.close();
            } catch (SQLException e) {

            } finally {
                if (con != null) {
                    try {
                        con.close();
                    } catch (SQLException e) {
                    }
                }
            }
        }
    };




}
