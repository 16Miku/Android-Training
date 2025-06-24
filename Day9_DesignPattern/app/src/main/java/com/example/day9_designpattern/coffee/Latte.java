package com.example.day9_designpattern.coffee;

import android.util.Log;

public class Latte implements Coffee {
    private static final String TAG = "Latte";


    public Latte() {
        Log.d(TAG, "Creating Latte.");
    }


    @Override
    public String getName() {


        return "Latte";
    }

    @Override
    public void prepare() {

        Log.d(TAG, "Preparing Latte: Pulling espresso shot, steaming milk, pouring.");

    }



    @Override
    public String getDescription() {
        return "Latte";
    }

    @Override
    public double getCost() {
        return 3.50;
    }



}
