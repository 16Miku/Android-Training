package com.example.day4_calculator;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ServiceConnection;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity implements ButtonAdapter.OnButtonClickListener {

    private static final String TAG = "CalculatorClient";

    private ICalculator calculatorService;

    private boolean isBound = false;


    private TextView displayTextView;

    private Button bindServiceButton, unbindServiceButton;

    private StringBuilder currentNumber = new StringBuilder("0");

    private String operator = "";

    private int operand1 = 0;

    private boolean newOperation = true;


    // ServiceConnection 用于监听 Service 的连接状态
    private ServiceConnection serviceConnection = new ServiceConnection() {

        // 当 Service 连接成功时调用
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            Log.d(TAG, "onServiceConnected: Service connected"); // 打印日志

            // 将 IBinder 对象转换为 AIDL 接口类型 (Proxy 对象)
            calculatorService = ICalculator.Stub.asInterface(service);

            isBound = true; // 设置绑定状态为 true

            Toast.makeText(MainActivity.this, "Service Connected", Toast.LENGTH_SHORT).show(); // 提示用户

            bindServiceButton.setEnabled(false); // 禁用绑定按钮

            unbindServiceButton.setEnabled(true); // 启用解绑按钮

            // 连接成功后，可以启用计算器按钮（RecyclerView 按钮）
            setCalculatorButtonsEnabled(true);
        }

        // 当 Service 连接断开时调用 (Service 崩溃或被系统杀死时调用，客户端主动解绑不会调用)
        @Override
        public void onServiceDisconnected(ComponentName name) {

            Log.d(TAG, "onServiceDisconnected: Service disconnected"); // 打印日志

            calculatorService = null; // 清空 Service 引用

            isBound = false; // 设置绑定状态为 false

            Toast.makeText(MainActivity.this, "Service Disconnected", Toast.LENGTH_SHORT).show(); // 提示用户

            bindServiceButton.setEnabled(true); // 启用绑定按钮

            unbindServiceButton.setEnabled(false); // 禁用解绑按钮

            // 连接断开后，禁用计算器按钮

            setCalculatorButtonsEnabled(false);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // 初始化视图
        displayTextView = findViewById(R.id.show_text_view);
        bindServiceButton = findViewById(R.id.bindServiceButton);
        unbindServiceButton = findViewById(R.id.unbindServiceButton);

        // 初始化 RecyclerView 按钮
        RecyclerView buttonsRecyclerView = findViewById(R.id.buttons_recycler_view);

        buttonsRecyclerView.setLayoutManager(new GridLayoutManager(this, 4)); // 4 列网格布局

        // 定义按钮文本
        List<String> buttonLabels = Arrays.asList(
                "C", "DEL", "/", "*",
                "7", "8", "9", "-",
                "4", "5", "6", "+",
                "1", "2", "3", "=",
                "0", "."
        );

        // 创建并设置适配器
        ButtonAdapter adapter = new ButtonAdapter(buttonLabels,  this);
        // this 作为 OnButtonClickListener

        buttonsRecyclerView.setAdapter(adapter);

        // 初始状态下禁用计算器按钮，直到 Service 绑定成功
        setCalculatorButtonsEnabled(false);

        // 绑定 Service 按钮点击事件
        bindServiceButton.setOnClickListener(v -> {

            if (!isBound) { // 如果 Service 未绑定

                Log.d(TAG, "onClick: Binding Service..."); // 打印日志

                // 创建 Intent，使用服务端的 Action 来绑定 Service
                Intent intent = new Intent("com.example.day4_calculator.CALCULATOR_SERVICE");

                // 必须设置包名，否则无法找到跨应用的 Service
                intent.setPackage("com.example.day4_calculator");

                // 绑定 Service
                // Context.BIND_AUTO_CREATE 标志表示如果 Service 尚未创建，则创建它
                bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);

            } else {

                Toast.makeText(MainActivity.this, "Service already bound", Toast.LENGTH_SHORT).show();

            }
        });

        // 解绑 Service 按钮点击事件
        unbindServiceButton.setOnClickListener(v -> {

            if (isBound) { // 如果 Service 已绑定

                Log.d(TAG, "onClick: Unbinding Service..."); // 打印日志

                unbindService(serviceConnection); // 解绑 Service

                isBound = false; // 更新绑定状态

                calculatorService = null; // 清空 Service 引用

                Toast.makeText(MainActivity.this, "Service Unbound", Toast.LENGTH_SHORT).show();

                bindServiceButton.setEnabled(true); // 启用绑定按钮

                unbindServiceButton.setEnabled(false); // 禁用解绑按钮

                setCalculatorButtonsEnabled(false); // 禁用计算器按钮

                resetCalculator(); // 重置计算器状态

            } else {

                Toast.makeText(MainActivity.this, "Service not bound", Toast.LENGTH_SHORT).show();

            }
        });




    }



    // 实现 ButtonAdapter.OnButtonClickListener 接口的方法
    @Override
    public void onButtonClick(String label) {

        if (!isBound || calculatorService == null) {

            Toast.makeText(this, "Service not connected. Please bind first.", Toast.LENGTH_SHORT).show();

            return;
        }

        switch (label) {
            case "C": // 清除
                resetCalculator();
                break;
            case "DEL": // 删除
                if (currentNumber.length() > 1) {

                    currentNumber.deleteCharAt(currentNumber.length() - 1);

                } else {

                    currentNumber.replace(0, currentNumber.length(), "0");

                }
                displayTextView.setText(currentNumber.toString());

                break;
            case "+":
            case "-":
            case "*":
            case "/":
                handleOperator(label);
                break;
            case "=":
                calculateResult();
                break;
            default: // 数字键
                handleNumber(label);
                break;
        }
    }

    // 处理数字键输入
    private void handleNumber(String number) {

        if (newOperation) {

            currentNumber.replace(0, currentNumber.length(), number);

            newOperation = false;

        } else {

            if (currentNumber.toString().equals("0") && !number.equals(".")) { // 避免多余的0

                currentNumber.replace(0, currentNumber.length(), number);

            } else {

                currentNumber.append(number);

            }
        }

        displayTextView.setText(currentNumber.toString());

    }

    // 处理操作符输入
    private void handleOperator(String op) {

        if (!operator.isEmpty() && !newOperation) { // 如果已经有操作符且不是新操作，先计算上一个结果

            calculateResult();

        }

        operand1 = Integer.parseInt(currentNumber.toString());

        operator = op;

        newOperation = true; // 准备输入下一个操作数

        displayTextView.setText(String.valueOf(operand1) + " " + operator); // 显示当前操作

    }

    // 执行计算
    private void calculateResult() {

        if (operator.isEmpty() || newOperation) { // 没有操作符或只输入了一个数

            return;

        }

        int operand2 = Integer.parseInt(currentNumber.toString());

        int result = 0;

        boolean error = false;

        try {
            switch (operator) {

                case "+":
                    result = calculatorService.add(operand1, operand2);
                    break;
                case "-":
                    result = calculatorService.subtract(operand1, operand2);
                    break;
                case "*":
                    result = calculatorService.multiply(operand1, operand2);
                    break;
                case "/":
                    if (operand2 == 0) {

                        Toast.makeText(this, "Cannot divide by zero!", Toast.LENGTH_SHORT).show();
                        error = true;

                    } else {

                        result = calculatorService.divide(operand1, operand2);

                    }
                    break;
            }
        } catch (RemoteException e) {

            Log.e(TAG, "RemoteException during calculation: " + e.getMessage());

            Toast.makeText(this, "Service error: " + e.getMessage(), Toast.LENGTH_LONG).show();

            error = true;

        } catch (NumberFormatException e) {

            Log.e(TAG, "NumberFormatException: " + e.getMessage());

            Toast.makeText(this, "Invalid number format.", Toast.LENGTH_SHORT).show();

            error = true;

        }

        if (!error) {

            displayTextView.setText(String.valueOf(result));

            currentNumber.replace(0, currentNumber.length(), String.valueOf(result));

            operand1 = result; // 将结果作为下一个操作的第一个操作数

        } else {

            resetCalculator(); // 发生错误时重置

        }
        operator = ""; // 清除操作符
        newOperation = true; // 准备开始新的操作

    }

    // 重置计算器状态
    private void resetCalculator() {

        currentNumber.replace(0, currentNumber.length(), "0");

        operator = "";

        operand1 = 0;

        newOperation = true;

        displayTextView.setText("0");

    }

    // 启用/禁用计算器按钮
    private void setCalculatorButtonsEnabled(boolean enabled) {

        RecyclerView buttonsRecyclerView = findViewById(R.id.buttons_recycler_view);

        for (int i = 0; i < buttonsRecyclerView.getChildCount(); i++) {

            View view = buttonsRecyclerView.getChildAt(i);

            Button button = view.findViewById(R.id.item_button);

            if (button != null) {

                button.setEnabled(enabled);

            }
        }
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