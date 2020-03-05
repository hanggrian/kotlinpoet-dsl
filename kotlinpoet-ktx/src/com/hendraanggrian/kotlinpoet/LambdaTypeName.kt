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
fun TypeName?.lambdaBy(parameters: List<ParameterSpec>, returnType: TypeName): LambdaTypeName =
    LambdaTypeName.get(this, parameters, returnType)

/**
 * Returns a [LambdaTypeName] with optional [Type] receiver,
 * [ParameterSpec] list parameters and [Type] return type.
 */
fun Type?.lambdaBy(parameters: List<ParameterSpec>, returnType: Type): LambdaTypeName =
    LambdaTypeName.get(this?.asTypeName(), parameters, returnType.asTypeName())

/**
 * Returns a [LambdaTypeName] with optional [KClass] receiver,
 * [ParameterSpec] list parameters and [KClass] return type.
 */
fun KClass<*>?.lambdaBy(parameters: List<ParameterSpec>, returnType: KClass<*>): LambdaTypeName =
    LambdaTypeName.get(this?.asTypeName(), parameters, returnType.asTypeName())

/**
 * Returns a [LambdaTypeName] with optional [TypeName] receiver,
 * [TypeName] parameters and [TypeName] return type.
 */
fun TypeName?.lambdaBy(vararg parameters: TypeName, returnType: TypeName): LambdaTypeName =
    LambdaTypeName.get(this, *parameters, returnType = returnType)

/**
 * Returns a [LambdaTypeName] with optional [Type] receiver,
 * [Type] parameters and [Type] return type.
 */
fun Type?.lambdaBy(vararg parameters: Type, returnType: Type): LambdaTypeName =
    LambdaTypeName.get(
        this?.asTypeName(),
        *parameters.map { it.asTypeName() }.toTypedArray(),
        returnType = returnType.asTypeName()
    )

/**
 * Returns a [LambdaTypeName] with optional [KClass] receiver,
 * [KClass] parameters and [KClass] return type.
 */
fun KClass<*>?.lambdaBy(vararg parameters: KClass<*>, returnType: KClass<*>): LambdaTypeName =
    LambdaTypeName.get(
        this?.asTypeName(),
        *parameters.map { it.asTypeName() }.toTypedArray(),
        returnType = returnType.asTypeName()
    )

/**
 * Returns a [LambdaTypeName] with optional [TypeName] receiver,
 * [ParameterSpec] parameters and [TypeName] return type.
 */
fun TypeName?.lambdaBy(vararg parameters: ParameterSpec, returnType: TypeName): LambdaTypeName =
    LambdaTypeName.get(this, *parameters, returnType = returnType)

/**
 * Returns a [LambdaTypeName] with optional [Type] receiver,
 * [ParameterSpec] parameters and [Type] return type.
 */
fun Type?.lambdaBy(vararg parameters: ParameterSpec, returnType: Type): LambdaTypeName =
    LambdaTypeName.get(this?.asTypeName(), *parameters, returnType = returnType.asTypeName())

/**
 * Returns a [LambdaTypeName] with optional [KClass] receiver,
 * [ParameterSpec] parameters and [KClass] return type.
 */
fun KClass<*>?.lambdaBy(vararg parameters: ParameterSpec, returnType: KClass<*>): LambdaTypeName =
    LambdaTypeName.get(this?.asTypeName(), *parameters, returnType = returnType.asTypeName())
