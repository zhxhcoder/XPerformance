package com.zhxh.performance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.zhxh.performance.support.StringUtil;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EditText name;
        EditText pwd;

        name = findViewById(R.id.name);
        pwd = findViewById(R.id.pwd);

        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //移除监听
                name.removeTextChangedListener(this);
                name.setText(StringUtil.replace("(?<=^.).", "*", s.toString()));
                name.setSelection(s.length());
                //重新监听
                name.addTextChangedListener(this);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //账号为abcd,密码为123
                Toast.makeText(LoginActivity.this, name.getText().toString() + ":" + pwd.getText().toString(), Toast.LENGTH_LONG).show();
                startActivity(new Intent(LoginActivity.this, DashActivity.class));
            }
        });
    }
}