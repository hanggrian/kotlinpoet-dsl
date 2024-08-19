@file:OptIn(ExperimentalContracts::class)

package com.hanggrian.kotlinpoet

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.DelicateKotlinPoetApi
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeAliasSpec
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeVariableName
import java.lang.reflect.Type
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.reflect.KClass

/** Creates new [TypeAliasSpec] using parameters. */
public inline fun typeAliasSpecOf(name: String, type: TypeName): TypeAliasSpec =
    TypeAliasSpec
        .builder(name, type)
        .build()

/**
 * Builds new [TypeAliasSpec] by populating newly created [TypeAliasSpecBuilder] using provided
 * [configuration].
 */
public inline fun buildTypeAliasSpec(
    name: String,
    type: TypeName,
    configuration: TypeAliasSpecBuilder.() -> Unit,
): TypeAliasSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeAliasSpecBuilder(TypeAliasSpec.builder(name, type))
        .apply(configuration)
        .build()
}

/**
 * Inserts new [TypeAliasSpec] by populating newly created [TypeAliasSpecBuilder] using provided
 * [configuration].
 */
public inline fun TypeAliasSpecHandler.add(
    name: String,
    type: TypeName,
    configuration: TypeAliasSpecBuilder.() -> Unit,
): TypeAliasSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeAliasSpecBuilder(TypeAliasSpec.builder(name, type))
        .apply(configuration)
        .build()
        .also(::add)
}

/**
 * Inserts new [TypeAliasSpec] by populating newly created [TypeAliasSpecBuilder] using provided
 * [configuration].
 */
@OptIn(DelicateKotlinPoetApi::class)
public inline fun TypeAliasSpecHandler.add(
    name: String,
    type: Type,
    configuration: TypeAliasSpecBuilder.() -> Unit,
): TypeAliasSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeAliasSpecBuilder(TypeAliasSpec.builder(name, type))
        .apply(configuration)
        .build()
        .also(::add)
}

/**
 * Inserts new [TypeAliasSpec] by populating newly created [TypeAliasSpecBuilder] using provided
 * [configuration].
 */
public inline fun TypeAliasSpecHandler.add(
    name: String,
    type: KClass<*>,
    configuration: TypeAliasSpecBuilder.() -> Unit,
): TypeAliasSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeAliasSpecBuilder(TypeAliasSpec.builder(name, type))
        .apply(configuration)
        .build()
        .also(::add)
}

/**
 * Property delegate for inserting new [TypeAliasSpec] by populating newly created
 * [TypeAliasSpecBuilder] using provided [configuration].
 */
public fun TypeAliasSpecHandler.adding(
    type: TypeName,
    configuration: TypeAliasSpecBuilder.() -> Unit,
): SpecDelegateProvider<TypeAliasSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider {
        TypeAliasSpecBuilder(TypeAliasSpec.builder(it, type))
            .apply(configuration)
            .build()
            .also(::add)
    }
}

/**
 * Property delegate for inserting new [TypeAliasSpec] by populating newly created
 * [TypeAliasSpecBuilder] using provided [configuration].
 */
@OptIn(DelicateKotlinPoetApi::class)
public fun TypeAliasSpecHandler.adding(
    type: Type,
    configuration: TypeAliasSpecBuilder.() -> Unit,
): SpecDelegateProvider<TypeAliasSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider {
        TypeAliasSpecBuilder(TypeAliasSpec.builder(it, type))
            .apply(configuration)
            .build()
            .also(::add)
    }
}

/**
 * Property delegate for inserting new [TypeAliasSpec] by populating newly created
 * [TypeAliasSpecBuilder] using provided [configuration].
 */
public fun TypeAliasSpecHandler.adding(
    type: KClass<*>,
    configuration: TypeAliasSpecBuilder.() -> Unit,
): SpecDelegateProvider<TypeAliasSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider {
        TypeAliasSpecBuilder(TypeAliasSpec.builder(it, type))
            .apply(configuration)
            .build()
            .also(::add)
    }
}

/** Convenient method to insert [TypeAliasSpec] using reified type. */
public inline fun <reified T> TypeAliasSpecHandler.add(name: String): TypeAliasSpec =
    TypeAliasSpec
        .builder(name, T::class)
        .build()
        .also(::add)

/** Responsible for managing a set of [TypeAliasSpec] instances. */
public interface TypeAliasSpecHandler {
    public fun add(typeAlias: TypeAliasSpec)

    public fun add(name: String, type: TypeName): TypeAliasSpec =
        typeAliasSpecOf(name, type).also(::add)

    public fun add(name: String, type: Type): TypeAliasSpec =
        typeAliasSpecOf(name, type.name).also(::add)

    public fun add(name: String, type: KClass<*>): TypeAliasSpec =
        typeAliasSpecOf(name, type.name).also(::add)

    public fun adding(type: TypeName): SpecDelegateProvider<TypeAliasSpec> =
        SpecDelegateProvider { typeAliasSpecOf(it, type).also(::add) }

    public fun adding(type: Type): SpecDelegateProvider<TypeAliasSpec> =
        SpecDelegateProvider { typeAliasSpecOf(it, type.name).also(::add) }

    public fun adding(type: KClass<*>): SpecDelegateProvider<TypeAliasSpec> =
        SpecDelegateProvider { typeAliasSpecOf(it, type.name).also(::add) }
}

/**
 * Receiver for the `typeAliases` block providing an extended set of operators for the
 * configuration.
 */
@KotlinPoetDsl
public open class TypeAliasSpecHandlerScope private constructor(handler: TypeAliasSpecHandler) :
    TypeAliasSpecHandler by handler {
        public inline operator fun String.invoke(
            type: TypeName,
            configuration: TypeAliasSpecBuilder.() -> Unit,
        ): TypeAliasSpec = add(this, type, configuration)

        public inline operator fun String.invoke(
            type: Type,
            configuration: TypeAliasSpecBuilder.() -> Unit,
        ): TypeAliasSpec = add(this, type.name, configuration)

        public inline operator fun String.invoke(
            type: KClass<*>,
            configuration: TypeAliasSpecBuilder.() -> Unit,
        ): TypeAliasSpec = add(this, type.name, configuration)

        public companion object {
            public fun of(handler: TypeAliasSpecHandler): TypeAliasSpecHandlerScope =
                TypeAliasSpecHandlerScope(handler)
        }
    }

/** Wrapper of [TypeAliasSpec.Builder], providing DSL support as a replacement to Java builder. */
@KotlinPoetDsl
public class TypeAliasSpecBuilder(private val nativeBuilder: TypeAliasSpec.Builder) {
    public val annotations: AnnotationSpecHandler =
        object : AnnotationSpecHandler {
            override fun add(annotation: AnnotationSpec) {
                annotationSpecs += annotation
            }
        }

    /** Invokes DSL to configure [AnnotationSpec] collection. */
    public inline fun annotations(configuration: AnnotationSpecHandlerScope.() -> Unit) {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        AnnotationSpecHandlerScope
            .of(annotations)
            .configuration()
    }

    public val modifiers: MutableSet<KModifier> get() = nativeBuilder.modifiers
    public val typeVariables: MutableSet<TypeVariableName> get() = nativeBuilder.typeVariables
    public val tags: MutableMap<KClass<*>, *> get() = nativeBuilder.tags
    public val kdoc: CodeBlock.Builder get() = nativeBuilder.kdoc
    public val annotationSpecs: MutableList<AnnotationSpec> get() = nativeBuilder.annotations

    public fun addModifiers(vararg modifiers: KModifier) {
        this.modifiers += modifiers
    }

    public fun addTypeVariables(vararg typeVariables: TypeVariableName) {
        this.typeVariables += typeVariables
    }

    public fun addKdoc(format: String, vararg args: Any) {
        nativeBuilder.addKdoc(format, *args)
    }

    public fun addKdoc(block: CodeBlock) {
        nativeBuilder.addKdoc(block)
    }

    public fun build(): TypeAliasSpec = nativeBuilder.build()
}
