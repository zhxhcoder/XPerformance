package com.zhxh.performance.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.zhxh.performance.support.StringUtil;

import java.util.LinkedList;

/*
 * Created by zhxh on 2023/4/27
 * 为了解决华为手机密码保险问题
 */
@SuppressLint("AppCompatCustomView")
public class PwdEditText extends EditText {
    TextWatcher watcher;
    LinkedList<Character> charStack = new LinkedList<>();

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
                    charStack.pop();
                } else {
                    char a = s.charAt(start);
                    charStack.push(a);
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
    }

    public String getPlainText() {
        StringBuilder sb = new StringBuilder();
        for (int i = charStack.size() - 1; i >= 0; i--) {
            sb.append(charStack.get(i));
        }
        return sb.toString();
    }
}
