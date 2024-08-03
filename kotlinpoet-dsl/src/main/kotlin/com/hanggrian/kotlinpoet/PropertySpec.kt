@file:OptIn(ExperimentalContracts::class)

package com.hanggrian.kotlinpoet

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
public fun buildPropertySpec(
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
public fun PropertySpecHandler.property(
    name: String,
    type: TypeName,
    vararg modifiers: KModifier,
    configuration: PropertySpecBuilder.() -> Unit,
): PropertySpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return PropertySpecBuilder(PropertySpec.builder(name, type, *modifiers))
        .apply(configuration)
        .build()
        .also(::property)
}

/**
 * Inserts [PropertySpec] by populating newly created [PropertySpecBuilder] using provided
 * [configuration].
 */
public fun PropertySpecHandler.property(
    name: String,
    type: Class<*>,
    vararg modifiers: KModifier,
    configuration: PropertySpecBuilder.() -> Unit,
): PropertySpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return PropertySpecBuilder(PropertySpec.builder(name, type, *modifiers))
        .apply(configuration)
        .build()
        .also(::property)
}

/**
 * Inserts [PropertySpec] by populating newly created [PropertySpecBuilder] using provided
 * [configuration].
 */
public fun PropertySpecHandler.property(
    name: String,
    type: KClass<*>,
    vararg modifiers: KModifier,
    configuration: PropertySpecBuilder.() -> Unit,
): PropertySpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return PropertySpecBuilder(PropertySpec.builder(name, type, *modifiers))
        .apply(configuration)
        .build()
        .also(::property)
}

/**
 * Property delegate for inserting new [PropertySpec] by populating newly created
 * [PropertySpecBuilder] using provided [configuration].
 */
public fun PropertySpecHandler.propertying(
    type: TypeName,
    vararg modifiers: KModifier,
    configuration: PropertySpecBuilder.() -> Unit,
): SpecDelegateProvider<PropertySpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider {
        PropertySpecBuilder(PropertySpec.builder(it, type, *modifiers))
            .apply(configuration)
            .build()
            .also(::property)
    }
}

/**
 * Property delegate for inserting new [PropertySpec] by populating newly created
 * [PropertySpecBuilder] using provided [configuration].
 */
public fun PropertySpecHandler.propertying(
    type: Class<*>,
    vararg modifiers: KModifier,
    configuration: PropertySpecBuilder.() -> Unit,
): SpecDelegateProvider<PropertySpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider {
        PropertySpecBuilder(PropertySpec.builder(it, type, *modifiers))
            .apply(configuration)
            .build()
            .also(::property)
    }
}

/**
 * Property delegate for inserting new [PropertySpec] by populating newly created
 * [PropertySpecBuilder] using provided [configuration].
 */
public fun PropertySpecHandler.propertying(
    type: KClass<*>,
    vararg modifiers: KModifier,
    configuration: PropertySpecBuilder.() -> Unit,
): SpecDelegateProvider<PropertySpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider {
        PropertySpecBuilder(PropertySpec.builder(it, type, *modifiers))
            .apply(configuration)
            .build()
            .also(::property)
    }
}

/** Convenient method to insert [PropertySpec] using reified type. */
public inline fun <reified T> PropertySpecHandler.property(
    name: String,
    vararg modifiers: KModifier,
): PropertySpec =
    PropertySpec
        .builder(name, T::class, *modifiers)
        .build()
        .also(::property)

/** Invokes DSL to configure [PropertySpec] collection. */
public fun PropertySpecHandler.properties(configuration: PropertySpecHandlerScope.() -> Unit) {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    PropertySpecHandlerScope
        .of(this)
        .configuration()
}

/** Responsible for managing a set of [PropertySpec] instances. */
public interface PropertySpecHandler {
    public fun property(property: PropertySpec)

    public fun property(name: String, type: TypeName, vararg modifiers: KModifier): PropertySpec =
        propertySpecOf(name, type, *modifiers).also(::property)

    @OptIn(DelicateKotlinPoetApi::class)
    public fun property(name: String, type: Class<*>, vararg modifiers: KModifier): PropertySpec =
        propertySpecOf(name, type.name2, *modifiers).also(::property)

    public fun property(name: String, type: KClass<*>, vararg modifiers: KModifier): PropertySpec =
        propertySpecOf(name, type.name, *modifiers).also(::property)

    public fun propertying(
        type: TypeName,
        vararg modifiers: KModifier,
    ): SpecDelegateProvider<PropertySpec> =
        SpecDelegateProvider { propertySpecOf(it, type, *modifiers).also(::property) }

    @OptIn(DelicateKotlinPoetApi::class)
    public fun propertying(
        type: Class<*>,
        vararg modifiers: KModifier,
    ): SpecDelegateProvider<PropertySpec> =
        SpecDelegateProvider { propertySpecOf(it, type.name2, *modifiers).also(::property) }

    public fun propertying(
        type: KClass<*>,
        vararg modifiers: KModifier,
    ): SpecDelegateProvider<PropertySpec> =
        SpecDelegateProvider { propertySpecOf(it, type.name, *modifiers).also(::property) }
}

/**
 * Receiver for the `properties` block providing an extended set of operators for the configuration.
 */
@KotlinpoetDsl
public open class PropertySpecHandlerScope private constructor(handler: PropertySpecHandler) :
    PropertySpecHandler by handler {
        /**
         * @see property
         */
        public operator fun String.invoke(
            type: TypeName,
            vararg modifiers: KModifier,
            configuration: PropertySpecBuilder.() -> Unit,
        ): PropertySpec = property(this, type, *modifiers, configuration = configuration)

        /**
         * @see property
         */
        @OptIn(DelicateKotlinPoetApi::class)
        public operator fun String.invoke(
            type: Class<*>,
            vararg modifiers: KModifier,
            configuration: PropertySpecBuilder.() -> Unit,
        ): PropertySpec = property(this, type.name2, *modifiers, configuration = configuration)

        /**
         * @see property
         */
        public operator fun String.invoke(
            type: KClass<*>,
            vararg modifiers: KModifier,
            configuration: PropertySpecBuilder.() -> Unit,
        ): PropertySpec = property(this, type.name, *modifiers, configuration = configuration)

        public companion object {
            public fun of(handler: PropertySpecHandler): PropertySpecHandlerScope =
                PropertySpecHandlerScope(handler)
        }
    }

/** Wrapper of [PropertySpec.Builder], providing DSL support as a replacement to Java builder. */
@KotlinpoetDsl
public class PropertySpecBuilder(private val nativeBuilder: PropertySpec.Builder) :
    AnnotationSpecHandler {
    public val modifiers: MutableList<KModifier> get() = nativeBuilder.modifiers
    public val typeVariables: MutableList<TypeVariableName> get() = nativeBuilder.typeVariables
    public val tags: MutableMap<KClass<*>, *> get() = nativeBuilder.tags
    public val kdoc: CodeBlock.Builder get() = nativeBuilder.kdoc
    public val originatingElements: MutableList<Element> get() = nativeBuilder.originatingElements
    public val annotations: MutableList<AnnotationSpec> get() = nativeBuilder.annotations

    @OptIn(ExperimentalKotlinPoetApi::class)
    public val contextReceiverTypes: MutableList<TypeName>
        get() = nativeBuilder.contextReceiverTypes

    public var isMutable: Boolean
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
        get() = noGetter()
        set(value) {
            nativeBuilder.mutable(value)
        }

    public fun modifiers(vararg modifiers: KModifier) {
        nativeBuilder.addModifiers(*modifiers)
    }

    public fun modifiers(modifiers: Iterable<KModifier>) {
        nativeBuilder.addModifiers(modifiers)
    }

    public fun typeVariables(typeVariables: Iterable<TypeVariableName>) {
        nativeBuilder.addTypeVariables(typeVariables)
    }

    public fun typeVariable(typeVariable: TypeVariableName) {
        nativeBuilder.addTypeVariable(typeVariable)
    }

    public fun initializer(format: String, vararg args: Any) {
        nativeBuilder.initializer(format, *args)
    }

    public var initializer: CodeBlock
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
        get() = noGetter()
        set(value) {
            nativeBuilder.initializer(value)
        }

    public fun delegate(format: String, vararg args: Any) {
        nativeBuilder.delegate(format, *args)
    }

    public var delegate: CodeBlock
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
        get() = noGetter()
        set(value) {
            nativeBuilder.delegate(value)
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

    @DelicateKotlinPoetApi(DELICATE_API)
    public fun receiver(type: Type) {
        nativeBuilder.receiver(type)
    }

    public fun receiver(type: KClass<*>) {
        nativeBuilder.receiver(type)
    }

    public inline fun <reified T> receiver(): Unit = receiver(T::class)

    public override fun annotation(annotation: AnnotationSpec) {
        nativeBuilder.addAnnotation(annotation)
    }

    public fun kdoc(format: String, vararg args: Any) {
        nativeBuilder.addKdoc(format, *args)
    }

    public fun kdoc(block: CodeBlock) {
        nativeBuilder.addKdoc(block)
    }

    public fun build(): PropertySpec = nativeBuilder.build()
}
