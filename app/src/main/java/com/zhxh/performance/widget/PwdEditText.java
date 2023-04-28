package com.zhxh.performance.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.view.ActionMode;

import com.zhxh.performance.support.StringUtil;

import java.util.Deque;
import java.util.LinkedList;

/*
 * Created by zhxh on 2023/4/27
 * 目的：
 * 为了解决华为手机密码保险问题
 *
 * 功能：
 * 输入的每一个字符都会在100ms后变成*号
 * 禁止了该输入框的复制功能和粘贴功能
 * 用了一个双端队列维护字符串，getPlainText可以获得明文字符串
 */
@SuppressLint("AppCompatCustomView")
public class PwdEditText extends EditText {
    TextWatcher watcher;
    Deque<Character> charDeque = new LinkedList<>();

    public PwdEditText(Context context) {
        super(context);
        init(context);
    }

    public PwdEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PwdEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }

    public PwdEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context ctx) {
        watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d("TextWatcher", "beforeTextChanged:" + s.toString() + " start:" + start + " count:" + count + " after:" + after);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //before 0表示添加，1表示删除
                if (before == 1) {//出栈
                    charDeque.pollLast();
                } else {
                    for (int i = 0; i < count; i++) {
                        char a = s.charAt(start + i);
                        charDeque.offer(a);
                    }
                }
                PwdEditText.this.postDelayed(() -> {
                    //移除监听
                    Log.d("TextWatcher", "onTextChanged:" + s.toString() + " start:" + start + " before:" + before + " count:" + count);
                    PwdEditText.this.removeTextChangedListener(watcher);
                    PwdEditText.this.setText(StringUtil.replace(".", "*", s.toString()));
                    PwdEditText.this.setSelection(s.length());
                    //重新监听
                    PwdEditText.this.addTextChangedListener(watcher);
                }, 100L * (1 - before));
            }

            @Override
            public void afterTextChanged(Editable s) {
                Toast.makeText(ctx, getPlainText(), Toast.LENGTH_SHORT).show();
            }
        };
        this.addTextChangedListener(watcher);

        //禁止复制功能
        this.setLongClickable(false);

        //禁止粘贴功能
        this.setCustomSelectionActionModeCallback(new android.view.ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(android.view.ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onPrepareActionMode(android.view.ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(android.view.ActionMode mode, MenuItem item) {
                return false;
            }

            @Override
            public void onDestroyActionMode(android.view.ActionMode mode) {

            }
        });
    }

    //获取明文
    public String getPlainText() {
        StringBuilder sb = new StringBuilder();
        for (char item : charDeque) {
            sb.append(item);
        }
        return sb.toString();
    }

    //一键清除密码时采用
    public void clear() {
        this.setText("");
        charDeque.clear();
    }
}
