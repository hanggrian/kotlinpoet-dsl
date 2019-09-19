plugins {
    kotlin("jvm")
    application
}

application.mainClassName = "com.example.MyApp"

sourceSets["main"].java.srcDir("src")

dependencies {
    implementation(kotlin("stdlib", VERSION_KOTLIN))
    implementation(project(":$RELEASE_ARTIFACT"))
}
