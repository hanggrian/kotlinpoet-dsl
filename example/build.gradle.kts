plugins {
    kotlin("jvm")
    application
}

application {
    mainClass.set("com.example.VehicleWriter")
}

sourceSets {
    main {
        java.srcDir("src")
    }
}

dependencies {
    api(kotlin("stdlib", VERSION_KOTLIN))
    api(project(":$RELEASE_ARTIFACT"))
}