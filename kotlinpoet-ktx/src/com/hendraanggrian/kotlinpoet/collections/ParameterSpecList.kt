package com.hendraanggrian.kotlinpoet.collections

import com.hendraanggrian.kotlinpoet.ParameterSpecBuilder
import com.hendraanggrian.kotlinpoet.SpecMarker
import com.hendraanggrian.kotlinpoet.buildParameterSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.TypeName
import java.lang.reflect.Type
import kotlin.reflect.KClass

/** A [ParameterSpecList] is responsible for managing a set of parameter instances. */
open class ParameterSpecList internal constructor(actualList: MutableList<ParameterSpec>) :
    MutableList<ParameterSpec> by actualList {

    /** Add parameter from [TypeName]. */
    fun add(name: String, type: TypeName, vararg modifiers: KModifier): ParameterSpec =
        ParameterSpec.builder(name, type, *modifiers).build().also(::add)

    /** Add parameter from [TypeName] with custom initialization [configuration]. */
    fun add(
        name: String,
        type: TypeName,
        vararg modifiers: KModifier,
        configuration: ParameterSpecBuilder.() -> Unit
    ): ParameterSpec = buildParameterSpec(name, type, *modifiers, configuration = configuration).also(::add)

    /** Add parameter from [Type]. */
    fun add(name: String, type: Type, vararg modifiers: KModifier): ParameterSpec =
        ParameterSpec.builder(name, type, *modifiers).build().also(::add)

    /** Add parameter from [Type] with custom initialization [configuration]. */
    fun add(
        name: String,
        type: Type,
        vararg modifiers: KModifier,
        configuration: ParameterSpecBuilder.() -> Unit
    ): ParameterSpec = buildParameterSpec(name, type, *modifiers, configuration = configuration).also(::add)

    /** Add parameter from [KClass]. */
    fun add(name: String, type: KClass<*>, vararg modifiers: KModifier): ParameterSpec =
        ParameterSpec.builder(name, type, *modifiers).build().also(::add)

    /** Add parameter from [KClass] with custom initialization [configuration]. */
    fun add(
        name: String,
        type: KClass<*>,
        vararg modifiers: KModifier,
        configuration: ParameterSpecBuilder.() -> Unit
    ): ParameterSpec = buildParameterSpec(name, type, *modifiers, configuration = configuration).also(::add)

    /** Add parameter from [T]. */
    inline fun <reified T> add(name: String, vararg modifiers: KModifier): ParameterSpec =
        ParameterSpec.builder(name, T::class, *modifiers).build().also(::add)

    /** Add parameter from [T] with custom initialization [configuration]. */
    inline fun <reified T> add(
        name: String,
        vararg modifiers: KModifier,
        noinline configuration: ParameterSpecBuilder.() -> Unit
    ): ParameterSpec = buildParameterSpec<T>(name, *modifiers, configuration = configuration).also(::add)

    /** Convenient method to add parameter with operator function. */
    inline operator fun set(name: String, type: TypeName) {
        add(name, type)
    }

    /** Convenient method to add parameter with operator function. */
    inline operator fun set(name: String, type: Type) {
        add(name, type)
    }

    /** Convenient method to add parameter with operator function. */
    inline operator fun set(name: String, type: KClass<*>) {
        add(name, type)
    }
}

/** Receiver for the `parameters` block providing an extended set of operators for the configuration. */
@SpecMarker
class ParameterSpecListScope internal constructor(actualList: MutableList<ParameterSpec>) :
    ParameterSpecList(actualList) {

    /** @see ParameterSpecList.add */
    operator fun String.invoke(
        type: TypeName,
        vararg modifiers: KModifier,
        configuration: ParameterSpecBuilder.() -> Unit
    ): ParameterSpec = add(this, type, *modifiers, configuration = configuration)

    /** @see ParameterSpecList.add */
    operator fun String.invoke(
        type: Type,
        vararg modifiers: KModifier,
        configuration: ParameterSpecBuilder.() -> Unit
    ): ParameterSpec = add(this, type, *modifiers, configuration = configuration)

    /** @see ParameterSpecList.add */
    operator fun String.invoke(
        type: KClass<*>,
        vararg modifiers: KModifier,
        configuration: ParameterSpecBuilder.() -> Unit
    ): ParameterSpec = add(this, type, *modifiers, configuration = configuration)

    /** @see ParameterSpecList.add */
    inline operator fun <reified T> String.invoke(
        vararg modifiers: KModifier,
        noinline configuration: ParameterSpecBuilder.() -> Unit
    ): ParameterSpec = add<T>(this, *modifiers, configuration = configuration)
}
