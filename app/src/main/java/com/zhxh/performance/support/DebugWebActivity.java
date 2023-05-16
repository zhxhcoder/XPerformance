package com.zhxh.performance.support;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Method;

/*
 * Created by zhxh on 2023/5/15
 * 模拟h5调用逻辑
 */
public class DebugWebActivity extends AppCompatActivity {
    TextView tvResponse;
    EditText editMethod;
    EditText editFunc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);

        tvResponse = new TextView(this);
        //外方法名
        editMethod = new EditText(this);
        //子方法名
        editFunc = new EditText(this);
        editMethod.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if ("nativeBridge".equals(s.toString())) {
                    editFunc.setVisibility(View.VISIBLE);
                } else {
                    editFunc.setVisibility(View.GONE);
                }
            }
        });

        editMethod.setText("nativeBridge");

        //回调callbackId
        EditText etCallBack = new EditText(this);
        etCallBack.setHint("回调callbackId");

        //回调参数
        EditText etParams = new EditText(this);
        etParams.setHint("参数");

        Button action = new Button(this);
        action.setText("执行");
        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Object res = invokeJavaMethod(editMethod.getText().toString(),
                        editFunc.getText().toString(),
                        etCallBack.getText().toString(),
                        etParams.getText().toString());

                Toast.makeText(DebugHelper.curActivity,
                        res.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 30, 0, 0);

        ll.addView(editMethod, params);
        ll.addView(editFunc, params);
        ll.addView(etCallBack, params);
        ll.addView(etParams, params);

        params.setMargins(0, 60, 0, 0);
        ll.addView(action, params);
        ll.addView(tvResponse, params);

        setContentView(ll);
    }


    /*
     methodName不可能为空
     */
    private Object invokeJavaMethod(String methodName, String funcName, String callbackId, String params) {
        DebugWebJSObject jsObject = new DebugWebJSObject(null, this, null);
        Class clazz = DebugWebJSObject.class;
        Object resultObj = null;
        try {
            Method method = null;
            if (!TextUtils.isEmpty(funcName) && !TextUtils.isEmpty(callbackId) && !TextUtils.isEmpty(params)) {
                // 三个参数 nativeBridge
                method = clazz.getMethod(methodName, String.class, String.class, String.class);
                method.setAccessible(true);
                resultObj = method.invoke(jsObject, funcName, callbackId, params);
            } else if (!TextUtils.isEmpty(callbackId) && !TextUtils.isEmpty(params)) {
                // 两个参数
                method = clazz.getMethod(methodName, String.class, String.class);
                method.setAccessible(true);
                resultObj = method.invoke(jsObject, callbackId, params);
            } else if (TextUtils.isEmpty(funcName) && TextUtils.isEmpty(callbackId) && TextUtils.isEmpty(params)) {
                // 无参数
                method = clazz.getMethod(methodName);
                method.setAccessible(true);
                resultObj = method.invoke(jsObject);
            } else {//一个参数
                method = clazz.getMethod(methodName, String.class);
                method.setAccessible(true);
                resultObj = method.invoke(jsObject, !TextUtils.isEmpty(params) ? params : callbackId);
            }
            return resultObj;
        } catch (Exception e) {
            e.printStackTrace();
            return "Exception";
        }
    }

    public void resultForCallback(String callbackId, String methodContent) {
        tvResponse.setText("callbackId:" + callbackId + "\nmethodContent:" + methodContent);
    }
}