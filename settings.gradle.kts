enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    includeBuild("build-logic")
    repositories {
        maven("https://maven.accident.space/repository/maven-public/")
        maven("https://maven.minecraftforge.net")
        maven("https://plugins.gradle.org/m2/")
        gradlePluginPortal()
        mavenCentral()
        mavenLocal()
    }
}