package com.example.anrproblemdemo;

import android.app.Application;
import android.util.Log;

import com.github.anrwatchdog.ANRWatchDog;
import com.github.anrwatchdog.ANRError; // 导入 ANRError 类

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

                        // TODO: 在这里可以执行自定义的ANR处理逻辑：
                        // 1. 上传ANR信息到崩溃收集平台 (如 Firebase Crashlytics, Bugly)
                        // Crashlytics.logException(anrError);
                        // 2. 记录到本地文件
                        // 3. 显示一个友好的错误提示 (如果应用尚未完全冻结)
                        // Toast.makeText(getApplicationContext(), "应用卡顿了！", Toast.LENGTH_LONG).show();

                        // 注意：anrError 包含了主线程的完整堆栈信息
                        // ANRError 是 Error 的子类，Error 是 Throwable 的子类，所以可以直接传入 Log.e
                        // 如果不希望应用崩溃，不要在这里重新抛出异常
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
}
