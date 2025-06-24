package com.example.day9_designpattern;

import android.os.Bundle;
import android.util.Log; // 导入 Log
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.day9_designpattern.coffee.AlipayPayment;
import com.example.day9_designpattern.coffee.CashPayment;
import com.example.day9_designpattern.coffee.Coffee;
import com.example.day9_designpattern.coffee.CoffeeFactory;
import com.example.day9_designpattern.coffee.CreditCardPayment;
import com.example.day9_designpattern.coffee.CustomerObserver;
import com.example.day9_designpattern.coffee.KitchenObserver;
import com.example.day9_designpattern.coffee.MilkDecorator;
import com.example.day9_designpattern.coffee.Order;
import com.example.day9_designpattern.coffee.OrderManager;
import com.example.day9_designpattern.coffee.PaymentProcessor;
import com.example.day9_designpattern.coffee.SugarDecorator;
import com.example.day9_designpattern.coffee.WhipDecorator;


public class DesignPatternDemoActivity extends AppCompatActivity {

    private static final String TAG = "DesignPatternDemo";
    private TextView logOutputTextView; // 用于在UI上显示日志
    private StringBuilder logBuilder = new StringBuilder(); // 用于收集日志

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design_pattern_demo); // 假设有这个布局文件

        logOutputTextView = findViewById(R.id.log_output_text_view);
        Button runDemoButton = findViewById(R.id.run_demo_button);

        // 设置一个自定义的Logcat输出，同时显示在TextView上
        // 注意：这种方式仅用于演示，实际生产环境通常依赖Logcat和崩溃收集平台
        System.setProperty("log.tag." + TAG, "VERBOSE"); // 设置Logcat的TAG可见级别
        android.util.Log.d(TAG, "Activity onCreate: Starting demo setup.");

        runDemoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logBuilder.setLength(0); // 清空之前的日志
                logOutputTextView.setText(""); // 清空UI显示
                runDesignPatternDemo(); // 触发设计模式演示逻辑
            }
        });

        // 初始显示一些提示
        appendToLogOutput("点击 '运行演示' 按钮开始观察设计模式效果...");
    }

    private void appendToLogOutput(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, message); // 也输出到Logcat
                logBuilder.append(message).append("\n");
                logOutputTextView.setText(logBuilder.toString());
            }
        });
    }

    /**
     * 封装 Client.java 中的设计模式演示逻辑
     */
    private void runDesignPatternDemo() {
        appendToLogOutput("--- 咖啡店订单系统启动 ---");

        // 1. 单例模式：获取订单管理器
        OrderManager orderManager = OrderManager.getInstance();
        appendToLogOutput("OrderManager (Singleton) retrieved.");

        // 2. 观察者模式：注册顾客和厨房观察者
        CustomerObserver customer1 = new CustomerObserver("迪迦");
        CustomerObserver customer2 = new CustomerObserver("戴拿");
        KitchenObserver kitchen = new KitchenObserver();

        orderManager.registerObserver(customer1);
        orderManager.registerObserver(customer2);
        orderManager.registerObserver(kitchen);
        appendToLogOutput("Observers (Customers, Kitchen) registered.");

        // --- 订单流程开始 ---

        // 3. 工厂模式：创建咖啡
        appendToLogOutput("\n--- 创建咖啡 ---");
        CoffeeFactory espressoFactory = CoffeeFactory.getFactory("espresso");
        Coffee espresso = null;
        if (espressoFactory != null) {
            espresso = espressoFactory.createCoffee();
            appendToLogOutput("Created coffee: " + espresso.getName());
        } else {
            appendToLogOutput("Failed to create Espresso.");
        }

        CoffeeFactory latteFactory = CoffeeFactory.getFactory("latte");
        Coffee latte = null;
        if (latteFactory != null) {
            latte = latteFactory.createCoffee();
            appendToLogOutput("Created coffee: " + latte.getName());
        } else {
            appendToLogOutput("Failed to create Latte.");
        }

        // 4. 装饰者模式：装饰咖啡
        appendToLogOutput("\n--- 装饰咖啡 ---");
        // 制作一杯加奶加糖的浓缩咖啡
        Coffee decoratedEspresso = new MilkDecorator(new SugarDecorator(espresso));
        appendToLogOutput("Decorated coffee: " + decoratedEspresso.getName() + ", Description: " + decoratedEspresso.getDescription() + ", Cost: $" + decoratedEspresso.getCost());
        decoratedEspresso.prepare(); // 准备装饰后的咖啡

        // 制作一杯加奶油的拿铁
        Coffee decoratedLatte = new WhipDecorator(latte);
        appendToLogOutput("Decorated coffee: " + decoratedLatte.getName() + ", Description: " + decoratedLatte.getDescription() + ", Cost: $" + decoratedLatte.getCost());
        decoratedLatte.prepare(); // 准备装饰后的咖啡

        // 5. 策略模式：选择支付方式并处理支付
        appendToLogOutput("\n--- 支付流程 ---");
        PaymentProcessor paymentProcessor = new PaymentProcessor(new CashPayment()); // 默认现金支付
        appendToLogOutput("Initial payment strategy: Cash.");

        double order1Amount = decoratedEspresso.getCost();
        appendToLogOutput("Order 1 Amount: $" + order1Amount);
        paymentProcessor.processPayment(order1Amount); // 现金支付

        // 运行时切换支付策略
        appendToLogOutput("Switching payment strategy to Alipay.");
        paymentProcessor.setPaymentStrategy(new AlipayPayment("ALIPAY_USER_123")); // 切换到支付宝
        double order2Amount = decoratedLatte.getCost();
        appendToLogOutput("Order 2 Amount: $" + order2Amount);
        paymentProcessor.processPayment(order2Amount); // 支付宝支付

        appendToLogOutput("Switching payment strategy to Credit Card.");
        paymentProcessor.setPaymentStrategy(new CreditCardPayment("1234-5678-9012-3456", "789")); // 切换到信用卡
        double order3Amount = espresso.getCost(); // 未装饰的浓缩咖啡
        appendToLogOutput("Order 3 Amount: $" + order3Amount);
        paymentProcessor.processPayment(order3Amount); // 信用卡支付


        // 6. 观察者模式：添加订单并观察状态变化
        appendToLogOutput("\n--- 订单状态更新及观察者通知 ---");
        Order order1 = new Order("ORD-001", decoratedEspresso);
        orderManager.addOrder(order1); // 添加订单，观察者会收到“待处理”通知

        appendToLogOutput("模拟等待厨房处理订单，观察者将收到状态更新...");

        // 移除一个观察者
        appendToLogOutput("\n--- 移除观察者 ---");
        orderManager.removeObserver(customer2);
        appendToLogOutput("Removed customer2 observer.");

        // 添加第二个订单，只有剩余的观察者会收到通知
        Order order2 = new Order("ORD-002", decoratedLatte);
        orderManager.addOrder(order2);

        // 模拟手动更新订单状态 (例如，如果需要人工干预)
        new android.os.Handler(android.os.Looper.getMainLooper()).postDelayed(() -> {
            appendToLogOutput("\n--- 手动更新订单状态 ---");
            orderManager.updateOrderStatus(order1, "已取消"); // 取消第一个订单
        }, 8000); // 延迟一段时间，让之前的自动流程走完

        appendToLogOutput("\n--- 订单系统演示结束 ---");
    }
}
