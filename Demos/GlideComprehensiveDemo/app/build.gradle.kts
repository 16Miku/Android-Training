plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.glidecomprehensive"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.glidecomprehensive"
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

    // 配置资源目录，确保 Android Studio 能够识别资源
    sourceSets {
        getByName("main") {
            res.srcDirs("src/main/res")
        }
    }

}

dependencies {

    // AndroidX UI 基础库
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    // 测试依赖
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)



    // =====================================
    // Glide 图片加载库依赖
    // =====================================
    implementation("com.github.bumptech.glide:glide:4.16.0")
    // Glide 注解处理器，用于生成 GlideApp 类，提供更好的API体验和自定义模块支持
    annotationProcessor("com.github.bumptech.glide:compiler:4.16.0")



    // RecyclerView 相关依赖
    // =====================================
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    // RecyclerViewPreloader 是 Glide 官方提供的集成库，用于优化 RecyclerView 滚动性能
    implementation("com.github.bumptech.glide:recyclerview-integration:4.16.0")

    // =====================================
    // CardView 依赖 (可选，用于 RecyclerView item 美化)
    // =====================================
    implementation("androidx.cardview:cardview:1.0.0")

    // =====================================
    // 运行时权限库 (可选，如果你使用第三方库，AndroidX 提供了更好的方案)
    // =====================================
    // implementation("com.karumi:dexter:6.2.3") // 这是一个流行的权限请求库，但我们将使用原生方式











}