package com.hendraanggrian.kotlinpoet.dsl

import com.hendraanggrian.kotlinpoet.AnnotationSpecBuilder
import com.hendraanggrian.kotlinpoet.KotlinpoetDslMarker
import com.hendraanggrian.kotlinpoet.buildAnnotation
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import kotlin.reflect.KClass

/** An [AnnotationSpecContainer] is responsible for managing a set of annotation instances. */
abstract class AnnotationSpecContainer internal constructor() {

    /** Add annotation to this container. */
    abstract fun add(spec: AnnotationSpec)

    /** Add annotation from [type], returning the annotation added. */
    fun add(type: ClassName): AnnotationSpec = buildAnnotation(type).also { add(it) }

    /** Add annotation from [type] with custom initialization [builderAction], returning the annotation added. */
    inline fun add(type: ClassName, builderAction: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
        buildAnnotation(type, builderAction).also { add(it) }

    /** Add annotation from [type], returning the annotation added. */
    fun <T : Annotation> add(type: Class<T>): AnnotationSpec = buildAnnotation(type).also { add(it) }

    /** Add annotation from [type] with custom initialization [builderAction], returning the annotation added. */
    inline fun <T : Annotation> add(type: Class<T>, builderAction: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
        buildAnnotation(type, builderAction).also { add(it) }

    /** Add annotation from [type], returning the annotation added. */
    fun <T : Annotation> add(type: KClass<T>): AnnotationSpec = buildAnnotation(type).also { add(it) }

    /** Add annotation from [type] with custom initialization [builderAction], returning the annotation added. */
    inline fun <T : Annotation> add(type: KClass<T>, builderAction: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
        buildAnnotation(type, builderAction).also { add(it) }

    /** Add annotation from reified [T], returning the annotation added. */
    inline fun <reified T : Annotation> add(): AnnotationSpec = buildAnnotation<T>().also { add(it) }

    /** Add annotation from reified [T] with custom initialization [builderAction], returning the annotation added. */
    inline fun <reified T : Annotation> add(builderAction: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
        buildAnnotation<T>(builderAction).also { add(it) }

    /** Convenient method to add annotation with operator function. */
    operator fun plusAssign(spec: AnnotationSpec) {
        add(spec)
    }

    /** Convenient method to add annotation with operator function. */
    operator fun plusAssign(type: ClassName) {
        add(type)
    }

    /** Convenient method to add annotation with operator function. */
    operator fun <T : Annotation> plusAssign(type: Class<T>) {
        add(type)
    }

    /** Convenient method to add annotation with operator function. */
    operator fun <T : Annotation> plusAssign(type: KClass<T>) {
        add(type)
    }

    /** Configure this container with DSL. */
    inline operator fun invoke(configuration: AnnotationSpecContainerScope.() -> Unit): Unit =
        AnnotationSpecContainerScope(this).configuration()
}

/** Receiver for the `annotations` block providing an extended set of operators for the configuration. */
@KotlinpoetDslMarker
class AnnotationSpecContainerScope @PublishedApi internal constructor(private val container: AnnotationSpecContainer) :
    AnnotationSpecContainer() {

    override fun add(spec: AnnotationSpec) = container.add(spec)

    /** Convenient method to add annotation with receiver type. */
    inline operator fun ClassName.invoke(builderAction: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
        add(this, builderAction)

    /** Convenient method to add annotation with receiver type. */
    inline operator fun <T : Annotation> Class<T>.invoke(builderAction: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
        add(this, builderAction)

    /** Convenient method to add annotation with receiver type. */
    inline operator fun <T : Annotation> KClass<T>.invoke(builderAction: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
        add(this, builderAction)
}
