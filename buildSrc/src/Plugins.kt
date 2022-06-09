import org.gradle.api.artifacts.ModuleDependency

internal typealias Plugins = org.gradle.plugin.use.PluginDependenciesSpec

fun ModuleDependency.features(vararg capabilityModules: Any) =
    capabilities { capabilityModules.forEach { requireCapability("$group:$it") } }

const val VERSION_KOTLIN = "1.6.21"
const val VERSION_COROUTINES = "1.6.2"
val Dependencies.dokka get() = "org.jetbrains.dokka:dokka-gradle-plugin:$VERSION_KOTLIN"
val Plugins.dokka get() = id("org.jetbrains.dokka")
fun Dependencies.kotlinx(module: String, version: String? = null) =
    "org.jetbrains.kotlinx:kotlinx-$module${version?.let { ":$it" }.orEmpty()}"

val Dependencies.spotless get() = "com.diffplug.spotless:spotless-plugin-gradle:6.7.0"
val Plugins.spotless get() = id("com.diffplug.spotless")

val Dependencies.`gradle-maven-publish` get() = "com.vanniktech:gradle-maven-publish-plugin:0.20.0"
val Plugins.`gradle-maven-publish` get() = id("com.vanniktech.maven.publish.base")

val Dependencies.pages get() = "com.hendraanggrian:pages-gradle-plugin:0.1"
val Plugins.pages get() = id("com.hendraanggrian.pages")

val Dependencies.`git-publish` get() = "org.ajoberstar.git-publish:gradle-git-publish:3.0.1"
val Plugins.`git-publish` get() = id("org.ajoberstar.git-publish")
