package com.example.day6_waterfall;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private RefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private ImageAdapter imageAdapter;
    private final Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. 初始化视图
        initViews();

        // 2. 设置 RecyclerView
        setupRecyclerView();

        // 3. 设置刷新和加载监听
        setupRefreshLayout();

        // 4. 首次自动刷新加载数据
        refreshLayout.autoRefresh();
    }

    private void initViews() {
        refreshLayout = findViewById(R.id.refreshLayout);
        recyclerView = findViewById(R.id.recyclerView);
    }

    private void setupRecyclerView() {
        imageAdapter = new ImageAdapter();
        // 设置瀑布流布局管理器
        // 参数：列数，方向
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(imageAdapter);
    }

    private void setupRefreshLayout() {
        // 设置下拉刷新监听
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout layout) {
                // 执行刷新逻辑
                loadData(true);
            }
        });

        // 设置上滑加载更多监听
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout layout) {
                // 执行加载更多逻辑
                loadData(false);
            }
        });
    }

    /**
     * 加载数据的方法
     * @param isRefresh true 表示是刷新操作，false 表示是加载更多操作
     */
    private void loadData(final boolean isRefresh) {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {

            List<ImageItem> data = generateImageData();

            if (isRefresh) {
                // 错误的方法: imageAdapter.setList(data);
                // 修正 ✅: 使用 setItems() 替换 setList()
                imageAdapter.setItems(data);
                refreshLayout.finishRefresh(true);
            } else {
                // 错误的方法: imageAdapter.addData(data);
                // 修正 ✅: 使用 addAll() 替换 addData()
                imageAdapter.addAll(data);
                refreshLayout.finishLoadMore(true);
            }

        }, 1500);
    }

    /**
     * 生成图片数据
     * @return 图片数据列表
     */
    private List<ImageItem> generateImageData() {
        List<ImageItem> list = new ArrayList<>();
        // 每次生成 20 条数据
        for (int i = 0; i < 20; i++) {
            // 作业要求：图片数据网址: https://picsum.photos/400/{length} 取200 ~ 800之间的随机值
            int height = random.nextInt(601) + 200; // 生成 200 到 800 的随机数
            String url = "https://picsum.photos/400/" + height;
            list.add(new ImageItem(url, height));
        }
        return list;
    }
}