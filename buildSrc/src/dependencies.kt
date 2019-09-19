import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.plugin.use.PluginDependenciesSpec

fun DependencyHandler.kotlinpoet() = "com.squareup:kotlinpoet:$VERSION_KOTLINPOET"

fun DependencyHandler.dokka() = "org.jetbrains.dokka:dokka-gradle-plugin:$VERSION_DOKKA"
fun PluginDependenciesSpec.dokka() = id("org.jetbrains.dokka")

fun DependencyHandler.bintrayRelease() = "com.novoda:bintray-release:$VERSION_BINTRAY_RELEASE"
inline val PluginDependenciesSpec.`bintray-release` get() = id("com.novoda.bintray-release")

fun DependencyHandler.gitPublish() = "org.ajoberstar:gradle-git-publish:$VERSION_GIT_PUBLISH"
inline val PluginDependenciesSpec.`git-publish` get() = id("org.ajoberstar.git-publish")

fun DependencyHandler.ktlint() = "com.pinterest:ktlint:$VERSION_KTLINT"

fun DependencyHandler.truth() = "com.google.truth:truth:$VERSION_TRUTH"