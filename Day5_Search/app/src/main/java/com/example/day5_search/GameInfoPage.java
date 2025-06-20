package com.example.day5_search;



import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GameInfoPage {
    @SerializedName("records") // 游戏列表
    private List<GameInfo> records;
    @SerializedName("total") // 总数
    private int total;
    @SerializedName("size") // 当前页大小
    private int size;
    @SerializedName("current") // 当前页
    private int current;
    @SerializedName("pages") // 总页数
    private int pages;

    // 假设还有其他字段，如 orders, searchCount，但这里只关注作业中提到的
    @SerializedName("orders")
    private List<Object> orders; // 假设是列表，具体类型未知，用Object代替
    @SerializedName("searchCount")
    private boolean searchCount;

    public List<GameInfo> getRecords() {
        return records;
    }

    public void setRecords(List<GameInfo> records) {
        this.records = records;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<Object> getOrders() {
        return orders;
    }

    public void setOrders(List<Object> orders) {
        this.orders = orders;
    }

    public boolean isSearchCount() {
        return searchCount;
    }

    public void setSearchCount(boolean searchCount) {
        this.searchCount = searchCount;
    }

    @Override
    public String toString() {
        return "GameInfoPage{" +
                "records=" + records +
                ", total=" + total +
                ", size=" + size +
                ", current=" + current +
                ", pages=" + pages +
                ", orders=" + orders +
                ", searchCount=" + searchCount +
                '}';
    }
}
