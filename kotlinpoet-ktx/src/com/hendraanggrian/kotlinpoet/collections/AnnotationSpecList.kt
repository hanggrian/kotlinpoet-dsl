package com.hendraanggrian.kotlinpoet.collections

import com.hendraanggrian.kotlinpoet.AnnotationSpecBuilder
import com.hendraanggrian.kotlinpoet.KotlinpoetDslMarker
import com.hendraanggrian.kotlinpoet.annotationSpecOf
import com.hendraanggrian.kotlinpoet.buildAnnotationSpec
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import kotlin.reflect.KClass

/** An [AnnotationSpecList] is responsible for managing a set of annotation instances. */
open class AnnotationSpecList internal constructor(actualList: MutableList<AnnotationSpec>) :
    MutableList<AnnotationSpec> by actualList {

    /** Add annotation from [type], returning the annotation added. */
    fun add(type: ClassName): AnnotationSpec = annotationSpecOf(type).also(::plusAssign)

    /** Add annotation from [type], returning the annotation added. */
    fun add(type: Class<out Annotation>): AnnotationSpec = annotationSpecOf(type).also(::plusAssign)

    /** Add annotation from [type], returning the annotation added. */
    fun add(type: KClass<out Annotation>): AnnotationSpec = annotationSpecOf(type).also(::plusAssign)

    /** Add annotation from reified [T], returning the annotation added. */
    inline fun <reified T : Annotation> add(): AnnotationSpec = annotationSpecOf<T>().also(::plusAssign)

    /** Add annotation from [type] with custom initialization [builderAction], returning the annotation added. */
    inline fun add(
        type: ClassName,
        builderAction: AnnotationSpecBuilder.() -> Unit
    ): AnnotationSpec = buildAnnotationSpec(type, builderAction).also(::plusAssign)

    /** Add annotation from [type] with custom initialization [builderAction], returning the annotation added. */
    inline fun add(
        type: Class<out Annotation>,
        builderAction: AnnotationSpecBuilder.() -> Unit
    ): AnnotationSpec = buildAnnotationSpec(type, builderAction).also(::plusAssign)

    /** Add annotation from [type] with custom initialization [builderAction], returning the annotation added. */
    inline fun add(
        type: KClass<out Annotation>,
        builderAction: AnnotationSpecBuilder.() -> Unit
    ): AnnotationSpec = buildAnnotationSpec(type, builderAction).also(::plusAssign)

    /** Add annotation from reified [T] with custom initialization [builderAction], returning the annotation added. */
    inline fun <reified T : Annotation> add(
        builderAction: AnnotationSpecBuilder.() -> Unit
    ): AnnotationSpec = buildAnnotationSpec<T>(builderAction).also(::plusAssign)

    /** Convenient method to add annotation with operator function. */
    operator fun plusAssign(type: ClassName): Unit = plusAssign(annotationSpecOf(type))

    /** Convenient method to add annotation with operator function. */
    operator fun plusAssign(type: Class<out Annotation>): Unit = plusAssign(annotationSpecOf(type))

    /** Convenient method to add annotation with operator function. */
    operator fun plusAssign(type: KClass<out Annotation>): Unit = plusAssign(annotationSpecOf(type))
}

/** Receiver for the `annotations` block providing an extended set of operators for the configuration. */
@KotlinpoetDslMarker
class AnnotationSpecListScope(actualList: MutableList<AnnotationSpec>) : AnnotationSpecList(actualList) {

    /** Convenient method to add annotation with receiver type. */
    inline operator fun ClassName.invoke(
        builderAction: AnnotationSpecBuilder.() -> Unit
    ): AnnotationSpec = add(this, builderAction)

    /** Convenient method to add annotation with receiver type. */
    inline operator fun Class<out Annotation>.invoke(
        builderAction: AnnotationSpecBuilder.() -> Unit
    ): AnnotationSpec = add(this, builderAction)

    /** Convenient method to add annotation with receiver type. */
    inline operator fun KClass<out Annotation>.invoke(
        builderAction: AnnotationSpecBuilder.() -> Unit
    ): AnnotationSpec = add(this, builderAction)
}
