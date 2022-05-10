package com.hendraanggrian.kotlinpoet.collections

import com.hendraanggrian.kotlinpoet.AnnotationSpecBuilder
import com.hendraanggrian.kotlinpoet.DELICATE_JAVA
import com.hendraanggrian.kotlinpoet.SpecMarker
import com.hendraanggrian.kotlinpoet.buildAnnotationSpec
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.DelicateKotlinPoetApi
import kotlin.reflect.KClass

/** An [AnnotationSpecList] is responsible for managing a set of annotation instances. */
open class AnnotationSpecList internal constructor(actualList: MutableList<AnnotationSpec>) :
    MutableList<AnnotationSpec> by actualList {

    /** Add annotation from [ClassName]. */
    fun add(type: ClassName): AnnotationSpec = AnnotationSpec.builder(type).build().also(::add)

    /** Add annotation from [ClassName] with custom initialization [configuration]. */
    fun add(type: ClassName, configuration: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
        buildAnnotationSpec(type, configuration).also(::add)

    /** Add annotation from [Class]. */
    @DelicateKotlinPoetApi(DELICATE_JAVA)
    fun add(type: Class<out Annotation>): AnnotationSpec = AnnotationSpec.builder(type).build().also(::add)

    /** Add annotation from [Class] with custom initialization [configuration]. */
    @DelicateKotlinPoetApi(DELICATE_JAVA)
    fun add(type: Class<out Annotation>, configuration: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
        buildAnnotationSpec(type, configuration).also(::add)

    /** Add annotation from [KClass]. */
    fun add(type: KClass<out Annotation>): AnnotationSpec = AnnotationSpec.builder(type).build().also(::add)

    /** Add annotation from [KClass] with custom initialization [configuration]. */
    fun add(type: KClass<out Annotation>, configuration: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
        buildAnnotationSpec(type, configuration).also(::add)

    /** Add annotation from [T]. */
    inline fun <reified T : Annotation> add(): AnnotationSpec = AnnotationSpec.builder(T::class).build().also(::add)

    /** Add annotation from [T] with custom initialization [configuration]. */
    inline fun <reified T : Annotation> add(noinline configuration: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
        buildAnnotationSpec<T>(configuration).also(::add)

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

/** Receiver for the `annotations` block providing an extended set of operators for the configuration. */
@SpecMarker
class AnnotationSpecListScope internal constructor(actualList: MutableList<AnnotationSpec>) :
    AnnotationSpecList(actualList) {

    /** @see AnnotationSpecList.add */
    operator fun ClassName.invoke(configuration: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
        add(this, configuration)

    /** @see AnnotationSpecList.add */
    @DelicateKotlinPoetApi(DELICATE_JAVA)
    operator fun Class<out Annotation>.invoke(configuration: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
        add(this, configuration)

    /** @see AnnotationSpecList.add */
    operator fun KClass<out Annotation>.invoke(configuration: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
        add(this, configuration)
}
