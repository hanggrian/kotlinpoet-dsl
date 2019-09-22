package com.hendraanggrian.kotlinpoet.dsl

import com.hendraanggrian.kotlinpoet.KotlinpoetDslMarker
import com.hendraanggrian.kotlinpoet.PropertySpecBuilder
import com.hendraanggrian.kotlinpoet.buildProperty
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeName
import java.lang.reflect.Type
import kotlin.reflect.KClass

private interface PropertyAddable {

    /** Add field to this container, returning the field added. */
    fun add(spec: PropertySpec): PropertySpec
}

/** A [PropertyContainer] is responsible for managing a set of field instances. */
abstract class PropertyContainer internal constructor() : PropertyAddable {

    /** Add field from [type] and [name], returning the field added. */
    fun add(name: String, type: TypeName, vararg modifiers: KModifier): PropertySpec =
        add(buildProperty(name, type, *modifiers))

    /** Add field from [type] and [name] with custom initialization [builderAction], returning the field added. */
    inline fun add(
        name: String,
        type: TypeName,
        vararg modifiers: KModifier,
        builderAction: PropertySpecBuilder.() -> Unit
    ): PropertySpec = add(buildProperty(name, type, *modifiers, builderAction = builderAction))

    /** Add field from [type] and [name], returning the field added. */
    fun add(name: String, type: Type, vararg modifiers: KModifier): PropertySpec =
        add(buildProperty(name, type, *modifiers))

    /** Add field from [type] and [name] with custom initialization [builderAction], returning the field added. */
    inline fun add(
        name: String,
        type: Type,
        vararg modifiers: KModifier,
        builderAction: PropertySpecBuilder.() -> Unit
    ): PropertySpec = add(buildProperty(name, type, *modifiers, builderAction = builderAction))

    /** Add field from [type] and [name], returning the field added. */
    fun add(name: String, type: KClass<*>, vararg modifiers: KModifier): PropertySpec =
        add(buildProperty(name, type, *modifiers))

    /** Add field from [type] and [name] with custom initialization [builderAction], returning the field added. */
    inline fun add(
        name: String,
        type: KClass<*>,
        vararg modifiers: KModifier,
        builderAction: PropertySpecBuilder.() -> Unit
    ): PropertySpec = add(buildProperty(name, type, *modifiers, builderAction = builderAction))

    /** Add field from reified [T] and [name], returning the field added. */
    inline fun <reified T> add(name: String, vararg modifiers: KModifier): PropertySpec =
        add(buildProperty<T>(name, *modifiers))

    /** Add field from reified [T] and [name] with custom initialization [builderAction], returning the field added. */
    inline fun <reified T> add(
        name: String,
        vararg modifiers: KModifier,
        builderAction: PropertySpecBuilder.() -> Unit
    ): PropertySpec = add(buildProperty<T>(name, *modifiers, builderAction = builderAction))

    /** Convenient method to add field with operator function. */
    operator fun plusAssign(spec: PropertySpec) {
        add(spec)
    }

    /** Convenient method to add field with operator function. */
    operator fun set(name: String, type: TypeName) {
        add(name, type)
    }

    /** Convenient method to add field with operator function. */
    operator fun set(name: String, type: Type) {
        add(name, type)
    }

    /** Convenient method to add field with operator function. */
    operator fun set(name: String, type: KClass<*>) {
        add(name, type)
    }

    /** Configure this container with DSL. */
    inline operator fun invoke(configuration: PropertyContainerScope.() -> Unit) =
        PropertyContainerScope(this).configuration()
}

/** Receiver for the `fields` block providing an extended set of operators for the configuration. */
@KotlinpoetDslMarker
class PropertyContainerScope @PublishedApi internal constructor(container: PropertyContainer) :
    PropertyContainer(), PropertyAddable by container {

    /** Convenient method to add field with receiver type. */
    inline operator fun String.invoke(
        type: TypeName,
        vararg modifiers: KModifier,
        builderAction: PropertySpecBuilder.() -> Unit
    ): PropertySpec = add(this, type, *modifiers, builderAction = builderAction)

    /** Convenient method to add field with receiver type. */
    inline operator fun String.invoke(
        type: Type,
        vararg modifiers: KModifier,
        builderAction: PropertySpecBuilder.() -> Unit
    ): PropertySpec = add(this, type, *modifiers, builderAction = builderAction)

    /** Convenient method to add field with receiver type. */
    inline operator fun String.invoke(
        type: KClass<*>,
        vararg modifiers: KModifier,
        builderAction: PropertySpecBuilder.() -> Unit
    ): PropertySpec = add(this, type, *modifiers, builderAction = builderAction)

    /** Convenient method to add field with receiver type. */
    inline operator fun <reified T> String.invoke(
        vararg modifiers: KModifier,
        builderAction: PropertySpecBuilder.() -> Unit
    ): PropertySpec = add<T>(this, *modifiers, builderAction = builderAction)
}
