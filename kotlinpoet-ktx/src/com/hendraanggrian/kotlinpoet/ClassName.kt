package com.hendraanggrian.kotlinpoet

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.asClassName

/** Returns a [ClassName] for the given fully-qualified class name string. */
inline fun classNameOf(fullName: String): ClassName = ClassName.bestGuess(fullName)

/** Returns a [ClassName] created from the given parts. */
inline fun classNameOf(packageName: String, simpleName: String, vararg simpleNames: String): ClassName =
    ClassName(packageName, simpleName, *simpleNames)

/** Returns a [ClassName] equivalent to [T]. */
inline fun <reified T> classNameOf(): ClassName = T::class.asClassName()
