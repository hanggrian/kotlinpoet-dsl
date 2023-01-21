package com.hendraanggrian.kotlinpoet.collections

import com.hendraanggrian.kotlinpoet.AnnotationSpecBuilder
import com.hendraanggrian.kotlinpoet.DELICATE_JAVA
import com.hendraanggrian.kotlinpoet.KotlinpoetSpecDsl
import com.hendraanggrian.kotlinpoet.buildAnnotationSpec
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.DelicateKotlinPoetApi
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.reflect.KClass

/** An [AnnotationSpecList] is responsible for managing a set of annotation instances. */
@OptIn(ExperimentalContracts::class)
open class AnnotationSpecList internal constructor(actualList: MutableList<AnnotationSpec>) :
    MutableList<AnnotationSpec> by actualList {

    /** Add annotation from [ClassName]. */
    fun add(type: ClassName): AnnotationSpec = AnnotationSpec.builder(type).build().also(::add)

    /**  annotation from [ClassName] with custom initialization [configuration]. */
    fun add(type: ClassName, configuration: AnnotationSpecBuilder.() -> Unit): AnnotationSpec {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return buildAnnotationSpec(type, configuration).also(::add)
    }

    /** Add annotation from [Class]. */
    @DelicateKotlinPoetApi(DELICATE_JAVA)
    fun add(type: Class<out Annotation>): AnnotationSpec =
        AnnotationSpec.builder(type).build().also(::add)

    /** Add annotation from [Class] with custom initialization [configuration]. */
    @DelicateKotlinPoetApi(DELICATE_JAVA)
    fun add(
        type: Class<out Annotation>,
        configuration: AnnotationSpecBuilder.() -> Unit
    ): AnnotationSpec {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return buildAnnotationSpec(type, configuration).also(::add)
    }

    /** Add annotation from [KClass]. */
    fun add(type: KClass<out Annotation>): AnnotationSpec =
        AnnotationSpec.builder(type).build().also(::add)

    /** Add annotation from [KClass] with custom initialization [configuration]. */
    fun add(
        type: KClass<out Annotation>,
        configuration: AnnotationSpecBuilder.() -> Unit
    ): AnnotationSpec {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return buildAnnotationSpec(type, configuration).also(::add)
    }

    /** Add annotation from [T]. */
    inline fun <reified T : Annotation> add(): AnnotationSpec = add(T::class)

    /** Add annotation from [T] with custom initialization [configuration]. */
    inline fun <reified T : Annotation> add(
        noinline configuration: AnnotationSpecBuilder.() -> Unit
    ): AnnotationSpec {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return add(T::class, configuration)
    }

    /** Convenient method to add annotation with operator function. */
    inline operator fun plusAssign(type: ClassName) {
        add(type)
    }

    /** Convenient method to add annotation with operator function. */
    @DelicateKotlinPoetApi(DELICATE_JAVA)
    inline operator fun plusAssign(type: Class<out Annotation>) {
        add(type)
    }

    /** Convenient method to add annotation with operator function. */
    inline operator fun plusAssign(type: KClass<out Annotation>) {
        add(type)
    }
}

/**
 * Receiver for the `annotations` block providing an extended set of operators for the
 * configuration.
 */
@KotlinpoetSpecDsl
class AnnotationSpecListScope(actualList: MutableList<AnnotationSpec>) :
    AnnotationSpecList(actualList)
