package com.example.day9_designpattern.coffee;

import android.util.Log;

public class MilkDecorator extends CoffeeDecorator {

    private static final String TAG = "MilkDecorator";

    public MilkDecorator(Coffee decoratedCoffee) {
        super(decoratedCoffee);
        Log.d(TAG, "Adding Milk to " + decoratedCoffee.getDescription());
    }

    @Override
    public String getName() {
        // 返回基础咖啡名称 + 装饰者名称
        return decoratedCoffee.getName() + " with Milk";
    }

    @Override
    public void prepare() {
        super.prepare(); // 先准备被装饰的咖啡
        Log.d(TAG, "Steaming and adding milk."); // 添加牛奶的准备步骤
    }

    @Override
    public String getDescription() {
        return decoratedCoffee.getDescription() + ", Milk";
    }

    @Override
    public double getCost() {
        return decoratedCoffee.getCost() + 0.50; // 加奶增加 0.50
    }
}
