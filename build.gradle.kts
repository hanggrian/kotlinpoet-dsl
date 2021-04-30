buildscript {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
    dependencies {
        classpath(kotlin("gradle-plugin", VERSION_KOTLIN))
        classpath(dokka)
        classpath(`git-publish`)
    }
}

allprojects {
    repositories {
        mavenCentral()
    }
    tasks {
        withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
            kotlinOptions { jvmTarget = "1.8" }
        }
    }
}