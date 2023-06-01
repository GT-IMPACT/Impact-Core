@file:Suppress("UnstableApiUsage")

import groovy.lang.Closure
import groovy.util.Node
import groovy.util.NodeList
import net.minecraftforge.gradle.user.patch.UserPatchExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.pm20.util.archivesName

buildscript {
    dependencies {
        classpath(libs.forge.gradle) {
            isChanging = true
        }
    }
}

repositories {
    mavenCentral()
    maven("https://maven.accident.space/repository/maven-public/")
    maven("https://maven.minecraftforge.net")
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
    maven("https://jitpack.io")
    maven("https://plugins.gradle.org/m2/")
    mavenLocal()
}

apply(plugin = "forge")

plugins {
    alias(libs.plugins.buildconfig)
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.gitversion)
    `maven-publish`
    `java-library`
}

val Project.minecraft: UserPatchExtension
    get() = project.extensions.getByType<UserPatchExtension>()

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

val modId: String = extra["modId"].toString()
val modName: String = extra["modName"].toString()
val modGroup: String = extra["modGroup"].toString()
val minecraftVersion: String = libs.versions.minecraftVersion.get()

minecraft.version = libs.versions.minecraft.forge.get()
minecraft.runDir = "impact"

group = modGroup

var versionOverride: String? = System.getenv("VERSION") ?: null
var identifiedVersion = runCatching {
    val gitVersion: Closure<String> by extra
    versionOverride ?: gitVersion()
}.getOrElse {
    "unknown".also {
        versionOverride = it
    }
}

version = identifiedVersion
val modVersion = identifiedVersion

archivesName.set("$modId-$minecraftVersion-$modVersion")

buildConfig {
    packageName("space.impact")
    buildConfigField("String", "MODID", "\"${extra["modId"].toString()}\"")
    buildConfigField("String", "MODNAME", "\"${extra["modName"].toString()}\"")
    buildConfigField("String", "VERSION", "\"$modVersion\"")
    buildConfigField("String", "GROUPNAME", "\"${extra["modGroup"].toString()}\"")
}

tasks.named<ProcessResources>("processResources") {
    inputs.property("version", project.version)
    inputs.property("mcversion", libs.versions.minecraftVersion.get())
    filesMatching("mcmod.info") {
        expand(
            "minecraftVersion" to libs.versions.minecraftVersion.get(),
            "modVersion" to modName,
            "modName" to modName,
        )
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "run/", "include" to listOf("*.jar"))))
    compileOnly(fileTree(mapOf("dir" to "notRun/", "include" to listOf("*.jar"))))
    annotationProcessor("io.gitlab.hohserg.elegant.networking:annotation-processor:3.14")
    annotationProcessor("org.projectlombok:lombok:1.16.4")
}

tasks.withType<GenerateModuleMetadata> {
    enabled = false
    mustRunAfter("reobf")
}

publishing {
    publications {
        register("mavenJava", MavenPublication::class) {
            from(components["java"])
            pom.withXml {
                val pomNode = asNode()

                val dependencyNodes: NodeList = pomNode.get("dependencies") as NodeList
                dependencyNodes.forEach {
                    (it as Node).parent().remove(it)
                }
            }
            val devJar by tasks.registering(Jar::class) {
                from(sourceSets["main"].output)
                archiveClassifier.set("dev")
            }
            artifact(devJar.get())
            groupId = modGroup
            artifactId = modId
            version = identifiedVersion
        }
    }
    repositories {
        maven {
            url = uri("https://maven.accident.space/repository/maven-releases/")
            credentials {
                username = System.getenv("MAVEN_USER") ?: "NONE"
                password = System.getenv("MAVEN_PASSWORD") ?: "NONE"
            }
        }
    }
}
