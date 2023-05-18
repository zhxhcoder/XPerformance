package com.zhxh.performance;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

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

    private void exeJsFunc(String callbackId, String methodContent) {
        String loadMethodUrl = "javascript:" + "resultForCallback" + "('" + callbackId + "'," + methodContent + ")";
        wb.loadUrl(loadMethodUrl);
    }
}