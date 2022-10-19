package com.hendraanggrian.kotlinpoet.collections

import com.hendraanggrian.kotlinpoet.SpecLoader
import com.hendraanggrian.kotlinpoet.createSpecLoader
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

    /** Add a type variable name without bounds. */
    fun add(name: String, variance: KModifier? = null): TypeVariableName =
        name.genericsBy(variance).also(::add)

    /** Returns a type variable name with [TypeName] bounds. */
    fun add(name: String, vararg bounds: TypeName, variance: KModifier? = null): TypeVariableName =
        name.genericsBy(*bounds, variance = variance).also(::add)

    /** Returns a type variable name with [Type] bounds. */
    fun add(name: String, vararg bounds: Type, variance: KModifier? = null): TypeVariableName =
        name.genericsBy(*bounds, variance = variance).also(::add)

    /** Returns a type variable name with [KClass] bounds. */
    fun add(name: String, vararg bounds: KClass<*>, variance: KModifier? = null): TypeVariableName =
        name.genericsBy(*bounds, variance = variance).also(::add)

    /** Returns a type variable name with collection of [TypeName] bounds. */
    fun add(name: String, bounds: List<TypeName>, variance: KModifier? = null): TypeVariableName =
        name.genericsBy(bounds, variance).also(::add)

    /** Returns a type variable name with collection of [Type] bounds. */
    @JvmName("addWithTypes")
    fun add(name: String, bounds: Iterable<Type>, variance: KModifier? = null): TypeVariableName =
        name.genericsBy(bounds, variance).also(::add)

    /** Returns a type variable name with collection of [KClass] bounds. */
    @JvmName("addWithClasses")
    fun add(
        name: String,
        bounds: Iterable<KClass<*>>,
        variance: KModifier? = null
    ): TypeVariableName = name.genericsBy(bounds, variance).also(::add)

    /** Convenient method to add type variable name with operator function. */
    inline operator fun plusAssign(name: String) {
        add(name)
    }

    /** Property delegate for adding type variable name without bounds. */
    val adding: SpecLoader<TypeVariableName> get() = createSpecLoader(::add)

    /** Property delegate for adding type variable name with [TypeName] bounds. */
    fun adding(vararg bounds: TypeName): SpecLoader<TypeVariableName> =
        createSpecLoader { add(it, *bounds) }

    /** Property delegate for adding type variable name with [Type] bounds. */
    fun adding(vararg bounds: Type): SpecLoader<TypeVariableName> =
        createSpecLoader { add(it, *bounds) }

    /** Property delegate for adding type variable name with [KClass] bounds. */
    fun adding(vararg bounds: KClass<*>): SpecLoader<TypeVariableName> =
        createSpecLoader { add(it, *bounds) }

    /** Property delegate for adding type variable name with collection of [TypeName] bounds. */
    fun adding(bounds: List<TypeName>): SpecLoader<TypeVariableName> =
        createSpecLoader { add(it, bounds) }

    /** Property delegate for adding type variable name with collection of [Type] bounds. */
    @JvmName("addingType")
    fun adding(bounds: Iterable<Type>): SpecLoader<TypeVariableName> =
        createSpecLoader { add(it, bounds) }

    /** Property delegate for adding type variable name with collection of [KClass] bounds. */
    @JvmName("addingClass")
    fun adding(bounds: Iterable<KClass<*>>): SpecLoader<TypeVariableName> =
        createSpecLoader { add(it, bounds) }
}
