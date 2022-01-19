@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.kotlinpoet.collections

import com.hendraanggrian.kotlinpoet.typeVarBy
import com.hendraanggrian.kotlinpoet.typeVarOf
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeVariableName
import java.lang.reflect.Type
import kotlin.reflect.KClass

/**
 * A [TypeVariableNameCollection] is responsible for managing a set of type variable name instances.
 * Since Kotlinpoet keep [TypeVariableName] in lists and sets, this class extends [Collection].
 */
class TypeVariableNameCollection internal constructor(actualList: MutableCollection<TypeVariableName>) :
    MutableCollection<TypeVariableName> by actualList {

    /** Add a [TypeVariableName] without bounds. */
    fun add(name: String): Boolean = add(name.typeVarOf())

    /** Returns a [TypeVariableName] with [TypeName] bounds. */
    fun add(name: String, vararg bounds: TypeName): Boolean = add(name.typeVarBy(*bounds))

    /** Returns a [TypeVariableName] with [Type] bounds. */
    fun add(name: String, vararg bounds: Type): Boolean = add(name.typeVarBy(*bounds))

    /** Returns a [TypeVariableName] with [KClass] bounds. */
    fun add(name: String, vararg bounds: KClass<*>): Boolean = add(name.typeVarBy(*bounds))

    /** Convenient method to add type name with operator function. */
    inline operator fun plusAssign(name: String) {
        add(name)
    }
}
