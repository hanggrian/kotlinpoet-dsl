package com.hendraanggrian.kotlinpoet

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.asClassName

/** Returns a [ClassName] equivalent to this [T].  */
inline fun <reified T> asClassName(): ClassName =
    T::class.asClassName()
