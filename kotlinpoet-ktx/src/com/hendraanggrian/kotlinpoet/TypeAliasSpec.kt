package com.hendraanggrian.kotlinpoet

import com.hendraanggrian.kotlinpoet.collections.AnnotationSpecList
import com.hendraanggrian.kotlinpoet.collections.AnnotationSpecListScope
import com.hendraanggrian.kotlinpoet.collections.KdocContainer
import com.hendraanggrian.kotlinpoet.collections.KdocContainerScope
import com.hendraanggrian.kotlinpoet.collections.TypeVariableNameSet
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
 * by populating newly created [TypeAliasSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildTypeAliasSpec(
    name: String,
    type: TypeName,
    builderAction: TypeAliasSpecBuilder.() -> Unit
): TypeAliasSpec = TypeAliasSpec.builder(name, type).build(builderAction)

/**
 * Builds new [TypeAliasSpec] from name and [Type],
 * by populating newly created [TypeAliasSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildTypeAliasSpec(
    name: String,
    type: Type,
    builderAction: TypeAliasSpecBuilder.() -> Unit
): TypeAliasSpec = TypeAliasSpec.builder(name, type).build(builderAction)

/**
 * Builds new [TypeAliasSpec] from name and [KClass],
 * by populating newly created [TypeAliasSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildTypeAliasSpec(
    name: String,
    type: KClass<*>,
    builderAction: TypeAliasSpecBuilder.() -> Unit
): TypeAliasSpec = TypeAliasSpec.builder(name, type).build(builderAction)

/**
 * Builds new [TypeAliasSpec] from name and [T],
 * by populating newly created [TypeAliasSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun <reified T> buildTypeAliasSpec(
    name: String,
    builderAction: TypeAliasSpecBuilder.() -> Unit
): TypeAliasSpec = TypeAliasSpec.builder(name, T::class).build(builderAction)

/** Modify existing [TypeAliasSpec.Builder] using provided [builderAction] and then building it. */
inline fun TypeAliasSpec.Builder.build(
    builderAction: TypeAliasSpecBuilder.() -> Unit
): TypeAliasSpec = TypeAliasSpecBuilder(this).apply(builderAction).build()

/** Wrapper of [TypeAliasSpec.Builder], providing DSL support as a replacement to Java builder. */
@KotlinpoetDslMarker
class TypeAliasSpecBuilder(private val nativeBuilder: TypeAliasSpec.Builder) {

    /** Modifiers of this type alias. */
    val modifiers: MutableSet<KModifier> get() = nativeBuilder.modifiers

    /** Tags variables of this type alias. */
    val tags: MutableMap<KClass<*>, *> get() = nativeBuilder.tags

    /** Add type-alias modifiers. */
    fun addModifiers(vararg modifiers: KModifier) {
        nativeBuilder.addModifiers(*modifiers)
    }

    /** Type variables of this type alias. */
    val typeVariables: TypeVariableNameSet = TypeVariableNameSet(nativeBuilder.typeVariables)

    /** Annotations of this type alias. */
    val annotations: AnnotationSpecList = AnnotationSpecList(nativeBuilder.annotations)

    /** Configures annotations of this type alias. */
    inline fun annotations(configuration: AnnotationSpecListScope.() -> Unit): Unit =
        AnnotationSpecListScope(annotations).configuration()

    /** Kdoc of this type alias. */
    val kdoc: KdocContainer = object : KdocContainer() {
        override fun append(format: String, vararg args: Any) {
            nativeBuilder.addKdoc(format, *args)
        }

        override fun append(code: CodeBlock) {
            nativeBuilder.addKdoc(code)
        }
    }

    /** Configures kdoc of this type alias. */
    inline fun kdoc(configuration: KdocContainerScope.() -> Unit): Unit =
        KdocContainerScope(kdoc).configuration()

    /** Returns native spec. */
    fun build(): TypeAliasSpec = nativeBuilder.build()
}
