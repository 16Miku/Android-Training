package com.example.day9_designpattern.coffee;

import android.util.Log;

public class PaymentProcessor {
    private static final String TAG = "PaymentProcessor";
    private PaymentStrategy paymentStrategy;

    public PaymentProcessor(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    // 允许运行时动态切换策略
    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
        Log.d(TAG, "Payment strategy set to: " + paymentStrategy.getClass().getSimpleName());
        // System.out.println("Payment strategy set to: " + paymentStrategy.getClass().getSimpleName());
    }

    // 执行支付操作，委托给当前策略
    public void processPayment(double amount) {
        if (paymentStrategy == null) {
            Log.e(TAG, "No payment strategy set!");
            // System.err.println("No payment strategy set!");
            return;
        }
        paymentStrategy.pay(amount);
    }
}