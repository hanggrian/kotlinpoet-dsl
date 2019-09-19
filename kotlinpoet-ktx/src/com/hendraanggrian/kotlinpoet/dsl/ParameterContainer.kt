package com.hendraanggrian.kotlinpoet.dsl

import com.hendraanggrian.kotlinpoet.KotlinpoetDslMarker
import com.hendraanggrian.kotlinpoet.ParameterSpecBuilder
import com.hendraanggrian.kotlinpoet.buildParameter
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.TypeName
import kotlin.reflect.KClass

private interface ParameterAddable {

    /** Add parameter to this container, returning the parameter added. */
    fun add(spec: ParameterSpec): ParameterSpec
}

/** A [ParameterContainer] is responsible for managing a set of parameter instances. */
abstract class ParameterContainer internal constructor() : ParameterAddable {

    /** Add parameter from [type] and [name], returning the parameter added. */
    fun add(name: String, type: TypeName, vararg modifiers: KModifier): ParameterSpec =
        add(buildParameter(name, type, *modifiers))

    /** Add parameter from [type] and [name] with custom initialization [builderAction], returning the parameter added. */
    inline fun add(
        name: String,
        type: TypeName,
        vararg modifiers: KModifier,
        builderAction: ParameterSpecBuilder.() -> Unit
    ): ParameterSpec = add(buildParameter(name, type, *modifiers, builderAction = builderAction))

    /** Add parameter from [type] and [name], returning the parameter added. */
    fun add(name: String, type: Class<*>, vararg modifiers: KModifier): ParameterSpec =
        add(buildParameter(name, type, *modifiers))

    /** Add parameter from [type] and [name] with custom initialization [builderAction], returning the parameter added. */
    inline fun add(
        name: String,
        type: Class<*>,
        vararg modifiers: KModifier,
        builderAction: ParameterSpecBuilder.() -> Unit
    ): ParameterSpec = add(buildParameter(name, type, *modifiers, builderAction = builderAction))

    /** Add parameter from [type] and [name], returning the parameter added. */
    fun add(name: String, type: KClass<*>, vararg modifiers: KModifier): ParameterSpec =
        add(buildParameter(name, type, *modifiers))

    /** Add parameter from [type] and [name] with custom initialization [builderAction], returning the parameter added. */
    inline fun add(
        name: String,
        type: KClass<*>,
        vararg modifiers: KModifier,
        builderAction: ParameterSpecBuilder.() -> Unit
    ): ParameterSpec = add(buildParameter(name, type, *modifiers, builderAction = builderAction))

    /** Add parameter from reified [T] and [name], returning the parameter added. */
    inline fun <reified T> add(name: String, vararg modifiers: KModifier): ParameterSpec =
        add(buildParameter<T>(name, *modifiers))

    /** Add parameter from reified [T] and [name] with custom initialization [builderAction], returning the parameter added. */
    inline fun <reified T> add(
        name: String,
        vararg modifiers: KModifier,
        builderAction: ParameterSpecBuilder.() -> Unit
    ): ParameterSpec = add(buildParameter<T>(name, *modifiers, builderAction = builderAction))

    /** Convenient method to add parameter with operator function. */
    operator fun plusAssign(spec: ParameterSpec) {
        add(spec)
    }

    /** Convenient method to add parameter with operator function. */
    operator fun set(name: String, type: TypeName) {
        add(name, type)
    }

    /** Convenient method to add parameter with operator function. */
    operator fun set(name: String, type: Class<*>) {
        add(name, type)
    }

    /** Convenient method to add parameter with operator function. */
    operator fun set(name: String, type: KClass<*>) {
        add(name, type)
    }

    /** Configure this container with DSL. */
    inline operator fun invoke(configuration: ParameterContainerScope.() -> Unit) =
        ParameterContainerScope(this).configuration()
}

/** Receiver for the `parameters` block providing an extended set of operators for the configuration. */
@KotlinpoetDslMarker
class ParameterContainerScope @PublishedApi internal constructor(container: ParameterContainer) :
    ParameterContainer(), ParameterAddable by container {

    /** Convenient method to add parameter with receiver type. */
    inline operator fun String.invoke(
        type: TypeName,
        vararg modifiers: KModifier,
        builderAction: ParameterSpecBuilder.() -> Unit
    ): ParameterSpec = add(this, type, *modifiers, builderAction = builderAction)

    /** Convenient method to add parameter with receiver type. */
    inline operator fun String.invoke(
        type: Class<*>,
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
