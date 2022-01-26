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
inline fun VariableElement.asParameterSpec(): ParameterSpec = ParameterSpec.get(this)

/**
 * Builds new [ParameterSpec] from [TypeName],
 * by populating newly created [ParameterSpecBuilder] using provided [configuration].
 */
fun buildParameterSpec(
    name: String,
    type: TypeName,
    vararg modifiers: KModifier,
    configuration: ParameterSpecBuilder.() -> Unit
): ParameterSpec = ParameterSpecBuilder(ParameterSpec.builder(name, type, *modifiers)).apply(configuration).build()

/**
 * Builds new [ParameterSpec] from [Type],
 * by populating newly created [ParameterSpecBuilder] using provided [configuration].
 */
fun buildParameterSpec(
    name: String,
    type: Type,
    vararg modifiers: KModifier,
    configuration: ParameterSpecBuilder.() -> Unit
): ParameterSpec = ParameterSpecBuilder(ParameterSpec.builder(name, type, *modifiers)).apply(configuration).build()

/**
 * Builds new [ParameterSpec] from [KClass],
 * by populating newly created [ParameterSpecBuilder] using provided [configuration].
 */
fun buildParameterSpec(
    name: String,
    type: KClass<*>,
    vararg modifiers: KModifier,
    configuration: ParameterSpecBuilder.() -> Unit
): ParameterSpec = ParameterSpecBuilder(ParameterSpec.builder(name, type, *modifiers)).apply(configuration).build()

/**
 * Builds new [ParameterSpec] from [T],
 * by populating newly created [ParameterSpecBuilder] using provided [configuration].
 */
inline fun <reified T> buildParameterSpec(
    name: String,
    vararg modifiers: KModifier,
    noinline configuration: ParameterSpecBuilder.() -> Unit
): ParameterSpec = buildParameterSpec(name, T::class, *modifiers, configuration = configuration)

/** Modify existing [ParameterSpec.Builder] using provided [configuration]. */
fun ParameterSpec.Builder.edit(configuration: ParameterSpecBuilder.() -> Unit): ParameterSpec.Builder =
    ParameterSpecBuilder(this).apply(configuration).nativeBuilder

/**
 * Wrapper of [ParameterSpec.Builder], providing DSL support as a replacement to Java builder.
 * @param nativeBuilder source builder.
 */
@SpecMarker
class ParameterSpecBuilder internal constructor(val nativeBuilder: ParameterSpec.Builder) {

    /** Kdoc of this parameter. */
    val actualKdoc: CodeBlock.Builder get() = nativeBuilder.kdoc

    /** Modifiers of this parameter. */
    val modifiers: MutableList<KModifier> get() = nativeBuilder.modifiers

    /** Tags variables of this parameter. */
    val tags: MutableMap<KClass<*>, *> get() = nativeBuilder.tags

    /** Kdoc of this parameter. */
    val kdoc: KdocContainer = object : KdocContainer {
        override fun append(format: String, vararg args: Any) {
            nativeBuilder.addKdoc(format, *args)
        }

        override fun append(code: CodeBlock) {
            nativeBuilder.addKdoc(code)
        }
    }

    /** Configures kdoc of this parameter. */
    fun kdoc(configuration: KdocContainerScope.() -> Unit): Unit = KdocContainerScope(kdoc).configuration()

    /** Annotations of this parameter. */
    val annotations: AnnotationSpecList = AnnotationSpecList(nativeBuilder.annotations)

    /** Configures annotations of this parameter. */
    fun annotations(configuration: AnnotationSpecListScope.() -> Unit): Unit =
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

    /** Returns native spec. */
    fun build(): ParameterSpec = nativeBuilder.build()
}
