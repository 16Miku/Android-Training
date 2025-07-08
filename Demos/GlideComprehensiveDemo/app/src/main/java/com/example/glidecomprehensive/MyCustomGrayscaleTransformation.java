// MyCustomGrayscaleTransformation.java
package com.example.glidecomprehensive;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

import androidx.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;
import java.util.Objects;


/**
 * 代码讲解：
 *
 * extends BitmapTransformation: 你的自定义转换类必须继承这个抽象类。
 * ID 和 ID_BYTES: 这是一个唯一的字符串 ID，用于在磁盘缓存中区分不同的转换。每当你的转换逻辑发生变化时，都应该更新这个 ID，以确保旧的缓存失效。
 * transform(): 这是你实现图片处理逻辑的核心方法。
 * BitmapPool pool: Glide 提供的 Bitmap 对象池，非常重要。你应该尝试从池中获取可复用的 Bitmap，而不是每次都创建新的，这能显著减少内存分配和 GC。
 * toTransform: Glide 传入的原始 Bitmap。
 * 我们在这里实现了一个简单的灰度转换：通过 ColorMatrix 将饱和度设为 0，然后用 Canvas 和 Paint 将原始 Bitmap 绘制到新的 Bitmap 上。
 * equals(), hashCode(): 这两个方法必须正确实现，因为 Glide 会使用它们来判断两个转换是否相同，从而决定是否使用缓存。对于无参数的转换，我们只需比较它们的 ID。
 * updateDiskCacheKey(): 这个方法用于为磁盘缓存生成一个唯一的 Key。它会将你的转换 ID 的字节数组添加到 MessageDigest 中。这样，经过不同转换的相同原始图片会有不同的磁盘缓存条目。
 *
 */
// 自定义灰度转换，将图片转换为黑白
public class MyCustomGrayscaleTransformation extends BitmapTransformation {

    // 定义一个唯一的ID，用于磁盘缓存和 equals/hashCode 方法
    // 这个ID必须是唯一的，且每次转换逻辑改变时，应该改变这个ID，以确保缓存失效
    private static final String ID = "com.example.glidecomprehensive.MyCustomGrayscaleTransformation";
    private static final byte[] ID_BYTES = ID.getBytes(CHARSET); // 将ID转换为字节数组

    public MyCustomGrayscaleTransformation() {
        // 无参构造函数
    }

    /**
     * 核心转换逻辑。
     * @param pool 用于复用 Bitmap 的 BitmapPool
     * @param toTransform 原始的 Bitmap 对象
     * @param outWidth 目标 ImageView 的宽度
     * @param outHeight 目标 ImageView 的高度
     * @return 转换后的 Bitmap
     */
    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        // 从 BitmapPool 获取一个可复用的 Bitmap，或者创建一个新的。
        // 获取的 Bitmap 应该与原始 Bitmap 具有相同的尺寸和配置。
        Bitmap transformedBitmap = pool.get(toTransform.getWidth(), toTransform.getHeight(), toTransform.getConfig());

        // 如果池中没有可复用的 Bitmap，则创建一个新的
        if (transformedBitmap == null) {
            transformedBitmap = Bitmap.createBitmap(toTransform.getWidth(), toTransform.getHeight(), toTransform.getConfig());
        }

        // 创建 Canvas，将其绘制到 transformedBitmap 上
        Canvas canvas = new Canvas(transformedBitmap);
        // 创建 Paint，用于绘制和应用滤镜
        Paint paint = new Paint();
        // 创建 ColorMatrix，用于颜色变换
        ColorMatrix colorMatrix = new ColorMatrix();
        // 设置饱和度为 0，将颜色转换为灰度
        colorMatrix.setSaturation(0);
        // 创建 ColorMatrixColorFilter，并将其应用到 Paint
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(colorMatrix);
        paint.setColorFilter(filter);
        // 将原始 Bitmap 绘制到新创建的 Bitmap 上，并应用 Paint 中的滤镜
        canvas.drawBitmap(toTransform, 0, 0, paint);

        // 返回转换后的 Bitmap。
        // Glide 会自动将 toTransform (原始 Bitmap) 放回池中或回收。
        return transformedBitmap;
    }

    /**
     * 比较两个转换是否相等。
     * 如果转换有参数，则必须在 equals 方法中比较这些参数。
     * 对于无参数的转换，只需比较 ID。
     * @param o 另一个对象
     * @return true 如果相等，否则 false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        // 对于无参数的转换，我们只比较它们的唯一 ID
        return ID.equals(((MyCustomGrayscaleTransformation) o).ID);
    }

    /**
     * 返回转换的哈希码。
     * 如果转换有参数，则必须在 hashCode 方法中包含这些参数。
     * @return 哈希码
     */
    @Override
    public int hashCode() {
        return Objects.hash(ID); // 返回唯一 ID 的哈希码
    }

    /**
     * 更新磁盘缓存的 Key。
     * 确保不同转换的图片有不同的缓存 Key，这样它们就不会互相覆盖。
     * 如果转换有参数，也需要将参数加入到 Key 中。
     * @param messageDigest 用于生成 Key 的 MessageDigest
     */
    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        // 将转换的唯一 ID 添加到消息摘要中，作为磁盘缓存 Key 的一部分
        messageDigest.update(ID_BYTES);
    }
}
