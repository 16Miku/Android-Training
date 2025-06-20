plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.day5_search"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.day5_search"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    // 添加 OkHttp 库依赖
    implementation("com.squareup.okhttp3:okhttp:4.11.0")    // 使用最新稳定版本

    // Gson 库，用于JSON解析
    implementation ("com.google.code.gson:gson:2.10.1") // 使用最新稳定版本

    /*当您在 MainActivity.java 中使用 Response 类时，如果您的 IDE (Android Studio) 或编译器在解析 Response 时，错误地选择了 Cronet 库中的 Response 类（或者一个与 OkHttp Response 签名不完全一致的类），而那个类没有 errorBody() 方法，就会导致这个编译错误。
    尽管 OkHttp 和 Cronet 都是网络库，但它们是不同的实现，它们的 API 设计也不同。同时引入并混用它们的类很容易导致这种类型解析错误。
    解决方案
    最直接和推荐的解决方案是：如果您打算使用 OkHttp 进行网络请求，那么请移除 cronet.embedded 依赖。
    1.修改 build.gradle.kts：
    将 implementation(libs.cronet.embedded) 这行从 dependencies 块中删除。
    2.同步 Gradle 项目：
    点击 Android Studio 工具栏上的“Sync Project with Gradle Files”按钮（通常是一个大象图标）。
    3.清理和重建项目：
    在 Android Studio 菜单栏中，选择 Build -> Clean Project。
    清理完成后，再次选择 Build -> Rebuild Project。
    4.检查 MainActivity.java 中的导入：
    确保 MainActivity.java 文件中 Response 类的导入是正确的：
    */


    // Room Persistence Library
    implementation("androidx.room:room-runtime:2.6.1") // Room 运行时库
    // Room 注解处理器 (对于Java项目使用annotationProcessor，对于Kotlin项目使用kapt)
    annotationProcessor("androidx.room:room-compiler:2.6.1") // Java


    // Glide 图片加载库
    implementation("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.16.0")

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
//    implementation(libs.cronet.embedded)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}