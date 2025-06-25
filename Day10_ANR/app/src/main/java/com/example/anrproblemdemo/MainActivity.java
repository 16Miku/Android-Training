package com.example.anrproblemdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log; // 导入 Log 类
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast; // 导入 Toast 类

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "AnrProblemDemo"; // 用于日志输出的TAG

    public static final String ACTION_ANR_DETECTED = "com.example.anrproblemdemo.ANR_DETECTED";
    // 定义一个常量，作为 LocalBroadcastManager 的 Action

    public static final String EXTRA_ANR_STACK_TRACE = "anr_stack_trace";


    private TextView statusTextView; // 显示状态的文本视图
    private Button anrButton; // 触发ANR的按钮
    private Button normalButton; // 测试UI响应的普通按钮

    private TextView anrStackTraceTextView; // anr堆栈信息显示视图



    private BroadcastReceiver anrReceiver = new BroadcastReceiver() {
        //用于接收 ANR 堆栈信息的广播接收器

        @Override
        public void onReceive(Context context, Intent intent) {


            String stackTrace = intent.getStringExtra( EXTRA_ANR_STACK_TRACE );

            if( stackTrace != null ) {

                anrStackTraceTextView.setText( stackTrace );
                // 更新UI显示堆栈

                statusTextView.setText("ANR已被检测到并显示堆栈信息");

                Toast.makeText(MainActivity.this,"ANR已被检测到！", Toast.LENGTH_LONG).show();

                Log.d(TAG, "ANR已被检测到并显示堆栈信息");


            }

        }
    };








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // 设置布局文件

        // 绑定UI组件
        statusTextView = findViewById(R.id.status_text_view);
        anrButton = findViewById(R.id.anr_button);
        normalButton = findViewById(R.id.normal_button);

        anrStackTraceTextView = findViewById(R.id.anr_stack_trace_text_view);



        // 设置触发ANR按钮的点击事件监听器
        anrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statusTextView.setText("正在模拟ANR，主线程被阻塞..."); // 更新状态提示
                Log.d(TAG, "ANR button clicked, starting heavy task on main thread."); // 打印日志

                // 模拟一个非常耗时的操作，这将导致ANR
                simulateHeavyTaskOnMainThread();

                // 这行代码可能在ANR对话框弹出后才执行，或者根本不执行
                statusTextView.setText("ANR模拟任务完成 (如果没崩溃)");
                Toast.makeText(MainActivity.this, "ANR模拟任务完成 (如果没崩溃)", Toast.LENGTH_SHORT).show();
            }
        });

        // 设置正常按钮的点击事件监听器，用于测试UI是否响应
        normalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "正常按钮被点击，UI响应正常", Toast.LENGTH_SHORT).show(); // 弹出Toast
                Log.d(TAG, "Normal button clicked."); // 打印日志
            }
        });


        // 注册 LocalBroadcastReceiver
        IntentFilter filter = new IntentFilter(ACTION_ANR_DETECTED);

        LocalBroadcastManager.getInstance(this).registerReceiver(anrReceiver, filter);

        Log.d(TAG, "ANR广播接收器已注册");


    }

    /**
     * 模拟在主线程执行一个非常耗时的操作，该操作会阻塞UI线程，从而导致ANR。
     */
    private void simulateHeavyTaskOnMainThread() {
        long startTime = System.currentTimeMillis(); // 记录开始时间
        // 这是一个计算密集型的循环，执行次数非常大，确保耗时超过ANR阈值（5秒）
        for (long i = 0; i < 10_000_000_000L; i++) { // 100亿次循环，这会非常耗时
            double result = Math.sqrt(i); // 进行一些计算，增加CPU消耗
            // 避免频繁打印日志，以免影响模拟效果
            if (i % 1_000_000_000L == 0) { // 每10亿次循环打印一次进度
                Log.d(TAG, "Heavy task progress: " + i);
            }
        }
        long endTime = System.currentTimeMillis(); // 记录结束时间
        Log.d(TAG, "Heavy task on main thread completed in " + (endTime - startTime) + " ms."); // 打印总耗时
    }



    @Override
    protected void onDestroy() {



        super.onDestroy();

        // 解除注册 LocalBroadcastReceiver，防止内存泄漏!
        LocalBroadcastManager.getInstance(this).unregisterReceiver(anrReceiver);

        Log.d(TAG, "ANR广播接收器已解除注册");


    }

}
