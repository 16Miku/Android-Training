// ICalculator.aidl
package com.example.day4_calculator;

// Declare any non-default types here with import statements

interface ICalculator {


    // 定义加法方法
    int add(int num1, int num2);

    // 定义减法方法
    int subtract(int num1, int num2);

    // 定义乘法方法
    int multiply(int num1, int num2);

    // 定义除法方法 (整数除法)
    int divide(int num1, int num2);


}