package com.hendraanggrian.kotlinpoet.collections

import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asTypeName
import java.lang.reflect.Type
import kotlin.reflect.KClass

/** A [TypeNameMap] is responsible for managing a set of type name instances. */
class TypeNameMap internal constructor(actualMap: MutableMap<TypeName, CodeBlock?>) :
    MutableMap<TypeName, CodeBlock?> by actualMap {

    /** Add type name without code. */
    fun put(key: TypeName): CodeBlock? = put(key, null)

    /** Add type name from [Class] with optional code. */
    fun put(key: Type, value: CodeBlock? = null): CodeBlock? = put(key.asTypeName(), value)

    /** Add type name from [KClass] with optional code. */
    fun put(key: KClass<*>, value: CodeBlock? = null): CodeBlock? = put(key.asTypeName(), value)

    /** Add type name from [T] with optional code. */
    inline fun <reified T> put(value: CodeBlock? = null): CodeBlock? = put(T::class, value)

    /** Convenient method to add type name with operator function. */
    inline operator fun set(name: Type, value: CodeBlock?) {
        put(name, value)
    }

    /** Convenient method to add type name with operator function. */
    inline operator fun set(name: KClass<*>, value: CodeBlock?) {
        put(name, value)
    }

    /** Convenient method to add type name with operator function. */
    inline operator fun plusAssign(name: TypeName) {
        put(name)
    }

    /** Convenient method to add type name with operator function. */
    inline operator fun plusAssign(name: Type) {
        put(name)
    }

    /** Convenient method to add type name with operator function. */
    inline operator fun plusAssign(name: KClass<*>) {
        put(name)
    }
}
