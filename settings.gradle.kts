pluginManagement {
    includeBuild("remote-build-logic")
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven("https://nexus.gtnewhorizons.com/repository/public/") {
            content { includeGroupByRegex("com\\.gtnewhorizons(\\..*)?") }
        }
        maven("https://maven.minecraftforge.net")
    }
}

plugins {
    id("settings-setup")
}

rootProject.name = "Impact-Core"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("remote-build-logic/libs.versions.toml"))
        }
    }
}

// Develop Core and GT together without a manually supplied, potentially stale jar.
val gregtechProject = file(providers.gradleProperty("gregtechProjectDir").getOrElse("../gt-worktree"))
if (!providers.gradleProperty("gregtechDevJar").isPresent && gregtechProject.resolve("settings.gradle.kts").isFile) {
    includeBuild(gregtechProject) {
        name = "gregtech"
        dependencySubstitution {
            substitute(module("space.impact:gregtech-impact"))
                .using(project(":"))
        }
    }
}

// Only the root build adds TecTech: TecTech itself also compiles against Core.
val tectechProject = file(providers.gradleProperty("tectechProjectDir").getOrElse("../tectech-worktree"))
if (gradle.parent == null && tectechProject.resolve("settings.gradle.kts").isFile) {
    // TecTech must resolve its Core dependency to this root project as well.
    includeBuild(".")
    includeBuild(tectechProject) {
        name = "tectech"
        dependencySubstitution {
            substitute(module("space.impact:TecTech"))
                .using(project(":"))
        }
    }
}
