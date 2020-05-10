package com.hendraanggrian.kotlinpoet.collections

import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asTypeName
import java.lang.reflect.Type
import kotlin.reflect.KClass

/** A [TypeNameMap] is responsible for managing a set of type name instances. */
class TypeNameMap internal constructor(actualMap: MutableMap<TypeName, CodeBlock?>) :
    MutableMap<TypeName, CodeBlock?> by actualMap {

    /** Add type name from [Class]. */
    operator fun set(key: Type, value: CodeBlock?): Unit = set(key.asTypeName(), value)

    /** Add type name from [KClass]. */
    operator fun set(key: KClass<*>, value: CodeBlock?): Unit = set(key.asTypeName(), value)

    /** Add type name from [T]. */
    inline fun <reified T> set(value: CodeBlock? = null): Unit = set(T::class.asTypeName(), value)
}
