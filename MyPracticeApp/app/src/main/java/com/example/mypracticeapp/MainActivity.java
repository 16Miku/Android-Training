package com.example.mypracticeapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen; // 导入 SplashScreen API

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 确保在 super.onCreate() 之前调用，以启用 Splash Screen
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // 你的主布局

        // 保持 Splash Screen 可见直到内容加载完成
        // splashScreen.setKeepOnScreenCondition(() -> !isContentReady); // 如果有内容加载逻辑

        // 模拟初始化或跳转逻辑
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
            startActivity(intent);
            finish(); // 结束 MainActivity，防止用户返回到 Splash Screen
        }, 2000); // 延迟 2 秒跳转
    }
}