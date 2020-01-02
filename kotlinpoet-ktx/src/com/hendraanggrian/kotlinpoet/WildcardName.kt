package com.hendraanggrian.kotlinpoet

import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.WildcardTypeName
import java.lang.reflect.Type
import kotlin.reflect.KClass

/** Returns a [WildcardTypeName] that represents an unknown type that produces `outType`. */
fun TypeName.asProducerWildcardTypeName(): WildcardTypeName = WildcardTypeName.producerOf(this)

/** Returns a [WildcardTypeName] that represents an unknown type that produces `outType`. */
fun Type.asProducerWildcardTypeName(): WildcardTypeName = WildcardTypeName.producerOf(this)

/** Returns a [WildcardTypeName] that represents an unknown type that produces `outType`. */
fun KClass<*>.asProducerWildcardTypeName(): WildcardTypeName = WildcardTypeName.producerOf(this)

/** Returns a [WildcardTypeName] that represents an unknown type that produces `outType`. */
inline fun <reified T> asProducerWildcardTypeName(): WildcardTypeName = T::class.asProducerWildcardTypeName()

/** Returns a [WildcardTypeName] that represents an unknown type that consumes `inType`. */
fun TypeName.asConsumerWildcardTypeName(): WildcardTypeName = WildcardTypeName.consumerOf(this)

/** Returns a [WildcardTypeName] that represents an unknown type that consumes `inType`. */
fun Type.asConsumerWildcardTypeName(): WildcardTypeName = WildcardTypeName.consumerOf(this)

/** Returns a [WildcardTypeName] that represents an unknown type that consumes `inType`. */
fun KClass<*>.asConsumerWildcardTypeName(): WildcardTypeName = WildcardTypeName.consumerOf(this)

/** Returns a [WildcardTypeName] that represents an unknown type that consumes `inType`. */
inline fun <reified T> asConsumerWildcardTypeName(): WildcardTypeName = T::class.asConsumerWildcardTypeName()

/** Create a nullable coppy of [WildcardTypeName].  */
fun WildcardTypeName.asNullable(): WildcardTypeName = copy(true, emptyList(), emptyMap())
