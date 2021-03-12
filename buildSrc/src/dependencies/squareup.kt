const val VERSION_KOTLINPOET = "1.7.1"

fun org.gradle.api.artifacts.dsl.DependencyHandler.squareup(module: String, version: String) =
    "com.squareup:$module:$version"