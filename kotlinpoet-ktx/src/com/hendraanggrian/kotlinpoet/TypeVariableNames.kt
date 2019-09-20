package com.hendraanggrian.kotlinpoet

import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeVariableName
import java.lang.reflect.Type
import kotlin.reflect.KClass

/** Returns a [TypeVariableName] named [name] without bounds. */
fun typeVariableNameOf(name: String, variance: KModifier? = null): TypeVariableName =
    TypeVariableName(name, variance)

/** Returns a [TypeVariableName] named [name] with [bounds]. */
fun typeVariableNameOf(name: String, vararg bounds: TypeName, variance: KModifier? = null): TypeVariableName =
    TypeVariableName(name, *bounds, variance = variance)

/** Returns a [TypeVariableName] named [name] with [bounds]. */
fun typeVariableNameOf(name: String, vararg bounds: Type, variance: KModifier? = null): TypeVariableName =
    TypeVariableName(name, *bounds, variance = variance)

/** Returns a [TypeVariableName] named [name] with [bounds]. */
fun typeVariableNameOf(name: String, vararg bounds: KClass<*>, variance: KModifier? = null): TypeVariableName =
    TypeVariableName(name, *bounds, variance = variance)
