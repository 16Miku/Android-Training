package com.example.day6_waterfall;

// ImageItem.java


public class ImageItem {
    private String url;
    private int height; // 保存图片高度，用于瀑布流

    public ImageItem(String url, int height) {
        this.url = url;
        this.height = height;
    }

    public String getUrl() {
        return url;
    }

    public int getHeight() {
        return height;
    }
}