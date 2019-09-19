package com.hendraanggrian.kotlinpoet

import com.hendraanggrian.kotlinpoet.dsl.AnnotationContainer
import com.hendraanggrian.kotlinpoet.dsl.KdocContainer
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeName
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
fun buildProperty(name: String, type: Class<*>, vararg modifiers: KModifier): PropertySpec =
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
    type: Class<*>,
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

    /** Collection of javadoc, may be configured with Kotlin DSL. */
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

    /** Set field value like [String.format]. */
    fun initializer(format: String, vararg args: Any) {
        nativeBuilder.initializer(format, *args)
    }

    /** Set field value to simple string. */
    inline var initializer: String
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) = initializer(value)

    /** Set field value to code. */
    fun initializer(code: CodeBlock): CodeBlock =
        code.also { nativeBuilder.initializer(it) }

    /** Set field value to code with custom initialization [builderAction]. */
    inline fun initializer(builderAction: CodeBlockBuilder.() -> Unit): CodeBlock =
        initializer(buildCode(builderAction))

    /** Returns native spec. */
    fun build(): PropertySpec =
        nativeBuilder.build()
}
