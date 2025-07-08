package com.example.fruitlistview;

public class Fruit {


    private String name;

    private int imageId;
    /*Fruit 类中只有两个字段，name 表示水果的名字，imageId 表示水果对应图片的资源id。*/

    public Fruit( String name, int imageId ) {
        // 构造函数初始化实例

        this.name = name;

        this.imageId = imageId;

    }

    public String getName() {

        return this.name;

    }

    public int getImageId() {
        return imageId;
    }

}
