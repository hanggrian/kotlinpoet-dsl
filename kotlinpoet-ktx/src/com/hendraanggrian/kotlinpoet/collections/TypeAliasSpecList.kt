package com.hendraanggrian.kotlinpoet.collections

import com.hendraanggrian.kotlinpoet.DELICATE_JAVA
import com.hendraanggrian.kotlinpoet.SpecMarker
import com.hendraanggrian.kotlinpoet.TypeAliasSpecBuilder
import com.hendraanggrian.kotlinpoet.buildTypeAliasSpec
import com.hendraanggrian.kotlinpoet.typeAliasSpecOf
import com.squareup.kotlinpoet.DelicateKotlinPoetApi
import com.squareup.kotlinpoet.TypeAliasSpec
import com.squareup.kotlinpoet.TypeName
import java.lang.reflect.Type
import kotlin.reflect.KClass

/** An [TypeAliasSpecList] is responsible for managing a set of type alias instances. */
open class TypeAliasSpecList internal constructor(actualList: MutableList<TypeAliasSpec>) :
    MutableList<TypeAliasSpec> by actualList {

    /** Add type alias from [TypeName]. */
    fun add(name: String, type: TypeName): TypeAliasSpec = typeAliasSpecOf(name, type).also(::add)

    /** Add type alias from [TypeName] with custom initialization [configuration]. */
    fun add(
        name: String,
        type: TypeName,
        configuration: TypeAliasSpecBuilder.() -> Unit
    ): TypeAliasSpec = buildTypeAliasSpec(name, type, configuration).also(::add)

    /** Add type alias from [Type]. */
    @DelicateKotlinPoetApi(DELICATE_JAVA)
    fun add(name: String, type: Type): TypeAliasSpec = typeAliasSpecOf(name, type).also(::add)

    /** Add type alias from [Type] with custom initialization [configuration]. */
    @DelicateKotlinPoetApi(DELICATE_JAVA)
    fun add(
        name: String,
        type: Type,
        configuration: TypeAliasSpecBuilder.() -> Unit
    ): TypeAliasSpec = buildTypeAliasSpec(name, type, configuration).also(::add)

    /** Add type alias from [KClass]. */
    fun add(name: String, type: KClass<*>): TypeAliasSpec = typeAliasSpecOf(name, type).also(::add)

    /** Add type alias from [KClass] with custom initialization [configuration]. */
    fun add(
        name: String,
        type: KClass<*>,
        configuration: TypeAliasSpecBuilder.() -> Unit
    ): TypeAliasSpec = buildTypeAliasSpec(name, type, configuration).also(::add)

    /** Add type alias from [T]. */
    inline fun <reified T> add(name: String): TypeAliasSpec = typeAliasSpecOf<T>(name).also(::add)

    /** Add type alias from [T] with custom initialization [configuration]. */
    inline fun <reified T> add(name: String, noinline configuration: TypeAliasSpecBuilder.() -> Unit): TypeAliasSpec =
        buildTypeAliasSpec<T>(name, configuration).also(::add)

    /** Convenient method to add type alias with operator function. */
    inline operator fun set(name: String, type: TypeName) {
        add(name, type)
    }

    /** Convenient method to add type alias with operator function. */
    inline operator fun set(name: String, type: Type) {
        add(name, type)
    }

    /** Convenient method to add type alias with operator function. */
    inline operator fun set(name: String, type: KClass<*>) {
        add(name, type)
    }
}

/** Receiver for the `typeAliases` block providing an extended set of operators for the configuration. */
@SpecMarker
class TypeAliasSpecListScope internal constructor(actualList: MutableList<TypeAliasSpec>) :
    TypeAliasSpecList(actualList) {

    /** @see TypeAliasSpecList.add */
    operator fun String.invoke(type: TypeName, configuration: TypeAliasSpecBuilder.() -> Unit): TypeAliasSpec =
        add(this, type, configuration)

    /** @see TypeAliasSpecList.add */
    operator fun String.invoke(type: Type, configuration: TypeAliasSpecBuilder.() -> Unit): TypeAliasSpec =
        add(this, type, configuration)

    /** @see TypeAliasSpecList.add */
    operator fun String.invoke(type: KClass<*>, configuration: TypeAliasSpecBuilder.() -> Unit): TypeAliasSpec =
        add(this, type, configuration)

    /** @see TypeAliasSpecList.add */
    inline operator fun <reified T> String.invoke(noinline configuration: TypeAliasSpecBuilder.() -> Unit): TypeAliasSpec =
        add<T>(this, configuration)
}
