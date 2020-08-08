package com.hendraanggrian.kotlinpoet.collections

import com.hendraanggrian.kotlinpoet.KotlinpoetDslMarker
import com.hendraanggrian.kotlinpoet.ParameterSpecBuilder
import com.hendraanggrian.kotlinpoet.buildParameterSpec
import com.hendraanggrian.kotlinpoet.parameterSpecOf
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.TypeName
import java.lang.reflect.Type
import kotlin.reflect.KClass

/** A [ParameterSpecList] is responsible for managing a set of parameter instances. */
open class ParameterSpecList internal constructor(actualList: MutableList<ParameterSpec>) :
    MutableList<ParameterSpec> by actualList {

    /** Add parameter from [type] and [name], returning the parameter added. */
    fun add(name: String, type: TypeName, vararg modifiers: KModifier): ParameterSpec =
        parameterSpecOf(name, type, *modifiers).also(::plusAssign)

    /** Add parameter from [type] and [name], returning the parameter added. */
    fun add(name: String, type: Type, vararg modifiers: KModifier): ParameterSpec =
        parameterSpecOf(name, type, *modifiers).also(::plusAssign)

    /** Add parameter from [type] and [name], returning the parameter added. */
    fun add(name: String, type: KClass<*>, vararg modifiers: KModifier): ParameterSpec =
        parameterSpecOf(name, type, *modifiers).also(::plusAssign)

    /** Add parameter from reified [T] and [name], returning the parameter added. */
    inline fun <reified T> add(name: String, vararg modifiers: KModifier): ParameterSpec =
        parameterSpecOf<T>(name, *modifiers).also(::plusAssign)

    /** Add parameter from [type] and [name] with custom initialization [builderAction], returning the parameter added. */
    inline fun add(
        name: String,
        type: TypeName,
        vararg modifiers: KModifier,
        builderAction: ParameterSpecBuilder.() -> Unit
    ): ParameterSpec = buildParameterSpec(name, type, *modifiers, builderAction = builderAction).also(::plusAssign)

    /** Add parameter from [type] and [name] with custom initialization [builderAction], returning the parameter added. */
    inline fun add(
        name: String,
        type: Type,
        vararg modifiers: KModifier,
        builderAction: ParameterSpecBuilder.() -> Unit
    ): ParameterSpec = buildParameterSpec(name, type, *modifiers, builderAction = builderAction).also(::plusAssign)

    /** Add parameter from [type] and [name] with custom initialization [builderAction], returning the parameter added. */
    inline fun add(
        name: String,
        type: KClass<*>,
        vararg modifiers: KModifier,
        builderAction: ParameterSpecBuilder.() -> Unit
    ): ParameterSpec = buildParameterSpec(name, type, *modifiers, builderAction = builderAction).also(::plusAssign)

    /** Add parameter from reified [T] and [name] with custom initialization [builderAction], returning the parameter added. */
    inline fun <reified T> add(
        name: String,
        vararg modifiers: KModifier,
        builderAction: ParameterSpecBuilder.() -> Unit
    ): ParameterSpec = buildParameterSpec<T>(name, *modifiers, builderAction = builderAction).also(::plusAssign)

    /** Convenient method to add parameter with operator function. */
    operator fun set(name: String, type: TypeName): Unit = plusAssign(parameterSpecOf(name, type))

    /** Convenient method to add parameter with operator function. */
    operator fun set(name: String, type: Type): Unit = plusAssign(parameterSpecOf(name, type))

    /** Convenient method to add parameter with operator function. */
    operator fun set(name: String, type: KClass<*>): Unit = plusAssign(parameterSpecOf(name, type))
}

/** Receiver for the `parameters` block providing an extended set of operators for the configuration. */
@KotlinpoetDslMarker
class ParameterSpecListScope(actualList: MutableList<ParameterSpec>) : ParameterSpecList(actualList) {

    /** Convenient method to add parameter with receiver type. */
    inline operator fun String.invoke(
        type: TypeName,
        vararg modifiers: KModifier,
        builderAction: ParameterSpecBuilder.() -> Unit
    ): ParameterSpec = add(this, type, *modifiers, builderAction = builderAction)

    /** Convenient method to add parameter with receiver type. */
    inline operator fun String.invoke(
        type: Type,
        vararg modifiers: KModifier,
        builderAction: ParameterSpecBuilder.() -> Unit
    ): ParameterSpec = add(this, type, *modifiers, builderAction = builderAction)

    /** Convenient method to add parameter with receiver type. */
    inline operator fun String.invoke(
        type: KClass<*>,
        vararg modifiers: KModifier,
        builderAction: ParameterSpecBuilder.() -> Unit
    ): ParameterSpec = add(this, type, *modifiers, builderAction = builderAction)

    /** Convenient method to add parameter with receiver type. */
    inline operator fun <reified T> String.invoke(
        vararg modifiers: KModifier,
        builderAction: ParameterSpecBuilder.() -> Unit
    ): ParameterSpec = add<T>(this, *modifiers, builderAction = builderAction)
}
