buildscript {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
    dependencies {
        classpath(kotlin("gradle-plugin", VERSION_KOTLIN))
        classpath(dokka)
        classpath(spotless)
        classpath(`gradle-maven-publish`)
        classpath(pages) { features("pages-minimal") }
        classpath(`git-publish`)
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
    plugins.withType<org.jetbrains.kotlin.gradle.plugin.KotlinPlatformJvmPlugin> {
        extensions.configure<org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension> {
            jvmToolchain {
                (this as JavaToolchainSpec).languageVersion.set(JavaLanguageVersion.of(8))
            }
        }
    }
    plugins.withType<org.jetbrains.dokka.gradle.DokkaPlugin> {
        tasks.getByName<org.jetbrains.dokka.gradle.DokkaTask>("dokkaHtml") {
            outputDirectory.set(buildDir.resolve("dokka/dokka"))
        }
    }
    plugins.withType<com.diffplug.gradle.spotless.SpotlessPlugin> {
        extensions.configure<com.diffplug.gradle.spotless.SpotlessExtension> {
            kotlin {
                ktlint()
            }
        }
    }
    plugins.withType<com.vanniktech.maven.publish.MavenPublishBasePlugin> {
        extensions.configure<com.vanniktech.maven.publish.MavenPublishBaseExtension> {
            publishToMavenCentral(com.vanniktech.maven.publish.SonatypeHost.S01)
            signAllPublications()
            pom {
                name.set(RELEASE_ARTIFACT)
                description.set(RELEASE_DESCRIPTION)
                inceptionYear.set("2022")
                url.set(RELEASE_URL)
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                        distribution.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set(DEVELOPER_ID)
                        name.set(DEVELOPER_NAME)
                        url.set(DEVELOPER_URL)
                    }
                }
                scm {
                    url.set(RELEASE_URL)
                    connection.set("scm:git:git://github.com/$DEVELOPER_ID/$RELEASE_ARTIFACT.git")
                    developerConnection.set("scm:git:ssh://git@github.com/$DEVELOPER_ID/$RELEASE_ARTIFACT.git")
                }
            }
        }
    }
}