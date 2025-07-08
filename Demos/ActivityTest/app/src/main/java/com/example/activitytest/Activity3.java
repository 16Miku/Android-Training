package com.example.activitytest;

import android.content.Intent;
import android.net.Uri;
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

import com.example.activitytest.databinding.Activity3Binding;

public class Activity3 extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private Activity3Binding binding;

    public static final String  TAG = "Activity3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "Oncreate");


        setContentView(R.layout.activity_3);

        Button startActivity3 = (Button) findViewById(R.id.button2);

        startActivity3.setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Log.d(TAG, "启动Activity2页面");

                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        // intent隐式调用

                        intent.setData(Uri.parse("http://www.baidu.com"));

                        startActivity(intent);

                    }
                }

        );


    }


}