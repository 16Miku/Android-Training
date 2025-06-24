package com.example.day9_designpattern.coffee;

public class CappuccinoFactory extends CoffeeFactory {


    @Override
    public Coffee createCoffee() {



        return new Cappuccino();
    }
}