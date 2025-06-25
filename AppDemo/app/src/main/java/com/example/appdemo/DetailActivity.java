// app/src/main/java/com/example/appdemo/DetailActivity.java
package com.example.appdemo; // 请替换为您的实际包名

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar; // 导入 Toolbar

import com.bumptech.glide.Glide; // 导入 Glide

// DetailActivity 类继承自 AppCompatActivity，用于显示列表项的详细信息
public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_IMAGE_URL = "extra_image_url"; // 用于传递图片URL的Intent Extra键
    public static final String EXTRA_ITEM_TITLE = "extra_item_title"; // 用于传递标题的Intent Extra键

    private ImageView detailImage; // 详情图片视图
    private TextView detailTitle;  // 详情标题文本视图
    private TextView detailDescription; // 详情描述文本视图

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置Activity的布局文件
        setContentView(R.layout.activity_detail);

        // 初始化 Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbar); // 将 Toolbar 设置为 Activity 的 ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 显示返回按钮
            getSupportActionBar().setTitle("详情页"); // 设置 Toolbar 标题
        }

        // 初始化视图组件
        detailImage = findViewById(R.id.detail_image);
        detailTitle = findViewById(R.id.detail_title);
        detailDescription = findViewById(R.id.detail_description);

        // 获取从 Intent 传递过来的数据
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String imageUrl = extras.getString(EXTRA_IMAGE_URL); // 获取图片URL
            String itemTitle = extras.getString(EXTRA_ITEM_TITLE); // 获取标题

            // 使用 Glide 加载图片到 ImageView
            if (imageUrl != null && !imageUrl.isEmpty()) {
                Glide.with(this) // 使用 Activity 作为上下文
                        .load(imageUrl)
                        .into(detailImage);
            }

            // 设置标题
            if (itemTitle != null && !itemTitle.isEmpty()) {
                detailTitle.setText(itemTitle);
            }

            // 设置一个简单的描述，实际中可以传递更多数据
            detailDescription.setText("这是关于 \"" + itemTitle + "\" 的详细信息。");
        }
    }

    // 处理 Toolbar 上返回按钮的点击事件
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed(); // 当点击返回按钮时，模拟按下系统返回键
        return true;
    }
}
