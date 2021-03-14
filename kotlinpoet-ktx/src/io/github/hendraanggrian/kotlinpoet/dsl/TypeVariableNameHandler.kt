package io.github.hendraanggrian.kotlinpoet.dsl

import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeVariableName
import io.github.hendraanggrian.kotlinpoet.SpecDslMarker
import io.github.hendraanggrian.kotlinpoet.typeVarBy
import io.github.hendraanggrian.kotlinpoet.typeVarOf
import java.lang.reflect.Type
import kotlin.reflect.KClass

/**
 * A [TypeVariableNameHandler] is responsible for managing a set of type variable name instances.
 * Since Kotlinpoet keep [TypeVariableName] in lists and sets, this class extends [Collection].
 */
open class TypeVariableNameHandler internal constructor(actualList: MutableCollection<TypeVariableName>) :
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
    operator fun plusAssign(name: String): Unit = plusAssign(name.typeVarOf())
}

/** Receiver for the `typeVariables` function type providing an extended set of operators for the configuration. */
@SpecDslMarker
class TypeVariableNameHandlerScope(actualList: MutableCollection<TypeVariableName>) :
    TypeVariableNameHandler(actualList)
