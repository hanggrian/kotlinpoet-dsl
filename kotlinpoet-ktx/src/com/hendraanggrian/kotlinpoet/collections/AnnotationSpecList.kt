package com.hendraanggrian.kotlinpoet.collections

import com.hendraanggrian.kotlinpoet.AnnotationSpecBuilder
import com.hendraanggrian.kotlinpoet.SpecDslMarker
import com.hendraanggrian.kotlinpoet.annotationSpecOf
import com.hendraanggrian.kotlinpoet.buildAnnotationSpec
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import kotlin.reflect.KClass

/** An [AnnotationSpecList] is responsible for managing a set of annotation instances. */
open class AnnotationSpecList internal constructor(actualList: MutableList<AnnotationSpec>) :
    MutableList<AnnotationSpec> by actualList {

    /** Add annotation from [ClassName]. */
    fun add(type: ClassName): Boolean = add(annotationSpecOf(type))

    /** Add annotation from [Class]. */
    fun add(type: Class<out Annotation>): Boolean = add(annotationSpecOf(type))

    /** Add annotation from [KClass]. */
    fun add(type: KClass<out Annotation>): Boolean = add(annotationSpecOf(type))

    /** Add annotation from [T]. */
    inline fun <reified T : Annotation> add(): Boolean = add(annotationSpecOf<T>())

    /** Add annotation from [ClassName] with custom initialization [builderAction]. */
    inline fun add(
        type: ClassName,
        builderAction: AnnotationSpecBuilder.() -> Unit
    ): Boolean = add(buildAnnotationSpec(type, builderAction))

    /** Add annotation from [Class] with custom initialization [builderAction]. */
    inline fun add(
        type: Class<out Annotation>,
        builderAction: AnnotationSpecBuilder.() -> Unit
    ): Boolean = add(buildAnnotationSpec(type, builderAction))

    /** Add annotation from [KClass] with custom initialization [builderAction]. */
    inline fun add(
        type: KClass<out Annotation>,
        builderAction: AnnotationSpecBuilder.() -> Unit
    ): Boolean = add(buildAnnotationSpec(type, builderAction))

    /** Add annotation from [T] with custom initialization [builderAction]. */
    inline fun <reified T : Annotation> add(
        builderAction: AnnotationSpecBuilder.() -> Unit
    ): Boolean = add(buildAnnotationSpec<T>(builderAction))

    /** Convenient method to add annotation with operator function. */
    operator fun plusAssign(type: ClassName): Unit = plusAssign(annotationSpecOf(type))

    /** Convenient method to add annotation with operator function. */
    operator fun plusAssign(type: Class<out Annotation>): Unit = plusAssign(annotationSpecOf(type))

    /** Convenient method to add annotation with operator function. */
    operator fun plusAssign(type: KClass<out Annotation>): Unit = plusAssign(annotationSpecOf(type))
}

/** Receiver for the `annotations` block providing an extended set of operators for the configuration. */
@SpecDslMarker
class AnnotationSpecListScope(actualList: MutableList<AnnotationSpec>) : AnnotationSpecList(actualList) {

    /** Convenient method to add annotation with receiver type. */
    inline operator fun ClassName.invoke(
        builderAction: AnnotationSpecBuilder.() -> Unit
    ): Boolean = add(this, builderAction)

    /** Convenient method to add annotation with receiver type. */
    inline operator fun Class<out Annotation>.invoke(
        builderAction: AnnotationSpecBuilder.() -> Unit
    ): Boolean = add(this, builderAction)

    /** Convenient method to add annotation with receiver type. */
    inline operator fun KClass<out Annotation>.invoke(
        builderAction: AnnotationSpecBuilder.() -> Unit
    ): Boolean = add(this, builderAction)
}
