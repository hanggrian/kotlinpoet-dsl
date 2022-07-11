include("kotlinpoet-dsl")
include("sample")
include("website")

dependencyResolutionManagement {
    versionCatalogs {
        val kotlinVersion = "1.7.0"
        register("sdk") {
            version("jdk", "8")
        }
        register("plugs") {
            plugin("kotlin-jvm", "org.jetbrains.kotlin.jvm").version(kotlinVersion)
            plugin("kotlin-kapt", "org.jetbrains.kotlin.kapt").version(kotlinVersion)
            plugin("kotlinx-kover", "org.jetbrains.kotlinx.kover").version("0.5.1")
            plugin("dokka", "org.jetbrains.dokka").version(kotlinVersion)
            plugin("spotless", "com.diffplug.spotless").version("6.7.2")
            plugin("maven-publish", "com.vanniktech.maven.publish.base").version("0.20.0")
            plugin("git-publish", "org.ajoberstar.git-publish").version("3.0.1")
            plugin("pages", "com.hendraanggrian.pages").version("0.1")
        }
        register("libs") {
            library("kotlinx-coroutines", "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.3")
            library("kotlinpoet", "com.squareup:kotlinpoet:1.11.0")
        }
        register("testLibs") {
            library("kotlin-junit", "org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")
            library("truth", "com.google.truth:truth:1.1.3")
        }
    }
}
