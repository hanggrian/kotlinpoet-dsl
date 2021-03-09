import org.gradle.api.Project
import org.gradle.api.provider.Provider
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.tasks.TaskProvider
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.withType
import org.gradle.plugins.signing.Sign
import org.gradle.plugins.signing.SigningExtension

private val OSSRH_USERNAME get() = System.getenv("OSSRH_USERNAME")
private val OSSRH_PASSWORD get() = System.getenv("OSSRH_PASSWORD")

private fun isReleaseSnapshot() = RELEASE_VERSION.endsWith("SNAPSHOT")

fun Project.mavenCentral(
    dokkaJar: TaskProvider<Jar>,
    sourcesJar: TaskProvider<Jar>,
    libraryName: String = RELEASE_ARTIFACT
) {
    lateinit var mavenJava: Provider<MavenPublication>
    extensions.configure<PublishingExtension>("publishing") {
        repositories {
            maven {
                val releasesRepoUrl = "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"
                val snapshotsRepoUrl = "https://s01.oss.sonatype.org/content/repositories/snapshots/"
                url = uri(if (isReleaseSnapshot()) snapshotsRepoUrl else releasesRepoUrl)
                credentials {
                    username = OSSRH_USERNAME
                    password = OSSRH_PASSWORD
                }
            }
        }
        publications {
            mavenJava = register<MavenPublication>("mavenJava") {
                groupId = RELEASE_GROUP
                artifactId = libraryName
                version = RELEASE_VERSION
                from(components["java"])
                artifact(dokkaJar.get())
                artifact(sourcesJar.get())
                pom {
                    name.set(libraryName)
                    description.set(RELEASE_DESCRIPTION)
                    url.set(RELEASE_URL)
                    licenses {
                        license {
                            name.set("The Apache License, Version 2.0")
                            url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                        }
                    }
                    developers {
                        developer {
                            id.set(RELEASE_USER)
                            name.set("Hendra Anggrian")
                            email.set("hendraanggrian@gmail.com")
                        }
                    }
                    scm {
                        connection.set("scm:git:$RELEASE_URL.git")
                        developerConnection.set("scm:git:$RELEASE_URL.git")
                        url.set(RELEASE_URL)
                    }
                }
            }
        }
    }
    extensions.configure<SigningExtension>("signing") { sign(mavenJava.get()) }
    tasks.withType<Sign> { onlyIf { isReleaseSnapshot() } }
}