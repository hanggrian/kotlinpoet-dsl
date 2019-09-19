package com.hendraanggrian.kotlinpoet.dsl

import com.hendraanggrian.kotlinpoet.AnnotationSpecBuilder
import com.hendraanggrian.kotlinpoet.KotlinpoetDslMarker
import com.hendraanggrian.kotlinpoet.buildAnnotation
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import kotlin.reflect.KClass

private interface AnnotationAddable {

    /** Add annotation to this container, returning the annotation added. */
    fun add(spec: AnnotationSpec): AnnotationSpec
}

/** An [AnnotationContainer] is responsible for managing a set of annotation instances. */
abstract class AnnotationContainer internal constructor() : AnnotationAddable {

    /** Add annotation from [type], returning the annotation added. */
    fun add(type: ClassName): AnnotationSpec =
        add(buildAnnotation(type))

    /** Add annotation from [type] with custom initialization [builderAction], returning the annotation added. */
    inline fun add(type: ClassName, builderAction: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
        add(buildAnnotation(type, builderAction))

    /** Add annotation from [type], returning the annotation added. */
    fun <T : Annotation> add(type: Class<T>): AnnotationSpec =
        add(buildAnnotation(type))

    /** Add annotation from [type] with custom initialization [builderAction], returning the annotation added. */
    inline fun <T : Annotation> add(type: Class<T>, builderAction: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
        add(buildAnnotation(type, builderAction))

    /** Add annotation from [type], returning the annotation added. */
    fun <T : Annotation> add(type: KClass<T>): AnnotationSpec =
        add(buildAnnotation(type))

    /** Add annotation from [type] with custom initialization [builderAction], returning the annotation added. */
    inline fun <T : Annotation> add(type: KClass<T>, builderAction: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
        add(buildAnnotation(type, builderAction))

    /** Add annotation from reified [T], returning the annotation added. */
    inline fun <reified T : Annotation> add(): AnnotationSpec =
        add(buildAnnotation<T>())

    /** Add annotation from reified [T] with custom initialization [builderAction], returning the annotation added. */
    inline fun <reified T : Annotation> add(builderAction: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
        add(buildAnnotation<T>(builderAction))

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
    inline operator fun invoke(configuration: AnnotationContainerScope.() -> Unit) =
        AnnotationContainerScope(this).configuration()
}

/** Receiver for the `annotations` block providing an extended set of operators for the configuration. */
@KotlinpoetDslMarker
class AnnotationContainerScope @PublishedApi internal constructor(container: AnnotationContainer) :
    AnnotationContainer(), AnnotationAddable by container {

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
