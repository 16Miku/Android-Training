// ImageAdapter.java (修正后)
package com.example.day6_waterfall;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.chad.library.adapter4.BaseQuickAdapter;
import com.chad.library.adapter4.viewholder.QuickViewHolder;
import com.bumptech.glide.request.target.Target;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import jp.wasabeef.glide.transformations.GrayscaleTransformation;

// 1. 构造函数不再需要传入布局ID
public class ImageAdapter extends BaseQuickAdapter<ImageItem, QuickViewHolder> {

    // 2. 必须重写 onCreateViewHolder 方法来创建视图和 ViewHolder
    @NotNull
    @Override
    protected QuickViewHolder onCreateViewHolder(@NotNull Context context, @NotNull ViewGroup parent, int viewType) {
        // 在这里传入 item 布局
        return new QuickViewHolder(R.layout.item_image, parent);
    }

    // 3. 将原来的 'convert' 方法重命名为 'onBindViewHolder'，并更新方法签名
    @Override
    protected void onBindViewHolder(@NotNull QuickViewHolder holder, int position, @org.jetbrains.annotations.Nullable ImageItem item) {
        // 如果 item 为空，则直接返回，增加代码健壮性
        if (item == null) return;

        ImageView imageView = holder.getView(R.id.imageView);

        // 设置一个最小高度，防止图片加载时布局跳动
        imageView.setMinimumHeight(item.getHeight());


        // 创建一个通用的 Glide 错误监听器
        RequestListener<Drawable> glideListener = new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, @Nullable Object model, @NonNull Target<Drawable> target, boolean isFirstResource) {
                // 在 Logcat 中打印详细的错误日志
                Log.e("GlideError", "Image load failed for URL: " + model, e);
                return false; // 返回 false 让 Glide 继续调用 .error() 中设置的占位图
            }

            @Override
            public boolean onResourceReady(@NonNull Drawable resource, @NonNull Object model, Target<Drawable> target, com.bumptech.glide.load.DataSource dataSource, boolean isFirstResource) {
                return false; // 返回 false 让 Glide 正常处理加载成功的图片
            }
        };


        // 使用 Glide 加载图片的代码逻辑保持不变
        if (Math.random() > 0.5) {
            // 圆角效果
            Glide.with(getContext())
                    .load(item.getUrl())
                    .transform(new CenterCrop(), new RoundedCorners(25)) // 25px的圆角
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_foreground)
                    .listener(glideListener) // <-- 在这里添加监听器
                    .into(imageView);
        } else {
            // 灰度效果
            Glide.with(getContext())
                    .load(item.getUrl())
                    .transform(new GrayscaleTransformation())
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_foreground)
                    .listener(glideListener) // <-- 在这里添加监听器
                    .into(imageView);
        }
    }
}