@file:OptIn(ExperimentalContracts::class)

package com.hendraanggrian.kotlinpoet

import com.hendraanggrian.kotlinpoet.collections.AnnotationSpecList
import com.hendraanggrian.kotlinpoet.collections.AnnotationSpecListScope
import com.hendraanggrian.kotlinpoet.collections.KdocContainer
import com.hendraanggrian.kotlinpoet.collections.KdocContainerScope
import com.hendraanggrian.kotlinpoet.collections.TypeVariableNameCollection
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.DelicateKotlinPoetApi
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeName
import java.lang.reflect.Type
import javax.lang.model.element.Element
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.reflect.KClass

/**
 * Builds new [PropertySpec] from [TypeName] supplying its name and modifiers,
 * by populating newly created [PropertySpecBuilder] using provided [configuration].
 */
inline fun buildPropertySpec(
    name: String,
    type: TypeName,
    vararg modifiers: KModifier,
    configuration: PropertySpecBuilder.() -> Unit
): PropertySpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return PropertySpecBuilder(PropertySpec.builder(name, type, *modifiers)).apply(configuration).build()
}

/**
 * Builds new [PropertySpec] from [Type] supplying its name and modifiers,
 * by populating newly created [PropertySpecBuilder] using provided [configuration].
 */
inline fun buildPropertySpec(
    name: String,
    type: Type,
    vararg modifiers: KModifier,
    configuration: PropertySpecBuilder.() -> Unit
): PropertySpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return PropertySpecBuilder(PropertySpec.builder(name, type, *modifiers)).apply(configuration).build()
}

/**
 * Builds new [PropertySpec] from [KClass] supplying its name and modifiers,
 * by populating newly created [PropertySpecBuilder] using provided [configuration].
 */
inline fun buildPropertySpec(
    name: String,
    type: KClass<*>,
    vararg modifiers: KModifier,
    configuration: PropertySpecBuilder.() -> Unit
): PropertySpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return PropertySpecBuilder(PropertySpec.builder(name, type, *modifiers)).apply(configuration).build()
}

/**
 * Builds new [PropertySpec] from [T] supplying its name and modifiers,
 * by populating newly created [PropertySpecBuilder] using provided [configuration].
 */
inline fun <reified T> buildPropertySpec(
    name: String,
    vararg modifiers: KModifier,
    configuration: PropertySpecBuilder.() -> Unit
): PropertySpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return PropertySpecBuilder(PropertySpec.builder(name, T::class, *modifiers)).apply(configuration).build()
}

/**
 * Property delegate for building new [PropertySpec] from [TypeName] supplying its name and modifiers,
 * by populating newly created [PropertySpecBuilder] using provided [configuration].
 */
fun buildingPropertySpec(
    type: TypeName,
    vararg modifiers: KModifier,
    configuration: PropertySpecBuilder.() -> Unit
): SpecLoader<PropertySpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return createSpecLoader { buildPropertySpec(it, type, *modifiers, configuration = configuration) }
}

/**
 * Property delegate for building new [PropertySpec] from [Type] supplying its name and modifiers,
 * by populating newly created [PropertySpecBuilder] using provided [configuration].
 */
fun buildingPropertySpec(
    type: Type,
    vararg modifiers: KModifier,
    configuration: PropertySpecBuilder.() -> Unit
): SpecLoader<PropertySpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return createSpecLoader { buildPropertySpec(it, type, *modifiers, configuration = configuration) }
}

/**
 * Property delegate for building new [PropertySpec] from [KClass] supplying its name and modifiers,
 * by populating newly created [PropertySpecBuilder] using provided [configuration].
 */
fun buildingPropertySpec(
    type: KClass<*>,
    vararg modifiers: KModifier,
    configuration: PropertySpecBuilder.() -> Unit
): SpecLoader<PropertySpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return createSpecLoader { buildPropertySpec(it, type, *modifiers, configuration = configuration) }
}

/**
 * Wrapper of [PropertySpec.Builder], providing DSL support as a replacement to Java builder.
 * @param nativeBuilder source builder.
 */
@KotlinpoetSpecDsl
class PropertySpecBuilder(private val nativeBuilder: PropertySpec.Builder) {
    val modifiers: MutableList<KModifier> get() = nativeBuilder.modifiers
    val tags: MutableMap<KClass<*>, *> get() = nativeBuilder.tags
    val originatingElements: MutableList<Element> get() = nativeBuilder.originatingElements

    /** True to create a `var` instead of a `val`. */
    var isMutable: Boolean
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
        get() = noGetter()
        set(value) {
            nativeBuilder.mutable(value)
        }

    /** Kdoc of this property. */
    val kdoc: KdocContainer = object : KdocContainer {
        override fun append(format: String, vararg args: Any) {
            nativeBuilder.addKdoc(format, *args)
        }

        override fun append(code: CodeBlock) {
            nativeBuilder.addKdoc(code)
        }
    }

    /** Configures kdoc of this property. */
    fun kdoc(configuration: KdocContainerScope.() -> Unit) {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        KdocContainerScope(kdoc).configuration()
    }

    /** Annotations of this property. */
    val annotations: AnnotationSpecList = AnnotationSpecList(nativeBuilder.annotations)

    /** Configures annotations of this property. */
    fun annotations(configuration: AnnotationSpecListScope.() -> Unit) {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        AnnotationSpecListScope(annotations).configuration()
    }

    /** Add property modifiers. */
    fun addModifiers(vararg modifiers: KModifier) {
        nativeBuilder.addModifiers(*modifiers)
    }

    /** Type variables of this property. */
    val typeVariables: TypeVariableNameCollection = TypeVariableNameCollection(nativeBuilder.typeVariables)

    /** Configures type variables of this property. */
    fun typeVariables(configuration: TypeVariableNameCollection.() -> Unit) {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        typeVariables.configuration()
    }

    /** Initialize field value like [String.format]. */
    fun initializer(format: String, vararg args: Any) {
        nativeBuilder.initializer(format, *args)
    }

    /** Initialize field value with simple string. */
    var initializer: CodeBlock
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
        get() = noGetter()
        set(value) {
            nativeBuilder.initializer(value)
        }

    /** Delegate field value like [String.format]. */
    fun delegate(format: String, vararg args: Any) {
        nativeBuilder.delegate(format, *args)
    }

    /** Delegate field value with simple string. */
    var delegate: CodeBlock
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
        get() = noGetter()
        set(value) {
            nativeBuilder.delegate(value)
        }

    /** Set getter function from [FunSpec]. */
    var getter: FunSpec
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
        get() = noGetter()
        set(value) {
            nativeBuilder.getter(value)
        }

    /** Set getter function with custom initialization [configuration]. */
    fun getter(configuration: FunSpecBuilder.() -> Unit) {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        getter = buildGetterFunSpec(configuration)
    }

    /** Set setter function from [FunSpec]. */
    var setter: FunSpec
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
        get() = noGetter()
        set(value) {
            nativeBuilder.setter(value)
        }

    /** Set setter function with custom initialization [configuration]. */
    fun setter(configuration: FunSpecBuilder.() -> Unit) {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        setter = buildSetterFunSpec(configuration)
    }

    /** Set receiver to type. */
    var receiver: TypeName
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
        get() = noGetter()
        set(value) {
            nativeBuilder.receiver(value)
        }

    /** Set receiver to [type]. */
    @DelicateKotlinPoetApi(DELICATE_JAVA)
    fun receiver(type: Type) {
        nativeBuilder.receiver(type)
    }

    /** Set receiver to [type]. */
    fun receiver(type: KClass<*>) {
        nativeBuilder.receiver(type)
    }

    /** Set receiver to [T]. */
    inline fun <reified T> receiver(): Unit = receiver(T::class)

    /** Returns native spec. */
    fun build(): PropertySpec = nativeBuilder.build()
}
