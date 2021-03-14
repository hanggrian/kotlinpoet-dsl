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
open class FunSpecHandler internal constructor(actualList: MutableList<FunSpec>) :
    MutableList<FunSpec> by actualList {

    /** Add function from name. */
    fun add(name: String): Boolean = add(funSpecOf(name))

    /** Add constructor function. */
    fun addConstructor(): Boolean = add(emptyConstructorFunSpec())

    /** Add getter function. */
    fun addGetter(): Boolean = add(emptyGetterFunSpec())

    /** Add setter function. */
    fun addSetter(): Boolean = add(emptySetterFunSpec())

    /** Add function from name with custom initialization [configuration]. */
    inline fun add(
        name: String,
        configuration: FunSpecBuilder.() -> Unit
    ): Boolean = add(buildFunSpec(name, configuration))

    /** Add constructor function with custom initialization [configuration]. */
    inline fun addConstructor(
        configuration: FunSpecBuilder.() -> Unit
    ): Boolean = add(buildConstructorFunSpec(configuration))

    /** Add getter function with custom initialization [configuration]. */
    inline fun addGetter(
        configuration: FunSpecBuilder.() -> Unit
    ): Boolean = add(buildGetterFunSpec(configuration))

    /** Add setter function with custom initialization [configuration]. */
    inline fun addSetter(
        configuration: FunSpecBuilder.() -> Unit
    ): Boolean = add(buildSetterFunSpec(configuration))
}

/** Receiver for the `functions` block providing an extended set of operators for the configuration. */
@SpecDslMarker
class FunSpecHandlerScope(actualList: MutableList<FunSpec>) : FunSpecHandler(actualList) {

    /** Convenient function to add function with receiver type. */
    inline operator fun String.invoke(
        configuration: FunSpecBuilder.() -> Unit
    ): Boolean = add(this, configuration)
}
