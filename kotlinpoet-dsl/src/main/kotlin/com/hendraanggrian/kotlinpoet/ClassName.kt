@file:JvmMultifileClass
@file:JvmName("TypeNamesKt")

package com.hendraanggrian.kotlinpoet

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.DelicateKotlinPoetApi
import com.squareup.kotlinpoet.asClassName
import kotlin.reflect.KClass

inline fun ClassName.nullable(tags: Map<KClass<*>, Any> = emptyMap()): ClassName =
    copy(nullable = true, tags = tags)

fun ClassName.annotate(
    vararg annotations: AnnotationSpec,
    tags: Map<KClass<*>, Any> = emptyMap(),
): ClassName = copy(annotations = annotations.toList(), tags = tags)

@DelicateKotlinPoetApi(DELICATE_API)
inline val Class<*>.name2: ClassName get() = asClassName()

inline val KClass<*>.name: ClassName get() = asClassName()

@DelicateKotlinPoetApi(DELICATE_API)
inline val KClass<*>.javaName: ClassName get() = java.asClassName()

/** Returns a new class name instance for the given fully-qualified class name string. */
inline fun classNamed(fullName: String): ClassName = ClassName.bestGuess(fullName)

/** Returns a class name created from the given parts. */
inline fun classNamed(
    packageName: String,
    simpleName: String,
    vararg simpleNames: String,
): ClassName = ClassName(packageName, simpleName, *simpleNames)
