package com.hendraanggrian.kotlinpoet.dsl

import com.hendraanggrian.kotlinpoet.AnnotationSpecBuilder
import com.hendraanggrian.kotlinpoet.KotlinpoetDslMarker
import com.hendraanggrian.kotlinpoet.annotationSpecOf
import com.hendraanggrian.kotlinpoet.buildAnnotationSpec
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import kotlin.reflect.KClass

/** An [AnnotationSpecContainer] is responsible for managing a set of annotation instances. */
abstract class AnnotationSpecContainer {

    /** Add annotation to this container. */
    abstract fun add(spec: AnnotationSpec)

    /** Add annotation from [type], returning the annotation added. */
    fun add(type: ClassName): AnnotationSpec = annotationSpecOf(type).also { add(it) }

    /** Add annotation from [type] with custom initialization [builderAction], returning the annotation added. */
    inline fun add(type: ClassName, builderAction: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
        buildAnnotationSpec(type, builderAction).also { add(it) }

    /** Add annotation from [type], returning the annotation added. */
    fun add(type: Class<out Annotation>): AnnotationSpec = annotationSpecOf(type).also { add(it) }

    /** Add annotation from [type] with custom initialization [builderAction], returning the annotation added. */
    inline fun add(type: Class<out Annotation>, builderAction: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
        buildAnnotationSpec(type, builderAction).also { add(it) }

    /** Add annotation from [type], returning the annotation added. */
    fun add(type: KClass<out Annotation>): AnnotationSpec = annotationSpecOf(type).also { add(it) }

    /** Add annotation from [type] with custom initialization [builderAction], returning the annotation added. */
    inline fun add(type: KClass<out Annotation>, builderAction: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
        buildAnnotationSpec(type, builderAction).also { add(it) }

    /** Add annotation from reified [T], returning the annotation added. */
    inline fun <reified T : Annotation> add(): AnnotationSpec = annotationSpecOf<T>().also { add(it) }

    /** Add annotation from reified [T] with custom initialization [builderAction], returning the annotation added. */
    inline fun <reified T : Annotation> add(builderAction: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
        buildAnnotationSpec<T>(builderAction).also { add(it) }

    /** Convenient method to add annotation with operator function. */
    operator fun plusAssign(spec: AnnotationSpec) {
        add(spec)
    }

    /** Convenient method to add annotation with operator function. */
    operator fun plusAssign(type: ClassName) {
        add(type)
    }

    /** Convenient method to add annotation with operator function. */
    operator fun plusAssign(type: Class<out Annotation>) {
        add(type)
    }

    /** Convenient method to add annotation with operator function. */
    operator fun plusAssign(type: KClass<out Annotation>) {
        add(type)
    }
}

/** Receiver for the `annotations` block providing an extended set of operators for the configuration. */
@KotlinpoetDslMarker
class AnnotationSpecContainerScope(private val container: AnnotationSpecContainer) : AnnotationSpecContainer() {

    override fun add(spec: AnnotationSpec) = container.add(spec)

    /** Convenient method to add annotation with receiver type. */
    inline operator fun ClassName.invoke(builderAction: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
        add(this, builderAction)

    /** Convenient method to add annotation with receiver type. */
    inline operator fun Class<out Annotation>.invoke(builderAction: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
        add(this, builderAction)

    /** Convenient method to add annotation with receiver type. */
    inline operator fun KClass<out Annotation>.invoke(builderAction: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
        add(this, builderAction)
}
