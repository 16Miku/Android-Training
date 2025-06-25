package com.example.appdemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {
    // HomeFragment 类继承自 Fragment，代表应用的主页内容


    public HomeFragment() {
        // 无参数构造函数是 Fragment 的最佳实践，系统会调用它

    }

    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container, Bundle savedInstanceState) {


        // 充气（inflate）布局文件，将其转换为 View 对象
        // R.layout.fragment_home 是我们为 HomeFragment 设计的布局文件
        // container 是 Fragment 将被添加到的父视图
        // false 表示不立即将充气后的 View 添加到父视图，因为 FragmentManager 会处理这个
        return inflater.inflate( R.layout.fragment_home, container, false  );


    }


}
