package com.hendraanggrian.kotlinpoet

import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeVariableName
import java.lang.reflect.Type
import kotlin.reflect.KClass

/** Returns a [TypeVariableName] without bounds. */
fun String.typeVariableBy(variance: KModifier? = null): TypeVariableName =
    TypeVariableName(this, variance)

/** Returns a [TypeVariableName] with [TypeName] bounds. */
fun String.typeVariableBy(vararg bounds: TypeName, variance: KModifier? = null): TypeVariableName =
    TypeVariableName(this, *bounds, variance = variance)

/** Returns a [TypeVariableName] with [Type] bounds. */
fun String.typeVariableBy(vararg bounds: Type, variance: KModifier? = null): TypeVariableName =
    TypeVariableName(this, *bounds, variance = variance)

/** Returns a [TypeVariableName] with [KClass] bounds. */
fun String.typeVariableBy(vararg bounds: KClass<*>, variance: KModifier? = null): TypeVariableName =
    TypeVariableName(this, *bounds, variance = variance)
