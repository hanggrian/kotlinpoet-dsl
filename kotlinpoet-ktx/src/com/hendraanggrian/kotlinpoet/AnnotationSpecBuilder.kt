package com.hendraanggrian.kotlinpoet

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.ParameterizedTypeName
import javax.lang.model.element.AnnotationMirror
import kotlin.reflect.KClass

val ANNOTATION_FILE: AnnotationSpec.UseSiteTarget = AnnotationSpec.UseSiteTarget.FILE
val ANNOTATION_PROPERTY: AnnotationSpec.UseSiteTarget = AnnotationSpec.UseSiteTarget.PROPERTY
val ANNOTATION_FIELD: AnnotationSpec.UseSiteTarget = AnnotationSpec.UseSiteTarget.FIELD
val ANNOTATION_GET: AnnotationSpec.UseSiteTarget = AnnotationSpec.UseSiteTarget.GET
val ANNOTATION_SET: AnnotationSpec.UseSiteTarget = AnnotationSpec.UseSiteTarget.SET
val ANNOTATION_RECEIVER: AnnotationSpec.UseSiteTarget = AnnotationSpec.UseSiteTarget.RECEIVER
val ANNOTATION_PARAM: AnnotationSpec.UseSiteTarget = AnnotationSpec.UseSiteTarget.PARAM
val ANNOTATION_SETPARAM: AnnotationSpec.UseSiteTarget = AnnotationSpec.UseSiteTarget.SETPARAM
val ANNOTATION_DELEGATE: AnnotationSpec.UseSiteTarget = AnnotationSpec.UseSiteTarget.DELEGATE

/** Converts [Annotation] to [AnnotationSpec]. */
inline fun Annotation.toAnnotationSpec(includeDefaultValues: Boolean = false): AnnotationSpec =
    AnnotationSpec.get(this, includeDefaultValues)

/** Converts [AnnotationMirror] to [AnnotationSpec]. */
inline fun AnnotationMirror.toAnnotationSpec(): AnnotationSpec = AnnotationSpec.get(this)

/**
 * Builds new [AnnotationSpec] from [ClassName],
 * by populating newly created [AnnotationSpecBuilder] using provided [configuration].
 */
fun buildAnnotationSpec(type: ClassName, configuration: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
    AnnotationSpecBuilder(AnnotationSpec.builder(type)).apply(configuration).build()

/**
 * Builds new [AnnotationSpec] from [ParameterizedTypeName],
 * by populating newly created [AnnotationSpecBuilder] using provided [configuration].
 */
fun buildAnnotationSpec(type: ParameterizedTypeName, configuration: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
    AnnotationSpecBuilder(AnnotationSpec.builder(type)).apply(configuration).build()

/**
 * Builds new [AnnotationSpec] from [Class],
 * by populating newly created [AnnotationSpecBuilder] using provided [configuration].
 */
fun buildAnnotationSpec(type: Class<out Annotation>, configuration: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
    AnnotationSpecBuilder(AnnotationSpec.builder(type)).apply(configuration).build()

/**
 * Builds new [AnnotationSpec] from [KClass],
 * by populating newly created [AnnotationSpecBuilder] using provided [configuration].
 */
fun buildAnnotationSpec(type: KClass<out Annotation>, configuration: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
    AnnotationSpecBuilder(AnnotationSpec.builder(type)).apply(configuration).build()

/**
 * Builds new [AnnotationSpec] from [T],
 * by populating newly created [AnnotationSpecBuilder] using provided [configuration].
 */
inline fun <reified T : Annotation> buildAnnotationSpec(noinline configuration: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
    buildAnnotationSpec(T::class, configuration)

/** Modify existing [AnnotationSpec.Builder] using provided [configuration]. */
fun AnnotationSpec.Builder.edit(configuration: AnnotationSpecBuilder.() -> Unit): AnnotationSpec.Builder =
    AnnotationSpecBuilder(this).apply(configuration).nativeBuilder

/**
 * Wrapper of [AnnotationSpec.Builder], providing DSL support as a replacement to Java builder.
 * @param nativeBuilder source builder.
 */
@SpecMarker
class AnnotationSpecBuilder internal constructor(val nativeBuilder: AnnotationSpec.Builder) {

    /** Members of this annotation. */
    val members: MutableList<CodeBlock> get() = nativeBuilder.members

    /** Tags of this annotation. */
    val tags: MutableMap<KClass<*>, Any> get() = nativeBuilder.tags

    /** Add code as a member of this annotation. */
    fun addMember(format: String, vararg args: Any) {
        nativeBuilder.addMember(format, *args)
    }

    /** Add code as a member of this annotation. */
    fun addMember(code: CodeBlock) {
        nativeBuilder.addMember(code)
    }

    /** Sets [AnnotationSpec.UseSiteTarget]. */
    var useSiteTarget: AnnotationSpec.UseSiteTarget?
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            nativeBuilder.useSiteTarget(value)
        }

    /** Returns native spec. */
    fun build(): AnnotationSpec = nativeBuilder.build()
}
