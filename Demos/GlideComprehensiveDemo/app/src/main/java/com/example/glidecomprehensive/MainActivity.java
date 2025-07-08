// MainActivity.java
package com.example.glidecomprehensive;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide; // 导入 Glide 类
// 确保导入生成的 GlideApp 类
import com.example.glidecomprehensive.GlideApp; // 替换为你的实际包名

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader;
import com.bumptech.glide.util.ViewPreloadSizeProvider;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors; // 导入线程池工具类


/**
 * 代码讲解：
 *
 * 权限请求： 在 onCreate 中调用 checkAndRequestPermissions()，在 onRequestPermissionsResult 中处理权限结果。这是 Android 6.0+ 运行时权限的标准做法。
 * `runGlideDemos()`: 权限获取成功后，所有 Glide 演示方法都在这里调用。
 * `demoBasicLoad()`: 最简单的加载，使用 GlideApp.with(this).load(URL).into(ImageView)。
 * `demoPlaceholderAndError()`: 演示 placeholder() 和 error() 的使用。
 * `demoLoadFromVariousSources()`:
 * load(R.mipmap.ic_launcher): 从 Drawable 资源加载。
 * load(File): 从本地文件加载。这里我们有一个 downloadImageToFileForDemo() 方法在后台下载一张图片到应用私有外部存储目录，然后加载它。
 * load(byte[]): 演示从字节数组加载图片。
 *
 * `demoImageTransformations()`:
 * centerCrop(), fitCenter(), circleCrop(): 内置转换。
 * transform(new CenterCrop(), new RoundedCorners(cornerRadius)): 组合多个转换。
 * transform(new MyCustomGrayscaleTransformation()): 应用我们自定义的灰度转换。
 *
 * `demoCacheManagement()`:
 * Glide.get(this).clearMemory(): 清除内存缓存，必须在主线程。
 * new AsyncTask<Void, Void, Void>() { ... }.execute(): 清除磁盘缓存，必须在后台线程，因为它涉及文件 I/O。
 *
 * `demoRequestPriority()`: 演示 priority() 方法设置请求优先级。
 * `demoThumbnailLoading()`: 演示 thumbnail() 方法，先加载小图（这里是另一个 URL），再加载大图。
 * `demoTransitionAnimation()`: 演示 transition() 方法，使用 DrawableCrossFadeFactory 实现交叉淡入动画。
 * `demoRequestListener()`: 演示 listener() 方法，可以在图片加载成功或失败时执行自定义逻辑。注意 onLoadFailed 和 onResourceReady 的返回值。
 * `demoOverrideSize()`: 演示 override() 方法，强制 Glide 将图片缩放到指定尺寸，而不依赖 ImageView 的实际尺寸。
 *
 * `setupRecyclerViewWithPreloader()`:
 * 初始化 RecyclerView、MyImageAdapter 和 ViewPreloadSizeProvider。
 * 创建 MyPreloadModelProvider 和 RecyclerViewPreloader。
 * recyclerViewImages.addOnScrollListener(preloader): 将 RecyclerViewPreloader 添加为 RecyclerView 的滚动监听器，实现预加载。
 *
 * `downloadImageToFileForDemo()`: 辅助方法，在后台下载一张图片并保存到本地文件，用于演示从文件加载。
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "GlideDemo";
    private static final int REQUEST_CODE_PERMISSIONS = 101; // 权限请求码

    // 示例图片 URL
    private final String IMAGE_URL_VALID = "https://picsum.photos/id/237/800/600";   // 有效 URL，用于基本加载、过渡动画、监听器成功演示
    private final String IMAGE_URL_INVALID = "https://example.com/invalid_image.jpg"; // 无效 URL，用于监听器失败演示
    private final String IMAGE_URL_LARGE = "https://picsum.photos/id/237/800/600"; // 较大图片，用于转换、强制覆盖尺寸、缩略图的大图
    private final String IMAGE_URL_THUMBNAIL = "https://picsum.photos/id/237/50/38";  // 缩略图，用于缩略图演示

    private final String IMAGE_URL_ASUKA = "https://bkimg.cdn.bcebos.com/pic/c2cec3fdfc039245d688f58614ccb3c27d1ed31b8fb8?x-bce-process=image/format,f_auto/watermark,image_d2F0ZXIvYmFpa2UyNzI,g_7,xp_5,yp_5,P_20/resize,m_lfit,limit_1,h_1080";


    // UI 组件
    private ImageView imageBasicLoad;
    private ImageView imagePlaceholderErrorValid;
    private ImageView imagePlaceholderErrorInvalid;
    private ImageView imageFromDrawable;
    private ImageView imageFromFile;
    private ImageView imageFromBytes;
    private ImageView imageTransformCenterCrop;
    private ImageView imageTransformFitCenter;
    private ImageView imageTransformCircle;
    private ImageView imageTransformRounded;
    private ImageView imageTransformCustom;
    private Button btnClearMemoryCache;
    private Button btnClearDiskCache;
    private ImageView imagePriorityHigh;
    private ImageView imagePriorityNormal;
    private ImageView imagePriorityLow;
    private ImageView imageThumbnail;
    private ImageView imageTransition;
    private ImageView imageListener;
    private ImageView imageOverride;
    private RecyclerView recyclerViewImages;

    // RecyclerView 相关
    private MyImageAdapter myImageAdapter;
    private List<String> recyclerViewImageUrls = new ArrayList<>();
    private ViewPreloadSizeProvider<String> preloadSizeProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化所有 UI 组件
        initViews();

        // 检查并请求权限
        checkAndRequestPermissions();
    }

    private void initViews() {
        imageBasicLoad = findViewById(R.id.image_basic_load);
        imagePlaceholderErrorValid = findViewById(R.id.image_placeholder_error_valid);
        imagePlaceholderErrorInvalid = findViewById(R.id.image_placeholder_error_invalid);
        imageFromDrawable = findViewById(R.id.image_from_drawable);
        imageFromFile = findViewById(R.id.image_from_file);
        imageFromBytes = findViewById(R.id.image_from_bytes);
        imageTransformCenterCrop = findViewById(R.id.image_transform_center_crop);
        imageTransformFitCenter = findViewById(R.id.image_transform_fit_center);
        imageTransformCircle = findViewById(R.id.image_transform_circle);
        imageTransformRounded = findViewById(R.id.image_transform_rounded);
        imageTransformCustom = findViewById(R.id.image_transform_custom);
        btnClearMemoryCache = findViewById(R.id.btn_clear_memory_cache);
        btnClearDiskCache = findViewById(R.id.btn_clear_disk_cache);
        imagePriorityHigh = findViewById(R.id.image_priority_high);
        imagePriorityNormal = findViewById(R.id.image_priority_normal);
        imagePriorityLow = findViewById(R.id.image_priority_low);
        imageThumbnail = findViewById(R.id.image_thumbnail);
        imageTransition = findViewById(R.id.image_transition);
        imageListener = findViewById(R.id.image_listener);
        imageOverride = findViewById(R.id.image_override);
        recyclerViewImages = findViewById(R.id.recycler_view_images);
    }

    /**
     * 检查并请求必要的运行时权限。
     */
    private void checkAndRequestPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED ||
                // For Android 13+
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES)
                        != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_MEDIA_IMAGES // For Android 13+
                    },
                    REQUEST_CODE_PERMISSIONS);
        } else {
            // 权限已授予，可以执行 Glide 演示
            runGlideDemos();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 权限已授予
                Toast.makeText(this, "存储权限已授予", Toast.LENGTH_SHORT).show();
                runGlideDemos();
            } else {
                // 权限被拒绝
                Toast.makeText(this, "存储权限被拒绝，部分功能可能无法正常工作", Toast.LENGTH_LONG).show();
                // 即使权限被拒绝，也尝试运行演示，但文件加载部分会失败
                runGlideDemos();
            }
        }
    }

    /**
     * 执行所有 Glide 演示功能。
     */
    private void runGlideDemos() {
        // 1. 基本图片加载
        demoBasicLoad();

        // 2. 占位符与错误图
        demoPlaceholderAndError();

        // 3. 不同来源加载图片
        demoLoadFromVariousSources();

        // 4. 图片转换
        demoImageTransformations();

        // 5. 缓存管理
        demoCacheManagement();

        // 6. 请求优先级
        demoRequestPriority();

        // 7. 缩略图
        demoThumbnailLoading();

        // 8. 过渡动画
        demoTransitionAnimation();

        // 9. 请求监听器
        demoRequestListener();

        // 10. 强制覆盖尺寸
        demoOverrideSize();

        // 11. RecyclerView 集成与预加载
        setupRecyclerViewWithPreloader();

        // 预加载一些图片到本地文件，用于演示从文件加载
        downloadImageToFileForDemo();
    }

    // =====================================================================
    // 各个 Glide 演示方法
    // =====================================================================

    /**
     * 演示基本图片加载：从 URL 加载到 ImageView。
     */
    private void demoBasicLoad() {
        Log.d(TAG, "演示：基本图片加载");
        GlideApp.with(this) // 使用生成的 GlideApp
                .load(IMAGE_URL_VALID)
                .into(imageBasicLoad);
    }

    /**
     * 演示占位符和错误图：
     * 1. 加载有效 URL，显示占位符后显示图片。
     * 2. 加载无效 URL，显示占位符后显示错误图。
     */
    private void demoPlaceholderAndError() {
        Log.d(TAG, "演示：占位符与错误图");
        // 有效 URL
        GlideApp.with(this)
                .load(IMAGE_URL_VALID)
                .placeholder(R.drawable.placeholder_image) // 加载时的占位符
                .error(R.drawable.error_image) // 加载失败时的错误图
                .into(imagePlaceholderErrorValid);

        // 无效 URL
        GlideApp.with(this)
                .load(IMAGE_URL_INVALID)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image) // 将显示此错误图
                .into(imagePlaceholderErrorInvalid);
    }

    /**
     * 演示从不同来源加载图片：Drawable 资源、本地文件、字节数组。
     */
    private void demoLoadFromVariousSources() {
        Log.d(TAG, "演示：从不同来源加载图片");

        // 从 Drawable 资源加载
        GlideApp.with(this)
                .load(R.mipmap.ic_launcher) // 加载应用图标
                .into(imageFromDrawable);

        // 从本地文件加载 (需要先确保文件存在并有权限)
        File localImageFile = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "asuka.jpg");
        if (localImageFile.exists()) {
            GlideApp.with(this)
                    .load(localImageFile)
                    .into(imageFromFile);
        } else {
            Toast.makeText(this, "本地文件 'asuka.jpg' 不存在，请稍候...", Toast.LENGTH_SHORT).show();
            // 如果文件不存在，我们会在权限检查后下载它
        }

        // 从字节数组加载 (模拟从网络获取的图片字节数据)
        // 这是一个模拟方法，实际中可能来自网络请求
        new AsyncTask<Void, Void, byte[]>() {
            @Override
            protected byte[] doInBackground(Void... voids) {
                return getBytesFromDrawable(R.drawable.placeholder_image); // 将占位图转换为字节数组
            }

            @Override
            protected void onPostExecute(byte[] bytes) {
                if (bytes != null && bytes.length > 0) {
                    GlideApp.with(MainActivity.this)
                            .load(bytes)
                            .into(imageFromBytes);
                }
            }
        }.execute();
    }

    /**
     * 演示各种图片转换：CenterCrop, FitCenter, CircleCrop, RoundedCorners, 自定义灰度转换。
     */
    private void demoImageTransformations() {
        Log.d(TAG, "演示：图片转换");
        String transformImageUrl = IMAGE_URL_LARGE; // 使用一个较大的图片进行转换演示

        // CenterCrop
        GlideApp.with(this)
                .load(transformImageUrl)
                .centerCrop()
                .into(imageTransformCenterCrop);

        // FitCenter
        GlideApp.with(this)
                .load(transformImageUrl)
                .fitCenter()
                .into(imageTransformFitCenter);

        // CircleCrop
        GlideApp.with(this)
                .load(transformImageUrl)
                .circleCrop()
                .into(imageTransformCircle);

        // RoundedCorners (圆角)
        int cornerRadius = 25; // 25dp 的圆角
        GlideApp.with(this)
                .load(transformImageUrl)
                .transform(new CenterCrop(), new RoundedCorners(cornerRadius)) // 组合转换
                .into(imageTransformRounded);

        // 自定义转换 (灰度)
        GlideApp.with(this)
                .load(transformImageUrl)
                .transform(new MyCustomGrayscaleTransformation()) // 应用自定义灰度转换
                .into(imageTransformCustom);
    }

    /**
     * 演示缓存管理：清除内存缓存和磁盘缓存。
     */
    private void demoCacheManagement() {
        Log.d(TAG, "演示：缓存管理");
        btnClearMemoryCache.setOnClickListener(v -> {
            // 清除内存缓存，必须在主线程调用
            Glide.get(this).clearMemory();
            Toast.makeText(this, "内存缓存已清除", Toast.LENGTH_SHORT).show();
        });

        btnClearDiskCache.setOnClickListener(v -> {
            // 清除磁盘缓存，必须在后台线程调用
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    Glide.get(MainActivity.this).clearDiskCache();
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    Toast.makeText(MainActivity.this, "磁盘缓存已清除", Toast.LENGTH_SHORT).show();
                }
            }.execute();
        });
    }

    /**
     * 演示请求优先级：高、普通、低。
     */
    private void demoRequestPriority() {
        Log.d(TAG, "演示：请求优先级");
        String urlBase = "https://via.placeholder.com/100/";

        // 高优先级
        GlideApp.with(this)
                .load(urlBase + "FF0000/FFFFFF?text=HIGH")
                .priority(Priority.HIGH)
                .into(imagePriorityHigh);

        // 正常优先级 (默认)
        GlideApp.with(this)
                .load(urlBase + "00FF00/FFFFFF?text=NORMAL")
                .priority(Priority.NORMAL)
                .into(imagePriorityNormal);

        // 低优先级
        GlideApp.with(this)
                .load(urlBase + "0000FF/FFFFFF?text=LOW")
                .priority(Priority.LOW)
                .into(imagePriorityLow);
    }

    /**
     * 演示缩略图加载：先加载小图，再加载大图。
     */
    private void demoThumbnailLoading() {
        Log.d(TAG, "演示：缩略图");
        GlideApp.with(this)
                .load(IMAGE_URL_LARGE) // 完整大图
                .thumbnail(
                        GlideApp.with(this) // 缩略图请求
                                .load(IMAGE_URL_THUMBNAIL) // 缩略图 URL
                )
                .into(imageThumbnail);

        // 也可以使用缩放因子生成缩略图 (0.1f 表示加载原始图片 10% 大小的缩略图)
        // GlideApp.with(this)
        //         .load(IMAGE_URL_LARGE)
        //         .thumbnail(0.1f)
        //         .into(imageThumbnail);
    }

    /**
     * 演示过渡动画：交叉淡入效果。
     */
    private void demoTransitionAnimation() {
        Log.d(TAG, "演示：过渡动画");
        DrawableCrossFadeFactory factory =
                new DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build();

        GlideApp.with(this)
                .load(IMAGE_URL_VALID)
                .transition(DrawableTransitionOptions.with(factory)) // 应用交叉淡入动画
                .into(imageTransition);

        // 如果不想要动画，可以使用 .dontAnimate()
        // GlideApp.with(this).load(IMAGE_URL_VALID).dontAnimate().into(imageTransition);
    }

    /**
     * 演示请求监听器：加载成功或失败时显示 Toast。
     */
    private void demoRequestListener() {
        Log.d(TAG, "演示：请求监听器");
        GlideApp.with(this)
                .load(IMAGE_URL_INVALID) // 使用无效 URL 演示失败情况
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        // 加载失败时回调
                        Log.e(TAG, "图片加载失败: " + (e != null ? e.getMessage() : "未知错误"));
                        Toast.makeText(MainActivity.this, "监听器：图片加载失败！", Toast.LENGTH_SHORT).show();
                        // 返回 false 表示 Glide 会继续调用 error() 设置的 Drawable
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        // 加载成功时回调
                        Log.d(TAG, "图片加载成功！数据源: " + dataSource.name());
                        Toast.makeText(MainActivity.this, "监听器：图片加载成功！", Toast.LENGTH_SHORT).show();
                        // 返回 false 表示 Glide 会继续将资源设置到目标上
                        return false;
                    }
                })
                .into(imageListener);
    }

    /**
     * 演示强制覆盖尺寸：使用 override() 方法。
     */
    private void demoOverrideSize() {
        Log.d(TAG, "演示：强制覆盖尺寸");
        GlideApp.with(this)
                .load(IMAGE_URL_LARGE)
                .override(100, 100) // 强制将图片缩放到 100x100 像素
                .into(imageOverride);
    }

    /**
     * 设置 RecyclerView 并集成 Glide Preloader。
     */
    private void setupRecyclerViewWithPreloader() {
        Log.d(TAG, "演示：RecyclerView 集成与预加载");

        // 填充 RecyclerView 的图片 URL 列表
        for (int i = 0; i < 50; i++) {
            recyclerViewImageUrls.add("https://picsum.photos/id/" + (100 + i) + "/300/200");
        }

        recyclerViewImages.setLayoutManager(new LinearLayoutManager(this));

        // 初始化 ViewPreloadSizeProvider，用于提供预加载图片的大小
        preloadSizeProvider = new ViewPreloadSizeProvider<>();
        myImageAdapter = new MyImageAdapter(this, recyclerViewImageUrls, preloadSizeProvider);
        recyclerViewImages.setAdapter(myImageAdapter);

        // 创建 PreloadModelProvider，告诉 Preloader 哪些模型需要预加载
        // 修正：将 ApplicationContext 传递给 MyPreloadModelProvider
        MyPreloadModelProvider preloadModelProvider = new MyPreloadModelProvider(getApplicationContext(), recyclerViewImageUrls);

        // 创建 RecyclerViewPreloader
        // 最后一个参数是预加载数量：提前预加载 10 个项目
        RecyclerViewPreloader<String> preloader = new RecyclerViewPreloader<>(
                GlideApp.with(this), // 使用 GlideApp 实例
                preloadModelProvider, // 自定义 ModelProvider
                preloadSizeProvider, // 大小提供者
                10 // 预加载数量
        );

        // 将 Preloader 添加到 RecyclerView 的滚动监听器中
        recyclerViewImages.addOnScrollListener(preloader);
    }

    // =====================================================================
    // 辅助方法
    // =====================================================================

    /**
     * 辅助方法：将 Drawable 转换为字节数组。
     * @param drawableResId Drawable 资源 ID
     * @return 字节数组
     */
    private byte[] getBytesFromDrawable(int drawableResId) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), drawableResId);
        if (bitmap != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            return stream.toByteArray();
        }
        return null;
    }

    /**
     * 辅助方法：下载一张图片到本地文件，用于演示从文件加载。
     * 在后台线程执行。
     */
    private void downloadImageToFileForDemo() {
        // 确保有写入外部存储的权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Log.w(TAG, "无写入外部存储权限，无法下载图片到本地文件。");
            return;
        }

        Executors.newSingleThreadExecutor().execute(() -> {
            File picturesDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            if (picturesDir == null) {
                Log.e(TAG, "无法获取外部图片目录。");
                return;
            }
            File outputFile = new File(picturesDir, "asuka.jpg");

            if (outputFile.exists()) {
                Log.d(TAG, "本地演示图片已存在：" + outputFile.getAbsolutePath());
                // 如果文件已存在，则重新加载演示
                runOnUiThread(this::demoLoadFromVariousSources);
                return;
            }

            try {
                URL url = new URL(IMAGE_URL_ASUKA);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream inputStream = connection.getInputStream();

                FileOutputStream fos = new FileOutputStream(outputFile);
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    fos.write(buffer, 0, bytesRead);
                }
                fos.close();
                inputStream.close();
                Log.d(TAG, "演示图片下载成功到：" + outputFile.getAbsolutePath());

                // 下载完成后，在主线程重新加载图片
                runOnUiThread(this::demoLoadFromVariousSources);

            } catch (IOException e) {
                Log.e(TAG, "下载演示图片失败: " + e.getMessage());
                runOnUiThread(() -> Toast.makeText(MainActivity.this, "下载演示图片失败！", Toast.LENGTH_LONG).show());
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 如果在 Activity 销毁时需要清除所有 Glide 请求（通常 Glide 会自动处理）
        // Glide.with(this).clear(imageView); // 清除单个 ImageView
        // Glide.with(this).onDestroy(); // 清除与此 Context 关联的所有请求
    }
}
