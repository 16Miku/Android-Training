package com.example.day9_designpattern.coffee;

public class Order {
    private String orderId;
    private String status; // 例如 "待处理", "准备中", "已完成", "已取消"
    private Coffee orderedCoffee; // 订单中的咖啡对象

    public Order(String orderId, Coffee orderedCoffee) {
        this.orderId = orderId;
        this.orderedCoffee = orderedCoffee;
        this.status = "待处理"; // 初始状态
    }

    public String getOrderId() {
        return orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Coffee getOrderedCoffee() {
        return orderedCoffee;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", status='" + status + '\'' +
                ", coffee='" + orderedCoffee.getName() + '\'' +
                ", cost=$" + orderedCoffee.getCost() +
                '}';
    }
}
