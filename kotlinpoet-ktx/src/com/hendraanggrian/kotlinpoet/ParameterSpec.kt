package com.hendraanggrian.kotlinpoet

import com.hendraanggrian.kotlinpoet.collections.AnnotationSpecList
import com.hendraanggrian.kotlinpoet.collections.AnnotationSpecListScope
import com.hendraanggrian.kotlinpoet.collections.KdocContainer
import com.hendraanggrian.kotlinpoet.collections.KdocContainerScope
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.TypeName
import java.lang.reflect.Type
import javax.lang.model.element.VariableElement
import kotlin.reflect.KClass

/** Converts element to [ParameterSpec]. */
fun VariableElement.asParameterSpec(): ParameterSpec =
    ParameterSpec.get(this)

/** Builds a new [ParameterSpec] from [type]. */
fun parameterSpecOf(name: String, type: TypeName, vararg modifiers: KModifier): ParameterSpec =
    ParameterSpec.builder(name, type, *modifiers).build()

/**
 * Builds a new [ParameterSpec] from [type],
 * by populating newly created [ParameterSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildParameterSpec(
    name: String,
    type: TypeName,
    vararg modifiers: KModifier,
    builderAction: ParameterSpecBuilder.() -> Unit
): ParameterSpec = ParameterSpec.builder(name, type, *modifiers).build(builderAction)

/** Builds a new [ParameterSpec] from [type]. */
fun parameterSpecOf(name: String, type: Type, vararg modifiers: KModifier): ParameterSpec =
    ParameterSpec.builder(name, type, *modifiers).build()

/** Builds a new [ParameterSpec] from [type]. */
fun parameterSpecOf(name: String, type: KClass<*>, vararg modifiers: KModifier): ParameterSpec =
    ParameterSpec.builder(name, type, *modifiers).build()

/** Builds a new [ParameterSpec] from [T]. */
inline fun <reified T> parameterSpecOf(name: String, vararg modifiers: KModifier): ParameterSpec =
    parameterSpecOf(name, T::class, *modifiers)

/**
 * Builds a new [ParameterSpec] from [type],
 * by populating newly created [ParameterSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildParameterSpec(
    name: String,
    type: Type,
    vararg modifiers: KModifier,
    builderAction: ParameterSpecBuilder.() -> Unit
): ParameterSpec = ParameterSpec.builder(name, type, *modifiers).build(builderAction)

/**
 * Builds a new [ParameterSpec] from [type],
 * by populating newly created [ParameterSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildParameterSpec(
    name: String,
    type: KClass<*>,
    vararg modifiers: KModifier,
    builderAction: ParameterSpecBuilder.() -> Unit
): ParameterSpec = ParameterSpec.builder(name, type, *modifiers).build(builderAction)

/**
 * Builds a new [ParameterSpec] from [T],
 * by populating newly created [ParameterSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun <reified T> buildParameterSpec(
    name: String,
    vararg modifiers: KModifier,
    builderAction: ParameterSpecBuilder.() -> Unit
): ParameterSpec = buildParameterSpec(name, T::class, *modifiers, builderAction = builderAction)

/** Modify existing [ParameterSpec.Builder] using provided [builderAction] and then building it. */
inline fun ParameterSpec.Builder.build(builderAction: ParameterSpecBuilder.() -> Unit): ParameterSpec =
    ParameterSpecBuilder(this).apply(builderAction).build()

/** Wrapper of [ParameterSpec.Builder], providing DSL support as a replacement to Java builder. */
@KotlinpoetDslMarker
class ParameterSpecBuilder(private val nativeBuilder: ParameterSpec.Builder) {

    /** Kdoc of this parameter. */
    val kdocCodeBlock: CodeBlock.Builder get() = nativeBuilder.kdoc

    /** Modifiers of this parameter. */
    val modifiers: MutableList<KModifier> get() = nativeBuilder.modifiers

    /** Tags variables of this parameter. */
    val tags: MutableMap<KClass<*>, *> get() = nativeBuilder.tags

    /** Kdoc of this parameter. */
    val kdoc: KdocContainer = object : KdocContainer() {
        override fun append(format: String, vararg args: Any) {
            nativeBuilder.addKdoc(format, *args)
        }

        override fun append(code: CodeBlock) {
            nativeBuilder.addKdoc(code)
        }
    }

    /** Configures kdoc of this parameter. */
    inline fun kdoc(configuration: KdocContainerScope.() -> Unit) =
        KdocContainerScope(kdoc).configuration()

    /** Annotations of this parameter. */
    val annotations: AnnotationSpecList = AnnotationSpecList(nativeBuilder.annotations)

    /** Configures annotations of this parameter. */
    inline fun annotations(configuration: AnnotationSpecListScope.() -> Unit) =
        AnnotationSpecListScope(annotations).configuration()

    /** Add parameter modifiers. */
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
    inline fun defaultValue(builderAction: CodeBlockBuilder.() -> Unit): CodeBlock =
        buildCodeBlock(builderAction).also { defaultValue = it }

    /** Returns native spec. */
    fun build(): ParameterSpec = nativeBuilder.build()
}
