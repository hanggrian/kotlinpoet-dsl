package com.hendraanggrian.kotlinpoet

import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asTypeName

/** Returns a [TypeName] equivalent to this [T].  */
inline fun <reified T> asTypeName(): TypeName = T::class.asTypeName()

/** Create a nullable coppy of [TypeName].  */
fun TypeName.asNullable(): TypeName = copy(true)
