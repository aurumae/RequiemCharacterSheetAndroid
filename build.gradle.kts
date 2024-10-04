// Top-level build file where you can add configuration options common to all sub-projects/modules.

// Root build.gradle.kts
plugins {
    id("com.android.application") version "8.7.0" apply false
    id("org.jetbrains.kotlin.android") version "2.0.20" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.20" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.20" apply false
}

task<Delete>("clean") {
    delete(rootProject.layout.buildDirectory)
}
