import org.jetbrains.kotlin.gradle.dsl.kotlinExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinPluginWrapper

buildscript {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

plugins {
    alias(plugs.plugins.kotlin.jvm) apply false
}

allprojects {
    group = RELEASE_GROUP
    version = RELEASE_VERSION
    repositories.mavenCentral()
}

subprojects {
    withPluginEagerly<KotlinPluginWrapper> {
        kotlinExtension.jvmToolchain {
            languageVersion.set(JavaLanguageVersion.of(sdk.versions.jdk.get()))
        }
    }
}
