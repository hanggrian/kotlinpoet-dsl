plugins {
    kotlin("jvm")
    application
}

application.mainClass.set("com.example.VehicleWriter")

dependencies {
    implementation(project(":$RELEASE_ARTIFACT"))
}
