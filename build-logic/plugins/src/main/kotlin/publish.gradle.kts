@file:Suppress("DSL_SCOPE_VIOLATION")

import com.palantir.gradle.gitversion.VersionDetails
import groovy.lang.Closure
import groovy.util.Node
import groovy.util.NodeList
import org.jetbrains.kotlin.gradle.plugin.mpp.pm20.util.archivesName
import java.net.HttpURLConnection
import java.net.URL

plugins {
    kotlin("jvm")
    id("com.palantir.git-version")
    `maven-publish`
    `java-library`
}

val modId: String by extra
val modName: String by extra
val modGroup: String by extra
val modFile: String? by extra
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

tasks.register("deployImpact") {
    doLast {
        try {
            val folder = File(buildDir, "libs")

            val ignore = ".*-dev.jar".toRegex()
            if (folder.isDirectory) {
                val jar = folder.listFiles()?.find { !ignore.matches(it.name) } ?: throw NullPointerException("Not found jar file!")
                println(jar.name)
                val link = System.getenv("URL_MODPACK_MOD_UPDATE") ?: "http://impact.accident.space:25565/upload/impact1"
                val url = URL("$link?name=${jar.name}&modid=${archivesName.get()}&version=$modVersion")
                val conn = url.openConnection() as HttpURLConnection
                conn.setReadTimeout(10000)
                conn.setConnectTimeout(15000)
                conn.setRequestMethod("POST")
                conn.setUseCaches(false)
                conn.setDoInput(true)
                conn.setDoOutput(true)
                conn.outputStream.use {
                    it.write(jar.readBytes())
                }
                conn.connect()
                val status = conn.getResponseCode()
                println(status)
                if (status != HttpURLConnection.HTTP_OK)
                    throw NullPointerException("Error Upload")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    if (System.getenv("CI") == null)
        dependsOn(tasks.test, tasks.build)
    else
        dependsOn(tasks.clean, tasks.test, tasks.publish)
}

archivesName.set(modFile ?: modId)

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

            artifact(tasks["devJar"])
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
