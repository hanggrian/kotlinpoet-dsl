package com.hendraanggrian.kotlinpoet

import com.squareup.kotlinpoet.ClassName

/** Returns a [ClassName] created from the given parts. */
fun String.classOf(vararg simpleNames: String): ClassName = ClassName(this, *simpleNames)
