package com.example.appdemo;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

// LoginActivity 类继承自 AppCompatActivity，用于处理用户登录逻辑
public class LoginActivity extends AppCompatActivity {

    private EditText etUsername; // 用户名输入框
    private EditText etPassword; // 密码输入框
    private Button btnLogin;     // 登录按钮
    private TextView tvRegisterLink; // 注册链接文本

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置Activity的布局文件为 activity_login.xml
        setContentView(R.layout.activity_login);

        // 初始化视图组件
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        tvRegisterLink = findViewById(R.id.tv_register_link);

        // 设置登录按钮的点击事件监听器
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取用户输入的用户名和密码
                String username = etUsername.getText().toString().trim(); // trim() 去除首尾空格
                String password = etPassword.getText().toString().trim();

                // 简单的输入校验
                if (username.isEmpty()) {
                    // 如果用户名为空，显示提示信息
                    Toast.makeText(LoginActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                    return; // 停止执行后续代码
                }

                if (password.isEmpty()) {
                    // 如果密码为空，显示提示信息
                    Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return; // 停止执行后续代码
                }

                // TODO: 在这里添加实际的登录逻辑，例如与后端服务器通信进行身份验证
                // 目前我们只做简单的模拟登录判断

                // 模拟登录成功条件（例如：用户名和密码都为 "admin"）
                if ("admin".equals(username) && "123456".equals(password)) {
                    // 登录成功
                    Toast.makeText(LoginActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
                    // 跳转到主界面 MainActivity
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish(); // 结束当前登录Activity，防止用户按返回键返回登录页
                } else {
                    // 登录失败
                    Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 设置注册链接文本的点击事件监听器
        tvRegisterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 点击注册链接，跳转到注册页面 RegisterActivity
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                // 登录页面不finish，以便用户注册后可以返回登录页面
            }
        });
    }
}

