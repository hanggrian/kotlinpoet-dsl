package com.hendraanggrian.kotlinpoet

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ParameterizedTypeName
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asClassName
import java.lang.reflect.Type
import kotlin.reflect.KClass

/**
 * Returns a [ParameterizedTypeName] applying [TypeName] arguments to [ClassName].
 * @see ParameterizedTypeName.parameterizedBy
 */
fun ClassName.parameterizedBy(vararg typeArguments: TypeName): ParameterizedTypeName =
    ParameterizedTypeName.run { parameterizedBy(*typeArguments) }

/**
 * Returns a [ParameterizedTypeName] applying [T] argument to [KClass].
 * @see ClassName.parameterizedBy
 */
inline fun <reified T> ClassName.parameterizedBy(): ParameterizedTypeName = parameterizedBy(T::class.asClassName())

/**
 * Returns a [ParameterizedTypeName] applying [Type] arguments to [Class].
 * @see ParameterizedTypeName.parameterizedBy
 */
fun Class<*>.parameterizedBy(vararg typeArguments: Type): ParameterizedTypeName =
    ParameterizedTypeName.run { parameterizedBy(*typeArguments) }

/**
 * Returns a [ParameterizedTypeName] applying [T] argument to [Class].
 * @see Class.parameterizedBy
 */
inline fun <reified T> Class<*>.parameterizedBy(): ParameterizedTypeName = parameterizedBy(T::class.java)

/**
 * Returns a [ParameterizedTypeName] applying [KClass] arguments to [KClass].
 * @see ParameterizedTypeName.parameterizedBy
 */
fun KClass<*>.parameterizedBy(vararg typeArguments: KClass<*>): ParameterizedTypeName =
    ParameterizedTypeName.run { parameterizedBy(*typeArguments) }

/**
 * Returns a [ParameterizedTypeName] applying [T] argument to [KClass].
 * @see KClass.parameterizedBy
 */
inline fun <reified T> KClass<*>.parameterizedBy(): ParameterizedTypeName = parameterizedBy(T::class)
