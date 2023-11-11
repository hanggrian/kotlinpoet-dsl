val releaseArtifact: String by project

plugins {
    kotlin("jvm") version libs.versions.kotlin
    application
}

application.mainClass.set("com.example.VehicleWriter")

dependencies.implementation(project(":$releaseArtifact"))
