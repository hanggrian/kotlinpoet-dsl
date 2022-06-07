import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = RELEASE_GROUP
version = RELEASE_VERSION

plugins {
    kotlin("jvm")
    dokka
    `maven-publish`
    signing
}

kotlin {
    jvmToolchain {
        (this as JavaToolchainSpec).languageVersion.set(JavaLanguageVersion.of(8))
    }
}

ktlint()

dependencies {
    api(kotlin("stdlib", VERSION_KOTLIN))
    api(squareup("kotlinpoet", VERSION_KOTLINPOET))
    testImplementation(kotlin("test-junit", VERSION_KOTLIN))
    testImplementation(google("truth", version = VERSION_TRUTH))
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions.freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
    }
    dokkaHtml {
        outputDirectory.set(buildDir.resolve("dokka/dokka"))
    }
}

mavenPublishJvm()