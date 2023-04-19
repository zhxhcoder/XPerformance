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
            HookClickHelper.getInstance().traverseHookView(fl, fl);

            String activityName = activity.getClass().getSimpleName();

            TextView textView = new TextView(activity, null);
            textView.setClickable(false);
            textView.setText(activityName);
            textView.setTextSize(13);
            textView.setTextColor(Color.parseColor("#00A443"));
            textView.setPadding(25, 140, 0, 0);
            textView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            fl.addView(textView);
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

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
    }



}
