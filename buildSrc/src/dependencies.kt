internal typealias Plugins = org.gradle.plugin.use.PluginDependenciesSpec
internal typealias Dependencies = org.gradle.api.artifacts.dsl.DependencyHandler

const val VERSION_KOTLIN = "1.4.32"
const val VERSION_COROUTINES = "1.4.3"
val Dependencies.dokka get() = "org.jetbrains.dokka:dokka-gradle-plugin:1.4.30"
val Plugins.dokka get() = id("org.jetbrains.dokka")
fun Dependencies.kotlinx(module: String, version: String? = null) =
    "org.jetbrains.kotlinx:kotlinx-$module${version?.let { ":$it" }.orEmpty()}"

val Dependencies.`git-publish` get() = "org.ajoberstar:gradle-git-publish:2.1.3"
val Plugins.`git-publish` get() = id("org.ajoberstar.git-publish")

const val VERSION_KOTLINPOET = "1.7.1"
fun Dependencies.squareup(module: String, version: String) =
    "com.squareup:$module:$version"

const val VERSION_TRUTH = "1.0.1"
fun Dependencies.google(repo: String? = null, module: String, version: String) =
    "com.google${repo?.let { ".$it" }.orEmpty()}:$module:$version"