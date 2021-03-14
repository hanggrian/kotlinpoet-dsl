package io.github.hendraanggrian.kotlinpoet.dsl

import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.TypeName
import io.github.hendraanggrian.kotlinpoet.ParameterSpecBuilder
import io.github.hendraanggrian.kotlinpoet.SpecDslMarker
import io.github.hendraanggrian.kotlinpoet.buildParameterSpec
import io.github.hendraanggrian.kotlinpoet.parameterSpecOf
import java.lang.reflect.Type
import kotlin.reflect.KClass

/** A [ParameterSpecHandler] is responsible for managing a set of parameter instances. */
open class ParameterSpecHandler internal constructor(actualList: MutableList<ParameterSpec>) :
    MutableList<ParameterSpec> by actualList {

    /** Add parameter from [TypeName]. */
    fun add(name: String, type: TypeName, vararg modifiers: KModifier): Boolean =
        add(parameterSpecOf(name, type, *modifiers))

    /** Add parameter from [Type]. */
    fun add(name: String, type: Type, vararg modifiers: KModifier): Boolean =
        add(parameterSpecOf(name, type, *modifiers))

    /** Add parameter from [KClass]. */
    fun add(name: String, type: KClass<*>, vararg modifiers: KModifier): Boolean =
        add(parameterSpecOf(name, type, *modifiers))

    /** Add parameter from [T]. */
    inline fun <reified T> add(name: String, vararg modifiers: KModifier): Boolean =
        add(parameterSpecOf<T>(name, *modifiers))

    /** Add parameter from [TypeName] with custom initialization [configuration]. */
    inline fun add(
        name: String,
        type: TypeName,
        vararg modifiers: KModifier,
        configuration: ParameterSpecBuilder.() -> Unit
    ): Boolean = add(buildParameterSpec(name, type, *modifiers, configuration = configuration))

    /** Add parameter from [Type] with custom initialization [configuration]. */
    inline fun add(
        name: String,
        type: Type,
        vararg modifiers: KModifier,
        configuration: ParameterSpecBuilder.() -> Unit
    ): Boolean = add(buildParameterSpec(name, type, *modifiers, configuration = configuration))

    /** Add parameter from [KClass] with custom initialization [configuration]. */
    inline fun add(
        name: String,
        type: KClass<*>,
        vararg modifiers: KModifier,
        configuration: ParameterSpecBuilder.() -> Unit
    ): Boolean = add(buildParameterSpec(name, type, *modifiers, configuration = configuration))

    /** Add parameter from [T] with custom initialization [configuration]. */
    inline fun <reified T> add(
        name: String,
        vararg modifiers: KModifier,
        configuration: ParameterSpecBuilder.() -> Unit
    ): Boolean = add(buildParameterSpec<T>(name, *modifiers, configuration = configuration))

    /** Convenient method to add parameter with operator function. */
    operator fun set(name: String, type: TypeName): Unit = plusAssign(parameterSpecOf(name, type))

    /** Convenient method to add parameter with operator function. */
    operator fun set(name: String, type: Type): Unit = plusAssign(parameterSpecOf(name, type))

    /** Convenient method to add parameter with operator function. */
    operator fun set(name: String, type: KClass<*>): Unit = plusAssign(parameterSpecOf(name, type))
}

/** Receiver for the `parameters` block providing an extended set of operators for the configuration. */
@SpecDslMarker
class ParameterSpecHandlerScope(actualList: MutableList<ParameterSpec>) : ParameterSpecHandler(actualList) {

    /** Convenient method to add parameter with receiver type. */
    inline operator fun String.invoke(
        type: TypeName,
        vararg modifiers: KModifier,
        configuration: ParameterSpecBuilder.() -> Unit
    ): Boolean = add(this, type, *modifiers, configuration = configuration)

    /** Convenient method to add parameter with receiver type. */
    inline operator fun String.invoke(
        type: Type,
        vararg modifiers: KModifier,
        configuration: ParameterSpecBuilder.() -> Unit
    ): Boolean = add(this, type, *modifiers, configuration = configuration)

    /** Convenient method to add parameter with receiver type. */
    inline operator fun String.invoke(
        type: KClass<*>,
        vararg modifiers: KModifier,
        configuration: ParameterSpecBuilder.() -> Unit
    ): Boolean = add(this, type, *modifiers, configuration = configuration)

    /** Convenient method to add parameter with receiver type. */
    inline operator fun <reified T> String.invoke(
        vararg modifiers: KModifier,
        configuration: ParameterSpecBuilder.() -> Unit
    ): Boolean = add<T>(this, *modifiers, configuration = configuration)
}
