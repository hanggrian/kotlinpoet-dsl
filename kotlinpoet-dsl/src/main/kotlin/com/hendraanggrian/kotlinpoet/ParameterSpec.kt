@file:OptIn(ExperimentalContracts::class)

package com.hendraanggrian.kotlinpoet

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.DelicateKotlinPoetApi
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.TypeName
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.reflect.KClass

inline fun TypeName.asParameterSpec(name: String, vararg modifiers: KModifier): ParameterSpec =
    ParameterSpec.builder(name, this, *modifiers).build()

/**
 * Creates new [ParameterSpec] by populating newly created [ParameterSpecBuilder] using provided
 * [configuration].
 */
inline fun buildParameterSpec(
    name: String,
    type: TypeName,
    vararg modifiers: KModifier,
    configuration: ParameterSpecBuilder.() -> Unit,
): ParameterSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return ParameterSpecBuilder(ParameterSpec.builder(name, type, *modifiers))
        .apply(configuration)
        .build()
}

/**
 * Inserts new [ParameterSpec] by populating newly created [ParameterSpecBuilder] using provided
 * [configuration].
 */
inline fun ParameterSpecHandler.parameter(
    name: String,
    type: TypeName,
    vararg modifiers: KModifier,
    configuration: ParameterSpecBuilder.() -> Unit,
): ParameterSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return buildParameterSpec(name, type, *modifiers, configuration = configuration)
        .also(::parameter)
}

/**
 * Inserts new [ParameterSpec] by populating newly created [ParameterSpecBuilder] using provided
 * [configuration].
 */
inline fun ParameterSpecHandler.parameter(
    name: String,
    type: Class<*>,
    vararg modifiers: KModifier,
    configuration: ParameterSpecBuilder.() -> Unit,
): ParameterSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return buildParameterSpec(name, type.name2, *modifiers, configuration = configuration)
        .also(::parameter)
}

/**
 * Inserts new [ParameterSpec] by populating newly created [ParameterSpecBuilder] using provided
 * [configuration].
 */
inline fun ParameterSpecHandler.parameter(
    name: String,
    type: KClass<*>,
    vararg modifiers: KModifier,
    configuration: ParameterSpecBuilder.() -> Unit,
): ParameterSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return buildParameterSpec(name, type.name, *modifiers, configuration = configuration)
        .also(::parameter)
}

/**
 * Property delegate for inserting new [ParameterSpec] by populating newly created
 * [ParameterSpecBuilder] using provided [configuration].
 */
fun ParameterSpecHandler.parametering(
    type: TypeName,
    vararg modifiers: KModifier,
    configuration: ParameterSpecBuilder.() -> Unit,
): SpecDelegateProvider<ParameterSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider {
        buildParameterSpec(it, type, *modifiers, configuration = configuration)
            .also(::parameter)
    }
}

/**
 * Property delegate for inserting new [ParameterSpec] by populating newly created
 * [ParameterSpecBuilder] using provided [configuration].
 */
fun ParameterSpecHandler.parametering(
    type: Class<*>,
    vararg modifiers: KModifier,
    configuration: ParameterSpecBuilder.() -> Unit,
): SpecDelegateProvider<ParameterSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider {
        buildParameterSpec(it, type.name2, *modifiers, configuration = configuration)
            .also(::parameter)
    }
}

/**
 * Property delegate for inserting new [ParameterSpec] by populating newly created
 * [ParameterSpecBuilder] using provided [configuration].
 */
fun ParameterSpecHandler.parametering(
    type: KClass<*>,
    vararg modifiers: KModifier,
    configuration: ParameterSpecBuilder.() -> Unit,
): SpecDelegateProvider<ParameterSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider {
        buildParameterSpec(it, type.name, *modifiers, configuration = configuration)
            .also(::parameter)
    }
}

/** Convenient method to insert [ParameterSpec] using reified type. */
inline fun <reified T> ParameterSpecHandler.parameter(
    name: String,
    vararg modifiers: KModifier,
): ParameterSpec = T::class.name.asParameterSpec(name, *modifiers).also(::parameter)

/** Invokes DSL to configure [ParameterSpec] collection. */
fun ParameterSpecHandler.parameters(configuration: ParameterSpecHandlerScope.() -> Unit) {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    ParameterSpecHandlerScope.of(this).configuration()
}

/** Responsible for managing a set of [ParameterSpec] instances. */
interface ParameterSpecHandler {
    fun parameter(parameter: ParameterSpec)

    fun parameter(name: String, type: TypeName, vararg modifiers: KModifier): ParameterSpec =
        type.asParameterSpec(name, *modifiers).also(::parameter)

    @OptIn(DelicateKotlinPoetApi::class)
    fun parameter(name: String, type: Class<*>, vararg modifiers: KModifier): ParameterSpec =
        type.name2.asParameterSpec(name, *modifiers).also(::parameter)

    fun parameter(name: String, type: KClass<*>, vararg modifiers: KModifier): ParameterSpec =
        type.name.asParameterSpec(name, *modifiers).also(::parameter)

    fun parametering(
        type: TypeName,
        vararg modifiers: KModifier,
    ): SpecDelegateProvider<ParameterSpec> =
        SpecDelegateProvider { type.asParameterSpec(it, *modifiers).also(::parameter) }

    @OptIn(DelicateKotlinPoetApi::class)
    fun parametering(
        type: Class<*>,
        vararg modifiers: KModifier,
    ): SpecDelegateProvider<ParameterSpec> =
        SpecDelegateProvider { type.name2.asParameterSpec(it, *modifiers).also(::parameter) }

    fun parametering(
        type: KClass<*>,
        vararg modifiers: KModifier,
    ): SpecDelegateProvider<ParameterSpec> =
        SpecDelegateProvider { type.name.asParameterSpec(it, *modifiers).also(::parameter) }
}

/**
 * Receiver for the `parameters` block providing an extended set of operators for the
 * configuration.
 */
@KotlinpoetDsl
open class ParameterSpecHandlerScope private constructor(
    handler: ParameterSpecHandler,
) : ParameterSpecHandler by handler {
    companion object {
        fun of(handler: ParameterSpecHandler): ParameterSpecHandlerScope =
            ParameterSpecHandlerScope(handler)
    }

    /** @see parameter */
    operator fun String.invoke(
        type: TypeName,
        vararg modifiers: KModifier,
        configuration: ParameterSpecBuilder.() -> Unit,
    ): ParameterSpec =
        buildParameterSpec(this, type, *modifiers, configuration = configuration)
            .also(::parameter)

    /** @see parameter */
    operator fun String.invoke(
        type: Class<*>,
        vararg modifiers: KModifier,
        configuration: ParameterSpecBuilder.() -> Unit,
    ): ParameterSpec =
        buildParameterSpec(this, type.name2, *modifiers, configuration = configuration)
            .also(::parameter)

    /** @see parameter */
    operator fun String.invoke(
        type: KClass<*>,
        vararg modifiers: KModifier,
        configuration: ParameterSpecBuilder.() -> Unit,
    ): ParameterSpec =
        buildParameterSpec(this, type.name, *modifiers, configuration = configuration)
            .also(::parameter)
}

/** Wrapper of [ParameterSpec.Builder], providing DSL support as a replacement to Java builder. */
@KotlinpoetDsl
class ParameterSpecBuilder(
    private val nativeBuilder: ParameterSpec.Builder,
) : AnnotationSpecHandler {
    val modifiers: List<KModifier> get() = nativeBuilder.modifiers
    val kdoc: CodeBlock.Builder get() = nativeBuilder.kdoc
    val tags: MutableMap<KClass<*>, *> get() = nativeBuilder.tags
    val annotations: MutableList<AnnotationSpec> get() = nativeBuilder.annotations

    fun modifiers(vararg modifiers: KModifier) {
        nativeBuilder.addModifiers(*modifiers)
    }

    fun modifiers(modifiers: Iterable<KModifier>) {
        nativeBuilder.addModifiers(modifiers)
    }

    fun defaultValue(format: String, vararg args: Any) {
        nativeBuilder.defaultValue(format, *args)
    }

    var defaultValue: CodeBlock
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
        get() = noGetter()
        set(value) {
            nativeBuilder.defaultValue(value)
        }

    override fun annotation(annotation: AnnotationSpec) {
        nativeBuilder.addAnnotation(annotation)
    }

    fun kdoc(format: String, vararg args: Any) {
        nativeBuilder.addKdoc(format, *args)
    }

    fun kdoc(block: CodeBlock) {
        nativeBuilder.addKdoc(block)
    }

    fun build(): ParameterSpec = nativeBuilder.build()
}
