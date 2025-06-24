package com.example.day9_designpattern.coffee;

// CoffeeFactory.java
public abstract class CoffeeFactory {

    public abstract Coffee createCoffee();

    /**
     * 静态工厂方法：根据咖啡类型字符串创建对应的咖啡工厂实例。
     * 这种方式可以作为工厂方法模式的一个扩展，或者简单工厂模式的实现。
     * 它隔离了客户端创建具体工厂的逻辑。
     *
     * @param type 咖啡类型字符串 (例如 "espresso", "latte", "cappuccino")
     * @return 对应的 CoffeeFactory 实例
     */
    public static CoffeeFactory getFactory(String type) {
        if (type == null) {
            return null;
        }
        switch (type.toLowerCase()) { // 转换为小写进行匹配，增加健壮性
            case "espresso":
                return new EspressoFactory();
            case "latte":
                return new LatteFactory();
            case "cappuccino":
                return new CappuccinoFactory();
            default:
                // 可以抛出异常或返回null，这里返回null表示不支持的类型
                System.err.println("Unsupported coffee type: " + type);
                return null;
        }
    }
}
