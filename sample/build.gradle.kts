plugins {
    kotlin("jvm")
    application
}

application {
    mainClass.set("com.example.VehicleWriter")
}

kotlin {
    jvmToolchain {
        (this as JavaToolchainSpec).languageVersion.set(JavaLanguageVersion.of(8))
    }
}

dependencies {
    api(kotlin("stdlib", VERSION_KOTLIN))
    api(project(":$RELEASE_ARTIFACT"))
}