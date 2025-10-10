pluginManagement {
    repositories {
//        maven { url = uri("https://maven.aliyun.com/repository/google") }
//        maven { url = uri("https://maven.aliyun.com/repository/jcenter") }
//        maven { url = uri("https://maven.aliyun.com/repository/public") }
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
//        maven { url = uri("https://maven.aliyun.com/repository/google") }
//        maven { url = uri("https://maven.aliyun.com/repository/jcenter") }
//        maven { url = uri("https://maven.aliyun.com/repository/public") }
        google()
        mavenCentral()
    }
}

rootProject.name = "MyApplication"
include(":app")
include(":module_compose")
include(":module_compose2")
include(":module_net")
include(":module_ui")
