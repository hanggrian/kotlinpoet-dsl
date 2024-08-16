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
public inline fun buildClassTypeSpec(
    name: String,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.classBuilder(name))
        .apply(configuration)
        .build()
}

/**
 * Builds new class [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public inline fun buildClassTypeSpec(
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
public inline fun buildObjectTypeSpec(
    name: String,
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
public inline fun buildObjectTypeSpec(
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
public inline fun buildCompanionObjectTypeSpec(
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
public inline fun buildInterfaceTypeSpec(
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
public inline fun buildInterfaceTypeSpec(
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
public inline fun buildEnumTypeSpec(
    name: String,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.enumBuilder(name))
        .apply(configuration)
        .build()
}

/**
 * Builds new enum [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public inline fun buildEnumTypeSpec(
    name: ClassName,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.enumBuilder(name))
        .apply(configuration)
        .build()
}

/**
 * Builds new anonymous [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public inline fun buildAnonymousTypeSpec(configuration: TypeSpecBuilder.() -> Unit): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.anonymousClassBuilder())
        .apply(configuration)
        .build()
}

/**
 * Builds new annotation [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public inline fun buildAnnotationTypeSpec(
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
public inline fun buildAnnotationTypeSpec(
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
public inline fun TypeSpecHandler.addClass(
    name: String,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.classBuilder(name))
        .apply(configuration)
        .build()
        .also(::add)
}

/**
 * Inserts new class [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public inline fun TypeSpecHandler.addClass(
    name: ClassName,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.classBuilder(name))
        .apply(configuration)
        .build()
        .also(::add)
}

/**
 * Inserts new object [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public inline fun TypeSpecHandler.addObject(
    name: String,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.objectBuilder(name))
        .apply(configuration)
        .build()
        .also(::add)
}

/**
 * Inserts new object [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public inline fun TypeSpecHandler.addObject(
    name: ClassName,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.objectBuilder(name))
        .apply(configuration)
        .build()
        .also(::add)
}

/**
 * Inserts new object [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public inline fun TypeSpecHandler.addCompanionObject(
    name: String? = null,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.companionObjectBuilder(name))
        .apply(configuration)
        .build()
        .also(::add)
}

/**
 * Inserts new interface [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public inline fun TypeSpecHandler.addInterface(
    name: String,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.interfaceBuilder(name))
        .apply(configuration)
        .build()
        .also(::add)
}

/**
 * Inserts new interface [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public inline fun TypeSpecHandler.addInterface(
    name: ClassName,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.interfaceBuilder(name))
        .apply(configuration)
        .build()
        .also(::add)
}

/**
 * Inserts new enum [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public inline fun TypeSpecHandler.addEnum(
    name: String,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.enumBuilder(name))
        .apply(configuration)
        .build()
        .also(::add)
}

/**
 * Inserts new enum [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public inline fun TypeSpecHandler.addEnum(
    name: ClassName,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.enumBuilder(name))
        .apply(configuration)
        .build()
        .also(::add)
}

/**
 * Inserts new anonymous [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public inline fun TypeSpecHandler.addAnonymous(
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.anonymousClassBuilder())
        .apply(configuration)
        .build()
        .also(::add)
}

/**
 * Inserts new annotation [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public inline fun TypeSpecHandler.addAnnotation(
    name: String,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.annotationBuilder(name))
        .apply(configuration)
        .build()
        .also(::add)
}

/**
 * Inserts new annotation [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public inline fun TypeSpecHandler.addAnnotation(
    name: ClassName,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.annotationBuilder(name))
        .apply(configuration)
        .build()
        .also(::add)
}

/**
 * Property delegate for inserting new class [TypeSpec] by populating newly created
 * [TypeSpecBuilder] using provided [configuration].
 */
public fun TypeSpecHandler.addingClass(
    configuration: TypeSpecBuilder.() -> Unit,
): SpecDelegateProvider<TypeSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider {
        TypeSpecBuilder(TypeSpec.classBuilder(it))
            .apply(configuration)
            .build()
            .also(::add)
    }
}

/**
 * Property delegate for inserting new class [TypeSpec] by populating newly created
 * [TypeSpecBuilder] using provided [configuration].
 */
public fun TypeSpecHandler.addingObject(
    configuration: TypeSpecBuilder.() -> Unit,
): SpecDelegateProvider<TypeSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider {
        TypeSpecBuilder(TypeSpec.objectBuilder(it))
            .apply(configuration)
            .build()
            .also(::add)
    }
}

/**
 * Property delegate for inserting new class [TypeSpec] by populating newly created
 * [TypeSpecBuilder] using provided [configuration].
 */
public fun TypeSpecHandler.addingCompanionObject(
    configuration: TypeSpecBuilder.() -> Unit,
): SpecDelegateProvider<TypeSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider {
        TypeSpecBuilder(TypeSpec.companionObjectBuilder(it))
            .apply(configuration)
            .build()
            .also(::add)
    }
}

/**
 * Property delegate for inserting new class [TypeSpec] by populating newly created
 * [TypeSpecBuilder] using provided [configuration].
 */
public fun TypeSpecHandler.addingInterface(
    configuration: TypeSpecBuilder.() -> Unit,
): SpecDelegateProvider<TypeSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider {
        TypeSpecBuilder(TypeSpec.interfaceBuilder(it))
            .apply(configuration)
            .build()
            .also(::add)
    }
}

/**
 * Property delegate for inserting new class [TypeSpec] by populating newly created
 * [TypeSpecBuilder] using provided [configuration].
 */
public fun TypeSpecHandler.addingEnum(
    configuration: TypeSpecBuilder.() -> Unit,
): SpecDelegateProvider<TypeSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider {
        TypeSpecBuilder(TypeSpec.enumBuilder(it))
            .apply(configuration)
            .build()
            .also(::add)
    }
}

/**
 * Property delegate for inserting new class [TypeSpec] by populating newly created
 * [TypeSpecBuilder] using provided [configuration].
 */
public fun TypeSpecHandler.addingAnnotation(
    configuration: TypeSpecBuilder.() -> Unit,
): SpecDelegateProvider<TypeSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider {
        TypeSpecBuilder(TypeSpec.annotationBuilder(it))
            .apply(configuration)
            .build()
            .also(::add)
    }
}

/** Responsible for managing a set of [TypeSpec] instances. */
public interface TypeSpecHandler {
    public fun add(type: TypeSpec)

    public fun addClass(name: String): TypeSpec = classTypeSpecOf(name).also(::add)

    public fun addClass(name: ClassName): TypeSpec = classTypeSpecOf(name).also(::add)

    public fun addObject(name: String): TypeSpec = objectTypeSpecOf(name).also(::add)

    public fun addObject(name: ClassName): TypeSpec = objectTypeSpecOf(name).also(::add)

    public fun addCompanionObject(name: String? = null): TypeSpec =
        companionObjectTypeSpecOf(name).also(::add)

    public fun addInterface(name: String): TypeSpec = interfaceTypeSpecOf(name).also(::add)

    public fun addInterface(name: ClassName): TypeSpec = interfaceTypeSpecOf(name).also(::add)

    public fun addEnum(name: String): TypeSpec = enumTypeSpecOf(name).also(::add)

    public fun addEnum(name: ClassName): TypeSpec = enumTypeSpecOf(name).also(::add)

    public fun addAnonymous(): TypeSpec = emptyAnonymousClassTypeSpec().also(::add)

    public fun addAnnotation(name: String): TypeSpec = annotationTypeSpecOf(name).also(::add)

    public fun addAnnotation(name: ClassName): TypeSpec = annotationTypeSpecOf(name).also(::add)

    public fun addingClass(): SpecDelegateProvider<TypeSpec> =
        SpecDelegateProvider { classTypeSpecOf(it).also(::add) }

    public fun addingObject(): SpecDelegateProvider<TypeSpec> =
        SpecDelegateProvider { objectTypeSpecOf(it).also(::add) }

    public fun addingCompanionObject(): SpecDelegateProvider<TypeSpec> =
        SpecDelegateProvider { companionObjectTypeSpecOf(it).also(::add) }

    public fun addingInterface(): SpecDelegateProvider<TypeSpec> =
        SpecDelegateProvider { interfaceTypeSpecOf(it).also(::add) }

    public fun addingEnum(): SpecDelegateProvider<TypeSpec> =
        SpecDelegateProvider { enumTypeSpecOf(it).also(::add) }

    public fun addingAnnotation(): SpecDelegateProvider<TypeSpec> =
        SpecDelegateProvider { annotationTypeSpecOf(it).also(::add) }
}

/** Receiver for the `types` block providing an extended set of operators for the configuration. */
@KotlinpoetDsl
public open class TypeSpecHandlerScope private constructor(handler: TypeSpecHandler) :
    TypeSpecHandler by handler {
        public inline operator fun String.invoke(
            configuration: TypeSpecBuilder.() -> Unit,
        ): TypeSpec = addClass(this, configuration)

        public companion object {
            public fun of(handler: TypeSpecHandler): TypeSpecHandlerScope =
                TypeSpecHandlerScope(handler)
        }
    }

/** Wrapper of [TypeSpec.Builder], providing DSL support as a replacement to Java builder. */
@KotlinpoetDsl
public class TypeSpecBuilder(private val nativeBuilder: TypeSpec.Builder) {
    public val annotations: AnnotationSpecHandler =
        object : AnnotationSpecHandler {
            override fun add(annotation: AnnotationSpec) {
                annotationSpecs += annotation
            }
        }

    public val properties: PropertySpecHandler =
        object : PropertySpecHandler {
            override fun add(property: PropertySpec) {
                propertySpecs += property
            }
        }

    public val functions: FunSpecHandler =
        object : FunSpecHandler {
            override fun add(function: FunSpec) {
                functionSpecs += function
            }
        }

    public val types: TypeSpecHandler =
        object : TypeSpecHandler {
            override fun add(type: TypeSpec) {
                typeSpecs += type
            }
        }

    /** Invokes DSL to configure [AnnotationSpec] collection. */
    public inline fun annotations(configuration: AnnotationSpecHandlerScope.() -> Unit) {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        AnnotationSpecHandlerScope
            .of(annotations)
            .configuration()
    }

    /** Invokes DSL to configure [PropertySpec] collection. */
    public inline fun properties(configuration: PropertySpecHandlerScope.() -> Unit) {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        PropertySpecHandlerScope
            .of(properties)
            .configuration()
    }

    /** Invokes DSL to configure [FunSpec] collection. */
    public inline fun functions(configuration: FunSpecHandlerScope.() -> Unit) {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        FunSpecHandlerScope
            .of(functions)
            .configuration()
    }

    /** Invokes DSL to configure [TypeSpec] collection. */
    public inline fun types(configuration: TypeSpecHandlerScope.() -> Unit) {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        TypeSpecHandlerScope
            .of(types)
            .configuration()
    }

    public var initializerIndex: Int
        get() = nativeBuilder.initializerIndex
        set(value) {
            nativeBuilder.initializerIndex = value
        }

    public val tags: MutableMap<KClass<*>, *> get() = nativeBuilder.tags
    public val kdoc: CodeBlock.Builder get() = nativeBuilder.kdoc
    public val originatingElements: MutableList<Element>
        get() = nativeBuilder.originatingElements
    public val annotationSpecs: MutableList<AnnotationSpec> get() = nativeBuilder.annotations

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

    public val propertySpecs: MutableList<PropertySpec> get() = nativeBuilder.propertySpecs
    public val functionSpecs: MutableList<FunSpec> get() = nativeBuilder.funSpecs
    public val typeSpecs: MutableList<TypeSpec> get() = nativeBuilder.typeSpecs

    public fun addModifiers(vararg modifiers: KModifier) {
        this.modifiers += modifiers
    }

    public fun addTypeVariables(vararg typeVariables: TypeVariableName) {
        this.typeVariables += typeVariables
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
    public fun setSuperclass(type: Type) {
        superclass = type.name
    }

    public fun setSuperclass(type: KClass<*>) {
        superclass = type.name
    }

    public inline fun <reified T> setSuperclass() {
        superclass = T::class.name
    }

    public fun addSuperclassConstructorParameter(format: String, vararg args: Any) {
        superclassConstructorParameters += codeBlockOf(format, *args)
    }

    public fun addSuperinterface(
        superinterface: TypeName,
        delegate: CodeBlock = CodeBlock.builder().build(),
    ) {
        nativeBuilder.addSuperinterface(superinterface, delegate)
    }

    @DelicateKotlinPoetApi(DELICATE_API)
    public fun addSuperinterface(
        superinterface: Type,
        delegate: CodeBlock = CodeBlock.builder().build(),
    ) {
        nativeBuilder.addSuperinterface(superinterface, delegate)
    }

    public fun addSuperinterface(
        superinterface: KClass<*>,
        delegate: CodeBlock = CodeBlock.builder().build(),
    ) {
        nativeBuilder.addSuperinterface(superinterface, delegate)
    }

    public fun addSuperinterface(superinterface: TypeName, constructorParameterName: String) {
        nativeBuilder.addSuperinterface(superinterface, constructorParameterName)
    }

    public fun addSuperinterface(superinterface: KClass<*>, constructorParameterName: String) {
        nativeBuilder.addSuperinterface(superinterface, constructorParameterName)
    }

    public inline fun <reified T> addSuperinterface(): Unit = addSuperinterface(T::class)

    public fun enumConstant(
        name: String,
        type: TypeSpec = TypeSpec.anonymousClassBuilder().build(),
    ) {
        enumConstants[name] = type
    }

    public fun addInitializerBlock(code: CodeBlock) {
        nativeBuilder.addInitializerBlock(code)
    }

    public fun addInitializerBlock(format: String, vararg args: Any) {
        nativeBuilder.addInitializerBlock(codeBlockOf(format, *args))
    }

    @OptIn(ExperimentalKotlinPoetApi::class)
    public fun contextReceivers(receiverTypes: Iterable<TypeName>) {
        nativeBuilder.contextReceivers(receiverTypes)
    }

    public fun addKdoc(format: String, vararg args: Any) {
        nativeBuilder.addKdoc(format, *args)
    }

    public fun addKdoc(block: CodeBlock) {
        nativeBuilder.addKdoc(block)
    }

    public fun build(): TypeSpec = nativeBuilder.build()
}
