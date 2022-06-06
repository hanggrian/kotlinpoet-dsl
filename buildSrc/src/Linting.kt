import org.gradle.api.Project
import org.gradle.api.attributes.Bundling
import org.gradle.api.tasks.JavaExec
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.registering
import org.gradle.language.base.plugins.LifecycleBasePlugin

fun Dependencies.ktlint(module: String? = null) = when (module) {
    null -> "com.pinterest:ktlint:0.45.2"
    else -> "com.pinterest.ktlint:ktlint-$module:0.45.2"
}

fun Project.ktlint(vararg rulesets: Any) {
    val ktlint by configurations.registering
    dependencies {
        ktlint {
            invoke(ktlint()) {
                attributes {
                    attribute(Bundling.BUNDLING_ATTRIBUTE, objects.named<Bundling>(Bundling.EXTERNAL))
                }
            }
            rulesets.forEach { invoke(it) }
        }
    }
    val outputDir = "${project.buildDir}/reports/ktlint/"
    val inputFiles = project.fileTree(mapOf("dir" to "src", "include" to "**/*.kt"))
    tasks {
        val ktlintCheck by registering(JavaExec::class) {
            group = LifecycleBasePlugin.VERIFICATION_GROUP
            inputs.files(inputFiles)
            outputs.dir(outputDir)
            description = "Check Kotlin code style."
            classpath = ktlint.get()
            mainClass.set("com.pinterest.ktlint.Main")
            args("src/**/*.kt")
        }
        named("check") {
            dependsOn(ktlintCheck)
        }
        register<JavaExec>("ktlintFormat") {
            group = "formatting"
            inputs.files(inputFiles)
            outputs.dir(outputDir)
            description = "Fix Kotlin code style deviations."
            classpath = ktlint.get()
            mainClass.set("com.pinterest.ktlint.Main")
            args("-F", "src/**/*.kt")
            jvmArgs("--add-opens", "java.base/java.lang=ALL-UNNAMED")
        }
    }
}