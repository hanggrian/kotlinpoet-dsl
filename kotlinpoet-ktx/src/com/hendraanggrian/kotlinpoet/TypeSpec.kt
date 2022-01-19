package com.hendraanggrian.kotlinpoet

import com.hendraanggrian.kotlinpoet.collections.AnnotationSpecCollection
import com.hendraanggrian.kotlinpoet.collections.AnnotationSpecCollectionScope
import com.hendraanggrian.kotlinpoet.collections.FunSpecCollection
import com.hendraanggrian.kotlinpoet.collections.FunSpecCollectionScope
import com.hendraanggrian.kotlinpoet.collections.KdocCollection
import com.hendraanggrian.kotlinpoet.collections.KdocCollectionScope
import com.hendraanggrian.kotlinpoet.collections.PropertySpecCollection
import com.hendraanggrian.kotlinpoet.collections.PropertySpecCollectionScope
import com.hendraanggrian.kotlinpoet.collections.TypeNameCollection
import com.hendraanggrian.kotlinpoet.collections.TypeSpecCollection
import com.hendraanggrian.kotlinpoet.collections.TypeSpecCollectionScope
import com.hendraanggrian.kotlinpoet.collections.TypeVariableNameCollection
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeSpec
import java.lang.reflect.Type
import javax.lang.model.element.Element
import kotlin.reflect.KClass

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
@SpecMarker
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
    val kdoc: KdocCollection = object : KdocCollection() {
        override fun append(format: String, vararg args: Any) {
            nativeBuilder.addKdoc(format, *args)
        }

        override fun append(code: CodeBlock) {
            nativeBuilder.addKdoc(code)
        }
    }

    /** Configures kdoc of this type. */
    fun kdoc(configuration: KdocCollectionScope.() -> Unit): Unit = KdocCollectionScope(kdoc).configuration()

    /** Annotations of this type. */
    val annotations: AnnotationSpecCollection = AnnotationSpecCollection(nativeBuilder.annotationSpecs)

    /** Configures annotations of this type. */
    fun annotations(configuration: AnnotationSpecCollectionScope.() -> Unit): Unit =
        AnnotationSpecCollectionScope(annotations).configuration()

    /** Add type modifiers. */
    fun addModifiers(vararg modifiers: KModifier) {
        nativeBuilder.addModifiers(*modifiers)
    }

    /** Type variables of this type. */
    val typeVariables: TypeVariableNameCollection = TypeVariableNameCollection(nativeBuilder.typeVariables)

    /** Configures type variables of this type. */
    fun typeVariables(configuration: TypeVariableNameCollection.() -> Unit): Unit = typeVariables.configuration()

    /** Set primary constructor to type. */
    var primaryConstructor: FunSpec?
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            nativeBuilder.primaryConstructor(value)
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

    /** Super interfaces of this type. */
    val superinterfaces: TypeNameCollection get() = TypeNameCollection(nativeBuilder.superinterfaces)

    /** Configures super interfaces of this type. */
    fun superinterfaces(configuration: TypeNameCollection.() -> Unit): Unit = superinterfaces.configuration()

    /** Add enum constant with name. */
    fun addEnumConstant(name: String) {
        nativeBuilder.addEnumConstant(name)
    }

    /** Add enum constant with name and specified [TypeSpec]. */
    fun addEnumConstant(name: String, spec: TypeSpec) {
        nativeBuilder.addEnumConstant(name, spec)
    }

    /** Properties of this type. */
    val properties: PropertySpecCollection = PropertySpecCollection(nativeBuilder.propertySpecs)

    /** Configures properties of this type. */
    fun properties(configuration: PropertySpecCollectionScope.() -> Unit): Unit =
        PropertySpecCollectionScope(properties).configuration()

    /** Add initializer block containing [code]. */
    fun addInitializerBlock(code: CodeBlock) {
        nativeBuilder.addInitializerBlock(code)
    }

    /** Functions of this type. */
    val functions: FunSpecCollection = FunSpecCollection(nativeBuilder.funSpecs)

    /** Configures functions of this type. */
    fun functions(configuration: FunSpecCollectionScope.() -> Unit): Unit =
        FunSpecCollectionScope(functions).configuration()

    /** Types of this type. */
    val types: TypeSpecCollection = TypeSpecCollection(nativeBuilder.typeSpecs)

    /** Configures types of this type. */
    fun types(configuration: TypeSpecCollectionScope.() -> Unit): Unit =
        TypeSpecCollectionScope(types).configuration()

    /** Returns native spec. */
    fun build(): TypeSpec = nativeBuilder.build()
}
