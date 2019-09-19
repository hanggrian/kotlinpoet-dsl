package com.hendraanggrian.kotlinpoet.dsl

import com.hendraanggrian.kotlinpoet.FunSpecBuilder
import com.hendraanggrian.kotlinpoet.KotlinpoetDslMarker
import com.hendraanggrian.kotlinpoet.buildConstructorFunction
import com.hendraanggrian.kotlinpoet.buildFunction
import com.squareup.kotlinpoet.FunSpec

private interface FunctionAddable {

    /** Add method to this container, returning the method added. */
    fun add(spec: FunSpec): FunSpec
}

/** A [FunctionContainer] is responsible for managing a set of method instances. */
abstract class FunctionContainer internal constructor() : FunctionAddable {

    /** Add method from [name], returning the method added. */
    fun add(name: String): FunSpec =
        add(buildFunction(name))

    /** Add method from [name] with custom initialization [builderAction], returning the method added. */
    inline fun add(name: String, builderAction: FunSpecBuilder.() -> Unit): FunSpec =
        add(buildFunction(name, builderAction))

    /** Add constructor method, returning the method added. */
    fun addConstructor(): FunSpec =
        add(buildConstructorFunction())

    /** Add constructor method with custom initialization [builderAction], returning the method added. */
    inline fun addConstructor(builderAction: FunSpecBuilder.() -> Unit): FunSpec =
        add(buildConstructorFunction(builderAction))

    /** Convenient method to add method with operator function. */
    operator fun plusAssign(spec: FunSpec) {
        add(spec)
    }

    /** Convenient method to add method with operator function. */
    operator fun plusAssign(name: String) {
        add(name)
    }

    /** Configure this container with DSL. */
    inline operator fun invoke(configuration: FunctionContainerScope.() -> Unit) =
        FunctionContainerScope(this).configuration()
}

/** Receiver for the `methods` block providing an extended set of operators for the configuration. */
@KotlinpoetDslMarker
class FunctionContainerScope @PublishedApi internal constructor(container: FunctionContainer) :
    FunctionContainer(), FunctionAddable by container {

    /** Convenient method to add method with receiver type. */
    inline operator fun String.invoke(builderAction: FunSpecBuilder.() -> Unit): FunSpec =
        add(this, builderAction)
}
