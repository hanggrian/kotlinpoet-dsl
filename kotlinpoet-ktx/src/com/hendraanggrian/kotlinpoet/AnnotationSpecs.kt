package com.hendraanggrian.kotlinpoet

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import javax.lang.model.element.AnnotationMirror
import kotlin.reflect.KClass

/** Converts annotation to [AnnotationSpec]. */
fun Annotation.toAnnotation(includeDefaultValues: Boolean = false): AnnotationSpec =
    AnnotationSpec.get(this, includeDefaultValues)

/** Converts mirror to [AnnotationSpec]. */
fun AnnotationMirror.toAnnotation(): AnnotationSpec =
    AnnotationSpec.get(this)

/** Builds a new [AnnotationSpec] from [type]. */
fun buildAnnotation(type: ClassName): AnnotationSpec =
    AnnotationSpec.builder(type).build()

/**
 * Builds a new [AnnotationSpec] from [type],
 * by populating newly created [AnnotationSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildAnnotation(type: ClassName, builderAction: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
    AnnotationSpecBuilder(AnnotationSpec.builder(type)).apply(builderAction).build()

/** Builds a new [AnnotationSpec] from [type]. */
fun <T : Annotation> buildAnnotation(type: Class<T>): AnnotationSpec =
    AnnotationSpec.builder(type).build()

/** Builds a new [AnnotationSpec] from [type]. */
fun <T : Annotation> buildAnnotation(type: KClass<T>): AnnotationSpec =
    buildAnnotation(type.java)

/** Builds a new [AnnotationSpec] from [T]. */
inline fun <reified T : Annotation> buildAnnotation(): AnnotationSpec =
    buildAnnotation(T::class.java)

/**
 * Builds a new [AnnotationSpec] from [type],
 * by populating newly created [AnnotationSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun <T : Annotation> buildAnnotation(
    type: Class<T>,
    builderAction: AnnotationSpecBuilder.() -> Unit
): AnnotationSpec = AnnotationSpecBuilder(AnnotationSpec.builder(type)).apply(builderAction).build()

/**
 * Builds a new [AnnotationSpec] from [type],
 * by populating newly created [AnnotationSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun <T : Annotation> buildAnnotation(
    type: KClass<T>,
    builderAction: AnnotationSpecBuilder.() -> Unit
): AnnotationSpec = buildAnnotation(type.java, builderAction)

/**
 * Builds a new [AnnotationSpec] from [T],
 * by populating newly created [AnnotationSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun <reified T : Annotation> buildAnnotation(builderAction: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
    buildAnnotation(T::class, builderAction)

/** Wrapper of [AnnotationSpec.Builder], providing DSL support as a replacement to Java builder. */
@KotlinpoetDslMarker
class AnnotationSpecBuilder @PublishedApi internal constructor(private val nativeBuilder: AnnotationSpec.Builder) {

    /** Add code as a member of this annotation. */
    fun addMember(name: String, format: String, vararg args: Any) {
        nativeBuilder.addMember(name, format, *args)
    }

    /** Add code as a member of this annotation. */
    fun addMember(name: String, code: CodeBlock): CodeBlock =
        code.also { nativeBuilder.addMember(name, it) }

    /** Add code as a member of this annotation with custom initialization [builderAction]. */
    inline fun addMember(name: String, builderAction: CodeBlockBuilder.() -> Unit): CodeBlock =
        addMember(name, buildCode(builderAction))

    /** Convenient method to add member with operator function. */
    operator fun String.invoke(format: String, vararg args: Any) =
        addMember(this, format, *args)

    /** Convenient method to add member with operator function. */
    operator fun String.invoke(builderAction: CodeBlockBuilder.() -> Unit) =
        addMember(this, builderAction)

    /** Returns native spec. */
    fun build(): AnnotationSpec =
        nativeBuilder.build()
}
