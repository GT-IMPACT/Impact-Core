
plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(libs.gradle.kotlin)
    implementation(libs.gradle.gitversion)
    implementation(libs.forge.gradle) { isChanging = true }
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}
