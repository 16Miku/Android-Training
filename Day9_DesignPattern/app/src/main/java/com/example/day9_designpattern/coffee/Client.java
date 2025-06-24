package com.example.day9_designpattern.coffee;

import android.util.Log; // 导入 Log

public class Client {

    private static final String TAG = "CoffeeShopClient";

    public static void main(String[] args) {
        // Android 应用通常在 Activity/Fragment 的 onCreate 中执行这些逻辑
        // 这里为了演示设计模式，我们模拟在 main 方法中执行

        Log.d(TAG, "--- 咖啡店订单系统启动 ---");

        // 1. 单例模式：获取订单管理器
        OrderManager orderManager = OrderManager.getInstance();
        Log.d(TAG, "OrderManager (Singleton) retrieved.");

        // 2. 观察者模式：注册顾客和厨房观察者
        CustomerObserver customer1 = new CustomerObserver("迪迦");
        CustomerObserver customer2 = new CustomerObserver("戴拿");
        KitchenObserver kitchen = new KitchenObserver();

        orderManager.registerObserver(customer1);
        orderManager.registerObserver(customer2);
        orderManager.registerObserver(kitchen);
        Log.d(TAG, "Observers (Customers, Kitchen) registered.");

        // --- 订单流程开始 ---

        // 3. 工厂模式：创建咖啡
        Log.d(TAG, "\n--- 创建咖啡 ---");
        CoffeeFactory espressoFactory = CoffeeFactory.getFactory("espresso");
        Coffee espresso = null;
        if (espressoFactory != null) {
            espresso = espressoFactory.createCoffee();
            Log.d(TAG, "Created coffee: " + espresso.getName());
        } else {
            Log.e(TAG, "Failed to create Espresso.");
        }

        CoffeeFactory latteFactory = CoffeeFactory.getFactory("latte");
        Coffee latte = null;
        if (latteFactory != null) {
            latte = latteFactory.createCoffee();
            Log.d(TAG, "Created coffee: " + latte.getName());
        } else {
            Log.e(TAG, "Failed to create Latte.");
        }

        // 4. 装饰者模式：装饰咖啡
        Log.d(TAG, "\n--- 装饰咖啡 ---");
        // 制作一杯加奶加糖的浓缩咖啡
        Coffee decoratedEspresso = new MilkDecorator(new SugarDecorator(espresso));
        Log.d(TAG, "Decorated coffee: " + decoratedEspresso.getName() + ", Description: " + decoratedEspresso.getDescription() + ", Cost: $" + decoratedEspresso.getCost());
        decoratedEspresso.prepare(); // 准备装饰后的咖啡

        // 制作一杯加奶油的拿铁
        Coffee decoratedLatte = new WhipDecorator(latte);
        Log.d(TAG, "Decorated coffee: " + decoratedLatte.getName() + ", Description: " + decoratedLatte.getDescription() + ", Cost: $" + decoratedLatte.getCost());
        decoratedLatte.prepare(); // 准备装饰后的咖啡

        // 5. 策略模式：选择支付方式并处理支付
        Log.d(TAG, "\n--- 支付流程 ---");
        PaymentProcessor paymentProcessor = new PaymentProcessor(new CashPayment()); // 默认现金支付
        Log.d(TAG, "Initial payment strategy: Cash.");

        double order1Amount = decoratedEspresso.getCost();
        Log.d(TAG, "Order 1 Amount: $" + order1Amount);
        paymentProcessor.processPayment(order1Amount); // 现金支付

        // 运行时切换支付策略
        Log.d(TAG, "Switching payment strategy to Alipay.");
        paymentProcessor.setPaymentStrategy(new AlipayPayment("ALIPAY_USER_123")); // 切换到支付宝
        double order2Amount = decoratedLatte.getCost();
        Log.d(TAG, "Order 2 Amount: $" + order2Amount);
        paymentProcessor.processPayment(order2Amount); // 支付宝支付

        Log.d(TAG, "Switching payment strategy to Credit Card.");
        paymentProcessor.setPaymentStrategy(new CreditCardPayment("1234-5678-9012-3456", "789")); // 切换到信用卡
        double order3Amount = espresso.getCost(); // 未装饰的浓缩咖啡
        Log.d(TAG, "Order 3 Amount: $" + order3Amount);
        paymentProcessor.processPayment(order3Amount); // 信用卡支付


        // 6. 观察者模式：添加订单并观察状态变化
        Log.d(TAG, "\n--- 订单状态更新及观察者通知 ---");
        Order order1 = new Order("ORD-001", decoratedEspresso);
        orderManager.addOrder(order1); // 添加订单，观察者会收到“待处理”通知

        // 模拟订单处理流程 (由 KitchenObserver 自动触发状态更新并通知)
        Log.d(TAG, "模拟等待厨房处理订单，观察者将收到状态更新...");

        // 移除一个观察者
        Log.d(TAG, "\n--- 移除观察者 ---");
        orderManager.removeObserver(customer2);
        Log.d(TAG, "Removed customer2 observer.");

        // 添加第二个订单，只有剩余的观察者会收到通知
        Order order2 = new Order("ORD-002", decoratedLatte);
        orderManager.addOrder(order2);

        // 模拟手动更新订单状态 (例如，如果需要人工干预)
        new android.os.Handler(android.os.Looper.getMainLooper()).postDelayed(() -> {
            Log.d(TAG, "\n--- 手动更新订单状态 ---");
            orderManager.updateOrderStatus(order1, "已取消"); // 取消第一个订单
        }, 8000); // 延迟一段时间，让之前的自动流程走完

        Log.d(TAG, "\n--- 订单系统演示结束 ---");
    }
}
