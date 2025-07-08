package com.example.aidlserver;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName; // 导入 ComponentName
import android.content.Context; // 导入 Context
import android.content.Intent; // 导入 Intent
import android.content.ServiceConnection; // 导入 ServiceConnection
import android.os.IBinder; // 导入 IBinder
import android.os.RemoteException; // 导入 RemoteException
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

// 导入 AIDL 接口，注意包名是服务端的包名
import com.example.aidlserver.IAidlCalculator;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "AIDLClient_MainActivity"; // 日志标签

    private IAidlCalculator calculatorService; // AIDL 接口实例，实际是 Proxy 对象
    private boolean isBound = false; // 标记 Service 是否已绑定

    private EditText editTextNum1, editTextNum2; // 输入数字的 EditText
    private Button buttonAdd, buttonSubtract, buttonBind, buttonUnbind; // 操作按钮
    private TextView textViewResult; // 显示结果的 TextView

    // ServiceConnection 用于监听 Service 的连接状态
    private ServiceConnection serviceConnection = new ServiceConnection() {
        // 当 Service 连接成功时调用
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected: Service connected"); // 打印日志
            // 将 IBinder 对象转换为 AIDL 接口类型 (Proxy 对象)
            calculatorService = IAidlCalculator.Stub.asInterface(service);
            isBound = true; // 设置绑定状态为 true
            Toast.makeText(MainActivity.this, "Service Connected", Toast.LENGTH_SHORT).show(); // 提示用户
            // 连接成功后，启用计算按钮
            buttonAdd.setEnabled(true);
            buttonSubtract.setEnabled(true);
        }

        // 当 Service 连接断开时调用 (Service 崩溃或被系统杀死时调用，客户端主动解绑不会调用)
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected: Service disconnected"); // 打印日志
            calculatorService = null; // 清空 Service 引用
            isBound = false; // 设置绑定状态为 false
            Toast.makeText(MainActivity.this, "Service Disconnected", Toast.LENGTH_SHORT).show(); // 提示用户
            // 连接断开后，禁用计算按钮
            buttonAdd.setEnabled(false);
            buttonSubtract.setEnabled(false);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // 设置布局文件

        // 初始化视图
        editTextNum1 = findViewById(R.id.editTextNum1);
        editTextNum2 = findViewById(R.id.editTextNum2);
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonSubtract = findViewById(R.id.buttonSubtract);
        buttonBind = findViewById(R.id.buttonBind);
        buttonUnbind = findViewById(R.id.buttonUnbind);
        textViewResult = findViewById(R.id.textViewResult);

        // 初始状态下禁用计算按钮
        buttonAdd.setEnabled(false);
        buttonSubtract.setEnabled(false);

        // 绑定 Service 按钮点击事件
        buttonBind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isBound) { // 如果 Service 未绑定
                    Log.d(TAG, "onClick: Binding Service..."); // 打印日志
                    // 创建 Intent，使用服务端的 Action 来绑定 Service
                    Intent intent = new Intent("com.example.aidlserver.CALCULATOR_SERVICE");
                    // 必须设置包名，否则无法找到跨应用的 Service
                    intent.setPackage("com.example.aidlserver");
                    // 绑定 Service
                    // Context.BIND_AUTO_CREATE 标志表示如果 Service 尚未创建，则创建它
                    bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
                } else {
                    Toast.makeText(MainActivity.this, "Service already bound", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 解绑 Service 按钮点击事件
        buttonUnbind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isBound) { // 如果 Service 已绑定
                    Log.d(TAG, "onClick: Unbinding Service..."); // 打印日志
                    unbindService(serviceConnection); // 解绑 Service
                    isBound = false; // 更新绑定状态
                    calculatorService = null; // 清空引用
                    Toast.makeText(MainActivity.this, "Service Unbound", Toast.LENGTH_SHORT).show();
                    // 解绑后禁用计算按钮
                    buttonAdd.setEnabled(false);
                    buttonSubtract.setEnabled(false);
                    textViewResult.setText("Result: N/A"); // 清空结果显示
                } else {
                    Toast.makeText(MainActivity.this, "Service not bound", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 加法按钮点击事件
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isBound && calculatorService != null) {
                    try {
                        int num1 = Integer.parseInt(editTextNum1.getText().toString());
                        int num2 = Integer.parseInt(editTextNum2.getText().toString());
                        // 调用远程 Service 的 add 方法
                        int result = calculatorService.add(num1, num2);
                        textViewResult.setText("Result: " + result); // 显示结果
                    } catch (NumberFormatException e) {
                        Toast.makeText(MainActivity.this, "Please enter valid numbers", Toast.LENGTH_SHORT).show();
                    } catch (RemoteException e) {
                        // 处理远程调用异常，例如 Service 崩溃或连接断开
                        Log.e(TAG, "RemoteException during add: " + e.getMessage());
                        Toast.makeText(MainActivity.this, "Service error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        // 可以在这里尝试重新绑定 Service
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Service not connected", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 减法按钮点击事件
        buttonSubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isBound && calculatorService != null) {
                    try {
                        int num1 = Integer.parseInt(editTextNum1.getText().toString());
                        int num2 = Integer.parseInt(editTextNum2.getText().toString());
                        // 调用远程 Service 的 subtract 方法
                        int result = calculatorService.subtract(num1, num2);
                        textViewResult.setText("Result: " + result); // 显示结果
                    } catch (NumberFormatException e) {
                        Toast.makeText(MainActivity.this, "Please enter valid numbers", Toast.LENGTH_SHORT).show();
                    } catch (RemoteException e) {
                        // 处理远程调用异常
                        Log.e(TAG, "RemoteException during subtract: " + e.getMessage());
                        Toast.makeText(MainActivity.this, "Service error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Service not connected", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // 在 Activity 销毁时解绑 Service，避免内存泄漏
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isBound) {
            unbindService(serviceConnection); // 解绑 Service
            isBound = false;
            Log.d(TAG, "onDestroy: Service unbound in onDestroy"); // 打印日志
        }
    }
}