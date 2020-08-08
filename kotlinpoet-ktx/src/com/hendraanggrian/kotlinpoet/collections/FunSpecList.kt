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

    /** Add function from [name], returning the function added. */
    fun add(name: String): FunSpec = funSpecOf(name).also(::plusAssign)

    /** Add constructor function, returning the function added. */
    fun addConstructor(): FunSpec = emptyConstructorFunSpec().also(::plusAssign)

    /** Add getter function, returning the function added. */
    fun addGetter(): FunSpec = emptyGetterFunSpec().also(::plusAssign)

    /** Add setter function, returning the function added. */
    fun addSetter(): FunSpec = emptySetterFunSpec().also(::plusAssign)

    /** Add function from [name] with custom initialization [builderAction], returning the function added. */
    inline fun add(
        name: String,
        builderAction: FunSpecBuilder.() -> Unit
    ): FunSpec = buildFunSpec(name, builderAction).also(::plusAssign)

    /** Add constructor function with custom initialization [builderAction], returning the function added. */
    inline fun addConstructor(
        builderAction: FunSpecBuilder.() -> Unit
    ): FunSpec = buildConstructorFunSpec(builderAction).also(::plusAssign)

    /** Add getter function with custom initialization [builderAction], returning the function added. */
    inline fun addGetter(
        builderAction: FunSpecBuilder.() -> Unit
    ): FunSpec = buildGetterFunSpec(builderAction).also(::plusAssign)

    /** Add setter function with custom initialization [builderAction], returning the function added. */
    inline fun addSetter(
        builderAction: FunSpecBuilder.() -> Unit
    ): FunSpec = buildSetterFunSpec(builderAction).also(::plusAssign)
}

/** Receiver for the `functions` block providing an extended set of operators for the configuration. */
@KotlinpoetDslMarker
class FunSpecListScope(actualList: MutableList<FunSpec>) : FunSpecList(actualList) {

    /** Convenient function to add function with receiver type. */
    inline operator fun String.invoke(
        builderAction: FunSpecBuilder.() -> Unit
    ): FunSpec = add(this, builderAction)
}
