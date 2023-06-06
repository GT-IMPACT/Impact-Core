plugins {
    alias(libs.plugins.buildconfig)
    id("minecraft")
    id("publish")
}

repositories {
    mavenCentral()
    maven("https://maven.accident.space/repository/maven-public/")
    maven("https://jitpack.io")
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
    implementation(fileTree(mapOf("dir" to "libs/", "include" to listOf("*.jar"))))
    compileOnly(fileTree(mapOf("dir" to "notRun/", "include" to listOf("*.jar"))))
}
