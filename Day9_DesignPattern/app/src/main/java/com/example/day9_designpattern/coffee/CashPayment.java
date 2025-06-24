package com.example.day9_designpattern.coffee;


import android.util.Log;

public class CashPayment implements PaymentStrategy {
    private static final String TAG = "CashPayment";
    @Override
    public void pay(double amount) {
        Log.d(TAG, "Paying $" + amount + " using Cash.");
        // System.out.println("Paying $" + amount + " using Cash.");
        // 模拟支付逻辑...
    }
}
