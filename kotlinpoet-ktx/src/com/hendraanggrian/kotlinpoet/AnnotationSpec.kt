package com.hendraanggrian.kotlinpoet

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import javax.lang.model.element.AnnotationMirror
import kotlin.reflect.KClass

/** Converts annotation to [AnnotationSpec]. */
fun Annotation.asAnnotationSpec(includeDefaultValues: Boolean = false): AnnotationSpec =
    AnnotationSpec.get(this, includeDefaultValues)

/** Converts mirror to [AnnotationSpec]. */
fun AnnotationMirror.asAnnotationSpec(): AnnotationSpec =
    AnnotationSpec.get(this)

/** Builds a new [AnnotationSpec] from [type]. */
fun annotationSpecOf(type: ClassName): AnnotationSpec =
    AnnotationSpec.builder(type).build()

/**
 * Builds a new [AnnotationSpec] from [type],
 * by populating newly created [AnnotationSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildAnnotationSpec(type: ClassName, builderAction: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
    AnnotationSpec.builder(type).build(builderAction)

/** Builds a new [AnnotationSpec] from [type]. */
fun annotationSpecOf(type: Class<out Annotation>): AnnotationSpec =
    AnnotationSpec.builder(type).build()

/** Builds a new [AnnotationSpec] from [type]. */
fun annotationSpecOf(type: KClass<out Annotation>): AnnotationSpec =
    AnnotationSpec.builder(type).build()

/** Builds a new [AnnotationSpec] from [T]. */
inline fun <reified T : Annotation> annotationSpecOf(): AnnotationSpec =
    annotationSpecOf(T::class)

/**
 * Builds a new [AnnotationSpec] from [type],
 * by populating newly created [AnnotationSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildAnnotationSpec(
    type: Class<out Annotation>,
    builderAction: AnnotationSpecBuilder.() -> Unit
): AnnotationSpec = AnnotationSpec.builder(type).build(builderAction)

/**
 * Builds a new [AnnotationSpec] from [type],
 * by populating newly created [AnnotationSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildAnnotationSpec(
    type: KClass<out Annotation>,
    builderAction: AnnotationSpecBuilder.() -> Unit
): AnnotationSpec = AnnotationSpec.builder(type).build(builderAction)

/**
 * Builds a new [AnnotationSpec] from [T],
 * by populating newly created [AnnotationSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun <reified T : Annotation> buildAnnotationSpec(builderAction: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
    buildAnnotationSpec(T::class, builderAction)

/** Modify existing [AnnotationSpec.Builder] using provided [builderAction] and then building it. */
inline fun AnnotationSpec.Builder.build(builderAction: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
    AnnotationSpecBuilder(this).apply(builderAction).build()

/** Wrapper of [AnnotationSpec.Builder], providing DSL support as a replacement to Java builder. */
@KotlinpoetDslMarker
class AnnotationSpecBuilder(private val nativeBuilder: AnnotationSpec.Builder) {

    /** Members of this annotation. */
    val members: MutableList<CodeBlock> get() = nativeBuilder.members

    /** Tags of this annotation. */
    val tags: MutableMap<KClass<*>, Any> = nativeBuilder.tags

    /** Add code as a member of this annotation. */
    fun addMember(format: String, vararg args: Any) {
        nativeBuilder.addMember(format, *args)
    }

    /** Add code as a member of this annotation. */
    fun addMember(code: CodeBlock): CodeBlock = code.also { nativeBuilder.addMember(it) }

    /** Add code as a member of this annotation with custom initialization [builderAction]. */
    inline fun addMember(builderAction: CodeBlockBuilder.() -> Unit): CodeBlock =
        addMember(buildCodeBlock(builderAction))

    /** Sets [AnnotationSpec.UseSiteTarget]. */
    var useSiteTarget: AnnotationSpec.UseSiteTarget
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            nativeBuilder.useSiteTarget(value)
        }

    /** Returns native spec. */
    fun build(): AnnotationSpec = nativeBuilder.build()
}
