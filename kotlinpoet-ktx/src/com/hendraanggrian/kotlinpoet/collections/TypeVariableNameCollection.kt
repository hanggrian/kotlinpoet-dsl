package com.hendraanggrian.kotlinpoet.collections

import com.hendraanggrian.kotlinpoet.genericsBy
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeVariableName
import java.lang.reflect.Type
import kotlin.reflect.KClass

/**
 * A [TypeVariableNameCollection] is responsible for managing a set of type variable name instances.
 * Since Kotlinpoet keep [TypeVariableName] in lists and sets, this class extends [Collection].
 */
class TypeVariableNameCollection internal constructor(actualCollection: MutableCollection<TypeVariableName>) :
    MutableCollection<TypeVariableName> by actualCollection {

    /** Add a [TypeVariableName] without bounds. */
    fun add(name: String, variance: KModifier? = null): Boolean = add(name.genericsBy(variance))

    /** Returns a [TypeVariableName] with [TypeName] bounds. */
    fun add(name: String, vararg bounds: TypeName, variance: KModifier? = null): Boolean =
        add(name.genericsBy(*bounds, variance = variance))

    /** Returns a [TypeVariableName] with [Type] bounds. */
    fun add(name: String, vararg bounds: Type, variance: KModifier? = null): Boolean =
        add(name.genericsBy(*bounds, variance = variance))

    /** Returns a [TypeVariableName] with [KClass] bounds. */
    fun add(name: String, vararg bounds: KClass<*>, variance: KModifier? = null): Boolean =
        add(name.genericsBy(*bounds, variance = variance))

    /** Returns a [TypeVariableName] with collection of [TypeName] bounds. */
    fun add(name: String, bounds: List<TypeName>, variance: KModifier? = null): Boolean =
        add(name.genericsBy(bounds, variance))

    /** Returns a [TypeVariableName] with collection of [Type] bounds. */
    @JvmName("addWithTypes")
    fun add(name: String, bounds: Iterable<Type>, variance: KModifier? = null): Boolean =
        add(name.genericsBy(bounds, variance))

    /** Returns a [TypeVariableName] with collection of [KClass] bounds. */
    @JvmName("addWithClasses")
    fun add(name: String, bounds: Iterable<KClass<*>>, variance: KModifier? = null): Boolean =
        add(name.genericsBy(bounds, variance))

    /** Convenient method to add type variable name with operator function. */
    inline operator fun plusAssign(name: String) {
        add(name)
    }
}
