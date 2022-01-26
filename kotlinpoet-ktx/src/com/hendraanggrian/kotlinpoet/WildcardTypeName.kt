package com.hendraanggrian.kotlinpoet

import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.WildcardTypeName
import java.lang.reflect.Type
import kotlin.reflect.KClass

/** Returns a [WildcardTypeName] that represents an unknown type that extends [TypeName]. */
inline fun TypeName.toUpperWildcardTypeName(): WildcardTypeName = WildcardTypeName.producerOf(this)

/** Returns a [WildcardTypeName] that represents an unknown type that extends [Type]. */
inline fun Type.toUpperWildcardTypeName(): WildcardTypeName = WildcardTypeName.producerOf(this)

/** Returns a [WildcardTypeName] that represents an unknown type that extends [KClass]. */
inline fun KClass<*>.toUpperWildcardTypeName(): WildcardTypeName = WildcardTypeName.producerOf(this)

/** Returns a [WildcardTypeName] that represents an unknown type that produces [T]. */
inline fun <reified T> wildcardTypeNameUpperOf(): WildcardTypeName = WildcardTypeName.producerOf(T::class)

/** Returns a [WildcardTypeName] that represents an unknown supertype of [TypeName]. */
inline fun TypeName.toLowerWildcardTypeName(): WildcardTypeName = WildcardTypeName.consumerOf(this)

/** Returns a [WildcardTypeName] that represents an unknown supertype of [Type]. */
inline fun Type.toLowerWildcardTypeName(): WildcardTypeName = WildcardTypeName.consumerOf(this)

/** Returns a [WildcardTypeName] that represents an unknown supertype of [KClass]. */
inline fun KClass<*>.toLowerWildcardTypeName(): WildcardTypeName = WildcardTypeName.consumerOf(this)

/** Returns a [WildcardTypeName] that represents an unknown type that consumes [T]. */
inline fun <reified T> wildcardTypeNameLowerOf(): WildcardTypeName = WildcardTypeName.consumerOf(T::class)
