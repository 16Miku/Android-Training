package com.example.day9_designpattern.coffee;

import android.util.Log;

public class Espresso implements Coffee {
    private static final String TAG = "Espresso";

    public Espresso() {
        Log.d(TAG, "Creating Espresso.");
    }


    @Override
    public String getName() {
        return "Espresso";
    }

    @Override
    public void prepare() {
        Log.d(TAG, "Preparing Espresso: Grinding beans, pulling shot.");

    }


    @Override
    public String getDescription() {
        return "Espresso";
    }

    @Override
    public double getCost() {
        return 2.00;
    }


}