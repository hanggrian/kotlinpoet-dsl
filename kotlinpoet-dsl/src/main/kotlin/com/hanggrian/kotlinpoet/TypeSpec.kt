@file:OptIn(ExperimentalContracts::class)

package com.hanggrian.kotlinpoet

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.DelicateKotlinPoetApi
import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.TypeVariableName
import java.lang.reflect.Type
import javax.lang.model.element.Element
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.reflect.KClass

/** Creates new class [TypeSpec] using parameters. */
public inline fun classTypeSpecOf(name: String): TypeSpec =
    TypeSpec
        .classBuilder(name)
        .build()

/** Creates new class [TypeSpec] using parameters. */
public inline fun classTypeSpecOf(name: ClassName): TypeSpec =
    TypeSpec
        .classBuilder(name)
        .build()

/** Creates new object [TypeSpec] using parameters. */
public inline fun objectTypeSpecOf(name: String): TypeSpec =
    TypeSpec
        .objectBuilder(name)
        .build()

/** Creates new object [TypeSpec] using parameters. */
public inline fun objectTypeSpecOf(name: ClassName): TypeSpec =
    TypeSpec
        .objectBuilder(name)
        .build()

/** Creates new companion object [TypeSpec] using parameters. */
public inline fun companionObjectTypeSpecOf(name: String? = null): TypeSpec =
    TypeSpec
        .companionObjectBuilder(name)
        .build()

/** Creates new interface [TypeSpec] using parameters. */
public inline fun interfaceTypeSpecOf(name: String): TypeSpec =
    TypeSpec
        .interfaceBuilder(name)
        .build()

/** Creates new interface [TypeSpec] using parameters. */
public inline fun interfaceTypeSpecOf(name: ClassName): TypeSpec =
    TypeSpec
        .interfaceBuilder(name)
        .build()

/** Creates new enum [TypeSpec] using parameters. */
public inline fun enumTypeSpecOf(name: String): TypeSpec =
    TypeSpec
        .enumBuilder(name)
        .build()

/** Creates new enum [TypeSpec] using parameters. */
public inline fun enumTypeSpecOf(name: ClassName): TypeSpec =
    TypeSpec
        .enumBuilder(name)
        .build()

/** Creates new empty anonymous class [TypeSpec]. */
public inline fun emptyAnonymousClassTypeSpec(): TypeSpec =
    TypeSpec
        .anonymousClassBuilder()
        .build()

/** Creates new annotation [TypeSpec] using parameters. */
public inline fun annotationTypeSpecOf(name: String): TypeSpec =
    TypeSpec
        .annotationBuilder(name)
        .build()

/** Creates new annotation [TypeSpec] using parameters. */
public inline fun annotationTypeSpecOf(name: ClassName): TypeSpec =
    TypeSpec
        .annotationBuilder(name)
        .build()

/**
 * Builds new class [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public fun buildClassTypeSpec(name: String, configuration: TypeSpecBuilder.() -> Unit): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.classBuilder(name))
        .apply(configuration)
        .build()
}

/**
 * Builds new class [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public fun buildClassTypeSpec(
    name: ClassName,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.classBuilder(name))
        .apply(configuration)
        .build()
}

/**
 * Builds new object [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public fun buildObjectTypeSpec(name: String, configuration: TypeSpecBuilder.() -> Unit): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.objectBuilder(name))
        .apply(configuration)
        .build()
}

/**
 * Builds new object [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public fun buildObjectTypeSpec(
    name: ClassName,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.objectBuilder(name))
        .apply(configuration)
        .build()
}

/**
 * Builds new object [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public fun buildCompanionObjectTypeSpec(
    name: String? = null,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.companionObjectBuilder(name))
        .apply(configuration)
        .build()
}

/**
 * Builds new interface [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public fun buildInterfaceTypeSpec(
    name: String,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.interfaceBuilder(name))
        .apply(configuration)
        .build()
}

/**
 * Builds new interface [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public fun buildInterfaceTypeSpec(
    name: ClassName,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.interfaceBuilder(name))
        .apply(configuration)
        .build()
}

/**
 * Builds new enum [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public fun buildEnumTypeSpec(name: String, configuration: TypeSpecBuilder.() -> Unit): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.enumBuilder(name))
        .apply(configuration)
        .build()
}

/**
 * Builds new enum [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public fun buildEnumTypeSpec(name: ClassName, configuration: TypeSpecBuilder.() -> Unit): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.enumBuilder(name))
        .apply(configuration)
        .build()
}

/**
 * Builds new anonymous [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public fun buildAnonymousTypeSpec(configuration: TypeSpecBuilder.() -> Unit): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.anonymousClassBuilder())
        .apply(configuration)
        .build()
}

/**
 * Builds new annotation [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public fun buildAnnotationTypeSpec(
    name: String,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.annotationBuilder(name))
        .apply(configuration)
        .build()
}

/**
 * Builds new annotation [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public fun buildAnnotationTypeSpec(
    name: ClassName,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.annotationBuilder(name))
        .apply(configuration)
        .build()
}

/**
 * Inserts new class [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public fun TypeSpecHandler.classType(
    name: String,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.classBuilder(name))
        .apply(configuration)
        .build()
        .also(::type)
}

/**
 * Inserts new class [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public fun TypeSpecHandler.classType(
    name: ClassName,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.classBuilder(name))
        .apply(configuration)
        .build()
        .also(::type)
}

/**
 * Inserts new object [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public fun TypeSpecHandler.objectType(
    name: String,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.objectBuilder(name))
        .apply(configuration)
        .build()
        .also(::type)
}

/**
 * Inserts new object [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public fun TypeSpecHandler.objectType(
    name: ClassName,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.objectBuilder(name))
        .apply(configuration)
        .build()
        .also(::type)
}

/**
 * Inserts new object [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public fun TypeSpecHandler.companionObjectType(
    name: String? = null,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.companionObjectBuilder(name))
        .apply(configuration)
        .build()
        .also(::type)
}

/**
 * Inserts new interface [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public fun TypeSpecHandler.interfaceType(
    name: String,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.interfaceBuilder(name))
        .apply(configuration)
        .build()
        .also(::type)
}

/**
 * Inserts new interface [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public fun TypeSpecHandler.interfaceType(
    name: ClassName,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.interfaceBuilder(name))
        .apply(configuration)
        .build()
        .also(::type)
}

/**
 * Inserts new enum [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public fun TypeSpecHandler.enumType(
    name: String,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.enumBuilder(name))
        .apply(configuration)
        .build()
        .also(::type)
}

/**
 * Inserts new enum [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public fun TypeSpecHandler.enumType(
    name: ClassName,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.enumBuilder(name))
        .apply(configuration)
        .build()
        .also(::type)
}

/**
 * Inserts new anonymous [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public fun TypeSpecHandler.anonymousType(configuration: TypeSpecBuilder.() -> Unit): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.anonymousClassBuilder())
        .apply(configuration)
        .build()
        .also(::type)
}

/**
 * Inserts new annotation [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public fun TypeSpecHandler.annotationType(
    name: String,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.annotationBuilder(name))
        .apply(configuration)
        .build()
        .also(::type)
}

/**
 * Inserts new annotation [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public fun TypeSpecHandler.annotationType(
    name: ClassName,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.annotationBuilder(name))
        .apply(configuration)
        .build()
        .also(::type)
}

/**
 * Property delegate for inserting new class [TypeSpec] by populating newly created
 * [TypeSpecBuilder] using provided [configuration].
 */
public fun TypeSpecHandler.classTyping(
    configuration: TypeSpecBuilder.() -> Unit,
): SpecDelegateProvider<TypeSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider {
        TypeSpecBuilder(TypeSpec.classBuilder(it))
            .apply(configuration)
            .build()
            .also(::type)
    }
}

/**
 * Property delegate for inserting new class [TypeSpec] by populating newly created
 * [TypeSpecBuilder] using provided [configuration].
 */
public fun TypeSpecHandler.objectTyping(
    configuration: TypeSpecBuilder.() -> Unit,
): SpecDelegateProvider<TypeSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider {
        TypeSpecBuilder(TypeSpec.objectBuilder(it))
            .apply(configuration)
            .build()
            .also(::type)
    }
}

/**
 * Property delegate for inserting new class [TypeSpec] by populating newly created
 * [TypeSpecBuilder] using provided [configuration].
 */
public fun TypeSpecHandler.companionObjectTyping(
    configuration: TypeSpecBuilder.() -> Unit,
): SpecDelegateProvider<TypeSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider {
        TypeSpecBuilder(TypeSpec.companionObjectBuilder(it))
            .apply(configuration)
            .build()
            .also(::type)
    }
}

/**
 * Property delegate for inserting new class [TypeSpec] by populating newly created
 * [TypeSpecBuilder] using provided [configuration].
 */
public fun TypeSpecHandler.interfaceTyping(
    configuration: TypeSpecBuilder.() -> Unit,
): SpecDelegateProvider<TypeSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider {
        TypeSpecBuilder(TypeSpec.interfaceBuilder(it))
            .apply(configuration)
            .build()
            .also(::type)
    }
}

/**
 * Property delegate for inserting new class [TypeSpec] by populating newly created
 * [TypeSpecBuilder] using provided [configuration].
 */
public fun TypeSpecHandler.enumTyping(
    configuration: TypeSpecBuilder.() -> Unit,
): SpecDelegateProvider<TypeSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider {
        TypeSpecBuilder(TypeSpec.enumBuilder(it))
            .apply(configuration)
            .build()
            .also(::type)
    }
}

/**
 * Property delegate for inserting new class [TypeSpec] by populating newly created
 * [TypeSpecBuilder] using provided [configuration].
 */
public fun TypeSpecHandler.annotationTyping(
    configuration: TypeSpecBuilder.() -> Unit,
): SpecDelegateProvider<TypeSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider {
        TypeSpecBuilder(TypeSpec.annotationBuilder(it))
            .apply(configuration)
            .build()
            .also(::type)
    }
}

/** Invokes DSL to configure [TypeSpec] collection. */
public fun TypeSpecHandler.types(configuration: TypeSpecHandlerScope.() -> Unit) {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    TypeSpecHandlerScope
        .of(this)
        .configuration()
}

/** Responsible for managing a set of [TypeSpec] instances. */
public interface TypeSpecHandler {
    public fun type(type: TypeSpec)

    public fun classType(name: String): TypeSpec = classTypeSpecOf(name).also(::type)

    public fun classType(name: ClassName): TypeSpec = classTypeSpecOf(name).also(::type)

    public fun objectType(name: String): TypeSpec = objectTypeSpecOf(name).also(::type)

    public fun objectType(name: ClassName): TypeSpec = objectTypeSpecOf(name).also(::type)

    public fun companionObjectType(name: String? = null): TypeSpec =
        companionObjectTypeSpecOf(name).also(::type)

    public fun interfaceType(name: String): TypeSpec = interfaceTypeSpecOf(name).also(::type)

    public fun interfaceType(name: ClassName): TypeSpec = interfaceTypeSpecOf(name).also(::type)

    public fun enumType(name: String): TypeSpec = enumTypeSpecOf(name).also(::type)

    public fun enumType(name: ClassName): TypeSpec = enumTypeSpecOf(name).also(::type)

    public fun anonymousType(): TypeSpec = emptyAnonymousClassTypeSpec().also(::type)

    public fun annotationType(name: String): TypeSpec = annotationTypeSpecOf(name).also(::type)

    public fun annotationType(name: ClassName): TypeSpec = annotationTypeSpecOf(name).also(::type)

    public fun classTyping(): SpecDelegateProvider<TypeSpec> =
        SpecDelegateProvider { classTypeSpecOf(it).also(::type) }

    public fun objectTyping(): SpecDelegateProvider<TypeSpec> =
        SpecDelegateProvider { objectTypeSpecOf(it).also(::type) }

    public fun companionObjectTyping(): SpecDelegateProvider<TypeSpec> =
        SpecDelegateProvider { companionObjectTypeSpecOf(it).also(::type) }

    public fun interfaceTyping(): SpecDelegateProvider<TypeSpec> =
        SpecDelegateProvider { interfaceTypeSpecOf(it).also(::type) }

    public fun enumTyping(): SpecDelegateProvider<TypeSpec> =
        SpecDelegateProvider { enumTypeSpecOf(it).also(::type) }

    public fun annotationTyping(): SpecDelegateProvider<TypeSpec> =
        SpecDelegateProvider { annotationTypeSpecOf(it).also(::type) }
}

/** Receiver for the `types` block providing an extended set of operators for the configuration. */
@KotlinpoetDsl
public open class TypeSpecHandlerScope private constructor(handler: TypeSpecHandler) :
    TypeSpecHandler by handler {
        /**
         * @see classType
         */
        public operator fun String.invoke(configuration: TypeSpecBuilder.() -> Unit): TypeSpec =
            classType(this, configuration)

        public companion object {
            public fun of(handler: TypeSpecHandler): TypeSpecHandlerScope =
                TypeSpecHandlerScope(handler)
        }
    }

/** Wrapper of [TypeSpec.Builder], providing DSL support as a replacement to Java builder. */
@KotlinpoetDsl
public class TypeSpecBuilder(private val nativeBuilder: TypeSpec.Builder) :
    AnnotationSpecHandler,
    PropertySpecHandler,
    FunSpecHandler,
    TypeSpecHandler {
    public var initializerIndex: Int
        get() = nativeBuilder.initializerIndex
        set(value) {
            nativeBuilder.initializerIndex = value
        }

    public val tags: MutableMap<KClass<*>, *> get() = nativeBuilder.tags
    public val kdoc: CodeBlock.Builder get() = nativeBuilder.kdoc
    public val originatingElements: MutableList<Element>
        get() = nativeBuilder.originatingElements
    public val annotations: MutableList<AnnotationSpec> get() = nativeBuilder.annotations

    @OptIn(ExperimentalKotlinPoetApi::class)
    public val contextReceiverTypes: MutableList<TypeName>
        get() = nativeBuilder.contextReceiverTypes

    public val modifiers: MutableSet<KModifier> get() = nativeBuilder.modifiers

    public val superinterfaces: MutableMap<TypeName, CodeBlock?>
        get() = nativeBuilder.superinterfaces

    public val enumConstants: MutableMap<String, TypeSpec> get() = nativeBuilder.enumConstants
    public val typeVariables: MutableList<TypeVariableName> get() = nativeBuilder.typeVariables

    public val superclassConstructorParameters: MutableList<CodeBlock>
        get() = nativeBuilder.superclassConstructorParameters

    public val properties: MutableList<PropertySpec> get() = nativeBuilder.propertySpecs
    public val functions: MutableList<FunSpec> get() = nativeBuilder.funSpecs
    public val types: MutableList<TypeSpec> get() = nativeBuilder.typeSpecs

    public fun modifiers(vararg modifiers: KModifier) {
        nativeBuilder.addModifiers(*modifiers)
    }

    public fun typeVariables(typeVariables: Iterable<TypeVariableName>) {
        nativeBuilder.addTypeVariables(typeVariables)
    }

    public fun typeVariable(typeVariable: TypeVariableName) {
        nativeBuilder.addTypeVariable(typeVariable)
    }

    public var primaryConstructor: FunSpec?
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
        get() = noGetter()
        set(value) {
            nativeBuilder.primaryConstructor(value)
        }

    public var superclass: TypeName
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
        get() = noGetter()
        set(value) {
            nativeBuilder.superclass(value)
        }

    @DelicateKotlinPoetApi(DELICATE_API)
    public fun superclass(type: Type) {
        nativeBuilder.superclass(type)
    }

    public fun superclass(type: KClass<*>) {
        nativeBuilder.superclass(type)
    }

    public inline fun <reified T> superclass(): Unit = superclass(T::class)

    public fun superclassConstructorParameter(format: String, vararg args: Any) {
        nativeBuilder.addSuperclassConstructorParameter(format, *args)
    }

    public fun superclassConstructorParameter(code: CodeBlock) {
        nativeBuilder.addSuperclassConstructorParameter(code)
    }

    public fun superinterface(
        superinterface: TypeName,
        delegate: CodeBlock = CodeBlock.builder().build(),
    ) {
        nativeBuilder.addSuperinterface(superinterface, delegate)
    }

    @DelicateKotlinPoetApi(DELICATE_API)
    public fun superinterface(
        superinterface: Type,
        delegate: CodeBlock = CodeBlock.builder().build(),
    ) {
        nativeBuilder.addSuperinterface(superinterface, delegate)
    }

    public fun superinterface(
        superinterface: KClass<*>,
        delegate: CodeBlock = CodeBlock.builder().build(),
    ) {
        nativeBuilder.addSuperinterface(superinterface, delegate)
    }

    public fun superinterface(superinterface: TypeName, constructorParameterName: String) {
        nativeBuilder.addSuperinterface(superinterface, constructorParameterName)
    }

    public fun superinterface(superinterface: KClass<*>, constructorParameterName: String) {
        nativeBuilder.addSuperinterface(superinterface, constructorParameterName)
    }

    public inline fun <reified T> superinterface(): Unit = superinterface(T::class)

    public fun enumConstant(
        name: String,
        typeSpec: TypeSpec = TypeSpec.anonymousClassBuilder().build(),
    ) {
        nativeBuilder.addEnumConstant(name, typeSpec)
    }

    public override fun property(property: PropertySpec) {
        nativeBuilder.addProperty(property)
    }

    public fun initializerBlock(code: CodeBlock) {
        nativeBuilder.addInitializerBlock(code)
    }

    public fun initializerBlock(format: String, vararg args: Any) {
        nativeBuilder.addInitializerBlock(codeBlockOf(format, *args))
    }

    public override fun function(function: FunSpec) {
        nativeBuilder.addFunction(function)
    }

    public override fun type(type: TypeSpec) {
        nativeBuilder.addType(type)
    }

    @OptIn(ExperimentalKotlinPoetApi::class)
    public fun contextReceivers(receiverTypes: Iterable<TypeName>) {
        nativeBuilder.contextReceivers(receiverTypes)
    }

    public override fun annotation(annotation: AnnotationSpec) {
        nativeBuilder.addAnnotation(annotation)
    }

    public fun kdoc(format: String, vararg args: Any) {
        nativeBuilder.addKdoc(format, *args)
    }

    public fun kdoc(block: CodeBlock) {
        nativeBuilder.addKdoc(block)
    }

    public fun build(): TypeSpec = nativeBuilder.build()
}
