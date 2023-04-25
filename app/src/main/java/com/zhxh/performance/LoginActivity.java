package com.zhxh.performance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EditText name;
        EditText pwd;

        name = findViewById(R.id.name);
        pwd = findViewById(R.id.pwd);

        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, name.getText().toString() + ":" + pwd.getText().toString(), Toast.LENGTH_LONG).show();
                startActivity(new Intent(LoginActivity.this, LoginActivity.class));
            }
        });
    }
}