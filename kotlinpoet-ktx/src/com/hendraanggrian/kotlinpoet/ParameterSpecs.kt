package com.hendraanggrian.kotlinpoet

import com.hendraanggrian.kotlinpoet.dsl.AnnotationContainer
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.TypeName
import javax.lang.model.element.VariableElement
import kotlin.reflect.KClass

/** Converts element to [ParameterSpec]. */
fun VariableElement.toParameter(): ParameterSpec =
    ParameterSpec.get(this)

/** Builds a new [ParameterSpec] from [type]. */
fun buildParameter(name: String, type: TypeName, vararg modifiers: KModifier): ParameterSpec =
    ParameterSpec.builder(name, type, *modifiers).build()

/**
 * Builds a new [ParameterSpec] from [type],
 * by populating newly created [ParameterSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildParameter(
    name: String,
    type: TypeName,
    vararg modifiers: KModifier,
    builderAction: ParameterSpecBuilder.() -> Unit
): ParameterSpec = ParameterSpecBuilder(ParameterSpec.builder(name, type, *modifiers)).apply(builderAction).build()

/** Builds a new [ParameterSpec] from [type]. */
fun buildParameter(name: String, type: Class<*>, vararg modifiers: KModifier): ParameterSpec =
    ParameterSpec.builder(name, type, *modifiers).build()

/** Builds a new [ParameterSpec] from [type]. */
fun buildParameter(name: String, type: KClass<*>, vararg modifiers: KModifier): ParameterSpec =
    buildParameter(name, type.java, *modifiers)

/** Builds a new [ParameterSpec] from [T]. */
inline fun <reified T> buildParameter(name: String, vararg modifiers: KModifier): ParameterSpec =
    buildParameter(name, T::class, *modifiers)

/**
 * Builds a new [ParameterSpec] from [type],
 * by populating newly created [ParameterSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildParameter(
    name: String,
    type: Class<*>,
    vararg modifiers: KModifier,
    builderAction: ParameterSpecBuilder.() -> Unit
): ParameterSpec = ParameterSpecBuilder(ParameterSpec.builder(name, type, *modifiers)).apply(builderAction).build()

/**
 * Builds a new [ParameterSpec] from [type],
 * by populating newly created [ParameterSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildParameter(
    name: String,
    type: KClass<*>,
    vararg modifiers: KModifier,
    builderAction: ParameterSpecBuilder.() -> Unit
): ParameterSpec = buildParameter(name, type.java, *modifiers, builderAction = builderAction)

/**
 * Builds a new [ParameterSpec] from [T],
 * by populating newly created [ParameterSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun <reified T> buildParameter(
    name: String,
    vararg modifiers: KModifier,
    builderAction: ParameterSpecBuilder.() -> Unit
): ParameterSpec = buildParameter(name, T::class, *modifiers, builderAction = builderAction)

/** Wrapper of [ParameterSpec.Builder], providing DSL support as a replacement to Java builder. */
@KotlinpoetDslMarker
class ParameterSpecBuilder @PublishedApi internal constructor(private val nativeBuilder: ParameterSpec.Builder) {

    /** Collection of annotations, may be configured with Kotlin DSL. */
    val annotations: AnnotationContainer = object : AnnotationContainer() {
        override fun add(spec: AnnotationSpec): AnnotationSpec =
            spec.also { nativeBuilder.addAnnotation(it) }
    }

    /** Add field modifiers. */
    fun addModifiers(vararg modifiers: KModifier) {
        nativeBuilder.addModifiers(*modifiers)
    }

    /** Returns native spec. */
    fun build(): ParameterSpec =
        nativeBuilder.build()
}
