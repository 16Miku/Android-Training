package com.example.day9_designpattern.coffee;

import android.util.Log;

public class WhipDecorator extends CoffeeDecorator {
    private static final String TAG = "WhipDecorator";

    public WhipDecorator(Coffee decoratedCoffee) {
        super(decoratedCoffee);
        Log.d(TAG, "Adding Whip to " + decoratedCoffee.getDescription());
    }

    @Override
    public String getName() {
        return decoratedCoffee.getName() + " with Whip";
    }

    @Override
    public void prepare() {
        super.prepare(); // 先准备被装饰的咖啡
        Log.d(TAG, "Topping with whipped cream."); // 添加奶油的准备步骤
    }

    @Override
    public String getDescription() {
        return decoratedCoffee.getDescription() + ", Whip";
    }

    @Override
    public double getCost() {
        return decoratedCoffee.getCost() + 0.70; // 加奶油增加 0.70
    }
}
