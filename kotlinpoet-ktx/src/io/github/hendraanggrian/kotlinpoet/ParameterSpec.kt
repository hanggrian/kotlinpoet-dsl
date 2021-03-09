package io.github.hendraanggrian.kotlinpoet

import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.TypeName
import io.github.hendraanggrian.kotlinpoet.collections.AnnotationSpecList
import io.github.hendraanggrian.kotlinpoet.collections.AnnotationSpecListScope
import io.github.hendraanggrian.kotlinpoet.collections.KdocContainer
import io.github.hendraanggrian.kotlinpoet.collections.KdocContainerScope
import java.lang.reflect.Type
import kotlin.reflect.KClass

/** Builds new [ParameterSpec] from [TypeName]. */
fun parameterSpecOf(name: String, type: TypeName, vararg modifiers: KModifier): ParameterSpec =
    ParameterSpec.builder(name, type, *modifiers).build()

/** Builds new [ParameterSpec] from [Type]. */
fun parameterSpecOf(name: String, type: Type, vararg modifiers: KModifier): ParameterSpec =
    ParameterSpec.builder(name, type, *modifiers).build()

/** Builds new [ParameterSpec] from [KClass]. */
fun parameterSpecOf(name: String, type: KClass<*>, vararg modifiers: KModifier): ParameterSpec =
    ParameterSpec.builder(name, type, *modifiers).build()

/** Builds new [ParameterSpec] from [T]. */
inline fun <reified T> parameterSpecOf(name: String, vararg modifiers: KModifier): ParameterSpec =
    ParameterSpec.builder(name, T::class, *modifiers).build()

/**
 * Builds new [ParameterSpec] from [TypeName],
 * by populating newly created [ParameterSpecBuilder] using provided [builderAction].
 */
inline fun buildParameterSpec(
    name: String,
    type: TypeName,
    vararg modifiers: KModifier,
    builderAction: ParameterSpecBuilder.() -> Unit
): ParameterSpec = ParameterSpecBuilder(ParameterSpec.builder(name, type, *modifiers)).apply(builderAction).build()

/**
 * Builds new [ParameterSpec] from [Type],
 * by populating newly created [ParameterSpecBuilder] using provided [builderAction].
 */
inline fun buildParameterSpec(
    name: String,
    type: Type,
    vararg modifiers: KModifier,
    builderAction: ParameterSpecBuilder.() -> Unit
): ParameterSpec = ParameterSpecBuilder(ParameterSpec.builder(name, type, *modifiers)).apply(builderAction).build()

/**
 * Builds new [ParameterSpec] from [KClass],
 * by populating newly created [ParameterSpecBuilder] using provided [builderAction].
 */
inline fun buildParameterSpec(
    name: String,
    type: KClass<*>,
    vararg modifiers: KModifier,
    builderAction: ParameterSpecBuilder.() -> Unit
): ParameterSpec = ParameterSpecBuilder(ParameterSpec.builder(name, type, *modifiers)).apply(builderAction).build()

/**
 * Builds new [ParameterSpec] from [T],
 * by populating newly created [ParameterSpecBuilder] using provided [builderAction].
 */
inline fun <reified T> buildParameterSpec(
    name: String,
    vararg modifiers: KModifier,
    builderAction: ParameterSpecBuilder.() -> Unit
): ParameterSpec = ParameterSpecBuilder(ParameterSpec.builder(name, T::class, *modifiers)).apply(builderAction).build()

/** Modify existing [ParameterSpec.Builder] using provided [builderAction]. */
inline fun ParameterSpec.Builder.edit(
    builderAction: ParameterSpecBuilder.() -> Unit
): ParameterSpec.Builder = ParameterSpecBuilder(this).apply(builderAction).nativeBuilder

/**
 * Wrapper of [ParameterSpec.Builder], providing DSL support as a replacement to Java builder.
 * @param nativeBuilder source builder.
 */
@SpecDslMarker
class ParameterSpecBuilder(val nativeBuilder: ParameterSpec.Builder) {

    /** Kdoc of this parameter. */
    val kdocCode: CodeBlock.Builder get() = nativeBuilder.kdoc

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
    inline fun kdoc(builderAction: KdocContainerScope.() -> Unit): Unit =
        KdocContainerScope(kdoc).builderAction()

    /** Annotations of this parameter. */
    val annotations: AnnotationSpecList = AnnotationSpecList(nativeBuilder.annotations)

    /** Configures annotations of this parameter. */
    inline fun annotations(builderAction: AnnotationSpecListScope.() -> Unit): Unit =
        AnnotationSpecListScope(annotations).builderAction()

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
    inline fun defaultValue(builderAction: CodeBlockBuilder.() -> Unit) {
        defaultValue = buildCodeBlock(builderAction)
    }

    /** Returns native spec. */
    fun build(): ParameterSpec = nativeBuilder.build()
}
