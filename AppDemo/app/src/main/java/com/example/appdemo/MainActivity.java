package com.example.appdemo;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction; // 导入FragmentTransaction类

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {



    private BottomNavigationView bottomNavigationView;
    // 底部导航视图


    private HomeFragment homeFragment;

    private SearchFragment searchFragment;

    private MyFragment myFragment;

    private Fragment activeFragment;
    // 当前显示的 Fragment



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        // 设置Activity的布局文件为 activity_main.xml

        // 初始化底部导航视图
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // 初始化 Fragment 实例
        homeFragment = new HomeFragment();

        searchFragment = new SearchFragment();

        myFragment = new MyFragment();

        // 获取 FragmentManager，用于管理 Fragment
        FragmentManager fm = getSupportFragmentManager();

        // 首次加载时，添加所有 Fragment 但只显示 HomeFragment
        // 这样可以避免每次切换时都重新创建 Fragment，提高性能
        fm.beginTransaction().add( R.id.fragment_container, myFragment, "3" ).hide(myFragment).commit();

        fm.beginTransaction().add( R.id.fragment_container, searchFragment, "2" ).hide(searchFragment).commit();

        fm.beginTransaction().add( R.id.fragment_container, homeFragment, "1" ).commit();

        // 设置当前活跃的 Fragment 为 HomeFragment
        activeFragment = homeFragment;



        // 设置底部导航栏的选择监听器
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                // 根据选中的菜单项ID切换 Fragment
                int itemId = item.getItemId();

                // 使用 if-else if 结构处理不同的菜单项
                if( itemId == R.id.nav_home ) {
                    // 选中“首页”

                    // 切换到 HomeFragment
                    switchFragment( homeFragment );

                    // 表示事件已处理
                    return true;

                }
                else if( itemId == R.id.nav_search ) {

                    // 选中“搜索”
                    switchFragment( searchFragment );

                    return true;

                }
                else if( itemId == R.id.nav_my ) {

                    // 选中“我的”
                    switchFragment( myFragment );

                    return true;

                }

                // 如果没有匹配的菜单项，返回 false
                return false;

            }
        });


    }


    // 切换 Fragment 的方法
    private void switchFragment( Fragment targetFragment ) {
        // switchFragment 方法使用了 hide() 和 show() 来切换 Fragment，而不是每次都 replace()。
        // 这种方法可以保留 Fragment 的状态，并提高切换的流畅性，因为它避免了每次都重新创建视图。

        if( activeFragment != targetFragment ) {
            // 如果目标 Fragment 不是当前活跃的 Fragment


            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();


            ft.hide(activeFragment);
            // 隐藏当前活跃的 Fragment


            if( !targetFragment.isAdded() ) {
                // 如果目标 Fragment 尚未添加，则添加它

                ft.add( R.id.fragment_container, targetFragment );

            } else {
                // 如果目标 Fragment 已经添加，则显示它


                ft.show( targetFragment );
            }


            ft.commit();
            // 提交事务

            activeFragment = targetFragment;
            // 更新当前活跃的 Fragment


        }


    }


}