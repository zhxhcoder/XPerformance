package com.zhxh.performance.support;

/*
 * Created by zhxh on 2023/4/19
 */

import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class HookClickHelper {
    private static class HookClickHelperHolder {
        private static final HookClickHelper INSTANCE = new HookClickHelper();
    }

    private HookClickHelper() {
    }

    public static final HookClickHelper getInstance() {
        return HookClickHelperHolder.INSTANCE;
    }

    /*
   src 只为传值用
    */
    public void traverseHookView(Object src, View root, String linkStr) {
        if (null == root) {
            return;
        }
        linkStr = linkStr + "-" + root.getClass().getSimpleName();

        if (root instanceof ViewGroup) {
            //遍历ViewGroup,是子view加1，是ViewGroup递归调用
            for (int i = 0; i < ((ViewGroup) root).getChildCount(); i++) {
                View child = ((ViewGroup) root).getChildAt(i);
                hook(src, child, linkStr);
                if (child instanceof ViewGroup) {
                    traverseHookView(src, ((ViewGroup) root).getChildAt(i), linkStr);
                }
            }
        } else {
            hook(src, root, linkStr);
        }
    }

    /*
    src
     */
    private void hook(Object src, View view, String linkStr) {
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
            field.set(listenerInfo, new HookListener(src, (View.OnClickListener) field.get(listenerInfo), linkStr));
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
        private Object mSrc;
        private String mLinkStr;

        public HookListener(Object src, View.OnClickListener originalListener, String linkStr) {
            mSrc = src;
            mLinkStr = linkStr;
            mOriginalListener = originalListener;
        }

        @Override
        public void onClick(View v) {
            if (mOriginalListener != null) {
                mOriginalListener.onClick(v);
            }
            //获取到所有view的父母，爷爷-父亲-自己
            Toast.makeText(v.getContext(), mSrc.getClass().getSimpleName() + "-->\n" + mLinkStr + "-" + v.getClass().getSimpleName() + "\n@" + v.getId(), Toast.LENGTH_LONG).show();
        }
    }
}
