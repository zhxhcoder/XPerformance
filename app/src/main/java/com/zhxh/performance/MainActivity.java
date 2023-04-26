package com.zhxh.performance;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.zhxh.performance.support.DebugHelper;

/*
 * Created by zhxh on 2023/4/19
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tvTime = findViewById(R.id.tvTime);
        tvTime.setText("启动时间：" + (DebugHelper.endTime - DebugHelper.startTime) + "毫秒");

        findViewById(R.id.llLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: 父布局");
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                Log.d(TAG, "onClick: 按钮2");
            }
        });
        findViewById(R.id.button3).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.d(TAG, "onClick: 长按按钮3");
                return false;
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        DebugHelper.endTime = System.currentTimeMillis();
    }

    public void onClick(View view) {
        Log.d(TAG, "onClick: 按钮1");
    }
}
