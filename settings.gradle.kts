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
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "Portfolio"
include(":app")
include(":profile:presentation")
include(":profile:data")
include(":profile:domain")
include(":projects:data")
include(":projects:domain")
include(":projects:presentation")
include(":core")
include(":settings:domain")
include(":settings:data")
include(":settings:presentation")
include(":main:presentation")
include(":core_ui")
