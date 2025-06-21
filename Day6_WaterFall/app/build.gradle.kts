plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.day6_waterfall"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.day6_waterfall"
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

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)



    // 1. SmartRefreshLayout (下拉刷新和上滑加载) - 25分库
    implementation("io.github.scwang90:refresh-layout-kernel:2.1.0")      // 核心
    implementation("io.github.scwang90:refresh-header-classics:2.1.0")    // 经典刷新头
    implementation("io.github.scwang90:refresh-footer-classics:2.1.0")    // 经典加载尾

    // 2. BaseRecyclerViewAdapterHelper (瀑布流适配器) - 40分库
    implementation("io.github.cymchad:BaseRecyclerViewAdapterHelper4:4.1.2")

    // 3. Glide (图片加载) - 20分库
    implementation("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.16.0")

    // 4. Glide Transformations (图片特效) - 额外加5分
    implementation("jp.wasabeef:glide-transformations:4.3.0")


}