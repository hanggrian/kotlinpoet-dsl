package com.hendraanggrian.kotlinpoet

import com.hendraanggrian.kotlinpoet.dsl.KdocContainer
import com.hendraanggrian.kotlinpoet.dsl.KdocContainerScope
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeAliasSpec
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeVariableName
import java.lang.reflect.Type
import kotlin.reflect.KClass

/** Builds a new [TypeAliasSpec] from [name] and [type]. */
fun typeAliasSpecOf(name: String, type: TypeName): TypeAliasSpec = TypeAliasSpec.builder(name, type).build()

/**
 * Builds a new [TypeAliasSpec] from [name] and [type],
 * by populating newly created [TypeAliasSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildTypeAliasSpec(
    name: String,
    type: TypeName,
    builderAction: TypeAliasSpecBuilder.() -> Unit
): TypeAliasSpec = TypeAliasSpec.builder(name, type).build(builderAction)

/** Builds a new [TypeAliasSpec] from [name] and [type]. */
fun typeAliasSpecOf(name: String, type: Type): TypeAliasSpec = TypeAliasSpec.builder(name, type).build()

/** Builds a new [TypeAliasSpec] from [name] and [type]. */
fun typeAliasSpecOf(name: String, type: KClass<*>): TypeAliasSpec = TypeAliasSpec.builder(name, type).build()

/** Builds a new [TypeAliasSpec] from [name] and [T]. */
inline fun <reified T> typeAliasSpecOf(name: String): TypeAliasSpec = typeAliasSpecOf(name, T::class)

/**
 * Builds a new [TypeAliasSpec] from [name] and [type],
 * by populating newly created [TypeAliasSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildTypeAliasSpec(
    name: String,
    type: Type,
    builderAction: TypeAliasSpecBuilder.() -> Unit
): TypeAliasSpec = TypeAliasSpec.builder(name, type).build(builderAction)

/**
 * Builds a new [TypeAliasSpec] from [name] and [type],
 * by populating newly created [TypeAliasSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildTypeAliasSpec(
    name: String,
    type: KClass<*>,
    builderAction: TypeAliasSpecBuilder.() -> Unit
): TypeAliasSpec = TypeAliasSpec.builder(name, type).build(builderAction)

/**
 * Builds a new [TypeAliasSpec] from [name] and [T],
 * by populating newly created [TypeAliasSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun <reified T> buildTypeAliasSpec(
    name: String,
    builderAction: TypeAliasSpecBuilder.() -> Unit
): TypeAliasSpec = buildTypeAliasSpec(name, T::class, builderAction)

/** Modify existing [TypeAliasSpec.Builder] using provided [builderAction] and then building it. */
inline fun TypeAliasSpec.Builder.build(builderAction: TypeAliasSpecBuilder.() -> Unit): TypeAliasSpec =
    TypeAliasSpecBuilder(this).apply(builderAction).build()

/** Wrapper of [TypeAliasSpec.Builder], providing DSL support as a replacement to Java builder. */
@KotlinpoetDslMarker
class TypeAliasSpecBuilder @PublishedApi internal constructor(private val nativeBuilder: TypeAliasSpec.Builder) {

    /** Modifiers of this type alias. */
    val modifiers: MutableSet<KModifier> get() = nativeBuilder.modifiers

    /** Type variables of this type alias. */
    val typeVariables: MutableSet<TypeVariableName> get() = nativeBuilder.typeVariables

    /** Annotations of this type alias. */
    val annotationSpecs: MutableList<AnnotationSpec> get() = nativeBuilder.annotations

    /** Tags variables of this type alias. */
    val tags: MutableMap<KClass<*>, *> get() = nativeBuilder.tags

    /** Add type-alias modifiers. */
    fun addModifiers(vararg modifiers: KModifier) {
        nativeBuilder.addModifiers(*modifiers)
    }

    /** Add type variables. */
    fun addTypeVariables(typeVariables: Iterable<TypeVariableName>) {
        nativeBuilder.addTypeVariables(typeVariables)
    }

    /** Add type variables. */
    fun addTypeVariable(typeVariable: TypeVariableName) {
        nativeBuilder.addTypeVariable(typeVariable)
    }

    /** Configure kdoc without DSL. */
    val kdoc: KdocContainer = object : KdocContainer() {
        override fun append(format: String, vararg args: Any) {
            nativeBuilder.addKdoc(format, *args)
        }

        override fun append(code: CodeBlock) {
            nativeBuilder.addKdoc(code)
        }
    }

    /** Configure kdoc with DSL. */
    inline fun kdoc(configuration: KdocContainerScope.() -> Unit) =
        KdocContainerScope(kdoc).configuration()

    /** Returns native spec. */
    fun build(): TypeAliasSpec = nativeBuilder.build()
}
