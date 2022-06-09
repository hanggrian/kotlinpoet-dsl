plugins {
    kotlin("jvm")
    dokka
    spotless
    `gradle-maven-publish`
}

mavenPublishing {
    configure(
        com.vanniktech.maven.publish.JavaLibrary(
            com.vanniktech.maven.publish.JavadocJar.Dokka("dokkaJavadoc")
        )
    )
}

dependencies {
    api(kotlin("stdlib", VERSION_KOTLIN))
    api(squareup("kotlinpoet", VERSION_KOTLINPOET))
    testImplementation(kotlin("test-junit", VERSION_KOTLIN))
    testImplementation(google("truth", version = VERSION_TRUTH))
}

tasks {
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
    }
}