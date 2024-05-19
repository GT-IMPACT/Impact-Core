
plugins {
    alias(libs.plugins.buildconfig)
    groovy
    id("minecraft")
    id("publish")
    kotlin("jvm") version "1.9.24"
}

repositories {
    maven("https://maven.accident.space/repository/maven-public/")
    maven("http://jenkins.usrv.eu:8081/nexus/content/groups/public/") { isAllowInsecureProtocol = true }
    maven("https://jitpack.io")
    maven("https://cursemaven.com")
    maven("https://maven2.ic2.player.to/") { metadataSources { mavenPom(); artifact() } }
    mavenCentral()
    mavenLocal()
}

val modId: String by extra
val modName: String by extra
val modGroup: String by extra

buildConfig {
    packageName("space.impact.$modId")
    buildConfigField("String", "MODID", "\"${modId}\"")
    buildConfigField("String", "MODNAME", "\"${modName}\"")
    buildConfigField("String", "VERSION", "\"${project.version}\"")
    buildConfigField("String", "GROUPNAME", "\"${modGroup}\"")
    buildConfigField("boolean", "IS_DEBUG", "${System.getenv("IMPACT_DEBUG") != null}")
    useKotlinOutput { topLevelConstants = true }
}

tasks.test {
    useJUnitPlatform()
}

dependencies {
    api("space.impact:packet_network:1.1.3")
    api("space.impact:impactapi:0.0.+:dev") { isChanging = true }
    api("space.impact:wailaplugins:0.3.+:dev") { isChanging = true }
    api("space.impact:impact_vw:1.4.0:dev") {
        exclude("io.github.legacymoddingmc")
    }
    api("space.impact:gregtech:5.09.35.21:dev") { isTransitive = false }
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

apply(from = "runConf.gradle")