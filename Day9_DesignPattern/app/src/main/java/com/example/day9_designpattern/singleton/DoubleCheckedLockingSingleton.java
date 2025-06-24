package com.example.day9_designpattern.singleton;

public class DoubleCheckedLockingSingleton {

    private volatile static DoubleCheckedLockingSingleton singleton;
    // 加volatile锁


    private DoubleCheckedLockingSingleton() {};

    public static DoubleCheckedLockingSingleton getSingleton() {

        if( singleton == null ) {


            synchronized ( DoubleCheckedLockingSingleton.class ) {
                // 加synchronized锁

                if( singleton == null ) {

                    singleton = new DoubleCheckedLockingSingleton();

                }
            }

        }

        return singleton;

    }



}
