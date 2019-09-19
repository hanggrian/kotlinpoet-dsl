plugins {
    kotlin("jvm")
    application
}

group = RELEASE_GROUP
version = RELEASE_VERSION

application.mainClassName = "$RELEASE_GROUP.$RELEASE_ARTIFACT.MyApp"

sourceSets {
    get("main").java.srcDir("src")
    get("test").java.srcDir("tests/src")
}

val ktlint by configurations.registering

dependencies {
    implementation(kotlin("stdlib", VERSION_KOTLIN))
    implementation(kotlinx("coroutines-core", VERSION_COROUTINES))

    testImplementation(kotlin("test-junit", VERSION_KOTLIN))

    ktlint {
        invoke(ktlint())
    }
}

tasks {
    val ktlint by registering(JavaExec::class) {
        group = LifecycleBasePlugin.VERIFICATION_GROUP
        inputs.dir("src")
        outputs.dir("src")
        description = "Check Kotlin code style."
        classpath(configurations["ktlint"])
        main = "com.pinterest.ktlint.Main"
        args("src/**/*.kt")
    }
    "check" {
        dependsOn(ktlint)
    }
    register("ktlintFormat", JavaExec::class) {
        group = "formatting"
        inputs.dir("src")
        outputs.dir("src")
        description = "Fix Kotlin code style deviations."
        classpath(configurations["ktlint"])
        main = "com.pinterest.ktlint.Main"
        args("-F", "src/**/*.kt")
    }
}
