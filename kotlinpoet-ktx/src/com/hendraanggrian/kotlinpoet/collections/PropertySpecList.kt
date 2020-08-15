package com.hendraanggrian.kotlinpoet.collections

import com.hendraanggrian.kotlinpoet.PropertySpecBuilder
import com.hendraanggrian.kotlinpoet.SpecDslMarker
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

    /** Add property from [TypeName]. */
    fun add(name: String, type: TypeName, vararg modifiers: KModifier): Boolean =
        add(propertySpecOf(name, type, *modifiers))

    /** Add property from [Type]. */
    fun add(name: String, type: Type, vararg modifiers: KModifier): Boolean =
        add(propertySpecOf(name, type, *modifiers))

    /** Add property from [KClass]. */
    fun add(name: String, type: KClass<*>, vararg modifiers: KModifier): Boolean =
        add(propertySpecOf(name, type, *modifiers))

    /** Add property from [T]. */
    inline fun <reified T> add(name: String, vararg modifiers: KModifier): Boolean =
        add(propertySpecOf<T>(name, *modifiers))

    /** Add property from [TypeName] with custom initialization [builderAction]. */
    inline fun add(
        name: String,
        type: TypeName,
        vararg modifiers: KModifier,
        builderAction: PropertySpecBuilder.() -> Unit
    ): Boolean = add(buildPropertySpec(name, type, *modifiers, builderAction = builderAction))

    /** Add property from [Type] with custom initialization [builderAction]. */
    inline fun add(
        name: String,
        type: Type,
        vararg modifiers: KModifier,
        builderAction: PropertySpecBuilder.() -> Unit
    ): Boolean = add(buildPropertySpec(name, type, *modifiers, builderAction = builderAction))

    /** Add property from [KClass] with custom initialization [builderAction]. */
    inline fun add(
        name: String,
        type: KClass<*>,
        vararg modifiers: KModifier,
        builderAction: PropertySpecBuilder.() -> Unit
    ): Boolean = add(buildPropertySpec(name, type, *modifiers, builderAction = builderAction))

    /** Add property from [T] with custom initialization [builderAction]. */
    inline fun <reified T> add(
        name: String,
        vararg modifiers: KModifier,
        builderAction: PropertySpecBuilder.() -> Unit
    ): Boolean = add(buildPropertySpec<T>(name, *modifiers, builderAction = builderAction))

    /** Convenient method to add property with operator function. */
    operator fun set(name: String, type: TypeName): Unit = plusAssign(propertySpecOf(name, type))

    /** Convenient method to add property with operator function. */
    operator fun set(name: String, type: Type): Unit = plusAssign(propertySpecOf(name, type))

    /** Convenient method to add property with operator function. */
    operator fun set(name: String, type: KClass<*>): Unit = plusAssign(propertySpecOf(name, type))
}

/** Receiver for the `properties` block providing an extended set of operators for the configuration. */
@SpecDslMarker
class PropertySpecListScope(actualList: MutableList<PropertySpec>) : PropertySpecList(actualList) {

    /** Convenient method to add property with receiver type. */
    inline operator fun String.invoke(
        type: TypeName,
        vararg modifiers: KModifier,
        builderAction: PropertySpecBuilder.() -> Unit
    ): Boolean = add(this, type, *modifiers, builderAction = builderAction)

    /** Convenient method to add property with receiver type. */
    inline operator fun String.invoke(
        type: Type,
        vararg modifiers: KModifier,
        builderAction: PropertySpecBuilder.() -> Unit
    ): Boolean = add(this, type, *modifiers, builderAction = builderAction)

    /** Convenient method to add property with receiver type. */
    inline operator fun String.invoke(
        type: KClass<*>,
        vararg modifiers: KModifier,
        builderAction: PropertySpecBuilder.() -> Unit
    ): Boolean = add(this, type, *modifiers, builderAction = builderAction)

    /** Convenient method to add property with receiver type. */
    inline operator fun <reified T> String.invoke(
        vararg modifiers: KModifier,
        builderAction: PropertySpecBuilder.() -> Unit
    ): Boolean = add<T>(this, *modifiers, builderAction = builderAction)
}
