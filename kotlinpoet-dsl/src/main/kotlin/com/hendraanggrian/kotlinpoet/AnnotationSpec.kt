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

public val ANNOTATION_FILE: AnnotationSpec.UseSiteTarget = AnnotationSpec.UseSiteTarget.FILE
public val ANNOTATION_PROPERTY: AnnotationSpec.UseSiteTarget = AnnotationSpec.UseSiteTarget.PROPERTY
public val ANNOTATION_FIELD: AnnotationSpec.UseSiteTarget = AnnotationSpec.UseSiteTarget.FIELD
public val ANNOTATION_GET: AnnotationSpec.UseSiteTarget = AnnotationSpec.UseSiteTarget.GET
public val ANNOTATION_SET: AnnotationSpec.UseSiteTarget = AnnotationSpec.UseSiteTarget.SET
public val ANNOTATION_RECEIVER: AnnotationSpec.UseSiteTarget = AnnotationSpec.UseSiteTarget.RECEIVER
public val ANNOTATION_PARAM: AnnotationSpec.UseSiteTarget = AnnotationSpec.UseSiteTarget.PARAM
public val ANNOTATION_SETPARAM: AnnotationSpec.UseSiteTarget = AnnotationSpec.UseSiteTarget.SETPARAM
public val ANNOTATION_DELEGATE: AnnotationSpec.UseSiteTarget = AnnotationSpec.UseSiteTarget.DELEGATE

public fun ClassName.asAnnotationSpec(): AnnotationSpec = AnnotationSpec.builder(this).build()

public fun ParameterizedTypeName.asAnnotationSpec(): AnnotationSpec =
    AnnotationSpec.builder(this).build()

/**
 * Creates new [AnnotationSpec] by populating newly created [AnnotationSpecBuilder] using provided
 * [configuration].
 */
public inline fun buildAnnotationSpec(
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
public inline fun buildAnnotationSpec(
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
public inline fun AnnotationSpecHandler.annotation(
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
public inline fun AnnotationSpecHandler.annotation(
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
public inline fun AnnotationSpecHandler.annotation(
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
public inline fun AnnotationSpecHandler.annotation(
    type: KClass<out Annotation>,
    configuration: AnnotationSpecBuilder.() -> Unit,
): AnnotationSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return buildAnnotationSpec(type.name, configuration).also(::annotation)
}

/** Convenient method to insert [AnnotationSpec] using reified type. */
public inline fun <reified T> AnnotationSpecHandler.annotation(): AnnotationSpec =
    T::class.name.asAnnotationSpec().also(::annotation)

/** Invokes DSL to configure [AnnotationSpec] collection. */
public fun AnnotationSpecHandler.annotations(configuration: AnnotationSpecHandlerScope.() -> Unit) {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    AnnotationSpecHandlerScope.of(this).configuration()
}

/** Responsible for managing a set of [AnnotationSpec] instances. */
public interface AnnotationSpecHandler {
    public fun annotation(annotation: AnnotationSpec)

    public fun annotation(type: ClassName): AnnotationSpec =
        type.asAnnotationSpec().also(::annotation)

    public fun annotation(type: ParameterizedTypeName): AnnotationSpec =
        type.asAnnotationSpec().also(::annotation)

    @DelicateKotlinPoetApi(DELICATE_API)
    public fun annotation(type: Class<out Annotation>): AnnotationSpec =
        type.name2.asAnnotationSpec().also(::annotation)

    public fun annotation(type: KClass<out Annotation>): AnnotationSpec =
        type.name.asAnnotationSpec().also(::annotation)
}

/**
 * Receiver for the `annotations` block providing an extended set of operators for the
 * configuration.
 */
@KotlinpoetDsl
public open class AnnotationSpecHandlerScope private constructor(
    handler: AnnotationSpecHandler,
) : AnnotationSpecHandler by handler {
    public companion object {
        public fun of(handler: AnnotationSpecHandler): AnnotationSpecHandlerScope =
            AnnotationSpecHandlerScope(handler)
    }

    /** @see annotation */
    public operator fun ClassName.invoke(
        configuration: AnnotationSpecBuilder.() -> Unit,
    ): AnnotationSpec = buildAnnotationSpec(this, configuration).also(::annotation)

    /** @see annotation */
    public operator fun ParameterizedTypeName.invoke(
        configuration: AnnotationSpecBuilder.() -> Unit,
    ): AnnotationSpec = buildAnnotationSpec(this, configuration).also(::annotation)

    /** @see annotation */
    @OptIn(DelicateKotlinPoetApi::class)
    public operator fun Class<*>.invoke(
        configuration: AnnotationSpecBuilder.() -> Unit,
    ): AnnotationSpec = buildAnnotationSpec(name2, configuration).also(::annotation)

    /** @see annotation */
    public operator fun KClass<*>.invoke(
        configuration: AnnotationSpecBuilder.() -> Unit,
    ): AnnotationSpec = buildAnnotationSpec(name, configuration).also(::annotation)
}

/** Wrapper of [AnnotationSpec.Builder], providing DSL support as a replacement to Java builder. */
@KotlinpoetDsl
public class AnnotationSpecBuilder(
    private val nativeBuilder: AnnotationSpec.Builder,
) {
    public val members: MutableList<CodeBlock> get() = nativeBuilder.members
    public val tags: MutableMap<KClass<*>, Any> get() = nativeBuilder.tags

    public fun member(format: String, vararg args: Any) {
        nativeBuilder.addMember(format, *args)
    }

    public fun member(code: CodeBlock) {
        nativeBuilder.addMember(code)
    }

    public var useSiteTarget: AnnotationSpec.UseSiteTarget?
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
        get() = noGetter()
        set(value) {
            nativeBuilder.useSiteTarget(value)
        }

    public fun build(): AnnotationSpec = nativeBuilder.build()
}
