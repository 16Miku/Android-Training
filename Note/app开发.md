

# 请极尽详细全面具体地指导我完成该安卓app的开发@🔬 start_deep_research





好的，我们将极尽详细、全面、具体地指导您完成这个 Android App 的开发实践课程。这个任务涵盖了 Android App 开发的完整生命周期，从项目创建到功能实现、性能优化再到打包发布，是检验和提升 Android 开发技能的绝佳机会。

为了满足您的要求，我们将：
1.  **极尽详细地讲解**每个知识点和技术。
2.  在每部分提供**详细的 Java 代码示例**和**逐行注释**。
3.  **详细文字讲解说明**所有代码示例。
4.  提供**面试话术**，帮助您在面试中清晰地表达。
5.  关于“配置文件基于 Kotlin DSL”，我会解释其在 Gradle 中的应用，但核心 App 代码将基于 Java。

---

### 实践课程：完成一个小型 Android App 的开发指南

我们将按照您提供的任务要求，一步步构建这个 App。

#### Part 1: 项目创建与基础架构

**1.1 完成 App 的工程创建 (工程创建)**

这是所有 Android 项目的起点。

**知识技术讲解：**
Android Studio 是官方推荐的 IDE。创建一个新的 Android 项目会生成一个基本的项目结构，包含 `app` 模块（存放 App 源代码）、`build.gradle` 文件（用于配置构建）、`AndroidManifest.xml`（App 的清单文件）、资源文件夹等。

**具体运用示例 (操作步骤):**

1.  **打开 Android Studio。**
2.  点击 **"New Project"** (如果这是您第一次使用，或者在欢迎界面选择 "New Project")。
3.  选择 **"Phone and Tablet"** 选项卡下的 **"Empty Activity"** 模板。点击 **"Next"**。
4.  **配置您的项目：**
    *   **Name:** `MyPracticeApp` (您的App名称)
    *   **Package name:** `com.example.mypracticeapp` (您的App包名，通常是反域名形式)
    *   **Save location:** 选择项目存储路径。
    *   **Language:** `Java` (根据您的要求)
    *   **Minimum SDK:** 选择一个合适的最低 Android 版本，例如 `API 21: Android 5.0 (Lollipop)`，这能覆盖绝大多数设备并支持较新的 API。
    *   **Build configuration language:** `Kotlin DSL` (根据您的要求，这将影响 `build.gradle` 文件的语法)
5.  点击 **"Finish"**。Android Studio 会自动配置并同步项目。

**详细文字讲解说明：**
*   **Empty Activity：** 这是一个最基础的模板，只包含一个主 Activity 和一个布局文件，适合从零开始构建 App。
*   **Package name：** 包名是 App 的唯一标识符，发布到应用商店后不能更改。
*   **Minimum SDK：** 决定了您的 App 可以在最低哪个 Android 版本上运行。选择过低会增加兼容性问题，过高会减少用户覆盖。Lollipop (API 21) 是一个不错的平衡点。
*   **Kotlin DSL：** 如果您选择 `Kotlin DSL`，您的 `build.gradle` 文件将使用 Kotlin 语法编写，例如 `build.gradle.kts`。这比传统的 Groovy DSL 更具类型安全和 IDE 智能提示功能。

**面试话术：**
“在工程创建阶段，我通常会使用 Android Studio 的 'Empty Activity' 模板作为起点。我会仔细配置项目的名称、包名和最低支持 SDK 版本。包名是 App 的唯一标识符，最低 SDK 版本则平衡了用户覆盖率和可用的新 API。我会选择 Java 作为编程语言，并在构建配置语言上选择 Kotlin DSL，以利用其类型安全和更好的 IDE 支持来配置 Gradle 构建脚本。”

**1.2 分模块代码编写 (模块化)**

**知识技术讲解：**
分模块代码编写（模块化）是将 App 拆分成多个独立的、可重用的功能模块。这有助于：
*   **降低耦合度：** 各模块之间职责单一，相互依赖减少。
*   **提高可维护性：** 独立开发、测试和维护模块。
*   **加快编译速度：** 增量编译时，只编译修改过的模块。
*   **方便团队协作：** 不同团队成员负责不同模块。
*   **支持多版本/多渠道：** 某些模块可以根据需求灵活组合。

常见的模块划分方式包括：
*   **`app` 模块：** 主应用模块，负责集成其他模块，通常包含 `Application` 类和全局配置。
*   **`common` / `base` 模块：** 存放公共工具类、基础 UI 组件、网络请求基类、常量等。
*   **`feature-xxx` 模块：** 存放特定业务功能模块，如 `feature-login`, `feature-home`, `feature-search` 等。
*   **`data` 模块：** 存放数据模型、数据仓库接口、网络接口定义、本地存储接口等。

**具体运用示例 (操作步骤):**

1.  **创建 `common` 模块：**
    *   在 Android Studio 中，右键点击项目根目录 (通常是顶级的 `MyPracticeApp`)。
    *   选择 **"New" -> "Module"**。
    *   选择 **"Android Library"** 模板。点击 **"Next"**。
    *   **Module name:** `common`
    *   **Package name:** `com.example.mypracticeapp.common`
    *   **Language:** `Java`
    *   **Minimum SDK:** 与 `app` 模块保持一致。
    *   点击 **"Finish"**。
2.  **创建 `feature-login` 模块：**
    *   重复上述步骤，创建 `feature-login` 模块。
    *   **Module name:** `feature-login`
    *   **Package name:** `com.example.mypracticeapp.feature.login`
3.  **配置模块依赖：**
    *   打开 `app/build.gradle.kts` (或 `app/build.gradle`) 文件。
    *   在 `dependencies` 块中，添加对 `common` 和 `feature-login` 模块的依赖。
    *   **`app/build.gradle.kts` 示例:**
        ```kotlin
        // ... 其他配置
        dependencies {
            implementation(project(":common")) // 依赖 common 模块
            implementation(project(":feature-login")) // 依赖 feature-login 模块
            // ... 其他依赖
        }
        ```
    *   **`feature-login/build.gradle.kts` 示例 (如果需要依赖 `common`):**
        ```kotlin
        // ... 其他配置
        dependencies {
            implementation(project(":common")) // login 模块可能需要 common 模块的工具类
            // ... 其他依赖
        }
        ```
4.  **同步项目：** 点击 Android Studio 工具栏上的 **"Sync Project with Gradle Files"** 按钮。

**详细文字讲解说明：**
*   **Android Library：** 创建的是一个 Android Library 模块，它最终会被编译成 `.aar` 文件，可以被其他 Android 模块或项目引用。
*   **`implementation(project(":module_name"))`：** 这是在 `build.gradle.kts` 中添加模块依赖的语法。它告诉 Gradle，`app` 模块需要 `common` 和 `feature-login` 模块提供的功能。
*   **依赖关系：** 模块之间应该有清晰的依赖关系，例如业务模块依赖公共模块，主 App 模块依赖所有业务模块。避免循环依赖。

**面试话术：**
“为了提高项目的可维护性、可扩展性和团队协作效率，我会采用模块化开发。通常，我会将 App 拆分为：一个主 `app` 模块负责集成和整体配置；一个 `common` 或 `base` 模块用于存放所有公共的工具类、基础组件和常量；以及按业务功能划分的 `feature-xxx` 模块，例如 `feature-login`、`feature-home`。模块之间通过 Gradle 依赖进行管理，例如 `app` 模块会 `implementation` 依赖所有 `feature` 模块和 `common` 模块。”

**1.3 基本 App 架构 (MVP/MVVM) - 概念**

**知识技术讲解：**
为了更好地组织代码和职责分离，采用一种成熟的 App 架构模式至关重要。常见的有 MVP (Model-View-Presenter) 和 MVVM (Model-View-ViewModel)。

*   **MVP (Model-View-Presenter):**
    *   **Model：** 负责数据逻辑，提供数据接口（如数据库、网络请求）。
    *   **View：** 负责 UI 展示，不包含业务逻辑，将用户操作传递给 Presenter。
    *   **Presenter：** 连接 Model 和 View，处理业务逻辑，从 Model 获取数据并更新 View。
    *   **特点：** View 和 Presenter 之间通过接口交互，Presenter 持有 View 的引用。Presenter 负责所有业务逻辑，View 变得非常“傻瓜”。

*   **MVVM (Model-View-ViewModel):**
    *   **Model：** 同 MVP，负责数据。
    *   **View：** 同 MVP，负责 UI 展示。
    *   **ViewModel：** 连接 Model 和 View，持有 View 所需的数据和操作，但不直接持有 View 引用。通过数据绑定 (Data Binding) 或 LiveData/Flow 等响应式编程框架，数据变化自动更新 View。
    *   **特点：** View 和 ViewModel 之间通过数据绑定实现双向通信，ViewModel 不持有 View 引用，降低耦合度，更易于测试。

**选择建议：**
对于现代 Android 开发，MVVM 结合 Jetpack 组件 (如 `LiveData`, `ViewModel`, `Data Binding`, `Navigation`) 是更推荐的选择，因为它能更好地支持响应式编程和生命周期管理，减少样板代码。

**具体运用示例 (概念性):**
在 `feature-login` 模块中，我们将采用 MVVM 架构：

*   `feature-login/src/main/java/com/example.mypracticeapp.feature.login/view/LoginActivity.java` (View)
*   `feature-login/src/main/java/com.example.mypracticeapp.feature.login/viewmodel/LoginViewModel.java` (ViewModel)
*   `feature-login/src/main/java/com.example.mypracticeapp.feature.login/model/LoginRepository.java` (Model，负责网络请求、本地存储等)

**详细文字讲解说明：**
*   **职责分离：** 架构模式的核心是职责分离，使得代码更清晰、更易于测试和维护。
*   **数据绑定：** MVVM 的优势在于数据绑定，它减少了手动更新 UI 的代码。

**面试话术：**
“在项目架构上，我通常会采用 MVVM 模式，并结合 Android Jetpack 组件。MVVM 模式能很好地分离 Model（数据层）、View（UI 层）和 ViewModel（逻辑层），提高代码的可测试性和可维护性。ViewModel 负责持有 View 所需的数据和业务逻辑，通过 LiveData 或数据绑定与 View 进行通信，而 View 则专注于 UI 展示。这种模式避免了 ViewModel 直接持有 View 引用，降低了耦合度，并且能够更好地处理生命周期问题。”

#### Part 2: 核心功能实现

**2.1 欢迎页面 (欢迎页面) - Splash Screen**

**知识技术讲解：**
欢迎页面（Splash Screen）是 App 启动时用户看到的第一屏。它的作用是：
*   **提升用户体验：** 掩盖 App 启动时的加载时间，避免白屏。
*   **品牌展示：** 展示 App Logo 或公司品牌。
*   **初始化：** 在后台进行一些轻量级的初始化操作，如检查网络、用户登录状态、数据预加载等。

**实现方式：**
推荐使用 Android 12 (API 31) 引入的 **Splash Screen API**。对于旧版本，通常通过设置主题样式来创建启动画面。

**具体运用示例 (Java 代码):**

**方法一：使用 Android 12+ Splash Screen API (推荐)**

1.  **`res/drawable/splash_background.xml` (Splash 画面背景):**
    ```xml
    <?xml version="1.0" encoding="utf-8"?>
    <layer-list xmlns:android="http://schemas.android.com/apk/res/android">
        <item android:drawable="@color/white" /> <!-- 背景颜色 -->
        <item>
            <bitmap
                android:gravity="center"
                android:src="@mipmap/ic_launcher_round" /> <!-- App Logo -->
        </item>
    </layer-list>
    ```
    
2.  **`res/values/themes.xml` (定义 Splash 主题):**
    ```xml
    <!-- 在 styles.xml 或 themes.xml 中 -->
    <style name="Theme.App.SplashScreen" parent="Theme.SplashScreen">
        <item name="windowSplashScreenBackground">@drawable/splash_background</item>
        <item name="windowSplashScreenAnimatedIcon">@drawable/avd_splash_animation</item> <!-- 可选：动画图标 -->
        <item name="windowSplashScreenAnimationDuration">1000</item> <!-- 动画时长 -->
        <item name="postSplashScreenTheme">@style/Theme.MyPracticeApp</item> <!-- 启动后切换到的主题 -->
    </style>
    ```
    *   `@drawable/avd_splash_animation`：如果需要动画图标，可以创建一个 AnimatedVectorDrawable。
    
3.  **`AndroidManifest.xml` (应用 Splash 主题):**
    ```xml
    <application
        ...
        android:theme="@style/Theme.App.SplashScreen"> <!-- 应用 Splash 主题 -->
        <activity
            android:name=".MainActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
        <!-- 其他 Activity -->
        <activity android:name=".WelcomeActivity" /> <!-- 欢迎页逻辑处理 -->
    </application>
    ```
    
4.  **`MainActivity.java` (主 Activity 逻辑):**
    ```java
    package com.example.mypracticeapp;

    import android.content.Intent;
    import android.os.Bundle;
    import android.os.Handler;
    import android.os.Looper;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.core.splashscreen.SplashScreen; // 导入 SplashScreen API

    public class MainActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            // 确保在 super.onCreate() 之前调用，以启用 Splash Screen
            SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main); // 你的主布局

            // 保持 Splash Screen 可见直到内容加载完成
            // splashScreen.setKeepOnScreenCondition(() -> !isContentReady); // 如果有内容加载逻辑

            // 模拟初始化或跳转逻辑
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
                startActivity(intent);
                finish(); // 结束 MainActivity，防止用户返回到 Splash Screen
            }, 2000); // 延迟 2 秒跳转
        }
    }
    ```
    
5.  **`WelcomeActivity.java` (实际的欢迎/引导页，如果需要):**
    *   这个 Activity 可以是用户第一次打开 App 时的引导页，或者直接跳转到登录/注册页。
    *   这里为了简化，我们假设 `WelcomeActivity` 是一个简单的引导页，然后会跳转到 `LoginActivity`。
    
6.  build.gradle.kts修改

    ```kotlin
    dependencies {
    
        implementation(libs.appcompat)
        implementation(libs.material)
        implementation(libs.activity)
        implementation(libs.constraintlayout)
        implementation(libs.core.splashscreen)
        testImplementation(libs.junit)
        androidTestImplementation(libs.ext.junit)
        androidTestImplementation(libs.espresso.core)
    
    
    
        implementation(project(":common")) // 依赖 common 模块
        implementation(project(":feature-login")) // 依赖 feature-login 模块
    
    
    }
    ```

    

**方法二：兼容旧版本 Android 的 Splash Screen (通过主题)**

1.  **`res/layout/activity_splash.xml` (Splash 布局):**
    ```xml
    <?xml version="1.0" encoding="utf-8"?>
    <RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="@color/white"
        android:gravity="center">

        <ImageView
            android:layout_width="120dp"
            android:layout_height="120dp"
            android:src="@mipmap/ic_launcher_round"
            android:contentDescription="App Logo" />

    </RelativeLayout>
    ```
2.  **`res/values/themes.xml` (定义 Splash 主题):**
    ```xml
    <style name="Theme.Splash" parent="Theme.AppCompat.Light.NoActionBar">
        <item name="android:windowBackground">@drawable/splash_background_compat</item> <!-- 引用一个 Drawable -->
        <item name="android:windowFullscreen">true</item> <!-- 全屏显示 -->
    </style>
    <!-- res/drawable/splash_background_compat.xml -->
    <layer-list xmlns:android="http://schemas.android.com/apk/res/android">
        <item android:drawable="@color/white" />
        <item>
            <bitmap
                android:gravity="center"
                android:src="@mipmap/ic_launcher_round" />
        </item>
    </layer-list>
    ```
3.  **`AndroidManifest.xml` (应用 Splash 主题):**
    ```xml
    <application ...>
        <activity
            android:name=".SplashActivity"
            android:exported="true"
            android:theme="@style/Theme.Splash"> <!-- 应用 Splash 主题 -->
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
        <activity android:name=".WelcomeActivity" />
        <!-- 其他 Activity -->
    </application>
    ```
4.  **`SplashActivity.java` (Splash 逻辑):**
    ```java
    package com.example.mypracticeapp;

    import android.content.Intent;
    import android.os.Bundle;
    import android.os.Handler;
    import android.os.Looper;
    import androidx.appcompat.app.AppCompatActivity;

    public class SplashActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            // 不需要 setContentView，因为背景已经通过主题设置
            // setContentView(R.layout.activity_splash); // 如果需要更复杂的布局才设置

            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                // 模拟初始化操作，例如检查用户登录状态，决定跳转到 WelcomeActivity 或 LoginActivity
                Intent intent = new Intent(SplashActivity.this, WelcomeActivity.class);
                startActivity(intent);
                finish(); // 结束 SplashActivity
            }, 2000); // 延迟 2 秒跳转
        }
    }
    ```

**详细文字讲解说明：**
*   **Splash Screen API (Android 12+)：** 这是官方推荐的现代方法，它在系统层面优化了启动体验，消除了冷启动白屏问题。通过主题来定义 Splash 画面，在 `MainActivity` 中调用 `SplashScreen.installSplashScreen()` 即可。
*   **兼容旧版本：** 对于旧版本，通常通过设置 `android:windowBackground` 为一个包含 Logo 的 `layer-list` Drawable 来实现。`windowFullscreen` 可以让 Splash 画面全屏显示。
*   **`Handler().postDelayed()`：** 用于模拟加载过程，并在一定时间后跳转到下一个 Activity。
*   **`finish()`：** 在跳转后调用 `finish()` 结束当前 Activity，防止用户按返回键回到 Splash 画面。
*   **初始化逻辑：** 在 Splash 画面中，可以进行一些轻量级的初始化，但**避免耗时操作**，以免影响启动速度。耗时操作应该放在单独的线程中，并在完成后跳转。

**面试话术：**
“欢迎页面，也就是 Splash Screen，在 App 启动时起到过渡和品牌展示的作用。对于 Android 12 及以上版本，我推荐使用官方的 Splash Screen API，它通过主题配置，能够很好地优化冷启动时的白屏问题，并支持动画图标。对于旧版本，我通常会通过设置 Activity 的 `android:windowBackground` 主题属性为一个包含 App Logo 的 `layer-list` Drawable 来实现。在 Splash Activity 的 `onCreate` 方法中，我会使用 `Handler().postDelayed()` 来模拟加载过程，并在短暂延迟后跳转到主页面或登录页，并调用 `finish()` 销毁 Splash Activity，防止用户回退。需要注意的是，Splash 页面不应承载过重的初始化逻辑，耗时操作应异步进行，以保证启动速度。”

**2.2 登录、注册 (登录、注册)**

**知识技术讲解：**
登录和注册是用户认证的核心功能。它们通常涉及：
*   **UI 设计：** 用户名/手机号、密码输入框、验证码（可选）、登录/注册按钮、忘记密码/切换模式链接。
*   **输入验证：** 客户端对输入格式进行校验（如手机号格式、密码强度）。
*   **网络请求：** 向后端服务器发送登录/注册请求。
*   **数据存储：** 登录成功后，保存用户 Token、用户 ID 等信息（如使用 `SharedPreferences` 或 `DataStore`）。
*   **状态管理：** 登录/注册过程中的加载、成功、失败状态反馈。

**架构：** 在 `feature-login` 模块中采用 MVVM 架构。

**具体运用示例 (Java 代码):**

**`feature-login/src/main/java/com/example.mypracticeapp.feature.login/view/LoginActivity.java`**

```java
package com.example.mypracticeapp.feature.login.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer; // 导入 Observer
import androidx.lifecycle.ViewModelProvider; // 导入 ViewModelProvider

import com.example.mypracticeapp.MainActivity; // 假设登录成功后跳转到 MainActivity
import com.example.mypracticeapp.R; // 确保 R 文件可访问
import com.example.mypracticeapp.feature.login.viewmodel.LoginViewModel;
import com.example.mypracticeapp.feature.login.viewmodel.LoginResult; // 导入 LoginResult (自定义数据类)
import com.example.mypracticeapp.feature.login.view.RegisterActivity; // 注册Activity

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private TextView tvRegister;
    private ProgressBar progressBar;

    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 绑定 UI 组件
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        tvRegister = findViewById(R.id.tv_register);
        progressBar = findViewById(R.id.progress_bar);

        // 初始化 ViewModel
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        // 观察登录结果 LiveData
        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(LoginResult loginResult) {
                progressBar.setVisibility(View.GONE); // 隐藏进度条
                if (loginResult.isSuccess()) {
                    Toast.makeText(LoginActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
                    // 登录成功后跳转到主页
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish(); // 销毁当前登录页
                } else {
                    Toast.makeText(LoginActivity.this, "登录失败: " + loginResult.getErrorMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        // 观察加载状态 LiveData
        loginViewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE); // 显示/隐藏进度条
                btnLogin.setEnabled(!isLoading); // 登录按钮在加载时禁用
            }
        });


        // 登录按钮点击事件
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                // 客户端输入校验
                if (TextUtils.isEmpty(username)) {
                    etUsername.setError("用户名不能为空");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    etPassword.setError("密码不能为空");
                    return;
                }
                if (password.length() < 6) {
                    etPassword.setError("密码至少6位");
                    return;
                }

                // 调用 ViewModel 进行登录
                loginViewModel.login(username, password);
            }
        });

        // 注册链接点击事件
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
```

**`feature-login/src/main/java/com.mypracticeapp.feature.login/viewmodel/LoginViewModel.java`**

```java
package com.example.mypracticeapp.feature.login.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mypracticeapp.feature.login.model.LoginRepository;
import com.example.mypracticeapp.feature.login.model.User; // 导入 User (自定义数据类)

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private LoginRepository loginRepository;

    public LoginViewModel() {
        loginRepository = new LoginRepository(); // 初始化数据仓库
    }

    public LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    /**
     * 执行登录操作
     * @param username 用户名
     * @param password 密码
     */
    public void login(String username, String password) {
        isLoading.setValue(true); // 显示加载状态

        // 模拟网络请求 (实际中会调用 LoginRepository 的网络请求方法)
        loginRepository.login(username, password, new LoginRepository.LoginCallback() {
            @Override
            public void onSuccess(User user) {
                isLoading.postValue(false); // 隐藏加载状态
                loginResult.postValue(new LoginResult(true, null)); // 登录成功
                // 可以在这里保存用户信息到 SharedPreferences
                // SharedPreferencesUtil.saveUserToken(user.getToken());
            }

            @Override
            public void onFailure(String errorMessage) {
                isLoading.postValue(false); // 隐藏加载状态
                loginResult.postValue(new LoginResult(false, errorMessage)); // 登录失败
            }
        });
    }

    /**
     * 执行注册操作 (类似登录，但调用 RegisterRepository 或 LoginRepository 的注册方法)
     * @param username
     * @param password
     */
    public void register(String username, String password) {
        isLoading.setValue(true);
        loginRepository.register(username, password, new LoginRepository.RegisterCallback() {
            @Override
            public void onSuccess() {
                isLoading.postValue(false);
                loginResult.postValue(new LoginResult(true, null)); // 注册成功
            }

            @Override
            public void onFailure(String errorMessage) {
                isLoading.postValue(false);
                loginResult.postValue(new LoginResult(false, errorMessage)); // 注册失败
            }
        });
    }
}
```

**`feature-login/src/main/java/com.mypracticeapp.feature.login/model/LoginRepository.java`**

```java
package com.example.mypracticeapp.feature.login.model;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

public class LoginRepository {

    private static final String TAG = "LoginRepository";

    // 模拟用户数据
    private static final String MOCK_USERNAME = "test";
    private static final String MOCK_PASSWORD = "password";

    // 登录回调接口
    public interface LoginCallback {
        void onSuccess(User user);
        void onFailure(String errorMessage);
    }

    // 注册回调接口
    public interface RegisterCallback {
        void onSuccess();
        void onFailure(String errorMessage);
    }

    /**
     * 模拟登录网络请求
     * @param username 用户名
     * @param password 密码
     * @param callback 登录回调
     */
    public void login(String username, String password, LoginCallback callback) {
        Log.d(TAG, "Simulating login for: " + username);
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (MOCK_USERNAME.equals(username) && MOCK_PASSWORD.equals(password)) {
                // 模拟登录成功
                User loggedInUser = new User("user123", username, "mock_token_abc");
                callback.onSuccess(loggedInUser);
            } else {
                // 模拟登录失败
                callback.onFailure("用户名或密码错误");
            }
        }, 1500); // 模拟网络延迟 1.5 秒
    }

    /**
     * 模拟注册网络请求
     * @param username 用户名
     * @param password 密码
     * @param callback 注册回调
     */
    public void register(String username, String password, RegisterCallback callback) {
        Log.d(TAG, "Simulating registration for: " + username);
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            // 模拟注册逻辑：简单判断用户名是否已被占用
            if (MOCK_USERNAME.equals(username)) {
                callback.onFailure("该用户名已被占用");
            } else {
                callback.onSuccess(); // 模拟注册成功
            }
        }, 1500); // 模拟网络延迟 1.5 秒
    }
}
```

**`feature-login/src/main/java/com.mypracticeapp.feature.login/model/User.java` (数据类)**

```java
package com.example.mypracticeapp.feature.login.model;

public class User {
    private String userId;
    private String username;
    private String token;

    public User(String userId, String username, String token) {
        this.userId = userId;
        this.username = username;
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getToken() {
        return token;
    }
}
```

**`feature-login/src/main/java/com.mypracticeapp.feature.login/viewmodel/LoginResult.java` (结果封装)**

```java
package com.example.mypracticeapp.feature.login.viewmodel;

public class LoginResult {
    private boolean success;
    private String errorMessage;

    public LoginResult(boolean success, String errorMessage) {
        this.success = success;
        this.errorMessage = errorMessage;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
```

**`feature-login/src/main/res/layout/activity_login.xml`**

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:gravity="center"
    android:padding="32dp">

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="用户登录"
        android:textSize="32sp"
        android:textStyle="bold"
        android:layout_marginBottom="48dp"/>

    <EditText
        android:id="@+id/et_username"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="用户名"
        android:inputType="text"
        android:autofillHints="username"
        android:padding="12dp"
        android:background="@drawable/edittext_background"
        android:layout_marginBottom="16dp"/>

    <EditText
        android:id="@+id/et_password"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="密码"
        android:inputType="textPassword"
        android:autofillHints="password"
        android:padding="12dp"
        android:background="@drawable/edittext_background"
        android:layout_marginBottom="24dp"/>

    <Button
        android:id="@+id/btn_login"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="登录"
        android:textSize="18sp"
        android:padding="12dp"
        android:background="@drawable/button_background"
        android:textColor="@android:color/white"
        android:layout_marginBottom="16dp"/>

    <TextView
        android:id="@+id/tv_register"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="没有账号？去注册"
        android:textColor="@color/design_default_color_primary"
        android:textSize="16sp"
        android:padding="8dp"/>

    <ProgressBar
        android:id="@+id/progress_bar"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="24dp"
        android:visibility="gone"/>

</LinearLayout>
```

**`feature-login/src/main/res/drawable/edittext_background.xml`**

```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android"
    android:shape="rectangle">
    <solid android:color="@android:color/white"/>
    <corners android:radius="8dp"/>
    <stroke android:width="1dp" android:color="#CCCCCC"/>
</shape>
```

**`feature-login/src/main/res/drawable/button_background.xml`**

```xml
<?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://schemas.android.com/apk/res/android">
    <!-- 按钮按下时的状态 -->
    <item android:state_pressed="true">
        <shape android:shape="rectangle">
            <corners android:radius="8dp"/>
            <solid android:color="@color/design_default_color_primary_dark"/>
        </shape>
    </item>
    <!-- 按钮禁用时的状态 -->
    <item android:state_enabled="false">
        <shape android:shape="rectangle">
            <corners android:radius="8dp"/>
            <solid android:color="#BBBBBB"/>
        </shape>
    </item>
    <!-- 按钮默认状态 -->
    <item>
        <shape android:shape="rectangle">
            <corners android:radius="8dp"/>
            <solid android:color="@color/design_default_color_primary"/>
        </shape>
    </item>
</selector>
```

**`feature-login/src/main/java/com.mypracticeapp.feature.login/view/RegisterActivity.java` (注册页)**

```java
package com.example.mypracticeapp.feature.login.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.mypracticeapp.R;
import com.example.mypracticeapp.feature.login.viewmodel.LoginResult;
import com.example.mypracticeapp.feature.login.viewmodel.LoginViewModel;

public class RegisterActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private Button btnRegister;
    private ProgressBar progressBar;

    private LoginViewModel loginViewModel; // 复用 LoginViewModel 进行注册操作

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register); // 假设有 activity_register.xml 布局

        etUsername = findViewById(R.id.et_username_register);
        etPassword = findViewById(R.id.et_password_register);
        etConfirmPassword = findViewById(R.id.et_confirm_password_register);
        btnRegister = findViewById(R.id.btn_register_submit);
        progressBar = findViewById(R.id.progress_bar_register);

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        // 观察注册结果
        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(LoginResult registerResult) {
                progressBar.setVisibility(View.GONE);
                if (registerResult.isSuccess()) {
                    Toast.makeText(RegisterActivity.this, "注册成功！请登录", Toast.LENGTH_LONG).show();
                    finish(); // 注册成功后返回登录页
                } else {
                    Toast.makeText(RegisterActivity.this, "注册失败: " + registerResult.getErrorMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        // 观察加载状态
        loginViewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                btnRegister.setEnabled(!isLoading);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String confirmPassword = etConfirmPassword.getText().toString().trim();

                // 客户端输入校验
                if (TextUtils.isEmpty(username)) {
                    etUsername.setError("用户名不能为空");
                    return;
                }
                if (TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
                    etPassword.setError("密码不能为空");
                    return;
                }
                if (!password.equals(confirmPassword)) {
                    etConfirmPassword.setError("两次密码不一致");
                    return;
                }
                if (password.length() < 6) {
                    etPassword.setError("密码至少6位");
                    return;
                }

                loginViewModel.register(username, password); // 调用 ViewModel 的注册方法
            }
        });
    }
}
```

**`feature-login/src/main/res/layout/activity_register.xml`**

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:gravity="center"
    android:padding="32dp">

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="用户注册"
        android:textSize="32sp"
        android:textStyle="bold"
        android:layout_marginBottom="48dp"/>

    <EditText
        android:id="@+id/et_username_register"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="用户名"
        android:inputType="text"
        android:autofillHints="username"
        android:padding="12dp"
        android:background="@drawable/edittext_background"
        android:layout_marginBottom="16dp"/>

    <EditText
        android:id="@+id/et_password_register"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="设置密码"
        android:inputType="textPassword"
        android:autofillHints="newPassword"
        android:padding="12dp"
        android:background="@drawable/edittext_background"
        android:layout_marginBottom="16dp"/>

    <EditText
        android:id="@+id/et_confirm_password_register"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="确认密码"
        android:inputType="textPassword"
        android:autofillHints="newPassword"
        android:padding="12dp"
        android:background="@drawable/edittext_background"
        android:layout_marginBottom="24dp"/>

    <Button
        android:id="@+id/btn_register_submit"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="注册"
        android:textSize="18sp"
        android:padding="12dp"
        android:background="@drawable/button_background"
        android:textColor="@android:color/white"
        android:layout_marginBottom="16dp"/>

    <ProgressBar
        android:id="@+id/progress_bar_register"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="24dp"
        android:visibility="gone"/>

</LinearLayout>
```

**详细文字讲解说明：**
*   **MVVM 实践：** `LoginActivity` (View) 负责 UI 交互，`LoginViewModel` 负责业务逻辑和数据准备，`LoginRepository` (Model) 模拟网络请求。
*   **`ViewModelProvider`：** 用于获取 `ViewModel` 实例，它能确保 `ViewModel` 在屏幕旋转等配置变化时不会被销毁，并在 Activity/Fragment 生命周期结束时正确清除。
*   **`LiveData`：** `loginResult` 和 `isLoading` 都是 `LiveData`。`LiveData` 是一种可观察的数据持有者，它具有生命周期感知能力，这意味着它只在活跃的生命周期所有者（如 Activity、Fragment）处于活动状态时更新 UI，从而避免内存泄漏和空指针异常。
*   **`Observer`：** 在 `LoginActivity` 中，通过 `observe()` 方法订阅 `LiveData` 的变化，当 `ViewModel` 中的数据更新时，`onChanged()` 方法会被调用，从而更新 UI。
*   **客户端校验：** 在发送网络请求前，对用户名和密码进行非空、长度、一致性等基本校验，这是良好的用户体验和安全实践。
*   **进度条：** `ProgressBar` 用于在网络请求期间向用户提供加载反馈，提高用户体验。`isLoading` `LiveData` 负责控制其可见性。
*   **`postValue()` vs. `setValue()`：**
    *   `setValue()`：在主线程中更新 `LiveData` 的值。
    *   `postValue()`：在子线程中更新 `LiveData` 的值。如果 `loginRepository` 的回调在子线程中执行，则必须使用 `postValue()`。这里 `LoginRepository` 使用 `Handler` 模拟延迟，并在主线程回调，所以两者都可以，但 `postValue()` 更安全。
*   **`SharedPreferences` / `DataStore`：** 登录成功后，实际项目中通常会使用 `SharedPreferences` (简单键值对存储) 或更现代的 `DataStore` (异步、类型安全) 来保存用户 Token 或其他身份信息，以便后续自动登录和访问受保护资源。

**面试话术：**
“登录和注册功能我采用 MVVM 架构实现。`LoginActivity` 负责 UI 渲染和用户输入，它通过 `ViewModelProvider` 获取 `LoginViewModel` 实例。`LoginViewModel` 包含登录和注册的业务逻辑，并使用 `LiveData` 暴露登录结果 (`LoginResult`) 和加载状态 (`isLoading`)。`LoginRepository` 则模拟后端数据交互，例如网络请求。在 `LoginActivity` 中，我会观察 `LoginViewModel` 暴露的 `LiveData`，当数据变化时，更新 UI 状态，例如显示/隐藏进度条、显示Toast提示，并根据登录结果进行页面跳转。在发送网络请求前，我还会进行客户端输入校验，确保数据的有效性。登录成功后，我会将用户 Token 等关键信息存储在 `SharedPreferences` 或 `DataStore` 中，以便后续的会话管理。”

**2.3 首页 (首页)**

**知识技术讲解：**
首页是 App 的核心内容展示区域，通常包含：
*   **顶部 AppBar：** 标题、搜索图标、个人中心图标等。
*   **内容区域：** 通常是列表形式，展示各种数据流（如文章、商品、图片等）。
*   **底部导航栏：** 引导用户切换到其他主要功能模块（首页、搜索、我的）。

**布局：**
*   **`CoordinatorLayout`：** 强大的布局，可以协调 AppBar 和可滚动内容的行为，实现滚动隐藏 AppBar 等效果。
*   **`RecyclerView`：** 显示列表内容的最佳选择，高效、可复用。
*   **`BottomNavigationView`：** 底部导航栏，方便用户在主要功能之间切换。

**具体运用示例 (Java 代码):**

1.  **`app/src/main/java/com.example.mypracticeapp/MainActivity.java` (主页面，包含底部导航)**
    ```java
    package com.example.mypracticeapp;

    import android.os.Bundle;
    import android.view.MenuItem;

    import androidx.annotation.NonNull;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.fragment.app.Fragment;
    import androidx.fragment.app.FragmentManager;
    import androidx.fragment.app.FragmentTransaction;

    import com.example.mypracticeapp.feature.home.view.HomeFragment; // 导入 HomeFragment
    import com.example.mypracticeapp.feature.search.view.SearchFragment; // 导入 SearchFragment
    import com.example.mypracticeapp.feature.mine.view.MineFragment; // 导入 MineFragment
    import com.google.android.material.bottomnavigation.BottomNavigationView; // 导入 BottomNavigationView

    public class MainActivity extends AppCompatActivity {

        private BottomNavigationView bottomNavigationView;
        private Fragment homeFragment;
        private Fragment searchFragment;
        private Fragment mineFragment;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main_with_bottom_nav); // 主 Activity 布局

            bottomNavigationView = findViewById(R.id.bottom_navigation);

            // 初始化 Fragment
            homeFragment = new HomeFragment();
            searchFragment = new SearchFragment();
            mineFragment = new MineFragment();

            // 默认显示首页 Fragment
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, homeFragment)
                    .commit();

            // 设置底部导航栏监听器
            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    int itemId = item.getItemId();
                    if (itemId == R.id.nav_home) {
                        switchFragment(homeFragment);
                        return true;
                    } else if (itemId == R.id.nav_search) {
                        switchFragment(searchFragment);
                        return true;
                    } else if (itemId == R.id.nav_mine) {
                        switchFragment(mineFragment);
                        return true;
                    }
                    return false;
                }
            });
        }

        /**
         * 切换 Fragment
         * @param targetFragment 目标 Fragment
         */
        private void switchFragment(Fragment targetFragment) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            // 隐藏所有 Fragment
            if (homeFragment.isAdded()) transaction.hide(homeFragment);
            if (searchFragment.isAdded()) transaction.hide(searchFragment);
            if (mineFragment.isAdded()) transaction.hide(mineFragment);

            // 显示目标 Fragment，如果未添加则添加
            if (targetFragment.isAdded()) {
                transaction.show(targetFragment);
            } else {
                transaction.add(R.id.fragment_container, targetFragment);
            }
            transaction.commit();
        }
    }
    ```

2.  **`app/src/main/res/layout/activity_main_with_bottom_nav.xml` (主 Activity 布局)**
    ```xml
    <?xml version="1.0" encoding="utf-8"?>
    <RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <!-- Fragment 容器 -->
        <FrameLayout
            android:id="@+id/fragment_container"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:layout_above="@+id/bottom_navigation" />

        <!-- 底部导航栏 -->
        <com.google.android.material.bottomnavigation.BottomNavigationView
            android:id="@+id/bottom_navigation"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_alignParentBottom="true"
            android:background="?android:attr/windowBackground"
            app:menu="@menu/bottom_nav_menu" />

    </RelativeLayout>
    ```

3.  **`app/src/main/res/menu/bottom_nav_menu.xml` (底部导航菜单)**
    ```xml
    <?xml version="1.0" encoding="utf-8"?>
    <menu xmlns:android="http://schemas.android.com/apk/res/android">
        <item
            android:id="@+id/nav_home"
            android:icon="@drawable/ic_home_black_24dp"
            android:title="首页" />
        <item
            android:id="@+id/nav_search"
            android:icon="@drawable/ic_search_black_24dp"
            android:title="搜索" />
        <item
            android:id="@+id/nav_mine"
            android:icon="@drawable/ic_person_black_24dp"
            android:title="我的" />
    </menu>
    ```
    *   `ic_home_black_24dp` 等图标需要添加到 `res/drawable` 文件夹。

4.  **`feature-home/src/main/java/com.example.mypracticeapp.feature.home/view/HomeFragment.java` (首页 Fragment)**
    ```java
    package com.example.mypracticeapp.feature.home.view;

    import android.os.Bundle;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.TextView;
    import android.widget.Toast;

    import androidx.annotation.NonNull;
    import androidx.annotation.Nullable;
    import androidx.fragment.app.Fragment;
    import androidx.recyclerview.widget.LinearLayoutManager; // 导入 LinearLayoutManager
    import androidx.recyclerview.widget.RecyclerView; // 导入 RecyclerView

    import com.example.mypracticeapp.R; // 确保 R 文件可访问
    import com.example.mypracticeapp.feature.home.adapter.HomeAdapter; // 导入自定义 Adapter
    import com.example.mypracticeapp.feature.home.model.HomeItem; // 导入自定义数据类
    import com.example.mypracticeapp.feature.home.viewmodel.HomeViewModel; // 导入 ViewModel

    import java.util.ArrayList;
    import java.util.List;

    public class HomeFragment extends Fragment {

        private RecyclerView recyclerView;
        private HomeAdapter homeAdapter;
        private HomeViewModel homeViewModel; // 声明 ViewModel

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_home, container, false);
            recyclerView = view.findViewById(R.id.home_recycler_view);

            // 初始化 RecyclerView
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            homeAdapter = new HomeAdapter(new ArrayList<>()); // 初始空列表
            recyclerView.setAdapter(homeAdapter);

            // TODO: 在这里初始化 HomeViewModel 并加载数据
            // homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
            // homeViewModel.getHomeData().observe(getViewLifecycleOwner(), items -> {
            //     homeAdapter.updateData(items);
            // });
            // homeViewModel.loadHomeData(); // 首次加载数据

            // 模拟数据加载
            List<HomeItem> mockItems = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                mockItems.add(new HomeItem("Title " + i, "Description for item " + i));
            }
            homeAdapter.updateData(mockItems);

            return view;
        }
    }
    ```

5.  **`feature-home/src/main/res/layout/fragment_home.xml` (首页 Fragment 布局)**
    ```xml
    <?xml version="1.0" encoding="utf-8"?>
    <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical">

        <TextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="首页内容"
            android:textSize="28sp"
            android:textStyle="bold"
            android:gravity="center"
            android:padding="16dp"
            android:background="#DDDDDD"/>

        <androidx.recyclerview.widget.RecyclerView
            android:id="@+id/home_recycler_view"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:scrollbars="vertical"/>

    </LinearLayout>
    ```

6.  **`feature-home/src/main/java/com.example.mypracticeapp.feature.home/adapter/HomeAdapter.java` (RecyclerView Adapter)**
    ```java
    package com.example.mypracticeapp.feature.home.adapter;

    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.TextView;
    import android.widget.Toast;

    import androidx.annotation.NonNull;
    import androidx.recyclerview.widget.RecyclerView;

    import com.example.mypracticeapp.R;
    import com.example.mypracticeapp.feature.home.model.HomeItem;

    import java.util.List;

    public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {

        private List<HomeItem> dataList;

        public HomeAdapter(List<HomeItem> dataList) {
            this.dataList = dataList;
        }

        public void updateData(List<HomeItem> newData) {
            this.dataList.clear();
            this.dataList.addAll(newData);
            notifyDataSetChanged(); // 通知 RecyclerView 数据已更新
        }

        public void addMoreData(List<HomeItem> moreData) {
            int startPosition = dataList.size();
            dataList.addAll(moreData);
            notifyItemRangeInserted(startPosition, moreData.size());
        }
    ```


        @NonNull
        @Override
        public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_home_list, parent, false); // 假设有 item_home_list.xml 布局
            return new HomeViewHolder(view);
        }
    
        @Override
        public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
            HomeItem item = dataList.get(position);
            holder.tvTitle.setText(item.getTitle());
            holder.tvDescription.setText(item.getDescription());
    
            // 设置点击监听器
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "点击了: " + item.getTitle(), Toast.LENGTH_SHORT).show();
                    // TODO: 跳转到详情页
                }
            });
        }
    
        @Override
        public int getItemCount() {
            return dataList.size();
        }
    
        static class HomeViewHolder extends RecyclerView.ViewHolder {
            TextView tvTitle;
            TextView tvDescription;
    
            public HomeViewHolder(@NonNull View itemView) {
                super(itemView);
                tvTitle = itemView.findViewById(R.id.item_title);
                tvDescription = itemView.findViewById(R.id.item_description);
            }
        }
    }
    ```

7.  **`feature-home/src/main/res/layout/item_home_list.xml` (RecyclerView Item 布局)**
    ```xml
    <?xml version="1.0" encoding="utf-8"?>
    <androidx.cardview.widget.CardView xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_margin="8dp"
        app:cardCornerRadius="8dp"
        app:cardElevation="4dp">

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="vertical"
            android:padding="16dp">

            <TextView
                android:id="@+id/item_title"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:text="Item Title"
                android:textSize="18sp"
                android:textStyle="bold"
                android:textColor="@android:color/black"
                android:layout_marginBottom="4dp"/>

            <TextView
                android:id="@+id/item_description"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:text="This is a description for the item. It provides more details about the content."
                android:textSize="14sp"
                android:textColor="@android:color/darker_gray"/>

        </LinearLayout>
    </androidx.cardview.widget.CardView>
    ```
    *   **注意：** 如果使用 `CardView`，需要在 `build.gradle.kts` 中添加依赖：
        `implementation("androidx.cardview:cardview:1.0.0")`

**详细文字讲解说明：**
*   **`BottomNavigationView`：** 用于实现底部 Tab 导航。通过 `app:menu` 属性引用一个菜单资源文件 (`bottom_nav_menu.xml`)。
*   **`Fragment`：** 首页、搜索、我的等不同功能模块通常以 `Fragment` 的形式存在，然后动态加载到 `MainActivity` 的 `FrameLayout` 容器中。
*   **`FragmentManager` 和 `FragmentTransaction`：** 用于管理 Fragment 的添加、替换、显示、隐藏等操作。这里使用了 `add()` 和 `hide()`/`show()` 组合，避免 Fragment 每次切换都被销毁重建，提升性能和用户体验。
*   **`RecyclerView`：** 用于高效显示大量数据的列表。
    *   `setLayoutManager()`：设置布局管理器，如 `LinearLayoutManager` (线性列表)、`GridLayoutManager` (网格)、`StaggeredGridLayoutManager` (瀑布流)。
    *   `Adapter`：`RecyclerView` 的适配器，负责将数据绑定到列表项的 View 上。`onCreateViewHolder` 创建 `ViewHolder`，`onBindViewHolder` 绑定数据。
    *   `ViewHolder`：持有列表项 View 的引用，避免重复 `findViewById`。
    *   `notifyDataSetChanged()`/`notifyItemRangeInserted()`：通知 `RecyclerView` 数据变化，触发 UI 更新。
*   **`CardView`：** 提供卡片式的 UI 效果，带有圆角和阴影，常用于列表项。

**面试话术：**
“首页作为 App 的核心，我会采用 `MainActivity` 作为主容器，并集成 `BottomNavigationView` 实现底部导航。首页、搜索、我的等核心功能模块则以 `Fragment` 的形式存在，通过 `FragmentManager` 和 `FragmentTransaction` 动态加载到 `MainActivity` 的 `FrameLayout` 容器中，并使用 `hide()`/`show()` 方法优化 Fragment 切换时的性能。首页内容展示我会采用 `RecyclerView`，它具备高效的视图复用机制，能够流畅地显示大量数据。我会为 `RecyclerView` 编写自定义的 `Adapter` 和 `ViewHolder`，将数据绑定到 `CardView` 风格的列表项上，以提供良好的视觉体验。至于数据加载，我会继续沿用 MVVM 模式，通过 `HomeViewModel` 管理数据，并使用 `LiveData` 观察数据变化来更新 `RecyclerView`。”

**2.4 搜索 (搜索)**

**知识技术讲解：**
搜索功能通常包括：
*   **搜索框：** `EditText` 或 `SearchView`。
*   **搜索历史/热门搜索：** 引导用户。
*   **搜索结果列表：** `RecyclerView` 展示搜索结果。
*   **搜索逻辑：** 客户端输入监听、网络请求（模糊搜索、关键词搜索）、结果展示。
*   **防抖 (Debounce)：** 避免用户输入时频繁发送网络请求。

**具体运用示例 (Java 代码):**

1.  **`feature-search/src/main/java/com.example.mypracticeapp.feature.search/view/SearchFragment.java`**
    ```java
    package com.example.mypracticeapp.feature.search.view;

    import android.os.Bundle;
    import android.text.Editable;
    import android.text.TextWatcher;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.EditText;
    import android.widget.ProgressBar;
    import android.widget.TextView;
    import android.widget.Toast;

    import androidx.annotation.NonNull;
    import androidx.annotation.Nullable;
    import androidx.fragment.app.Fragment;
    import androidx.lifecycle.Observer;
    import androidx.lifecycle.ViewModelProvider;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;

    import com.example.mypracticeapp.R;
    import com.example.mypracticeapp.feature.search.adapter.SearchAdapter;
    import com.example.mypracticeapp.feature.search.model.SearchResultItem;
    import com.example.mypracticeapp.feature.search.viewmodel.SearchViewModel;

    import java.util.ArrayList;
    import java.util.List;
    import java.util.Timer; // 导入 Timer
    import java.util.TimerTask; // 导入 TimerTask

    public class SearchFragment extends Fragment {

        private EditText etSearch;
        private RecyclerView recyclerView;
        private SearchAdapter searchAdapter;
        private ProgressBar progressBar;
        private TextView tvNoResults;

        private SearchViewModel searchViewModel;

        private Timer searchTimer = new Timer(); // 用于实现防抖
        private final long DELAY_MS = 500; // 防抖延迟 500 毫秒

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_search, container, false);

            etSearch = view.findViewById(R.id.et_search);
            recyclerView = view.findViewById(R.id.search_results_recycler_view);
            progressBar = view.findViewById(R.id.search_progress_bar);
            tvNoResults = view.findViewById(R.id.tv_no_results);

            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            searchAdapter = new SearchAdapter(new ArrayList<>());
            recyclerView.setAdapter(searchAdapter);

            searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);

            // 观察搜索结果
            searchViewModel.getSearchResults().observe(getViewLifecycleOwner(), new Observer<List<SearchResultItem>>() {
                @Override
                public void onChanged(List<SearchResultItem> searchResultItems) {
                    progressBar.setVisibility(View.GONE);
                    if (searchResultItems != null && !searchResultItems.isEmpty()) {
                        searchAdapter.updateData(searchResultItems);
                        recyclerView.setVisibility(View.VISIBLE);
                        tvNoResults.setVisibility(View.GONE);
                    } else {
                        searchAdapter.updateData(new ArrayList<>()); // 清空数据
                        recyclerView.setVisibility(View.GONE);
                        tvNoResults.setVisibility(View.VISIBLE);
                    }
                }
            });

            // 观察加载状态
            searchViewModel.getIsLoading().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean isLoading) {
                    progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                }
            });

            // 搜索框文本变化监听 (实现防抖)
            etSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    // 取消之前的定时任务
                    if (searchTimer != null) {
                        searchTimer.cancel();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                    final String query = s.toString().trim();
                    if (query.isEmpty()) {
                        // 清空结果，隐藏进度条和“无结果”提示
                        searchAdapter.updateData(new ArrayList<>());
                        progressBar.setVisibility(View.GONE);
                        tvNoResults.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE); // 显示空列表
                        return;
                    }

                    // 启动新的定时任务
                    searchTimer = new Timer();
                    searchTimer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            // 在子线程中执行搜索，但 LiveData 更新需要在主线程
                            // ViewModel 内部会处理切换到主线程更新 LiveData
                            searchViewModel.search(query);
                        }
                    }, DELAY_MS);
                }
            });

            return view;
        }

        @Override
        public void onDestroyView() {
            super.onDestroyView();
            // 销毁 Timer，避免内存泄漏
            if (searchTimer != null) {
                searchTimer.cancel();
                searchTimer = null;
            }
        }
    }
    ```

**`feature-search/src/main/java/com.example.mypracticeapp.feature.search/viewmodel/SearchViewModel.java`**

```java
package com.example.mypracticeapp.feature.search.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mypracticeapp.feature.search.model.SearchRepository;
import com.example.mypracticeapp.feature.search.model.SearchResultItem;

import java.util.List;

public class SearchViewModel extends ViewModel {

    private MutableLiveData<List<SearchResultItem>> searchResults = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private SearchRepository searchRepository;

    public SearchViewModel() {
        searchRepository = new SearchRepository();
    }

    public LiveData<List<SearchResultItem>> getSearchResults() {
        return searchResults;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void search(String query) {
        isLoading.postValue(true); // 显示加载状态

        searchRepository.search(query, new SearchRepository.SearchCallback() {
            @Override
            public void onSuccess(List<SearchResultItem> results) {
                isLoading.postValue(false);
                searchResults.postValue(results);
            }

            @Override
            public void onFailure(String errorMessage) {
                isLoading.postValue(false);
                searchResults.postValue(null); // 或者发送一个空的列表
                // TODO: 处理错误信息，例如显示 Toast
            }
        });
    }
}
```

**`feature-search/src/main/java/com.example.mypracticeapp.feature.search/model/SearchRepository.java`**

```java
package com.example.mypracticeapp.feature.search.model;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class SearchRepository {

    private static final String TAG = "SearchRepository";

    public interface SearchCallback {
        void onSuccess(List<SearchResultItem> results);
        void onFailure(String errorMessage);
    }

    public void search(String query, SearchCallback callback) {
        Log.d(TAG, "Simulating search for: " + query);
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            List<SearchResultItem> mockResults = new ArrayList<>();
            if (query.contains("测试") || query.contains("test")) {
                for (int i = 0; i < 5; i++) {
                    mockResults.add(new SearchResultItem("搜索结果 " + query + "-" + i, "这是关于 '" + query + "' 的描述。"));
                }
            } else if (query.contains("无")) {
                // 模拟无结果
            } else {
                for (int i = 0; i < 3; i++) {
                    mockResults.add(new SearchResultItem("条目 " + query + "_" + i, "详细内容 " + query + " " + i));
                }
            }

            if (mockResults.isEmpty() && !query.contains("无")) {
                callback.onFailure("未找到相关结果"); // 模拟找不到结果的失败
            } else {
                callback.onSuccess(mockResults);
            }
        }, 800); // 模拟网络延迟 0.8 秒
    }
}
```

**`feature-search/src/main/java/com.example.mypracticeapp.feature.search/model/SearchResultItem.java`**

```java
package com.example.mypracticeapp.feature.search.model;

public class SearchResultItem {
    private String title;
    private String description;

    public SearchResultItem(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
```

**`feature-search/src/main/java/com.example.mypracticeapp.feature.search/adapter/SearchAdapter.java`**

```java
package com.example.mypracticeapp.feature.search.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mypracticeapp.R;
import com.example.mypracticeapp.feature.search.model.SearchResultItem;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private List<SearchResultItem> dataList;

    public SearchAdapter(List<SearchResultItem> dataList) {
        this.dataList = dataList;
    }

    public void updateData(List<SearchResultItem> newData) {
        this.dataList.clear();
        if (newData != null) {
            this.dataList.addAll(newData);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_search_result, parent, false); // 假设有 item_search_result.xml 布局
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        SearchResultItem item = dataList.get(position);
        holder.tvTitle.setText(item.getTitle());
        holder.tvDescription.setText(item.getDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "点击了搜索结果: " + item.getTitle(), Toast.LENGTH_SHORT).show();
                // TODO: 跳转到搜索结果详情页
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    static class SearchViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvDescription;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.search_item_title);
            tvDescription = itemView.findViewById(R.id.search_item_description);
        }
    }
}
```

**`feature-search/src/main/res/layout/fragment_search.xml`**

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="16dp">

    <EditText
        android:id="@+id/et_search"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="请输入搜索关键词"
        android:inputType="text"
        android:maxLines="1"
        android:singleLine="true"
        android:padding="12dp"
        android:background="@drawable/edittext_background"
        android:layout_marginBottom="16dp"/>

    <ProgressBar
        android:id="@+id/search_progress_bar"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center_horizontal"
        android:visibility="gone"/>

    <TextView
        android:id="@+id/tv_no_results"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="未找到相关结果"
        android:textSize="18sp"
        android:textColor="@android:color/darker_gray"
        android:layout_gravity="center_horizontal"
        android:layout_marginTop="32dp"
        android:visibility="gone"/>

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/search_results_recycler_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>

</LinearLayout>
```

**`feature-search/src/main/res/layout/item_search_result.xml`**

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.cardview.widget.CardView xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_margin="8dp"
    app:cardCornerRadius="8dp"
    app:cardElevation="4dp">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        android:padding="16dp">

        <TextView
            android:id="@+id/search_item_title"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="Search Result Title"
            android:textSize="18sp"
            android:textStyle="bold"
            android:textColor="@android:color/black"
            android:layout_marginBottom="4dp"/>

        <TextView
            android:id="@+id/search_item_description"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="This is a description for the search result item."
            android:textSize="14sp"
            android:textColor="@android:color/darker_gray"/>

    </LinearLayout>
</androidx.cardview.widget.CardView>
```

**详细文字讲解说明：**
*   **`EditText` + `TextWatcher`：** 用于监听搜索框的文本输入。
*   **防抖 (`Timer`/`TimerTask`)：** 在 `afterTextChanged` 中，每次输入都取消之前的 `TimerTask`，并创建一个新的 `TimerTask` 延迟执行搜索。这样，只有当用户停止输入一段时间后（例如 500ms），才会触发真正的搜索请求，避免频繁的 API 调用，节省资源。
*   **搜索结果展示：** 同样使用 `RecyclerView`。`tvNoResults` 用于在没有搜索结果时向用户提示。
*   **MVVM 模式：** `SearchViewModel` 和 `SearchRepository` 负责搜索逻辑和数据获取。
*   **`onDestroyView()`：** 在 Fragment 销毁 View 时，务必取消或销毁 `Timer`，防止内存泄漏。

**面试话术：**
“搜索功能的核心挑战在于用户输入时的性能优化和网络请求的控制。我会使用 `EditText` 结合 `TextWatcher` 来监听用户输入。为了避免用户每输入一个字符就发送一次网络请求，我会在 `TextWatcher` 的 `afterTextChanged` 方法中实现**防抖 (Debounce)** 机制：每次输入时，取消前一个未执行的定时任务，并启动一个新的定时任务延迟执行搜索。只有当用户停止输入超过预设的延迟时间（例如 500ms）后，才会真正触发 `SearchViewModel` 的 `search()` 方法。搜索结果会通过 `RecyclerView` 展示，并通过 `LiveData` 观察 `SearchViewModel` 的 `searchResults` 和 `isLoading` 状态来更新 UI，包括显示/隐藏进度条和‘未找到结果’提示。在 Fragment 的 `onDestroyView()` 中，我会确保销毁 `Timer` 实例，防止内存泄漏。”

**2.5 我的 (我的)**

**知识技术讲解：**
“我的”页面通常是用户个人信息、设置、关于 App、退出登录等功能的聚合。

**具体运用示例 (Java 代码):**

1.  **`feature-mine/src/main/java/com.example.mypracticeapp.feature.mine/view/MineFragment.java`**
    ```java
    package com.example.mypracticeapp.feature.mine.view;

    import android.content.Intent;
    import android.os.Bundle;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.Button;
    import android.widget.TextView;
    import android.widget.Toast;

    import androidx.annotation.NonNull;
    import androidx.annotation.Nullable;
    import androidx.fragment.app.Fragment;
    import androidx.lifecycle.ViewModelProvider;

    import com.example.mypracticeapp.R;
    import com.example.mypracticeapp.feature.login.view.LoginActivity; // 导入登录页
    import com.example.mypracticeapp.feature.mine.viewmodel.MineViewModel; // 导入 ViewModel

    public class MineFragment extends Fragment {

        private TextView tvUsername;
        private Button btnSettings;
        private Button btnAbout;
        private Button btnLogout;

        private MineViewModel mineViewModel;

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_mine, container, false);

            tvUsername = view.findViewById(R.id.tv_mine_username);
            btnSettings = view.findViewById(R.id.btn_mine_settings);
            btnAbout = view.findViewById(R.id.btn_mine_about);
            btnLogout = view.findViewById(R.id.btn_mine_logout);

            mineViewModel = new ViewModelProvider(this).get(MineViewModel.class);

            // 观察用户名 LiveData (如果用户已登录)
            mineViewModel.getUsername().observe(getViewLifecycleOwner(), username -> {
                if (username != null && !username.isEmpty()) {
                    tvUsername.setText("欢迎你，" + username + "！");
                } else {
                    tvUsername.setText("请登录");
                    // 可以设置点击 tvUsername 跳转到登录页
                }
            });

            // 模拟加载用户信息
            mineViewModel.loadUserInfo(); // 实际中会从 SharedPreferences 或网络加载

            btnSettings.setOnClickListener(v -> {
                Toast.makeText(getContext(), "进入设置页面", Toast.LENGTH_SHORT).show();
                // TODO: 跳转到设置 Activity
            });

            btnAbout.setOnClickListener(v -> {
                Toast.makeText(getContext(), "进入关于页面", Toast.LENGTH_SHORT).show();
                // TODO: 跳转到关于 Activity
            });

            btnLogout.setOnClickListener(v -> {
                // 执行退出登录逻辑
                mineViewModel.logout();
                Toast.makeText(getContext(), "已退出登录", Toast.LENGTH_SHORT).show();
                // 清除本地登录状态，并跳转回登录页
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // 清除所有之前的 Activity
                startActivity(intent);
            });

            return view;
        }
    }
    ```

2.  **`feature-mine/src/main/java/com.example.mypracticeapp.feature.mine/viewmodel/MineViewModel.java`**
    ```java
    package com.example.mypracticeapp.feature.mine.viewmodel;

    import androidx.lifecycle.LiveData;
    import androidx.lifecycle.MutableLiveData;
    import androidx.lifecycle.ViewModel;

    import com.example.mypracticeapp.feature.mine.model.MineRepository; // 导入 Repository

    public class MineViewModel extends ViewModel {

        private MutableLiveData<String> username = new MutableLiveData<>();
        private MineRepository mineRepository;

        public MineViewModel() {
            mineRepository = new MineRepository();
        }

        public LiveData<String> getUsername() {
            return username;
        }

        /**
         * 加载用户信息 (例如从本地存储或网络)
         */
        public void loadUserInfo() {
            // 模拟从本地加载用户名
            String storedUsername = mineRepository.getStoredUsername();
            if (storedUsername != null) {
                username.postValue(storedUsername);
            } else {
                username.postValue("未登录用户"); // 或者显示“请登录”
            }
        }

        /**
         * 执行退出登录操作
         */
        public void logout() {
            mineRepository.clearLoginState(); // 清除本地存储的登录信息
            username.postValue(null); // 更新用户名状态
        }
    }
    ```

3.  **`feature-mine/src/main/java/com.example.mypracticeapp.feature.mine/model/MineRepository.java`**
    ```java
    package com.example.mypracticeapp.feature.mine.model;

    // 假设有一个 SharedPreferencesUtil 用于操作本地存储
    import com.example.mypracticeapp.common.utils.SharedPreferencesUtil; // 导入 SharedPreferencesUtil

    public class MineRepository {

        public String getStoredUsername() {
            // 实际中从 SharedPreferences 或 DataStore 读取用户名
            return SharedPreferencesUtil.getString("current_username", null);
        }

        public void clearLoginState() {
            // 实际中清除 SharedPreferences 或 DataStore 中的所有登录相关信息
            SharedPreferencesUtil.clearAll(); // 清除所有数据 (示例)
        }
    }
    ```
    *   **注意：** `SharedPreferencesUtil` 需要在 `common` 模块中实现。

4.  **`common/src/main/java/com.example.mypracticeapp.common.utils/SharedPreferencesUtil.java` (公共工具类)**
    ```java
    package com.example.mypracticeapp.common.utils;

    import android.content.Context;
    import android.content.SharedPreferences;

    public class SharedPreferencesUtil {

        private static final String PREF_NAME = "my_app_prefs";
        private static SharedPreferences sSharedPreferences;
        private static SharedPreferences.Editor sEditor;

        public static void init(Context context) {
            if (sSharedPreferences == null) {
                sSharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
                sEditor = sSharedPreferences.edit();
            }
        }

        public static void putString(String key, String value) {
            if (sEditor != null) {
                sEditor.putString(key, value).apply();
            }
        }

        public static String getString(String key, String defaultValue) {
            if (sSharedPreferences != null) {
                return sSharedPreferences.getString(key, defaultValue);
            }
            return defaultValue;
        }

        public static void clearAll() {
            if (sEditor != null) {
                sEditor.clear().apply();
            }
        }
    }
    ```
    *   **注意：** `SharedPreferencesUtil.init(Context)` 需要在 `Application` 类中调用。

5.  **`app/src/main/java/com.example.mypracticeapp/MyApplication.java` (Application 类)**
    ```java
    package com.example.mypracticeapp;

    import android.app.Application;

    import com.example.mypracticeapp.common.utils.SharedPreferencesUtil;

    public class MyApplication extends Application {

        @Override
        public void onCreate() {
            super.onCreate();
            // 在 Application 启动时初始化 SharedPreferencesUtil
            SharedPreferencesUtil.init(this);
        }
    }
    ```
    *   **注意：** 需要在 `AndroidManifest.xml` 中注册 `MyApplication`：
        `<application android:name=".MyApplication" ...>`

6.  **`feature-mine/src/main/res/layout/fragment_mine.xml`**
    ```xml
    <?xml version="1.0" encoding="utf-8"?>
    <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
        android:layout_width="match_content"
        android:layout_height="match_parent"
        android:orientation="vertical"
        android:gravity="center_horizontal"
        android:padding="24dp">

        <ImageView
            android:layout_width="80dp"
            android:layout_height="80dp"
            android:src="@drawable/ic_person_black_24dp"
            android:tint="@color/design_default_color_primary"
            android:layout_marginBottom="16dp"/>

        <TextView
            android:id="@+id/tv_mine_username"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="欢迎你，用户！"
            android:textSize="22sp"
            android:textStyle="bold"
            android:textColor="@android:color/black"
            android:layout_marginBottom="32dp"/>

        <Button
            android:id="@+id/btn_mine_settings"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="设置"
            android:layout_marginBottom="12dp"
            style="@style/MineButtonStyle"/>

        <Button
            android:id="@+id/btn_mine_about"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="关于"
            android:layout_marginBottom="12dp"
            style="@style/MineButtonStyle"/>

        <Button
            android:id="@+id/btn_mine_logout"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="退出登录"
            android:background="@drawable/button_background_red"
            android:textColor="@android:color/white"
            android:layout_marginTop="32dp"
            style="@style/MineButtonStyle"/>

    </LinearLayout>
    ```

7.  **`feature-mine/src/main/res/values/styles.xml` (自定义按钮样式)**
    ```xml
    <?xml version="1.0" encoding="utf-8"?>
    <resources>
        <style name="MineButtonStyle" parent="Widget.AppCompat.Button">
            <item name="android:layout_width">match_parent</item>
            <item name="android:layout_height">wrap_content</item>
            <item name="android:padding">12dp</item>
            <item name="android:textSize">18sp</item>
            <item name="android:textColor">@android:color/black</item>
            <item name="android:background">@drawable/button_background_white</item>
        </style>
    </resources>
    ```

8.  **`feature-mine/src/main/res/drawable/button_background_white.xml`**
    ```xml
    <?xml version="1.0" encoding="utf-8"?>
    <shape xmlns:android="http://schemas.android.com/apk/res/android"
        android:shape="rectangle">
        <solid android:color="@android:color/white"/>
        <corners android:radius="8dp"/>
        <stroke android:width="1dp" android:color="#DDDDDD"/>
    </shape>
    ```

9.  **`feature-mine/src/main/res/drawable/button_background_red.xml` (退出登录按钮)**
    ```xml
    <?xml version="1.0" encoding="utf-8"?>
    <selector xmlns:android="http://schemas.android.com/apk/res/android">
        <item android:state_pressed="true">
            <shape android:shape="rectangle">
                <corners android:radius="8dp"/>
                <solid android:color="#CC0000"/>
            </shape>
        </item>
        <item>
            <shape android:shape="rectangle">
                <corners android:radius="8dp"/>
                <solid android:color="#FF0000"/>
            </shape>
        </item>
    </selector>
    ```

**详细文字讲解说明：**
*   **用户状态管理：** `MineViewModel` 负责管理用户的登录状态，例如显示用户名、处理退出登录。
*   **`SharedPreferencesUtil`：** 这是一个简单的工具类，用于在本地存储和读取键值对数据，这里用于模拟存储用户名和清除登录状态。在实际项目中，更推荐使用 `DataStore`。
*   **`Application` 类：** `SharedPreferencesUtil` 在 `Application` 类的 `onCreate()` 中进行初始化，确保在 App 启动时就可用。`Application` 类是 App 进程的入口，在所有 Activity、Service 等组件创建之前被创建。
*   **退出登录：** 退出登录操作通常包括：
    1.  清除本地存储的用户 Token 和其他身份信息。
    2.  清除内存中的用户数据。
    3.  跳转回登录页，并清除 Activity 栈，防止用户按返回键回到主页。`Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK` 标志可以实现这个目的。

**面试话术：**
“‘我的’页面主要用于展示用户个人信息和提供设置、关于、退出登录等功能。我依然会采用 Fragment + MVVM 的模式来构建。`MineViewModel` 会通过 `MineRepository` 获取用户数据（例如从 `SharedPreferences` 或 `DataStore`），并通过 `LiveData` 暴露给 `MineFragment`。退出登录功能是其核心之一，点击退出后，我会调用 `MineViewModel` 的 `logout()` 方法，该方法会清除本地存储的用户 Token 等身份信息。随后，通过 `Intent` 跳转回 `LoginActivity`，并设置 `FLAG_ACTIVITY_NEW_TASK` 和 `FLAG_ACTIVITY_CLEAR_TASK` 标志，以清除所有之前的 Activity 栈，确保用户无法通过返回键再次进入主页。”

#### Part 3: 工程与发布流程

**3.1 项目结构回顾**

**知识技术讲解：**
一个典型的 Android 项目结构：

```
MyPracticeApp/
├── app/                        # 主应用模块
│   ├── build.gradle.kts        # app 模块的构建配置
│   ├── src/
│   │   ├── main/
│   │   │   ├── AndroidManifest.xml # 应用清单文件
│   │   │   ├── java/
│   │   │   │   └── com/example/mypracticeapp/ # 主应用代码 (MainActivity, MyApplication)
│   │   │   └── res/            # 资源文件 (layout, drawable, values, menu, mipmap)
│   ├── ...
├── common/                     # 公共库模块
│   ├── build.gradle.kts
│   ├── src/
│   │   └── main/
│   │       └── java/
│   │           └── com/example/mypracticeapp/common/utils/ # 公共工具类 (SharedPreferencesUtil)
├── feature-login/              # 登录注册模块
│   ├── build.gradle.kts
│   ├── src/
│   │   └── main/
│   │       ├── java/
│   │       │   └── com/example/mypracticeapp/feature/login/ # 登录注册相关代码 (view, model, viewmodel)
│   │       └── res/            # 登录注册模块的资源文件 (layout, drawable)
├── feature-home/               # 首页模块
│   ├── build.gradle.kts
│   ├── src/
│   │   └── main/
│   │       └── java/
│   │           └── com/example/mypracticeapp/feature/home/ # 首页相关代码 (adapter, model, view, viewmodel)
│   │       └── res/
├── feature-search/             # 搜索模块
│   ├── build.gradle.kts
│   ├── src/
│   │   └── main/
│   │       └── java/
│   │           └── com/example/mypracticeapp/feature/search/ # 搜索相关代码
│   │       └── res/
├── feature-mine/               # 我的模块
│   ├── build.gradle.kts
│   ├── src/
│   │   └── main/
│   │       └── java/
│   │           └── com/example/mypracticeapp/feature/mine/ # 我的相关代码
│   │       └── res/
├── build.gradle.kts            # 项目根目录的构建配置 (top-level build file)
├── settings.gradle.kts         # 项目模块配置
├── gradle/
│   └── wrapper/
│       └── gradle-wrapper.properties # Gradle wrapper 配置
├── gradlew                     # Gradle Wrapper 脚本 (Linux/macOS)
├── gradlew.bat                 # Gradle Wrapper 脚本 (Windows)
└── ...
```

**详细文字讲解说明：**
*   **`build.gradle.kts` (Kotlin DSL)：** 每个模块都有自己的 `build.gradle.kts` 文件，用于配置该模块的编译、依赖、插件等。项目根目录还有一个顶级的 `build.gradle.kts` 用于全局配置。
*   **`settings.gradle.kts`：** 告诉 Gradle 项目中包含哪些模块，例如 `include(":app", ":common", ":feature-login", ...)`。
*   **`AndroidManifest.xml`：** 每个 Android 模块（包括 Library 模块）都有一个 `AndroidManifest.xml`。最终，所有模块的 `AndroidManifest.xml` 会在构建时合并到 `app` 模块的 `AndroidManifest.xml` 中。
*   **`res` 目录：** 存放资源文件，如布局 (`layout`)、图片 (`drawable`, `mipmap`)、字符串 (`values/strings.xml`)、颜色 (`values/colors.xml`)、样式 (`values/styles.xml` 或 `themes.xml`)、菜单 (`menu`) 等。

**面试话术：**
“我的项目结构遵循模块化原则，主要分为一个主 `app` 模块和多个功能模块（如 `common`、`feature-login`、`feature-home`、`feature-search`、`feature-mine`）。每个模块都有独立的 `build.gradle.kts` 文件来管理其构建配置和依赖。`app` 模块作为主入口，集成了所有功能模块。这种结构清晰地划分了职责，提高了代码复用性、可维护性和团队协作效率。所有的资源文件都存放在各自模块的 `res` 目录下，并在构建时由 Gradle 进行合并。”

**3.2 打包 (打包)**

**知识技术讲解：**
打包是将 App 的源代码、资源、依赖库等编译、链接成一个可安装的 APK (Android Package) 或 AAB (Android App Bundle) 文件的过程。

*   **Debug APK：** 用于开发和测试，通常不签名或使用调试签名，可以直接安装到设备。
*   **Release APK/AAB：** 用于发布到应用商店，必须经过签名。

**具体运用示例 (操作步骤):**

1.  **生成 Debug APK：**
    *   在 Android Studio 菜单栏，选择 **"Build" -> "Build Bundle(s) / APK(s)" -> "Build APK(s)"**。
    *   构建完成后，Android Studio 会在事件日志中提供一个链接，点击 **"locate"** 可以找到生成的 APK 文件，通常在 `app/build/outputs/apk/debug/` 目录下。

2.  **生成 Release APK/AAB (用于发布)：**
    *   这需要先进行签名。

**详细文字讲解说明：**
*   **APK：** Android Package，直接包含所有代码和资源。
*   **AAB (Android App Bundle)：** Google 推荐的发布格式。它将 App 的所有编译代码和资源打包在一起，但推迟 APK 的生成和签名到 Google Play。Google Play 会根据用户的设备配置（如屏幕密度、CPU 架构、语言）动态生成优化的 APK，从而减小用户下载包体的大小。

**面试话术：**
“打包是将 App 的所有代码、资源和依赖编译、链接成可安装文件（APK 或 AAB）的过程。在开发调试阶段，我通常通过 Android Studio 菜单 'Build' -> 'Build APK(s)' 生成 Debug APK，用于快速测试。对于最终发布，我则会生成 Release 版本的 APK 或 AAB。AAB 是 Google 推荐的发布格式，它允许 Google Play 根据用户设备动态生成优化的 APK，从而减小用户下载包体大小。”

**3.3 签名 (签名)**

**知识技术讲解：**
Android App 必须经过数字签名才能安装到设备或发布到应用商店。签名用于：
*   **身份验证：** 验证 App 的开发者身份。
*   **完整性：** 确保 App 未被篡改。
*   **更新：** 只有使用相同密钥签名的 App 才能进行更新。

**具体运用示例 (操作步骤):**

1.  **生成签名密钥 (KeyStore)：**
    *   在 Android Studio 菜单栏，选择 **"Build" -> "Generate Signed Bundle / APK..."**。
    *   选择 **"Android App Bundle"** 或 **"APK"**，点击 **"Next"**。
    *   在 "Key store path" 旁边点击 **"Create new..."**。
    *   填写以下信息：
        *   **Key store path:** 选择 `.jks` 文件的保存路径和文件名（例如 `my_release_key.jks`）。
        *   **Password:** 密钥库密码。
        *   **Key:**
            *   **Alias:** 密钥别名（例如 `my_key_alias`）。
            *   **Password:** 密钥密码（可以和密钥库密码相同）。
            *   **Validity (years):** 密钥有效期，建议设置 25 年以上。
            *   **Certificate:** 填写您的组织信息（姓名、组织单位、组织、城市、省份、国家代码）。
        *   点击 **"OK"**。
    *   **重要：** 妥善保管您的 `.jks` 文件和密码，丢失将无法更新 App！

2.  **配置 `build.gradle.kts` 使用签名密钥 (可选，推荐手动配置)**
    *   将 `.jks` 文件放在项目的 `app` 模块目录下（例如 `app/my_release_key.jks`）。
    *   在 `app/build.gradle.kts` 中配置签名信息：
        ```kotlin
        // app/build.gradle.kts
        android {
            // ... 其他配置

            signingConfigs {
                create("release") {
                    storeFile = file("my_release_key.jks") // 密钥库文件路径
                    storePassword = "your_key_store_password" // 密钥库密码
                    keyAlias = "my_key_alias" // 密钥别名
                    keyPassword = "your_key_password" // 密钥密码
                }
            }

            buildTypes {
                release {
                    // ... 其他 release 配置
                    signingConfig = signingConfigs.getByName("release") // 应用签名配置
                    minifyEnabled = true // 启用代码混淆和资源压缩
                    proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
                }
            }
        }
        ```
    *   **注意：** 密钥库密码和密钥密码不应直接写在 `build.gradle.kts` 中。更好的做法是将其放在 `gradle.properties` 文件中，并使用 `System.getenv()` 或 `project.findProperty()` 来读取，或者使用 CI/CD 工具的安全变量。

3.  **生成签名的 Release APK/AAB：**
    *   再次选择 **"Build" -> "Generate Signed Bundle / APK..."**。
    *   选择 **"Android App Bundle"** 或 **"APK"**，点击 **"Next"**。
    *   选择您刚刚创建的密钥库，输入密码和别名密码。
    *   选择 **"release"** 构建类型。
    *   点击 **"Finish"**。生成的 APK/AAB 文件在 `app/build/outputs/bundle/release/` 或 `app/build/outputs/apk/release/` 目录下。

**详细文字讲解说明：**
*   **KeyStore (`.jks` 文件)：** 包含了您的数字证书和私钥。它是 App 身份的唯一凭证。
*   **密码保护：** 密钥库和密钥本身都受密码保护。
*   **有效期：** 建议设置较长的有效期，因为一旦过期，您将无法使用该密钥更新 App。
*   **`minifyEnabled` 和 `proguardFiles`：** 在 Release 构建中通常会启用代码混淆 (`minifyEnabled = true`)，它会混淆代码、移除未使用代码，从而减小 APK 体积并提高安全性。`proguard-rules.pro` 用于定义混淆规则。

**面试话术：**
“App 发布前必须进行数字签名。签名主要用于验证开发者身份、保证 App 完整性以及支持后续更新。我会通过 Android Studio 的 'Generate Signed Bundle / APK...' 向导来生成一个签名密钥库 (`.jks` 文件)。这个 `.jks` 文件包含了我的数字证书和私钥，并且受到密码保护。我会在 `app/build.gradle.kts` 中配置这个签名密钥库的路径、密码和别名，并将其应用到 Release 构建类型中。在 Release 构建时，我还会启用 `minifyEnabled` 来进行代码混淆和资源压缩，以减小 APK 体积并提高安全性。妥善保管 `.jks` 文件和密码至关重要，因为一旦丢失，App 将无法更新。”

**3.4 发布 (发布) - 高级概述**

**知识技术讲解：**
发布是将您的 App 上传到应用商店供用户下载的过程。

*   **Google Play Console：** 官方应用商店。
*   **国内应用商店：** 华为应用市场、小米应用商店、腾讯应用宝、OPPO 软件商店、vivo 应用商店等。

**具体运用示例 (概念性):**

1.  **注册开发者账号：** 在 Google Play Console 或国内应用商店注册开发者账号（通常需要付费）。
2.  **准备发布信息：**
    *   App 名称、简短描述、完整描述。
    *   屏幕截图、宣传图、应用图标。
    *   隐私政策 URL。
    *   分类、内容分级。
3.  **上传 AAB/APK：** 将您签名的 Release AAB 或 APK 文件上传到应用商店。
4.  **配置发布渠道：**
    *   **内部测试：** 供内部团队测试。
    *   **封闭测试/开放测试：** 邀请部分用户进行测试。
    *   **正式发布：** 面向所有用户。
5.  **审核：** 应用商店会对您的 App 进行审核，确保其符合平台政策。
6.  **发布：** 审核通过后，您可以选择发布 App。

**详细文字讲解说明：**
*   **Google Play Console：** 是 Android App 发布到全球市场的官方平台。
*   **国内市场：** 中国大陆有多个主流应用商店，需要分别提交。
*   **审核周期：** 应用商店的审核周期可能从几小时到几天不等。
*   **版本管理：** 每次更新 App 都需要上传新的签名版本。

**面试话术：**
“App 发布通常是指将其上传到应用商店供用户下载。对于全球市场，我会将 App 发布到 Google Play Console，这需要注册开发者账号并准备详细的发布信息，包括 App 名称、描述、截图、图标、隐私政策 URL 等。对于国内市场，则需要分别提交到华为、小米、腾讯应用宝等主流应用商店。在发布前，我会仔细配置内部测试、封闭测试、开放测试等发布渠道，进行充分测试。所有应用在发布前都需要经过平台审核，确保符合其政策和规范。每次 App 更新都需要上传新的签名版本。”

#### Part 4: 性能优化

性能优化是提升 App 用户体验的关键，也是一个持续的过程。

**4.1 UI 优化 (UI 优化)**

**知识技术讲解：**
UI 优化旨在提高界面的渲染速度和流畅性，避免卡顿。

1.  **布局层级优化：**
    *   **问题：** 过深的 View 层次结构会导致测量和布局阶段的性能下降。
    *   **优化：**
        *   使用 `ConstraintLayout`：它是一个扁平化的布局，能够通过约束实现复杂的 UI，减少嵌套。
        *   使用 `<merge>` 标签：当布局文件作为另一个布局的根 View 包含时，可以避免不必要的 View 层次。
        *   使用 `<include>` 标签：复用布局片段。
        *   自定义 `ViewGroup`：针对特定复杂布局需求，自定义 `ViewGroup` 可以扁平化 View 树。
2.  **过度绘制 (Overdraw) 减少：**
    *   **问题：** 屏幕上的某个像素被绘制了多次，浪费 GPU 资源。例如，多个重叠 View 都有背景。
    *   **检测：** 开发者选项 -> 调试 GPU 过度绘制。
    *   **优化：**
        *   移除不必要的背景：如果一个 View 完全被其子 View 覆盖，它的背景可以移除。
        *   使用 `canvas.clipRect()` 或 `canvas.clipPath()`：在自定义 View 中裁剪绘制区域。
        *   使用 `android:clipChildren="false"` 和 `android:clipToPadding="false"`：谨慎使用，它们会影响裁剪行为。
3.  **自定义 View 优化：**
    *   **问题：** 在 `onDraw()` 中创建对象或执行耗时操作。
    *   **优化：**
        *   **`Paint` 等对象初始化：** 所有 `Paint`、`Path`、`Bitmap` 等绘制对象都应在 View 的构造函数或 `init()` 方法中初始化，避免在 `onDraw()` 中频繁创建。
        *   **避免复杂计算：** `onDraw()` 应该只专注于绘制，避免进行大量计算。
        *   **合理使用 `invalidate()` 和 `requestLayout()`：** 根据变化类型选择正确的方法，避免不必要的 `requestLayout()`。
        *   **利用硬件加速：** 确保绘制操作能够利用硬件加速，避免使用不支持硬件加速的 `Canvas` 操作。

**具体运用示例 (概念性):**

*   **布局层级：** 将多层嵌套的 `LinearLayout` 替换为 `ConstraintLayout`。
*   **过度绘制：** 检查 `activity_main_with_bottom_nav.xml` 中 `FrameLayout` 和 `BottomNavigationView` 的背景，如果它们有重叠部分，考虑移除其中一个的默认背景。
*   **自定义 View：** 确保所有自定义 View 的 `onDraw()` 方法中没有 `new` 操作。

**面试话术：**
“UI 优化主要关注界面的渲染性能和流畅性。我会从以下几方面入手：
1.  **布局层级优化：** 避免过深的 View 嵌套，优先使用 `ConstraintLayout` 来实现扁平化布局，必要时使用 `<merge>` 标签或自定义 `ViewGroup`。
2.  **减少过度绘制：** 通过开发者选项的 '调试 GPU 过度绘制' 来发现问题。优化方法包括移除不必要的背景、使用 `canvas.clipRect()` 限制绘制区域。
3.  **自定义 View 优化：** 确保在 `onDraw()` 方法中不进行任何对象创建或耗时计算，所有 `Paint`、`Path` 等绘制对象都应在构造函数中初始化。同时，根据变化类型正确使用 `invalidate()` 和 `requestLayout()`。”

**4.2 启动优化 (启动优化)**

**知识技术讲解：**
启动优化旨在缩短 App 从点击图标到第一个界面完全显示的时间。

1.  **Application 初始化：**
    *   **问题：** `Application.onCreate()` 中进行了过多的耗时初始化操作。
    *   **优化：**
        *   **延迟初始化：** 将非必要的初始化操作延迟到第一次使用时或 App 启动后异步进行。
        *   **懒加载：** 采用懒加载策略，只在真正需要时才初始化资源。
        *   **异步初始化：** 使用线程池或协程将耗时初始化任务放到后台线程执行。
        *   **按需加载：** 针对不同的模块，按需初始化其依赖的 SDK。
        *   **启动器 (App Startup)：** Google 提供的 Jetpack App Startup 库，可以简化启动时组件的初始化，并允许以同步或异步方式初始化组件，减少冷启动开销。
2.  **Splash Screen 优化：**
    *   **问题：** Splash Screen 停留时间过长，或其本身加载耗时。
    *   **优化：**
        *   Splash 页面布局尽量简单，避免复杂的 View 层次。
        *   Splash 页面不进行网络请求或数据库操作。
        *   利用 Android 12+ 的 Splash Screen API，系统层面优化启动体验。

**具体运用示例 (概念性):**

*   **延迟初始化：** 将 `SharedPreferencesUtil.init(this)` 这样的操作放在 `Application.onCreate()` 中是合理的，因为它很快。但如果是第三方 SDK 初始化、图片库初始化等，可以考虑延迟。
*   **App Startup：** 如果有多个 SDK 需要初始化，可以考虑集成 App Startup 库。

**面试话术：**
“启动优化是提升用户第一印象的关键。我会关注 `Application.onCreate()` 方法，避免其中进行过多的耗时同步初始化操作。策略包括：
1.  **延迟初始化和懒加载：** 将非核心或耗时的 SDK 初始化、数据预加载等操作延迟到第一次使用时，或在 App 启动后异步进行。
2.  **异步初始化：** 对于必须在启动时进行的耗时初始化，我会将其放入后台线程（例如使用线程池）执行，避免阻塞主线程。
3.  **使用 Jetpack App Startup 库：** 如果有多个组件需要初始化，我会考虑使用 App Startup 库来简化和优化初始化流程。
4.  **Splash Screen 优化：** 确保 Splash 页面布局简单，不进行任何耗时操作，并且利用 Android 12+ 的 Splash Screen API 优化体验。”

**4.3 通用性能优化 (性能优化)**

**知识技术讲解：**
涵盖内存、CPU、网络、电量等方面的综合优化。

1.  **内存优化：**
    *   **避免内存泄漏：**
        *   及时解除对 `Context` 的引用（尤其是在异步任务和内部类中）。
        *   避免持有对 `Activity` 或 `Fragment` 的长生命周期引用。
        *   `Handler` 内存泄漏：使用静态内部类 + 弱引用 `Activity`。
        *   非静态匿名内部类持有外部类引用。
        *   资源未关闭：`Cursor`、`Stream`、`Bitmap` 等使用后及时关闭或回收。
    *   **优化图片加载：**
        *   使用 Glide、Picasso 等图片加载库，它们有内存和磁盘缓存、图片压缩和缩放功能。
        *   根据 `ImageView` 大小加载合适分辨率的图片，避免加载过大图片。
        *   及时回收不再使用的 `Bitmap`。
    *   **减少内存抖动：** 避免在循环或 `onDraw` 等频繁调用的方法中频繁创建小对象。
2.  **CPU 优化：**
    *   **避免主线程耗时操作：** 所有网络请求、数据库操作、大文件读写、复杂计算都应在子线程执行。
    *   **优化算法和数据结构：** 选择高效的算法和适合的数据结构。
    *   **减少不必要的布局重绘：** 合理使用 `invalidate()` 和 `requestLayout()`。
    *   **合理使用多线程：** 避免创建过多线程导致线程切换开销。
3.  **网络优化：**
    *   **请求合并与缓存：** 合并相似请求，使用网络缓存（HTTP 缓存、OkHttp 缓存）。
    *   **数据压缩：** 开启 GZIP 压缩。
    *   **协议优化：** 使用 HTTP/2 或 QUIC。
    *   **请求限流与重试：** 合理控制并发请求，设置重试策略。
    *   **弱网优化：** 适配弱网络环境，降低图片质量、减少数据量。
4.  **电量优化：**
    *   **减少网络请求频率和数据量。**
    *   **合理使用传感器：** 不使用时及时注销。
    *   **优化定位服务：** 根据需求选择合适的定位精度和更新频率。
    *   **后台任务管理：** 使用 `WorkManager` 进行后台任务调度，适应 Doze 模式和 App Standby。

**具体运用示例 (概念性):**

*   **内存泄漏：** 确保 `Handler` 使用静态内部类。
*   **图片加载：** 集成 Glide 库。
*   **网络：** 使用 OkHttp 或 Retrofit 进行网络请求，并配置缓存。
*   **后台任务：** 如果有需要后台执行的任务（如数据同步），使用 `WorkManager`。

**面试话术：**
“通用性能优化是一个涵盖内存、CPU、网络和电量等多方面的持续过程。
在**内存优化**方面，我会重点避免内存泄漏，例如解除对 `Context` 的长生命周期引用、使用静态内部类处理 `Handler`。同时，会优化图片加载，使用 Glide 等库进行缓存和按需加载合适分辨率的图片，并及时回收 `Bitmap`。
**CPU 优化**的核心是避免主线程阻塞，所有耗时操作都放在子线程执行。我会优化算法，并合理使用 `invalidate()` 和 `requestLayout()` 减少不必要的布局重绘。
**网络优化**包括请求合并、使用缓存、数据压缩（如 GZIP）、以及适配弱网络环境。
**电量优化**则主要通过减少不必要的网络请求频率和数据量，合理使用传感器和定位服务，并利用 `WorkManager` 等组件进行后台任务的智能调度，以适应系统 Doze 模式。”

#### Part 5: 加分项实现

**5.1 首页使用瀑布流 (瀑布流)**

**知识技术讲解：**
瀑布流布局是一种不规则的多列布局，每列高度不一，但整体排列紧密，常用于图片展示。`RecyclerView` 结合 `StaggeredGridLayoutManager` 可以轻松实现。

**具体运用示例 (Java 代码):**

1.  **`feature-home/src/main/java/com.example.mypracticeapp.feature.home/view/HomeFragment.java` (修改 `RecyclerView` 的 `LayoutManager`)**
    ```java
    // ... (HomeFragment.java 的其他代码)

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.home_recycler_view);

        // --- 修改这里为 StaggeredGridLayoutManager ---
        // 参数1: 列数，例如 2 列
        // 参数2: 布局方向，可以是 RecyclerView.VERTICAL 或 RecyclerView.HORIZONTAL
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        // --- ------------------------------------ ---

        homeAdapter = new HomeAdapter(new ArrayList<>());
        recyclerView.setAdapter(homeAdapter);

        // 模拟不同高度的数据
        List<HomeItem> mockItems = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            // 随机生成不同高度的描述，模拟瀑布流效果
            String description = "Description for item " + i + ". ";
            for (int j = 0; j < (i % 5) + 1; j++) { // 模拟不同长度的描述
                description += "This is a longer text to make the item height vary. ";
            }
            mockItems.add(new HomeItem("Title " + i, description));
        }
        homeAdapter.updateData(mockItems);

        return view;
    }
    // ...
    ```
2.  **`feature-home/src/main/res/layout/item_home_list.xml` (确保 `CardView` 高度为 `wrap_content`)**
    
    *   保持 `android:layout_height="wrap_content"`，这是瀑布流的关键。

**详细文字讲解说明：**
*   **`StaggeredGridLayoutManager`：** 这是实现瀑布流的关键 `LayoutManager`。它允许列表项在多列中以不规则的高度排列。
*   **`android:layout_height="wrap_content"`：** 列表项的根布局（例如 `CardView`）的高度必须设置为 `wrap_content`，这样 `StaggeredGridLayoutManager` 才能根据内容自动调整其高度。

**面试话术：**
“首页的瀑布流布局，我通过 `RecyclerView` 结合 `StaggeredGridLayoutManager` 实现。`StaggeredGridLayoutManager` 允许列表项在多列中以不规则的高度进行排列，这非常适合图片流或内容卡片流的展示。关键在于，列表项的根布局高度必须设置为 `wrap_content`，这样 `StaggeredGridLayoutManager` 才能根据每个列表项内容的实际高度进行自适应布局，形成瀑布流效果。”

**5.2 首页有下拉刷新 (下拉刷新)**

**知识技术讲解：**
下拉刷新是用户在列表顶部下拉时触发数据刷新的常见手势。`SwipeRefreshLayout` 提供了便捷的实现方式。

**具体运用示例 (Java 代码):**

1.  **`feature-home/src/main/res/layout/fragment_home.xml` (用 `SwipeRefreshLayout` 包裹 `RecyclerView`)**
    ```xml
    <?xml version="1.0" encoding="utf-8"?>
    <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical">

        <TextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="首页内容"
            android:textSize="28sp"
            android:textStyle="bold"
            android:gravity="center"
            android:padding="16dp"
            android:background="#DDDDDD"/>

        <!-- 用 SwipeRefreshLayout 包裹 RecyclerView -->
        <androidx.swiperefreshlayout.widget.SwipeRefreshLayout
            android:id="@+id/swipe_refresh_layout"
            android:layout_width="match_parent"
            android:layout_height="match_parent">

            <androidx.recyclerview.widget.RecyclerView
                android:id="@+id/home_recycler_view"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:scrollbars="vertical"/>

        </androidx.swiperefreshlayout.widget.SwipeRefreshLayout>

    </LinearLayout>
    ```
    *   **注意：** 需要在 `build.gradle.kts` 中添加依赖：
        `implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")`

2.  **`feature-home/src/main/java/com.example.mypracticeapp.feature.home/view/HomeFragment.java` (添加刷新逻辑)**
    ```java
    package com.example.mypracticeapp.feature.home.view;

    // ... (其他导入)
    import androidx.swiperefreshlayout.widget.SwipeRefreshLayout; // 导入 SwipeRefreshLayout

    public class HomeFragment extends Fragment {

        private RecyclerView recyclerView;
        private HomeAdapter homeAdapter;
        private HomeViewModel homeViewModel;
        private SwipeRefreshLayout swipeRefreshLayout; // 声明 SwipeRefreshLayout

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_home, container, false);
            recyclerView = view.findViewById(R.id.home_recycler_view);
            swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout); // 绑定 SwipeRefreshLayout

            // ... (RecyclerView 布局管理器和 Adapter 初始化)
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)); // 瀑布流

            homeAdapter = new HomeAdapter(new ArrayList<>());
            recyclerView.setAdapter(homeAdapter);

            // --- 下拉刷新逻辑 ---
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    // 执行刷新操作，例如重新加载数据
                    Toast.makeText(getContext(), "正在刷新数据...", Toast.LENGTH_SHORT).show();
                    // 模拟网络请求延迟
                    new Handler(Looper.getMainLooper()).postDelayed(() -> {
                        List<HomeItem> refreshedItems = new ArrayList<>();
                        for (int i = 0; i < 10; i++) { // 模拟新数据
                            String description = "Refreshed item " + i + ". ";
                            for (int j = 0; j < (i % 5) + 1; j++) {
                                description += "This is refreshed content. ";
                            }
                            refreshedItems.add(new HomeItem("刷新标题 " + i, description));
                        }
                        homeAdapter.updateData(refreshedItems);
                        swipeRefreshLayout.setRefreshing(false); // 停止刷新动画
                        Toast.makeText(getContext(), "刷新完成！", Toast.LENGTH_SHORT).show();
                    }, 2000);
                }
            });

            // 首次加载数据
            List<HomeItem> mockItems = new ArrayList<>();
            for (int i = 0; i < 30; i++) {
                String description = "Description for item " + i + ". ";
                for (int j = 0; j < (i % 5) + 1; j++) {
                    description += "This is a longer text to make the item height vary. ";
                }
                mockItems.add(new HomeItem("Title " + i, description));
            }
            homeAdapter.updateData(mockItems);

            return view;
        }
    }
    ```

**详细文字讲解说明：**
*   **`SwipeRefreshLayout`：** 这是一个容器 View，可以包裹一个可滚动的子 View (如 `RecyclerView`)。
*   **`setOnRefreshListener()`：** 设置刷新监听器。当用户下拉触发刷新时，`onRefresh()` 回调会被调用。
*   **`setRefreshing(false)`：** 在数据加载完成后，**务必调用此方法**来停止刷新动画。

**面试话术：**
“首页的下拉刷新功能我通过 `SwipeRefreshLayout` 实现。我将 `RecyclerView` 包裹在 `SwipeRefreshLayout` 中，并为其设置 `OnRefreshListener`。当用户下拉触发刷新时，`onRefresh()` 回调会被调用，我会在其中执行数据加载逻辑（例如，调用 `HomeViewModel` 的刷新方法，模拟网络请求）。数据加载完成后，**务必调用 `swipeRefreshLayout.setRefreshing(false)`** 来停止刷新动画，避免一直显示加载状态。这提供了直观的用户体验，让用户知道数据正在更新。”

**5.3 首页有加载更多 (加载更多)**

**知识技术讲解：**
加载更多是当用户滚动到列表底部时，自动加载更多数据的功能。这通过监听 `RecyclerView` 的滚动事件来实现。

**具体运用示例 (Java 代码):**

1.  **`feature-home/src/main/java/com.example.mypracticeapp.feature.home/view/HomeFragment.java` (添加滚动监听器)**
    ```java
    package com.example.mypracticeapp.feature.home.view;

    // ... (其他导入)
    import androidx.recyclerview.widget.RecyclerView; // 导入 RecyclerView
    import androidx.recyclerview.widget.StaggeredGridLayoutManager; // 导入 StaggeredGridLayoutManager (如果瀑布流)

    public class HomeFragment extends Fragment {

        // ... (其他成员变量)
        private boolean isLoadingMore = false; // 标记是否正在加载更多，避免重复加载
        private int currentPage = 1; // 当前页码

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            // ... (View 绑定和 RecyclerView 初始化)

            // --- 加载更多逻辑 ---
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    // 只有当向下滚动 (dy > 0) 并且没有正在加载更多时才触发
                    if (dy > 0 && !isLoadingMore) {
                        // 获取布局管理器
                        StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
                        if (layoutManager == null) return;

                        // 获取屏幕上最后一个完全可见的item的位置
                        int[] lastVisibleItemPositions = layoutManager.findLastCompletelyVisibleItemPositions(null);
                        int lastVisibleItem = getLastVisibleItem(lastVisibleItemPositions);

                        // 获取总的item数量
                        int totalItemCount = layoutManager.getItemCount();

                        // 判断是否滚动到接近底部 (例如，距离底部还有 5 个 item)
                        if (totalItemCount - lastVisibleItem <= 5) { // 阈值可以调整
                            isLoadingMore = true;
                            currentPage++;
                            Toast.makeText(getContext(), "正在加载更多，页码: " + currentPage, Toast.LENGTH_SHORT).show();
                            loadMoreData(currentPage); // 调用加载更多数据的方法
                        }
                    }
                }
            });

            // ... (首次加载数据)

            return view;
        }

        /**
         * 获取 StaggeredGridLayoutManager 最后一个可见 item 的位置
         */
        private int getLastVisibleItem(int[] lastVisibleItemPositions) {
            int max = lastVisibleItemPositions[0];
            for (int i = 1; i < lastVisibleItemPositions.length; i++) {
                if (i > max) {
                    max = lastVisibleItemPositions[i];
                }
            }
            return max;
        }

        /**
         * 模拟加载更多数据
         * @param page 页码
         */
        private void loadMoreData(int page) {
            // 模拟网络请求延迟
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                List<HomeItem> moreItems = new ArrayList<>();
                for (int i = 0; i < 10; i++) { // 每次加载 10 条新数据
                    String description = "Loaded More item " + (page * 10 + i) + ". ";
                    for (int j = 0; j < (i % 5) + 1; j++) {
                        description += "This is more content for loading. ";
                    }
                    moreItems.add(new HomeItem("更多标题 " + (page * 10 + i), description));
                }
                homeAdapter.addMoreData(moreItems); // 添加到 Adapter
                isLoadingMore = false; // 标记加载完成
                Toast.makeText(getContext(), "加载更多完成，新增 " + moreItems.size() + " 条", Toast.LENGTH_SHORT).show();
            }, 1500);
        }
    }
    ```

**详细文字讲解说明：**
*   **`addOnScrollListener()`：** 为 `RecyclerView` 添加滚动监听器。
*   **`onScrolled()`：** 在滚动时频繁回调。
*   **`dy > 0`：** 判断是否是向下滚动。
*   **`isLoadingMore` 标志：** 避免在数据加载过程中重复触发“加载更多”。
*   **`findLastCompletelyVisibleItemPositions()`：** `StaggeredGridLayoutManager` 的方法，用于获取屏幕上最后一个完全可见的 item 的位置（因为它有多列，会返回一个数组）。
*   **`totalItemCount - lastVisibleItem <= THRESHOLD`：** 这是判断是否滚动到列表底部的核心逻辑。当总 item 数减去最后一个可见 item 的位置小于等于某个阈值（例如 5）时，就认为接近底部了。
*   **`loadMoreData()`：** 模拟加载更多数据的方法，更新 `currentPage`，并调用 `homeAdapter.addMoreData()` 添加数据。
*   **`notifyItemRangeInserted()`：** 在 `Adapter` 中使用 `notifyItemRangeInserted()` 来更高效地更新 `RecyclerView`，而不是 `notifyDataSetChanged()`。

**面试话术：**
“首页的加载更多功能，我通过为 `RecyclerView` 添加 `addOnScrollListener` 来实现。在 `onScrolled()` 回调中，我会判断用户是否正在向下滚动 (`dy > 0`)，并且当前没有正在进行的其他加载操作。然后，我会获取 `RecyclerView` 的布局管理器（例如 `StaggeredGridLayoutManager`），并利用其 `findLastCompletelyVisibleItemPositions()` 方法获取最后一个完全可见的 item 的位置。当这个位置距离总 item 数量的底部达到某个阈值时（例如，距离底部还有 5 个 item），就认为用户已经滚动到接近底部，此时我会将 `isLoadingMore` 标志设置为 `true`，并触发数据加载逻辑（例如，请求下一页数据）。数据加载完成后，我会将新数据添加到 `Adapter` 中，并使用 `notifyItemRangeInserted()` 来高效更新 `RecyclerView`，同时将 `isLoadingMore` 标志重置为 `false`，允许下一次加载。”

**5.4 页面跳转有动画 (页面跳转有动画)**

**知识技术讲解：**
页面跳转动画可以提升用户体验。Android 提供了多种实现方式：
*   **Activity 默认转场动画：** 使用 `overridePendingTransition()`。
*   **Activity Transition API (API 21+)：** 共享元素动画、内容转场动画。
*   **Fragment Transition API：** 类似 Activity Transition。

**具体运用示例 (Java 代码):**

**方法一：使用 `overridePendingTransition()` (简单快捷，兼容性好)**

在 `startActivity(intent)` 后立即调用。

```java
// 在任何需要跳转的 Activity 中
// 例如，从 SplashActivity 跳转到 WelcomeActivity
Intent intent = new Intent(SplashActivity.this, WelcomeActivity.class);
startActivity(intent);
// 参数1: 进入动画 (新 Activity 进入时的动画)
// 参数2: 退出动画 (当前 Activity 退出时的动画)
overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left); // 从右侧滑入，当前页左侧滑出
finish();
```

1.  **`res/anim/slide_in_right.xml` (进入动画)**
    ```xml
    <?xml version="1.0" encoding="utf-8"?>
    <translate xmlns:android="http://schemas.android.com/apk/res/android"
        android:fromXDelta="100%"
        android:toXDelta="0%"
        android:duration="300"
        android:interpolator="@android:anim/decelerate_interpolator"/>
    ```
2.  **`res/anim/slide_out_left.xml` (退出动画)**
    ```xml
    <?xml version="1.0" encoding="utf-8"?>
    <translate xmlns:android="http://schemas.android.com/apk/res/android"
        android:fromXDelta="0%"
        android:toXDelta="-100%"
        android:duration="300"
        android:interpolator="@android:anim/decelerate_interpolator"/>
    ```

**方法二：使用 Activity Transition API (API 21+) (更流畅，支持共享元素)**

这需要对主题和 `AndroidManifest.xml` 进行配置。

1.  **`res/values/themes.xml` (启用窗口内容转场动画)**
    ```xml
    <style name="Theme.MyPracticeApp" parent="Theme.MaterialComponents.DayNight.DarkActionBar">
        <!-- Enable Window Content Transitions -->
        <item name="android:windowActivityTransitions">true</item>
        <!-- Optional: Specify enter and exit transitions -->
        <item name="android:windowEnterTransition">@transition/slide_right</item>
        <item name="android:windowExitTransition">@transition/slide_left</item>
    </style>
    ```
2.  **`res/transition/slide_right.xml`**
    ```xml
    <?xml version="1.0" encoding="utf-8"?>
    <transitionSet xmlns:android="http://schemas.android.com/apk/res/android">
        <slide android:slideEdge="right"
            android:duration="300"/>
    </transitionSet>
    ```
3.  **`res/transition/slide_left.xml`**
    ```xml
    <?xml version="1.0" encoding="utf-8"?>
    <transitionSet xmlns:android="http://schemas.android.com/apk/res/android">
        <slide android:slideEdge="left"
            android:duration="300"/>
    </transitionSet>
    ```
4.  **Java 代码 (启动 Activity)**
    ```java
    // 在启动 Activity 时
    Intent intent = new Intent(CurrentActivity.this, TargetActivity.class);
    // 创建转场选项
    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(CurrentActivity.this);
    startActivity(intent, options.toBundle()); // 传递 Bundle
    ```
    *   **共享元素转场动画：**
        ```java
        // 在布局中为共享元素设置 transitionName
        // <ImageView android:transitionName="robot_image" .../>

        // 在启动 Activity 时
        Intent intent = new Intent(CurrentActivity.this, TargetActivity.class);
        // 参数1: 当前 Activity
        // 参数2: 共享元素 View
        // 参数3: 目标 Activity 中对应的 transitionName
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                CurrentActivity.this,
                sharedImageView, // 当前 Activity 的 ImageView
                "robot_image" // 目标 Activity 中 ImageView 的 transitionName
        );
        startActivity(intent, options.toBundle());
        ```
        在 `TargetActivity` 的 `onCreate()` 中，确保 `ImageView` 也设置了相同的 `android:transitionName="robot_image"`。

**详细文字讲解说明：**
*   **`overridePendingTransition()`：** 这是最简单的方法，兼容性好。它在 `startActivity()` 或 `finish()` 后立即调用，参数是进入动画和退出动画的资源 ID。
*   **Activity Transition API (API 21+)：** 更现代、更强大的 API，支持共享元素动画（一个 View 在两个 Activity 之间平滑过渡）和内容转场动画（Activity 内容的进入/退出动画）。
    *   需要先在主题中启用 `android:windowActivityTransitions`。
    *   通过 `ActivityOptions.makeSceneTransitionAnimation()` 创建转场选项。
    *   **共享元素动画**需要两个 Activity 中的 View 拥有相同的 `android:transitionName`。
*   **`res/anim/` vs. `res/transition/`：** `res/anim/` 用于补间动画或属性动画资源，而 `res/transition/` 用于 Activity Transition API 中的场景过渡动画资源。

**面试话术：**
“页面跳转动画可以显著提升用户体验。我常用的方式有两种：
1.  **`overridePendingTransition()`：** 这是最简单且兼容性最好的方法。在 `startActivity()` 或 `finish()` 之后立即调用 `overridePendingTransition(enterAnim, exitAnim)`，传入自定义的进入和退出动画资源 ID（例如 XML 定义的平移动画）。
2.  **Activity Transition API (API 21+)：** 这是更现代、更强大的方式。我会在主题中启用 `android:windowActivityTransitions`，并可以指定默认的进入/退出动画。更高级的是**共享元素转场动画**，我可以通过 `ActivityOptions.makeSceneTransitionAnimation()`，将两个 Activity 中具有相同 `android:transitionName` 的 View 进行平滑过渡。这种方式能够提供非常流畅且富有表现力的用户体验。”

**5.5 性能优秀 (加分)**

**知识技术讲解：**
这部分已经在 Part 4 的性能优化中详细讲解了。它是一个综合性的结果，体现了您在 UI 优化、启动优化、内存优化、CPU 优化、网络优化等方面的综合能力。

**面试话术：**
“性能优秀是 App 质量的最终体现。我在项目开发过程中，始终贯穿性能优化的理念，这体现在：
*   **UI 层面：** 扁平化 View 层次，使用 `ConstraintLayout`，减少过度绘制，优化自定义 View 的 `onDraw()`。
*   **启动层面：** 延迟非必要初始化，异步加载，利用 Android 12+ Splash Screen API。
*   **内存层面：** 严格避免内存泄漏（如 `Handler` 泄漏、`Context` 引用），使用图片加载库并按需加载图片，减少内存抖动。
*   **CPU 层面：** 避免主线程耗时操作，优化算法，合理利用多线程。
*   **网络层面：** 实现请求缓存、数据压缩、防抖机制，并考虑弱网优化。
*   **架构层面：** 采用 MVVM 架构，清晰职责分离，提高代码可测试性和可维护性，间接提升性能。
这些综合措施确保了 App 运行流畅，响应迅速，为用户提供卓越体验。”

**5.6 有封装概念的加分 (加分)**

**知识技术讲解：**
封装是面向对象编程的三大特性之一。在 Android 开发中，封装体现在：
*   **模块化：** 将不同功能拆分到独立的模块中（如 `feature-login`, `common`）。
*   **架构模式：** MVP/MVVM 模式本身就是一种封装，将 Model、View、Presenter/ViewModel 的职责封装起来。
*   **工具类：** 将常用功能封装成静态工具类（如 `SharedPreferencesUtil`）。
*   **自定义 View/ViewGroup：** 将复杂 UI 和逻辑封装成可复用的组件。
*   **数据模型：** 将数据结构封装成清晰的 Java Bean 或 Kotlin Data Class。
*   **接口/抽象类：** 定义清晰的接口和抽象类，实现多态和依赖倒置。
*   **Repository 模式：** 封装数据源的细节，向上提供统一的数据访问接口。

**具体运用示例 (已在上述代码中体现):**
*   `common` 模块中的 `SharedPreferencesUtil`。
*   `LoginRepository` 封装了登录注册的数据请求细节，向上提供 `login()`、`register()` 方法和回调接口。
*   `HomeAdapter` 封装了 `RecyclerView` 列表项的创建和数据绑定逻辑。
*   `FlowLayout` 封装了流式布局的测量和布局逻辑。

**面试话术：**
“封装概念贯穿于我整个 App 的设计和开发过程中。
1.  **模块化：** 我将 App 拆分为多个独立的模块，如 `feature-login`、`feature-home` 和 `common`，每个模块职责单一，对外暴露清晰的接口，降低了模块间的耦合度。
2.  **架构模式：** 采用 MVVM 模式本身就是一种高级封装。Model 封装数据源，ViewModel 封装业务逻辑和数据，View 封装 UI 展示，它们之间通过 `LiveData` 和接口进行通信，隐藏了内部实现细节。
3.  **工具类和辅助类：** 我会创建 `SharedPreferencesUtil` 这样的公共工具类，将共享偏好设置的读写操作封装起来。
4.  **Repository 模式：** 例如 `LoginRepository` 和 `SearchRepository`，它们封装了数据获取的细节（无论是网络请求还是本地缓存），向上层（ViewModel）提供统一、简洁的数据访问接口。
5.  **自定义 View/ViewGroup：** 将复杂的 UI 结构和交互逻辑封装成独立的自定义组件，如 `FlowLayout` 或 `UnderlinedTextView`，对外暴露可配置的属性和方法，提高复用性。
通过这些方式，我确保了代码的高内聚、低耦合，提高了项目的可维护性、可测试性和团队协作效率。”

---

**总结**

这个实践课程非常全面，涵盖了 Android App 开发的方方面面。按照上述指南，您将能够：
*   完成一个功能完整的 App。
*   掌握 Android 项目的工程化流程。
*   理解并实践 MVVM 架构。
*   实现常见的 UI 组件和交互逻辑。
*   深入理解并实践性能优化。
*   掌握高级 UI 技术（瀑布流、下拉刷新、加载更多、页面动画）。
*   培养良好的代码封装习惯。

希望这份详细的指南能够帮助您成功完成实践课程！祝您开发顺利，面试成功！







# 1








