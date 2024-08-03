@file:OptIn(ExperimentalContracts::class)

package com.hanggrian.kotlinpoet

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

/** Creates new [ParameterSpec] using parameters. */
public inline fun parameterSpecOf(
    name: String,
    type: TypeName,
    vararg modifiers: KModifier,
): ParameterSpec =
    ParameterSpec
        .builder(name, type, *modifiers)
        .build()

/**
 * Builds new [ParameterSpec] by populating newly created [ParameterSpecBuilder] using provided
 * [configuration].
 */
public fun buildParameterSpec(
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
public fun ParameterSpecHandler.parameter(
    name: String,
    type: TypeName,
    vararg modifiers: KModifier,
    configuration: ParameterSpecBuilder.() -> Unit,
): ParameterSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return ParameterSpecBuilder(ParameterSpec.builder(name, type, *modifiers))
        .apply(configuration)
        .build()
        .also(::parameter)
}

/**
 * Inserts new [ParameterSpec] by populating newly created [ParameterSpecBuilder] using provided
 * [configuration].
 */
public fun ParameterSpecHandler.parameter(
    name: String,
    type: Class<*>,
    vararg modifiers: KModifier,
    configuration: ParameterSpecBuilder.() -> Unit,
): ParameterSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return ParameterSpecBuilder(ParameterSpec.builder(name, type, *modifiers))
        .apply(configuration)
        .build()
        .also(::parameter)
}

/**
 * Inserts new [ParameterSpec] by populating newly created [ParameterSpecBuilder] using provided
 * [configuration].
 */
public fun ParameterSpecHandler.parameter(
    name: String,
    type: KClass<*>,
    vararg modifiers: KModifier,
    configuration: ParameterSpecBuilder.() -> Unit,
): ParameterSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return ParameterSpecBuilder(ParameterSpec.builder(name, type, *modifiers))
        .apply(configuration)
        .build()
        .also(::parameter)
}

/**
 * Property delegate for inserting new [ParameterSpec] by populating newly created
 * [ParameterSpecBuilder] using provided [configuration].
 */
public fun ParameterSpecHandler.parametering(
    type: TypeName,
    vararg modifiers: KModifier,
    configuration: ParameterSpecBuilder.() -> Unit,
): SpecDelegateProvider<ParameterSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider {
        ParameterSpecBuilder(ParameterSpec.builder(it, type, *modifiers))
            .apply(configuration)
            .build()
            .also(::parameter)
    }
}

/**
 * Property delegate for inserting new [ParameterSpec] by populating newly created
 * [ParameterSpecBuilder] using provided [configuration].
 */
public fun ParameterSpecHandler.parametering(
    type: Class<*>,
    vararg modifiers: KModifier,
    configuration: ParameterSpecBuilder.() -> Unit,
): SpecDelegateProvider<ParameterSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider {
        ParameterSpecBuilder(ParameterSpec.builder(it, type, *modifiers))
            .apply(configuration)
            .build()
            .also(::parameter)
    }
}

/**
 * Property delegate for inserting new [ParameterSpec] by populating newly created
 * [ParameterSpecBuilder] using provided [configuration].
 */
public fun ParameterSpecHandler.parametering(
    type: KClass<*>,
    vararg modifiers: KModifier,
    configuration: ParameterSpecBuilder.() -> Unit,
): SpecDelegateProvider<ParameterSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider {
        ParameterSpecBuilder(ParameterSpec.builder(it, type, *modifiers))
            .apply(configuration)
            .build()
            .also(::parameter)
    }
}

/** Convenient method to insert [ParameterSpec] using reified type. */
public inline fun <reified T> ParameterSpecHandler.parameter(
    name: String,
    vararg modifiers: KModifier,
): ParameterSpec =
    ParameterSpec
        .builder(name, T::class, *modifiers)
        .build()
        .also(::parameter)

/** Invokes DSL to configure [ParameterSpec] collection. */
public fun ParameterSpecHandler.parameters(configuration: ParameterSpecHandlerScope.() -> Unit) {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    ParameterSpecHandlerScope
        .of(this)
        .configuration()
}

/** Responsible for managing a set of [ParameterSpec] instances. */
public interface ParameterSpecHandler {
    public fun parameter(parameter: ParameterSpec)

    public fun parameter(name: String, type: TypeName, vararg modifiers: KModifier): ParameterSpec =
        parameterSpecOf(name, type, *modifiers).also(::parameter)

    @OptIn(DelicateKotlinPoetApi::class)
    public fun parameter(name: String, type: Class<*>, vararg modifiers: KModifier): ParameterSpec =
        parameterSpecOf(name, type.name2, *modifiers).also(::parameter)

    public fun parameter(
        name: String,
        type: KClass<*>,
        vararg modifiers: KModifier,
    ): ParameterSpec = parameterSpecOf(name, type.name, *modifiers).also(::parameter)

    public fun parametering(
        type: TypeName,
        vararg modifiers: KModifier,
    ): SpecDelegateProvider<ParameterSpec> =
        SpecDelegateProvider { parameterSpecOf(it, type, *modifiers).also(::parameter) }

    @OptIn(DelicateKotlinPoetApi::class)
    public fun parametering(
        type: Class<*>,
        vararg modifiers: KModifier,
    ): SpecDelegateProvider<ParameterSpec> =
        SpecDelegateProvider { parameterSpecOf(it, type.name2, *modifiers).also(::parameter) }

    public fun parametering(
        type: KClass<*>,
        vararg modifiers: KModifier,
    ): SpecDelegateProvider<ParameterSpec> =
        SpecDelegateProvider { parameterSpecOf(it, type.name, *modifiers).also(::parameter) }
}

/**
 * Receiver for the `parameters` block providing an extended set of operators for the
 * configuration.
 */
@KotlinpoetDsl
public open class ParameterSpecHandlerScope private constructor(handler: ParameterSpecHandler) :
    ParameterSpecHandler by handler {
        /**
         * @see parameter
         */
        public operator fun String.invoke(
            type: TypeName,
            vararg modifiers: KModifier,
            configuration: ParameterSpecBuilder.() -> Unit,
        ): ParameterSpec = parameter(this, type, *modifiers, configuration = configuration)

        /**
         * @see parameter
         */
        public operator fun String.invoke(
            type: Class<*>,
            vararg modifiers: KModifier,
            configuration: ParameterSpecBuilder.() -> Unit,
        ): ParameterSpec = parameter(this, type, *modifiers, configuration = configuration)

        /**
         * @see parameter
         */
        public operator fun String.invoke(
            type: KClass<*>,
            vararg modifiers: KModifier,
            configuration: ParameterSpecBuilder.() -> Unit,
        ): ParameterSpec = parameter(this, type, *modifiers, configuration = configuration)

        public companion object {
            public fun of(handler: ParameterSpecHandler): ParameterSpecHandlerScope =
                ParameterSpecHandlerScope(handler)
        }
    }

/** Wrapper of [ParameterSpec.Builder], providing DSL support as a replacement to Java builder. */
@KotlinpoetDsl
public class ParameterSpecBuilder(private val nativeBuilder: ParameterSpec.Builder) :
    AnnotationSpecHandler {
    public val modifiers: List<KModifier> get() = nativeBuilder.modifiers
    public val kdoc: CodeBlock.Builder get() = nativeBuilder.kdoc
    public val tags: MutableMap<KClass<*>, *> get() = nativeBuilder.tags
    public val annotations: MutableList<AnnotationSpec> get() = nativeBuilder.annotations

    public fun modifiers(vararg modifiers: KModifier) {
        nativeBuilder.addModifiers(*modifiers)
    }

    public fun modifiers(modifiers: Iterable<KModifier>) {
        nativeBuilder.addModifiers(modifiers)
    }

    public fun defaultValue(format: String, vararg args: Any) {
        nativeBuilder.defaultValue(format, *args)
    }

    public var defaultValue: CodeBlock
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
        get() = noGetter()
        set(value) {
            nativeBuilder.defaultValue(value)
        }

    public override fun annotation(annotation: AnnotationSpec) {
        nativeBuilder.addAnnotation(annotation)
    }

    public fun kdoc(format: String, vararg args: Any) {
        nativeBuilder.addKdoc(format, *args)
    }

    public fun kdoc(block: CodeBlock) {
        nativeBuilder.addKdoc(block)
    }

    public fun build(): ParameterSpec = nativeBuilder.build()
}
