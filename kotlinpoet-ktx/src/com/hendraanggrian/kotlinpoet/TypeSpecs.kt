package com.hendraanggrian.kotlinpoet

import com.hendraanggrian.kotlinpoet.dsl.AnnotationContainer
import com.hendraanggrian.kotlinpoet.dsl.FunContainer
import com.hendraanggrian.kotlinpoet.dsl.KdocContainer
import com.hendraanggrian.kotlinpoet.dsl.PropertyContainer
import com.hendraanggrian.kotlinpoet.dsl.TypeContainer
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.TypeVariableName
import java.lang.reflect.Type
import javax.lang.model.element.Element
import kotlin.reflect.KClass

/** Builds a new class [TypeSpec] from [type]. */
fun buildClassType(type: String): TypeSpec =
    TypeSpec.classBuilder(type).build()

/**
 * Builds a new class [TypeSpec] from [type],
 * by populating newly created [TypeSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildClassType(type: String, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
    TypeSpecBuilder(TypeSpec.classBuilder(type)).apply(builderAction).build()

/** Builds a new class [TypeSpec] from [type]. */
fun buildClassType(type: ClassName): TypeSpec =
    TypeSpec.classBuilder(type).build()

/**
 * Builds a new class [TypeSpec] from [type],
 * by populating newly created [TypeSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildClassType(type: ClassName, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
    TypeSpecBuilder(TypeSpec.classBuilder(type)).apply(builderAction).build()

/** Builds a new interface [TypeSpec] from [type]. */
fun buildInterfaceType(type: String): TypeSpec =
    TypeSpec.interfaceBuilder(type).build()

/**
 * Builds a new interface [TypeSpec] from [type],
 * by populating newly created [TypeSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildInterfaceType(type: String, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
    TypeSpecBuilder(TypeSpec.interfaceBuilder(type)).apply(builderAction).build()

/** Builds a new interface [TypeSpec] from [type]. */
fun buildInterfaceType(type: ClassName): TypeSpec =
    TypeSpec.interfaceBuilder(type).build()

/**
 * Builds a new interface [TypeSpec] from [type],
 * by populating newly created [TypeSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildInterfaceType(type: ClassName, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
    TypeSpecBuilder(TypeSpec.interfaceBuilder(type)).apply(builderAction).build()

/** Builds a new enum [TypeSpec] from [type]. */
fun buildEnumType(type: String): TypeSpec =
    TypeSpec.enumBuilder(type).build()

/**
 * Builds a new enum [TypeSpec] from [type],
 * by populating newly created [TypeSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildEnumType(type: String, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
    TypeSpecBuilder(TypeSpec.enumBuilder(type)).apply(builderAction).build()

/** Builds a new enum [TypeSpec] from [type]. */
fun buildEnumType(type: ClassName): TypeSpec =
    TypeSpec.enumBuilder(type).build()

/**
 * Builds a new enum [TypeSpec] from [type],
 * by populating newly created [TypeSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildEnumType(type: ClassName, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
    TypeSpecBuilder(TypeSpec.enumBuilder(type)).apply(builderAction).build()

/** Builds a new anonymous [TypeSpec]. */
fun buildAnonymousType(): TypeSpec =
    TypeSpec.anonymousClassBuilder().build()

/**
 * Builds a new anonymous [TypeSpec],
 * by populating newly created [TypeSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildAnonymousType(
    builderAction: TypeSpecBuilder.() -> Unit
): TypeSpec = TypeSpecBuilder(TypeSpec.anonymousClassBuilder()).apply(builderAction).build()

/** Builds a new annotation [TypeSpec] from [type]. */
fun buildAnnotationType(type: String): TypeSpec =
    TypeSpec.annotationBuilder(type).build()

/**
 * Builds a new annotation [TypeSpec] from [type],
 * by populating newly created [TypeSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildAnnotationType(type: String, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
    TypeSpecBuilder(TypeSpec.annotationBuilder(type)).apply(builderAction).build()

/** Builds a new annotation [TypeSpec] from [type]. */
fun buildAnnotationType(type: ClassName): TypeSpec =
    TypeSpec.annotationBuilder(type).build()

/**
 * Builds a new annotation [TypeSpec] from [type],
 * by populating newly created [TypeSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildAnnotationType(type: ClassName, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
    TypeSpecBuilder(TypeSpec.annotationBuilder(type)).apply(builderAction).build()

/** Wrapper of [TypeSpec.Builder], providing DSL support as a replacement to Java builder. */
@KotlinpoetDslMarker
class TypeSpecBuilder @PublishedApi internal constructor(private val nativeBuilder: TypeSpec.Builder) {

    /** Tags variables of this builder. */
    val tags: MutableMap<KClass<*>, *>
        get() = nativeBuilder.tags

    /** Originating elements of this builder. */
    val originatingElements: MutableList<Element>
        get() = nativeBuilder.originatingElements

    /** Modifiers of this builder. */
    val modifiers: MutableSet<KModifier>
        get() = nativeBuilder.modifiers

    /** Super interfaces of this builder. */
    val superInterfaces: MutableMap<TypeName, CodeBlock?>
        get() = nativeBuilder.superinterfaces

    /** Enum constants of this builder. */
    val enumConstants: MutableMap<String, TypeSpec>
        get() = nativeBuilder.enumConstants

    /** Annotations of this builder. */
    val annotationSpecs: MutableList<AnnotationSpec>
        get() = nativeBuilder.annotationSpecs

    /** Type variables of this builder. */
    val typeVariables: MutableList<TypeVariableName>
        get() = nativeBuilder.typeVariables

    /** Super class constructor parameters of this builder. */
    val superclassConstructorParameters: MutableList<CodeBlock>
        get() = nativeBuilder.superclassConstructorParameters

    /** Properties of this builder. */
    val propertySpecs: MutableList<PropertySpec>
        get() = nativeBuilder.propertySpecs

    /** Functions of this builder. */
    val funSpecs: MutableList<FunSpec>
        get() = nativeBuilder.funSpecs

    /** Types of this builder. */
    val typeSpecs: MutableList<TypeSpec>
        get() = nativeBuilder.typeSpecs

    /** Collection of kdoc, may be configured with Kotlin DSL. */
    val kdoc: KdocContainer = object : KdocContainer() {
        override fun append(format: String, vararg args: Any) {
            nativeBuilder.addKdoc(format, *args)
        }

        override fun append(code: CodeBlock): CodeBlock =
            code.also { nativeBuilder.addKdoc(it) }
    }

    /** Collection of annotations, may be configured with Kotlin DSL. */
    val annotations: AnnotationContainer = object : AnnotationContainer() {
        override fun add(spec: AnnotationSpec): AnnotationSpec =
            spec.also { nativeBuilder.addAnnotation(it) }
    }

    /** Add field modifiers. */
    fun addModifiers(vararg modifiers: KModifier) {
        nativeBuilder.addModifiers(*modifiers)
    }

    /** Add type variables. */
    fun addTypeVariable(typeVariable: TypeVariableName) {
        nativeBuilder.addTypeVariable(typeVariable)
    }

    /** Add type variables. */
    fun addTypeVariables(typeVariables: Iterable<TypeVariableName>) {
        nativeBuilder.addTypeVariables(typeVariables)
    }

    /** Set primary constructor to type. */
    var primaryConstructor: FunSpec?
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            nativeBuilder.primaryConstructor(value)
        }

    /** Set primary constructor to type, returning the function. */
    fun primaryConstructor(): FunSpec =
        buildConstructorFunction().also { primaryConstructor = it }

    /** Set primary constructor to type with [builderAction], returning the function. */
    inline fun primaryConstructor(builderAction: FunSpecBuilder.() -> Unit): FunSpec =
        buildConstructorFunction(builderAction).also { primaryConstructor = it }

    /** Set superclass to type. */
    var superClass: TypeName
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            nativeBuilder.superclass(value)
        }

    /** Set superclass to [type]. */
    fun superClass(type: Type) {
        nativeBuilder.superclass(type)
    }

    /** Set superclass to [type]. */
    fun superClass(type: KClass<*>) =
        superClass(type.java)

    /** Set superclass to [T]. */
    inline fun <reified T> superClass() =
        superClass(T::class)

    /** Add super class constructor parameters like [String.format]. */
    fun addSuperclassConstructorParameter(format: String, vararg args: Any) {
        nativeBuilder.addSuperclassConstructorParameter(format, *args)
    }

    /** Add super class constructor parameters with code. */
    fun addSuperclassConstructorParameter(code: CodeBlock) =
        code.also { nativeBuilder.addSuperclassConstructorParameter(it) }

    /** Add super class constructor parameters with custom [builderAction]. */
    inline fun addSuperclassConstructorParameter(builderAction: CodeBlockBuilder.() -> Unit) =
        addSuperclassConstructorParameter(buildCode(builderAction))

    /** Add superinterface to [type]. */
    fun addSuperInterface(type: TypeName) {
        nativeBuilder.addSuperinterface(type)
    }

    /** Add superinterface to [type]. */
    fun addSuperInterface(type: Type) {
        nativeBuilder.addSuperinterface(type)
    }

    /** Add superinterface to [type]. */
    fun addSuperInterface(type: KClass<*>) =
        addSuperInterface(type.java)

    /** Add superinterface to [T]. */
    inline fun <reified T> addSuperInterface() =
        addSuperInterface(T::class)

    /** Add enum constant named [name]. */
    fun addEnumConstant(name: String) {
        nativeBuilder.addEnumConstant(name)
    }

    /** Add enum constant named [name] with specified [TypeSpec]. */
    fun addEnumConstant(name: String, spec: TypeSpec) {
        nativeBuilder.addEnumConstant(name, spec)
    }

    /** Collection of fields, may be configured with Kotlin DSL. */
    val properties: PropertyContainer = object : PropertyContainer() {
        override fun add(spec: PropertySpec): PropertySpec =
            spec.also { nativeBuilder.addProperty(it) }
    }

    /** Add initializer block containing [code]. */
    fun addInitializerBlock(code: CodeBlock): CodeBlock =
        code.also { nativeBuilder.addInitializerBlock(it) }

    /** Add initializer block containing code with custom initialization [builderAction]. */
    inline fun addInitializerBlock(builderAction: CodeBlockBuilder.() -> Unit): CodeBlock =
        addInitializerBlock(buildCode(builderAction))

    /** Collection of functions, may be configured with Kotlin DSL. */
    val functions: FunContainer = object : FunContainer() {
        override fun add(spec: FunSpec): FunSpec =
            spec.also { nativeBuilder.addFunction(it) }
    }

    /** Collection of types, may be configured with Kotlin DSL. */
    val types: TypeContainer = object : TypeContainer() {
        override fun add(spec: TypeSpec): TypeSpec =
            spec.also { nativeBuilder.addType(it) }
    }

    /** Add originating element. */
    fun addOriginatingElement(originatingElement: Element) {
        nativeBuilder.addOriginatingElement(originatingElement)
    }

    /** Returns native spec. */
    fun build(): TypeSpec =
        nativeBuilder.build()
}
