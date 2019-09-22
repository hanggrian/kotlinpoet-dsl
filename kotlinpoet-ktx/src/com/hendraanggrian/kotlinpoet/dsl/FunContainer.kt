package com.hendraanggrian.kotlinpoet.dsl

import com.hendraanggrian.kotlinpoet.FunSpecBuilder
import com.hendraanggrian.kotlinpoet.KotlinpoetDslMarker
import com.hendraanggrian.kotlinpoet.buildConstructorFunction
import com.hendraanggrian.kotlinpoet.buildFunction
import com.squareup.kotlinpoet.FunSpec

private interface FunAddable {

    /** Add function to this container, returning the function added. */
    fun add(spec: FunSpec): FunSpec
}

/** A [FunContainer] is responsible for managing a set of function instances. */
abstract class FunContainer internal constructor() : FunAddable {

    /** Add function from [name], returning the function added. */
    fun add(name: String): FunSpec =
        add(buildFunction(name))

    /** Add function from [name] with custom initialization [builderAction], returning the function added. */
    inline fun add(name: String, builderAction: FunSpecBuilder.() -> Unit): FunSpec =
        add(buildFunction(name, builderAction))

    /** Add constructor function, returning the function added. */
    fun addConstructor(): FunSpec =
        add(buildConstructorFunction())

    /** Add constructor function with custom initialization [builderAction], returning the function added. */
    inline fun addConstructor(builderAction: FunSpecBuilder.() -> Unit): FunSpec =
        add(buildConstructorFunction(builderAction))

    /** Convenient function to add function with operator function. */
    operator fun plusAssign(spec: FunSpec) {
        add(spec)
    }

    /** Convenient function to add function with operator function. */
    operator fun plusAssign(name: String) {
        add(name)
    }

    /** Configure this container with DSL. */
    inline operator fun invoke(configuration: FunContainerScope.() -> Unit) =
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
