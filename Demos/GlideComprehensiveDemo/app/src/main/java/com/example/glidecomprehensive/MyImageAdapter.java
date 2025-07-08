// MyImageAdapter.java
package com.example.glidecomprehensive;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader;
import com.bumptech.glide.ListPreloader;
import com.bumptech.glide.util.ViewPreloadSizeProvider; // 导入 ViewPreloadSizeProvider


import com.example.glidecomprehensive.GlideApp;

import java.util.List;

// RecyclerView 的适配器，用于在列表中显示图片


/**
 * extends RecyclerView.Adapter<MyImageAdapter.ImageViewHolder>: 适配器继承 RecyclerView.Adapter。
 * ImageViewHolder: 静态内部类，持有 item_image.xml 布局中的 ImageView 和 TextView 引用。
 * onBindViewHolder(): 这是核心方法。
 * GlideApp.with(context): 使用 GlideApp 发起图片加载请求，它会应用我们在 MyApplication 中定义的全局配置。传入 Context（通常是 Activity 或 Fragment）以确保生命周期绑定。
 * load(imageUrl): 加载当前项的图片 URL。
 * placeholder() 和 error(): 设置占位符和错误图。
 * centerCrop(): 应用图片转换。
 * into(holder.imageView): 将图片加载到 ViewHolder 中的 ImageView。
 * preloadSizeProvider.setView(holder.imageView): 重要。这行代码用于 RecyclerViewPreloader。它告诉 ViewPreloadSizeProvider 当前 ImageView 的尺寸，以便 Preloader 能够预加载正确尺寸的图片。
 */
public class MyImageAdapter extends RecyclerView.Adapter<MyImageAdapter.ImageViewHolder> {

    private final List<String> imageUrls; // 图片URL列表
    private final Context context; // 用于 Glide 的 Context
    // 用于 RecyclerViewPreloader，它需要知道每个 item 的尺寸
    private final ViewPreloadSizeProvider<String> preloadSizeProvider;

    /**
     * 构造函数
     * @param context Activity 或 Fragment 的 Context，用于 Glide 的生命周期管理
     * @param imageUrls 图片 URL 列表
     * @param preloadSizeProvider 用于 RecyclerViewPreloader 的尺寸提供器
     */
    public MyImageAdapter(Context context, List<String> imageUrls, ViewPreloadSizeProvider<String> preloadSizeProvider) {
        this.context = context;
        this.imageUrls = imageUrls;
        this.preloadSizeProvider = preloadSizeProvider;
    }

    /**
     * 核心方法 1: 创建并返回 ViewHolder !!!
     * 当 RecyclerView 需要一个新的列表项视图时调用此方法
     * @param parent 父视图组
     * @param viewType 视图类型
     * @return 新创建的 ImageViewHolder
     */
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 从 item_image.xml 布局文件创建视图
        // 使用 LayoutInflater 加载列表项布局文件
        // parent.getContext() 获取 Context
        // R.layout.item_image 是列表项布局文件ID
        // parent 是父容器 (RecyclerView)
        // false 表示不立即附加到父容器，RecyclerView 会自己处理附加
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);

        // 创建并返回一个新的 ViewHolder 实例，将加载的视图传递给它
        return new ImageViewHolder(view);
    }

    /**
     * 核心方法 2: 将数据绑定到 ViewHolder 中的视图上 !!!
     * 当 RecyclerView 需要显示指定位置的数据时调用此方法
     * @param holder 要绑定的 ViewHolder
     * @param position 数据在列表中的位置
     */
    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {

        // 获取当前位置对应的数据项
        String imageUrl = imageUrls.get(position);

        // 将数据绑定到 ViewHolder 中持有的子视图上
        holder.textView.setText("图片编号: " + (position + 1)); // 设置图片标题

        // **在 onBindViewHolder 中使用 Glide 加载图片**
        // Glide 会自动处理 ViewHolder 的复用和旧请求的取消，避免图片错位。
        // 使用 GlideApp (如果已生成) 或 Glide
        GlideApp.with(context) // 传入 Activity 或 Fragment 的 Context，确保生命周期绑定
                .load(imageUrl) // 加载图片 URL
                .placeholder(R.drawable.placeholder_image) // 设置占位符
                .error(R.drawable.error_image) // 设置错误图片
                .centerCrop() // 应用 CenterCrop 转换
                .into(holder.imageView); // 加载到 ImageView

        // **为 RecyclerViewPreloader 注册视图尺寸**
        // 确保在图片加载前调用 setView，这样 Preloader 才能获取到 ImageView 的实际尺寸，用于预加载
        preloadSizeProvider.setView(holder.imageView);
    }

    /**
     * 返回列表中的总项数
     * @return 图片 URL 列表的大小
     */
    @Override
    public int getItemCount() {
        return imageUrls.size();
    }

    /**
     * ViewHolder 定义。
     * ViewHolder 类：静态内部类，用于持有列表项布局中的子视图引用 !!!继承自 RecyclerView.ViewHolder
     */
    static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        // 构造函数，接收列表项的根视图 (itemView)
        ImageViewHolder(@NonNull View itemView) {

            // 调用父类构造函数
            super(itemView);

            // 绑定布局中的视图
            imageView = itemView.findViewById(R.id.item_image_view);
            textView = itemView.findViewById(R.id.item_text_view);

            // !!! 注意：这里通常不直接设置点击监听器，而是在 onBindViewHolder 中设置 !!!
            // 因为 ViewHolder 是重用的，直接在这里设置监听器会导致点击事件处理逻辑错误
            // 如果需要在 ViewHolder 内部处理点击，可以实现 View.OnClickListener 接口
            // 并将监听器设置给 itemView 或其子视图


        }
    }
}
