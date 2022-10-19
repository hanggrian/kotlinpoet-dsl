@file:OptIn(ExperimentalContracts::class)

package com.hendraanggrian.kotlinpoet

import com.hendraanggrian.kotlinpoet.collections.AnnotationSpecList
import com.hendraanggrian.kotlinpoet.collections.AnnotationSpecListScope
import com.hendraanggrian.kotlinpoet.collections.KdocContainer
import com.hendraanggrian.kotlinpoet.collections.KdocContainerScope
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.DelicateKotlinPoetApi
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.TypeName
import java.lang.reflect.Type
import javax.lang.model.element.VariableElement
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.reflect.KClass

/** Converts element to [ParameterSpec]. */
@DelicateKotlinPoetApi(DELICATE_ELEMENT)
inline fun VariableElement.asParameterSpec(): ParameterSpec = ParameterSpec.get(this)

/**
 * Builds new [ParameterSpec] from [TypeName], by populating newly created [ParameterSpecBuilder]
 * using provided [configuration].
 */
inline fun buildParameterSpec(
    name: String,
    type: TypeName,
    vararg modifiers: KModifier,
    configuration: ParameterSpecBuilder.() -> Unit
): ParameterSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return ParameterSpecBuilder(ParameterSpec.builder(name, type, *modifiers)).apply(configuration)
        .build()
}

/**
 * Builds new [ParameterSpec] from [Type], by populating newly created [ParameterSpecBuilder] using
 * provided [configuration].
 */
inline fun buildParameterSpec(
    name: String,
    type: Type,
    vararg modifiers: KModifier,
    configuration: ParameterSpecBuilder.() -> Unit
): ParameterSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return ParameterSpecBuilder(ParameterSpec.builder(name, type, *modifiers)).apply(configuration)
        .build()
}

/**
 * Builds new [ParameterSpec] from [KClass], by populating newly created [ParameterSpecBuilder]
 * using provided [configuration].
 */
inline fun buildParameterSpec(
    name: String,
    type: KClass<*>,
    vararg modifiers: KModifier,
    configuration: ParameterSpecBuilder.() -> Unit
): ParameterSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return ParameterSpecBuilder(ParameterSpec.builder(name, type, *modifiers)).apply(configuration)
        .build()
}

/**
 * Builds new [ParameterSpec] from [T], by populating newly created [ParameterSpecBuilder] using
 * provided [configuration].
 */
inline fun <reified T> buildParameterSpec(
    name: String,
    vararg modifiers: KModifier,
    configuration: ParameterSpecBuilder.() -> Unit
): ParameterSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return ParameterSpecBuilder(ParameterSpec.builder(name, T::class, *modifiers))
        .apply(configuration)
        .build()
}

/**
 * Property delegate for building new [ParameterSpec] from [TypeName], by populating newly
 * created [ParameterSpecBuilder] using provided [configuration].
 */
fun buildingParameterSpec(
    type: TypeName,
    vararg modifiers: KModifier,
    configuration: ParameterSpecBuilder.() -> Unit
): SpecLoader<ParameterSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return createSpecLoader {
        buildParameterSpec(it, type, *modifiers, configuration = configuration)
    }
}

/**
 * Property delegate for building new [ParameterSpec] from [Type], by populating newly
 * created [ParameterSpecBuilder] using provided [configuration].
 */
fun buildingParameterSpec(
    type: Type,
    vararg modifiers: KModifier,
    configuration: ParameterSpecBuilder.() -> Unit
): SpecLoader<ParameterSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return createSpecLoader {
        buildParameterSpec(it, type, *modifiers, configuration = configuration)
    }
}

/**
 * Property delegate for building new [ParameterSpec] from [KClass], by populating newly
 * created [ParameterSpecBuilder] using provided [configuration].
 */
fun buildingParameterSpec(
    type: KClass<*>,
    vararg modifiers: KModifier,
    configuration: ParameterSpecBuilder.() -> Unit
): SpecLoader<ParameterSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return createSpecLoader {
        buildParameterSpec(it, type, *modifiers, configuration = configuration)
    }
}

/**
 * Wrapper of [ParameterSpec.Builder], providing DSL support as a replacement to Java builder.
 * @param nativeBuilder source builder.
 */
@KotlinpoetSpecDsl
class ParameterSpecBuilder(private val nativeBuilder: ParameterSpec.Builder) {
    val actualKdoc: CodeBlock.Builder get() = nativeBuilder.kdoc
    val modifiers: MutableList<KModifier> get() = nativeBuilder.modifiers
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
    fun kdoc(configuration: KdocContainerScope.() -> Unit) {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        KdocContainerScope(kdoc).configuration()
    }

    /** Annotations of this parameter. */
    val annotations: AnnotationSpecList = AnnotationSpecList(nativeBuilder.annotations)

    /** Configures annotations of this parameter. */
    fun annotations(configuration: AnnotationSpecListScope.() -> Unit) {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        AnnotationSpecListScope(annotations).configuration()
    }

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
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
        get() = noGetter()
        set(value) {
            nativeBuilder.defaultValue(value)
        }

    /** Returns native spec. */
    fun build(): ParameterSpec = nativeBuilder.build()
}
