@file:Suppress("UnstableApiUsage")

import net.minecraftforge.gradle.common.BaseExtension
import org.apache.tools.ant.types.Commandline
import org.gradle.internal.impldep.org.apache.commons.io.FileUtils.getFile

plugins {
    id("forge")
    kotlin("jvm")
    kotlin("kapt")
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
val modGroup: String by extra

val usesMixins: String by extra
val useMixins = usesMixins.toBoolean()

val mixinPlugin: String by extra
val mixinsPackage: String by extra
val mixingConfigRefMap = "mixins.$modId.refmap.json"
val mixinTmpDir = buildDir.path + File.separator + "tmp" + File.separator + "mixins"
val refMap = mixinTmpDir + File.separator + mixingConfigRefMap

tasks.processResources.configure {
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
    if (useMixins) {
        from(refMap)
        dependsOn(tasks["compileJava"])
    }
}

tasks.register("generateAssets") {
    onlyIf { useMixins }
    doLast {
        val mixinConfigFile = getFile("/src/main/resources/mixins.$modId.json")
        if (!mixinConfigFile.exists()) {
            var mixinPluginLine = ""
            if (mixinPlugin.isNotEmpty()) {
                mixinPluginLine += """\n  "plugin": "${modGroup}.${mixinPlugin}", """
            }
            mixinConfigFile.writeText(
                """{
  "required": true,
  "minVersion": "0.8.5-GTNH",
  "package": "${modGroup}.${mixinsPackage}",${mixinPluginLine}
  "refmap": "$mixingConfigRefMap",
  "target": "@env(DEFAULT)",
  "compatibilityLevel": "JAVA_8",
  "mixins": [],
  "client": [],
  "server": []
}
"""
            )
        }
    }
}


fun getManifestAttributes(): Map<String, Any> {
    val manifestAttributes = mutableMapOf<String, Any>()
    if (useMixins) {
        manifestAttributes["FMLCorePluginContainsFMLMod"] = true
        manifestAttributes["TweakClass"] = "org.spongepowered.asm.launch.MixinTweaker"
        manifestAttributes["MixinConfigs"] = "mixins.$modId.json"
        manifestAttributes["ForceLoadAsMod"] = true
    }
    return manifestAttributes
}

tasks.register("devJar", Jar::class) {
    from(sourceSets["main"].output)
    archiveClassifier.set("dev")
    manifest { attributes(getManifestAttributes()) }
}

tasks.named<Jar>("jar").configure {
    manifest { attributes(getManifestAttributes()) }
    dependsOn(tasks["devJar"])
}

tasks.named("runClient") {
    doFirst {
        println("--tweakClass, org.spongepowered.asm.launch.MixinTweaker")
    }
}

val mixinProviderGroup = "io.github.legacymoddingmc"
val mixinProviderModule = "unimixins"
val mixinProviderVersion = "0.1.7.1"
val mixinProviderSpecNoClassifer = "${mixinProviderGroup}:${mixinProviderModule}:${mixinProviderVersion}"
val mixinProviderSpec = "${mixinProviderSpecNoClassifer}:dev"

dependencies {
    if (useMixins) {
        annotationProcessor("org.ow2.asm:asm-debug-all:5.0.3")
        annotationProcessor("com.google.guava:guava:24.1.1-jre")
        annotationProcessor("com.google.code.gson:gson:2.8.6")
        annotationProcessor(mixinProviderSpec)
    }
}

pluginManager.withPlugin("org.jetbrains.kotlin.kapt") {
    if (useMixins) {
        dependencies {
            kapt(mixinProviderSpec)
        }
    }
}
