package com.hendraanggrian.kotlinpoet.collections

import com.hendraanggrian.kotlinpoet.FunSpecBuilder
import com.hendraanggrian.kotlinpoet.SpecDslMarker
import com.hendraanggrian.kotlinpoet.SpecLoader
import com.hendraanggrian.kotlinpoet.buildConstructorFunSpec
import com.hendraanggrian.kotlinpoet.buildFunSpec
import com.hendraanggrian.kotlinpoet.buildGetterFunSpec
import com.hendraanggrian.kotlinpoet.buildSetterFunSpec
import com.hendraanggrian.kotlinpoet.createSpecLoader
import com.squareup.kotlinpoet.FunSpec
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/** A [FunSpecList] is responsible for managing a set of function instances. */
@OptIn(ExperimentalContracts::class)
open class FunSpecList internal constructor(actualList: MutableList<FunSpec>) : MutableList<FunSpec> by actualList {

    /** Add function from name. */
    fun add(name: String): FunSpec = FunSpec.builder(name).build().also(::add)

    /** Add function from name with custom initialization [configuration]. */
    fun add(name: String, configuration: FunSpecBuilder.() -> Unit): FunSpec {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return buildFunSpec(name, configuration).also(::add)
    }

    /** Add constructor function. */
    fun addConstructor(): FunSpec = FunSpec.constructorBuilder().build().also(::add)

    /** Add constructor function with custom initialization [configuration]. */
    fun addConstructor(configuration: FunSpecBuilder.() -> Unit): FunSpec {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return buildConstructorFunSpec(configuration).also(::add)
    }

    /** Add getter function. */
    fun addGetter(): FunSpec = FunSpec.getterBuilder().build().also(::add)

    /** Add getter function with custom initialization [configuration]. */
    fun addGetter(configuration: FunSpecBuilder.() -> Unit): FunSpec {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return buildGetterFunSpec(configuration).also(::add)
    }

    /** Add setter function. */
    fun addSetter(): FunSpec = FunSpec.setterBuilder().build().also(::add)

    /** Add setter function with custom initialization [configuration]. */
    fun addSetter(configuration: FunSpecBuilder.() -> Unit): FunSpec {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return buildSetterFunSpec(configuration).also(::add)
    }

    /** Convenient method to add function with operator function. */
    inline operator fun plusAssign(name: String) {
        add(name)
    }

    /** Property delegate for adding method from name. */
    val adding: SpecLoader<FunSpec> get() = createSpecLoader(::add)

    /** Property delegate for adding method from name with initialization [configuration]. */
    fun adding(configuration: FunSpecBuilder.() -> Unit): SpecLoader<FunSpec> {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return createSpecLoader { add(it, configuration) }
    }
}

/** Receiver for the `functions` block providing an extended set of operators for the configuration. */
@SpecDslMarker
class FunSpecListScope internal constructor(actualList: MutableList<FunSpec>) : FunSpecList(actualList) {

    /** @see FunSpecList.add */
    inline operator fun String.invoke(noinline configuration: FunSpecBuilder.() -> Unit): FunSpec =
        add(this, configuration)
}
