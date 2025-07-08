// MyApplication.java - 修正后
package com.example.glidecomprehensive;

import android.app.Application;
import android.util.Log;

/**
 *  修正：MyApplication 类现在只继承 Application，不再实现 AppGlideModule。
 *  Glide 的 AppGlideModule 逻辑将移到一个独立的类中。
 * 代码讲解：
 * extends Application: MyApplication 现在只继承 Application。
 * 移除了 implements AppGlideModule，以及 applyOptions()、registerComponents() 和 isManifestParsingEnabled() 方法。这些方法将移到新的 MyAppGlideModule.java 文件中。
 * Log.d(): 打印日志，确认 Application 已启动。
 */
public final class MyApplication extends Application {

    private static final String TAG = "MyApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "MyApplication onCreate: Application started. Glide AppGlideModule will be initialized automatically.");
        // Glide 会自动检测并初始化通过 @GlideModule 注解声明的 AppGlideModule，无需在此手动调用。
    }
}
