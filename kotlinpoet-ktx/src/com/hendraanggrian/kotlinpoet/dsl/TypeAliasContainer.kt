package com.hendraanggrian.kotlinpoet.dsl

import com.hendraanggrian.kotlinpoet.KotlinpoetDslMarker
import com.hendraanggrian.kotlinpoet.TypeAliasSpecBuilder
import com.hendraanggrian.kotlinpoet.buildTypeAlias
import com.squareup.kotlinpoet.TypeAliasSpec
import com.squareup.kotlinpoet.TypeName
import java.lang.reflect.Type
import kotlin.reflect.KClass

private interface TypeAliasAddable {

    /** Add type alias to this container, returning the type alias added. */
    fun add(spec: TypeAliasSpec): TypeAliasSpec
}

/** An [TypeAliasContainer] is responsible for managing a set of type alias instances. */
abstract class TypeAliasContainer internal constructor() : TypeAliasAddable {

    /** Add type alias from [name] and [type], returning the type alias added. */
    fun add(name: String, type: TypeName): TypeAliasSpec =
        add(buildTypeAlias(name, type))

    /** Add type alias from [name] and [type] with custom initialization [builderAction], returning the type alias added. */
    inline fun add(name: String, type: TypeName, builderAction: TypeAliasSpecBuilder.() -> Unit): TypeAliasSpec =
        add(buildTypeAlias(name, type, builderAction))

    /** Add type alias from [name] and [type], returning the type alias added. */
    fun add(name: String, type: Type): TypeAliasSpec =
        add(buildTypeAlias(name, type))

    /** Add type alias from [name] and [type] with custom initialization [builderAction], returning the type alias added. */
    inline fun add(
        name: String,
        type: Type,
        builderAction: TypeAliasSpecBuilder.() -> Unit
    ): TypeAliasSpec = add(buildTypeAlias(name, type, builderAction))

    /** Add type alias from [name] and [type], returning the type alias added. */
    fun add(name: String, type: KClass<*>): TypeAliasSpec =
        add(buildTypeAlias(name, type))

    /** Add type alias from [name] and [type] with custom initialization [builderAction], returning the type alias added. */
    inline fun add(name: String, type: KClass<*>, builderAction: TypeAliasSpecBuilder.() -> Unit): TypeAliasSpec =
        add(buildTypeAlias(name, type, builderAction))

    /** Add type alias from [name] and reified [T], returning the type alias added. */
    inline fun <reified T> add(name: String): TypeAliasSpec =
        add(buildTypeAlias<T>(name))

    /** Add type alias from [name] and reified [T] with custom initialization [builderAction], returning the type alias added. */
    inline fun <reified T> add(
        name: String,
        builderAction: TypeAliasSpecBuilder.() -> Unit
    ): TypeAliasSpec = add(buildTypeAlias<T>(name, builderAction))

    /** Convenient method to add type alias with operator function. */
    operator fun plusAssign(spec: TypeAliasSpec) {
        add(spec)
    }

    /** Configure this container with DSL. */
    inline operator fun invoke(configuration: TypeAliasContainerScope.() -> Unit) =
        TypeAliasContainerScope(this).configuration()
}

/** Receiver for the `typeAliases` block providing an extended set of operators for the configuration. */
@KotlinpoetDslMarker
class TypeAliasContainerScope @PublishedApi internal constructor(container: TypeAliasContainer) :
    TypeAliasContainer(), TypeAliasAddable by container {

    /** Convenient method to add type alias with receiver type. */
    inline operator fun String.invoke(type: TypeName, builderAction: TypeAliasSpecBuilder.() -> Unit): TypeAliasSpec =
        add(this, type, builderAction)

    /** Convenient method to add type alias with receiver type. */
    inline operator fun String.invoke(type: Type, builderAction: TypeAliasSpecBuilder.() -> Unit): TypeAliasSpec =
        add(this, type, builderAction)

    /** Convenient method to add type alias with receiver type. */
    inline operator fun String.invoke(type: KClass<*>, builderAction: TypeAliasSpecBuilder.() -> Unit): TypeAliasSpec =
        add(this, type, builderAction)

    /** Convenient method to add type alias with receiver type. */
    inline operator fun <reified T> String.invoke(builderAction: TypeAliasSpecBuilder.() -> Unit): TypeAliasSpec =
        add(this, T::class, builderAction)
}
