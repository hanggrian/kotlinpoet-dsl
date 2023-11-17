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
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.TypeVariableName
import java.lang.reflect.Type
import javax.lang.model.element.Element
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.reflect.KClass

/**
 * Creates new class [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public inline fun buildClassTypeSpec(
    type: String,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.classBuilder(type)).apply(configuration).build()
}

/**
 * Creates new expect class [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public inline fun buildExpectClassTypeSpec(
    type: String,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.expectClassBuilder(type)).apply(configuration).build()
}

/**
 * Creates new object [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public inline fun buildObjectTypeSpec(
    type: String,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.objectBuilder(type)).apply(configuration).build()
}

/**
 * Creates new object [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public inline fun buildCompanionObjectTypeSpec(
    type: String? = null,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.companionObjectBuilder(type)).apply(configuration).build()
}

/**
 * Creates new interface [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public inline fun buildInterfaceTypeSpec(
    type: String,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.interfaceBuilder(type)).apply(configuration).build()
}

/**
 * Creates new enum [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public inline fun buildEnumTypeSpec(
    type: String,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.enumBuilder(type)).apply(configuration).build()
}

/**
 * Creates new anonymous [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public inline fun buildAnonymousTypeSpec(configuration: TypeSpecBuilder.() -> Unit): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.anonymousClassBuilder()).apply(configuration).build()
}

/**
 * Creates new annotation [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public inline fun buildAnnotationTypeSpec(
    type: String,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.annotationBuilder(type)).apply(configuration).build()
}

/**
 * Inserts new class [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public inline fun TypeSpecHandler.classType(
    type: String,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return buildClassTypeSpec(type, configuration).also(::type)
}

/**
 * Inserts new expect class [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public inline fun TypeSpecHandler.expectClassType(
    type: String,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return buildExpectClassTypeSpec(type, configuration).also(::type)
}

/**
 * Inserts new object [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public inline fun TypeSpecHandler.objectType(
    type: String,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return buildObjectTypeSpec(type, configuration).also(::type)
}

/**
 * Inserts new object [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public inline fun TypeSpecHandler.companionObjectType(
    type: String? = null,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return buildCompanionObjectTypeSpec(type, configuration).also(::type)
}

/**
 * Inserts new interface [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public inline fun TypeSpecHandler.interfaceType(
    type: String,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return buildInterfaceTypeSpec(type, configuration).also(::type)
}

/**
 * Inserts new enum [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public inline fun TypeSpecHandler.enumType(
    type: String,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return buildEnumTypeSpec(type, configuration).also(::type)
}

/**
 * Inserts new anonymous [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public inline fun TypeSpecHandler.anonymousType(
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return buildAnonymousTypeSpec(configuration).also(::type)
}

/**
 * Inserts new annotation [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public inline fun TypeSpecHandler.annotationType(
    type: String,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return buildAnnotationTypeSpec(type, configuration).also(::type)
}

/**
 * Property delegate for inserting new class [TypeSpec] by populating newly created
 * [TypeSpecBuilder] using provided [configuration].
 */
public fun TypeSpecHandler.classTyping(
    configuration: TypeSpecBuilder.() -> Unit,
): SpecDelegateProvider<TypeSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider { buildClassTypeSpec(it, configuration).also(::type) }
}

/**
 * Property delegate for inserting new class [TypeSpec] by populating newly created
 * [TypeSpecBuilder] using provided [configuration].
 */
public fun TypeSpecHandler.expectClassTyping(
    configuration: TypeSpecBuilder.() -> Unit,
): SpecDelegateProvider<TypeSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider { buildExpectClassTypeSpec(it, configuration).also(::type) }
}

/**
 * Property delegate for inserting new class [TypeSpec] by populating newly created
 * [TypeSpecBuilder] using provided [configuration].
 */
public fun TypeSpecHandler.objectTyping(
    configuration: TypeSpecBuilder.() -> Unit,
): SpecDelegateProvider<TypeSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider { buildObjectTypeSpec(it, configuration).also(::type) }
}

/**
 * Property delegate for inserting new class [TypeSpec] by populating newly created
 * [TypeSpecBuilder] using provided [configuration].
 */
public fun TypeSpecHandler.companionObjectTyping(
    configuration: TypeSpecBuilder.() -> Unit,
): SpecDelegateProvider<TypeSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider { buildCompanionObjectTypeSpec(it, configuration).also(::type) }
}

/**
 * Property delegate for inserting new class [TypeSpec] by populating newly created
 * [TypeSpecBuilder] using provided [configuration].
 */
public fun TypeSpecHandler.interfaceTyping(
    configuration: TypeSpecBuilder.() -> Unit,
): SpecDelegateProvider<TypeSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider { buildInterfaceTypeSpec(it, configuration).also(::type) }
}

/**
 * Property delegate for inserting new class [TypeSpec] by populating newly created
 * [TypeSpecBuilder] using provided [configuration].
 */
public fun TypeSpecHandler.enumTyping(
    configuration: TypeSpecBuilder.() -> Unit,
): SpecDelegateProvider<TypeSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider { buildEnumTypeSpec(it, configuration).also(::type) }
}

/**
 * Property delegate for inserting new class [TypeSpec] by populating newly created
 * [TypeSpecBuilder] using provided [configuration].
 */
public fun TypeSpecHandler.annotationTyping(
    configuration: TypeSpecBuilder.() -> Unit,
): SpecDelegateProvider<TypeSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider { buildAnnotationTypeSpec(it, configuration).also(::type) }
}

/** Invokes DSL to configure [TypeSpec] collection. */
public fun TypeSpecHandler.types(configuration: TypeSpecHandlerScope.() -> Unit) {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    TypeSpecHandlerScope.of(this).configuration()
}

/** Responsible for managing a set of [TypeSpec] instances. */
public interface TypeSpecHandler {
    public fun type(type: TypeSpec)

    public fun classType(name: String): TypeSpec = TypeSpec.classBuilder(name).build().also(::type)

    public fun expectClassType(type: String): TypeSpec =
        TypeSpecBuilder(TypeSpec.expectClassBuilder(type)).build().also(::type)

    public fun objectType(type: String): TypeSpec =
        TypeSpecBuilder(TypeSpec.objectBuilder(type)).build().also(::type)

    public fun companionObjectType(type: String? = null): TypeSpec =
        TypeSpecBuilder(TypeSpec.companionObjectBuilder(type)).build().also(::type)

    public fun interfaceType(type: String): TypeSpec =
        TypeSpecBuilder(TypeSpec.interfaceBuilder(type)).build().also(::type)

    public fun enumType(type: String): TypeSpec =
        TypeSpecBuilder(TypeSpec.enumBuilder(type)).build().also(::type)

    public fun anonymousType(): TypeSpec =
        TypeSpecBuilder(TypeSpec.anonymousClassBuilder()).build().also(::type)

    public fun annotationType(type: String): TypeSpec =
        TypeSpecBuilder(TypeSpec.annotationBuilder(type)).build().also(::type)

    public fun classTyping(): SpecDelegateProvider<TypeSpec> =
        SpecDelegateProvider { TypeSpec.classBuilder(it).build().also(::type) }

    public fun expectClassTyping(): SpecDelegateProvider<TypeSpec> =
        SpecDelegateProvider {
            TypeSpecBuilder(TypeSpec.expectClassBuilder(it)).build().also(::type)
        }

    public fun objectTyping(): SpecDelegateProvider<TypeSpec> =
        SpecDelegateProvider {
            TypeSpecBuilder(TypeSpec.objectBuilder(it)).build().also(::type)
        }

    public fun companionObjectTyping(): SpecDelegateProvider<TypeSpec> =
        SpecDelegateProvider {
            TypeSpecBuilder(TypeSpec.companionObjectBuilder(it)).build().also(::type)
        }

    public fun interfaceTyping(): SpecDelegateProvider<TypeSpec> =
        SpecDelegateProvider {
            TypeSpecBuilder(TypeSpec.interfaceBuilder(it)).build().also(::type)
        }

    public fun enumTyping(): SpecDelegateProvider<TypeSpec> =
        SpecDelegateProvider {
            TypeSpecBuilder(TypeSpec.enumBuilder(it)).build().also(::type)
        }

    public fun annotationTyping(): SpecDelegateProvider<TypeSpec> =
        SpecDelegateProvider {
            TypeSpecBuilder(TypeSpec.annotationBuilder(it)).build().also(::type)
        }
}

/** Receiver for the `types` block providing an extended set of operators for the configuration. */
@KotlinpoetDsl
public open class TypeSpecHandlerScope private constructor(
    handler: TypeSpecHandler,
) : TypeSpecHandler by handler {
    public companion object {
        public fun of(handler: TypeSpecHandler): TypeSpecHandlerScope =
            TypeSpecHandlerScope(handler)
    }

    /** @see classType */
    public operator fun String.invoke(configuration: TypeSpecBuilder.() -> Unit): TypeSpec =
        buildClassTypeSpec(this, configuration).also(::type)
}

/** Wrapper of [TypeSpec.Builder], providing DSL support as a replacement to Java builder. */
@KotlinpoetDsl
public class TypeSpecBuilder(
    private val nativeBuilder: TypeSpec.Builder,
) : AnnotationSpecHandler, PropertySpecHandler, FunSpecHandler, TypeSpecHandler {
    public var initializerIndex: Int
        get() = nativeBuilder.initializerIndex
        set(value) {
            nativeBuilder.initializerIndex = value
        }

    public val tags: MutableMap<KClass<*>, *> get() = nativeBuilder.tags
    public val kdoc: CodeBlock.Builder get() = nativeBuilder.kdoc
    public val originatingElements: MutableList<Element> get() = nativeBuilder.originatingElements
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

    public fun modifiers(modifiers: Iterable<KModifier>) {
        nativeBuilder.addModifiers(modifiers)
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
