// IAidlCalculator.aidl
package com.example.aidlserver;

// Declare any non-default types here with import statements
// 声明 AIDL 接口
interface IAidlCalculator {

    // 定义两个整数相加的方法
    int add(int a, int b);

    // 定义两个整数相减的方法
    int subtract(int a, int b);

}