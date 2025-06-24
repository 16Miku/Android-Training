package com.example.day9_designpattern.coffee;

import android.util.Log;

public class SugarDecorator extends CoffeeDecorator {
    private static final String TAG = "SugarDecorator";

    public SugarDecorator(Coffee decoratedCoffee) {
        super(decoratedCoffee);
        Log.d(TAG, "Adding Sugar to " + decoratedCoffee.getDescription());
    }

    @Override
    public String getName() {
        return decoratedCoffee.getName() + " with Sugar";
    }

    @Override
    public void prepare() {
        super.prepare(); // 先准备被装饰的咖啡
        Log.d(TAG, "Adding sugar cubes/syrup."); // 添加糖的准备步骤
    }

    @Override
    public String getDescription() {
        return decoratedCoffee.getDescription() + ", Sugar";
    }

    @Override
    public double getCost() {
        return decoratedCoffee.getCost() + 0.20; // 加糖增加 0.20
    }
}
