// app/src/main/java/com/example/day6_waterfall/ImageAdapter.java
package com.example.day6_waterfall;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

import jp.wasabeef.glide.transformations.GrayscaleTransformation;
// app/src/main/java/com.example/day6_waterfall/ImageAdapter.java

// ... (其他导入和类定义保持不变)

public class ImageAdapter extends BaseQuickAdapter<ImageItem, QuickViewHolder> {

    private int mItemWidth;

    public ImageAdapter(int itemWidth) {
        super();
        this.mItemWidth = itemWidth;
    }

    @NotNull
    @Override
    protected QuickViewHolder onCreateViewHolder(@NotNull Context context, @NotNull ViewGroup parent, int viewType) {
        return new QuickViewHolder(R.layout.item_image, parent);
    }

    @Override
    protected void onBindViewHolder(@NotNull QuickViewHolder holder, int position, @org.jetbrains.annotations.Nullable ImageItem item) {
        if (item == null) return;

        ImageView imageView = holder.getView(R.id.imageView);
        TextView descriptionView = holder.getView(R.id.tv_description);

        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        layoutParams.width = mItemWidth;
        layoutParams.height = item.getHeight();
        imageView.setLayoutParams(layoutParams);

        RequestListener<Drawable> glideListener = new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, @Nullable Object model, @NonNull Target<Drawable> target, boolean isFirstResource) {
                Log.e("GlideError", "Image load failed for URL: " + model + ", Exception: " + e.getMessage());
                if (e != null && e.getRootCauses() != null) {
                    for (Throwable rootCause : e.getRootCauses()) {
                        Log.e("GlideError", "Root Cause: " + rootCause.getMessage());
                    }
                }
                return false;
            }

            @Override
            public boolean onResourceReady(@NonNull Drawable resource, @NonNull Object model, Target<Drawable> target, com.bumptech.glide.load.DataSource dataSource, boolean isFirstResource) {
                return false;
            }
        };

        // 使用 Glide 加载图片，并随机应用圆角或灰度效果
        // *** 关键改进：添加 .diskCacheStrategy(DiskCacheStrategy.NONE) 和 .skipMemoryCache(true) ***
        // 这会强制 Glide 每次都从网络加载，不使用任何缓存
        if (Math.random() > 0.5) {
            Glide.with(getContext())
                    .load(item.getUrl())
                    .transform(new CenterCrop(), new RoundedCorners(25))
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.error_image)
                    .listener(glideListener)
                    .diskCacheStrategy(com.bumptech.glide.load.engine.DiskCacheStrategy.NONE) // 不使用磁盘缓存
                    .skipMemoryCache(true) // 不使用内存缓存
                    .into(imageView);
        } else {
            Glide.with(getContext())
                    .load(item.getUrl())
                    .transform(new GrayscaleTransformation())
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.error_image)
                    .listener(glideListener)
                    .diskCacheStrategy(com.bumptech.glide.load.engine.DiskCacheStrategy.NONE) // 不使用磁盘缓存
                    .skipMemoryCache(true) // 不使用内存缓存
                    .into(imageView);
        }

        String displayUrl = item.getUrl().length() > 40 ? item.getUrl().substring(0, 40) + "..." : item.getUrl();
        descriptionView.setText("H: " + item.getHeight() + "px\nURL: " + displayUrl);
    }
}
