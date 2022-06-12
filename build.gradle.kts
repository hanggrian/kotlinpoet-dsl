
import com.diffplug.gradle.spotless.SpotlessExtension
import com.vanniktech.maven.publish.MavenPublishBaseExtension
import org.jetbrains.dokka.gradle.DokkaTask

buildscript {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
    dependencies {
        classpath(plugs.kotlin)
        classpath(plugs.kotlin.kover)
        classpath(plugs.dokka)
        classpath(plugs.spotless)
        classpath(plugs.maven.publish)
        classpath(plugs.pages) { features("pages-minimal") }
        classpath(plugs.git.publish)
    }
}

allprojects {
    group = RELEASE_GROUP
    version = RELEASE_VERSION
    repositories {
        mavenCentral()
    }
}

subprojects {
    afterEvaluate {
        extensions.find<org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension>()?.jvmToolchain {
            (this as JavaToolchainSpec).languageVersion.set(JavaLanguageVersion.of(sdk.versions.jdk.get()))
        }
        tasks.find<DokkaTask>("dokkaHtml") {
            outputDirectory.set(buildDir.resolve("dokka/dokka"))
        }
        extensions.find<SpotlessExtension>()?.kotlin {
            ktlint()
        }
        extensions.find<MavenPublishBaseExtension> {
            publishToMavenCentral(com.vanniktech.maven.publish.SonatypeHost.S01)
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
        }
    }
}
