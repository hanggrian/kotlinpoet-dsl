package io.github.hendraanggrian.kotlinpoet.collections

import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeVariableName
import io.github.hendraanggrian.kotlinpoet.typeVarBy
import io.github.hendraanggrian.kotlinpoet.typeVarOf
import java.lang.reflect.Type
import kotlin.reflect.KClass

private interface TypeVariableNameCollection : MutableCollection<TypeVariableName> {

    /** Add a [TypeVariableName] without bounds. */
    fun add(name: String): Boolean = add(name.typeVarOf())

    /** Returns a [TypeVariableName] with [TypeName] bounds. */
    fun add(name: String, vararg bounds: TypeName): Boolean = add(name.typeVarBy(*bounds))

    /** Returns a [TypeVariableName] with [Type] bounds. */
    fun add(name: String, vararg bounds: Type): Boolean = add(name.typeVarBy(*bounds))

    /** Returns a [TypeVariableName] with [KClass] bounds. */
    fun add(name: String, vararg bounds: KClass<*>): Boolean = add(name.typeVarBy(*bounds))

    /** Convenient method to add type name with operator function. */
    operator fun plusAssign(name: String): Unit = plusAssign(name.typeVarOf())
}

/** A [TypeVariableNameList] is responsible for managing a set of type variable name instances. */
class TypeVariableNameList internal constructor(actualList: MutableList<TypeVariableName>) :
    MutableList<TypeVariableName> by actualList,
    TypeVariableNameCollection

/** A [TypeVariableNameSet] is responsible for managing a set of type variable name instances. */
class TypeVariableNameSet internal constructor(actualList: MutableSet<TypeVariableName>) :
    MutableSet<TypeVariableName> by actualList,
    TypeVariableNameCollection
