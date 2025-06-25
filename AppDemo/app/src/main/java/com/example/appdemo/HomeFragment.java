// app/src/main/java/com/example/appdemo/HomeFragment.java
package com.example.appdemo; // 请替换为您的实际包名

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar; // 导入 Toolbar 类
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// HomeFragment 类继承自 Fragment，代表应用的主页内容
public class HomeFragment extends Fragment {

    private SmartRefreshLayout refreshLayout; // 刷新布局
    private RecyclerView recyclerView;         // 列表视图
    private HomeAdapter homeAdapter;           // 瀑布流适配器
    private final List<HomeItem> dataList = new ArrayList<>(); // 数据源
    private int page = 0; // 当前页码，用于模拟分页加载

    // 示例图片URL，实际项目中应从网络获取
    private final String[] imageUrls = {
            "https://picsum.photos/id/10/300/200",
            "https://picsum.photos/id/100/300/400",
            "https://picsum.photos/id/101/300/250",
            "https://picsum.photos/id/102/300/350",
            "https://picsum.photos/id/103/300/280",
            "https://picsum.photos/id/104/300/320",
            "https://picsum.photos/id/105/300/220",
            "https://picsum.photos/id/106/300/380",
            "https://picsum.photos/id/107/300/270",
            "https://picsum.photos/id/108/300/330",
            "https://picsum.photos/id/109/300/290",
            "https://picsum.photos/id/110/300/310",
            "https://picsum.photos/id/111/300/260",
            "https://picsum.photos/id/112/300/340",
            "https://picsum.photos/id/113/300/230"
    };


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 充气布局文件，将其转换为 View 对象
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 初始化 Toolbar
        Toolbar toolbar = view.findViewById(R.id.toolbar_home);
        // 如果您的 Activity 继承自 AppCompatActivity 并且没有自己的 ActionBar，
        // 可以将 Toolbar 设置为 Activity 的 ActionBar
        // ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        // 由于我们使用了 BottomNavigationView，并且可能不需要 Fragment 拥有自己的 ActionBar，
        // 简单设置标题即可。

        // 初始化视图组件
        refreshLayout = view.findViewById(R.id.refreshLayout);
        recyclerView = view.findViewById(R.id.home_recycler_view);

        // 配置 RecyclerView
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        // 初始化适配器
        homeAdapter = new HomeAdapter(R.layout.item_home, dataList);
        recyclerView.setAdapter(homeAdapter);

        // 【新增】设置列表项的点击事件监听器
        homeAdapter.setOnItemClickListener(
                new OnItemClickListener() {
                    @Override
                    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {

                        // 获取被点击的 HomeItem 对象
                        HomeItem clikedItem = dataList.get(position);

                        // 创建 Intent 准备跳转到 DetailActivity
                        Intent intent = new Intent(getContext(), DetailActivity.class);

                        // 将图片URL和标题作为 Extra 数据传递给 DetailActivity
                        intent.putExtra( DetailActivity.EXTRA_IMAGE_URL, clikedItem.getImageUrl() );

                        intent.putExtra( DetailActivity.EXTRA_ITEM_TITLE, clikedItem.getTitle());


                        // 启动 DetailActivity
                        startActivity(intent);

                        // 可以在这里添加页面跳转动画
                        // if (getActivity() != null) {
                        //     getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        // }

                    }
                }
        );


        // 设置 SmartRefreshLayout 的监听器
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshlayout) {
                page = 0;
                loadData(true);
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshlayout) {
                page++;
                loadData(false);
            }
        });

        // 首次进入页面时自动刷新数据
        refreshLayout.autoRefresh();
    }

    // 加载数据的方法
    private void loadData(final boolean isRefresh) {
        // 模拟网络请求延迟
        new Handler(Looper.getMainLooper()).postDelayed(() -> { // 使用 Lambda 表达式
            List<HomeItem> newData = new ArrayList<>();
            Random random = new Random();

            // 模拟加载 10 条数据
            for (int i = 0; i < 10; i++) {
                int height = 200 + random.nextInt(201); // 200 + (0 to 200)
                String imageUrl = imageUrls[random.nextInt(imageUrls.length)];
                newData.add(new HomeItem(imageUrl, height, "图片标题 " + (page * 10 + i + 1)));
            }

            if (isRefresh) {
                dataList.clear();
                dataList.addAll(newData);
                homeAdapter.notifyDataSetChanged();
                refreshLayout.finishRefresh(true);
                Toast.makeText(getContext(), "刷新成功", Toast.LENGTH_SHORT).show();
            } else {
                // 在添加新数据前记录当前数据量
                int startPosition = dataList.size();
                dataList.addAll(newData);
                // 使用 notifyItemRangeInserted 进行局部刷新，提高效率
                homeAdapter.notifyItemRangeInserted(startPosition, newData.size());
                refreshLayout.finishLoadMore(true);
                Toast.makeText(getContext(), "加载了更多数据", Toast.LENGTH_SHORT).show();

                if (page >= 2) { // 假设只有3页数据 (0, 1, 2)
                    refreshLayout.finishLoadMoreWithNoMoreData();
                    Toast.makeText(getContext(), "没有更多数据了", Toast.LENGTH_SHORT).show();
                }
            }
        }, 1500);
    }
}
