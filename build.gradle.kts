import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension

buildscript {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
    dependencies {
        classpath(plugs.kotlin)
        classpath(plugs.kotlin.kover)
        classpath(plugs.dokka)
        classpath(plugs.spotless)
        classpath(plugs.maven.publish)
        classpath(plugs.pages) { features("pages-minimal") }
        classpath(plugs.git.publish)
    }
}

allprojects {
    group = RELEASE_GROUP
    version = RELEASE_VERSION
    repositories {
        mavenCentral()
    }
}

subprojects {
    afterEvaluate {
        extensions.find<KotlinProjectExtension>()?.jvmToolchain {
            (this as JavaToolchainSpec).languageVersion.set(JavaLanguageVersion.of(sdk.versions.jdk.get()))
        }
    }
}
