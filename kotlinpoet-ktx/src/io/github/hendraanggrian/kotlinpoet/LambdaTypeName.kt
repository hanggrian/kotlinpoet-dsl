@file:Suppress("NOTHING_TO_INLINE")

package io.github.hendraanggrian.kotlinpoet

import com.squareup.kotlinpoet.LambdaTypeName
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asTypeName
import java.lang.reflect.Type
import kotlin.reflect.KClass

/**
 * Returns a [LambdaTypeName] with [TypeName] return type,
 * [ParameterSpec] list parameters and optional [TypeName] receiver.
 */
inline fun TypeName.lambdaBy(parameters: List<ParameterSpec>, receiver: TypeName? = null): LambdaTypeName =
    LambdaTypeName.get(receiver, parameters, this)

/**
 * Returns a [LambdaTypeName] with [Type] return type,
 * [ParameterSpec] list parameters and optional [Type] receiver.
 */
fun Type.lambdaBy(parameters: List<ParameterSpec>, receiver: Type? = null): LambdaTypeName =
    asTypeName().lambdaBy(parameters, receiver?.asTypeName())

/**
 * Returns a [LambdaTypeName] with [KClass] return type,
 * [ParameterSpec] list parameters and optional [KClass] receiver.
 */
fun KClass<*>.lambdaBy(parameters: List<ParameterSpec>, receiver: KClass<*>? = null): LambdaTypeName =
    asTypeName().lambdaBy(parameters, receiver?.asTypeName())

/**
 * Returns a [LambdaTypeName] with [TypeName] return type,
 * [TypeName] parameters and optional [TypeName] receiver.
 */
inline fun TypeName.lambdaBy(vararg parameters: TypeName, receiver: TypeName? = null): LambdaTypeName =
    LambdaTypeName.get(receiver, *parameters, returnType = this)

/**
 * Returns a [LambdaTypeName] with [Type] return type,
 * [Type] parameters and optional [Type] receiver.
 */
fun Type.lambdaBy(vararg parameters: Type, receiver: Type? = null): LambdaTypeName =
    asTypeName().lambdaBy(*parameters.map { it.asTypeName() }.toTypedArray(), receiver = receiver?.asTypeName())

/**
 * Returns a [LambdaTypeName] with [KClass] return type,
 * [KClass] parameters and optional [KClass] receiver.
 */
fun KClass<*>.lambdaBy(vararg parameters: KClass<*>, receiver: KClass<*>? = null): LambdaTypeName =
    asTypeName().lambdaBy(*parameters.map { it.asTypeName() }.toTypedArray(), receiver = receiver?.asTypeName())

/**
 * Returns a [LambdaTypeName] with [TypeName] return type,
 * [ParameterSpec] parameters and optional [TypeName] receiver.
 */
inline fun TypeName.lambdaBy(
    vararg parameters: ParameterSpec = emptyArray(),
    receiver: TypeName? = null
): LambdaTypeName = LambdaTypeName.get(receiver, *parameters, returnType = this)

/**
 * Returns a [LambdaTypeName] with [Type] return type,
 * [ParameterSpec] parameters and optional [Type] receiver.
 */
fun Type.lambdaBy(vararg parameters: ParameterSpec = emptyArray(), receiver: Type? = null): LambdaTypeName =
    asTypeName().lambdaBy(*parameters, receiver = receiver?.asTypeName())

/**
 * Returns a [LambdaTypeName] with [KClass] return type,
 * [ParameterSpec] parameters and optional [KClass] receiver.
 */
fun KClass<*>.lambdaBy(vararg parameters: ParameterSpec = emptyArray(), receiver: KClass<*>? = null): LambdaTypeName =
    asTypeName().lambdaBy(*parameters, receiver = receiver?.asTypeName())
