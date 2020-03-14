@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.kotlinpoet

import com.squareup.kotlinpoet.LambdaTypeName
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asTypeName
import java.lang.reflect.Type
import kotlin.reflect.KClass

/**
 * Returns a [LambdaTypeName] with optional [TypeName] receiver,
 * [ParameterSpec] list parameters and [TypeName] return type.
 */
inline fun TypeName.lambdaBy2(parameters: List<ParameterSpec>, receiver: TypeName? = null): LambdaTypeName =
    LambdaTypeName.get(receiver, parameters, this)

/**
 * Returns a [LambdaTypeName] with optional [Type] receiver,
 * [ParameterSpec] list parameters and [Type] return type.
 */
fun Type.lambdaBy2(parameters: List<ParameterSpec>, receiver: Type? = null): LambdaTypeName =
    asTypeName().lambdaBy2(parameters, receiver?.asTypeName())

/**
 * Returns a [LambdaTypeName] with optional [KClass] receiver,
 * [ParameterSpec] list parameters and [KClass] return type.
 */
fun KClass<*>.lambdaBy2(parameters: List<ParameterSpec>, receiver: KClass<*>? = null): LambdaTypeName =
    asTypeName().lambdaBy2(parameters, receiver?.asTypeName())

/**
 * Returns a [LambdaTypeName] with optional [TypeName] receiver,
 * [TypeName] parameters and [TypeName] return type.
 */
inline fun TypeName.lambdaBy2(vararg parameters: TypeName, receiver: TypeName? = null): LambdaTypeName =
    LambdaTypeName.get(receiver, *parameters, returnType = this)

/**
 * Returns a [LambdaTypeName] with optional [Type] receiver,
 * [Type] parameters and [Type] return type.
 */
fun Type.lambdaBy2(vararg parameters: Type, receiver: Type? = null): LambdaTypeName =
    asTypeName().lambdaBy2(*parameters.map { it.asTypeName() }.toTypedArray(), receiver = receiver?.asTypeName())

/**
 * Returns a [LambdaTypeName] with optional [KClass] receiver,
 * [KClass] parameters and [KClass] return type.
 */
fun KClass<*>.lambdaBy2(vararg parameters: KClass<*>, receiver: KClass<*>? = null): LambdaTypeName =
    asTypeName().lambdaBy2(*parameters.map { it.asTypeName() }.toTypedArray(), receiver = receiver?.asTypeName())

/**
 * Returns a [LambdaTypeName] with optional [TypeName] receiver,
 * [ParameterSpec] parameters and [TypeName] return type.
 */
inline fun TypeName.lambdaBy2(
    vararg parameters: ParameterSpec = emptyArray(),
    receiver: TypeName? = null
): LambdaTypeName = LambdaTypeName.get(receiver, *parameters, returnType = this)

/**
 * Returns a [LambdaTypeName] with optional [Type] receiver,
 * [ParameterSpec] parameters and [Type] return type.
 */
fun Type.lambdaBy2(vararg parameters: ParameterSpec = emptyArray(), receiver: Type? = null): LambdaTypeName =
    asTypeName().lambdaBy2(*parameters, receiver = receiver?.asTypeName())

/**
 * Returns a [LambdaTypeName] with optional [KClass] receiver,
 * [ParameterSpec] parameters and [KClass] return type.
 */
fun KClass<*>.lambdaBy2(vararg parameters: ParameterSpec = emptyArray(), receiver: KClass<*>? = null): LambdaTypeName =
    asTypeName().lambdaBy2(*parameters, receiver = receiver?.asTypeName())
