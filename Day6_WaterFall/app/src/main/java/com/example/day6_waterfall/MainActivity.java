// app/src/main/java/com.example/day6_waterfall/MainActivity.java
package com.example.day6_waterfall;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.day6_waterfall.ApiService;
import com.example.day6_waterfall.RetrofitClient;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private SmartRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private ImageAdapter imageAdapter;
    private final Random random = new Random();
    private int currentPage = 0; // 当前页码
    private final int PAGE_SIZE = 20; // 每页加载的数据量
    private final int MAX_PAGES = 3; // 模拟总共只有 3 页数据

    private Handler mainHandler = new Handler(Looper.getMainLooper());
    private ApiService apiService;
    private ExecutorService threadPool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initThreadPool();
        initViews();
        setupRecyclerView();
        setupRefreshLayout();

        apiService = RetrofitClient.getApiService();

        refreshLayout.autoRefresh(); // 首次自动触发下拉刷新
    }

    /**
     * 初始化自定义线程池
     */
    private void initThreadPool() {
        threadPool = new ThreadPoolExecutor(
                3,
                6,
                60L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
        );
    }

    /**
     * 初始化视图控件
     */
    private void initViews() {
        refreshLayout = findViewById(R.id.refreshLayout);
        recyclerView = findViewById(R.id.recyclerView);
    }

    /**
     * 设置 RecyclerView 的布局管理器和适配器
     */
    private void setupRecyclerView() {
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int itemWidth = screenWidth / 2; // 假设两列，每列宽度为屏幕宽度的一半

        imageAdapter = new ImageAdapter(itemWidth);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(imageAdapter);
    }

    /**
     * 设置 SmartRefreshLayout 的刷新和加载监听器
     */
    private void setupRefreshLayout() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout layout) {
                currentPage = 0; // 刷新时重置页码

                // 延迟仍然保留，用于模拟网络请求的延迟
                mainHandler.postDelayed(() -> {
                    loadDataWithThreadPool(true, layout); // 执行刷新逻辑
                }, 200); // 延迟200毫秒，可以根据需要调整
            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout layout) {
                currentPage++;
                loadDataWithThreadPool(false, layout);
            }
        });
    }

    /**
     * 使用线程池在子线程中模拟加载数据
     * @param isRefresh true 表示是刷新操作，false 表示是加载更多操作
     * @param layout SmartRefreshLayout 实例，用于结束刷新/加载动画
     */
    private void loadDataWithThreadPool(final boolean isRefresh, final RefreshLayout layout) {
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                List<ImageItem> newData = new ArrayList<>();
                String errorMessage = null;
                boolean success = true;

                try {
                    Thread.sleep(1500); // 模拟网络请求延迟 1.5 秒

                    for (int i = 0; i < PAGE_SIZE; i++) {
                        int height = random.nextInt(601) + 200;
                        Response<Void> response = apiService.getPhotoUrl(400, height).execute();

                        if (response.isSuccessful()) {
                            String imageUrl = response.raw().request().url().toString();
                            Log.d("ImageRefresh", "Fetched URL: " + imageUrl + " for height: " + height);
                            newData.add(new ImageItem(imageUrl, height));
                        } else {
                            success = false;
                            errorMessage = "请求图片地址失败: " + response.code() + " " + response.message();
                            Log.e("MainActivity", errorMessage);
                            break;
                        }
                    }
                } catch (IOException e) {
                    success = false;
                    errorMessage = "网络错误: " + e.getMessage();
                    Log.e("MainActivity", errorMessage, e);
                } catch (InterruptedException e) {
                    success = false;
                    errorMessage = "线程中断: " + e.getMessage();
                    Log.e("MainActivity", errorMessage, e);
                }

                final List<ImageItem> finalNewData = newData;
                final String finalErrorMessage = errorMessage;
                final boolean finalSuccess = success;

                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (finalSuccess) {
                            if (isRefresh) {
                                imageAdapter.setItems(finalNewData); // 刷新时替换所有数据
                                layout.finishRefresh(true); // 结束刷新，传入 true 表示刷新成功
                                Toast.makeText(MainActivity.this, "刷新成功", Toast.LENGTH_SHORT).show();
                                layout.setNoMoreData(false); // 刷新后，重置加载更多状态，允许再次加载

                                recyclerView.scrollToPosition(0); // 立即滚动到顶部

                                // *** 关键改进：请求 RecyclerView 重新布局和绘制 ***
                                // 这会强制 RecyclerView 重新计算所有 Item 的位置和大小，并重新绘制
                                recyclerView.post(() -> { // 使用 post 确保在布局完成后执行
                                    recyclerView.getLayoutManager().requestLayout();
                                    // 或者更轻量级的：
                                    // recyclerView.invalidateItemDecorations();
                                    // recyclerView.invalidateOutline();
                                });

                            } else {
                                boolean hasMore = (currentPage < MAX_PAGES - 1);
                                if (hasMore) {
                                    imageAdapter.addAll(finalNewData);
                                    layout.finishLoadMore(true);
                                    Toast.makeText(MainActivity.this, "加载了 " + finalNewData.size() + " 条数据", Toast.LENGTH_SHORT).show();
                                } else {
                                    layout.finishLoadMoreWithNoMoreData();
                                    Toast.makeText(MainActivity.this, "没有更多数据了", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            if (isRefresh) {
                                layout.finishRefresh(false);
                                Toast.makeText(MainActivity.this, "刷新失败: " + finalErrorMessage, Toast.LENGTH_LONG).show();
                            } else {
                                layout.finishLoadMore(false);
                                Toast.makeText(MainActivity.this, "加载失败: " + finalErrorMessage, Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (threadPool != null && !threadPool.isShutdown()) {
            threadPool.shutdownNow();
        }
        if (mainHandler != null) {
            mainHandler.removeCallbacksAndMessages(null);
        }
    }
}
