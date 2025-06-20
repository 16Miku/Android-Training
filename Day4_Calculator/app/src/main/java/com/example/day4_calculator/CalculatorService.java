package com.example.day4_calculator;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class CalculatorService extends Service {


    private static final String TAG = "CalculatorService";

    private final ICalculator.Stub binder = new ICalculator.Stub() {
        @Override
        public int add(int num1, int num2) throws RemoteException {

            Log.d( TAG, "执行了加法运算" + num1 + "+" + num2 );

            return  num1 + num2 ;
        }

        @Override
        public int subtract(int num1, int num2) throws RemoteException {


            Log.d( TAG, "执行了减法运算" + num1 + "-" + num2 );

            return  num1 - num2 ;
        }

        @Override
        public int multiply(int num1, int num2) throws RemoteException {


            Log.d( TAG, "执行了乘法运算" + num1 + "*" + num2 );

            return  num1 * num2 ;
        }

        @Override
        public int divide(int num1, int num2) throws RemoteException {


            Log.d( TAG, "执行了除法运算" + num1 + "/" + num2 );

            if( num2 == 0 ) {

                Log.e(TAG, "除数不能为0");

                return 0;
            }


            return  num1 / num2 ;
        }



    };




    // Service 第一次创建时调用
    @Override
    public void onCreate() {

        super.onCreate();

        Log.d(TAG, "onCreate: Service created");
    }

    // 当客户端调用 bindService() 绑定 Service 时调用
    @Override
    public IBinder onBind(Intent intent) {

        Log.d(TAG, "onBind: Service bound, returning binder");

        return binder;
    }


    @Override
    public boolean onUnbind(Intent intent) {

        Log.d(TAG, "onUnbind: All clients unbound");

        return super.onUnbind(intent);
    }



    @Override
    public void onDestroy() {

        super.onDestroy();

        Log.d(TAG, "onDestroy: Service destroyed");

    }









}
