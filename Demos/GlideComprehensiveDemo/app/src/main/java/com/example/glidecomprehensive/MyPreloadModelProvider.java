// MyPreloadModelProvider.java
package com.example.glidecomprehensive;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.ListPreloader;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;

import java.util.Collections;
import java.util.List;

import com.example.glidecomprehensive.GlideApp;


// 自定义 PreloadModelProvider，用于告诉 RecyclerViewPreloader 哪些图片需要预加载

/**
 * import android.content.Context;: 导入 Context 类。
 * private final Context context;: 添加 Context 成员变量。
 *
 * public MyPreloadModelProvider(Context context, List<String> imageUrls): 修正构造函数，接收 Context 并保存。
 * implements ListPreloader.PreloadModelProvider<String>: 实现 Glide 提供的 ListPreloader.PreloadModelProvider 接口，泛型是数据模型的类型（这里是 String，代表 URL）。
 * @Override public List<String> getPreloadItems(int startPosition): 关键修正。方法签名现在完全匹配 ListPreloader.PreloadModelProvider 接口。内部逻辑也相应调整，使用 startPosition 和一个 preloadCount 来确定预加载范围。
 * getPreloadItems(): 当 RecyclerViewPreloader 需要预加载时，会调用此方法。你需要返回一个包含即将进入屏幕的图片 URL 的列表。我们这里简单地返回一个子列表。
 *
 * getPreloadRequestBuilder(): 为每个要预加载的 item（URL）创建一个 RequestBuilder。重要：
 * return GlideApp.with(context): 关键修正。现在 GlideApp.with() 接收的是一个有效的 Context 对象，解决了 找不到合适的方法 的错误。
 * load(item): 加载该 URL。
 * centerCrop(): 预加载的 `RequestBuilder` 配置应与实际显示图片的 `RequestBuilder` 保持一致，包括转换、占位符等，这样才能确保预加载到缓存的图片是正确版本。
 * diskCacheStrategy(DiskCacheStrategy.RESOURCE): 推荐将预加载的图片缓存为转换后的资源，这样当实际显示图片时，可以直接从磁盘缓存中获取。
 *
 */
public class MyPreloadModelProvider implements ListPreloader.PreloadModelProvider<String> {

    private final List<String> imageUrls; // 图片 URL 列表

    private final Context context; // 修正：添加 Context 成员变量

    /**
     * 构造函数
     * @param context Application Context 或 Activity/Fragment Context，用于 Glide 请求
     * @param imageUrls 完整的图片 URL 列表
     */
    public MyPreloadModelProvider(Context context, List<String> imageUrls) { // 修正：构造函数接收 Context
        this.context = context; // 修正：保存 Context
        this.imageUrls = imageUrls;
    }

    /**
     * 根据给定的位置和数量，返回需要预加载的图片模型列表。
     * @param startPosition 当前滚动到的起始位置
     * @return 需要预加载的图片模型列表 (这里是 URL 字符串)
     */
    @NonNull
    @Override
    // 修正：方法签名匹配 ListPreloader.PreloadModelProvider 接口
    // ListPreloader.PreloadModelProvider 的 getPreloadItems 方法只接收一个参数：startPosition
    public List<String> getPreloadItems(int startPosition) {
        // 修正：根据 startPosition 和一个固定的预加载数量来计算 endPosition
        // 这里的 preloadCount 可以根据你的需求调整，例如：
        int preloadCount = 10; // 假设每次预加载 10 个项目
        int endPosition = Math.min(imageUrls.size(), startPosition + preloadCount);

        // 如果范围无效，则返回空列表
        if (startPosition >= endPosition) {
            return Collections.emptyList();
        }

        // 返回需要预加载的子列表
        return imageUrls.subList(startPosition, endPosition);
    }


    /**
     * 为每个预加载的模型创建 RequestBuilder。
     * @param item 要预加载的图片模型 (URL 字符串)
     * @return 配置好的 RequestBuilder
     */
    @Nullable
    @Override
    public RequestBuilder<?> getPreloadRequestBuilder(@NonNull String item) {
        // 创建一个 RequestBuilder，用于预加载该图片
        // 注意：这里不需要 into() 方法，因为是预加载到缓存
        // 确保预加载的 RequestBuilder 配置与实际加载 RequestBuilder 保持一致
        return GlideApp.with(context) // 修正：使用传入的 Context
                .load(item) // 加载图片 URL
                .centerCrop() // 预加载时也应用相同的转换，以确保缓存的是转换后的图片
                .diskCacheStrategy(com.bumptech.glide.load.engine.DiskCacheStrategy.RESOURCE); // 缓存转换后的资源
    }
}



