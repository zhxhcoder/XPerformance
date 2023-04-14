package com.zhxh.performance;

import android.app.Application;
import android.content.Context;

/*
 * Created by zhxh on 2023/4/14
 */
public class XApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        System.out.println("开始时间" + System.currentTimeMillis());
    }

}
