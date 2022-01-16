@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.kotlinpoet.collections

import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asTypeName
import java.lang.reflect.Type
import kotlin.reflect.KClass

/** A [TypeNameCollection] is responsible for managing a set of type name instances. */
class TypeNameCollection(actualMap: MutableMap<TypeName, CodeBlock?>) : MutableMap<TypeName, CodeBlock?> by actualMap {

    /** Add type name from [Class]. */
    fun add(key: Type, value: CodeBlock? = null): CodeBlock? = put(key.asTypeName(), value)

    /** Add type name from [KClass]. */
    fun add(key: KClass<*>, value: CodeBlock? = null): CodeBlock? = put(key.asTypeName(), value)

    /** Add type name from [T]. */
    inline fun <reified T> add(value: CodeBlock? = null): CodeBlock? = put(T::class.asTypeName(), value)

    /** Convenient method to add type variable name with operator function. */
    inline operator fun set(name: Type, value: CodeBlock?) {
        add(name, value)
    }

    /** Convenient method to add type variable name with operator function. */
    inline operator fun set(name: KClass<*>, value: CodeBlock?) {
        add(name, value)
    }
}
