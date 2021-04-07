package com.hendraanggrian.kotlinpoet.dsl

import com.hendraanggrian.kotlinpoet.ParameterSpecBuilder
import com.hendraanggrian.kotlinpoet.SpecDslMarker
import com.hendraanggrian.kotlinpoet.buildParameterSpec
import com.hendraanggrian.kotlinpoet.parameterSpecOf
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.TypeName
import java.lang.reflect.Type
import kotlin.reflect.KClass

/** A [ParameterSpecHandler] is responsible for managing a set of parameter instances. */
open class ParameterSpecHandler(actualList: MutableList<ParameterSpec>) : MutableList<ParameterSpec> by actualList {

    /** Add parameter from [TypeName]. */
    fun add(name: String, type: TypeName, vararg modifiers: KModifier): Boolean =
        add(parameterSpecOf(name, type, *modifiers))

    /** Add parameter from [TypeName] with custom initialization [configuration]. */
    fun add(
        name: String,
        type: TypeName,
        vararg modifiers: KModifier,
        configuration: ParameterSpecBuilder.() -> Unit
    ): Boolean = add(buildParameterSpec(name, type, *modifiers, configuration = configuration))

    /** Add parameter from [Type]. */
    fun add(name: String, type: Type, vararg modifiers: KModifier): Boolean =
        add(parameterSpecOf(name, type, *modifiers))

    /** Add parameter from [Type] with custom initialization [configuration]. */
    fun add(
        name: String,
        type: Type,
        vararg modifiers: KModifier,
        configuration: ParameterSpecBuilder.() -> Unit
    ): Boolean = add(buildParameterSpec(name, type, *modifiers, configuration = configuration))

    /** Add parameter from [KClass]. */
    fun add(name: String, type: KClass<*>, vararg modifiers: KModifier): Boolean =
        add(parameterSpecOf(name, type, *modifiers))

    /** Add parameter from [KClass] with custom initialization [configuration]. */
    fun add(
        name: String,
        type: KClass<*>,
        vararg modifiers: KModifier,
        configuration: ParameterSpecBuilder.() -> Unit
    ): Boolean = add(buildParameterSpec(name, type, *modifiers, configuration = configuration))

    /** Add parameter from [T]. */
    inline fun <reified T> add(name: String, vararg modifiers: KModifier): Boolean =
        add(parameterSpecOf<T>(name, *modifiers))

    /** Add parameter from [T] with custom initialization [configuration]. */
    inline fun <reified T> add(
        name: String,
        vararg modifiers: KModifier,
        noinline configuration: ParameterSpecBuilder.() -> Unit
    ): Boolean = add(buildParameterSpec<T>(name, *modifiers, configuration = configuration))

    /** Convenient method to add parameter with operator function. */
    @Suppress("NOTHING_TO_INLINE")
    inline operator fun set(name: String, type: TypeName) {
        add(name, type)
    }

    /** Convenient method to add parameter with operator function. */
    @Suppress("NOTHING_TO_INLINE")
    inline operator fun set(name: String, type: Type) {
        add(name, type)
    }

    /** Convenient method to add parameter with operator function. */
    @Suppress("NOTHING_TO_INLINE")
    inline operator fun set(name: String, type: KClass<*>) {
        add(name, type)
    }
}

/** Receiver for the `parameters` block providing an extended set of operators for the configuration. */
@SpecDslMarker
class ParameterSpecHandlerScope(actualList: MutableList<ParameterSpec>) : ParameterSpecHandler(actualList) {

    /** @see ParameterSpecHandler.add */
    operator fun String.invoke(type: TypeName, vararg modifiers: KModifier): Boolean = add(this, type, *modifiers)

    /** @see ParameterSpecHandler.add */
    operator fun String.invoke(
        type: TypeName,
        vararg modifiers: KModifier,
        configuration: ParameterSpecBuilder.() -> Unit
    ): Boolean = add(this, type, *modifiers, configuration = configuration)

    /** @see ParameterSpecHandler.add */
    operator fun String.invoke(type: Type, vararg modifiers: KModifier): Boolean = add(this, type, *modifiers)

    /** @see ParameterSpecHandler.add */
    operator fun String.invoke(
        type: Type,
        vararg modifiers: KModifier,
        configuration: ParameterSpecBuilder.() -> Unit
    ): Boolean = add(this, type, *modifiers, configuration = configuration)

    /** @see ParameterSpecHandler.add */
    operator fun String.invoke(type: KClass<*>, vararg modifiers: KModifier): Boolean = add(this, type, *modifiers)

    /** @see ParameterSpecHandler.add */
    operator fun String.invoke(
        type: KClass<*>,
        vararg modifiers: KModifier,
        configuration: ParameterSpecBuilder.() -> Unit
    ): Boolean = add(this, type, *modifiers, configuration = configuration)

    /** @see ParameterSpecHandler.add */
    inline operator fun <reified T> String.invoke(vararg modifiers: KModifier): Boolean =
        add<T>(this, *modifiers)

    /** @see ParameterSpecHandler.add */
    inline operator fun <reified T> String.invoke(
        vararg modifiers: KModifier,
        noinline configuration: ParameterSpecBuilder.() -> Unit
    ): Boolean = add<T>(this, *modifiers, configuration = configuration)
}
