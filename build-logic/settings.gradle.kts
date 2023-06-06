@file:Suppress("UnstableApiUsage")

buildscript {
    repositories {
        mavenCentral()
        maven("https://maven.accident.space/repository/maven-public/")
        maven("https://maven.minecraftforge.net")
        maven("https://oss.sonatype.org/content/repositories/snapshots/")
        maven("https://jitpack.io")
        maven("https://plugins.gradle.org/m2/")
        mavenLocal()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        maven("https://maven.accident.space/repository/maven-public/")
        maven("https://maven.minecraftforge.net")
        maven("https://oss.sonatype.org/content/repositories/snapshots/")
        maven("https://jitpack.io")
        maven("https://plugins.gradle.org/m2/")
        mavenLocal()
    }

    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

include(":plugins")
