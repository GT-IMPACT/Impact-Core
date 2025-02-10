import settings.getVersionMod

plugins {
    alias(libs.plugins.setup.minecraft)
    alias(libs.plugins.setup.publish)
    id(libs.plugins.buildconfig.get().pluginId)
}

val modId: String by extra
val modName: String by extra
val modGroup: String by extra

extra.set("modVersion",getVersionMod())

buildConfig {
    packageName("space.impact.$modId")
    buildConfigField("String", "MODID", "\"${modId}\"")
    buildConfigField("String", "MODNAME", "\"${modName}\"")
    buildConfigField("String", "VERSION", "\"${getVersionMod()}\"")
    buildConfigField("String", "GROUPNAME", "\"${modGroup}\"")
    buildConfigField("boolean", "IS_DEBUG", "${System.getenv("IMPACT_DEBUG") != null}")
    useKotlinOutput { topLevelConstants = true }
}

repositories {
    maven("https://maven.accident.space/repository/maven-public/") {
        mavenContent {
            includeGroup("space.impact")
            includeGroupByRegex("space\\.impact\\..+")
        }
    }
    mavenLocal()
}

dependencies {
    api("space.impact:Packet-Network:1.1.8")
    api("space.impact:Impact-API:0.0.4:dev")
    api("space.impact:WAILAPlugins:0.3.1:dev")
    api("space.impact:VirtualWorld:1.4.2:dev")
    api("space.impact:gregtech-impact:5.09.35.25:dev") { isTransitive = false }
    api("com.github.GTNewHorizons:ModularUI:1.1.24:dev") { isTransitive = false }
    api("com.github.GTNewHorizons:NotEnoughItems:2.3.+:dev") { isChanging = true }
    api("com.github.GTNewHorizons:waila:1.6.0:dev") { isTransitive = false }
    api("net.industrial-craft:industrialcraft-2:2.2.828-experimental:dev")
    api("com.github.GTNewHorizons:inventory-tweaks:1.5.+:api") { isTransitive = false }
    api("com.github.GTNewHorizons:EnderCore:0.2.+:dev") { isTransitive = false }

    compileOnly("com.github.GTNewHorizons:NotEnoughEnergistics:1.4.2:dev") { isTransitive = false }
    runtimeOnly("com.github.GTNewHorizons:Nuclear-Control:2.4.+:dev") { isTransitive = false; isChanging = true }

    implementation("com.github.GTNewHorizons:Applied-Energistics-2-Unofficial:rv3-beta-400-GTNH") { isTransitive = false }
    implementation(fileTree(mapOf("dir" to "libs/", "include" to listOf("*.jar"))))
    compileOnly(fileTree(mapOf("dir" to "libs/compile", "include" to listOf("*.jar"))))

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.test {
    useJUnitPlatform()
}
