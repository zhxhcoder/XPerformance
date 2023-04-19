package com.zhxh.performance;

import android.app.Application;
import android.content.Context;

import com.zhxh.performance.support.ShowActivityNameCallbacks;

/*
 * Created by zhxh on 2023/4/14
 */
public class XApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(new ShowActivityNameCallbacks());
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        System.out.println("开始时间" + System.currentTimeMillis());
    }

}
