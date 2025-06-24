package com.example.day9_designpattern.coffee;

import android.util.Log;

public class CreditCardPayment implements PaymentStrategy {
    private static final String TAG = "CreditCardPayment";
    private String cardNumber;
    private String cvv;

    public CreditCardPayment(String cardNumber, String cvv) {
        this.cardNumber = cardNumber;
        this.cvv = cvv;
    }

    @Override
    public void pay(double amount) {
        Log.d(TAG, "Paying $" + amount + " using Credit Card (Card No: " + cardNumber + ").");
        // System.out.println("Paying $" + amount + " using Credit Card (Card No: " + cardNumber + ").");
        // 模拟支付逻辑...
    }
}

