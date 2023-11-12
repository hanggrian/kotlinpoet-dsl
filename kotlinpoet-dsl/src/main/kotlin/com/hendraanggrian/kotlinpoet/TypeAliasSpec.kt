@file:OptIn(ExperimentalContracts::class)

package com.hendraanggrian.kotlinpoet

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

inline fun TypeName.asTypeAliasSpec(name: String): TypeAliasSpec =
    TypeAliasSpec.builder(name, this).build()

/**
 * Creates new [TypeAliasSpec] by populating newly created [TypeAliasSpecBuilder] using provided
 * [configuration].
 */
inline fun buildTypeAliasSpec(
    name: String,
    type: TypeName,
    configuration: TypeAliasSpecBuilder.() -> Unit,
): TypeAliasSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeAliasSpecBuilder(TypeAliasSpec.builder(name, type)).apply(configuration).build()
}

/**
 * Inserts new [TypeAliasSpec] by populating newly created [TypeAliasSpecBuilder] using provided
 * [configuration].
 */
inline fun TypeAliasSpecHandler.typeAlias(
    name: String,
    type: TypeName,
    configuration: TypeAliasSpecBuilder.() -> Unit,
): TypeAliasSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return buildTypeAliasSpec(name, type, configuration).also(::typeAlias)
}

/**
 * Inserts new [TypeAliasSpec] by populating newly created [TypeAliasSpecBuilder] using provided
 * [configuration].
 */
inline fun TypeAliasSpecHandler.typeAlias(
    name: String,
    type: Class<*>,
    configuration: TypeAliasSpecBuilder.() -> Unit,
): TypeAliasSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return buildTypeAliasSpec(name, type.name2, configuration).also(::typeAlias)
}

/**
 * Inserts new [TypeAliasSpec] by populating newly created [TypeAliasSpecBuilder] using provided
 * [configuration].
 */
inline fun TypeAliasSpecHandler.typeAlias(
    name: String,
    type: KClass<*>,
    configuration: TypeAliasSpecBuilder.() -> Unit,
): TypeAliasSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return buildTypeAliasSpec(name, type.name, configuration).also(::typeAlias)
}

/**
 * Property delegate for inserting new [TypeAliasSpec] by populating newly created
 * [TypeAliasSpecBuilder] using provided [configuration].
 */
fun TypeAliasSpecHandler.typeAliasing(
    type: TypeName,
    configuration: TypeAliasSpecBuilder.() -> Unit,
): SpecDelegateProvider<TypeAliasSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider {
        buildTypeAliasSpec(it, type, configuration).also(::typeAlias)
    }
}

/**
 * Property delegate for inserting new [TypeAliasSpec] by populating newly created
 * [TypeAliasSpecBuilder] using provided [configuration].
 */
fun TypeAliasSpecHandler.typeAliasing(
    type: Class<*>,
    configuration: TypeAliasSpecBuilder.() -> Unit,
): SpecDelegateProvider<TypeAliasSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider {
        buildTypeAliasSpec(it, type.name2, configuration).also(::typeAlias)
    }
}

/**
 * Property delegate for inserting new [TypeAliasSpec] by populating newly created
 * [TypeAliasSpecBuilder] using provided [configuration].
 */
fun TypeAliasSpecHandler.typeAliasing(
    type: KClass<*>,
    configuration: TypeAliasSpecBuilder.() -> Unit,
): SpecDelegateProvider<TypeAliasSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider {
        buildTypeAliasSpec(it, type.name, configuration).also(::typeAlias)
    }
}

/** Convenient method to insert [TypeAliasSpec] using reified type. */
inline fun <reified T> TypeAliasSpecHandler.typeAlias(name: String): TypeAliasSpec =
    TypeAliasSpecBuilder(TypeAliasSpec.builder(name, T::class)).build().also(::typeAlias)

/** Invokes DSL to configure [TypeAliasSpec] collection. */
fun TypeAliasSpecHandler.typeAliases(configuration: TypeAliasSpecHandlerScope.() -> Unit) {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    TypeAliasSpecHandlerScope.of(this).configuration()
}

/** Responsible for managing a set of [TypeAliasSpec] instances. */
interface TypeAliasSpecHandler {
    fun typeAlias(typeAlias: TypeAliasSpec)

    fun typeAlias(name: String, type: TypeName): TypeAliasSpec =
        type.asTypeAliasSpec(name).also(::typeAlias)

    @DelicateKotlinPoetApi(DELICATE_API)
    fun typeAlias(name: String, type: Class<*>): TypeAliasSpec =
        type.name2.asTypeAliasSpec(name).also(::typeAlias)

    fun typeAlias(name: String, type: KClass<*>): TypeAliasSpec =
        type.name.asTypeAliasSpec(name).also(::typeAlias)

    fun typeAliasing(type: TypeName): SpecDelegateProvider<TypeAliasSpec> =
        SpecDelegateProvider { type.asTypeAliasSpec(it).also(::typeAlias) }

    @DelicateKotlinPoetApi(DELICATE_API)
    fun TypeAliasSpecHandler.typeAliasing(type: Class<*>): SpecDelegateProvider<TypeAliasSpec> =
        SpecDelegateProvider { type.name2.asTypeAliasSpec(it).also(::typeAlias) }

    fun TypeAliasSpecHandler.typeAliasing(type: KClass<*>): SpecDelegateProvider<TypeAliasSpec> =
        SpecDelegateProvider { type.name.asTypeAliasSpec(it).also(::typeAlias) }
}

/**
 * Receiver for the `typeAliases` block providing an extended set of operators for the
 * configuration.
 */
@KotlinpoetDsl
open class TypeAliasSpecHandlerScope private constructor(
    handler: TypeAliasSpecHandler,
) : TypeAliasSpecHandler by handler {
    companion object {
        fun of(handler: TypeAliasSpecHandler): TypeAliasSpecHandlerScope =
            TypeAliasSpecHandlerScope(handler)
    }

    /** @see typeAlias */
    operator fun String.invoke(
        type: TypeName,
        configuration: TypeAliasSpecBuilder.() -> Unit,
    ): TypeAliasSpec = buildTypeAliasSpec(this, type, configuration).also(::typeAlias)

    /** @see typeAlias */
    operator fun String.invoke(
        type: Class<*>,
        configuration: TypeAliasSpecBuilder.() -> Unit,
    ): TypeAliasSpec = buildTypeAliasSpec(this, type.name2, configuration).also(::typeAlias)

    /** @see typeAlias */
    operator fun String.invoke(
        type: KClass<*>,
        configuration: TypeAliasSpecBuilder.() -> Unit,
    ): TypeAliasSpec = buildTypeAliasSpec(this, type.name, configuration).also(::typeAlias)
}

/** Wrapper of [TypeAliasSpec.Builder], providing DSL support as a replacement to Java builder. */
@KotlinpoetDsl
class TypeAliasSpecBuilder(
    private val nativeBuilder: TypeAliasSpec.Builder,
) : AnnotationSpecHandler {
    val modifiers: MutableSet<KModifier> get() = nativeBuilder.modifiers
    val typeVariables: MutableSet<TypeVariableName> get() = nativeBuilder.typeVariables
    val tags: MutableMap<KClass<*>, *> get() = nativeBuilder.tags
    val kdoc: CodeBlock.Builder get() = nativeBuilder.kdoc
    val annotations: MutableList<AnnotationSpec> get() = nativeBuilder.annotations

    fun modifiers(vararg modifiers: KModifier) {
        nativeBuilder.addModifiers(*modifiers)
    }

    fun modifiers(modifiers: Iterable<KModifier>) {
        nativeBuilder.addModifiers(modifiers)
    }

    fun typeVariables(typeVariables: Iterable<TypeVariableName>) {
        nativeBuilder.addTypeVariables(typeVariables)
    }

    fun typeVariable(typeVariable: TypeVariableName) {
        nativeBuilder.addTypeVariable(typeVariable)
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

    fun build(): TypeAliasSpec = nativeBuilder.build()
}
