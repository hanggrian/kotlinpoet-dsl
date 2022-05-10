package com.hendraanggrian.kotlinpoet

import com.squareup.kotlinpoet.CodeBlock

/** Equivalent to [CodeBlock.EMPTY]. */
@PublishedApi
internal val EMPTY_CODEBLOCK: CodeBlock = CodeBlock.builder().build()

internal const val DELICATE_JAVA = "Java reflection APIs don't give complete information on Kotlin types."
internal const val DELICATE_MIRROR = "Mirror APIs don't give complete information on Kotlin types."
internal const val DELICATE_ELEMENT = "Element APIs don't give complete information on Kotlin types."

/** Field deprecation message. */
internal const val NO_GETTER: String = "Property does not have a getter"

/** Some mutable backing fields are only used to set value. */
internal fun noGetter(): Nothing = throw UnsupportedOperationException(NO_GETTER)
