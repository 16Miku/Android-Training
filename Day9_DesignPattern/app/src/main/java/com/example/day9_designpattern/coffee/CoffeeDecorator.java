package com.example.day9_designpattern.coffee;

public abstract class CoffeeDecorator implements Coffee {

    protected Coffee decoratedCoffee;

    public CoffeeDecorator(Coffee decoratedCoffee) {
        this.decoratedCoffee = decoratedCoffee;
    }

    @Override
    public String getName() {
        // 委托给被装饰的咖啡对象，或者添加装饰者的名称
        return decoratedCoffee.getName(); // 这里选择委托，如果装饰者有自己的名字，可以在子类中覆盖
    }

    @Override
    public void prepare() {
        // 委托给被装饰的咖啡对象，然后添加装饰者的准备步骤
        decoratedCoffee.prepare(); // 先准备基础咖啡
        // 子类可以在这里添加自己的 prepare 逻辑
    }

    @Override
    public String getDescription() {
        return decoratedCoffee.getDescription();
    }

    @Override
    public double getCost() {
        return decoratedCoffee.getCost();
    }
}
