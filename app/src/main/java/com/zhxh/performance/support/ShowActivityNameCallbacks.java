package com.zhxh.performance.support;

import android.app.Activity;
import android.app.Application;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

/*
 * Created by zhxh on 2023/4/19
 */
public class ShowActivityNameCallbacks implements Application.ActivityLifecycleCallbacks {
    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    @Override
    public void onActivityStarted(Activity activity) {
        if (true) {
            if (activity == null) {
                return;
            }
            if (activity.getWindow() == null) {
                return;
            }
            if (activity.getWindow().getDecorView() == null) {
                return;
            }

            View decorView = activity.getWindow().getDecorView();
            FrameLayout fl = decorView.findViewById(android.R.id.content);

            //遍历fl,对里面的每个view进行hook
            //HookClickHelper.getInstance().traverseHookView(activity, fl,"");

            String activityName = activity.getClass().getSimpleName();

            TextView textView = new TextView(activity, null);
            textView.setClickable(true);
            textView.setText(String.format("%s%s", activityName, DebugHelper.debugInfo()));
            textView.setTextSize(11);
            textView.setTextColor(Color.parseColor("#00A443"));
            FrameLayout.LayoutParams params =new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(25, 150, 0, 0);

            fl.addView(textView,params);
            //在添加textView之后再来赋值
            DebugHelper.curActivityName = activityName;
            DebugHelper.curActivity = activity;
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        if (true) {
            if (activity == null) {
                return;
            }
            if (activity.getWindow() == null) {
                return;
            }
            if (activity.getWindow().getDecorView() == null) {
                return;
            }
            View decorView = activity.getWindow().getDecorView();
            FrameLayout fl = decorView.findViewById(android.R.id.content);
            View top = fl.getChildAt(fl.getChildCount() - 1);
            if (top instanceof TextView) {
                fl.removeViewAt(fl.getChildCount() - 1);
            }
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
    }

}
