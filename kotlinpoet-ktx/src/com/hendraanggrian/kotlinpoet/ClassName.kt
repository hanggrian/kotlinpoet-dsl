package com.hendraanggrian.kotlinpoet

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.asClassName

/** Returns a [ClassName] created from the given parts. */
fun String.classOf(vararg simpleNames: String): ClassName = ClassName(this, *simpleNames)

/**
 * Returns a [ClassName] equivalent to [T].
 * @see com.squareup.kotlinpoet.typeNameOf
 */
inline fun <reified T> classNameOf(): ClassName = T::class.asClassName()
