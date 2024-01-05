import net.minecraftforge.gradle.common.BaseExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

fun BaseExtension.commonMinecraft(project: Project) {
    project.configureCompileOptions()
    minecraftSettings()

    project.registerRuntimeOnlyNonPublishable()
}

private fun BaseExtension.minecraftSettings() {
    version = "1.7.10-10.13.4.1614-1.7.10"
    runDir = "runDir"

}

private fun Project.configureCompileOptions() {
    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = "1.8"
            freeCompilerArgs += listOf(
                "-Xextended-compiler-checks"
            )
        }
    }
}

fun Project.registerRuntimeOnlyNonPublishable() {
    val runtimeOnlyNonPublishable: Configuration by configurations.creating {
        description = "Runtime only dependencies that are not published alongside the jar"
        isCanBeConsumed = false
        isCanBeResolved = false
    }

    listOf(configurations["runtimeClasspath"], configurations["testRuntimeClasspath"]).forEach {
        it.extendsFrom(runtimeOnlyNonPublishable)
    }
}

fun DependencyHandler.implementation(dependencyNotation: Any): Dependency? =
    add("implementation", dependencyNotation)

fun DependencyHandler.api(dependencyNotation: Any): Dependency? =
    add("api", dependencyNotation)

fun DependencyHandler.projectImplementation(depName: String) {
    add("implementation", (project(mapOf("path" to depName))))
}

fun DependencyHandler.runtimeOnlyNonPublishable(dependencyNotation: Any) =
    add("runtimeOnlyNonPublishable", dependencyNotation)

fun DependencyHandler.annotationProcessor(dependencyNotation: Any): Dependency? =
    add("annotationProcessor", dependencyNotation)

fun DependencyHandler.kapt(dependencyNotation: Any): Dependency? =
    add("kapt", dependencyNotation)

val Project.libs: LibrariesForLibs
    get() = the<LibrariesForLibs>()

val Project.sourceSets: SourceSetContainer
    get() = the<SourceSetContainer>()