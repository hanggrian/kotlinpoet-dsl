include("kotlinpoet-dsl")
include("sample")
include("website")

dependencyResolutionManagement {
    versionCatalogs {
        val kotlinVersion = "1.6.21"
        register("sdk") {
            version("jdk", "8")
        }
        register("plugs") {
            val koverVersion = "0.5.0"
            val spotlessVersion = "6.7.0"
            val mavenPublishVersion = "0.20.0"
            val pagesVersion = "0.1"
            val gitPublishVersion = "3.0.1"
            library("kotlin", "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
            library("kotlin.kover", "org.jetbrains.kotlinx:kover:$koverVersion")
            library("dokka", "org.jetbrains.dokka:dokka-gradle-plugin:$kotlinVersion")
            library("spotless", "com.diffplug.spotless:spotless-plugin-gradle:$spotlessVersion")
            library("maven-publish", "com.vanniktech:gradle-maven-publish-plugin:$mavenPublishVersion")
            library("pages", "com.hendraanggrian:pages-gradle-plugin:$pagesVersion")
            library("git-publish", "org.ajoberstar.git-publish:gradle-git-publish:$gitPublishVersion")
        }
        register("libs") {
            val coroutinesVersion = "1.6.2"
            val kotlinpoetVersion = "1.11.0"
            library("kotlinx-coroutines", "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
            library("kotlinpoet", "com.squareup:kotlinpoet:$kotlinpoetVersion")
        }
        register("testLibs") {
            val truthVersion = "1.1.3"
            library("kotlin-junit", "org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")
            library("truth", "com.google.truth:truth:$truthVersion")
        }
    }
}
