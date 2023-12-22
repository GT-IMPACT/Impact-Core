
plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(libs.gradle.kotlin)
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.21")
    implementation(libs.gradle.gitversion)
    implementation(libs.forge.gradle) { isChanging = true }
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}
