package com.hendraanggrian.kotlinpoet.collections

import com.hendraanggrian.kotlinpoet.KotlinpoetSpecDsl
import com.hendraanggrian.kotlinpoet.ParameterSpecBuilder
import com.hendraanggrian.kotlinpoet.SpecLoader
import com.hendraanggrian.kotlinpoet.buildParameterSpec
import com.hendraanggrian.kotlinpoet.createSpecLoader
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.TypeName
import java.lang.reflect.Type
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.reflect.KClass

/** A [ParameterSpecList] is responsible for managing a set of parameter instances. */
@OptIn(ExperimentalContracts::class)
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
    ): ParameterSpec {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return buildParameterSpec(name, type, *modifiers, configuration = configuration).also(::add)
    }

    /** Add parameter from [Type]. */
    fun add(name: String, type: Type, vararg modifiers: KModifier): ParameterSpec =
        ParameterSpec.builder(name, type, *modifiers).build().also(::add)

    /** Add parameter from [Type] with custom initialization [configuration]. */
    fun add(
        name: String,
        type: Type,
        vararg modifiers: KModifier,
        configuration: ParameterSpecBuilder.() -> Unit
    ): ParameterSpec {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return buildParameterSpec(name, type, *modifiers, configuration = configuration).also(::add)
    }

    /** Add parameter from [KClass]. */
    fun add(name: String, type: KClass<*>, vararg modifiers: KModifier): ParameterSpec =
        ParameterSpec.builder(name, type, *modifiers).build().also(::add)

    /** Add parameter from [KClass] with custom initialization [configuration]. */
    fun add(
        name: String,
        type: KClass<*>,
        vararg modifiers: KModifier,
        configuration: ParameterSpecBuilder.() -> Unit
    ): ParameterSpec {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return buildParameterSpec(name, type, *modifiers, configuration = configuration).also(::add)
    }

    /** Add parameter from [T]. */
    inline fun <reified T> add(name: String, vararg modifiers: KModifier): ParameterSpec =
        add(name, T::class, *modifiers)

    /** Add parameter from [T] with custom initialization [configuration]. */
    inline fun <reified T> add(
        name: String,
        vararg modifiers: KModifier,
        noinline configuration: ParameterSpecBuilder.() -> Unit
    ): ParameterSpec {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return add(name, T::class, *modifiers, configuration = configuration)
    }

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

    /** Property delegate for adding parameter from [TypeName]. */
    fun adding(type: TypeName, vararg modifiers: KModifier): SpecLoader<ParameterSpec> =
        createSpecLoader { add(it, type, *modifiers) }

    /**
     * Property delegate for adding parameter from [TypeName] with custom
     * initialization [configuration].
     */
    fun adding(
        type: TypeName,
        vararg modifiers: KModifier,
        configuration: ParameterSpecBuilder.() -> Unit
    ): SpecLoader<ParameterSpec> {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return createSpecLoader { add(it, type, *modifiers, configuration = configuration) }
    }

    /** Property delegate for adding parameter from [Type]. */
    fun adding(type: Type, vararg modifiers: KModifier): SpecLoader<ParameterSpec> =
        createSpecLoader { add(it, type, *modifiers) }

    /**
     * Property delegate for adding parameter from [Type] with custom
     * initialization [configuration].
     */
    fun adding(
        type: Type,
        vararg modifiers: KModifier,
        configuration: ParameterSpecBuilder.() -> Unit
    ): SpecLoader<ParameterSpec> {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return createSpecLoader { add(it, type, *modifiers, configuration = configuration) }
    }

    /** Property delegate for adding parameter from [KClass]. */
    fun adding(type: KClass<*>, vararg modifiers: KModifier): SpecLoader<ParameterSpec> =
        createSpecLoader { add(it, type, *modifiers) }

    /**
     * Property delegate for adding parameter from [KClass] with custom
     * initialization [configuration].
     */
    fun adding(
        type: KClass<*>,
        vararg modifiers: KModifier,
        configuration: ParameterSpecBuilder.() -> Unit
    ): SpecLoader<ParameterSpec> {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return createSpecLoader { add(it, type, *modifiers, configuration = configuration) }
    }
}

/**
 * Receiver for the `parameters` block providing an extended set of operators for the
 * configuration.
 */
@KotlinpoetSpecDsl
class ParameterSpecListScope internal constructor(actualList: MutableList<ParameterSpec>) :
    ParameterSpecList(actualList) {

    /** @see ParameterSpecList.add */
    inline operator fun String.invoke(
        type: TypeName,
        vararg modifiers: KModifier,
        noinline configuration: ParameterSpecBuilder.() -> Unit
    ): ParameterSpec = add(this, type, *modifiers, configuration = configuration)

    /** @see ParameterSpecList.add */
    inline operator fun String.invoke(
        type: Type,
        vararg modifiers: KModifier,
        noinline configuration: ParameterSpecBuilder.() -> Unit
    ): ParameterSpec = add(this, type, *modifiers, configuration = configuration)

    /** @see ParameterSpecList.add */
    inline operator fun String.invoke(
        type: KClass<*>,
        vararg modifiers: KModifier,
        noinline configuration: ParameterSpecBuilder.() -> Unit
    ): ParameterSpec = add(this, type, *modifiers, configuration = configuration)
}
