@file:Suppress("UnstableApiUsage")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    includeBuild("remote-build-logic")
    repositories {
        maven("https://maven.accident.space/repository/maven-public/") {
            mavenContent {
                includeGroup("space.impact")
                includeGroupByRegex("space\\.impact\\..+")
            }
        }
        maven("https://nexus.gtnewhorizons.com/repository/public/") {
            mavenContent {
                includeGroup("com.gtnewhorizons")
                includeGroupByRegex("com\\.gtnewhorizons\\..+")
            }
        }
        maven("https://maven.minecraftforge.net")
        maven("https://plugins.gradle.org/m2/")
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        maven("https://maven.accident.space/repository/maven-public/") {
            mavenContent {
                includeGroup("space.impact")
                includeGroupByRegex("space\\.impact\\..+")
            }
        }
        maven("https://nexus.gtnewhorizons.com/repository/public/") {
            mavenContent {
                includeGroup("com.gtnewhorizons")
                includeGroupByRegex("com\\.gtnewhorizons\\..+")
            }
        }
        maven("https://maven.minecraftforge.net")
        maven("https://plugins.gradle.org/m2/")
        mavenCentral()
    }

    versionCatalogs {
        create("libs") {
            from(files("remote-build-logic/libs.versions.toml"))
        }
    }
}

plugins {
    id("com.gtnewhorizons.gtnhsettingsconvention") version "1.0.32"
}
