package com.hendraanggrian.kotlinpoet

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ParameterizedTypeName
import com.squareup.kotlinpoet.TypeName
import java.lang.reflect.Type
import kotlin.reflect.KClass

/** Returns a [ParameterizedTypeName] applying [TypeName] arguments to [ClassName]. */
fun ClassName.parameterizedBy(vararg typeArguments: TypeName): ParameterizedTypeName =
    ParameterizedTypeName.run { parameterizedBy(*typeArguments) }

/** Returns a [ParameterizedTypeName] applying [Type] arguments to [Class]. */
fun Class<*>.parameterizedBy(vararg typeArguments: Type): ParameterizedTypeName =
    ParameterizedTypeName.run { parameterizedBy(*typeArguments) }

/** Returns a [ParameterizedTypeName] applying [KClass] arguments to [KClass]. */
fun KClass<*>.parameterizedBy(vararg typeArguments: KClass<*>): ParameterizedTypeName =
    ParameterizedTypeName.run { parameterizedBy(*typeArguments) }
