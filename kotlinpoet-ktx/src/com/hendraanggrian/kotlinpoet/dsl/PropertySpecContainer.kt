package com.hendraanggrian.kotlinpoet.dsl

import com.hendraanggrian.kotlinpoet.KotlinpoetDslMarker
import com.hendraanggrian.kotlinpoet.PropertySpecBuilder
import com.hendraanggrian.kotlinpoet.buildPropertySpec
import com.hendraanggrian.kotlinpoet.propertySpecOf
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeName
import java.lang.reflect.Type
import kotlin.reflect.KClass

private interface PropertySpecAddable {

    /** Add property to this container. */
    fun add(spec: PropertySpec)

    /** Add collection of properties to this container. */
    fun addAll(specs: Iterable<PropertySpec>): Boolean
}

/** A [PropertySpecContainer] is responsible for managing a set of property instances. */
abstract class PropertySpecContainer : PropertySpecAddable {

    /** Add property from [type] and [name], returning the property added. */
    fun add(name: String, type: TypeName, vararg modifiers: KModifier): PropertySpec =
        propertySpecOf(name, type, *modifiers).also { add(it) }

    /** Add property from [type] and [name] with custom initialization [builderAction], returning the property added. */
    inline fun add(
        name: String,
        type: TypeName,
        vararg modifiers: KModifier,
        builderAction: PropertySpecBuilder.() -> Unit
    ): PropertySpec = buildPropertySpec(name, type, *modifiers, builderAction = builderAction).also { add(it) }

    /** Add property from [type] and [name], returning the property added. */
    fun add(name: String, type: Type, vararg modifiers: KModifier): PropertySpec =
        propertySpecOf(name, type, *modifiers).also { add(it) }

    /** Add property from [type] and [name] with custom initialization [builderAction], returning the property added. */
    inline fun add(
        name: String,
        type: Type,
        vararg modifiers: KModifier,
        builderAction: PropertySpecBuilder.() -> Unit
    ): PropertySpec = buildPropertySpec(name, type, *modifiers, builderAction = builderAction).also { add(it) }

    /** Add property from [type] and [name], returning the property added. */
    fun add(name: String, type: KClass<*>, vararg modifiers: KModifier): PropertySpec =
        propertySpecOf(name, type, *modifiers).also { add(it) }

    /** Add property from [type] and [name] with custom initialization [builderAction], returning the property added. */
    inline fun add(
        name: String,
        type: KClass<*>,
        vararg modifiers: KModifier,
        builderAction: PropertySpecBuilder.() -> Unit
    ): PropertySpec = buildPropertySpec(name, type, *modifiers, builderAction = builderAction).also { add(it) }

    /** Add property from reified [T] and [name], returning the property added. */
    inline fun <reified T> add(name: String, vararg modifiers: KModifier): PropertySpec =
        propertySpecOf<T>(name, *modifiers).also { add(it) }

    /** Add property from reified [T] and [name] with custom initialization [builderAction], returning the property added. */
    inline fun <reified T> add(
        name: String,
        vararg modifiers: KModifier,
        builderAction: PropertySpecBuilder.() -> Unit
    ): PropertySpec = buildPropertySpec<T>(name, *modifiers, builderAction = builderAction).also { add(it) }

    /** Convenient method to add property with operator function. */
    operator fun plusAssign(spec: PropertySpec): Unit = add(spec)

    /** Convenient method to add collection of properties with operator function. */
    operator fun plusAssign(specs: Iterable<PropertySpec>) {
        addAll(specs)
    }

    /** Convenient method to add property with operator function. */
    operator fun set(name: String, type: TypeName) {
        add(name, type)
    }

    /** Convenient method to add property with operator function. */
    operator fun set(name: String, type: Type) {
        add(name, type)
    }

    /** Convenient method to add property with operator function. */
    operator fun set(name: String, type: KClass<*>) {
        add(name, type)
    }
}

/** Receiver for the `properties` block providing an extended set of operators for the configuration. */
@KotlinpoetDslMarker
class PropertySpecContainerScope(container: PropertySpecContainer) : PropertySpecContainer(),
    PropertySpecAddable by container {

    /** Convenient method to add property with receiver type. */
    inline operator fun String.invoke(
        type: TypeName,
        vararg modifiers: KModifier,
        builderAction: PropertySpecBuilder.() -> Unit
    ): PropertySpec = add(this, type, *modifiers, builderAction = builderAction)

    /** Convenient method to add property with receiver type. */
    inline operator fun String.invoke(
        type: Type,
        vararg modifiers: KModifier,
        builderAction: PropertySpecBuilder.() -> Unit
    ): PropertySpec = add(this, type, *modifiers, builderAction = builderAction)

    /** Convenient method to add property with receiver type. */
    inline operator fun String.invoke(
        type: KClass<*>,
        vararg modifiers: KModifier,
        builderAction: PropertySpecBuilder.() -> Unit
    ): PropertySpec = add(this, type, *modifiers, builderAction = builderAction)

    /** Convenient method to add property with receiver type. */
    inline operator fun <reified T> String.invoke(
        vararg modifiers: KModifier,
        builderAction: PropertySpecBuilder.() -> Unit
    ): PropertySpec = add<T>(this, *modifiers, builderAction = builderAction)
}
