package com.example.smartrefreshlayout_demo;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;



import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartrefreshlayout_demo.databinding.ActivityMainBinding; // 导入 ViewBinding
import com.example.smartrefreshlayout_demo.databinding.ActivityMainWithoutHeaderAndFooterBinding;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;


/**
 * ActivityMainBinding binding;： 使用 View Binding 简化视图访问。
 * binding.refreshLayout.setOnRefreshListener(...)： 设置下拉刷新监听器。当用户下拉触发刷新时，onRefresh() 方法会被调用。
 * 在 onRefresh() 中，通常执行数据清空、页码重置、加载第一页数据等操作。
 * refreshLayout.finishRefresh(true)： 在数据加载完成后，必须调用此方法来结束刷新动画。参数 true 表示刷新成功，false 表示刷新失败。
 * binding.refreshLayout.setOnLoadMoreListener(...)： 设置上拉加载更多监听器。当用户上拉触发加载时，onLoadMore() 方法会被调用。
 * 在 onLoadMore() 中，通常执行页码递增、加载下一页数据等操作。
 * refreshLayout.finishLoadMore(true)： 在数据加载完成后，必须调用此方法来结束加载动画。参数 true 表示加载成功，false 表示加载失败。
 * refreshLayout.finishLoadMoreWithNoMoreData()： 如果已经没有更多数据可加载，调用此方法会结束加载动画，并显示“没有更多数据”的提示，同时禁用后续的上拉加载。
 * binding.refreshLayout.autoRefresh()： 在 onCreate() 中调用此方法，可以模拟用户下拉操作，自动触发一次下拉刷新，常用于首次进入页面加载数据。
 *
 *
 * View Binding 的注意事项：
 * 命名约定： View Binding 会根据 XML 布局文件的名称来生成对应的 Binding 类。如果您的布局文件名为 activity_main_without_header_and_footer.xml，那么生成的 Binding 类将是 ActivityMainWithoutHeaderAndFooterBinding。
 * 修改 `binding` 变量类型： 如果您希望继续使用 View Binding，您需要将 MainActivity 中 binding 变量的类型从 ActivityMainBinding 改为 ActivityMainWithoutHeaderAndFooterBinding。
 * 修改初始化： 相应的，初始化代码也需要改为 binding = ActivityMainWithoutHeaderAndFooterAndFooterBinding.inflate(getLayoutInflater());
 * 重新编译： 确保您的项目已开启 View Binding (buildFeatures { viewBinding = true } 在 build.gradle.kts 中)，并且在修改 XML 文件名后，需要重新 Build/Rebuild Project，让 Android Studio 生成新的 Binding 类。
 */
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding old_binding; // ViewBinding 实例


    private ActivityMainWithoutHeaderAndFooterBinding binding;

    private MyAdapter adapter; // RecyclerView 适配器
    private List<String> dataList; // 数据源
    private int page = 0; // 当前页码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainWithoutHeaderAndFooterBinding.inflate(getLayoutInflater()); // 初始化 ViewBinding
        setContentView(binding.getRoot());

        // 初始化数据源
        dataList = new ArrayList<>();
        adapter = new MyAdapter(dataList); // 创建适配器

        // 配置 RecyclerView
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);

        // 设置下拉刷新监听器
        binding.refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                // 模拟网络请求刷新数据
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page = 0; // 刷新时重置页码
                        dataList.clear(); // 清空旧数据
                        loadData(page); // 加载第一页数据
                        adapter.notifyDataSetChanged(); // 通知适配器数据已改变
                        refreshLayout.finishRefresh(true); // 结束刷新，传入 true 表示刷新成功
                        Toast.makeText(MainActivity.this, "刷新成功", Toast.LENGTH_SHORT).show();
                        // 如果没有更多数据，可以禁用上拉加载
                        // refreshLayout.setEnableLoadMore(true);
                    }
                }, 2000); // 模拟 2 秒延迟
            }
        });

        // 设置上拉加载更多监听器
        binding.refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                // 模拟网络请求加载更多数据
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page++; // 页码递增
                        int oldSize = dataList.size();
                        loadData(page); // 加载下一页数据
                        if (dataList.size() > oldSize) { // 如果有新数据加载
                            adapter.notifyItemRangeInserted(oldSize, dataList.size() - oldSize); // 局部刷新
                            refreshLayout.finishLoadMore(true); // 结束加载，传入 true 表示加载成功
                            Toast.makeText(MainActivity.this, "加载成功", Toast.LENGTH_SHORT).show();
                        } else {
                            refreshLayout.finishLoadMoreWithNoMoreData(); // 结束加载，并提示没有更多数据
                            Toast.makeText(MainActivity.this, "没有更多数据了", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, 2000); // 模拟 2 秒延迟
            }
        });

        // 首次进入页面，自动触发下拉刷新
        binding.refreshLayout.autoRefresh();
    }

    // 模拟加载数据的方法
    private void loadData(int currentPage) {
        for (int i = 0; i < 10; i++) { // 每页加载 10 条数据
            dataList.add("Item " + (currentPage * 10 + i));
        }
    }
}