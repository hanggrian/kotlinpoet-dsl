plugins {
    application
    alias(libs.plugins.kotlin.jvm)
}

application.mainClass.set("com.example.VehicleWriter")

dependencies.implementation(project(":$RELEASE_ARTIFACT"))
