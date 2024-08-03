@file:JvmMultifileClass
@file:JvmName("TypeNamesKt")

package com.hanggrian.kotlinpoet

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.WildcardTypeName
import kotlin.reflect.KClass

public inline fun WildcardTypeName.nullable(
    tags: Map<KClass<*>, Any> = emptyMap(),
): WildcardTypeName = copy(nullable = true, tags = tags)

public fun WildcardTypeName.annotate(
    vararg annotations: AnnotationSpec,
    tags: Map<KClass<*>, Any> = emptyMap(),
): WildcardTypeName = copy(annotations = annotations.toList(), tags = tags)

/** Returns a type that represents an unknown type that produces outType. */
public inline val TypeName.producer: WildcardTypeName get() = WildcardTypeName.producerOf(this)

/** Returns a type that represents an unknown type that consumes inType. */
public inline val TypeName.consumer: WildcardTypeName get() = WildcardTypeName.consumerOf(this)
