package io.github.hendraanggrian.kotlinpoet.dsl

import com.squareup.kotlinpoet.FunSpec
import io.github.hendraanggrian.kotlinpoet.FunSpecBuilder
import io.github.hendraanggrian.kotlinpoet.SpecDslMarker
import io.github.hendraanggrian.kotlinpoet.buildConstructorFunSpec
import io.github.hendraanggrian.kotlinpoet.buildFunSpec
import io.github.hendraanggrian.kotlinpoet.buildGetterFunSpec
import io.github.hendraanggrian.kotlinpoet.buildSetterFunSpec
import io.github.hendraanggrian.kotlinpoet.emptyConstructorFunSpec
import io.github.hendraanggrian.kotlinpoet.emptyGetterFunSpec
import io.github.hendraanggrian.kotlinpoet.emptySetterFunSpec
import io.github.hendraanggrian.kotlinpoet.funSpecOf

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
