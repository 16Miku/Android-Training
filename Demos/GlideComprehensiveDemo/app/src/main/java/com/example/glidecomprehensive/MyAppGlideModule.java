// MyAppGlideModule.java - 新建并修正后
package com.example.glidecomprehensive;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule; // 导入 @GlideModule 注解
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.AppGlideModule; // 导入正确的 AppGlideModule 类

/**
 * 这是应用的全局 Glide 配置模块。
 * 必须添加 @GlideModule 注解，让 Glide 的注解处理器在编译时识别此模块并生成 GlideApp 类。
 * 此类必须继承 com.bumptech.glide.module.AppGlideModule。
 * 一个应用中只能有一个 AppGlideModule。
 *
 * 代码讲解：
 * public final class MyAppGlideModule extends AppGlideModule: 关键修正。现在 MyAppGlideModule 正确地继承了 AppGlideModule。
 * @GlideModule: 这个注解仍然是必须的，它会触发 Glide 的注解处理器生成 GlideApp。
 * applyOptions() 和 registerComponents(): 保持不变，它们现在在正确的位置。
 * TAG: 修改了 TAG，以避免与 MyApplication 的 TAG 冲突。
 */
@GlideModule
public final class MyAppGlideModule extends AppGlideModule { // 修正：正确继承 AppGlideModule

    private static final String TAG = "MyAppGlideModule"; // 修改 TAG 以区分

    /**
     * 配置全局选项，例如内存缓存大小、磁盘缓存大小和位置。
     * 这个方法在 Glide 初始化时被调用。
     * @param context 应用上下文
     * @param builder Glide 构建器，用于设置全局选项
     */
    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        Log.d(TAG, "applyOptions: Configuring Glide global options.");

        // 1. 配置内存缓存大小
        MemorySizeCalculator calculator = new MemorySizeCalculator.Builder(context)
                .setMemoryCacheScreens(2.0f) // 设置内存缓存大小为2个屏幕的大小
                .setBitmapPoolScreens(3.0f) // 设置 BitmapPool 大小为3个屏幕的大小
                .build();

        // 设置内存缓存，使用 LruResourceCache (LRU 算法)
        builder.setMemoryCache(new LruResourceCache(calculator.getMemoryCacheSize()));
        // 设置 BitmapPool，使用 LruBitmapPool (LRU 算法)，用于复用 Bitmap 对象，减少 GC 压力
        builder.setBitmapPool(new LruBitmapPool(calculator.getBitmapPoolSize()));

        // 2. 配置磁盘缓存
        int diskCacheSizeBytes = 1024 * 1024 * 100; // 100 MB
        String diskCacheFolderName = "glide_images"; // 磁盘缓存文件夹名称

        // 设置内部存储的磁盘缓存 (推荐，应用私有目录，无需额外权限)
        builder.setDiskCache(
                new DiskLruCacheFactory(context.getCacheDir().getPath() + "/" + diskCacheFolderName, diskCacheSizeBytes)
        );

        // 3. 可以在这里设置其他全局选项，例如日志级别等
        // builder.setLogLevel(Log.DEBUG); // 设置 Glide 的日志级别，用于调试
    }

    /**
     * 注册自定义组件，例如 ModelLoader、ResourceDecoder 等。
     * 这个方法在 Glide 初始化时被调用。
     * @param context 应用上下文
     * @param glide Glide 实例
     * @param registry 注册表，用于注册自定义组件
     */
    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        Log.d(TAG, "registerComponents: Registering custom Glide components.");
        // 在这里注册你的自定义 ModelLoader、ResourceDecoder 等
        // 例如：
        // registry.append(MyCustomDataModel.class, InputStream.class, new MyCustomDataLoader.Factory());
    }

    /**
     * 禁用清单解析，推荐设置为 true，可以加快初始化速度。
     * 如果你的应用或任何库中没有其他 LibraryGlideModule，可以设置为 true。
     * @return true 表示禁用清单解析
     */
    @Override
    public boolean isManifestParsingEnabled() {
        return false; // 禁用清单解析，因为我们已经通过 @GlideModule 注解指定了
    }
}
