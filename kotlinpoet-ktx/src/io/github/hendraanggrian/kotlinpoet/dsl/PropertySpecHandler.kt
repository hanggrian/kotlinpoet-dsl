package io.github.hendraanggrian.kotlinpoet.dsl

import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeName
import io.github.hendraanggrian.kotlinpoet.PropertySpecBuilder
import io.github.hendraanggrian.kotlinpoet.SpecDslMarker
import io.github.hendraanggrian.kotlinpoet.buildPropertySpec
import io.github.hendraanggrian.kotlinpoet.propertySpecOf
import java.lang.reflect.Type
import kotlin.reflect.KClass

/** A [PropertySpecHandler] is responsible for managing a set of property instances. */
open class PropertySpecHandler internal constructor(actualList: MutableList<PropertySpec>) :
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

    /** Add property from [TypeName] with custom initialization [configuration]. */
    inline fun add(
        name: String,
        type: TypeName,
        vararg modifiers: KModifier,
        configuration: PropertySpecBuilder.() -> Unit
    ): Boolean = add(buildPropertySpec(name, type, *modifiers, configuration = configuration))

    /** Add property from [Type] with custom initialization [configuration]. */
    inline fun add(
        name: String,
        type: Type,
        vararg modifiers: KModifier,
        configuration: PropertySpecBuilder.() -> Unit
    ): Boolean = add(buildPropertySpec(name, type, *modifiers, configuration = configuration))

    /** Add property from [KClass] with custom initialization [configuration]. */
    inline fun add(
        name: String,
        type: KClass<*>,
        vararg modifiers: KModifier,
        configuration: PropertySpecBuilder.() -> Unit
    ): Boolean = add(buildPropertySpec(name, type, *modifiers, configuration = configuration))

    /** Add property from [T] with custom initialization [configuration]. */
    inline fun <reified T> add(
        name: String,
        vararg modifiers: KModifier,
        configuration: PropertySpecBuilder.() -> Unit
    ): Boolean = add(buildPropertySpec<T>(name, *modifiers, configuration = configuration))

    /** Convenient method to add property with operator function. */
    operator fun set(name: String, type: TypeName): Unit = plusAssign(propertySpecOf(name, type))

    /** Convenient method to add property with operator function. */
    operator fun set(name: String, type: Type): Unit = plusAssign(propertySpecOf(name, type))

    /** Convenient method to add property with operator function. */
    operator fun set(name: String, type: KClass<*>): Unit = plusAssign(propertySpecOf(name, type))
}

/** Receiver for the `properties` block providing an extended set of operators for the configuration. */
@SpecDslMarker
class PropertySpecHandlerScope(actualList: MutableList<PropertySpec>) : PropertySpecHandler(actualList) {

    /** Convenient method to add property with receiver type. */
    inline operator fun String.invoke(
        type: TypeName,
        vararg modifiers: KModifier,
        configuration: PropertySpecBuilder.() -> Unit
    ): Boolean = add(this, type, *modifiers, configuration = configuration)

    /** Convenient method to add property with receiver type. */
    inline operator fun String.invoke(
        type: Type,
        vararg modifiers: KModifier,
        configuration: PropertySpecBuilder.() -> Unit
    ): Boolean = add(this, type, *modifiers, configuration = configuration)

    /** Convenient method to add property with receiver type. */
    inline operator fun String.invoke(
        type: KClass<*>,
        vararg modifiers: KModifier,
        configuration: PropertySpecBuilder.() -> Unit
    ): Boolean = add(this, type, *modifiers, configuration = configuration)

    /** Convenient method to add property with receiver type. */
    inline operator fun <reified T> String.invoke(
        vararg modifiers: KModifier,
        configuration: PropertySpecBuilder.() -> Unit
    ): Boolean = add<T>(this, *modifiers, configuration = configuration)
}
