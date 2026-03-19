import org.jlleitschuh.gradle.ktlint.KtlintExtension
import org.jlleitschuh.gradle.ktlint.KtlintPlugin

val releaseGroup: String by project
val releaseVersion: String by project

plugins {
    kotlin("jvm") version libs.versions.kotlin apply false
    alias(libs.plugins.ktlint.gradle)
}

allprojects {
    group = releaseGroup
    version = releaseVersion

    plugins.apply(KtlintPlugin::class)

    afterEvaluate {
        the<KtlintExtension>().version.set(libs.versions.ktlint.get())

        dependencies.ktlintRuleset(libs.rulebook.ktlint)
    }
}

tasks.register(LifecycleBasePlugin.CLEAN_TASK_NAME) {
    delete(layout.buildDirectory)
}
