package com.hendraanggrian.kotlinpoet

import com.hendraanggrian.kotlinpoet.dsl.AnnotationSpecContainer
import com.hendraanggrian.kotlinpoet.dsl.KdocContainer
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.TypeName
import java.lang.reflect.Type
import javax.lang.model.element.VariableElement
import kotlin.reflect.KClass

/** Converts element to [ParameterSpec]. */
fun VariableElement.toParameter(): ParameterSpec =
    ParameterSpec.get(this)

/** Builds a new [ParameterSpec] from [type]. */
fun buildParameter(name: String, type: TypeName, vararg modifiers: KModifier): ParameterSpec =
    ParameterSpec.builder(name, type, *modifiers).build()

/**
 * Builds a new [ParameterSpec] from [type],
 * by populating newly created [ParameterSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildParameter(
    name: String,
    type: TypeName,
    vararg modifiers: KModifier,
    builderAction: ParameterSpecBuilder.() -> Unit
): ParameterSpec = ParameterSpecBuilder(ParameterSpec.builder(name, type, *modifiers)).apply(builderAction).build()

/** Builds a new [ParameterSpec] from [type]. */
fun buildParameter(name: String, type: Type, vararg modifiers: KModifier): ParameterSpec =
    ParameterSpec.builder(name, type, *modifiers).build()

/** Builds a new [ParameterSpec] from [type]. */
fun buildParameter(name: String, type: KClass<*>, vararg modifiers: KModifier): ParameterSpec =
    ParameterSpec.builder(name, type, *modifiers).build()

/** Builds a new [ParameterSpec] from [T]. */
inline fun <reified T> buildParameter(name: String, vararg modifiers: KModifier): ParameterSpec =
    buildParameter(name, T::class, *modifiers)

/**
 * Builds a new [ParameterSpec] from [type],
 * by populating newly created [ParameterSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildParameter(
    name: String,
    type: Type,
    vararg modifiers: KModifier,
    builderAction: ParameterSpecBuilder.() -> Unit
): ParameterSpec = ParameterSpecBuilder(ParameterSpec.builder(name, type, *modifiers)).apply(builderAction).build()

/**
 * Builds a new [ParameterSpec] from [type],
 * by populating newly created [ParameterSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildParameter(
    name: String,
    type: KClass<*>,
    vararg modifiers: KModifier,
    builderAction: ParameterSpecBuilder.() -> Unit
): ParameterSpec = ParameterSpecBuilder(ParameterSpec.builder(name, type, *modifiers)).apply(builderAction).build()

/**
 * Builds a new [ParameterSpec] from [T],
 * by populating newly created [ParameterSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun <reified T> buildParameter(
    name: String,
    vararg modifiers: KModifier,
    builderAction: ParameterSpecBuilder.() -> Unit
): ParameterSpec = buildParameter(name, T::class, *modifiers, builderAction = builderAction)

/** Wrapper of [ParameterSpec.Builder], providing DSL support as a replacement to Java builder. */
@KotlinpoetDslMarker
class ParameterSpecBuilder @PublishedApi internal constructor(private val nativeBuilder: ParameterSpec.Builder) {

    /** Kdoc of this builder. */
    val kdocBuilder: CodeBlock.Builder
        get() = nativeBuilder.kdoc

    /** Annotations of this builder. */
    val annotationSpecs: MutableList<AnnotationSpec>
        get() = nativeBuilder.annotations

    /** Modifiers of this builder. */
    val modifiers: MutableList<KModifier>
        get() = nativeBuilder.modifiers

    /** Tags variables of this builder. */
    val tags: MutableMap<KClass<*>, *>
        get() = nativeBuilder.tags

    /** Collection of kdoc, may be configured with Kotlin DSL. */
    val kdoc: KdocContainer = object : KdocContainer() {
        override fun append(format: String, vararg args: Any) {
            nativeBuilder.addKdoc(format, *args)
        }

        override fun append(code: CodeBlock) {
            nativeBuilder.addKdoc(code)
        }
    }

    /** Collection of annotations, may be configured with Kotlin DSL. */
    val annotations: AnnotationSpecContainer = object : AnnotationSpecContainer() {
        override fun add(spec: AnnotationSpec) {
            nativeBuilder.addAnnotation(spec)
        }
    }

    /** Add field modifiers. */
    fun addModifiers(vararg modifiers: KModifier) {
        nativeBuilder.addModifiers(*modifiers)
    }

    /** Set default value like [String.format]. */
    fun defaultValue(format: String, vararg args: Any) {
        nativeBuilder.defaultValue(format, *args)
    }

    /** Set default value to simple string. */
    var defaultValue: CodeBlock
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            nativeBuilder.defaultValue(value)
        }

    /** Set default value to code with custom initialization [builderAction]. */
    inline fun defaultValue(builderAction: CodeBlockBlockBuilder.() -> Unit): CodeBlock =
        buildCode(builderAction).also { defaultValue = it }

    /** Returns native spec. */
    fun build(): ParameterSpec =
        nativeBuilder.build()
}
