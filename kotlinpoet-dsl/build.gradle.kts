import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinJvm
import com.vanniktech.maven.publish.SonatypeHost

plugins {
    alias(plugs.plugins.kotlin.jvm)
    alias(plugs.plugins.kotlinx.kover)
    alias(plugs.plugins.dokka)
    alias(plugs.plugins.spotless)
    alias(plugs.plugins.maven.publish)
}

kover.generateReportOnCheck = false

spotless.kotlin {
    ktlint().editorConfigOverride(mapOf("disabled_rules" to "filename"))
}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.S01)
    signAllPublications()
    pom {
        name.set(project.name)
        description.set(RELEASE_DESCRIPTION)
        url.set(RELEASE_URL)
        licenses {
            license {
                name.set("The Apache License, Version 2.0")
                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                distribution.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }
        scm {
            connection.set("scm:git:https://github.com/$DEVELOPER_ID/$RELEASE_ARTIFACT.git")
            developerConnection.set("scm:git:ssh://git@github.com/$DEVELOPER_ID/$RELEASE_ARTIFACT.git")
            url.set(RELEASE_URL)
        }
        developers {
            developer {
                id.set(DEVELOPER_ID)
                name.set(DEVELOPER_NAME)
                url.set(DEVELOPER_URL)
            }
        }
    }
    configure(KotlinJvm(JavadocJar.Dokka("dokkaJavadoc")))
}

dependencies {
    api(libs.kotlinpoet)
    testImplementation(testLibs.kotlin.junit)
    testImplementation(testLibs.truth)
}

tasks {
    compileKotlin {
        kotlinOptions.freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
    }
    dokkaHtml {
        outputDirectory.set(buildDir.resolve("dokka/dokka"))
    }
}
