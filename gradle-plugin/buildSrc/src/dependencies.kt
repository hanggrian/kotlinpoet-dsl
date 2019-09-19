import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.plugin.use.PluginDependenciesSpec

fun DependencyHandler.kotlinx(
    module: String,
    version: String? = null
) = "org.jetbrains.kotlinx:kotlinx-$module${version?.let { ":$it" } ?: ""}"

fun DependencyHandler.dokka(module: String? = null) = "org.jetbrains.dokka:dokka-${module.wrap { "$it-" }}gradle-plugin:$VERSION_DOKKA"
fun PluginDependenciesSpec.dokka(module: String? = null) = id("org.jetbrains.dokka${module.wrap { "-$it" }}")

fun DependencyHandler.bintrayRelease() = "com.novoda:bintray-release:$VERSION_BINTRAY_RELEASE"
inline val PluginDependenciesSpec.`bintray-release` get() = id("com.novoda.bintray-release")

fun DependencyHandler.gitPublish() = "org.ajoberstar:gradle-git-publish:$VERSION_GIT_PUBLISH"
inline val PluginDependenciesSpec.`git-publish` get() = id("org.ajoberstar.git-publish")

fun DependencyHandler.ktlint() = "com.pinterest:ktlint:$VERSION_KTLINT"

private fun String?.wrap(wrapper: (String) -> String) = this?.let(wrapper).orEmpty()
