package com.glandroid.mysqlandroid;
/**
 * Created by liyanzhen on 16/7/20.
 */

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class Util {


    //连接到数据库
    public static Connection openConnection(String url, String user,
                                            String password) {
        Connection conn = null;
        try {
            final String DRIVER_NAME = "com.mysql.jdbc.Driver";
            //加载驱动
            Class.forName(DRIVER_NAME);
            //连接到数据库
            conn = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            conn = null;
        } catch (SQLException e) {
            conn = null;
        }

        return conn;
    }

    public static User query(Connection conn, String sql) {

        User user = null;
        if (conn == null) {
            return user;
        }

        Statement statement = null;
        ResultSet result = null;

        try {
            statement = conn.createStatement();
            result = statement.executeQuery(sql);
            if (result != null && result.first()) {
                int id = result.getInt("id");
                String idColumnIndex = result.getString("name");
                String nameColumnIndex = result.getString("pwd");
                String phone = result.getString("phone");
                String address = result.getString("address");

                user = new User(id, idColumnIndex, nameColumnIndex, phone, address);


            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (result != null) {
                    result.close();
                    result = null;
                }
                if (statement != null) {
                    statement.close();
                    statement = null;
                }

            } catch (SQLException sqle) {

            }
        }
        return user;
    }

//    执行sql语句 返回数据的执行结果
    public static int execSQL(Connection conn, String sql) {
        Log.d("Util","ssssssssssss" +sql);
        int  execResult = 0;
        if (conn == null) {
            return execResult;
        }
        Statement statement = null;
        try {
            statement = conn.createStatement();
            if (statement != null) {
                //如果成功就返回
                return    statement.executeUpdate(sql) ;
            }
        } catch (SQLException e) {
            execResult = 0;
        }

        return execResult;
    }
}