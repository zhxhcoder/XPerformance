package com.zhxh.performance;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebView;

import java.util.Objects;

public class DebugJSActivity extends AppCompatActivity {
    WebView wb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String url = getIntent().getStringExtra("url");

        wb = new WebView(this);
        setContentView(wb);

        wb.loadUrl(url);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Objects.requireNonNull(this.getSupportActionBar()).getCustomView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exeJsFunc("666666");
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                exeJsFunc("123456");
                exeJsFunc("123", "哈哈哈");
            }
        }, 2000);

    }

    private void exeJsFunc(String callbackId, String methodContent) {
        String loadMethodUrl = "javascript:" + "toJsCallbackTwo" + "('" + callbackId + "'," + methodContent + ");";
        wb.loadUrl(loadMethodUrl);
    }

    private void exeJsFunc(String params) {
        String loadMethodUrl = "javascript:toJsCallback(" + params + ");";
        wb.loadUrl(loadMethodUrl);
    }
}