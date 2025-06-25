package com.example.appdemo;


// HomeItem 类定义了首页瀑布流列表项的数据结构
public class HomeItem {
    private String imageUrl; // 图片的URL地址
    private int itemHeight;  // 列表项的高度，用于模拟瀑布流的不等高效果
    private String title;    // 列表项的标题

    // 构造函数，用于创建 HomeItem 实例并初始化其属性
    public HomeItem(String imageUrl, int itemHeight, String title) {
        this.imageUrl = imageUrl;
        this.itemHeight = itemHeight;
        this.title = title;
    }

    // 获取图片URL的方法
    public String getImageUrl() {
        return imageUrl;
    }

    // 设置图片URL的方法
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    // 获取列表项高度的方法
    public int getItemHeight() {
        return itemHeight;
    }

    // 设置列表项高度的方法
    public void setItemHeight(int itemHeight) {
        this.itemHeight = itemHeight;
    }

    // 获取标题的方法
    public String getTitle() {
        return title;
    }

    // 设置标题的方法
    public void setTitle(String title) {
        this.title = title;
    }
}
