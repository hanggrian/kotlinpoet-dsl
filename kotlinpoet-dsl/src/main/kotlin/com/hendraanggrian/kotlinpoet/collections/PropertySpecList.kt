package com.hendraanggrian.kotlinpoet.collections

import com.hendraanggrian.kotlinpoet.PropertySpecBuilder
import com.hendraanggrian.kotlinpoet.KotlinpoetSpecMarker
import com.hendraanggrian.kotlinpoet.SpecLoader
import com.hendraanggrian.kotlinpoet.buildPropertySpec
import com.hendraanggrian.kotlinpoet.createSpecLoader
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeName
import java.lang.reflect.Type
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.reflect.KClass

/** A [PropertySpecList] is responsible for managing a set of property instances. */
@OptIn(ExperimentalContracts::class)
open class PropertySpecList internal constructor(actualList: MutableList<PropertySpec>) :
    MutableList<PropertySpec> by actualList {

    /** Add property from [TypeName]. */
    fun add(name: String, type: TypeName, vararg modifiers: KModifier): PropertySpec =
        PropertySpec.builder(name, type, *modifiers).build().also(::add)

    /** Add property from [TypeName] with custom initialization [configuration]. */
    fun add(
        name: String,
        type: TypeName,
        vararg modifiers: KModifier,
        configuration: PropertySpecBuilder.() -> Unit
    ): PropertySpec {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return buildPropertySpec(name, type, *modifiers, configuration = configuration).also(::add)
    }

    /** Add property from [Type]. */
    fun add(name: String, type: Type, vararg modifiers: KModifier): PropertySpec =
        PropertySpec.builder(name, type, *modifiers).build().also(::add)

    /** Add property from [Type] with custom initialization [configuration]. */
    fun add(
        name: String,
        type: Type,
        vararg modifiers: KModifier,
        configuration: PropertySpecBuilder.() -> Unit
    ): PropertySpec {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return buildPropertySpec(name, type, *modifiers, configuration = configuration).also(::add)
    }

    /** Add property from [KClass]. */
    fun add(name: String, type: KClass<*>, vararg modifiers: KModifier): PropertySpec =
        PropertySpec.builder(name, type, *modifiers).build().also(::add)

    /** Add property from [KClass] with custom initialization [configuration]. */
    fun add(
        name: String,
        type: KClass<*>,
        vararg modifiers: KModifier,
        configuration: PropertySpecBuilder.() -> Unit
    ): PropertySpec {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return buildPropertySpec(name, type, *modifiers, configuration = configuration).also(::add)
    }

    /** Add property from [T]. */
    inline fun <reified T> add(name: String, vararg modifiers: KModifier): PropertySpec =
        add(name, T::class, *modifiers)

    /** Add property from [T] with custom initialization [configuration]. */
    inline fun <reified T> add(
        name: String,
        vararg modifiers: KModifier,
        noinline configuration: PropertySpecBuilder.() -> Unit
    ): PropertySpec {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return add(name, T::class, *modifiers, configuration = configuration)
    }

    /** Convenient method to add property with operator function. */
    inline operator fun set(name: String, type: TypeName) {
        add(name, type)
    }

    /** Convenient method to add property with operator function. */
    inline operator fun set(name: String, type: Type) {
        add(name, type)
    }

    /** Convenient method to add property with operator function. */
    inline operator fun set(name: String, type: KClass<*>) {
        add(name, type)
    }

    /** Property delegate for adding property from [TypeName]. */
    fun adding(type: TypeName, vararg modifiers: KModifier): SpecLoader<PropertySpec> =
        createSpecLoader { add(it, type, *modifiers) }

    /** Property delegate for adding property from [TypeName] with initialization [configuration]. */
    fun adding(
        type: TypeName,
        vararg modifiers: KModifier,
        configuration: PropertySpecBuilder.() -> Unit
    ): SpecLoader<PropertySpec> {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return createSpecLoader { add(it, type, *modifiers, configuration = configuration) }
    }

    /** Property delegate for adding property from [Type]. */
    fun adding(type: Type, vararg modifiers: KModifier): SpecLoader<PropertySpec> =
        createSpecLoader { add(it, type, *modifiers) }

    /** Property delegate for adding property from [Type] with initialization [configuration]. */
    fun adding(
        type: Type,
        vararg modifiers: KModifier,
        configuration: PropertySpecBuilder.() -> Unit
    ): SpecLoader<PropertySpec> {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return createSpecLoader { add(it, type, *modifiers, configuration = configuration) }
    }

    /** Property delegate for adding property from [KClass]. */
    fun adding(type: KClass<*>, vararg modifiers: KModifier): SpecLoader<PropertySpec> =
        createSpecLoader { add(it, type, *modifiers) }

    /** Property delegate for adding property from [KClass] with initialization [configuration]. */
    fun adding(
        type: KClass<*>,
        vararg modifiers: KModifier,
        configuration: PropertySpecBuilder.() -> Unit
    ): SpecLoader<PropertySpec> {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return createSpecLoader { add(it, type, *modifiers, configuration = configuration) }
    }
}

/** Receiver for the `properties` block providing an extended set of operators for the configuration. */
@KotlinpoetSpecMarker
class PropertySpecListScope internal constructor(actualList: MutableList<PropertySpec>) : PropertySpecList(actualList) {

    /** @see PropertySpecList.add */
    inline operator fun String.invoke(
        type: TypeName,
        vararg modifiers: KModifier,
        noinline configuration: PropertySpecBuilder.() -> Unit
    ): PropertySpec = add(this, type, *modifiers, configuration = configuration)

    /** @see PropertySpecList.add */
    inline operator fun String.invoke(
        type: Type,
        vararg modifiers: KModifier,
        noinline configuration: PropertySpecBuilder.() -> Unit
    ): PropertySpec = add(this, type, *modifiers, configuration = configuration)

    /** @see PropertySpecList.add */
    inline operator fun String.invoke(
        type: KClass<*>,
        vararg modifiers: KModifier,
        noinline configuration: PropertySpecBuilder.() -> Unit
    ): PropertySpec = add(this, type, *modifiers, configuration = configuration)
}
