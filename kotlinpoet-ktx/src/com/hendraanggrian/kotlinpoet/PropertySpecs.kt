package com.hendraanggrian.kotlinpoet

import com.hendraanggrian.kotlinpoet.dsl.AnnotationContainer
import com.hendraanggrian.kotlinpoet.dsl.KdocContainer
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeVariableName
import java.lang.reflect.Type
import javax.lang.model.element.Element
import kotlin.reflect.KClass

/** Builds a new [PropertySpec] from [type] supplying its [name] and [modifiers]. */
fun buildProperty(name: String, type: TypeName, vararg modifiers: KModifier): PropertySpec =
    PropertySpec.builder(name, type, *modifiers).build()

/**
 * Builds a new [PropertySpec] from [type] supplying its [name] and [modifiers],
 * by populating newly created [PropertySpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildProperty(
    name: String,
    type: TypeName,
    vararg modifiers: KModifier,
    builderAction: PropertySpecBuilder.() -> Unit
): PropertySpec = PropertySpecBuilder(PropertySpec.builder(name, type, *modifiers)).apply(builderAction).build()

/** Builds a new [PropertySpec] from [type] supplying its [name] and [modifiers]. */
fun buildProperty(name: String, type: Type, vararg modifiers: KModifier): PropertySpec =
    PropertySpec.builder(name, type, *modifiers).build()

/** Builds a new [PropertySpec] from [type] supplying its [name] and [modifiers]. */
fun buildProperty(name: String, type: KClass<*>, vararg modifiers: KModifier): PropertySpec =
    buildProperty(name, type.java, *modifiers)

/** Builds a new [PropertySpec] from [T] supplying its [name] and [modifiers]. */
inline fun <reified T> buildProperty(name: String, vararg modifiers: KModifier): PropertySpec =
    buildProperty(name, T::class, *modifiers)

/**
 * Builds a new [PropertySpec] from [type] supplying its [name] and [modifiers],
 * by populating newly created [PropertySpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildProperty(
    name: String,
    type: Type,
    vararg modifiers: KModifier,
    builderAction: PropertySpecBuilder.() -> Unit
): PropertySpec = PropertySpecBuilder(PropertySpec.builder(name, type, *modifiers)).apply(builderAction).build()

/**
 * Builds a new [PropertySpec] from [type] supplying its [name] and [modifiers],
 * by populating newly created [PropertySpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildProperty(
    name: String,
    type: KClass<*>,
    vararg modifiers: KModifier,
    builderAction: PropertySpecBuilder.() -> Unit
): PropertySpec = buildProperty(name, type.java, *modifiers, builderAction = builderAction)

/**
 * Builds a new [PropertySpec] from [T] supplying its [name] and [modifiers],
 * by populating newly created [PropertySpecBuilder] using provided [builderAction] and then building it.
 */
inline fun <reified T> buildProperty(
    name: String,
    vararg modifiers: KModifier,
    builderAction: PropertySpecBuilder.() -> Unit
): PropertySpec = buildProperty(name, T::class, *modifiers, builderAction = builderAction)

/** Wrapper of [PropertySpec.Builder], providing DSL support as a replacement to Java builder. */
@KotlinpoetDslMarker
class PropertySpecBuilder @PublishedApi internal constructor(private val nativeBuilder: PropertySpec.Builder) {

    /** Annotations of this builder. */
    val annotationSpecs: MutableList<AnnotationSpec>
        get() = nativeBuilder.annotations

    /** Modifiers of this builder. */
    val modifiers: MutableList<KModifier>
        get() = nativeBuilder.modifiers

    /** Type variables of this builder. */
    val typeVariables: MutableList<TypeVariableName>
        get() = nativeBuilder.typeVariables

    /** Tags variables of this builder. */
    val tags: MutableMap<KClass<*>, *>
        get() = nativeBuilder.tags

    /** Originating elements of this builder. */
    val originatingElements: MutableList<Element>
        get() = nativeBuilder.originatingElements

    /** True to create a `var` instead of a `val`. */
    var isMutable: Boolean
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            nativeBuilder.mutable(value)
        }

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
    fun addTypeVariables(typeVariables: Iterable<TypeVariableName>) {
        nativeBuilder.addTypeVariables(typeVariables)
    }

    /** Add type variables. */
    fun addTypeVariable(typeVariable: TypeVariableName) {
        nativeBuilder.addTypeVariable(typeVariable)
    }

    /** Initialize field value like [String.format]. */
    fun initializer(format: String, vararg args: Any) {
        nativeBuilder.initializer(format, *args)
    }

    /** Initialize field value with simple string. */
    inline var initializer: String
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) = initializer(value)

    /** Initialize field value with code. */
    fun initializer(code: CodeBlock): CodeBlock =
        code.also { nativeBuilder.initializer(it) }

    /** Initialize field value with custom initialization [builderAction]. */
    inline fun initializer(builderAction: CodeBlockBuilder.() -> Unit): CodeBlock =
        initializer(buildCode(builderAction))

    /** Delegate field value like [String.format]. */
    fun delegate(format: String, vararg args: Any) {
        nativeBuilder.delegate(format, *args)
    }

    /** Delegate field value with simple string. */
    inline var delegate: String
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) = delegate(value)

    /** Delegate field value with code. */
    fun delegate(code: CodeBlock): CodeBlock =
        code.also { nativeBuilder.delegate(it) }

    /** Delegate field value with custom initialization [builderAction]. */
    inline fun delegate(builderAction: CodeBlockBuilder.() -> Unit): CodeBlock =
        delegate(buildCode(builderAction))

    /** Set getter function from [spec]. */
    fun getter(spec: FunSpec): FunSpec =
        spec.also { nativeBuilder.getter(it) }

    /** Set getter function, returning the function added. */
    fun getter(): FunSpec =
        getter(buildGetterFunction())

    /** Set getter function with custom initialization [builderAction], returning the function added. */
    inline fun getter(builderAction: FunSpecBuilder.() -> Unit): FunSpec =
        getter(buildGetterFunction(builderAction))

    /** Set setter function from [spec]. */
    fun setter(spec: FunSpec): FunSpec =
        spec.also { nativeBuilder.setter(it) }

    /** Set setter function, returning the function added. */
    fun setter(): FunSpec =
        setter(buildSetterFunction())

    /** Set setter function with custom initialization [builderAction], returning the function added. */
    inline fun setter(builderAction: FunSpecBuilder.() -> Unit): FunSpec =
        setter(buildSetterFunction(builderAction))

    /** Set receiver to type. */
    var receiver: TypeName
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            nativeBuilder.receiver(value)
        }

    /** Set receiver to [type]. */
    fun receiver(type: Type) {
        nativeBuilder.receiver(type)
    }

    /** Set receiver to [type]. */
    fun receiver(type: KClass<*>) =
        receiver(type.java)

    /** Set receiver to [T]. */
    inline fun <reified T> receiver() =
        receiver(T::class)

    /** Returns native spec. */
    fun build(): PropertySpec =
        nativeBuilder.build()
}
