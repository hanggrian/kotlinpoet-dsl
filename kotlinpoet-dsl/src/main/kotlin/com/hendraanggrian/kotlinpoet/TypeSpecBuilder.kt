@file:OptIn(ExperimentalContracts::class)

package com.hendraanggrian.kotlinpoet

import com.hendraanggrian.kotlinpoet.collections.AnnotationSpecList
import com.hendraanggrian.kotlinpoet.collections.AnnotationSpecListScope
import com.hendraanggrian.kotlinpoet.collections.EnumConstantMap
import com.hendraanggrian.kotlinpoet.collections.EnumConstantMapScope
import com.hendraanggrian.kotlinpoet.collections.FunSpecList
import com.hendraanggrian.kotlinpoet.collections.FunSpecListScope
import com.hendraanggrian.kotlinpoet.collections.KdocContainer
import com.hendraanggrian.kotlinpoet.collections.KdocContainerScope
import com.hendraanggrian.kotlinpoet.collections.PropertySpecList
import com.hendraanggrian.kotlinpoet.collections.PropertySpecListScope
import com.hendraanggrian.kotlinpoet.collections.TypeNameMap
import com.hendraanggrian.kotlinpoet.collections.TypeSpecList
import com.hendraanggrian.kotlinpoet.collections.TypeSpecListScope
import com.hendraanggrian.kotlinpoet.collections.TypeVariableNameCollection
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.DelicateKotlinPoetApi
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeSpec
import java.lang.reflect.Type
import javax.lang.model.element.Element
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.reflect.KClass

/**
 * Builds new class [TypeSpec] from name, by populating newly created [TypeSpecBuilder] using
 * provided [configuration].
 */
inline fun buildClassTypeSpec(type: String, configuration: TypeSpecBuilder.() -> Unit): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.classBuilder(type)).apply(configuration).build()
}

/**
 * Builds new class [TypeSpec] from [ClassName], by populating newly created [TypeSpecBuilder] using
 * provided [configuration].
 */
inline fun buildClassTypeSpec(
    type: ClassName,
    configuration: TypeSpecBuilder.() -> Unit
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.classBuilder(type)).apply(configuration).build()
}

/**
 * Builds new expect class [TypeSpec] from name, by populating newly created [TypeSpecBuilder] using
 * provided [configuration].
 */
inline fun buildExpectClassTypeSpec(
    type: String,
    configuration: TypeSpecBuilder.() -> Unit
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.expectClassBuilder(type)).apply(configuration).build()
}

/**
 * Builds new expect class [TypeSpec] from [ClassName], by populating newly
 * created [TypeSpecBuilder] using provided [configuration].
 */
inline fun buildExpectClassTypeSpec(
    type: ClassName,
    configuration: TypeSpecBuilder.() -> Unit
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.expectClassBuilder(type)).apply(configuration).build()
}

/**
 * Builds new object [TypeSpec] from name, by populating newly created [TypeSpecBuilder] using
 * provided [configuration].
 */
inline fun buildObjectTypeSpec(type: String, configuration: TypeSpecBuilder.() -> Unit): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.objectBuilder(type)).apply(configuration).build()
}

/**
 * Builds new object [TypeSpec] from [ClassName], by populating newly created [TypeSpecBuilder]
 * using provided [configuration].
 */
inline fun buildObjectTypeSpec(
    type: ClassName,
    configuration: TypeSpecBuilder.() -> Unit
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.objectBuilder(type)).apply(configuration).build()
}

/**
 * Builds new object [TypeSpec] from name, by populating newly created [TypeSpecBuilder] using
 * provided [configuration].
 */
inline fun buildCompanionObjectTypeSpec(
    type: String? = null,
    configuration: TypeSpecBuilder.() -> Unit
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.companionObjectBuilder(type)).apply(configuration).build()
}

/**
 * Builds new interface [TypeSpec] from name, by populating newly created [TypeSpecBuilder] using
 * provided [configuration].
 */
inline fun buildInterfaceTypeSpec(
    type: String,
    configuration: TypeSpecBuilder.() -> Unit
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.interfaceBuilder(type)).apply(configuration).build()
}

/**
 * Builds new interface [TypeSpec] from [ClassName], by populating newly created [TypeSpecBuilder]
 * using provided [configuration].
 */
inline fun buildInterfaceTypeSpec(
    type: ClassName,
    configuration: TypeSpecBuilder.() -> Unit
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.interfaceBuilder(type)).apply(configuration).build()
}

/**
 * Builds new enum [TypeSpec] from name, by populating newly created [TypeSpecBuilder] using
 * provided [configuration].
 */
inline fun buildEnumTypeSpec(type: String, configuration: TypeSpecBuilder.() -> Unit): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.enumBuilder(type)).apply(configuration).build()
}

/**
 * Builds new enum [TypeSpec] from [ClassName], by populating newly created [TypeSpecBuilder] using
 * provided [configuration].
 */
inline fun buildEnumTypeSpec(type: ClassName, configuration: TypeSpecBuilder.() -> Unit): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.enumBuilder(type)).apply(configuration).build()
}

/**
 * Builds new anonymous [TypeSpec], by populating newly created [TypeSpecBuilder] using
 * provided [configuration].
 */
inline fun buildAnonymousTypeSpec(configuration: TypeSpecBuilder.() -> Unit): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.anonymousClassBuilder()).apply(configuration).build()
}

/**
 * Builds new annotation [TypeSpec] from name, by populating newly created [TypeSpecBuilder] using
 * provided [configuration].
 */
inline fun buildAnnotationTypeSpec(
    type: String,
    configuration: TypeSpecBuilder.() -> Unit
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.annotationBuilder(type)).apply(configuration).build()
}

/**
 * Builds new annotation [TypeSpec] from [ClassName], by populating newly created [TypeSpecBuilder]
 * using provided [configuration].
 */
inline fun buildAnnotationTypeSpec(
    type: ClassName,
    configuration: TypeSpecBuilder.() -> Unit
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.annotationBuilder(type)).apply(configuration).build()
}

/**
 * Property delegate for building new class [TypeSpec] from [ClassName], by populating newly
 * created [TypeSpecBuilder] using provided [configuration].
 */
fun buildingClassTypeSpec(configuration: TypeSpecBuilder.() -> Unit): SpecLoader<TypeSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return createSpecLoader { buildClassTypeSpec(it, configuration) }
}

/**
 * Property delegate for building new expect class [TypeSpec] from [ClassName], by populating newly
 * created [TypeSpecBuilder] using provided [configuration].
 */
fun buildingExpectClassTypeSpec(configuration: TypeSpecBuilder.() -> Unit): SpecLoader<TypeSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return createSpecLoader { buildExpectClassTypeSpec(it, configuration) }
}

/**
 * Property delegate for building new object [TypeSpec] from [ClassName], by populating newly
 * created [TypeSpecBuilder] using provided [configuration].
 */
fun buildingObjectTypeSpec(configuration: TypeSpecBuilder.() -> Unit): SpecLoader<TypeSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return createSpecLoader { buildObjectTypeSpec(it, configuration) }
}

/**
 * Property delegate for building new companion object [TypeSpec] from [ClassName], by populating
 * newly created [TypeSpecBuilder] using provided [configuration].
 */
fun buildingCompanionObjectTypeSpec(
    configuration: TypeSpecBuilder.() -> Unit
): SpecLoader<TypeSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return createSpecLoader { buildCompanionObjectTypeSpec(it, configuration) }
}

/**
 * Property delegate for building new interface [TypeSpec] from [ClassName], by populating newly
 * created [TypeSpecBuilder] using provided [configuration].
 */
fun buildingInterfaceTypeSpec(configuration: TypeSpecBuilder.() -> Unit): SpecLoader<TypeSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return createSpecLoader { buildInterfaceTypeSpec(it, configuration) }
}

/**
 * Property delegate for building new enum [TypeSpec] from [ClassName], by populating newly
 * created [TypeSpecBuilder] using provided [configuration].
 */
fun buildingEnumTypeSpec(configuration: TypeSpecBuilder.() -> Unit): SpecLoader<TypeSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return createSpecLoader { buildEnumTypeSpec(it, configuration) }
}

/**
 * Property delegate for building new annotation [TypeSpec] from [ClassName], by populating newly
 * created [TypeSpecBuilder] using provided [configuration].
 */
fun buildingAnnotationTypeSpec(configuration: TypeSpecBuilder.() -> Unit): SpecLoader<TypeSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return createSpecLoader { buildAnnotationTypeSpec(it, configuration) }
}

/**
 * Wrapper of [TypeSpec.Builder], providing DSL support as a replacement to Java builder.
 *
 * @param nativeBuilder source builder.
 */
@KotlinpoetSpecDsl
class TypeSpecBuilder(private val nativeBuilder: TypeSpec.Builder) {
    var initializerIndex: Int
        get() = nativeBuilder.initializerIndex
        set(value) {
            nativeBuilder.initializerIndex = value
        }
    val tags: MutableMap<KClass<*>, *> get() = nativeBuilder.tags
    val originatingElements: MutableList<Element> get() = nativeBuilder.originatingElements
    val modifiers: MutableSet<KModifier> get() = nativeBuilder.modifiers
    val superclassConstructorParameters: MutableList<CodeBlock>
        get() = nativeBuilder.superclassConstructorParameters

    /** Kdoc of this type. */
    val kdoc: KdocContainer = object : KdocContainer {
        override fun append(format: String, vararg args: Any) {
            nativeBuilder.addKdoc(format, *args)
        }

        override fun append(code: CodeBlock) {
            nativeBuilder.addKdoc(code)
        }
    }

    /** Configures kdoc of this type. */
    fun kdoc(configuration: KdocContainerScope.() -> Unit) {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        KdocContainerScope(kdoc).configuration()
    }

    /** Annotations of this type. */
    val annotations: AnnotationSpecList = AnnotationSpecList(nativeBuilder.annotationSpecs)

    /** Configures annotations of this type. */
    fun annotations(configuration: AnnotationSpecListScope.() -> Unit) {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        AnnotationSpecListScope(annotations).configuration()
    }

    /** Add type modifiers. */
    fun addModifiers(vararg modifiers: KModifier) {
        nativeBuilder.addModifiers(*modifiers)
    }

    /** Type variables of this type. */
    val typeVariables: TypeVariableNameCollection =
        TypeVariableNameCollection(nativeBuilder.typeVariables)

    /** Configures type variables of this type. */
    fun typeVariables(configuration: TypeVariableNameCollection.() -> Unit) {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        typeVariables.configuration()
    }

    /** Set primary constructor to type. */
    var primaryConstructor: FunSpec?
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
        get() = noGetter()
        set(value) {
            nativeBuilder.primaryConstructor(value)
        }

    /** Set primary constructor to type with [configuration]. */
    fun primaryConstructor(configuration: FunSpecBuilder.() -> Unit) {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        primaryConstructor = buildConstructorFunSpec(configuration)
    }

    /** Set superclass to type. */
    var superclass: TypeName
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
        get() = noGetter()
        set(value) {
            nativeBuilder.superclass(value)
        }

    /** Set superclass to [type]. */
    @DelicateKotlinPoetApi(DELICATE_JAVA)
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
    val superinterfaces: TypeNameMap = TypeNameMap(nativeBuilder.superinterfaces)

    /** Configures super interfaces of this type. */
    fun superinterfaces(configuration: TypeNameMap.() -> Unit) {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        superinterfaces.configuration()
    }

    /** Enum constants of this type. */
    val enumConstants: EnumConstantMap = EnumConstantMap(nativeBuilder.enumConstants)

    /** Configures enum constants of this type. */
    fun enumConstants(configuration: EnumConstantMapScope.() -> Unit) {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        EnumConstantMapScope(enumConstants).configuration()
    }

    /** Properties of this type. */
    val properties: PropertySpecList = PropertySpecList(nativeBuilder.propertySpecs)

    /** Configures properties of this type. */
    fun properties(configuration: PropertySpecListScope.() -> Unit) {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        PropertySpecListScope(properties).configuration()
    }

    /** Add initializer block from [CodeBlock]. */
    fun addInitializerBlock(code: CodeBlock) {
        nativeBuilder.addInitializerBlock(code)
    }

    /** Add initializer block from formatted string. */
    fun addInitializerBlock(format: String, vararg args: Any) {
        nativeBuilder.addInitializerBlock(codeBlockOf(format, *args))
    }

    /** Functions of this type. */
    val functions: FunSpecList = FunSpecList(nativeBuilder.funSpecs)

    /** Configures functions of this type. */
    fun functions(configuration: FunSpecListScope.() -> Unit) {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        FunSpecListScope(functions).configuration()
    }

    /** Types of this type. */
    val types: TypeSpecList = TypeSpecList(nativeBuilder.typeSpecs)

    /** Configures types of this type. */
    fun types(configuration: TypeSpecListScope.() -> Unit) {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        TypeSpecListScope(types).configuration()
    }

    /** Returns native spec. */
    fun build(): TypeSpec = nativeBuilder.build()
}
