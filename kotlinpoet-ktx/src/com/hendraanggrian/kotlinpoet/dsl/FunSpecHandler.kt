package com.hendraanggrian.kotlinpoet.dsl

import com.hendraanggrian.kotlinpoet.FunSpecBuilder
import com.hendraanggrian.kotlinpoet.SpecDslMarker
import com.hendraanggrian.kotlinpoet.buildConstructorFunSpec
import com.hendraanggrian.kotlinpoet.buildFunSpec
import com.hendraanggrian.kotlinpoet.buildGetterFunSpec
import com.hendraanggrian.kotlinpoet.buildSetterFunSpec
import com.hendraanggrian.kotlinpoet.emptyConstructorFunSpec
import com.hendraanggrian.kotlinpoet.emptyGetterFunSpec
import com.hendraanggrian.kotlinpoet.emptySetterFunSpec
import com.hendraanggrian.kotlinpoet.funSpecOf
import com.squareup.kotlinpoet.FunSpec

/** A [FunSpecHandler] is responsible for managing a set of function instances. */
open class FunSpecHandler(actualList: MutableList<FunSpec>) : MutableList<FunSpec> by actualList {

    /** Add function from name. */
    fun add(name: String): Boolean = add(funSpecOf(name))

    /** Add function from name with custom initialization [configuration]. */
    fun add(name: String, configuration: FunSpecBuilder.() -> Unit): Boolean = add(buildFunSpec(name, configuration))

    /** Add constructor function. */
    fun addConstructor(): Boolean = add(emptyConstructorFunSpec())

    /** Add constructor function with custom initialization [configuration]. */
    fun addConstructor(configuration: FunSpecBuilder.() -> Unit): Boolean = add(buildConstructorFunSpec(configuration))

    /** Add getter function. */
    fun addGetter(): Boolean = add(emptyGetterFunSpec())

    /** Add getter function with custom initialization [configuration]. */
    fun addGetter(configuration: FunSpecBuilder.() -> Unit): Boolean = add(buildGetterFunSpec(configuration))

    /** Add setter function. */
    fun addSetter(): Boolean = add(emptySetterFunSpec())

    /** Add setter function with custom initialization [configuration]. */
    fun addSetter(configuration: FunSpecBuilder.() -> Unit): Boolean = add(buildSetterFunSpec(configuration))
}

/** Receiver for the `functions` block providing an extended set of operators for the configuration. */
@SpecDslMarker
class FunSpecHandlerScope(actualList: MutableList<FunSpec>) : FunSpecHandler(actualList) {

    /** @see FunSpecHandler.add */
    operator fun String.invoke(configuration: FunSpecBuilder.() -> Unit): Boolean = add(this, configuration)

    /** @see FunSpecHandler.addConstructor */
    fun String.constructor(configuration: FunSpecBuilder.() -> Unit): Boolean = add(this, configuration)

    /** @see FunSpecHandler.addGetter */
    fun String.getter(configuration: FunSpecBuilder.() -> Unit): Boolean = add(this, configuration)

    /** @see FunSpecHandler.addSetter */
    fun String.setter(configuration: FunSpecBuilder.() -> Unit): Boolean = add(this, configuration)
}
