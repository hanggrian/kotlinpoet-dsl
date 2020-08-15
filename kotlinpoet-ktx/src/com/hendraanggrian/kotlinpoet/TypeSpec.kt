package com.hendraanggrian.kotlinpoet

import com.hendraanggrian.kotlinpoet.collections.AnnotationSpecList
import com.hendraanggrian.kotlinpoet.collections.AnnotationSpecListScope
import com.hendraanggrian.kotlinpoet.collections.FunSpecList
import com.hendraanggrian.kotlinpoet.collections.FunSpecListScope
import com.hendraanggrian.kotlinpoet.collections.KdocContainer
import com.hendraanggrian.kotlinpoet.collections.KdocContainerScope
import com.hendraanggrian.kotlinpoet.collections.PropertySpecList
import com.hendraanggrian.kotlinpoet.collections.PropertySpecListScope
import com.hendraanggrian.kotlinpoet.collections.TypeNameMap
import com.hendraanggrian.kotlinpoet.collections.TypeSpecList
import com.hendraanggrian.kotlinpoet.collections.TypeSpecListScope
import com.hendraanggrian.kotlinpoet.collections.TypeVariableNameList
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
 * by populating newly created [TypeSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildClassTypeSpec(
    type: String,
    builderAction: TypeSpecBuilder.() -> Unit
): TypeSpec = TypeSpec.classBuilder(type).build(builderAction)

/**
 * Builds new class [TypeSpec] from [ClassName],
 * by populating newly created [TypeSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildClassTypeSpec(
    type: ClassName,
    builderAction: TypeSpecBuilder.() -> Unit
): TypeSpec = TypeSpec.classBuilder(type).build(builderAction)

/**
 * Builds new expect class [TypeSpec] from name,
 * by populating newly created [TypeSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildExpectClassTypeSpec(
    type: String,
    builderAction: TypeSpecBuilder.() -> Unit
): TypeSpec = TypeSpec.expectClassBuilder(type).build(builderAction)

/**
 * Builds new expect class [TypeSpec] from [ClassName],
 * by populating newly created [TypeSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildExpectClassTypeSpec(
    type: ClassName,
    builderAction: TypeSpecBuilder.() -> Unit
): TypeSpec = TypeSpec.expectClassBuilder(type).build(builderAction)

/**
 * Builds new object [TypeSpec] from name,
 * by populating newly created [TypeSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildObjectTypeSpec(
    type: String,
    builderAction: TypeSpecBuilder.() -> Unit
): TypeSpec = TypeSpec.objectBuilder(type).build(builderAction)

/**
 * Builds new object [TypeSpec] from [ClassName],
 * by populating newly created [TypeSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildObjectTypeSpec(
    type: ClassName,
    builderAction: TypeSpecBuilder.() -> Unit
): TypeSpec = TypeSpec.objectBuilder(type).build(builderAction)

/**
 * Builds new object [TypeSpec] from name,
 * by populating newly created [TypeSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildCompanionObjectTypeSpec(
    type: String? = null,
    builderAction: TypeSpecBuilder.() -> Unit
): TypeSpec = TypeSpec.companionObjectBuilder(type).build(builderAction)

/**
 * Builds new interface [TypeSpec] from name,
 * by populating newly created [TypeSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildInterfaceTypeSpec(
    type: String,
    builderAction: TypeSpecBuilder.() -> Unit
): TypeSpec = TypeSpec.interfaceBuilder(type).build(builderAction)

/**
 * Builds new interface [TypeSpec] from [ClassName],
 * by populating newly created [TypeSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildInterfaceTypeSpec(
    type: ClassName,
    builderAction: TypeSpecBuilder.() -> Unit
): TypeSpec = TypeSpec.interfaceBuilder(type).build(builderAction)

/**
 * Builds new enum [TypeSpec] from name,
 * by populating newly created [TypeSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildEnumTypeSpec(
    type: String,
    builderAction: TypeSpecBuilder.() -> Unit
): TypeSpec = TypeSpec.enumBuilder(type).build(builderAction)

/**
 * Builds new enum [TypeSpec] from [ClassName],
 * by populating newly created [TypeSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildEnumTypeSpec(
    type: ClassName,
    builderAction: TypeSpecBuilder.() -> Unit
): TypeSpec = TypeSpec.enumBuilder(type).build(builderAction)

/**
 * Builds new anonymous [TypeSpec],
 * by populating newly created [TypeSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildAnonymousTypeSpec(
    builderAction: TypeSpecBuilder.() -> Unit
): TypeSpec = TypeSpec.anonymousClassBuilder().build(builderAction)

/**
 * Builds new annotation [TypeSpec] from name,
 * by populating newly created [TypeSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildAnnotationTypeSpec(
    type: String,
    builderAction: TypeSpecBuilder.() -> Unit
): TypeSpec = TypeSpec.annotationBuilder(type).build(builderAction)

/**
 * Builds new annotation [TypeSpec] from [ClassName],
 * by populating newly created [TypeSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildAnnotationTypeSpec(
    type: ClassName,
    builderAction: TypeSpecBuilder.() -> Unit
): TypeSpec = TypeSpec.annotationBuilder(type).build(builderAction)

/** Modify existing [TypeSpec.Builder] using provided [builderAction] and then building it. */
inline fun TypeSpec.Builder.build(
    builderAction: TypeSpecBuilder.() -> Unit
): TypeSpec = TypeSpecBuilder(this).apply(builderAction).build()

/** Wrapper of [TypeSpec.Builder], providing DSL support as a replacement to Java builder. */
@SpecDslMarker
class TypeSpecBuilder(private val nativeBuilder: TypeSpec.Builder) {

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
    val kdoc: KdocContainer = object : KdocContainer() {
        override fun append(format: String, vararg args: Any) {
            nativeBuilder.addKdoc(format, *args)
        }

        override fun append(code: CodeBlock) {
            nativeBuilder.addKdoc(code)
        }
    }

    /** Configures kdoc of this type. */
    inline fun kdoc(builderAction: KdocContainerScope.() -> Unit): Unit =
        KdocContainerScope(kdoc).builderAction()

    /** Annotations of this type. */
    val annotations: AnnotationSpecList = AnnotationSpecList(nativeBuilder.annotationSpecs)

    /** Configures annotations of this type. */
    inline fun annotations(builderAction: AnnotationSpecListScope.() -> Unit): Unit =
        AnnotationSpecListScope(annotations).builderAction()

    /** Add type modifiers. */
    fun addModifiers(vararg modifiers: KModifier) {
        nativeBuilder.addModifiers(*modifiers)
    }

    /** Type variables of this type. */
    val typeVariables: TypeVariableNameList = TypeVariableNameList(nativeBuilder.typeVariables)

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

    /** Set primary constructor to type with [builderAction]. */
    inline fun primaryConstructor(builderAction: FunSpecBuilder.() -> Unit) {
        primaryConstructor = buildConstructorFunSpec(builderAction)
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

    /** Add super class constructor parameters with custom [builderAction]. */
    inline fun addSuperclassConstructorParameter(builderAction: CodeBlockBuilder.() -> Unit) {
        addSuperclassConstructorParameter(buildCodeBlock(builderAction))
    }

    /** Super interfaces of this type. */
    val superinterfaces: TypeNameMap get() = TypeNameMap(nativeBuilder.superinterfaces)

    /** Add enum constant with name. */
    fun addEnumConstant(name: String) {
        nativeBuilder.addEnumConstant(name)
    }

    /** Add enum constant with name and specified [TypeSpec]. */
    fun addEnumConstant(name: String, spec: TypeSpec) {
        nativeBuilder.addEnumConstant(name, spec)
    }

    /** Properties of this type. */
    val properties: PropertySpecList = PropertySpecList(nativeBuilder.propertySpecs)

    /** Configures properties of this type. */
    inline fun properties(builderAction: PropertySpecListScope.() -> Unit): Unit =
        PropertySpecListScope(properties).builderAction()

    /** Add initializer block containing [code]. */
    fun addInitializerBlock(code: CodeBlock) {
        nativeBuilder.addInitializerBlock(code)
    }

    /** Add initializer block containing code with custom initialization [builderAction]. */
    inline fun addInitializerBlock(builderAction: CodeBlockBuilder.() -> Unit) {
        addInitializerBlock(buildCodeBlock(builderAction))
    }

    /** Functions of this type. */
    val functions: FunSpecList = FunSpecList(nativeBuilder.funSpecs)

    /** Configures functions of this type. */
    inline fun functions(builderAction: FunSpecListScope.() -> Unit): Unit =
        FunSpecListScope(functions).builderAction()

    /** Types of this type. */
    val types: TypeSpecList = TypeSpecList(nativeBuilder.typeSpecs)

    /** Configures types of this type. */
    inline fun types(builderAction: TypeSpecListScope.() -> Unit): Unit =
        TypeSpecListScope(types).builderAction()

    /** Add originating element. */
    fun addOriginatingElement(originatingElement: Element) {
        nativeBuilder.addOriginatingElement(originatingElement)
    }

    /** Returns native spec. */
    fun build(): TypeSpec = nativeBuilder.build()
}
