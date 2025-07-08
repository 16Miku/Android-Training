package com.example.activitytest;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.activitytest.databinding.Activity2Binding;

public class Activity2 extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private Activity2Binding binding;

    public static final String  TAG = "Activity2";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "Oncreate");


        setContentView(R.layout.activity_2);

        Button startActivity3 = (Button) findViewById(R.id.button1);

        startActivity3.setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Log.d(TAG, "启动Activity2页面");

                        Intent intent = new Intent(Activity2.this, Activity3.class);

                        startActivity(intent);

                    }
                }

        );
    }


}