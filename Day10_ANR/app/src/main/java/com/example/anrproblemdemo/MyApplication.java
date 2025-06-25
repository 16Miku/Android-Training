package com.example.anrproblemdemo;



import android.app.Application;
import android.content.Intent;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.github.anrwatchdog.ANRWatchDog;
import com.github.anrwatchdog.ANRError; // 导入 ANRError 类

import java.io.PrintWriter;
import java.io.StringWriter;

public class MyApplication extends Application {

    private static final String TAG = "MyApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "MyApplication onCreate: Initializing ANRWatchDog.");

        // 初始化并启动 ANR-WatchDog
        new ANRWatchDog(2000)
                .setANRListener(new ANRWatchDog.ANRListener() {
                    @Override
                    public void onAppNotResponding(ANRError anrError) {
                        // 当检测到ANR时，这个回调会被触发
                        Log.e(TAG, "ANR detected by ANR-WatchDog!", anrError); // 打印ANR堆栈，传入 anrError

                        // 新增：将ANR堆栈信息发送给MainActivity
                        Intent  intent = new Intent(MainActivity.ACTION_ANR_DETECTED);

                        String fullStackTrace = getStackTraceString( anrError );
                        // 重点修改：将 anrError 的完整堆栈信息转换为字符串!


                        intent.putExtra( MainActivity.EXTRA_ANR_STACK_TRACE, fullStackTrace );
                        // 将完整的堆栈信息字符串放入 Intent 的额外数据中


                        LocalBroadcastManager.getInstance(MyApplication.this).sendBroadcast( intent );
                        // 通过 LocalBroadcastManager 发送这个 Intent。由于 MainActivity 已经注册了监听器，它将收到这个广播。

                    }
                })
                .setANRInterceptor(new ANRWatchDog.ANRInterceptor() {
                    @Override
                    public long intercept(long duration) {
                        // 拦截器，在ANRListener被调用之前触发
                        // 可以根据卡顿持续时间决定是否报告ANR
                        Log.w(TAG, "Main thread has been blocked for " + duration + " ms. Intercepting ANR.");
                        // 返回0表示立即报告ANR，返回大于0表示延迟报告，返回-1表示不报告
                        // 比如，可以设置只有卡顿超过2000ms才真正报告ANR
                        return duration > 2000 ? 0 : duration;
                    }
                })
                .setIgnoreDebugger(true)
                .setReportMainThreadOnly()
                .start();
    }


    /**
     * 将 Throwable 的完整堆栈信息转换为字符串。
     * 这个方法会遍历 Throwable 的 'Caused by' 链，将所有堆栈信息收集到一个字符串中。
     *
     * @param throwable 发生的异常或错误（例如 ANRError）
     * @return 包含完整堆栈信息的字符串
     */
    private String getStackTraceString( Throwable throwable ) {


        StringWriter sw = new StringWriter();
        // 创建一个 StringWriter，用于在内存中收集字符串

        PrintWriter pw = new PrintWriter(sw);
        // 创建一个 PrintWriter，将其连接到 StringWriter

        throwable.printStackTrace(pw);
        // 调用 Throwable 的 printStackTrace 方法，将完整的堆栈信息写入 PrintWriter

        return sw.toString();
        // 返回 StringWriter 中收集到的字符串，即完整的堆栈信息


    }



}
