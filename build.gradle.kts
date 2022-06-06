buildscript {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
    dependencies {
        classpath(kotlin("gradle-plugin", VERSION_KOTLIN))
        classpath(dokka)
        classpath(minimal)
        classpath(`git-publish`)
    }
}

allprojects {
    repositories {
        mavenCentral()
    }
}