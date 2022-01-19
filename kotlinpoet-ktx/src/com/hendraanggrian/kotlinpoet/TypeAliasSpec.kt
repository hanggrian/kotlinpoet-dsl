package com.hendraanggrian.kotlinpoet

import com.hendraanggrian.kotlinpoet.collections.AnnotationSpecCollection
import com.hendraanggrian.kotlinpoet.collections.AnnotationSpecCollectionScope
import com.hendraanggrian.kotlinpoet.collections.KdocCollection
import com.hendraanggrian.kotlinpoet.collections.KdocCollectionScope
import com.hendraanggrian.kotlinpoet.collections.TypeVariableNameCollection
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeAliasSpec
import com.squareup.kotlinpoet.TypeName
import java.lang.reflect.Type
import kotlin.reflect.KClass

/** Builds new [TypeAliasSpec] from name and [TypeName]. */
fun typeAliasSpecOf(name: String, type: TypeName): TypeAliasSpec = TypeAliasSpec.builder(name, type).build()

/** Builds new [TypeAliasSpec] from name and [Type]. */
fun typeAliasSpecOf(name: String, type: Type): TypeAliasSpec = TypeAliasSpec.builder(name, type).build()

/** Builds new [TypeAliasSpec] from name and [KClass]. */
fun typeAliasSpecOf(name: String, type: KClass<*>): TypeAliasSpec = TypeAliasSpec.builder(name, type).build()

/** Builds new [TypeAliasSpec] from name and [T]. */
inline fun <reified T> typeAliasSpecOf(name: String): TypeAliasSpec =
    TypeAliasSpec.builder(name, T::class).build()

/**
 * Builds new [TypeAliasSpec] from name and [TypeName],
 * by populating newly created [TypeAliasSpecBuilder] using provided [configuration].
 */
fun buildTypeAliasSpec(
    name: String,
    type: TypeName,
    configuration: TypeAliasSpecBuilder.() -> Unit
): TypeAliasSpec = TypeAliasSpecBuilder(TypeAliasSpec.builder(name, type)).apply(configuration).build()

/**
 * Builds new [TypeAliasSpec] from name and [Type],
 * by populating newly created [TypeAliasSpecBuilder] using provided [configuration].
 */
fun buildTypeAliasSpec(
    name: String,
    type: Type,
    configuration: TypeAliasSpecBuilder.() -> Unit
): TypeAliasSpec = TypeAliasSpecBuilder(TypeAliasSpec.builder(name, type)).apply(configuration).build()

/**
 * Builds new [TypeAliasSpec] from name and [KClass],
 * by populating newly created [TypeAliasSpecBuilder] using provided [configuration].
 */
fun buildTypeAliasSpec(
    name: String,
    type: KClass<*>,
    configuration: TypeAliasSpecBuilder.() -> Unit
): TypeAliasSpec = TypeAliasSpecBuilder(TypeAliasSpec.builder(name, type)).apply(configuration).build()

/**
 * Builds new [TypeAliasSpec] from name and [T],
 * by populating newly created [TypeAliasSpecBuilder] using provided [configuration].
 */
inline fun <reified T> buildTypeAliasSpec(
    name: String,
    noinline configuration: TypeAliasSpecBuilder.() -> Unit
): TypeAliasSpec = buildTypeAliasSpec(name, T::class, configuration)

/** Modify existing [TypeAliasSpec.Builder] using provided [configuration]. */
fun TypeAliasSpec.Builder.edit(configuration: TypeAliasSpecBuilder.() -> Unit): TypeAliasSpec.Builder =
    TypeAliasSpecBuilder(this).apply(configuration).nativeBuilder

/**
 * Wrapper of [TypeAliasSpec.Builder], providing DSL support as a replacement to Java builder.
 * @param nativeBuilder source builder.
 */
@SpecMarker
class TypeAliasSpecBuilder internal constructor(val nativeBuilder: TypeAliasSpec.Builder) {

    /** Modifiers of this type alias. */
    val modifiers: MutableSet<KModifier> get() = nativeBuilder.modifiers

    /** Tags variables of this type alias. */
    val tags: MutableMap<KClass<*>, *> get() = nativeBuilder.tags

    /** Add type-alias modifiers. */
    fun addModifiers(vararg modifiers: KModifier) {
        nativeBuilder.addModifiers(*modifiers)
    }

    /** Type variables of this type alias. */
    val typeVariables: TypeVariableNameCollection = TypeVariableNameCollection(nativeBuilder.typeVariables)

    /** Configures type variables of this type alias. */
    fun typeVariables(configuration: TypeVariableNameCollection.() -> Unit): Unit = typeVariables.configuration()

    /** Annotations of this type alias. */
    val annotations: AnnotationSpecCollection = AnnotationSpecCollection(nativeBuilder.annotations)

    /** Configures annotations of this type alias. */
    fun annotations(configuration: AnnotationSpecCollectionScope.() -> Unit): Unit =
        AnnotationSpecCollectionScope(annotations).configuration()

    /** Kdoc of this type alias. */
    val kdoc: KdocCollection = object : KdocCollection() {
        override fun append(format: String, vararg args: Any) {
            nativeBuilder.addKdoc(format, *args)
        }

        override fun append(code: CodeBlock) {
            nativeBuilder.addKdoc(code)
        }
    }

    /** Configures kdoc of this type alias. */
    fun kdoc(configuration: KdocCollectionScope.() -> Unit): Unit = KdocCollectionScope(kdoc).configuration()

    /** Returns native spec. */
    fun build(): TypeAliasSpec = nativeBuilder.build()
}
