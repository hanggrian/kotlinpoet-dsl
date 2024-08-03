@file:JvmMultifileClass
@file:JvmName("TypeNamesKt")

package com.hanggrian.kotlinpoet

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeVariableName
import java.lang.reflect.Type
import kotlin.reflect.KClass

public inline fun TypeVariableName.nullable(
    tags: Map<KClass<*>, Any> = emptyMap(),
): TypeVariableName = copy(nullable = true, tags = tags)

public fun TypeVariableName.annotate(
    vararg annotations: AnnotationSpec,
    tags: Map<KClass<*>, Any> = emptyMap(),
): TypeVariableName = copy(annotations = annotations.toList(), tags = tags)

/** Returns type variable named name without bounds. */
public inline val String.generics: TypeVariableName get() = TypeVariableName(this)

/** Returns type variable named name with variance and bounds. */
public inline fun String.genericsBy(
    vararg bounds: TypeName,
    variance: KModifier? = null,
): TypeVariableName = TypeVariableName(this, *bounds, variance = variance)

/** Returns type variable named name with variance and bounds. */
public inline fun String.genericsBy(
    vararg bounds: Type,
    variance: KModifier? = null,
): TypeVariableName = TypeVariableName(this, *bounds, variance = variance)

/** Returns type variable named name with variance and bounds. */
public inline fun String.genericsBy(
    vararg bounds: KClass<*>,
    variance: KModifier? = null,
): TypeVariableName = TypeVariableName(this, *bounds, variance = variance)
