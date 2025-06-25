package com.example.appdemo;



import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

// SplashActivity 类继承自 AppCompatActivity，用于创建欢迎页面
public class SplashActivity extends AppCompatActivity {

    // 欢迎页面的显示时长（毫秒），这里设置为2秒
    private static final long SPLASH_DISPLAY_LENGTH = 2000; // 2秒

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置Activity的布局文件为 activity_splash.xml
        setContentView(R.layout.activity_splash);

        // 使用Handler和postDelayed方法实现延迟跳转
        // Looper.getMainLooper() 确保Handler在主线程上运行
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                /*
                 * 在延迟结束后执行此Runnable
                 * 创建一个Intent，用于从SplashActivity跳转到MainActivity
                 * MainActivity是您应用的主界面，我们将在后续步骤中完善它
                 */
                Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(mainIntent); // 启动MainActivity

                // 结束当前的SplashActivity，防止用户按返回键回到欢迎页面
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH); // 延迟时间
    }
}
