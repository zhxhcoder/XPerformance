package com.zhxh.performance.support;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/*
 * Created by zhxh on 2023/5/15
 * 模拟h5调用逻辑
 */
public class DebugWebActivity extends AppCompatActivity {
    TextView tvResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);

        tvResponse = new TextView(this);

        //方法名
        EditText etFuncName = new EditText(this);
        etFuncName.setText("nativeBridge");

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
                Toast.makeText(DebugWebActivity.this,
                        etFuncName.getText().toString() + "\n" +
                                etCallBack.getText().toString() + "\n" +
                                etParams.getText().toString(), Toast.LENGTH_LONG).show();
            }
        });

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 30, 0, 0);

        ll.addView(etFuncName, params);
        ll.addView(etCallBack, params);
        ll.addView(etParams, params);

        params.setMargins(0, 60, 0, 0);
        ll.addView(action, params);
        ll.addView(tvResponse, params);

        setContentView(ll);
    }

    public void resultForCallback(String callbackId, String methodContent) {
        tvResponse.setText("callbackId:" + callbackId + "\nmethodContent:" + methodContent);
    }
}
