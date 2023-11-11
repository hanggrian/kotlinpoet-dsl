pluginManagement.repositories {
    mavenCentral()
    gradlePluginPortal()
}
dependencyResolutionManagement.repositories {
    mavenCentral()
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
}

rootProject.name = "kotlinpoet-dsl"

include("kotlinpoet-dsl")
include("sample")
include("website")
