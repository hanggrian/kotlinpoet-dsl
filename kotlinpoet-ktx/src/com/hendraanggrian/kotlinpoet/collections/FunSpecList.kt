package com.hendraanggrian.kotlinpoet.collections

import com.hendraanggrian.kotlinpoet.FunSpecBuilder
import com.hendraanggrian.kotlinpoet.SpecMarker
import com.hendraanggrian.kotlinpoet.buildConstructorFunSpec
import com.hendraanggrian.kotlinpoet.buildFunSpec
import com.hendraanggrian.kotlinpoet.buildGetterFunSpec
import com.hendraanggrian.kotlinpoet.buildSetterFunSpec
import com.squareup.kotlinpoet.FunSpec

/** A [FunSpecList] is responsible for managing a set of function instances. */
open class FunSpecList internal constructor(actualList: MutableList<FunSpec>) : MutableList<FunSpec> by actualList {

    /** Add function from name. */
    fun add(name: String): FunSpec = FunSpec.builder(name).build().also(::add)

    /** Add function from name with custom initialization [configuration]. */
    fun add(name: String, configuration: FunSpecBuilder.() -> Unit): FunSpec =
        buildFunSpec(name, configuration).also(::add)

    /** Add constructor function. */
    fun addConstructor(): FunSpec = FunSpec.constructorBuilder().build().also(::add)

    /** Add constructor function with custom initialization [configuration]. */
    fun addConstructor(configuration: FunSpecBuilder.() -> Unit): FunSpec =
        buildConstructorFunSpec(configuration).also(::add)

    /** Add getter function. */
    fun addGetter(): FunSpec = FunSpec.getterBuilder().build().also(::add)

    /** Add getter function with custom initialization [configuration]. */
    fun addGetter(configuration: FunSpecBuilder.() -> Unit): FunSpec = buildGetterFunSpec(configuration).also(::add)

    /** Add setter function. */
    fun addSetter(): FunSpec = FunSpec.setterBuilder().build().also(::add)

    /** Add setter function with custom initialization [configuration]. */
    fun addSetter(configuration: FunSpecBuilder.() -> Unit): FunSpec = buildSetterFunSpec(configuration).also(::add)

    /** Convenient method to add function with operator function. */
    inline operator fun plusAssign(name: String) {
        add(name)
    }
}

/** Receiver for the `functions` block providing an extended set of operators for the configuration. */
@SpecMarker
class FunSpecListScope internal constructor(actualList: MutableList<FunSpec>) : FunSpecList(actualList) {

    /** @see FunSpecList.add */
    operator fun String.invoke(configuration: FunSpecBuilder.() -> Unit): FunSpec = add(this, configuration)
}
