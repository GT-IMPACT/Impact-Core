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

repositories {
    mavenLocal {
        content {
            includeVersion(
                "space.impact",
                "gregtech-impact",
                "5.09.36.1",
            )
        }
    }
}

dependencies {
    // impact
    implementation("space.impact:Packet-network:1.1.8.dirty")
    implementation("space.impact:ImpactStructures:1.1.0:dev")
    implementation("space.impact:VirtualWorld:2.0.4:dev")
    implementation("space.impact:gregtech-impact:5.09.36.1:dev") {
        isTransitive = false
    }
    compileOnly("space.impact:OpenComputers:1.7.5.7-impact")
    runtimeOnlyNonPublishable("space.impact:RecipeModule:1.1.0.5") { isTransitive = false }
    runtimeOnlyNonPublishable("space.impact:GregTechBridge:1.0.2:dev") { isTransitive = false }

    // maven impact
    api("com.github.GTNewHorizons:modularui:1.1.24:dev") { isTransitive = false }
    implementation(modUtils.deobfuscate("com.github.Azanor:Baubles:1.0.1.12"))
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
}

tasks.test {
    useJUnitPlatform()
    // Forge 1.7.10 tests require the Java 8 URLClassLoader.
    javaLauncher.set(javaToolchains.launcherFor {
        languageVersion.set(JavaLanguageVersion.of(8))
    })
}
