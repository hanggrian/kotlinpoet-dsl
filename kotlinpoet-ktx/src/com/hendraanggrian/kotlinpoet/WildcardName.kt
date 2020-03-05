package com.hendraanggrian.kotlinpoet

import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.WildcardTypeName
import java.lang.reflect.Type
import kotlin.reflect.KClass

/**
 * Returns a [WildcardTypeName] producer of [T].
 * @see com.squareup.kotlinpoet.typeNameOf
 */
inline fun <reified T> wildcardTypeNameProducerOf(): WildcardTypeName = T::class.producerOf()

/**
 * Returns a [WildcardTypeName] consumer of [T].
 * @see com.squareup.kotlinpoet.typeNameOf
 */
inline fun <reified T> wildcardTypeNameConsumerOf(): WildcardTypeName = T::class.consumerOf()

/** Returns a [WildcardTypeName] that represents an unknown type that produces [TypeName]. */
fun TypeName.producerOf(): WildcardTypeName = WildcardTypeName.producerOf(this)

/** Returns a [WildcardTypeName] that represents an unknown type that produces [Type]. */
fun Type.producerOf(): WildcardTypeName = WildcardTypeName.producerOf(this)

/** Returns a [WildcardTypeName] that represents an unknown type that produces [KClass]. */
fun KClass<*>.producerOf(): WildcardTypeName = WildcardTypeName.producerOf(this)

/** Returns a [WildcardTypeName] that represents an unknown type that consumes [TypeName]. */
fun TypeName.consumerOf(): WildcardTypeName = WildcardTypeName.consumerOf(this)

/** Returns a [WildcardTypeName] that represents an unknown type that consumes [Type]. */
fun Type.consumerOf(): WildcardTypeName = WildcardTypeName.consumerOf(this)

/** Returns a [WildcardTypeName] that represents an unknown type that consumes [KClass]. */
fun KClass<*>.consumerOf(): WildcardTypeName = WildcardTypeName.consumerOf(this)
