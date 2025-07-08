package com.example.recyclerviewtest;

// 用户数据模型类
public class User {
    private String name; // 用户姓名
    private int avatarColor; // 用户头像颜色 (这里用一个颜色值代替图片)

    // 构造函数
    public User(String name, int avatarColor) {
        this.name = name;
        this.avatarColor = avatarColor;
    }

    // Getter 方法
    public String getName() {
        return name;
    }

    public int getAvatarColor() {
        return avatarColor;
    }

    // Setter 方法 (如果需要修改数据)
    public void setName(String name) {
        this.name = name;
    }

    public void setAvatarColor(int avatarColor) {
        this.avatarColor = avatarColor;
    }
}
