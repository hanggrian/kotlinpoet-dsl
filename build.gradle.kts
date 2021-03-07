buildscript {
    repositories {
        mavenCentral()
        maven("https://ajoberstar.github.io/bintray-backup/")
    }
    dependencies {
        classpath(kotlin("gradle-plugin", VERSION_KOTLIN))
        classpath(dokka())
        classpath(gitPublish())
    }
}

allprojects {
    repositories {
        mavenCentral()
    }
    tasks {
        withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
        withType<Delete> {
            delete(projectDir.resolve("out"))
        }
    }
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}