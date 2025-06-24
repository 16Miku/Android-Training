package com.example.day9_designpattern.coffee;

import android.util.Log;

public class KitchenObserver implements OrderObserver {
    private static final String TAG = "KitchenObserver";

    @Override
    public void update(Order order) {
        Log.d(TAG, "厨房收到订单更新: 订单 " + order.getOrderId() + " 状态变为 '" + order.getStatus() + "'。");
        // System.out.println("厨房收到订单更新: 订单 " + order.getOrderId() + " 状态变为 '" + order.getStatus() + "'。");
        if ("待处理".equals(order.getStatus())) {
            Log.d(TAG, "厨房: 新订单 '" + order.getOrderId() + "'，准备 " + order.getOrderedCoffee().getName() + "。");
            order.getOrderedCoffee().prepare(); // 厨房收到待处理订单后，开始准备咖啡
            // 模拟准备完成后更新状态
            new android.os.Handler(android.os.Looper.getMainLooper()).postDelayed(() -> {
                order.setStatus("准备中");
                OrderManager.getInstance().notifyObservers(order); // 通知状态更新
            }, 1000);
        } else if ("准备中".equals(order.getStatus())) {
            Log.d(TAG, "厨房: 订单 " + order.getOrderId() + " 正在准备中...");
            new android.os.Handler(android.os.Looper.getMainLooper()).postDelayed(() -> {
                order.setStatus("已完成");
                OrderManager.getInstance().notifyObservers(order); // 通知状态更新
            }, 1500);
        }
    }
}
