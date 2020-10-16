const val VERSION_KOTLIN = "1.4.10"

fun Dependencies.dokka() = "org.jetbrains.dokka:dokka-gradle-plugin:$VERSION_KOTLIN"

val Plugins.dokka get() = id("org.jetbrains.dokka")