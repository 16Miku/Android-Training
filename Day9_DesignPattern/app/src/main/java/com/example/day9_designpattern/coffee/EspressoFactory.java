package com.example.day9_designpattern.coffee;

public class EspressoFactory extends CoffeeFactory {


    @Override
    public Coffee createCoffee() {



        return new Espresso();
    }
}
