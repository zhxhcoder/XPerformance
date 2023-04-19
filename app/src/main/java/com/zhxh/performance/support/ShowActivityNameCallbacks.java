package com.zhxh.performance.support;

import android.app.Activity;
import android.app.Application;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhxh.performance.MainActivity;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
            traverseViewGroup(fl);


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


// 遍历viewGroup

    public int traverseViewGroup(View view) {
        int viewCount = 0;
        if (null == view) {
            return 0;
        }
        if (view instanceof ViewGroup) {
            //遍历ViewGroup,是子view加1，是ViewGroup递归调用
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View child = ((ViewGroup) view).getChildAt(i);

                hook(child);

                if (child instanceof ViewGroup) {
                    viewCount += traverseViewGroup(((ViewGroup) view).getChildAt(i));
                } else {
                    viewCount++;
                }
            }
        } else {
            hook(view);
            viewCount++;
        }
        return viewCount;
    }


    private void hook(View view) {
        try {
            //获取View 的class
            Class clazzView = Class.forName("android.view.View");
            //通过View的class获取getListenerInfo 方法
            Method method = clazzView.getDeclaredMethod("getListenerInfo");
            //getListenerInfo 方法是私有的，所以设置 setAccessible为true
            method.setAccessible(true);
            //让我们具体的view来执行getListenerInfo 方法。并且返回了一个类
            Object listenerInfo = method.invoke(view);
            //获取android.view.View$ListenerInfo 类的class
            Class clazzInfo = Class.forName("android.view.View$ListenerInfo");
            //获取ListenerInfo类中的参数mOnClickListener
            Field field = clazzInfo.getDeclaredField("mOnClickListener");
            //给listenerInfo所对应的类（也就是ListenerInfo）中的参数mOnClickListener 赋值。
            //赋的值就是我们要hook的
            //我们先field.get(listenerInfo) 获取View.OnClickListener，然后传递给HookListener
            //HookListener就可以做一些自己的事情了。
            //他只会接收一个View.OnClickListener接口，并不在乎他的实现类有多少个。
            // 但是实现了接口的类就都会执行了。
            field.set(listenerInfo, new HookListener((View.OnClickListener) field.get(listenerInfo)));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    //因为mOnClickListener变量是一个View.OnClickListener所以必须要实现这个接口
    public static class HookListener implements View.OnClickListener {
        private View.OnClickListener mOriginalListener;

        public HookListener(View.OnClickListener originalListener) {
            mOriginalListener = originalListener;
        }

        @Override
        public void onClick(View v) {
            if (mOriginalListener != null) {
                mOriginalListener.onClick(v);
            }
            Toast.makeText(v.getContext(), "hook:"+v.getAccessibilityClassName(), Toast.LENGTH_LONG).show();
        }
    }
}
