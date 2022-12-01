plugins {
    application
    kotlin("jvm") version libs.versions.kotlin
}

application.mainClass.set("com.example.VehicleWriter")

dependencies.implementation(project(":$RELEASE_ARTIFACT"))
