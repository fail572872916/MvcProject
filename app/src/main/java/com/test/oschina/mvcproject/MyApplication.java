package com.test.oschina.mvcproject;


import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.test.oschina.mvcproject.entity.gen.DaoMaster;
import com.test.oschina.mvcproject.entity.gen.DaoSession;
import com.test.oschina.mvcproject.entity.gen.DeskPointDao;
import com.test.oschina.mvcproject.entity.gen.DetailPointDao;


public class MyApplication extends Application {
    // 常量
    private final String TAG;
    // 变量
    private Context context;
    public static boolean isDevelopingVersion;
    private static DaoSession daoSession;
    public static DetailPointDao pointDao;

    public MyApplication() {
        this.TAG = getClass().getSimpleName();
    }

    @Override
    public void onCreate() {
        this.context = getApplicationContext();

        super.onCreate();
        setupDatabase();
    }
    /**
     * 配置数据库
     */
    private void setupDatabase() {
        //创建数据库shop.db
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "desk.db", null);
        //获取可写数据库
        SQLiteDatabase db = helper.getWritableDatabase();
        //获取数据库对象
        DaoMaster daoMaster = new DaoMaster(db);
        //获取dao对象管理者
        daoSession = daoMaster.newSession();
        pointDao = daoSession.getDetailPointDao();
    }

    public static DaoSession getDaoInstant() {
        return daoSession;
    }



}
