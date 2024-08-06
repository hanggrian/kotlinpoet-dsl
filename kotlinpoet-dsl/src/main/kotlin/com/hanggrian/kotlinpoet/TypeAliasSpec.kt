@file:OptIn(ExperimentalContracts::class)

package com.hanggrian.kotlinpoet

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.DelicateKotlinPoetApi
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeAliasSpec
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeVariableName
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
public inline fun TypeAliasSpecHandler.typeAlias(
    name: String,
    type: TypeName,
    configuration: TypeAliasSpecBuilder.() -> Unit,
): TypeAliasSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeAliasSpecBuilder(TypeAliasSpec.builder(name, type))
        .apply(configuration)
        .build()
        .also(::typeAlias)
}

/**
 * Inserts new [TypeAliasSpec] by populating newly created [TypeAliasSpecBuilder] using provided
 * [configuration].
 */
@OptIn(DelicateKotlinPoetApi::class)
public inline fun TypeAliasSpecHandler.typeAlias(
    name: String,
    type: Class<*>,
    configuration: TypeAliasSpecBuilder.() -> Unit,
): TypeAliasSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeAliasSpecBuilder(TypeAliasSpec.builder(name, type))
        .apply(configuration)
        .build()
        .also(::typeAlias)
}

/**
 * Inserts new [TypeAliasSpec] by populating newly created [TypeAliasSpecBuilder] using provided
 * [configuration].
 */
public inline fun TypeAliasSpecHandler.typeAlias(
    name: String,
    type: KClass<*>,
    configuration: TypeAliasSpecBuilder.() -> Unit,
): TypeAliasSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeAliasSpecBuilder(TypeAliasSpec.builder(name, type))
        .apply(configuration)
        .build()
        .also(::typeAlias)
}

/**
 * Property delegate for inserting new [TypeAliasSpec] by populating newly created
 * [TypeAliasSpecBuilder] using provided [configuration].
 */
public fun TypeAliasSpecHandler.typeAliasing(
    type: TypeName,
    configuration: TypeAliasSpecBuilder.() -> Unit,
): SpecDelegateProvider<TypeAliasSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider {
        TypeAliasSpecBuilder(TypeAliasSpec.builder(it, type))
            .apply(configuration)
            .build()
            .also(::typeAlias)
    }
}

/**
 * Property delegate for inserting new [TypeAliasSpec] by populating newly created
 * [TypeAliasSpecBuilder] using provided [configuration].
 */
@OptIn(DelicateKotlinPoetApi::class)
public fun TypeAliasSpecHandler.typeAliasing(
    type: Class<*>,
    configuration: TypeAliasSpecBuilder.() -> Unit,
): SpecDelegateProvider<TypeAliasSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider {
        TypeAliasSpecBuilder(TypeAliasSpec.builder(it, type))
            .apply(configuration)
            .build()
            .also(::typeAlias)
    }
}

/**
 * Property delegate for inserting new [TypeAliasSpec] by populating newly created
 * [TypeAliasSpecBuilder] using provided [configuration].
 */
public fun TypeAliasSpecHandler.typeAliasing(
    type: KClass<*>,
    configuration: TypeAliasSpecBuilder.() -> Unit,
): SpecDelegateProvider<TypeAliasSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider {
        TypeAliasSpecBuilder(TypeAliasSpec.builder(it, type))
            .apply(configuration)
            .build()
            .also(::typeAlias)
    }
}

/** Convenient method to insert [TypeAliasSpec] using reified type. */
public inline fun <reified T> TypeAliasSpecHandler.typeAlias(name: String): TypeAliasSpec =
    TypeAliasSpec
        .builder(name, T::class)
        .build()
        .also(::typeAlias)

/** Invokes DSL to configure [TypeAliasSpec] collection. */
public fun TypeAliasSpecHandler.typeAliases(configuration: TypeAliasSpecHandlerScope.() -> Unit) {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    TypeAliasSpecHandlerScope
        .of(this)
        .configuration()
}

/** Responsible for managing a set of [TypeAliasSpec] instances. */
public interface TypeAliasSpecHandler {
    public fun typeAlias(typeAlias: TypeAliasSpec)

    public fun typeAlias(name: String, type: TypeName): TypeAliasSpec =
        typeAliasSpecOf(name, type).also(::typeAlias)

    @DelicateKotlinPoetApi(DELICATE_API)
    public fun typeAlias(name: String, type: Class<*>): TypeAliasSpec =
        typeAliasSpecOf(name, type.name2).also(::typeAlias)

    public fun typeAlias(name: String, type: KClass<*>): TypeAliasSpec =
        typeAliasSpecOf(name, type.name).also(::typeAlias)

    public fun typeAliasing(type: TypeName): SpecDelegateProvider<TypeAliasSpec> =
        SpecDelegateProvider { typeAliasSpecOf(it, type).also(::typeAlias) }

    @DelicateKotlinPoetApi(DELICATE_API)
    public fun TypeAliasSpecHandler.typeAliasing(
        type: Class<*>,
    ): SpecDelegateProvider<TypeAliasSpec> =
        SpecDelegateProvider { typeAliasSpecOf(it, type.name2).also(::typeAlias) }

    public fun TypeAliasSpecHandler.typeAliasing(
        type: KClass<*>,
    ): SpecDelegateProvider<TypeAliasSpec> =
        SpecDelegateProvider { typeAliasSpecOf(it, type.name).also(::typeAlias) }
}

/**
 * Receiver for the `typeAliases` block providing an extended set of operators for the
 * configuration.
 */
@KotlinpoetDsl
public open class TypeAliasSpecHandlerScope private constructor(handler: TypeAliasSpecHandler) :
    TypeAliasSpecHandler by handler {
        /**
         * @see typeAlias
         */
        public operator fun String.invoke(
            type: TypeName,
            configuration: TypeAliasSpecBuilder.() -> Unit,
        ): TypeAliasSpec = typeAlias(this, type, configuration)

        /**
         * @see typeAlias
         */
        @DelicateKotlinPoetApi(DELICATE_API)
        public operator fun String.invoke(
            type: Class<*>,
            configuration: TypeAliasSpecBuilder.() -> Unit,
        ): TypeAliasSpec = typeAlias(this, type.name2, configuration)

        /**
         * @see typeAlias
         */
        public operator fun String.invoke(
            type: KClass<*>,
            configuration: TypeAliasSpecBuilder.() -> Unit,
        ): TypeAliasSpec = typeAlias(this, type.name, configuration)

        public companion object {
            public fun of(handler: TypeAliasSpecHandler): TypeAliasSpecHandlerScope =
                TypeAliasSpecHandlerScope(handler)
        }
    }

/** Wrapper of [TypeAliasSpec.Builder], providing DSL support as a replacement to Java builder. */
@KotlinpoetDsl
public class TypeAliasSpecBuilder(private val nativeBuilder: TypeAliasSpec.Builder) :
    AnnotationSpecHandler {
    public val modifiers: MutableSet<KModifier> get() = nativeBuilder.modifiers
    public val typeVariables: MutableSet<TypeVariableName> get() = nativeBuilder.typeVariables
    public val tags: MutableMap<KClass<*>, *> get() = nativeBuilder.tags
    public val kdoc: CodeBlock.Builder get() = nativeBuilder.kdoc
    public val annotations: MutableList<AnnotationSpec> get() = nativeBuilder.annotations

    public fun modifiers(vararg modifiers: KModifier) {
        nativeBuilder.addModifiers(*modifiers)
    }

    public fun typeVariables(typeVariables: Iterable<TypeVariableName>) {
        nativeBuilder.addTypeVariables(typeVariables)
    }

    public fun typeVariable(typeVariable: TypeVariableName) {
        nativeBuilder.addTypeVariable(typeVariable)
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

    public fun build(): TypeAliasSpec = nativeBuilder.build()
}
