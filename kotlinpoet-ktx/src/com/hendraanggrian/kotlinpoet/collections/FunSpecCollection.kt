@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.kotlinpoet.collections

import com.hendraanggrian.kotlinpoet.FunSpecBuilder
import com.hendraanggrian.kotlinpoet.SpecMarker
import com.hendraanggrian.kotlinpoet.buildConstructorFunSpec
import com.hendraanggrian.kotlinpoet.buildFunSpec
import com.hendraanggrian.kotlinpoet.buildGetterFunSpec
import com.hendraanggrian.kotlinpoet.buildSetterFunSpec
import com.squareup.kotlinpoet.FunSpec

/** A [FunSpecCollection] is responsible for managing a set of function instances. */
open class FunSpecCollection internal constructor(actualList: MutableList<FunSpec>) :
    MutableList<FunSpec> by actualList {

    /** Add function from name. */
    fun add(name: String): Boolean = add(FunSpec.builder(name).build())

    /** Add function from name with custom initialization [configuration]. */
    fun add(name: String, configuration: FunSpecBuilder.() -> Unit): Boolean = add(buildFunSpec(name, configuration))

    /** Add constructor function. */
    fun addConstructor(): Boolean = add(FunSpec.constructorBuilder().build())

    /** Add constructor function with custom initialization [configuration]. */
    fun addConstructor(configuration: FunSpecBuilder.() -> Unit): Boolean = add(buildConstructorFunSpec(configuration))

    /** Add getter function. */
    fun addGetter(): Boolean = add(FunSpec.getterBuilder().build())

    /** Add getter function with custom initialization [configuration]. */
    fun addGetter(configuration: FunSpecBuilder.() -> Unit): Boolean = add(buildGetterFunSpec(configuration))

    /** Add setter function. */
    fun addSetter(): Boolean = add(FunSpec.setterBuilder().build())

    /** Add setter function with custom initialization [configuration]. */
    fun addSetter(configuration: FunSpecBuilder.() -> Unit): Boolean = add(buildSetterFunSpec(configuration))

    /** Convenient method to add function with operator function. */
    inline operator fun plusAssign(name: String) {
        add(name)
    }
}

/** Receiver for the `functions` block providing an extended set of operators for the configuration. */
@SpecMarker
class FunSpecCollectionScope internal constructor(actualList: MutableList<FunSpec>) : FunSpecCollection(actualList) {

    /** @see FunSpecCollection.add */
    operator fun String.invoke(configuration: FunSpecBuilder.() -> Unit): Boolean = add(this, configuration)
}
