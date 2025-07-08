plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.brvah_demo"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.brvah_demo"
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

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)


    // BaseRecyclerViewAdapterHelper (BRVAH) 核心库
    // 请访问 BRVAH GitHub 仓库 (https://github.com/CymChad/BaseRecyclerViewAdapterHelper) 获取最新稳定版本和确认正确的依赖引入方式
    implementation ("io.github.cymchad:BaseRecyclerViewAdapterHelper4:4.1.4")

    implementation("androidx.recyclerview:recyclerview:1.3.0") // RecyclerView 依赖


}