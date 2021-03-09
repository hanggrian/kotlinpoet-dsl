@file:Suppress("NOTHING_TO_INLINE")

package io.github.hendraanggrian.kotlinpoet

import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeVariableName
import java.lang.reflect.Type
import kotlin.reflect.KClass

/** Returns a [TypeVariableName] without bounds. */
inline fun String.typeVarOf(variance: KModifier? = null): TypeVariableName = TypeVariableName(this, variance)

/** Returns a [TypeVariableName] with [TypeName] bounds. */
inline fun String.typeVarBy(vararg bounds: TypeName, variance: KModifier? = null): TypeVariableName =
    TypeVariableName(this, *bounds, variance = variance)

/** Returns a [TypeVariableName] with [Type] bounds. */
inline fun String.typeVarBy(vararg bounds: Type, variance: KModifier? = null): TypeVariableName =
    TypeVariableName(this, *bounds, variance = variance)

/** Returns a [TypeVariableName] with [KClass] bounds. */
inline fun String.typeVarBy(vararg bounds: KClass<*>, variance: KModifier? = null): TypeVariableName =
    TypeVariableName(this, *bounds, variance = variance)
