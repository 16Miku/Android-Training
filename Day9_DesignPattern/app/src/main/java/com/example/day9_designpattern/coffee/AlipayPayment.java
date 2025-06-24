package com.example.day9_designpattern.coffee;

import android.util.Log;

public class AlipayPayment implements PaymentStrategy {
    private static final String TAG = "AlipayPayment";
    private String userId;

    public AlipayPayment(String userId) {
        this.userId = userId;
    }

    @Override
    public void pay(double amount) {
        Log.d(TAG, "Paying $" + amount + " using Alipay (User ID: " + userId + ").");
        // System.out.println("Paying $" + amount + " using Alipay (User ID: " + userId + ").");
        // 模拟支付逻辑...
    }
}
