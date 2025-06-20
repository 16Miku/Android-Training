package com.example.day2_fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity implements FirstFragment.OnNavigateToDetailListener {


    private FragmentManager fragmentManager;

    private static final String TAG = "MainActivity";

    private ViewPager viewPager;

    private MyPagerAdapter adapter;

    private TabLayout layout;






    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);




        fragmentManager = getSupportFragmentManager();

        viewPager = findViewById(R.id.view_pager);

        layout = findViewById(R.id.tab_layout);


        adapter = new MyPagerAdapter(fragmentManager, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);


        viewPager.setAdapter(adapter);


        layout.setupWithViewPager(viewPager);



    }



    @Override
    public void onNavigateToDetail() {
        Log.d(TAG, "Received navigation request from FirstFragment");

        showFragment(new DetailFragment(), "DetailFragment");
    }


    public void showFragment( Fragment fragment, String tag ) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();


        try {
            transaction.setCustomAnimations(
                    R.anim.slide_from_left,
                    R.anim.slide_out_right,
                    R.anim.slide_from_right,
                    R.anim.slide_out_left
            );
        } catch (Exception e) {
            Log.e(TAG, "Animation resources not found or invalid", e);

        }



        transaction.replace( R.id.tab_layout, fragment, tag );


        transaction.addToBackStack("detail_fragment_tag");

        transaction.commit();


    }






}