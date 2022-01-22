package com.hendraanggrian.kotlinpoet

import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.WildcardTypeName
import java.lang.reflect.Type
import kotlin.reflect.KClass

/** Returns a [WildcardTypeName] that represents an unknown type that produces [TypeName]. */
inline fun TypeName.wildcardProducerOf(): WildcardTypeName = WildcardTypeName.producerOf(this)

/** Returns a [WildcardTypeName] that represents an unknown type that produces [Type]. */
inline fun Type.wildcardProducerOf(): WildcardTypeName = WildcardTypeName.producerOf(this)

/** Returns a [WildcardTypeName] that represents an unknown type that produces [KClass]. */
inline fun KClass<*>.wildcardProducerOf(): WildcardTypeName = WildcardTypeName.producerOf(this)

/** Returns a [WildcardTypeName] that represents an unknown type that consumes [TypeName]. */
inline fun TypeName.wildcardConsumerOf(): WildcardTypeName = WildcardTypeName.consumerOf(this)

/** Returns a [WildcardTypeName] that represents an unknown type that consumes [Type]. */
inline fun Type.wildcardConsumerOf(): WildcardTypeName = WildcardTypeName.consumerOf(this)

/** Returns a [WildcardTypeName] that represents an unknown type that consumes [KClass]. */
inline fun KClass<*>.wildcardConsumerOf(): WildcardTypeName = WildcardTypeName.consumerOf(this)
