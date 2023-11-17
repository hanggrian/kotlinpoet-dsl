@file:JvmMultifileClass
@file:JvmName("TypeNamesKt")

package com.hendraanggrian.kotlinpoet

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ParameterizedTypeName
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import kotlin.reflect.KClass

public inline fun ParameterizedTypeName.nullable(
    tags: Map<KClass<*>, Any> = emptyMap(),
): ParameterizedTypeName = copy(nullable = true, tags = tags)

public fun ParameterizedTypeName.annotate(
    vararg annotations: AnnotationSpec,
    tags: Map<KClass<*>, Any> = emptyMap(),
): ParameterizedTypeName = copy(annotations = annotations.toList(), tags = tags)

/** Returns a parameterized type, applying typeArguments to rawType. */
public inline fun <reified T> ClassName.parameterizedBy(): ParameterizedTypeName =
    parameterizedBy(T::class.name)
