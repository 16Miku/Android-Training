package com.example.day9_designpattern.coffee;

public class LatteFactory extends CoffeeFactory {
    @Override
    public Coffee createCoffee() {

        return new Latte();
    }
}
