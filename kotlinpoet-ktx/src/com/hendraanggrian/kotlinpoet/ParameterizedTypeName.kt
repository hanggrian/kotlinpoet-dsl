package com.hendraanggrian.kotlinpoet

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ParameterizedTypeName
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.asClassName
import kotlin.reflect.KClass

/** Returns a [ParameterizedTypeName] applying [T] argument to [ClassName]. */
inline fun <reified T> ClassName.plusParameter(): ParameterizedTypeName =
    parameterizedBy(T::class.asClassName())

/** Returns a [ParameterizedTypeName] applying [T] argument to [Class]. */
inline fun <reified T> Class<*>.plusParameter(): ParameterizedTypeName =
    parameterizedBy(T::class.java)

/** Returns a [ParameterizedTypeName] applying [T] argument to [KClass]. */
inline fun <reified T> KClass<*>.plusParameter(): ParameterizedTypeName =
    parameterizedBy(T::class)
