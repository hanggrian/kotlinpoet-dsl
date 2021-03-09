@file:Suppress("NOTHING_TO_INLINE")

package io.github.hendraanggrian.kotlinpoet

import com.squareup.kotlinpoet.ClassName

/** Returns a [ClassName] created from the given parts. */
inline fun String.classOf(vararg simpleNames: String): ClassName = ClassName(this, *simpleNames)