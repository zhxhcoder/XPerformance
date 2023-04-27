package com.zhxh.performance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.zhxh.performance.support.StringUtil;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    String strName = "";

    Map<Integer, CharSequence> nameMap = new HashMap<>();
    TextWatcher watcher;
    EditText name;
    EditText pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        name = findViewById(R.id.name);
        pwd = findViewById(R.id.pwd);

        watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d("TextWatcher", "beforeTextChanged:" + s.toString() + " start:" + start + " count:" + count + " after:" + after);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //移除监听
                Log.d("TextWatcher", "onTextChanged:" + s.toString() + " start:" + start + " before:" + before + " count:" + count);
                name.removeTextChangedListener(this);
                name.setText(StringUtil.replace("(?<=^.{1,2}).", "*", s.toString()));
                name.setSelection(s.length());
                //重新监听
                name.addTextChangedListener(this);
            }

            @Override
            public void afterTextChanged(Editable s) {
                strName = s.toString();

                //s.length 大于 map容量时，则添加
                if (s.length() > nameMap.size()) {
//                    nameMap.put()
                }

                Log.d("TextWatcher", "afterTextChanged:" + s.toString());
            }
        };

        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //成功之后，然后再让 name复原明文
                startActivity(new Intent(LoginActivity.this, DashActivity.class));
            }
        });

//        name.addTextChangedListener(watcher);

        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //成功之后，然后再让 name复原明文
                Toast.makeText(LoginActivity.this, name.getText().toString() + ":" + pwd.getText().toString(), Toast.LENGTH_LONG).show();

                startActivity(new Intent(LoginActivity.this, DashActivity.class));
                finish();
            }
        });
    }

    @Override
    protected void onStop() {
        name.setText("onStopBefore" + System.currentTimeMillis());
        super.onStop();
//        name.setText("onStopAfter" + System.currentTimeMillis());
    }

    @Override
    protected void onDestroy() {
        //密码保险箱只是在onDestroy事件中调用
//        name.removeTextChangedListener(watcher);
        name.setText("onDestroyBefore" + System.currentTimeMillis());
        super.onDestroy();
        name.setText("onDestroyAfter" + System.currentTimeMillis());

    }

    InputFilter mInputFilter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            //source 新输入的字符串
            //start 新输入的字符串起始下标，一般为0
            //end 新输入的字符串终点下标，一般为source长度-1
            //dest 输入之前文本框内容
            //dstart 原内容起始坐标，一般为0
            //dend 原内容终点坐标，一般为dest长度-1
            if (source.toString().equals("芝麻粒儿")) {
                //此示例：输入的如果是【芝麻粒儿】，则直接返回null，页面上表现为不显示
                return null;
            }
            Log.e("TAG", "filter: 自定义的过滤规则");
            return null;
        }
    };
}