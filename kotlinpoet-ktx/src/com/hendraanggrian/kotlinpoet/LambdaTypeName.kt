package com.hendraanggrian.kotlinpoet

import com.squareup.kotlinpoet.LambdaTypeName
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asTypeName
import java.lang.reflect.Type
import kotlin.reflect.KClass

fun TypeName?.lambdaBy(parameters: List<ParameterSpec>, returnType: TypeName): LambdaTypeName =
    LambdaTypeName.get(this, parameters, returnType)

fun Type?.lambdaBy(parameters: List<ParameterSpec>, returnType: Type): LambdaTypeName =
    LambdaTypeName.get(this?.asTypeName(), parameters, returnType.asTypeName())

fun KClass<*>?.lambdaBy(parameters: List<ParameterSpec>, returnType: KClass<*>): LambdaTypeName =
    LambdaTypeName.get(this?.asTypeName(), parameters, returnType.asTypeName())

fun TypeName?.lambdaBy(vararg parameters: TypeName, returnType: TypeName): LambdaTypeName =
    LambdaTypeName.get(this, *parameters, returnType = returnType)

fun Type?.lambdaBy(vararg parameters: Type, returnType: Type): LambdaTypeName =
    LambdaTypeName.get(
        this?.asTypeName(),
        *parameters.map { it.asTypeName() }.toTypedArray(),
        returnType = returnType.asTypeName()
    )

fun KClass<*>?.lambdaBy(vararg parameters: KClass<*>, returnType: KClass<*>): LambdaTypeName =
    LambdaTypeName.get(
        this?.asTypeName(),
        *parameters.map { it.asTypeName() }.toTypedArray(),
        returnType = returnType.asTypeName()
    )

fun TypeName?.lambdaBy(vararg parameters: ParameterSpec, returnType: TypeName): LambdaTypeName =
    LambdaTypeName.get(this, *parameters, returnType = returnType)

fun Type?.lambdaBy(vararg parameters: ParameterSpec, returnType: Type): LambdaTypeName =
    LambdaTypeName.get(this?.asTypeName(), *parameters, returnType = returnType.asTypeName())

fun KClass<*>?.lambdaBy(vararg parameters: ParameterSpec, returnType: KClass<*>): LambdaTypeName =
    LambdaTypeName.get(this?.asTypeName(), *parameters, returnType = returnType.asTypeName())
