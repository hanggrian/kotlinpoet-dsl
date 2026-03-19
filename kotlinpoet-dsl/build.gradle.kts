import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinJvm
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

val developerId: String by project
val developerName: String by project
val developerUrl: String by project
val releaseArtifact: String by project
val releaseDescription: String by project
val releaseUrl: String by project

val javaCompileVersion = JavaLanguageVersion.of(libs.versions.java.compile.get())
val javaSupportVersion = JavaVersion.toVersion(libs.versions.java.support.get())

plugins {
    kotlin("jvm") version libs.versions.kotlin
    alias(libs.plugins.dokka)
    alias(libs.plugins.dokka.javadoc)
    alias(libs.plugins.kotlinx.kover)
    alias(libs.plugins.maven.publish)
}

java {
    toolchain.languageVersion.set(javaCompileVersion)
    sourceCompatibility = javaSupportVersion
    targetCompatibility = javaSupportVersion
}

kotlin {
    jvmToolchain {
        languageVersion.set(javaCompileVersion)
    }
    compilerOptions.jvmTarget.set(JvmTarget.fromTarget(javaSupportVersion.toString()))
    explicitApi()
}

mavenPublishing {
    configure(KotlinJvm(JavadocJar.Dokka("dokkaGeneratePublicationJavadoc")))
    publishToMavenCentral()
    signAllPublications()
    pom {
        name.set(project.name)
        description.set(releaseDescription)
        url.set(releaseUrl)
        inceptionYear.set("2024")
        licenses {
            license {
                name.set("The Apache License, Version 2.0")
                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                distribution.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }
        developers {
            developer {
                id.set(developerId)
                name.set(developerName)
                url.set(developerUrl)
            }
        }
        scm {
            url.set(releaseUrl)
            connection.set("scm:git:https://github.com/$developerId/$releaseArtifact.git")
            developerConnection
                .set("scm:git:ssh://git@github.com/$developerId/$releaseArtifact.git")
        }
    }
}

dependencies {
    api(libs.kotlinpoet)

    testImplementation(kotlin("test-junit5", libs.versions.kotlin.get()))
    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.bundles.junit5)

    testRuntimeOnly(libs.junit.platform.launcher)
}

tasks {
    compileTestJava {
        options.release.set(11)
    }
    compileTestKotlin {
        compilerOptions.jvmTarget.set(JvmTarget.JVM_11)
    }
    test {
        useJUnitPlatform()
    }
}
