package com.hendraanggrian.kotlinpoet.collections

import com.hendraanggrian.kotlinpoet.KotlinpoetDslMarker
import com.hendraanggrian.kotlinpoet.TypeAliasSpecBuilder
import com.hendraanggrian.kotlinpoet.buildTypeAliasSpec
import com.hendraanggrian.kotlinpoet.typeAliasSpecOf
import com.squareup.kotlinpoet.TypeAliasSpec
import com.squareup.kotlinpoet.TypeName
import java.lang.reflect.Type
import kotlin.reflect.KClass

/** An [TypeAliasSpecList] is responsible for managing a set of type alias instances. */
open class TypeAliasSpecList internal constructor(actualList: MutableList<TypeAliasSpec>) :
    MutableList<TypeAliasSpec> by actualList {

    /** Add type alias from [TypeName]. */
    fun add(name: String, type: TypeName): Boolean = add(typeAliasSpecOf(name, type))

    /** Add type alias from [Type]. */
    fun add(name: String, type: Type): Boolean = add(typeAliasSpecOf(name, type))

    /** Add type alias from [KClass]. */
    fun add(name: String, type: KClass<*>): Boolean = add(typeAliasSpecOf(name, type))

    /** Add type alias from [T]. */
    inline fun <reified T> add(name: String): Boolean = add(typeAliasSpecOf<T>(name))

    /** Add type alias from [TypeName] with custom initialization [builderAction]. */
    inline fun add(
        name: String,
        type: TypeName,
        builderAction: TypeAliasSpecBuilder.() -> Unit
    ): Boolean = add(buildTypeAliasSpec(name, type, builderAction))

    /** Add type alias from [Type] with custom initialization [builderAction]. */
    inline fun add(
        name: String,
        type: Type,
        builderAction: TypeAliasSpecBuilder.() -> Unit
    ): Boolean = add(buildTypeAliasSpec(name, type, builderAction))

    /** Add type alias from [KClass] with custom initialization [builderAction]. */
    inline fun add(
        name: String,
        type: KClass<*>,
        builderAction: TypeAliasSpecBuilder.() -> Unit
    ): Boolean = add(buildTypeAliasSpec(name, type, builderAction))

    /** Add type alias from [T] with custom initialization [builderAction]. */
    inline fun <reified T> add(
        name: String,
        builderAction: TypeAliasSpecBuilder.() -> Unit
    ): Boolean = add(buildTypeAliasSpec<T>(name, builderAction))

    /** Convenient method to add type alias with operator function. */
    operator fun set(name: String, type: TypeName): Unit = plusAssign(typeAliasSpecOf(name, type))

    /** Convenient method to add type alias with operator function. */
    operator fun set(name: String, type: Type): Unit = plusAssign(typeAliasSpecOf(name, type))

    /** Convenient method to add type alias with operator function. */
    operator fun set(name: String, type: KClass<*>): Unit = plusAssign(typeAliasSpecOf(name, type))
}

/** Receiver for the `typeAliases` block providing an extended set of operators for the configuration. */
@KotlinpoetDslMarker
class TypeAliasSpecListScope(actualList: MutableList<TypeAliasSpec>) : TypeAliasSpecList(actualList) {

    /** Convenient method to add type alias with receiver type. */
    inline operator fun String.invoke(
        type: TypeName,
        builderAction: TypeAliasSpecBuilder.() -> Unit
    ): Boolean = add(this, type, builderAction)

    /** Convenient method to add type alias with receiver type. */
    inline operator fun String.invoke(
        type: Type,
        builderAction: TypeAliasSpecBuilder.() -> Unit
    ): Boolean = add(this, type, builderAction)

    /** Convenient method to add type alias with receiver type. */
    inline operator fun String.invoke(
        type: KClass<*>,
        builderAction: TypeAliasSpecBuilder.() -> Unit
    ): Boolean = add(this, type, builderAction)

    /** Convenient method to add type alias with receiver type. */
    inline operator fun <reified T> String.invoke(
        builderAction: TypeAliasSpecBuilder.() -> Unit
    ): Boolean = add(this, T::class, builderAction)
}
