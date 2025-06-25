package com.example.appdemo;



import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

// RegisterActivity 类继承自 AppCompatActivity，用于处理用户注册逻辑
public class RegisterActivity extends AppCompatActivity {

    private EditText etUsername;       // 用户名输入框
    private EditText etPassword;       // 密码输入框
    private EditText etConfirmPassword; // 确认密码输入框
    private Button btnRegister;        // 注册按钮

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置Activity的布局文件为 activity_register.xml
        setContentView(R.layout.activity_register);

        // 初始化视图组件
        etUsername = findViewById(R.id.et_register_username);
        etPassword = findViewById(R.id.et_register_password);
        etConfirmPassword = findViewById(R.id.et_confirm_password);
        btnRegister = findViewById(R.id.btn_register);

        // 设置注册按钮的点击事件监听器
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取用户输入的用户名、密码和确认密码
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String confirmPassword = etConfirmPassword.getText().toString().trim();

                // 注册输入校验
                if (username.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (confirmPassword.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "请确认密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!password.equals(confirmPassword)) {
                    // 判断两次输入的密码是否一致
                    Toast.makeText(RegisterActivity.this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
                    return;
                }
                // TODO: 可以添加更复杂的密码强度校验，例如长度、包含字符类型等

                // TODO: 在这里添加实际的注册逻辑，例如将用户信息发送到后端服务器
                // 目前我们只做简单的模拟注册成功

                // 模拟注册成功
                Toast.makeText(RegisterActivity.this, "注册成功！请登录。", Toast.LENGTH_SHORT).show();
                // 注册成功后，通常会返回到登录页面
                finish(); // 结束当前注册Activity，返回到上一个Activity（即LoginActivity）
            }
        });
    }
}




