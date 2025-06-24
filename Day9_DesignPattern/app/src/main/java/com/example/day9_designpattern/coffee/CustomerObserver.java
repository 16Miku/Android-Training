package com.example.day9_designpattern.coffee;

import android.util.Log;

public class CustomerObserver implements OrderObserver {
    private static final String TAG = "CustomerObserver";
    private String customerName;

    public CustomerObserver(String customerName) {
        this.customerName = customerName;
    }

    @Override
    public void update(Order order) {

        Log.d(TAG, "顾客 " + customerName + " 收到更新: 订单 " + order.getOrderId() + " 状态变为 '" + order.getStatus() + "'。");
        // System.out.println("顾客 " + customerName + " 收到更新: 订单 " + order.getOrderId() + " 状态变为 '" + order.getStatus() + "'。");

        if ("已完成".equals(order.getStatus())) {
            Log.d(TAG, "顾客 " + customerName + ": 您的 " + order.getOrderedCoffee().getName() + " 咖啡已准备好，共 $" + order.getOrderedCoffee().getCost() + "。");
        }

    }
}
