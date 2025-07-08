package com.example.activitytest;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.activitytest.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends AppCompatActivity  {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    public static final String  TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "Oncreate");


        setContentView(R.layout.activity_main);

        Button startActivity2 = (Button) findViewById(R.id.ToActivity2);

        startActivity2.setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Log.d(TAG, "启动Activity2页面");

                        Intent intent = new Intent(MainActivity.this, Activity2.class);

                        startActivity(intent);

                    }
                }
        );



        Button createFragment = (Button) findViewById(R.id.CreateFragment);

        createFragment.setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Log.d(TAG, "动态创建Fragment");

                        FragmentManager fragmentManager = getSupportFragmentManager();

                        FragmentTransaction transaction = fragmentManager.beginTransaction();

                        First2Fragment fragment2 = new First2Fragment();

                        transaction.add( R.id.fragment_container, fragment2 );

                        transaction.addToBackStack(null);

                        transaction.commit();

                    }
                }
        );



//        binding = ActivityMainBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//        setSupportActionBar(binding.toolbar);
//
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//
//        binding.fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAnchorView(R.id.fab)
//                        .setAction("Action", null).show();
//            }
//        });
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        return NavigationUI.navigateUp(navController, appBarConfiguration)
//                || super.onSupportNavigateUp();
//    }



    @Override
    protected void onStart() {

        super.onStart();
        Log.d(TAG,"onStart");

    }

    @Override
    protected void onResume() {

        super.onResume();
        Log.d(TAG,"onResume");

    }

    @Override
    protected void onPause() {

        super.onPause();
        Log.d(TAG,"onPause");

    }

    @Override
    protected void onStop() {

        super.onStop();
        Log.d(TAG,"onStop");

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        Log.d(TAG,"onDestroy");

    }



}