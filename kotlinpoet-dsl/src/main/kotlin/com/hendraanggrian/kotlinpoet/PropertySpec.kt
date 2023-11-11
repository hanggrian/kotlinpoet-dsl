@file:OptIn(ExperimentalContracts::class)

package com.hendraanggrian.kotlinpoet

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.DelicateKotlinPoetApi
import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeVariableName
import java.lang.reflect.Type
import javax.lang.model.element.Element
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.reflect.KClass

/**
 * Creates new [PropertySpec] by populating newly created [PropertySpecBuilder] using provided
 * [configuration].
 */
inline fun buildPropertySpec(
    name: String,
    type: TypeName,
    vararg modifiers: KModifier,
    configuration: PropertySpecBuilder.() -> Unit,
): PropertySpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return PropertySpecBuilder(PropertySpec.builder(name, type, *modifiers)).apply(configuration)
        .build()
}

/**
 * Inserts new [PropertySpec] by populating newly created [PropertySpecBuilder] using provided
 * [configuration].
 */
inline fun PropertySpecHandler.property(
    name: String,
    type: TypeName,
    vararg modifiers: KModifier,
    configuration: PropertySpecBuilder.() -> Unit,
): PropertySpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return buildPropertySpec(name, type, *modifiers, configuration = configuration)
        .also(::property)
}

/**
 * Inserts [PropertySpec] by populating newly created [PropertySpecBuilder] using provided
 * [configuration].
 */
inline fun PropertySpecHandler.property(
    name: String,
    type: Class<*>,
    vararg modifiers: KModifier,
    configuration: PropertySpecBuilder.() -> Unit,
): PropertySpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return buildPropertySpec(name, type.name2, *modifiers, configuration = configuration)
        .also(::property)
}

/**
 * Inserts [PropertySpec] by populating newly created [PropertySpecBuilder] using provided
 * [configuration].
 */
inline fun PropertySpecHandler.property(
    name: String,
    type: KClass<*>,
    vararg modifiers: KModifier,
    configuration: PropertySpecBuilder.() -> Unit,
): PropertySpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return buildPropertySpec(name, type.name, *modifiers, configuration = configuration)
        .also(::property)
}

/**
 * Property delegate for inserting new [PropertySpec] by populating newly created
 * [PropertySpecBuilder] using provided [configuration].
 */
fun PropertySpecHandler.propertying(
    type: TypeName,
    vararg modifiers: KModifier,
    configuration: PropertySpecBuilder.() -> Unit,
): SpecDelegateProvider<PropertySpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider {
        buildPropertySpec(it, type, *modifiers, configuration = configuration).also(::property)
    }
}

/**
 * Property delegate for inserting new [PropertySpec] by populating newly created
 * [PropertySpecBuilder] using provided [configuration].
 */
fun PropertySpecHandler.propertying(
    type: Class<*>,
    vararg modifiers: KModifier,
    configuration: PropertySpecBuilder.() -> Unit,
): SpecDelegateProvider<PropertySpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider {
        buildPropertySpec(it, type.name2, *modifiers, configuration = configuration)
            .also(::property)
    }
}

/**
 * Property delegate for inserting new [PropertySpec] by populating newly created
 * [PropertySpecBuilder] using provided [configuration].
 */
fun PropertySpecHandler.propertying(
    type: KClass<*>,
    vararg modifiers: KModifier,
    configuration: PropertySpecBuilder.() -> Unit,
): SpecDelegateProvider<PropertySpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider {
        buildPropertySpec(it, type.name, *modifiers, configuration = configuration)
            .also(::property)
    }
}

/** Convenient method to insert [PropertySpec] using reified type. */
inline fun <reified T> PropertySpecHandler.property(
    name: String,
    vararg modifiers: KModifier,
): PropertySpec =
    PropertySpecBuilder(PropertySpec.builder(name, T::class, *modifiers))
        .build()
        .also(::property)

/** Invokes DSL to configure [PropertySpec] collection. */
fun PropertySpecHandler.properties(configuration: PropertySpecHandlerScope.() -> Unit) {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    PropertySpecHandlerScope(this).configuration()
}

/** Responsible for managing a set of [PropertySpec] instances. */
sealed interface PropertySpecHandler {
    fun property(property: PropertySpec)

    fun property(name: String, type: TypeName, vararg modifiers: KModifier): PropertySpec =
        PropertySpec.builder(name, type, *modifiers).build().also(::property)

    fun property(name: String, type: Type, vararg modifiers: KModifier): PropertySpec =
        PropertySpec.builder(name, type, *modifiers).build().also(::property)

    fun property(name: String, type: KClass<*>, vararg modifiers: KModifier): PropertySpec =
        PropertySpec.builder(name, type, *modifiers).build().also(::property)

    fun propertying(
        type: TypeName,
        vararg modifiers: KModifier,
    ): SpecDelegateProvider<PropertySpec> =
        SpecDelegateProvider {
            PropertySpec.builder(it, type, *modifiers).build().also(::property)
        }

    fun propertying(type: Type, vararg modifiers: KModifier): SpecDelegateProvider<PropertySpec> =
        SpecDelegateProvider {
            PropertySpec.builder(it, type, *modifiers).build().also(::property)
        }

    fun propertying(
        type: KClass<*>,
        vararg modifiers: KModifier,
    ): SpecDelegateProvider<PropertySpec> =
        SpecDelegateProvider {
            PropertySpec.builder(it, type, *modifiers).build().also(::property)
        }
}

/**
 * Receiver for the `properties` block providing an extended set of operators for the configuration.
 */
@KotlinpoetDsl
class PropertySpecHandlerScope internal constructor(
    handler: PropertySpecHandler,
) : PropertySpecHandler by handler {
    /** @see property */
    operator fun String.invoke(
        type: TypeName,
        vararg modifiers: KModifier,
        configuration: PropertySpecBuilder.() -> Unit,
    ): PropertySpec =
        buildPropertySpec(this, type, *modifiers, configuration = configuration).also(::property)

    /** @see property */
    operator fun String.invoke(
        type: Class<*>,
        vararg modifiers: KModifier,
        configuration: PropertySpecBuilder.() -> Unit,
    ): PropertySpec =
        buildPropertySpec(this, type.name2, *modifiers, configuration = configuration)
            .also(::property)

    /** @see property */
    operator fun String.invoke(
        type: KClass<*>,
        vararg modifiers: KModifier,
        configuration: PropertySpecBuilder.() -> Unit,
    ): PropertySpec =
        buildPropertySpec(this, type.name, *modifiers, configuration = configuration)
            .also(::property)
}

/** Wrapper of [PropertySpec.Builder], providing DSL support as a replacement to Java builder. */
@KotlinpoetDsl
class PropertySpecBuilder(
    private val nativeBuilder: PropertySpec.Builder,
) : AnnotationSpecHandler {
    val modifiers: MutableList<KModifier> get() = nativeBuilder.modifiers
    val typeVariables: MutableList<TypeVariableName> get() = nativeBuilder.typeVariables
    val tags: MutableMap<KClass<*>, *> get() = nativeBuilder.tags
    val kdoc: CodeBlock.Builder get() = nativeBuilder.kdoc
    val originatingElements: MutableList<Element> get() = nativeBuilder.originatingElements
    val annotations: MutableList<AnnotationSpec> get() = nativeBuilder.annotations

    @OptIn(ExperimentalKotlinPoetApi::class)
    val contextReceiverTypes: MutableList<TypeName> get() = nativeBuilder.contextReceiverTypes

    var isMutable: Boolean
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
        get() = noGetter()
        set(value) {
            nativeBuilder.mutable(value)
        }

    fun modifiers(vararg modifiers: KModifier) {
        nativeBuilder.addModifiers(*modifiers)
    }

    fun modifiers(modifiers: Iterable<KModifier>) {
        nativeBuilder.addModifiers(modifiers)
    }

    fun typeVariables(typeVariables: Iterable<TypeVariableName>) {
        nativeBuilder.addTypeVariables(typeVariables)
    }

    fun typeVariable(typeVariable: TypeVariableName) {
        nativeBuilder.addTypeVariable(typeVariable)
    }

    fun initializer(format: String, vararg args: Any) {
        nativeBuilder.initializer(format, *args)
    }

    var initializer: CodeBlock
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
        get() = noGetter()
        set(value) {
            nativeBuilder.initializer(value)
        }

    fun delegate(format: String, vararg args: Any) {
        nativeBuilder.delegate(format, *args)
    }

    var delegate: CodeBlock
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
        get() = noGetter()
        set(value) {
            nativeBuilder.delegate(value)
        }

    var getter: FunSpec
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
        get() = noGetter()
        set(value) {
            nativeBuilder.getter(value)
        }

    var setter: FunSpec
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
        get() = noGetter()
        set(value) {
            nativeBuilder.setter(value)
        }

    var receiver: TypeName
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
        get() = noGetter()
        set(value) {
            nativeBuilder.receiver(value)
        }

    @DelicateKotlinPoetApi(DELICATE_API)
    fun receiver(type: Type) {
        nativeBuilder.receiver(type)
    }

    fun receiver(type: KClass<*>) {
        nativeBuilder.receiver(type)
    }

    inline fun <reified T> receiver(): Unit = receiver(T::class)

    override fun annotation(annotation: AnnotationSpec) {
        nativeBuilder.addAnnotation(annotation)
    }

    fun kdoc(format: String, vararg args: Any) {
        nativeBuilder.addKdoc(format, *args)
    }

    fun kdoc(block: CodeBlock) {
        nativeBuilder.addKdoc(block)
    }

    fun build(): PropertySpec = nativeBuilder.build()
}
