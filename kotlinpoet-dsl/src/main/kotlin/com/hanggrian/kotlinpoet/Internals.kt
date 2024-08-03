package com.hanggrian.kotlinpoet

internal const val DELICATE_API =
    "Java reflection APIs don't give complete information on Kotlin types"

internal const val NO_GETTER: String = "Property does not have a getter"

internal fun noGetter(): Nothing = throw UnsupportedOperationException(NO_GETTER)
