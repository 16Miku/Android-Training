package com.example.day9_designpattern.coffee;

import android.util.Log;

public class Cappuccino implements Coffee {
    private static final String TAG = "Cappuccino";
    @Override
    public String getName() {
        return "Cappuccino";
    }

    @Override
    public void prepare() {
        Log.d(TAG, "Preparing Cappuccino: Pulling espresso shot, steaming milk, adding foam.");
        // System.out.println("Preparing Cappuccino: Pulling espresso shot, steaming milk, adding foam.");
    }

    @Override // 明确指出这是对接口方法的实现
    public double getCost() {
        // 假设卡布奇诺的价格是 3.50
        return 3.50;
    }


    @Override // 明确指出这是对接口方法的实现
    public String getDescription() {
        return "Cappuccino";
    }

}
