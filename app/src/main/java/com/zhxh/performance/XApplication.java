package com.zhxh.performance;

import android.app.Application;
import android.content.Context;

import com.zhxh.performance.support.DebugHelper;
import com.zhxh.performance.support.ShowActivityNameCallbacks;

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
        DebugHelper.startTime = System.currentTimeMillis();
    }
}
