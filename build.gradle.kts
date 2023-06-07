plugins {
    alias(libs.plugins.buildconfig)
    id("minecraft")
    id("publish")
}

repositories {
    maven("https://maven.accident.space/repository/maven-public/")
    maven("http://jenkins.usrv.eu:8081/nexus/content/groups/public/") { isAllowInsecureProtocol = true }
    maven("https://jitpack.io")
    maven( "https://maven.ic2.player.to/") { metadataSources { mavenPom(); artifact() } }
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
    useKotlinOutput { topLevelConstants = true }
}

dependencies {
    api("space.impact:packet_network:1.1.+:dev") { isChanging = true }
    api("space.impact:impactapi:0.0.+:dev") { isChanging = true }
    api("space.impact:wailaplugins:0.3.+:dev") { isChanging = true }
    api("space.impact:impact_vw:1.0.+:dev") { isChanging = true }

    api("com.github.GTNewHorizons:NotEnoughItems:2.3.+:dev") { isChanging = true }
    api("com.github.GTNewHorizons:waila:1.6.0:dev")
    api("net.industrial-craft:industrialcraft-2:2.2.828-experimental:dev")
    api("com.github.GTNewHorizons:inventory-tweaks:1.5.+:api")
    api("com.github.GTNewHorizons:EnderCore:0.2.+:dev")

    compileOnly("com.github.GTNewHorizons:NotEnoughEnergistics:1.4.2:dev") { isTransitive = false }
    runtimeOnly("com.github.GTNewHorizons:Nuclear-Control:2.4.+:dev") { isTransitive = false; isChanging = true }

    implementation("com.github.GTNewHorizons:Applied-Energistics-2-Unofficial:rv3-beta-219-GTNH-pre") { isTransitive = false }
    implementation(fileTree(mapOf("dir" to "libs/", "include" to listOf("*.jar"))))
    compileOnly(fileTree(mapOf("dir" to "libs/compile", "include" to listOf("*.jar"))))
}
