pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()

        // 添加 JitPack 仓库，用于一些托管在 GitHub 上的库
        maven { url = uri("https://jitpack.io") }

    }
}

rootProject.name = "GlideComprehensiveDemo"
include(":app")
