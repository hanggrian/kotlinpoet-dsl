const val RELEASE_USER = "hendraanggrian"
const val RELEASE_GROUP = "com.hendraanggrian"
const val RELEASE_ARTIFACT = "kotlinpoet-ktx"
const val RELEASE_VERSION = "0.1-SNAPSHOT"
const val RELEASE_DESCRIPTION = "KotlinPoet Kotlin extension"
const val RELEASE_GITHUB = "https://github.com/hendraanggrian/$RELEASE_ARTIFACT"

fun getGithubRemoteUrl(artifact: String = RELEASE_ARTIFACT) =
    `java.net`.URL("$RELEASE_GITHUB/tree/main/$artifact/src")