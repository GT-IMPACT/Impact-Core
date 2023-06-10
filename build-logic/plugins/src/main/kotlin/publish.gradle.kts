@file:Suppress("DSL_SCOPE_VIOLATION")

import com.palantir.gradle.gitversion.VersionDetails
import groovy.lang.Closure
import groovy.util.Node
import groovy.util.NodeList
import org.jetbrains.kotlin.gradle.plugin.mpp.pm20.util.archivesName

plugins {
    kotlin("jvm")
    id("com.palantir.git-version")
    `maven-publish`
    `java-library`
}

val modId: String by extra
val modName: String by extra
val modGroup: String by extra
val versionDetails: Closure<VersionDetails> by extra
val gitDetails = versionDetails()
group = modGroup

var versionOverride: String? = System.getenv("VERSION") ?: null
var identifiedVersion = runCatching {
    versionOverride ?: if (System.getenv("CI") != null) gitDetails.lastTag else gitDetails.version
}.getOrElse {
    "unknown".also {
        versionOverride = it
    }
}

version = identifiedVersion
val modVersion = identifiedVersion

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

archivesName.set(modId)

tasks.withType<GenerateModuleMetadata> {
    enabled = false
    mustRunAfter("reobf")
}

configure<PublishingExtension> {
    publications {
        register("mavenJava", MavenPublication::class) {
            from(components["java"])
            pom.withXml {
                removeRuntimeDependencies(asNode())
            }
            val devJar by tasks.registering(Jar::class) {
                from(sourceSets["main"].output)
                archiveClassifier.set("dev")
            }
            artifact(devJar.get())
            groupId = "space.impact"
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

//hack https://youtrack.jetbrains.com/issue/KT-28355
fun removeRuntimeDependencies(pomNode: Node) {
    val dependencyNodes: NodeList = pomNode.get("dependencies") as NodeList
    val dependencies = dependencyNodes.lastOrNull() as? Node
    val removeCandidate = arrayListOf<Node>()
    dependencies?.children()?.forEach { dependency ->
        (dependency as? Node)?.children()
            ?.mapNotNull { it as? Node }
            ?.filter { (it.value() as? NodeList)?.lastOrNull() == "runtime" || it.value() == "org.jetbrains.kotlin" }
            ?.forEach { removeCandidate += it.parent() }
    }
    removeCandidate.forEach {
        it.parent().remove(it)
    }
}
