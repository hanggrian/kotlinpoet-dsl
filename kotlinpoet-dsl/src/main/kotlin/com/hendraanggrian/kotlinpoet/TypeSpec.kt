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
inline fun buildClassTypeSpec(type: String, configuration: TypeSpecBuilder.() -> Unit): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.classBuilder(type)).apply(configuration).build()
}

/**
 * Creates new expect class [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
inline fun buildExpectClassTypeSpec(
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
inline fun buildObjectTypeSpec(type: String, configuration: TypeSpecBuilder.() -> Unit): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.objectBuilder(type)).apply(configuration).build()
}

/**
 * Creates new object [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
inline fun buildCompanionObjectTypeSpec(
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
inline fun buildInterfaceTypeSpec(
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
inline fun buildEnumTypeSpec(type: String, configuration: TypeSpecBuilder.() -> Unit): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.enumBuilder(type)).apply(configuration).build()
}

/**
 * Creates new anonymous [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
inline fun buildAnonymousTypeSpec(configuration: TypeSpecBuilder.() -> Unit): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.anonymousClassBuilder()).apply(configuration).build()
}

/**
 * Creates new annotation [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
inline fun buildAnnotationTypeSpec(
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
inline fun TypeSpecHandler.classType(
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
inline fun TypeSpecHandler.expectClassType(
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
inline fun TypeSpecHandler.objectType(
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
inline fun TypeSpecHandler.companionObjectType(
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
inline fun TypeSpecHandler.interfaceType(
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
inline fun TypeSpecHandler.enumType(
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
inline fun TypeSpecHandler.anonymousType(configuration: TypeSpecBuilder.() -> Unit): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return buildAnonymousTypeSpec(configuration).also(::type)
}

/**
 * Inserts new annotation [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
inline fun TypeSpecHandler.annotationType(
    type: String,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return buildAnnotationTypeSpec(type, configuration).also(::type)
}

/**
 * Property delegate for inserting new class [TypeSpec] by populating newly created [TypeSpecBuilder]
 * using provided [configuration].
 */
fun TypeSpecHandler.classTyping(
    configuration: TypeSpecBuilder.() -> Unit,
): SpecDelegateProvider<TypeSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider { buildClassTypeSpec(it, configuration).also(::type) }
}

/**
 * Property delegate for inserting new class [TypeSpec] by populating newly created [TypeSpecBuilder]
 * using provided [configuration].
 */
fun TypeSpecHandler.expectClassTyping(
    configuration: TypeSpecBuilder.() -> Unit,
): SpecDelegateProvider<TypeSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider { buildExpectClassTypeSpec(it, configuration).also(::type) }
}

/**
 * Property delegate for inserting new class [TypeSpec] by populating newly created [TypeSpecBuilder]
 * using provided [configuration].
 */
fun TypeSpecHandler.objectTyping(
    configuration: TypeSpecBuilder.() -> Unit,
): SpecDelegateProvider<TypeSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider { buildObjectTypeSpec(it, configuration).also(::type) }
}

/**
 * Property delegate for inserting new class [TypeSpec] by populating newly created [TypeSpecBuilder]
 * using provided [configuration].
 */
fun TypeSpecHandler.companionObjectTyping(
    configuration: TypeSpecBuilder.() -> Unit,
): SpecDelegateProvider<TypeSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider { buildCompanionObjectTypeSpec(it, configuration).also(::type) }
}

/**
 * Property delegate for inserting new class [TypeSpec] by populating newly created [TypeSpecBuilder]
 * using provided [configuration].
 */
fun TypeSpecHandler.interfaceTyping(
    configuration: TypeSpecBuilder.() -> Unit,
): SpecDelegateProvider<TypeSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider { buildInterfaceTypeSpec(it, configuration).also(::type) }
}

/**
 * Property delegate for inserting new class [TypeSpec] by populating newly created [TypeSpecBuilder]
 * using provided [configuration].
 */
fun TypeSpecHandler.enumTyping(
    configuration: TypeSpecBuilder.() -> Unit,
): SpecDelegateProvider<TypeSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider { buildEnumTypeSpec(it, configuration).also(::type) }
}

/**
 * Property delegate for inserting new class [TypeSpec] by populating newly created [TypeSpecBuilder]
 * using provided [configuration].
 */
fun TypeSpecHandler.annotationTyping(
    configuration: TypeSpecBuilder.() -> Unit,
): SpecDelegateProvider<TypeSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider { buildAnnotationTypeSpec(it, configuration).also(::type) }
}

/** Invokes DSL to configure [TypeSpec] collection. */
fun TypeSpecHandler.types(configuration: TypeSpecHandlerScope.() -> Unit) {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    TypeSpecHandlerScope(this).configuration()
}

/** Responsible for managing a set of [TypeSpec] instances. */
sealed interface TypeSpecHandler {
    fun type(type: TypeSpec)

    fun classType(name: String): TypeSpec = TypeSpec.classBuilder(name).build().also(::type)

    fun expectClassType(type: String): TypeSpec =
        TypeSpecBuilder(TypeSpec.expectClassBuilder(type)).build().also(::type)

    fun objectType(type: String): TypeSpec =
        TypeSpecBuilder(TypeSpec.objectBuilder(type)).build().also(::type)

    fun companionObjectType(type: String? = null): TypeSpec =
        TypeSpecBuilder(TypeSpec.companionObjectBuilder(type)).build().also(::type)

    fun interfaceType(type: String): TypeSpec =
        TypeSpecBuilder(TypeSpec.interfaceBuilder(type)).build().also(::type)

    fun enumType(type: String): TypeSpec =
        TypeSpecBuilder(TypeSpec.enumBuilder(type)).build().also(::type)

    fun anonymousType(): TypeSpec =
        TypeSpecBuilder(TypeSpec.anonymousClassBuilder()).build().also(::type)

    fun annotationType(type: String): TypeSpec =
        TypeSpecBuilder(TypeSpec.annotationBuilder(type)).build().also(::type)

    fun classTyping(): SpecDelegateProvider<TypeSpec> =
        SpecDelegateProvider { TypeSpec.classBuilder(it).build().also(::type) }

    fun expectClassTyping(): SpecDelegateProvider<TypeSpec> =
        SpecDelegateProvider {
            TypeSpecBuilder(TypeSpec.expectClassBuilder(it)).build().also(::type)
        }

    fun objectTyping(): SpecDelegateProvider<TypeSpec> =
        SpecDelegateProvider {
            TypeSpecBuilder(TypeSpec.objectBuilder(it)).build().also(::type)
        }

    fun companionObjectTyping(): SpecDelegateProvider<TypeSpec> =
        SpecDelegateProvider {
            TypeSpecBuilder(TypeSpec.companionObjectBuilder(it)).build().also(::type)
        }

    fun interfaceTyping(): SpecDelegateProvider<TypeSpec> =
        SpecDelegateProvider {
            TypeSpecBuilder(TypeSpec.interfaceBuilder(it)).build().also(::type)
        }

    fun enumTyping(): SpecDelegateProvider<TypeSpec> =
        SpecDelegateProvider {
            TypeSpecBuilder(TypeSpec.enumBuilder(it)).build().also(::type)
        }

    fun annotationTyping(): SpecDelegateProvider<TypeSpec> =
        SpecDelegateProvider {
            TypeSpecBuilder(TypeSpec.annotationBuilder(it)).build().also(::type)
        }
}

/** Receiver for the `types` block providing an extended set of operators for the configuration. */
@KotlinpoetDsl
class TypeSpecHandlerScope internal constructor(
    handler: TypeSpecHandler,
) : TypeSpecHandler by handler {
    /** @see classType */
    operator fun String.invoke(configuration: TypeSpecBuilder.() -> Unit): TypeSpec =
        buildClassTypeSpec(this, configuration).also(::type)
}

/** Wrapper of [TypeSpec.Builder], providing DSL support as a replacement to Java builder. */
@KotlinpoetDsl
class TypeSpecBuilder(
    private val nativeBuilder: TypeSpec.Builder,
) : AnnotationSpecHandler, PropertySpecHandler, FunSpecHandler, TypeSpecHandler {
    var initializerIndex: Int
        get() = nativeBuilder.initializerIndex
        set(value) {
            nativeBuilder.initializerIndex = value
        }

    val tags: MutableMap<KClass<*>, *> get() = nativeBuilder.tags
    val kdoc: CodeBlock.Builder get() = nativeBuilder.kdoc
    val originatingElements: MutableList<Element> get() = nativeBuilder.originatingElements
    val annotations: MutableList<AnnotationSpec> get() = nativeBuilder.annotations

    @OptIn(ExperimentalKotlinPoetApi::class)
    val contextReceiverTypes: MutableList<TypeName> get() = nativeBuilder.contextReceiverTypes

    val modifiers: MutableSet<KModifier> get() = nativeBuilder.modifiers
    val superinterfaces: MutableMap<TypeName, CodeBlock?> get() = nativeBuilder.superinterfaces
    val enumConstants: MutableMap<String, TypeSpec> get() = nativeBuilder.enumConstants
    val typeVariables: MutableList<TypeVariableName> get() = nativeBuilder.typeVariables

    val superclassConstructorParameters: MutableList<CodeBlock>
        get() = nativeBuilder.superclassConstructorParameters

    val properties: MutableList<PropertySpec> get() = nativeBuilder.propertySpecs
    val functions: MutableList<FunSpec> get() = nativeBuilder.funSpecs
    val types: MutableList<TypeSpec> get() = nativeBuilder.typeSpecs

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

    var primaryConstructor: FunSpec?
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
        get() = noGetter()
        set(value) {
            nativeBuilder.primaryConstructor(value)
        }

    var superclass: TypeName
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
        get() = noGetter()
        set(value) {
            nativeBuilder.superclass(value)
        }

    @DelicateKotlinPoetApi(DELICATE_API)
    fun superclass(type: Type) {
        nativeBuilder.superclass(type)
    }

    fun superclass(type: KClass<*>) {
        nativeBuilder.superclass(type)
    }

    inline fun <reified T> superclass(): Unit = superclass(T::class)

    fun superclassConstructorParameter(format: String, vararg args: Any) {
        nativeBuilder.addSuperclassConstructorParameter(format, *args)
    }

    fun superclassConstructorParameter(code: CodeBlock) {
        nativeBuilder.addSuperclassConstructorParameter(code)
    }

    fun superinterface(
        superinterface: TypeName,
        delegate: CodeBlock = CodeBlock.builder().build(),
    ) {
        nativeBuilder.addSuperinterface(superinterface, delegate)
    }

    @DelicateKotlinPoetApi(DELICATE_API)
    fun superinterface(superinterface: Type, delegate: CodeBlock = CodeBlock.builder().build()) {
        nativeBuilder.addSuperinterface(superinterface, delegate)
    }

    fun superinterface(
        superinterface: KClass<*>,
        delegate: CodeBlock = CodeBlock.builder().build(),
    ) {
        nativeBuilder.addSuperinterface(superinterface, delegate)
    }

    fun superinterface(superinterface: TypeName, constructorParameterName: String) {
        nativeBuilder.addSuperinterface(superinterface, constructorParameterName)
    }

    fun superinterface(superinterface: KClass<*>, constructorParameterName: String) {
        nativeBuilder.addSuperinterface(superinterface, constructorParameterName)
    }

    inline fun <reified T> superinterface(): Unit = superinterface(T::class)

    fun enumConstant(name: String, typeSpec: TypeSpec = TypeSpec.anonymousClassBuilder().build()) {
        nativeBuilder.addEnumConstant(name, typeSpec)
    }

    override fun property(property: PropertySpec) {
        nativeBuilder.addProperty(property)
    }

    fun initializerBlock(code: CodeBlock) {
        nativeBuilder.addInitializerBlock(code)
    }

    fun initializerBlock(format: String, vararg args: Any) {
        nativeBuilder.addInitializerBlock(codeBlockOf(format, *args))
    }

    override fun function(function: FunSpec) {
        nativeBuilder.addFunction(function)
    }

    override fun type(type: TypeSpec) {
        nativeBuilder.addType(type)
    }

    @OptIn(ExperimentalKotlinPoetApi::class)
    fun contextReceivers(receiverTypes: Iterable<TypeName>) {
        nativeBuilder.contextReceivers(receiverTypes)
    }

    override fun annotation(annotation: AnnotationSpec) {
        nativeBuilder.addAnnotation(annotation)
    }

    fun kdoc(format: String, vararg args: Any) {
        nativeBuilder.addKdoc(format, *args)
    }

    fun kdoc(block: CodeBlock) {
        nativeBuilder.addKdoc(block)
    }

    fun build(): TypeSpec = nativeBuilder.build()
}
