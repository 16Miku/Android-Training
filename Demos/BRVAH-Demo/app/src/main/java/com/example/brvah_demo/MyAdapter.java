package com.example.brvah_demo;

// 导入 BRVAH 4.x 的新包名
import com.chad.library.adapter4.BaseQuickAdapter;
import com.chad.library.adapter4.viewholder.QuickViewHolder; // BRVAH 4.x 推荐的通用 ViewHolder
// 如果你使用了 ViewBinding，可以考虑使用 BindingHolder
// import com.chad.library.adapter4.viewholder.BindingHolder;
// import com.example.brvah_demo.databinding.ItemMyLayoutBinding; // 导入你的 Item 布局的 Binding 类

import android.view.ViewGroup;
import android.content.Context;
import android.widget.TextView; // 导入 TextView

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

// 泛型参数：第一个是数据类型 (MyItem)，第二个是 ViewHolder 类型 (QuickViewHolder)
public class MyAdapter extends BaseQuickAdapter<MyItem, QuickViewHolder> {

    // BRVAH 4.x 的构造函数通常是无参的
    public MyAdapter() {
        // 4.x 构造函数不再直接传入布局ID
        // 布局ID的指定通常在 onCreateViewHolder 中完成
        super();
    }

    // BRVAH 4.x 要求实现 onCreateViewHolder 方法
    // 在这里创建并返回你的 ViewHolder 实例
    @Override
    protected QuickViewHolder onCreateViewHolder(@androidx.annotation.NonNull Context context, @androidx.annotation.NonNull ViewGroup parent, int viewType) {
        // 使用 QuickViewHolder.createFrom(parent, layoutId) 来创建 ViewHolder
        // 这里的 R.layout.item_my_layout 就是你的列表项布局文件
        return QuickViewHolder.createFrom(parent, R.layout.item_my_layout);

        // 如果你使用 ViewBinding，可以这样创建 BindingHolder
        // ItemMyLayoutBinding binding = ItemMyLayoutBinding.inflate(LayoutInflater.from(context), parent, false);
        // return new BindingHolder(binding.getRoot());
    }

    // 核心方法：将数据绑定到视图
    // 在 BRVAH 4.x 中，convert 方法的参数是 QuickViewHolder
    @Override
    protected void convert(@androidx.annotation.NonNull QuickViewHolder holder, MyItem item) {
        // holder.setText() 方法在 QuickViewHolder 中是存在的，但需要确保导入正确
        // 检查你的 BRVAH 4.x 版本是否支持 holder.setText()
        // 如果不支持，你需要通过 holder.getView() 获取 TextView 再设置文本
        holder.setText(R.id.tv_title, item.getTitle());
        holder.setText(R.id.tv_content, item.getContent());

        // 如果 holder.setText() 仍然报错，请使用以下方式：
        // TextView tvTitle = holder.getView(R.id.tv_title);
        // if (tvTitle != null) {
        //     tvTitle.setText(item.getTitle());
        // }
        // TextView tvContent = holder.getView(R.id.tv_content);
        // if (tvContent != null) {
        //     tvContent.setText(item.getContent());
        // }

        // 也可以设置图片、点击事件等
        // holder.setImageResource(R.id.iv_icon, R.drawable.some_icon);
        // holder.itemView.setOnClickListener(v -> { /* item click logic */ });
    }

    @Override
    protected void onBindViewHolder(@NonNull QuickViewHolder quickViewHolder, int i, @Nullable MyItem myItem) {

    }
}
