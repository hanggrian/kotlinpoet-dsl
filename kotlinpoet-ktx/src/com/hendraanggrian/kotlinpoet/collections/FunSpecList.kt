package com.hendraanggrian.kotlinpoet.collections

import com.hendraanggrian.kotlinpoet.FunSpecBuilder
import com.hendraanggrian.kotlinpoet.KotlinpoetDslMarker
import com.hendraanggrian.kotlinpoet.buildConstructorFunSpec
import com.hendraanggrian.kotlinpoet.buildFunSpec
import com.hendraanggrian.kotlinpoet.buildGetterFunSpec
import com.hendraanggrian.kotlinpoet.buildSetterFunSpec
import com.hendraanggrian.kotlinpoet.emptyConstructorFunSpec
import com.hendraanggrian.kotlinpoet.emptyGetterFunSpec
import com.hendraanggrian.kotlinpoet.emptySetterFunSpec
import com.hendraanggrian.kotlinpoet.funSpecOf
import com.squareup.kotlinpoet.FunSpec

/** A [FunSpecList] is responsible for managing a set of function instances. */
open class FunSpecList internal constructor(actualList: MutableList<FunSpec>) :
    MutableList<FunSpec> by actualList {

    /** Add function from name. */
    fun add(name: String): Boolean = add(funSpecOf(name))

    /** Add constructor function. */
    fun addConstructor(): Boolean = add(emptyConstructorFunSpec())

    /** Add getter function. */
    fun addGetter(): Boolean = add(emptyGetterFunSpec())

    /** Add setter function. */
    fun addSetter(): Boolean = add(emptySetterFunSpec())

    /** Add function from name with custom initialization [builderAction]. */
    inline fun add(
        name: String,
        builderAction: FunSpecBuilder.() -> Unit
    ): Boolean = add(buildFunSpec(name, builderAction))

    /** Add constructor function with custom initialization [builderAction]. */
    inline fun addConstructor(
        builderAction: FunSpecBuilder.() -> Unit
    ): Boolean = add(buildConstructorFunSpec(builderAction))

    /** Add getter function with custom initialization [builderAction]. */
    inline fun addGetter(
        builderAction: FunSpecBuilder.() -> Unit
    ): Boolean = add(buildGetterFunSpec(builderAction))

    /** Add setter function with custom initialization [builderAction]. */
    inline fun addSetter(
        builderAction: FunSpecBuilder.() -> Unit
    ): Boolean = add(buildSetterFunSpec(builderAction))
}

/** Receiver for the `functions` block providing an extended set of operators for the configuration. */
@KotlinpoetDslMarker
class FunSpecListScope(actualList: MutableList<FunSpec>) : FunSpecList(actualList) {

    /** Convenient function to add function with receiver type. */
    inline operator fun String.invoke(
        builderAction: FunSpecBuilder.() -> Unit
    ): Boolean = add(this, builderAction)
}
