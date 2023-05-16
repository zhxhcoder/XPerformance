package com.zhxh.performance.support;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

import java.util.logging.Handler;

/*
 * Created by zhxh on 2023/5/15
 */
public class DebugWebJSObject {
    public DebugWebJSObject(WebView webView, Context ctx, Handler handler) {

    }

    @JavascriptInterface
    public String nativeBridge(String funcName, String callbackId, String params) {
        Toast.makeText(DebugHelper.curActivity, funcName + "\n" +
                callbackId + "\n" +
                params, Toast.LENGTH_LONG).show();
        return "nativeBridge-success";
    }

    @JavascriptInterface
    public String getToken() {
        return "666666666666666666666666666666666666666";
    }

    @JavascriptInterface
    public void setToken(String token) {
        Toast.makeText(DebugHelper.curActivity, token, Toast.LENGTH_LONG).show();
    }

    @JavascriptInterface
    public void open() {
        Toast.makeText(DebugHelper.curActivity, "open", Toast.LENGTH_LONG).show();
    }

}
