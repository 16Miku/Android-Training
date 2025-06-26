plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-parcelize") // <-- 添加这一行，启用 Parcelize 插件
}

android {
    namespace = "com.example.day11_kotlinrecyclerview"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.day11_kotlinrecyclerview"
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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    // --- 核心 AndroidX 依赖：解决大部分 Unresolved reference 错误 ---
    implementation("androidx.core:core-ktx:1.13.1") // 已有，提供核心 Kotlin 扩展
    implementation("androidx.appcompat:appcompat:1.6.1") // <-- 添加/确认此行，提供 AppCompatActivity
    implementation("androidx.constraintlayout:constraintlayout:2.1.4") // <-- 添加/确认此行，提供 ConstraintLayout
    implementation("androidx.recyclerview:recyclerview:1.3.2") // <-- 添加/确认此行，提供 RecyclerView, ListAdapter, LinearLayoutManager
    implementation("androidx.cardview:cardview:1.0.0") // 已有，提供 CardView


    // activity-ktx 提供了 ActivityResultLauncher
    implementation("androidx.activity:activity-ktx:1.9.0") // 检查最新版本


    // Google Material Design 组件 (通常与 AppCompat 配合使用)
    implementation("com.google.android.material:material:1.12.0")

    // Coil 图片加载库 (已在您的文件中)
    implementation("io.coil-kt:coil:2.6.0")

    // --- Jetpack Compose 相关依赖 (如果项目是纯 View-based，可以移除) ---
    // 根据您提供的文件，您的项目似乎是传统的 View-based，但包含了 Compose 依赖。
    // 如果您不使用 Compose，以下可以安全移除，以减少包体积和编译时间。
     implementation(libs.androidx.lifecycle.runtime.ktx)
     implementation(libs.androidx.activity.compose)
     implementation(platform(libs.androidx.compose.bom))
     implementation(libs.androidx.ui)
     implementation(libs.androidx.ui.graphics)
     implementation(libs.androidx.ui.tooling.preview)
     implementation(libs.androidx.material3)
     testImplementation(libs.junit) // 这个是 JUnit 4，可以保留
     androidTestImplementation(libs.androidx.junit) // JUnit 4 适配器，可以保留
     androidTestImplementation(libs.androidx.espresso.core) // Espresso 核心，可以保留
     androidTestImplementation(platform(libs.androidx.compose.bom))
     androidTestImplementation(libs.androidx.ui.test.junit4)
     debugImplementation(libs.androidx.ui.tooling)
     debugImplementation(libs.androidx.ui.test.manifest)




}