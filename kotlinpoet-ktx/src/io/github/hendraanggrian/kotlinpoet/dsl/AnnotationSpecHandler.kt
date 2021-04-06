package io.github.hendraanggrian.kotlinpoet.dsl

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import io.github.hendraanggrian.kotlinpoet.AnnotationSpecBuilder
import io.github.hendraanggrian.kotlinpoet.SpecDslMarker
import io.github.hendraanggrian.kotlinpoet.annotationSpecOf
import io.github.hendraanggrian.kotlinpoet.buildAnnotationSpec
import kotlin.reflect.KClass

/** An [AnnotationSpecHandler] is responsible for managing a set of annotation instances. */
open class AnnotationSpecHandler(actualList: MutableList<AnnotationSpec>) : MutableList<AnnotationSpec> by actualList {

    /** Add annotation from [ClassName]. */
    fun add(type: ClassName): Boolean = add(annotationSpecOf(type))

    /** Add annotation from [ClassName] with custom initialization [configuration]. */
    fun add(type: ClassName, configuration: AnnotationSpecBuilder.() -> Unit): Boolean =
        add(buildAnnotationSpec(type, configuration))

    /** Add annotation from [Class]. */
    fun add(type: Class<out Annotation>): Boolean = add(annotationSpecOf(type))

    /** Add annotation from [Class] with custom initialization [configuration]. */
    fun add(type: Class<out Annotation>, configuration: AnnotationSpecBuilder.() -> Unit): Boolean =
        add(buildAnnotationSpec(type, configuration))

    /** Add annotation from [KClass]. */
    fun add(type: KClass<out Annotation>): Boolean = add(annotationSpecOf(type))

    /** Add annotation from [KClass] with custom initialization [configuration]. */
    fun add(type: KClass<out Annotation>, configuration: AnnotationSpecBuilder.() -> Unit): Boolean =
        add(buildAnnotationSpec(type, configuration))

    /** Add annotation from [T]. */
    inline fun <reified T : Annotation> add(): Boolean = add(annotationSpecOf<T>())

    /** Add annotation from [T] with custom initialization [configuration]. */
    inline fun <reified T : Annotation> add(noinline configuration: AnnotationSpecBuilder.() -> Unit): Boolean =
        add(buildAnnotationSpec<T>(configuration))

    /** Convenient method to add annotation with operator function. */
    @Suppress("NOTHING_TO_INLINE")
    inline operator fun plusAssign(type: ClassName) {
        add(type)
    }

    /** Convenient method to add annotation with operator function. */
    @Suppress("NOTHING_TO_INLINE")
    inline operator fun plusAssign(type: Class<out Annotation>) {
        add(type)
    }

    /** Convenient method to add annotation with operator function. */
    @Suppress("NOTHING_TO_INLINE")
    inline operator fun plusAssign(type: KClass<out Annotation>) {
        add(type)
    }
}

/** Receiver for the `annotations` block providing an extended set of operators for the configuration. */
@SpecDslMarker
class AnnotationSpecHandlerScope(actualList: MutableList<AnnotationSpec>) : AnnotationSpecHandler(actualList) {

    /** @see AnnotationSpecHandler.add */
    operator fun ClassName.invoke(configuration: AnnotationSpecBuilder.() -> Unit): Boolean = add(this, configuration)

    /** @see AnnotationSpecHandler.add */
    operator fun Class<out Annotation>.invoke(configuration: AnnotationSpecBuilder.() -> Unit): Boolean =
        add(this, configuration)

    /** @see AnnotationSpecHandler.add */
    operator fun KClass<out Annotation>.invoke(configuration: AnnotationSpecBuilder.() -> Unit): Boolean =
        add(this, configuration)
}
