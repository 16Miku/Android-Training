package com.example.appdemo;

import androidx.fragment.app.Fragment;



import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

// MyFragment 类继承自 Fragment，代表应用的“我的”功能内容
public class MyFragment extends Fragment {

    public MyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 充气（inflate）布局文件
        return inflater.inflate(R.layout.fragment_my, container, false);
    }
}
