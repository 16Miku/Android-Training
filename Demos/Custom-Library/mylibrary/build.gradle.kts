plugins {

    alias(libs.plugins.android.library) // 引入 Android Library 插件
    alias(libs.plugins.kotlin.android) // !!! 关键修复：使用 alias 引用 Kotlin Android 插件 !!!
    id("maven-publish") // 引入 maven-publish 插件

}

android {
    namespace = "com.example.mylibrary"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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



// 定义发布信息
group = "com.example.mylibrary" // 你的组织或公司ID
version = "1.0.0" // 你的库版本号


// 配置发布任务
// !!! 关键修复：将 publishing 块包裹在 project.afterEvaluate 中 !!!
// 确保在 Android Gradle Plugin 完成其配置并生成 components 后再执行发布配置
project.afterEvaluate {
    publishing {
        publications {
            // 创建一个名为 'release' 的发布配置
            create<MavenPublication>("release") { // 使用 create<Type>("name") 创建发布
                // 指定要发布的组件，这里是Android组件
                // components["release"] 在 afterEvaluate 块中可以被正确访问到
                from(components["release"])

                // 配置POM文件中的信息
                groupId = "com.example.mylibrary" // 再次指定groupId
                artifactId = "mylibrary" // 你的库的artifact ID
                version = "1.0.0" // 你的库的版本号

                // 可选：添加POM文件中的描述信息
                pom {
                    name.set("My Android Library") // 使用 set() 方法设置属性
                    description.set("A sample Android library for demonstration.")
                    url.set("http://www.example.com/mylibrary") // 你的项目主页

                    licenses {
                        license {
                            name.set("The Apache Software License, Version 2.0")
                            url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                        }
                    }
                    developers {
                        developer {
                            id.set("your_id")
                            name.set("Your Name")
                            email.set("your.email@example.com")
                        }
                    }
                    scm {
                        connection.set("scm:git:github.com/your_username/your_repo.git")
                        developerConnection.set("scm:git:ssh://github.com/your_username/your_repo.git")
                        url.set("https://github.com/your_username/your_repo")
                    }
                }
            }
        }


        repositories {
             mavenLocal() // 仍然可以保留默认的本地 Maven 仓库
            // !!! 新增部分：配置一个自定义的本地文件系统仓库 !!!
            maven {
                // 指定本地仓库的路径。这里使用 project.layout.buildDirectory.dir("repo")
                // 表示在当前模块的 build 目录下创建一个名为 "repo" 的文件夹作为本地仓库。
                // 也可以是 project.rootDir.resolve("local_repo") 表示项目根目录下的文件夹。
                // 或者直接是 uri("file:///path/to/your/custom/repo")
                url = uri(layout.buildDirectory.dir("repo").get().asFile.toURI())
                // 或者更简洁地：
                // url = uri("${project.buildDir}/repo")
                // 或者指定绝对路径：
                // url = uri("file:///D:/MyCustomLocalRepoForThisProject")
            }
        }


    }
}

dependencies {
    // 库内部的依赖，这些是 mylibrary 自身运行所需的依赖
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation("androidx.constraintlayout:constraintlayout:2.1.4") // 示例依赖
    api("com.squareup.okhttp3:okhttp:4.12.0") // 示例 api 依赖

    // 注意：以下两个依赖通常不会出现在库模块的 build.gradle.kts 中
    // implementation(project(":mylibrary")) // 库不能依赖自身，这会导致循环依赖
    // implementation("com.example.mylibrary:mylibrary:1.0.0") // 库不应依赖其自身的发布版本

    // 本地AAR/JAR文件依赖 (适用于你已经有编译好的.aar或.jar文件，且不希望通过Maven仓库管理)
    // 这种方式通常用于集成第三方SDK，或者在没有Maven仓库的情况下临时使用。
    // 将mylibrary.aar文件放到app模块的libs目录下
    implementation(files("libs/sentinel-dashboard.jar")) // 示例本地 JAR 依赖

    // compileOnly: 依赖只在编译时可用，不会打包到最终的APK或AAR中。
    // 适用于只在编译时需要，运行时由宿主环境提供的依赖（如注解处理器）。
    compileOnly("org.projectlombok:lombok:1.18.20")

    // runtimeOnly: 依赖只在运行时可用，不会在编译时可见。
    // 适用于插件或驱动，编译时不需要，运行时才需要。
    runtimeOnly("com.example:my-plugin:1.0.0")

    // debugImplementation: 仅在debug构建类型下生效的依赖。
    // 适用于调试工具、测试库等。
    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.14")

    // releaseImplementation: 仅在release构建类型下生效的依赖。
    // 适用于发布版本特有的依赖。
    releaseImplementation("com.google.firebase:firebase-crashlytics:18.6.2")

    // testImplementation: 仅在单元测试时生效的依赖。
    testImplementation(libs.junit)
    testImplementation("junit:junit:4.13.2") // 确保 JUnit 版本一致或根据 libs.versions.toml 配置

    // androidTestImplementation: 仅在Android Instrumented Tests (UI测试) 时生效的依赖。
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation("androidx.test.ext:junit:1.1.5") // 确保版本一致
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1") // 确保版本一致


    // 依赖冲突解决:
    // 当不同的依赖引入了相同库的不同版本时，可能发生依赖冲突。
    // Gradle 默认会选择版本号最高的那个。如果需要手动解决，可以使用以下方式：

    // 排除某个传递性依赖
    // 假设 mylibrary 内部依赖了某个库，而这个库又传递性地引入了 guava，
    // 且你希望排除这个传递性 guava 依赖。
    // 注意：这里的 "com.example.mylibrary:mylibrary:1.0.0" 仍然是错误的，
    // 因为库不应该依赖自身。这里应该是一个外部库的依赖。
    // 正确的示例应该是：
    // implementation("com.some.other:library:1.0.0") {
    //     exclude(group = "com.google.guava", module = "guava")
    // }
}