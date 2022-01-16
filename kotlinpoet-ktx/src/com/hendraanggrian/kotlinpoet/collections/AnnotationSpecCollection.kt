@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.kotlinpoet.collections

import com.hendraanggrian.kotlinpoet.AnnotationSpecBuilder
import com.hendraanggrian.kotlinpoet.SpecMarker
import com.hendraanggrian.kotlinpoet.buildAnnotationSpec
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import kotlin.reflect.KClass

/** An [AnnotationSpecCollection] is responsible for managing a set of annotation instances. */
open class AnnotationSpecCollection(actualList: MutableList<AnnotationSpec>) :
    MutableList<AnnotationSpec> by actualList {

    /** Add annotation from [ClassName]. */
    fun add(type: ClassName): Boolean = add(AnnotationSpec.builder(type).build())

    /** Add annotation from [ClassName] with custom initialization [configuration]. */
    fun add(type: ClassName, configuration: AnnotationSpecBuilder.() -> Unit): Boolean =
        add(buildAnnotationSpec(type, configuration))

    /** Add annotation from [Class]. */
    fun add(type: Class<out Annotation>): Boolean = add(AnnotationSpec.builder(type).build())

    /** Add annotation from [Class] with custom initialization [configuration]. */
    fun add(type: Class<out Annotation>, configuration: AnnotationSpecBuilder.() -> Unit): Boolean =
        add(buildAnnotationSpec(type, configuration))

    /** Add annotation from [KClass]. */
    fun add(type: KClass<out Annotation>): Boolean = add(AnnotationSpec.builder(type).build())

    /** Add annotation from [KClass] with custom initialization [configuration]. */
    fun add(type: KClass<out Annotation>, configuration: AnnotationSpecBuilder.() -> Unit): Boolean =
        add(buildAnnotationSpec(type, configuration))

    /** Add annotation from [T]. */
    inline fun <reified T : Annotation> add(): Boolean = add(AnnotationSpec.builder(T::class).build())

    /** Add annotation from [T] with custom initialization [configuration]. */
    inline fun <reified T : Annotation> add(noinline configuration: AnnotationSpecBuilder.() -> Unit): Boolean =
        add(buildAnnotationSpec<T>(configuration))

    /** Convenient method to add annotation with operator function. */
    inline operator fun plusAssign(type: ClassName) {
        add(type)
    }

    /** Convenient method to add annotation with operator function. */
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
class AnnotationSpecCollectionScope(actualList: MutableList<AnnotationSpec>) : AnnotationSpecCollection(actualList) {

    /** @see AnnotationSpecCollection.add */
    operator fun ClassName.invoke(configuration: AnnotationSpecBuilder.() -> Unit): Boolean = add(this, configuration)

    /** @see AnnotationSpecCollection.add */
    operator fun Class<out Annotation>.invoke(configuration: AnnotationSpecBuilder.() -> Unit): Boolean =
        add(this, configuration)

    /** @see AnnotationSpecCollection.add */
    operator fun KClass<out Annotation>.invoke(configuration: AnnotationSpecBuilder.() -> Unit): Boolean =
        add(this, configuration)
}
