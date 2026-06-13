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
            includeGroup("com.github.GTNewHorizons")
            includeGroup("com.github.Azanor")
            includeGroupByRegex("space\\.impact\\..+")
        }
        credentials {
            username = System.getenv("MAVEN_USER") ?: "NONE"
            password = System.getenv("MAVEN_PASSWORD") ?: "NONE"
        }
    }
    mavenLocal()
}

dependencies {
    // impact
    api("space.impact:Packet-network:1.1.8.dirty")
    api("space.impact:ImpactAPI:0.0.4:dev")
    api("space.impact:WAILAPlugins:0.3.1.dirty:dev")
    api("space.impact:VirtualWorld:2.0.1:dev")
    api("space.impact:gregtech-impact:5.09.35.36:dev") { isTransitive = false }
    compileOnly("space.impact:OpenComputers:1.7.5.7-impact")
    runtimeOnlyNonPublishable("space.impact:RecipeModule:1.1.0.5") { isTransitive = false }

    // maven impact
    api("com.github.GTNewHorizons:CodeChickenCore:1.3.11:dev") {
        version { strictly("1.3.11") }
    }
    api("com.github.GTNewHorizons:modularui:1.1.24:dev") { isTransitive = false }
    api("com.github.GTNewHorizons:NotEnoughItems:2.6.0-GTNH:dev")
    implementation("com.github.Azanor:Baubles:1.0.1.12")
    implementation("com.github.GTNewHorizons:waila:1.7.3:dev") { isTransitive = false }
    implementation("com.github.GTNewHorizons:EnderCore:0.2.7:dev") { isTransitive = false }
    implementation("com.github.GTNewHorizons:appliedenergistics2:rv3-beta-400-GTNH") { isTransitive = false }
    compileOnly("com.github.GTNewHorizons:NotEnoughEnergistics:1.3.25:dev") { isTransitive = false }
    runtimeOnlyNonPublishable("com.github.GTNewHorizons:IC2NuclearControl:2.6.2:dev") { isTransitive = false }

    // other
    api("net.industrial-craft:industrialcraft-2:2.2.828-experimental:dev")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.test {
    useJUnitPlatform()
}
