package com.example.day9_designpattern.coffee;

// 订单观察者接口
public interface OrderObserver {
    void update(Order order); // 修改为接收 Order 对象
}
