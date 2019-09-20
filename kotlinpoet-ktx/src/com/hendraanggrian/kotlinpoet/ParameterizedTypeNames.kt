package com.hendraanggrian.kotlinpoet

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ParameterizedTypeName
import com.squareup.kotlinpoet.TypeName
import java.lang.reflect.Type
import kotlin.reflect.KClass

/** Returns a parameterized type, applying `typeArguments` to `this`.  */
fun ClassName.parameterizedBy(vararg typeArguments: TypeName): ParameterizedTypeName =
    ParameterizedTypeName.run { parameterizedBy(*typeArguments) }

/** Returns a parameterized type, applying `typeArguments` to `this`.  */
fun Class<*>.parameterizedBy(vararg typeArguments: Type): ParameterizedTypeName =
    ParameterizedTypeName.run { parameterizedBy(*typeArguments) }

/** Returns a parameterized type, applying `typeArguments` to `this`.  */
fun KClass<*>.parameterizedBy(vararg typeArguments: KClass<*>): ParameterizedTypeName =
    ParameterizedTypeName.run { parameterizedBy(*typeArguments) }

/** Returns a parameterized type, applying `typeArgument` to `this`.  */
operator fun ClassName.plus(typeArgument: TypeName): ParameterizedTypeName =
    ParameterizedTypeName.run { plusParameter(typeArgument) }

/** Returns a parameterized type, applying `typeArgument` to `this`.  */
operator fun Class<*>.plus(typeArgument: Class<*>): ParameterizedTypeName =
    ParameterizedTypeName.run { plusParameter(typeArgument) }

/** Returns a parameterized type, applying `typeArgument` to `this`.  */
operator fun KClass<*>.plus(typeArgument: KClass<*>): ParameterizedTypeName =
    ParameterizedTypeName.run { plusParameter(typeArgument) }
