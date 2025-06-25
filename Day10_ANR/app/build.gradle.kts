plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.anrproblemdemo"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.anrproblemdemo"
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



    // 添加 ANR-WatchDog 库
    implementation("com.github.anrwatchdog:anrwatchdog:1.4.0") // 检查Maven Central获取最新版本

    // LeakCanary 仅在 debug 构建时使用
    debugImplementation ("com.squareup.leakcanary:leakcanary-android:2.14")

    implementation("androidx.localbroadcastmanager:localbroadcastmanager:1.1.0") // 检查Maven Central获取最新版本
    // 提供了 LocalBroadcastManager 类，用于在应用程序的单个进程内发送和接收广播。它比全局广播更高效、更安全，因为它不涉及进程间通信。


}