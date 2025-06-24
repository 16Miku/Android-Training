package com.example.day9_designpattern.coffee;

import android.util.Log; // 导入 Log

import java.util.ArrayList;
import java.util.List;

public class OrderManager {

    private final static String TAG = "OrderManager";

    // 使用 List<Order> 存储订单对象
    private List<Order> orders;
    // 使用 List<OrderObserver> 存储观察者对象
    private List<OrderObserver> observers;

    private volatile static OrderManager instance; // 双重检查锁定单例

    private OrderManager() {
        this.orders = new ArrayList<>();
        this.observers = new ArrayList<>();
        Log.d(TAG, "OrderManager instance created.");
    }

    public static OrderManager getInstance() {
        if (instance == null) {
            synchronized (OrderManager.class) {
                if (instance == null) {
                    instance = new OrderManager();
                }
            }
        }
        return instance;
    }

    /**
     * 添加新订单
     *
     * @param order 新的订单对象
     */
    public void addOrder(Order order) { // 修改为接收 Order 对象
        orders.add(order);
        Log.d(TAG, "Order " + order.getOrderId() + " added. Status: " + order.getStatus());
        notifyObservers(order); // 通知观察者订单已创建
    }

    /**
     * 更新订单状态并通知观察者
     *
     * @param order 要更新的订单对象
     */
    public void updateOrderStatus(Order order, String newStatus) {
        order.setStatus(newStatus);
        Log.d(TAG, "Order " + order.getOrderId() + " status updated to: " + newStatus);
        notifyObservers(order);
    }

    public List<Order> getAllOrders() { // 返回 List<Order>
        return new ArrayList<>(orders);
    }

    /**
     * 注册观察者
     *
     * @param observer 订单观察者
     */
    public void registerObserver(OrderObserver observer) {
        observers.add(observer);
        Log.d(TAG, "Observer registered: " + observer.getClass().getSimpleName());
    }

    /**
     * 移除观察者
     *
     * @param observer 订单观察者
     */
    public void removeObserver(OrderObserver observer) {
        observers.remove(observer);
        Log.d(TAG, "Observer removed: " + observer.getClass().getSimpleName());
    }

    /**
     * 通知所有观察者订单状态更新
     *
     * @param order 发生更新的订单对象
     */
    public void notifyObservers(Order order) { // 修改为接收 Order 对象
        Log.d(TAG, "Notifying observers for Order " + order.getOrderId() + " with status: " + order.getStatus());
        // 使用一个副本列表，避免在迭代过程中修改观察者列表
        List<OrderObserver> observersCopy = new ArrayList<>(observers);
        for (OrderObserver observer : observersCopy) {
            observer.update(order);
        }
    }
}
