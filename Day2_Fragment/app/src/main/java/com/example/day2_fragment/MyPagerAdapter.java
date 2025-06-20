package com.example.day2_fragment;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MyPagerAdapter extends FragmentStatePagerAdapter {




    private static final int NUM_PAGES = 3;

    private String[] tabTitles = {"first", "second", "third"};

    public MyPagerAdapter(@NonNull FragmentManager fm, int behavior) {

        super(fm,behavior);

    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        // 根据位置创建并返回对应的 Fragment
        switch (position) {
            case 0:
                return new FirstFragment();
            case 1:
                return new SecondFragment();
            case 2:
                return new ThirdFragment();
            default:

                return new FirstFragment();
        }
    }


    @Override
    public int getCount() {
        return NUM_PAGES;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        // 根据位置返回对应的 Tab 标题
        if (position >= 0 && position < tabTitles.length) {
            return tabTitles[position];
        }
        return null;
    }

}
