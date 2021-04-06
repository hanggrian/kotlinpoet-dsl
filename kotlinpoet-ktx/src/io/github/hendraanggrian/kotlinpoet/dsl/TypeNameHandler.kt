package io.github.hendraanggrian.kotlinpoet.dsl

import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asTypeName
import io.github.hendraanggrian.kotlinpoet.SpecDslMarker
import java.lang.reflect.Type
import kotlin.reflect.KClass

/** A [TypeNameHandler] is responsible for managing a set of type name instances. */
open class TypeNameHandler(actualMap: MutableMap<TypeName, CodeBlock?>) :
    MutableMap<TypeName, CodeBlock?> by actualMap {

    /** Add type name from [Class]. */
    fun add(key: Type, value: CodeBlock? = null): CodeBlock? = put(key.asTypeName(), value)

    /** Add type name from [KClass]. */
    fun add(key: KClass<*>, value: CodeBlock? = null): CodeBlock? = put(key.asTypeName(), value)

    /** Add type name from [T]. */
    inline fun <reified T> add(value: CodeBlock? = null): CodeBlock? = put(T::class.asTypeName(), value)

    /** Convenient method to add type variable name with operator function. */
    @Suppress("NOTHING_TO_INLINE")
    inline operator fun set(name: Type, value: CodeBlock?) {
        add(name, value)
    }

    /** Convenient method to add type variable name with operator function. */
    @Suppress("NOTHING_TO_INLINE")
    inline operator fun set(name: KClass<*>, value: CodeBlock?) {
        add(name, value)
    }
}

/** Receiver for the `superinterfaces` function type providing an extended set of operators for the configuration. */
@SpecDslMarker
class TypeNameHandlerScope(actualList: MutableMap<TypeName, CodeBlock?>) : TypeNameHandler(actualList) {

    /** @see TypeNameHandler.set */
    operator fun Type.invoke(value: CodeBlock?): CodeBlock? = add(this, value)

    /** @see TypeNameHandler.set */
    operator fun KClass<*>.invoke(value: CodeBlock?): CodeBlock? = add(this, value)
}
