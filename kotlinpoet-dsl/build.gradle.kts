import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinJvm

plugins {
    kotlin("jvm")
    id("org.jetbrains.dokka")
    id("com.diffplug.spotless")
    id("com.vanniktech.maven.publish.base")
}

mavenPublishing.configure(KotlinJvm(JavadocJar.Dokka("dokkaJavadoc")))

dependencies {
    api(libs.kotlinpoet)
    testImplementation(testLibs.kotlin.junit)
    testImplementation(testLibs.truth)
}

tasks.compileKotlin {
    kotlinOptions.freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
}
