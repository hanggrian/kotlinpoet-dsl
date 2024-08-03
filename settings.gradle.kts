pluginManagement.repositories {
    gradlePluginPortal()
    mavenCentral()
}
dependencyResolutionManagement.repositories.mavenCentral()

rootProject.name = "kotlinpoet-dsl"

include("kotlinpoet-dsl")
include("sample")
include("website")
