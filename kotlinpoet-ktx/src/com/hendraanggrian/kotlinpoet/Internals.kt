package com.hendraanggrian.kotlinpoet

import com.squareup.kotlinpoet.CodeBlock

/** Equivalent to [CodeBlock.EMPTY]. */
@PublishedApi
internal val EMPTY_CODEBLOCK: CodeBlock = CodeBlock.builder().build()

/** Field deprecation message. */
internal const val NO_GETTER: String = "Property does not have a getter"

/** Some mutable backing fields are only used to set value. */
internal fun noGetter(): Nothing = throw UnsupportedOperationException(NO_GETTER)
