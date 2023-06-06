@file:Suppress("UnstableApiUsage")

import net.minecraftforge.gradle.common.BaseExtension

plugins {
    id("forge")
    kotlin("jvm")
}

configure<BaseExtension> {
    commonMinecraft(project)
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

val modName: String by extra
val modId: String by extra

tasks.named<ProcessResources>("processResources") {
    inputs.property("version", project.version)
    inputs.property("mcversion", "1.7.10")
    filesMatching("mcmod.info") {
        expand(
            "minecraftVersion" to "1.7.10",
            "modId" to modId,
            "modVersion" to project.version,
            "modName" to modName,
        )
    }
}
