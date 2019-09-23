package com.hendraanggrian.kotlinpoet.dsl

import com.hendraanggrian.kotlinpoet.FunSpecBuilder
import com.hendraanggrian.kotlinpoet.KotlinpoetDslMarker
import com.hendraanggrian.kotlinpoet.buildConstructorFunction
import com.hendraanggrian.kotlinpoet.buildFunction
import com.hendraanggrian.kotlinpoet.buildGetterFunction
import com.hendraanggrian.kotlinpoet.buildSetterFunction
import com.squareup.kotlinpoet.FunSpec

private interface FunAddable {

    /** Add function to this container. */
    fun add(spec: FunSpec)
}

/** A [FunContainer] is responsible for managing a set of function instances. */
abstract class FunContainer internal constructor() : FunAddable {

    /** Add function from [name], returning the function added. */
    fun add(name: String): FunSpec =
        buildFunction(name).also { add(it) }

    /** Add function from [name] with custom initialization [builderAction], returning the function added. */
    inline fun add(name: String, builderAction: FunSpecBuilder.() -> Unit): FunSpec =
        buildFunction(name, builderAction).also { add(it) }

    /** Add constructor function, returning the function added. */
    fun addConstructor(): FunSpec =
        buildConstructorFunction().also { add(it) }

    /** Add constructor function with custom initialization [builderAction], returning the function added. */
    inline fun addConstructor(builderAction: FunSpecBuilder.() -> Unit): FunSpec =
        buildConstructorFunction(builderAction).also { add(it) }

    /** Add getter function, returning the function added. */
    fun addGetter(): FunSpec =
        buildGetterFunction().also { add(it) }

    /** Add getter function with custom initialization [builderAction], returning the function added. */
    inline fun addGetter(builderAction: FunSpecBuilder.() -> Unit): FunSpec =
        buildGetterFunction(builderAction).also { add(it) }

    /** Add setter function, returning the function added. */
    fun addSetterFunction(): FunSpec =
        buildSetterFunction().also { add(it) }

    /** Add setter function with custom initialization [builderAction], returning the function added. */
    inline fun addSetterFunction(builderAction: FunSpecBuilder.() -> Unit): FunSpec =
        buildSetterFunction(builderAction).also { add(it) }

    /** Convenient function to add function with operator function. */
    operator fun plusAssign(spec: FunSpec) {
        add(spec)
    }

    /** Convenient function to add function with operator function. */
    operator fun plusAssign(name: String) {
        add(name)
    }

    /** Configure this container with DSL. */
    inline operator fun invoke(configuration: FunContainerScope.() -> Unit): Unit =
        FunContainerScope(this).configuration()
}

/** Receiver for the `functions` block providing an extended set of operators for the configuration. */
@KotlinpoetDslMarker
class FunContainerScope @PublishedApi internal constructor(container: FunContainer) :
    FunContainer(), FunAddable by container {

    /** Convenient function to add function with receiver type. */
    inline operator fun String.invoke(builderAction: FunSpecBuilder.() -> Unit): FunSpec =
        add(this, builderAction)
}
