package com.hendraanggrian.kotlinpoet.collections

import com.hendraanggrian.kotlinpoet.KotlinpoetDslMarker
import com.hendraanggrian.kotlinpoet.PropertySpecBuilder
import com.hendraanggrian.kotlinpoet.buildPropertySpec
import com.hendraanggrian.kotlinpoet.propertySpecOf
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeName
import java.lang.reflect.Type
import kotlin.reflect.KClass

/** A [PropertySpecList] is responsible for managing a set of property instances. */
open class PropertySpecList internal constructor(actualList: MutableList<PropertySpec>) :
    MutableList<PropertySpec> by actualList {

    /** Add property from [type] and [name], returning the property added. */
    fun add(name: String, type: TypeName, vararg modifiers: KModifier): PropertySpec =
        propertySpecOf(name, type, *modifiers).also(::plusAssign)

    /** Add property from [type] and [name], returning the property added. */
    fun add(name: String, type: Type, vararg modifiers: KModifier): PropertySpec =
        propertySpecOf(name, type, *modifiers).also(::plusAssign)

    /** Add property from [type] and [name], returning the property added. */
    fun add(name: String, type: KClass<*>, vararg modifiers: KModifier): PropertySpec =
        propertySpecOf(name, type, *modifiers).also(::plusAssign)

    /** Add property from reified [T] and [name], returning the property added. */
    inline fun <reified T> add(name: String, vararg modifiers: KModifier): PropertySpec =
        propertySpecOf<T>(name, *modifiers).also(::plusAssign)

    /** Add property from [type] and [name] with custom initialization [builderAction], returning the property added. */
    inline fun add(
        name: String,
        type: TypeName,
        vararg modifiers: KModifier,
        builderAction: PropertySpecBuilder.() -> Unit
    ): PropertySpec = buildPropertySpec(name, type, *modifiers, builderAction = builderAction).also(::plusAssign)

    /** Add property from [type] and [name] with custom initialization [builderAction], returning the property added. */
    inline fun add(
        name: String,
        type: Type,
        vararg modifiers: KModifier,
        builderAction: PropertySpecBuilder.() -> Unit
    ): PropertySpec = buildPropertySpec(name, type, *modifiers, builderAction = builderAction).also(::plusAssign)

    /** Add property from [type] and [name] with custom initialization [builderAction], returning the property added. */
    inline fun add(
        name: String,
        type: KClass<*>,
        vararg modifiers: KModifier,
        builderAction: PropertySpecBuilder.() -> Unit
    ): PropertySpec = buildPropertySpec(name, type, *modifiers, builderAction = builderAction).also(::plusAssign)

    /** Add property from reified [T] and [name] with custom initialization [builderAction], returning the property added. */
    inline fun <reified T> add(
        name: String,
        vararg modifiers: KModifier,
        builderAction: PropertySpecBuilder.() -> Unit
    ): PropertySpec = buildPropertySpec<T>(name, *modifiers, builderAction = builderAction).also(::plusAssign)

    /** Convenient method to add property with operator function. */
    operator fun set(name: String, type: TypeName): Unit = plusAssign(propertySpecOf(name, type))

    /** Convenient method to add property with operator function. */
    operator fun set(name: String, type: Type): Unit = plusAssign(propertySpecOf(name, type))

    /** Convenient method to add property with operator function. */
    operator fun set(name: String, type: KClass<*>): Unit = plusAssign(propertySpecOf(name, type))
}

/** Receiver for the `properties` block providing an extended set of operators for the configuration. */
@KotlinpoetDslMarker
class PropertySpecListScope(actualList: MutableList<PropertySpec>) : PropertySpecList(actualList) {

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
