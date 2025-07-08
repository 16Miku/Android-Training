package com.example.aidlserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException; // 导入 RemoteException
import android.util.Log;

public class CalculatorService extends Service {

    private static final String TAG = "CalculatorService"; // 日志标签

    // 实现 AIDL 接口的 Stub 内部类
    // 这个 Stub 对象是 Service 提供给客户端的接口实现
    private final IAidlCalculator.Stub binder = new IAidlCalculator.Stub() {
        // 实现 IAidlCalculator.aidl 中定义的 add 方法
        @Override
        public int add(int a, int b) throws RemoteException {
            Log.d(TAG, "add() called with: a = " + a + ", b = " + b); // 打印日志
            return a + b; // 返回相加结果
        }

        // 实现 IAidlCalculator.aidl 中定义的 subtract 方法
        @Override
        public int subtract(int a, int b) throws RemoteException {
            Log.d(TAG, "subtract() called with: a = " + a + ", b = " + b); // 打印日志
            return a - b; // 返回相减结果
        }
    };

    // Service 第一次创建时调用
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: Service created"); // 打印日志
    }

    // 当客户端调用 bindService() 绑定 Service 时调用
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: Service bound, returning binder"); // 打印日志
        // 返回我们实现的 Stub 对象
        return binder;
    }

    // 当所有客户端都解除绑定时调用
    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind: All clients unbound"); // 打印日志
        return super.onUnbind(intent); // 默认返回 false，表示下次绑定会重新调用 onBind()
    }

    // Service 销毁时调用
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: Service destroyed"); // 打印日志
    }
}
