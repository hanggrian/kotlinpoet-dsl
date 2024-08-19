@file:OptIn(ExperimentalContracts::class)

package com.hanggrian.kotlinpoet

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.CodeBlock
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

/** Creates new [PropertySpec] using parameters. */
public inline fun propertySpecOf(
    name: String,
    type: TypeName,
    vararg modifiers: KModifier,
): PropertySpec =
    PropertySpec
        .builder(name, type, *modifiers)
        .build()

/**
 * Builds new [PropertySpec] by populating newly created [PropertySpecBuilder] using provided
 * [configuration].
 */
public inline fun buildPropertySpec(
    name: String,
    type: TypeName,
    vararg modifiers: KModifier,
    configuration: PropertySpecBuilder.() -> Unit,
): PropertySpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return PropertySpecBuilder(PropertySpec.builder(name, type, *modifiers))
        .apply(configuration)
        .build()
}

/**
 * Inserts new [PropertySpec] by populating newly created [PropertySpecBuilder] using provided
 * [configuration].
 */
public inline fun PropertySpecHandler.add(
    name: String,
    type: TypeName,
    vararg modifiers: KModifier,
    configuration: PropertySpecBuilder.() -> Unit,
): PropertySpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return PropertySpecBuilder(PropertySpec.builder(name, type, *modifiers))
        .apply(configuration)
        .build()
        .also(::add)
}

/**
 * Inserts [PropertySpec] by populating newly created [PropertySpecBuilder] using provided
 * [configuration].
 */
public inline fun PropertySpecHandler.add(
    name: String,
    type: Type,
    vararg modifiers: KModifier,
    configuration: PropertySpecBuilder.() -> Unit,
): PropertySpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return PropertySpecBuilder(PropertySpec.builder(name, type, *modifiers))
        .apply(configuration)
        .build()
        .also(::add)
}

/**
 * Inserts [PropertySpec] by populating newly created [PropertySpecBuilder] using provided
 * [configuration].
 */
public inline fun PropertySpecHandler.add(
    name: String,
    type: KClass<*>,
    vararg modifiers: KModifier,
    configuration: PropertySpecBuilder.() -> Unit,
): PropertySpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return PropertySpecBuilder(PropertySpec.builder(name, type, *modifiers))
        .apply(configuration)
        .build()
        .also(::add)
}

/**
 * Property delegate for inserting new [PropertySpec] by populating newly created
 * [PropertySpecBuilder] using provided [configuration].
 */
public fun PropertySpecHandler.adding(
    type: TypeName,
    vararg modifiers: KModifier,
    configuration: PropertySpecBuilder.() -> Unit,
): SpecDelegateProvider<PropertySpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider {
        PropertySpecBuilder(PropertySpec.builder(it, type, *modifiers))
            .apply(configuration)
            .build()
            .also(::add)
    }
}

/**
 * Property delegate for inserting new [PropertySpec] by populating newly created
 * [PropertySpecBuilder] using provided [configuration].
 */
public fun PropertySpecHandler.adding(
    type: Type,
    vararg modifiers: KModifier,
    configuration: PropertySpecBuilder.() -> Unit,
): SpecDelegateProvider<PropertySpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider {
        PropertySpecBuilder(PropertySpec.builder(it, type, *modifiers))
            .apply(configuration)
            .build()
            .also(::add)
    }
}

/**
 * Property delegate for inserting new [PropertySpec] by populating newly created
 * [PropertySpecBuilder] using provided [configuration].
 */
public fun PropertySpecHandler.adding(
    type: KClass<*>,
    vararg modifiers: KModifier,
    configuration: PropertySpecBuilder.() -> Unit,
): SpecDelegateProvider<PropertySpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider {
        PropertySpecBuilder(PropertySpec.builder(it, type, *modifiers))
            .apply(configuration)
            .build()
            .also(::add)
    }
}

/** Convenient method to insert [PropertySpec] using reified type. */
public inline fun <reified T> PropertySpecHandler.add(
    name: String,
    vararg modifiers: KModifier,
): PropertySpec =
    PropertySpec
        .builder(name, T::class, *modifiers)
        .build()
        .also(::add)

/** Responsible for managing a set of [PropertySpec] instances. */
public interface PropertySpecHandler {
    public fun add(property: PropertySpec)

    public fun add(name: String, type: TypeName, vararg modifiers: KModifier): PropertySpec =
        propertySpecOf(name, type, *modifiers).also(::add)

    public fun add(name: String, type: Type, vararg modifiers: KModifier): PropertySpec =
        propertySpecOf(name, type.name, *modifiers).also(::add)

    public fun add(name: String, type: KClass<*>, vararg modifiers: KModifier): PropertySpec =
        propertySpecOf(name, type.name, *modifiers).also(::add)

    public fun adding(
        type: TypeName,
        vararg modifiers: KModifier,
    ): SpecDelegateProvider<PropertySpec> =
        SpecDelegateProvider { propertySpecOf(it, type, *modifiers).also(::add) }

    public fun adding(
        type: Type,
        vararg modifiers: KModifier,
    ): SpecDelegateProvider<PropertySpec> =
        SpecDelegateProvider { propertySpecOf(it, type.name, *modifiers).also(::add) }

    public fun adding(
        type: KClass<*>,
        vararg modifiers: KModifier,
    ): SpecDelegateProvider<PropertySpec> =
        SpecDelegateProvider { propertySpecOf(it, type.name, *modifiers).also(::add) }
}

/**
 * Receiver for the `properties` block providing an extended set of operators for the configuration.
 */
@KotlinPoetDsl
public open class PropertySpecHandlerScope private constructor(handler: PropertySpecHandler) :
    PropertySpecHandler by handler {
        public inline operator fun String.invoke(
            type: TypeName,
            vararg modifiers: KModifier,
            configuration: PropertySpecBuilder.() -> Unit,
        ): PropertySpec = add(this, type, *modifiers, configuration = configuration)

        public inline operator fun String.invoke(
            type: Type,
            vararg modifiers: KModifier,
            configuration: PropertySpecBuilder.() -> Unit,
        ): PropertySpec = add(this, type.name, *modifiers, configuration = configuration)

        public inline operator fun String.invoke(
            type: KClass<*>,
            vararg modifiers: KModifier,
            configuration: PropertySpecBuilder.() -> Unit,
        ): PropertySpec = add(this, type.name, *modifiers, configuration = configuration)

        public companion object {
            public fun of(handler: PropertySpecHandler): PropertySpecHandlerScope =
                PropertySpecHandlerScope(handler)
        }
    }

/** Wrapper of [PropertySpec.Builder], providing DSL support as a replacement to Java builder. */
@KotlinPoetDsl
public class PropertySpecBuilder(private val nativeBuilder: PropertySpec.Builder) {
    public val annotations: AnnotationSpecHandler =
        object : AnnotationSpecHandler {
            override fun add(annotation: AnnotationSpec) {
                annotationSpecs += annotation
            }
        }

    /** Invokes DSL to configure [AnnotationSpec] collection. */
    public inline fun annotations(configuration: AnnotationSpecHandlerScope.() -> Unit) {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        AnnotationSpecHandlerScope
            .of(annotations)
            .configuration()
    }

    public val modifiers: MutableList<KModifier> get() = nativeBuilder.modifiers
    public val typeVariables: MutableList<TypeVariableName> get() = nativeBuilder.typeVariables
    public val tags: MutableMap<KClass<*>, *> get() = nativeBuilder.tags
    public val kdoc: CodeBlock.Builder get() = nativeBuilder.kdoc
    public val originatingElements: MutableList<Element> get() = nativeBuilder.originatingElements
    public val annotationSpecs: MutableList<AnnotationSpec> get() = nativeBuilder.annotations

    @OptIn(ExperimentalKotlinPoetApi::class)
    public val contextReceiverTypes: MutableList<TypeName>
        get() = nativeBuilder.contextReceiverTypes

    public var isMutable: Boolean
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
        get() = noGetter()
        set(value) {
            nativeBuilder.mutable(value)
        }

    public fun addModifiers(vararg modifiers: KModifier) {
        this.modifiers += modifiers
    }

    public fun addTypeVariables(vararg typeVariables: TypeVariableName) {
        this.typeVariables += typeVariables
    }

    public var initializer: CodeBlock
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
        get() = noGetter()
        set(value) {
            nativeBuilder.initializer(value)
        }

    public fun setInitializer(format: String, vararg args: Any) {
        initializer = codeBlockOf(format, *args)
    }

    public var delegate: CodeBlock
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
        get() = noGetter()
        set(value) {
            nativeBuilder.delegate(value)
        }

    public fun delegate(format: String, vararg args: Any) {
        delegate = codeBlockOf(format, *args)
    }

    public var getter: FunSpec
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
        get() = noGetter()
        set(value) {
            nativeBuilder.getter(value)
        }

    public var setter: FunSpec
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
        get() = noGetter()
        set(value) {
            nativeBuilder.setter(value)
        }

    public var receiver: TypeName
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
        get() = noGetter()
        set(value) {
            nativeBuilder.receiver(value)
        }

    public fun setReceiver(type: Type) {
        receiver = type.name
    }

    public fun setReceiver(type: KClass<*>) {
        receiver = type.name
    }

    public inline fun <reified T> setReceiver() {
        receiver = T::class.name
    }

    public fun addKdoc(format: String, vararg args: Any) {
        nativeBuilder.addKdoc(format, *args)
    }

    public fun addKdoc(block: CodeBlock) {
        nativeBuilder.addKdoc(block)
    }

    public fun build(): PropertySpec = nativeBuilder.build()
}
