package io.github.hendraanggrian.kotlinpoet.collections

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
@SpecDslMarker
class FunSpecListScope(actualList: MutableList<FunSpec>) : FunSpecList(actualList) {

    /** Convenient function to add function with receiver type. */
    inline operator fun String.invoke(
        builderAction: FunSpecBuilder.() -> Unit
    ): Boolean = add(this, builderAction)
}
