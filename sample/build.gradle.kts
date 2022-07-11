plugins {
    application
    alias(plugs.plugins.kotlin.jvm)
}

application.mainClass.set("com.example.VehicleWriter")

dependencies.implementation(project(":$RELEASE_ARTIFACT"))
