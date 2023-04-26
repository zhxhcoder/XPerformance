package com.zhxh.performance.support;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;


/*
 * Created by zhxh on 2023/4/26
 * 每隔5秒刷新一次数据
 * 保存到最
 */
public class DebugHelper {
    public static long startTime;
    public static long endTime;

    //全局的一个noticeBoard

    //含有noticeBoard的最上面Activity
    public static Activity curActivity;
    //也可以activity.getClass().getSimpleName()
    public static String curActivityName;

    public static void funcAnchor() {
        System.currentTimeMillis();
    }

    public static void addNotice(String s) {
        getNoticeBoard().append("\n" + s);
    }

    //根据Activity获取NoticeBoard
    private static TextView getNoticeBoard() {
        View decorView = curActivity.getWindow().getDecorView();
        FrameLayout fl = decorView.findViewById(android.R.id.content);

        View top = fl.getChildAt(fl.getChildCount() - 1);
        TextView notice;
        if (top instanceof TextView) {
            notice = (TextView) fl.getChildAt(fl.getChildCount() - 1);
        } else {
            throw new RuntimeException("请检查curActivity是否含有NoticeBoard");
        }
        return notice;
    }

    public static String debugInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append(isMemAccountInfo());
        sb.append(isLocalAccountInfo());
        return sb.toString();
    }

    public static String isMemAccountInfo() {
        return "\n内存记住账号：" + true;
    }

    public static String isLocalAccountInfo() {
        return "\n本地记住账号：" + false;
    }
}
