package com.hendraanggrian.kotlinpoet

import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeVariableName
import java.lang.reflect.Type
import kotlin.reflect.KClass

/** Returns a [TypeVariableName] without bounds. */
inline fun String.genericsBy(variance: KModifier? = null): TypeVariableName = TypeVariableName(this, variance)

/** Returns a [TypeVariableName] with [TypeName] bounds. */
inline fun String.genericsBy(vararg bounds: TypeName, variance: KModifier? = null): TypeVariableName =
    TypeVariableName(this, *bounds, variance = variance)

/** Returns a [TypeVariableName] with [Type] bounds. */
inline fun String.genericsBy(vararg bounds: Type, variance: KModifier? = null): TypeVariableName =
    TypeVariableName(this, *bounds, variance = variance)

/** Returns a [TypeVariableName] with [KClass] bounds. */
inline fun String.genericsBy(vararg bounds: KClass<*>, variance: KModifier? = null): TypeVariableName =
    TypeVariableName(this, *bounds, variance = variance)

/** Returns a [TypeVariableName] with collection of [TypeName] bounds. */
inline fun String.genericsBy(bounds: List<TypeName>, variance: KModifier? = null): TypeVariableName =
    TypeVariableName(this, bounds, variance)

/** Returns a [TypeVariableName] with collection of [Type] bounds. */
@JvmName("genericsWithTypes")
inline fun String.genericsBy(bounds: Iterable<Type>, variance: KModifier? = null): TypeVariableName =
    TypeVariableName(this, bounds, variance)

/** Returns a [TypeVariableName] with collection of [KClass] bounds. */
@JvmName("genericsWithClasses")
inline fun String.genericsBy(bounds: Iterable<KClass<*>>, variance: KModifier? = null): TypeVariableName =
    TypeVariableName(this, bounds, variance)
