@file:Suppress("NOTHING_TO_INLINE")

package io.github.hendraanggrian.kotlinpoet

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ParameterizedTypeName
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asClassName
import com.squareup.kotlinpoet.asTypeName
import java.lang.reflect.Type
import kotlin.reflect.KClass

/**
 * Returns a [ParameterizedTypeName] applying [TypeName] arguments to [ClassName].
 *
 * @see ParameterizedTypeName.parameterizedBy
 */
inline fun ClassName.parameterizedBy(vararg typeArguments: TypeName): ParameterizedTypeName =
    ParameterizedTypeName.run { parameterizedBy(*typeArguments) }

/** Returns a [ParameterizedTypeName] applying [Type] arguments to [ClassName]. */
fun ClassName.parameterizedBy(vararg typeArguments: Type = emptyArray()): ParameterizedTypeName =
    parameterizedBy(*typeArguments.map { it.asTypeName() }.toTypedArray())

/** Returns a [ParameterizedTypeName] applying [KClass] arguments to [ClassName]. */
fun ClassName.parameterizedBy(vararg typeArguments: KClass<*> = emptyArray()): ParameterizedTypeName =
    parameterizedBy(*typeArguments.map { it.asTypeName() }.toTypedArray())

/**
 * Returns a [ParameterizedTypeName] applying [Type] arguments to [Class].
 *
 * @see ParameterizedTypeName.parameterizedBy
 */
inline fun Class<*>.parameterizedBy(vararg typeArguments: Type): ParameterizedTypeName =
    ParameterizedTypeName.run { parameterizedBy(*typeArguments) }

/** Returns a [ParameterizedTypeName] applying [TypeName] arguments to [Class]. */
fun Class<*>.parameterizedBy(vararg typeArguments: TypeName = emptyArray()): ParameterizedTypeName =
    asClassName().parameterizedBy(*typeArguments)

/** Returns a [ParameterizedTypeName] applying [KClass] arguments to [Class]. */
fun Class<*>.parameterizedBy(vararg typeArguments: KClass<*> = emptyArray()): ParameterizedTypeName =
    asClassName().parameterizedBy(*typeArguments)

/** Returns a [ParameterizedTypeName] applying [T] argument to [Class]. */
inline fun <reified T> Class<*>.parameterizedBy(): ParameterizedTypeName =
    asClassName().parameterizedBy(T::class)

/**
 * Returns a [ParameterizedTypeName] applying [KClass] arguments to [KClass].
 *
 * @see ParameterizedTypeName.parameterizedBy
 */
inline fun KClass<*>.parameterizedBy(vararg typeArguments: KClass<*>): ParameterizedTypeName =
    ParameterizedTypeName.run { parameterizedBy(*typeArguments) }

/** Returns a [ParameterizedTypeName] applying [Type] arguments to [KClass]. */
fun KClass<*>.parameterizedBy(vararg typeArguments: Type = emptyArray()): ParameterizedTypeName =
    asClassName().parameterizedBy(*typeArguments)

/** Returns a [ParameterizedTypeName] applying [TypeName] arguments to [KClass]. */
fun KClass<*>.parameterizedBy(vararg typeArguments: TypeName = emptyArray()): ParameterizedTypeName =
    asClassName().parameterizedBy(*typeArguments)

/**
 * Returns a [ParameterizedTypeName] applying [TypeName] argument list to [ClassName].
 *
 * @see ParameterizedTypeName.parameterizedBy
 */
inline fun ClassName.parameterizedBy(typeArguments: List<TypeName>): ParameterizedTypeName =
    ParameterizedTypeName.run { parameterizedBy(typeArguments) }

/**
 * Returns a [ParameterizedTypeName] applying [Type] argument list to [Class].
 *
 * @see ParameterizedTypeName.parameterizedBy
 */
inline fun Class<*>.parameterizedBy(typeArguments: Iterable<Type>): ParameterizedTypeName =
    ParameterizedTypeName.run { parameterizedBy(typeArguments) }

/**
 * Returns a [ParameterizedTypeName] applying [KClass] argument list to [KClass].
 *
 * @see ParameterizedTypeName.parameterizedBy
 */
inline fun KClass<*>.parameterizedBy(typeArguments: Iterable<KClass<*>>): ParameterizedTypeName =
    ParameterizedTypeName.run { parameterizedBy(typeArguments) }
