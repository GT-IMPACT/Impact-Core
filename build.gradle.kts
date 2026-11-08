plugins {
    id("minecraft-setup")
    id("publish-setup")
}

impactMod {
    modId.set("impact")
    modName.set("IMPACT CORE")
    modGroup.set("com.impact")
    mavenGroup.set("space.impact")
    mavenUrl.set("https://maven.accident.space/repository/maven-releases/")
    usesMixins.set(true)
}

val modId = impactMod.modId.get()
val modName = impactMod.modName.get()
val modGroup = impactMod.modGroup.get()

buildConfig {
    packageName("space.impact.$modId")
    buildConfigField("String", "MODID", "\"${modId}\"")
    buildConfigField("String", "MODNAME", "\"${modName}\"")
    buildConfigField("String", "VERSION", "\"${project.version}\"")
    buildConfigField("String", "GROUPNAME", "\"${modGroup}\"")
    buildConfigField("boolean", "IS_DEBUG", "${System.getenv("IMPACT_DEBUG") != null}")
    useKotlinOutput {
        topLevelConstants = true
        internalVisibility = false
    }
}

dependencies {
    // impact
    implementation("space.impact:Packet-network:1.1.8.dirty")
    implementation("space.impact:ImpactStructures:1.1.3:dev")
    implementation("space.impact:VirtualWorld:2.0.4:dev")
    implementation("space.impact:gregtech-impact:5.09.37.6:dev") {
        isTransitive = false
    }

    compileOnly("space.impact:ImpactBlueprints:0.0.4:dev") { isTransitive = false }
    runtimeOnlyNonPublishable("space.impact:ImpactBlueprints:0.0.4:dev") { isTransitive = false }
    testImplementation("space.impact:ImpactBlueprints:0.0.4:dev") { isTransitive = false }

    compileOnly("space.impact:OpenComputers:1.7.5.7-impact")
    runtimeOnlyNonPublishable("space.impact:GregTechBridge:1.0.2:dev") { isTransitive = false }

    // maven impact
    api("com.github.GTNewHorizons:modularui:1.1.24:dev") { isTransitive = false }
    api("com.github.GTNewHorizons:ModularUI2:2.3.92-1.7.10:dev") {
        exclude(group = "com.github.GTNewHorizons", module = "ModularUI")
    }
    implementation("com.github.GTNewHorizons:Baubles-Expanded:2.2.24-GTNH:dev")
    implementation("com.github.GTNewHorizons:waila:1.7.3:dev") { isTransitive = false }
    implementation("com.github.GTNewHorizons:EnderCore:0.2.7:dev") { isTransitive = false }
    runtimeOnlyNonPublishable("com.github.GTNewHorizons:IC2NuclearControl:2.6.2:dev") { isTransitive = false }

    // other
    api("net.industrial-craft:industrialcraft-2:2.2.828-experimental:dev")
    implementation("com.github.GTNewHorizons:Applied-Energistics-2-Unofficial:rv3-beta-1082-GTNH:dev")
    implementation("com.github.GTNewHorizons:NotEnoughEnergistics:1.7.47:dev") { isTransitive = false }
    implementation("com.github.GTNewHorizons:AE2FluidCraft-Rework:1.5.113-gtnh:dev")

    runtimeOnlyNonPublishable(modUtils.deobfuscate("curse.maven:journeymap-32274:4500659"))

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.8.1")
    // Интерфейс стеков AE2 ссылается на клиент Minecraft, использующий LWJGL.
    testRuntimeOnly("org.lwjgl.lwjgl:lwjgl:2.9.4-nightly-20150209")
    // Match GT's native MUI2 test runtime; the game supplies these through its loader mods.
    testRuntimeOnly("it.unimi.dsi:fastutil:8.5.18")
    testRuntimeOnly("org.joml:joml:1.10.8")
    testRuntimeOnly("com.github.GTNewHorizons:lwjgl3ify:3.0.34:api") { isTransitive = false }
    testRuntimeOnly("curse.maven:neverenoughanimation-1062347:7664003-sources-7663978") {
        isTransitive = false
    }
}

tasks.test {
    // Forge GUI tests retain isolated Minecraft class loaders until their worker exits.
    maxHeapSize = "1g"
    maxParallelForks = 1
    forkEvery = 1
    jvmArgs("-XX:+ExitOnOutOfMemoryError")
    useJUnitPlatform()
    testLogging {
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
    }
    // Forge 1.7.10 tests require the Java 8 URLClassLoader.
    javaLauncher.set(javaToolchains.launcherFor {
        languageVersion.set(JavaLanguageVersion.of(8))
    })
}

// Do not upload a release before its verification tasks have passed.
tasks.withType<org.gradle.api.publish.maven.tasks.AbstractPublishToMaven>().configureEach {
    dependsOn(tasks.check)
}
