@file:OptIn(ExperimentalContracts::class)

package com.hendraanggrian.kotlinpoet

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.DelicateKotlinPoetApi
import com.squareup.kotlinpoet.ParameterizedTypeName
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
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

/**
 * Creates new [AnnotationSpec] by populating newly created [AnnotationSpecBuilder] using provided
 * [configuration].
 */
inline fun buildAnnotationSpec(
    type: ClassName,
    configuration: AnnotationSpecBuilder.() -> Unit,
): AnnotationSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return AnnotationSpecBuilder(AnnotationSpec.builder(type)).apply(configuration).build()
}

/**
 * Creates new [AnnotationSpec] by populating newly created [AnnotationSpecBuilder] using provided
 * [configuration].
 */
inline fun buildAnnotationSpec(
    type: ParameterizedTypeName,
    configuration: AnnotationSpecBuilder.() -> Unit,
): AnnotationSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return AnnotationSpecBuilder(AnnotationSpec.builder(type)).apply(configuration).build()
}

/**
 * Inserts new [AnnotationSpec] by populating newly created [AnnotationSpecBuilder] using provided
 * [configuration].
 */
inline fun AnnotationSpecHandler.annotation(
    type: ClassName,
    configuration: AnnotationSpecBuilder.() -> Unit,
): AnnotationSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return buildAnnotationSpec(type, configuration).also(::annotation)
}

/**
 * Inserts new [AnnotationSpec] by populating newly created [AnnotationSpecBuilder] using provided
 * [configuration].
 */
inline fun AnnotationSpecHandler.annotation(
    type: ParameterizedTypeName,
    configuration: AnnotationSpecBuilder.() -> Unit,
): AnnotationSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return buildAnnotationSpec(type, configuration).also(::annotation)
}

/**
 * Inserts new [AnnotationSpec] by populating newly created [AnnotationSpecBuilder] using provided
 * [configuration].
 */
@DelicateKotlinPoetApi(DELICATE_API)
inline fun AnnotationSpecHandler.annotation(
    type: Class<out Annotation>,
    configuration: AnnotationSpecBuilder.() -> Unit,
): AnnotationSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return buildAnnotationSpec(type.name2, configuration).also(::annotation)
}

/**
 * Inserts new [AnnotationSpec] by populating newly created [AnnotationSpecBuilder] using provided
 * [configuration].
 */
inline fun AnnotationSpecHandler.annotation(
    type: KClass<out Annotation>,
    configuration: AnnotationSpecBuilder.() -> Unit,
): AnnotationSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return buildAnnotationSpec(type.name, configuration).also(::annotation)
}

/** Convenient method to insert [AnnotationSpec] using reified type. */
inline fun <reified T> AnnotationSpecHandler.annotation(): AnnotationSpec =
    AnnotationSpec.builder(T::class.name).build().also(::annotation)

/** Invokes DSL to configure [AnnotationSpec] collection. */
fun AnnotationSpecHandler.annotations(configuration: AnnotationSpecHandlerScope.() -> Unit) {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    AnnotationSpecHandlerScope(this).configuration()
}

/** Responsible for managing a set of [AnnotationSpec] instances. */
sealed interface AnnotationSpecHandler {
    fun annotation(annotation: AnnotationSpec)

    fun annotation(type: ClassName): AnnotationSpec =
        AnnotationSpec.builder(type).build().also(::annotation)

    fun annotation(type: ParameterizedTypeName): AnnotationSpec =
        AnnotationSpec.builder(type).build().also(::annotation)

    @DelicateKotlinPoetApi(DELICATE_API)
    fun annotation(type: Class<out Annotation>): AnnotationSpec =
        AnnotationSpec.builder(type).build().also(::annotation)

    fun annotation(type: KClass<out Annotation>): AnnotationSpec =
        AnnotationSpec.builder(type).build().also(::annotation)
}

/**
 * Receiver for the `annotations` block providing an extended set of operators for the
 * configuration.
 */
@KotlinpoetDsl
class AnnotationSpecHandlerScope internal constructor(
    handler: AnnotationSpecHandler,
) : AnnotationSpecHandler by handler {
    /** @see annotation */
    operator fun ClassName.invoke(
        configuration: AnnotationSpecBuilder.() -> Unit,
    ): AnnotationSpec = buildAnnotationSpec(this, configuration).also(::annotation)

    /** @see annotation */
    operator fun ParameterizedTypeName.invoke(
        configuration: AnnotationSpecBuilder.() -> Unit,
    ): AnnotationSpec = buildAnnotationSpec(this, configuration).also(::annotation)

    /** @see annotation */
    operator fun Class<*>.invoke(configuration: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
        buildAnnotationSpec(name2, configuration).also(::annotation)

    /** @see annotation */
    operator fun KClass<*>.invoke(
        configuration: AnnotationSpecBuilder.() -> Unit,
    ): AnnotationSpec = buildAnnotationSpec(name, configuration).also(::annotation)
}

/** Wrapper of [AnnotationSpec.Builder], providing DSL support as a replacement to Java builder. */
@KotlinpoetDsl
class AnnotationSpecBuilder(
    private val nativeBuilder: AnnotationSpec.Builder,
) {
    val members: MutableList<CodeBlock> get() = nativeBuilder.members
    val tags: MutableMap<KClass<*>, Any> get() = nativeBuilder.tags

    fun member(format: String, vararg args: Any) {
        nativeBuilder.addMember(format, *args)
    }

    fun member(code: CodeBlock) {
        nativeBuilder.addMember(code)
    }

    var useSiteTarget: AnnotationSpec.UseSiteTarget?
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
        get() = noGetter()
        set(value) {
            nativeBuilder.useSiteTarget(value)
        }

    fun build(): AnnotationSpec = nativeBuilder.build()
}
