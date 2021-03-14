package io.github.hendraanggrian.kotlinpoet.dsl

import com.squareup.kotlinpoet.TypeAliasSpec
import com.squareup.kotlinpoet.TypeName
import io.github.hendraanggrian.kotlinpoet.SpecDslMarker
import io.github.hendraanggrian.kotlinpoet.TypeAliasSpecBuilder
import io.github.hendraanggrian.kotlinpoet.buildTypeAliasSpec
import io.github.hendraanggrian.kotlinpoet.typeAliasSpecOf
import java.lang.reflect.Type
import kotlin.reflect.KClass

/** An [TypeAliasSpecHandler] is responsible for managing a set of type alias instances. */
open class TypeAliasSpecHandler internal constructor(actualList: MutableList<TypeAliasSpec>) :
    MutableList<TypeAliasSpec> by actualList {

    /** Add type alias from [TypeName]. */
    fun add(name: String, type: TypeName): Boolean = add(typeAliasSpecOf(name, type))

    /** Add type alias from [Type]. */
    fun add(name: String, type: Type): Boolean = add(typeAliasSpecOf(name, type))

    /** Add type alias from [KClass]. */
    fun add(name: String, type: KClass<*>): Boolean = add(typeAliasSpecOf(name, type))

    /** Add type alias from [T]. */
    inline fun <reified T> add(name: String): Boolean = add(typeAliasSpecOf<T>(name))

    /** Add type alias from [TypeName] with custom initialization [configuration]. */
    inline fun add(
        name: String,
        type: TypeName,
        configuration: TypeAliasSpecBuilder.() -> Unit
    ): Boolean = add(buildTypeAliasSpec(name, type, configuration))

    /** Add type alias from [Type] with custom initialization [configuration]. */
    inline fun add(
        name: String,
        type: Type,
        configuration: TypeAliasSpecBuilder.() -> Unit
    ): Boolean = add(buildTypeAliasSpec(name, type, configuration))

    /** Add type alias from [KClass] with custom initialization [configuration]. */
    inline fun add(
        name: String,
        type: KClass<*>,
        configuration: TypeAliasSpecBuilder.() -> Unit
    ): Boolean = add(buildTypeAliasSpec(name, type, configuration))

    /** Add type alias from [T] with custom initialization [configuration]. */
    inline fun <reified T> add(
        name: String,
        configuration: TypeAliasSpecBuilder.() -> Unit
    ): Boolean = add(buildTypeAliasSpec<T>(name, configuration))

    /** Convenient method to add type alias with operator function. */
    operator fun set(name: String, type: TypeName): Unit = plusAssign(typeAliasSpecOf(name, type))

    /** Convenient method to add type alias with operator function. */
    operator fun set(name: String, type: Type): Unit = plusAssign(typeAliasSpecOf(name, type))

    /** Convenient method to add type alias with operator function. */
    operator fun set(name: String, type: KClass<*>): Unit = plusAssign(typeAliasSpecOf(name, type))
}

/** Receiver for the `typeAliases` block providing an extended set of operators for the configuration. */
@SpecDslMarker
class TypeAliasSpecHandlerScope(actualList: MutableList<TypeAliasSpec>) : TypeAliasSpecHandler(actualList) {

    /** Convenient method to add type alias with receiver type. */
    inline operator fun String.invoke(
        type: TypeName,
        configuration: TypeAliasSpecBuilder.() -> Unit
    ): Boolean = add(this, type, configuration)

    /** Convenient method to add type alias with receiver type. */
    inline operator fun String.invoke(
        type: Type,
        configuration: TypeAliasSpecBuilder.() -> Unit
    ): Boolean = add(this, type, configuration)

    /** Convenient method to add type alias with receiver type. */
    inline operator fun String.invoke(
        type: KClass<*>,
        configuration: TypeAliasSpecBuilder.() -> Unit
    ): Boolean = add(this, type, configuration)

    /** Convenient method to add type alias with receiver type. */
    inline operator fun <reified T> String.invoke(
        configuration: TypeAliasSpecBuilder.() -> Unit
    ): Boolean = add(this, T::class, configuration)
}
