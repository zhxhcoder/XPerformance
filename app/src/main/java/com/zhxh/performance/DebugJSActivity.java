package com.zhxh.performance;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class DebugJSActivity extends AppCompatActivity {

    WebView wb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug_jsactivity);

        wb = findViewById(R.id.wb);

        wb.loadUrl("file:android_asset/www/debug_js_native.html");


    }
}