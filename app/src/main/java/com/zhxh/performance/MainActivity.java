package com.zhxh.performance;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
/*
 * Created by zhxh on 2023/4/19
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button mButton = findViewById(R.id.button);
        hook(mButton);
    }

    public void onClick(View view) {
        Log.d(TAG, "onClick: 点击事件");
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
            Toast.makeText(v.getContext(), "hook", Toast.LENGTH_LONG).show();
        }
    }
}
