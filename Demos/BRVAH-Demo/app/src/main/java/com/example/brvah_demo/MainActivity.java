package com.example.brvah_demo;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import java.util.ArrayList;
import java.util.List;

import com.example.brvah_demo.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MyAdapter myAdapter;
    private List<MyItem> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 1. 准备数据
        dataList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            dataList.add(new MyItem("Title " + i, "Content for item " + i + "."));
        }

        // 2. 创建 Adapter 实例 (BRVAH 4.x 构造函数不接受数据)
        myAdapter = new MyAdapter(); // 调用无参构造函数

        // 3. 配置 RecyclerView
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(myAdapter);

        // 4. 设置数据到 Adapter (BRVAH 4.x 使用 setList 方法)
        myAdapter.setList(dataList);
    }
}
