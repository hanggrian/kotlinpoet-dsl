plugins {
    kotlin("jvm")
    application
}

application {
    mainClass.set("com.example.VehicleWriter")
}

dependencies {
    implementation(kotlin("stdlib", VERSION_KOTLIN))
    implementation(project(":$RELEASE_ARTIFACT"))
}