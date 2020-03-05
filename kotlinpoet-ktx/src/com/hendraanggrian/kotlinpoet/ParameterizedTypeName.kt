package com.hendraanggrian.kotlinpoet

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ParameterizedTypeName
import com.squareup.kotlinpoet.TypeName
import java.lang.reflect.Type
import kotlin.reflect.KClass

/**
 * Returns a [ParameterizedTypeName] applying [KClass] to [T].
 * @see com.squareup.kotlinpoet.typeNameOf
 */
inline fun <reified T> parameterizedTypeNameOf(vararg typeArguments: KClass<*>): ParameterizedTypeName =
    T::class.parameterizedBy(*typeArguments)

/**
 * Returns a [ParameterizedTypeName] applying [TypeName] arguments to [ClassName].
 * @see ParameterizedTypeName.parameterizedBy
 */
fun ClassName.parameterizedBy(vararg typeArguments: TypeName): ParameterizedTypeName =
    ParameterizedTypeName.run { parameterizedBy(*typeArguments) }

/**
 * Returns a [ParameterizedTypeName] applying [Type] arguments to [Class].
 * @see ParameterizedTypeName.parameterizedBy
 */
fun Class<*>.parameterizedBy(vararg typeArguments: Type): ParameterizedTypeName =
    ParameterizedTypeName.run { parameterizedBy(*typeArguments) }

/**
 * Returns a [ParameterizedTypeName] applying [KClass] arguments to [KClass].
 * @see ParameterizedTypeName.parameterizedBy
 */
fun KClass<*>.parameterizedBy(vararg typeArguments: KClass<*>): ParameterizedTypeName =
    ParameterizedTypeName.run { parameterizedBy(*typeArguments) }
