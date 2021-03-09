package io.github.hendraanggrian.kotlinpoet

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.ParameterizedTypeName
import kotlin.reflect.KClass

/** Converts [Annotation] to [AnnotationSpec]. */
fun Annotation.asAnnotationSpec(includeDefaultValues: Boolean = false): AnnotationSpec =
    AnnotationSpec.get(this, includeDefaultValues)

/** Builds new [AnnotationSpec] from [ClassName]. */
fun annotationSpecOf(type: ClassName): AnnotationSpec = AnnotationSpec.builder(type).build()

/** Builds new [AnnotationSpec] from [ParameterizedTypeName]. */
fun annotationSpecOf(type: ParameterizedTypeName): AnnotationSpec = AnnotationSpec.builder(type).build()

/** Builds new [AnnotationSpec] from [Class]. */
fun annotationSpecOf(type: Class<out Annotation>): AnnotationSpec = AnnotationSpec.builder(type).build()

/** Builds new [AnnotationSpec] from [KClass]. */
fun annotationSpecOf(type: KClass<out Annotation>): AnnotationSpec = AnnotationSpec.builder(type).build()

/** Builds new [AnnotationSpec] from [T]. */
inline fun <reified T : Annotation> annotationSpecOf(): AnnotationSpec = AnnotationSpec.builder(T::class).build()

/**
 * Builds new [AnnotationSpec] from [ClassName],
 * by populating newly created [AnnotationSpecBuilder] using provided [builderAction].
 */
inline fun buildAnnotationSpec(
    type: ClassName,
    builderAction: AnnotationSpecBuilder.() -> Unit
): AnnotationSpec = AnnotationSpecBuilder(AnnotationSpec.builder(type)).apply(builderAction).build()

/**
 * Builds new [AnnotationSpec] from [ParameterizedTypeName],
 * by populating newly created [AnnotationSpecBuilder] using provided [builderAction].
 */
inline fun buildAnnotationSpec(
    type: ParameterizedTypeName,
    builderAction: AnnotationSpecBuilder.() -> Unit
): AnnotationSpec = AnnotationSpecBuilder(AnnotationSpec.builder(type)).apply(builderAction).build()

/**
 * Builds new [AnnotationSpec] from [Class],
 * by populating newly created [AnnotationSpecBuilder] using provided [builderAction].
 */
inline fun buildAnnotationSpec(
    type: Class<out Annotation>,
    builderAction: AnnotationSpecBuilder.() -> Unit
): AnnotationSpec = AnnotationSpecBuilder(AnnotationSpec.builder(type)).apply(builderAction).build()

/**
 * Builds new [AnnotationSpec] from [KClass],
 * by populating newly created [AnnotationSpecBuilder] using provided [builderAction].
 */
inline fun buildAnnotationSpec(
    type: KClass<out Annotation>,
    builderAction: AnnotationSpecBuilder.() -> Unit
): AnnotationSpec = AnnotationSpecBuilder(AnnotationSpec.builder(type)).apply(builderAction).build()

/**
 * Builds new [AnnotationSpec] from [T],
 * by populating newly created [AnnotationSpecBuilder] using provided [builderAction].
 */
inline fun <reified T : Annotation> buildAnnotationSpec(
    builderAction: AnnotationSpecBuilder.() -> Unit
): AnnotationSpec = AnnotationSpecBuilder(AnnotationSpec.builder(T::class)).apply(builderAction).build()

/** Modify existing [AnnotationSpec.Builder] using provided [builderAction]. */
inline fun AnnotationSpec.Builder.edit(
    builderAction: AnnotationSpecBuilder.() -> Unit
): AnnotationSpec.Builder = AnnotationSpecBuilder(this).apply(builderAction).nativeBuilder

/**
 * Wrapper of [AnnotationSpec.Builder], providing DSL support as a replacement to Java builder.
 * @param nativeBuilder source builder.
 */
@SpecDslMarker
class AnnotationSpecBuilder(val nativeBuilder: AnnotationSpec.Builder) {

    /** Members of this annotation. */
    val members: MutableList<CodeBlock> get() = nativeBuilder.members

    /** Tags of this annotation. */
    val tags: MutableMap<KClass<*>, Any> = nativeBuilder.tags

    /** Add code as a member of this annotation. */
    fun addMember(format: String, vararg args: Any) {
        nativeBuilder.addMember(format, *args)
    }

    /** Add code as a member of this annotation. */
    fun addMember(code: CodeBlock) {
        nativeBuilder.addMember(code)
    }

    /** Add code as a member of this annotation with custom initialization [builderAction]. */
    inline fun addMember(builderAction: CodeBlockBuilder.() -> Unit): Unit =
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
