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
fun AnnotationMirror.toAnnotation(): AnnotationSpec = AnnotationSpec.get(this)

/** Builds a new [AnnotationSpec] from [type]. */
fun buildAnnotation(type: ClassName): AnnotationSpec = AnnotationSpec.builder(type).build()

/**
 * Builds a new [AnnotationSpec] from [type],
 * by populating newly created [AnnotationSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildAnnotation(type: ClassName, builderAction: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
    AnnotationSpecBuilder(AnnotationSpec.builder(type)).apply(builderAction).build()

/** Builds a new [AnnotationSpec] from [type]. */
fun <T : Annotation> buildAnnotation(type: Class<T>): AnnotationSpec = AnnotationSpec.builder(type).build()

/** Builds a new [AnnotationSpec] from [type]. */
fun <T : Annotation> buildAnnotation(type: KClass<T>): AnnotationSpec = AnnotationSpec.builder(type).build()

/** Builds a new [AnnotationSpec] from [T]. */
inline fun <reified T : Annotation> buildAnnotation(): AnnotationSpec = buildAnnotation(T::class)

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
): AnnotationSpec = AnnotationSpecBuilder(AnnotationSpec.builder(type)).apply(builderAction).build()

/**
 * Builds a new [AnnotationSpec] from [T],
 * by populating newly created [AnnotationSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun <reified T : Annotation> buildAnnotation(builderAction: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
    buildAnnotation(T::class, builderAction)

/** Wrapper of [AnnotationSpec.Builder], providing DSL support as a replacement to Java builder. */
@KotlinpoetDslMarker
class AnnotationSpecBuilder @PublishedApi internal constructor(private val nativeBuilder: AnnotationSpec.Builder) {

    /** Members of this builder. */
    val members: MutableList<CodeBlock> get() = nativeBuilder.members

    /** Add code as a member of this annotation. */
    fun addMember(format: String, vararg args: Any) {
        nativeBuilder.addMember(format, *args)
    }

    /** Add code as a member of this annotation. */
    fun addMember(code: CodeBlock): CodeBlock = code.also { nativeBuilder.addMember(it) }

    /** Add code as a member of this annotation with custom initialization [builderAction]. */
    inline fun addMember(builderAction: CodeBlockBlockBuilder.() -> Unit): CodeBlock =
        addMember(buildCode(builderAction))

    /** Set [AnnotationSpec.UseSiteTarget]. */
    var useSiteTarget: AnnotationSpec.UseSiteTarget
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            nativeBuilder.useSiteTarget(value)
        }

    /** Returns native spec. */
    fun build(): AnnotationSpec = nativeBuilder.build()
}
