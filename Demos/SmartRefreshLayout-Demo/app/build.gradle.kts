plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.smartrefreshlayout_demo"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.smartrefreshlayout_demo"
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

    buildFeatures {
        viewBinding = true // 开启 View Binding，方便访问布局视图
    }


}

dependencies {

    // AndroidX 核心库
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.recyclerview:recyclerview:1.3.0") // 示例：如果使用 RecyclerView



    implementation  ("io.github.scwang90:refresh-layout-kernel:3.0.0-alpha")      //核心必须依赖
    implementation  ("io.github.scwang90:refresh-header-classics:3.0.0-alpha")   //经典刷新头
    implementation  ("io.github.scwang90:refresh-header-radar:3.0.0-alpha")      //雷达刷新头
    implementation  ("io.github.scwang90:refresh-header-falsify:3.0.0-alpha")    //虚拟刷新头
    implementation  ("io.github.scwang90:refresh-header-material:3.0.0-alpha")    //谷歌刷新头
    implementation  ("io.github.scwang90:refresh-header-two-level:3.0.0-alpha")   //二级刷新头
    implementation  ("io.github.scwang90:refresh-footer-ball:3.0.0-alpha")        //球脉冲加载
    implementation  ("io.github.scwang90:refresh-footer-classics:3.0.0-alpha")    //经典加载

    // 测试依赖
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")





}