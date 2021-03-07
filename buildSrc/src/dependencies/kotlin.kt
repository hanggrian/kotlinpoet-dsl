const val VERSION_KOTLIN = "1.4.30"

fun Dependencies.dokka() = "org.jetbrains.dokka:dokka-gradle-plugin:1.4.20"

val Plugins.dokka get() = id("org.jetbrains.dokka")