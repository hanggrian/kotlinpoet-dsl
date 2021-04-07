package com.hendraanggrian.kotlinpoet

import com.hendraanggrian.kotlinpoet.dsl.AnnotationSpecHandler
import com.hendraanggrian.kotlinpoet.dsl.AnnotationSpecHandlerScope
import com.hendraanggrian.kotlinpoet.dsl.FunSpecHandler
import com.hendraanggrian.kotlinpoet.dsl.FunSpecHandlerScope
import com.hendraanggrian.kotlinpoet.dsl.KdocHandler
import com.hendraanggrian.kotlinpoet.dsl.KdocHandlerScope
import com.hendraanggrian.kotlinpoet.dsl.PropertySpecHandler
import com.hendraanggrian.kotlinpoet.dsl.PropertySpecHandlerScope
import com.hendraanggrian.kotlinpoet.dsl.TypeNameHandler
import com.hendraanggrian.kotlinpoet.dsl.TypeNameHandlerScope
import com.hendraanggrian.kotlinpoet.dsl.TypeSpecHandler
import com.hendraanggrian.kotlinpoet.dsl.TypeSpecHandlerScope
import com.hendraanggrian.kotlinpoet.dsl.TypeVariableNameHandler
import com.hendraanggrian.kotlinpoet.dsl.TypeVariableNameHandlerScope
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeSpec
import java.lang.reflect.Type
import javax.lang.model.element.Element
import kotlin.reflect.KClass

/** Builds new class [TypeSpec] from name. */
fun classTypeSpecOf(type: String): TypeSpec = TypeSpec.classBuilder(type).build()

/** Builds new class [TypeSpec] from [ClassName]. */
fun classTypeSpecOf(type: ClassName): TypeSpec = TypeSpec.classBuilder(type).build()

/** Builds expect new class [TypeSpec] from name. */
fun expectClassTypeSpecOf(type: String): TypeSpec = TypeSpec.expectClassBuilder(type).build()

/** Builds new expect class [TypeSpec] from [ClassName]. */
fun expectClassTypeSpecOf(type: ClassName): TypeSpec = TypeSpec.expectClassBuilder(type).build()

/** Builds new object [TypeSpec] from name. */
fun objectTypeSpecOf(type: String): TypeSpec = TypeSpec.objectBuilder(type).build()

/** Builds new object class [TypeSpec] from [ClassName]. */
fun objectTypeSpecOf(type: ClassName): TypeSpec = TypeSpec.objectBuilder(type).build()

/** Builds new companion object [TypeSpec] from name. */
fun companionObjectTypeSpecOf(type: String? = null): TypeSpec = TypeSpec.companionObjectBuilder(type).build()

/** Builds new interface [TypeSpec] from name. */
fun interfaceTypeSpecOf(type: String): TypeSpec = TypeSpec.interfaceBuilder(type).build()

/** Builds new interface [TypeSpec] from [ClassName]. */
fun interfaceTypeSpecOf(type: ClassName): TypeSpec = TypeSpec.interfaceBuilder(type).build()

/** Builds new enum [TypeSpec] from name. */
fun enumTypeSpecOf(type: String): TypeSpec = TypeSpec.enumBuilder(type).build()

/** Builds new enum [TypeSpec] from [ClassName]. */
fun enumTypeSpecOf(type: ClassName): TypeSpec = TypeSpec.enumBuilder(type).build()

/** Builds new anonymous [TypeSpec]. */
fun emptyAnonymousTypeSpec(): TypeSpec = TypeSpec.anonymousClassBuilder().build()

/** Builds new annotation [TypeSpec] from name. */
fun annotationTypeSpecOf(type: String): TypeSpec = TypeSpec.annotationBuilder(type).build()

/** Builds new annotation [TypeSpec] from [ClassName]. */
fun annotationTypeSpecOf(type: ClassName): TypeSpec = TypeSpec.annotationBuilder(type).build()

/**
 * Builds new class [TypeSpec] from name,
 * by populating newly created [TypeSpecBuilder] using provided [configuration].
 */
fun buildClassTypeSpec(type: String, configuration: TypeSpecBuilder.() -> Unit): TypeSpec =
    TypeSpecBuilder(TypeSpec.classBuilder(type)).apply(configuration).build()

/**
 * Builds new class [TypeSpec] from [ClassName],
 * by populating newly created [TypeSpecBuilder] using provided [configuration].
 */
fun buildClassTypeSpec(type: ClassName, configuration: TypeSpecBuilder.() -> Unit): TypeSpec =
    TypeSpecBuilder(TypeSpec.classBuilder(type)).apply(configuration).build()

/**
 * Builds new expect class [TypeSpec] from name,
 * by populating newly created [TypeSpecBuilder] using provided [configuration].
 */
fun buildExpectClassTypeSpec(type: String, configuration: TypeSpecBuilder.() -> Unit): TypeSpec =
    TypeSpecBuilder(TypeSpec.expectClassBuilder(type)).apply(configuration).build()

/**
 * Builds new expect class [TypeSpec] from [ClassName],
 * by populating newly created [TypeSpecBuilder] using provided [configuration].
 */
fun buildExpectClassTypeSpec(type: ClassName, configuration: TypeSpecBuilder.() -> Unit): TypeSpec =
    TypeSpecBuilder(TypeSpec.expectClassBuilder(type)).apply(configuration).build()

/**
 * Builds new object [TypeSpec] from name,
 * by populating newly created [TypeSpecBuilder] using provided [configuration].
 */
fun buildObjectTypeSpec(type: String, configuration: TypeSpecBuilder.() -> Unit): TypeSpec =
    TypeSpecBuilder(TypeSpec.objectBuilder(type)).apply(configuration).build()

/**
 * Builds new object [TypeSpec] from [ClassName],
 * by populating newly created [TypeSpecBuilder] using provided [configuration].
 */
fun buildObjectTypeSpec(type: ClassName, configuration: TypeSpecBuilder.() -> Unit): TypeSpec =
    TypeSpecBuilder(TypeSpec.objectBuilder(type)).apply(configuration).build()

/**
 * Builds new object [TypeSpec] from name,
 * by populating newly created [TypeSpecBuilder] using provided [configuration].
 */
fun buildCompanionObjectTypeSpec(type: String? = null, configuration: TypeSpecBuilder.() -> Unit): TypeSpec =
    TypeSpecBuilder(TypeSpec.companionObjectBuilder(type)).apply(configuration).build()

/**
 * Builds new interface [TypeSpec] from name,
 * by populating newly created [TypeSpecBuilder] using provided [configuration].
 */
fun buildInterfaceTypeSpec(type: String, configuration: TypeSpecBuilder.() -> Unit): TypeSpec =
    TypeSpecBuilder(TypeSpec.interfaceBuilder(type)).apply(configuration).build()

/**
 * Builds new interface [TypeSpec] from [ClassName],
 * by populating newly created [TypeSpecBuilder] using provided [configuration].
 */
fun buildInterfaceTypeSpec(type: ClassName, configuration: TypeSpecBuilder.() -> Unit): TypeSpec =
    TypeSpecBuilder(TypeSpec.interfaceBuilder(type)).apply(configuration).build()

/**
 * Builds new enum [TypeSpec] from name,
 * by populating newly created [TypeSpecBuilder] using provided [configuration].
 */
fun buildEnumTypeSpec(type: String, configuration: TypeSpecBuilder.() -> Unit): TypeSpec =
    TypeSpecBuilder(TypeSpec.enumBuilder(type)).apply(configuration).build()

/**
 * Builds new enum [TypeSpec] from [ClassName],
 * by populating newly created [TypeSpecBuilder] using provided [configuration].
 */
fun buildEnumTypeSpec(type: ClassName, configuration: TypeSpecBuilder.() -> Unit): TypeSpec =
    TypeSpecBuilder(TypeSpec.enumBuilder(type)).apply(configuration).build()

/**
 * Builds new anonymous [TypeSpec],
 * by populating newly created [TypeSpecBuilder] using provided [configuration].
 */
fun buildAnonymousTypeSpec(configuration: TypeSpecBuilder.() -> Unit): TypeSpec =
    TypeSpecBuilder(TypeSpec.anonymousClassBuilder()).apply(configuration).build()

/**
 * Builds new annotation [TypeSpec] from name,
 * by populating newly created [TypeSpecBuilder] using provided [configuration].
 */
fun buildAnnotationTypeSpec(type: String, configuration: TypeSpecBuilder.() -> Unit): TypeSpec =
    TypeSpecBuilder(TypeSpec.annotationBuilder(type)).apply(configuration).build()

/**
 * Builds new annotation [TypeSpec] from [ClassName],
 * by populating newly created [TypeSpecBuilder] using provided [configuration].
 */
fun buildAnnotationTypeSpec(type: ClassName, configuration: TypeSpecBuilder.() -> Unit): TypeSpec =
    TypeSpecBuilder(TypeSpec.annotationBuilder(type)).apply(configuration).build()

/** Modify existing [TypeSpec.Builder] using provided [configuration]. */
fun TypeSpec.Builder.edit(configuration: TypeSpecBuilder.() -> Unit): TypeSpec.Builder =
    TypeSpecBuilder(this).apply(configuration).nativeBuilder

/**
 * Wrapper of [TypeSpec.Builder], providing DSL support as a replacement to Java builder.
 * @param nativeBuilder source builder.
 */
@SpecDslMarker
class TypeSpecBuilder internal constructor(val nativeBuilder: TypeSpec.Builder) {

    /** Initializer index of this type. */
    val initializerIndex: Int get() = nativeBuilder.initializerIndex

    /** Tags variables of this type. */
    val tags: MutableMap<KClass<*>, *> get() = nativeBuilder.tags

    /** Originating elements of this type. */
    val originatingElements: MutableList<Element> get() = nativeBuilder.originatingElements

    /** Modifiers of this type. */
    val modifiers: MutableSet<KModifier> get() = nativeBuilder.modifiers

    /** Enum constants of this type. */
    val enumConstants: MutableMap<String, TypeSpec> get() = nativeBuilder.enumConstants

    /** Super class constructor parameters of this type. */
    val superclassConstructorParameters: MutableList<CodeBlock> get() = nativeBuilder.superclassConstructorParameters

    /** Kdoc of this type. */
    val kdoc: KdocHandler = object : KdocHandler() {
        override fun append(format: String, vararg args: Any) {
            nativeBuilder.addKdoc(format, *args)
        }

        override fun append(code: CodeBlock) {
            nativeBuilder.addKdoc(code)
        }
    }

    /** Configures kdoc of this type. */
    fun kdoc(configuration: KdocHandlerScope.() -> Unit): Unit =
        KdocHandlerScope(kdoc).configuration()

    /** Annotations of this type. */
    val annotations: AnnotationSpecHandler = AnnotationSpecHandler(nativeBuilder.annotationSpecs)

    /** Configures annotations of this type. */
    fun annotations(configuration: AnnotationSpecHandlerScope.() -> Unit): Unit =
        AnnotationSpecHandlerScope(annotations).configuration()

    /** Add type modifiers. */
    fun addModifiers(vararg modifiers: KModifier) {
        nativeBuilder.addModifiers(*modifiers)
    }

    /** Type variables of this type. */
    val typeVariables: TypeVariableNameHandler = TypeVariableNameHandler(nativeBuilder.typeVariables)

    /** Configures type variables of this type. */
    fun typeVariables(configuration: TypeVariableNameHandlerScope.() -> Unit): Unit =
        TypeVariableNameHandlerScope(typeVariables).configuration()

    /** Set primary constructor to type. */
    var primaryConstructor: FunSpec?
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            nativeBuilder.primaryConstructor(value)
        }

    /** Set primary constructor to type. */
    fun primaryConstructor() {
        nativeBuilder.primaryConstructor(emptyConstructorFunSpec())
    }

    /** Set primary constructor to type with [configuration]. */
    fun primaryConstructor(configuration: FunSpecBuilder.() -> Unit) {
        primaryConstructor = buildConstructorFunSpec(configuration)
    }

    /** Set superclass to type. */
    var superclass: TypeName
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            nativeBuilder.superclass(value)
        }

    /** Set superclass to [type]. */
    fun superclass(type: Type) {
        nativeBuilder.superclass(type)
    }

    /** Set superclass to [type]. */
    fun superclass(type: KClass<*>) {
        nativeBuilder.superclass(type)
    }

    /** Set superclass to [T]. */
    inline fun <reified T> superclass(): Unit = superclass(T::class)

    /** Add super class constructor parameters like [String.format]. */
    fun addSuperclassConstructorParameter(format: String, vararg args: Any) {
        nativeBuilder.addSuperclassConstructorParameter(format, *args)
    }

    /** Add super class constructor parameters with code. */
    fun addSuperclassConstructorParameter(code: CodeBlock) {
        nativeBuilder.addSuperclassConstructorParameter(code)
    }

    /** Add super class constructor parameters with custom [configuration]. */
    fun addSuperclassConstructorParameter(configuration: CodeBlockBuilder.() -> Unit) {
        addSuperclassConstructorParameter(buildCodeBlock(configuration))
    }

    /** Super interfaces of this type. */
    val superinterfaces: TypeNameHandler get() = TypeNameHandler(nativeBuilder.superinterfaces)

    /** Configures super interfaces of this type. */
    fun superinterfaces(configuration: TypeNameHandlerScope.() -> Unit): Unit =
        TypeNameHandlerScope(superinterfaces).configuration()

    /** Add enum constant with name. */
    fun addEnumConstant(name: String) {
        nativeBuilder.addEnumConstant(name)
    }

    /** Add enum constant with name and specified [TypeSpec]. */
    fun addEnumConstant(name: String, spec: TypeSpec) {
        nativeBuilder.addEnumConstant(name, spec)
    }

    /** Properties of this type. */
    val properties: PropertySpecHandler = PropertySpecHandler(nativeBuilder.propertySpecs)

    /** Configures properties of this type. */
    fun properties(configuration: PropertySpecHandlerScope.() -> Unit): Unit =
        PropertySpecHandlerScope(properties).configuration()

    /** Add initializer block containing [code]. */
    fun addInitializerBlock(code: CodeBlock) {
        nativeBuilder.addInitializerBlock(code)
    }

    /** Add initializer block containing code with custom initialization [configuration]. */
    fun addInitializerBlock(configuration: CodeBlockBuilder.() -> Unit) {
        addInitializerBlock(buildCodeBlock(configuration))
    }

    /** Functions of this type. */
    val functions: FunSpecHandler = FunSpecHandler(nativeBuilder.funSpecs)

    /** Configures functions of this type. */
    fun functions(configuration: FunSpecHandlerScope.() -> Unit): Unit =
        FunSpecHandlerScope(functions).configuration()

    /** Types of this type. */
    val types: TypeSpecHandler = TypeSpecHandler(nativeBuilder.typeSpecs)

    /** Configures types of this type. */
    fun types(configuration: TypeSpecHandlerScope.() -> Unit): Unit =
        TypeSpecHandlerScope(types).configuration()

    /** Add originating element. */
    fun addOriginatingElement(originatingElement: Element) {
        nativeBuilder.addOriginatingElement(originatingElement)
    }

    /** Returns native spec. */
    fun build(): TypeSpec = nativeBuilder.build()
}
