@file:JvmMultifileClass
@file:JvmName("TypeNamesKt")

package com.hendraanggrian.kotlinpoet

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.LambdaTypeName
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.TypeName
import kotlin.reflect.KClass

inline fun LambdaTypeName.nullable(): LambdaTypeName = copy(nullable = true, suspending = false)

inline fun LambdaTypeName.suspending(): LambdaTypeName = copy(suspending = true)

fun LambdaTypeName.annotate(vararg annotations: AnnotationSpec): LambdaTypeName =
    copy(annotations = annotations.toList(), suspending = false)

/** Returns a lambda type with returnType and parameters listed in parameters. */
inline fun lambdaTypeNamed(vararg parameters: TypeName, returns: TypeName): LambdaTypeName =
    LambdaTypeName.get(null, *parameters, returnType = returns)

/** Returns a lambda type with returnType and parameters listed in parameters. */
inline fun lambdaTypeNamed(
    vararg parameters: ParameterSpec = emptyArray(),
    returns: TypeName,
): LambdaTypeName = LambdaTypeName.get(null, *parameters, returnType = returns)

/** Returns a lambda type with returnType and parameters listed in parameters. */
inline fun TypeName?.lambdaBy(vararg parameters: TypeName, returns: TypeName): LambdaTypeName =
    LambdaTypeName.get(this, *parameters, returnType = returns)

/** Returns a lambda type with returnType and parameters listed in parameters. */
fun TypeName?.lambdaBy(vararg parameters: Class<*>, returns: Class<*>): LambdaTypeName =
    LambdaTypeName.get(
        this,
        *parameters.map { it.name2 }.toTypedArray(),
        returnType = returns.name2,
    )

/** Returns a lambda type with returnType and parameters listed in parameters. */
fun TypeName?.lambdaBy(vararg parameters: KClass<*>, returns: KClass<*>): LambdaTypeName =
    LambdaTypeName.get(
        this,
        *parameters.map { it.name }.toTypedArray(),
        returnType = returns.name,
    )

/** Returns a lambda type with returnType and parameters listed in parameters. */
inline fun TypeName?.lambdaBy(
    vararg parameters: ParameterSpec = emptyArray(),
    returns: TypeName,
): LambdaTypeName = LambdaTypeName.get(this, *parameters, returnType = returns)

/** Returns a lambda type with returnType and parameters listed in parameters. */
inline fun TypeName?.lambdaBy(
    vararg parameters: ParameterSpec = emptyArray(),
    returns: Class<*>,
): LambdaTypeName = LambdaTypeName.get(this, *parameters, returnType = returns.name2)

/** Returns a lambda type with returnType and parameters listed in parameters. */
inline fun TypeName?.lambdaBy(
    vararg parameters: ParameterSpec = emptyArray(),
    returns: KClass<*>,
): LambdaTypeName = LambdaTypeName.get(this, *parameters, returnType = returns.name)
