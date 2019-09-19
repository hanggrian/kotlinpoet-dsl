package com.hendraanggrian.kotlinpoet

import com.hendraanggrian.kotlinpoet.dsl.AnnotationContainer
import com.hendraanggrian.kotlinpoet.dsl.CodeCollection
import com.hendraanggrian.kotlinpoet.dsl.KdocContainer
import com.hendraanggrian.kotlinpoet.dsl.ParameterContainer
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeVariableName
import kotlin.reflect.KClass

/** Builds a new [FunSpec] from [name]. */
fun buildFunction(name: String): FunSpec =
    FunSpecBuilder(FunSpec.builder(name)).build()

/**
 * Builds a new [FunSpec] from [name],
 * by populating newly created [FunSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildFunction(name: String, builderAction: FunSpecBuilder.() -> Unit): FunSpec =
    FunSpecBuilder(FunSpec.builder(name)).apply(builderAction).build()

/** Builds a new constructor [FunSpec]. */
fun buildConstructorFunction(): FunSpec =
    FunSpecBuilder(FunSpec.constructorBuilder()).build()

/**
 * Builds a new constructor [FunSpec],
 * by populating newly created [FunSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildConstructorFunction(builderAction: FunSpecBuilder.() -> Unit): FunSpec =
    FunSpecBuilder(FunSpec.constructorBuilder()).apply(builderAction).build()

/** Wrapper of [FunSpec.Builder], providing DSL support as a replacement to Java builder. */
@KotlinpoetDslMarker
class FunSpecBuilder @PublishedApi internal constructor(private val nativeBuilder: FunSpec.Builder) :
    CodeCollection() {

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

    /** Add field modifiers. */
    fun addModifiers(modifiers: Iterable<KModifier>) {
        nativeBuilder.addModifiers(modifiers)
    }

    /** Add type variables. */
    fun addTypeVariables(typeVariables: Iterable<TypeVariableName>) {
        nativeBuilder.addTypeVariables(typeVariables)
    }

    /** Add type variables. */
    fun addTypeVariable(typeVariable: TypeVariableName) {
        nativeBuilder.addTypeVariable(typeVariable)
    }

    /** Add return line to type name. */
    var returns: TypeName
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            nativeBuilder.returns(value)
        }

    /** Add return line to [type]. */
    fun returns(type: KClass<*>) {
        nativeBuilder.returns(type.java)
    }

    /** Add return line to [T]. */
    inline fun <reified T> returns() =
        returns(T::class)

    /** Collection of parameters, may be configured with Kotlin DSL. */
    val parameters: ParameterContainer = object : ParameterContainer() {
        override fun add(spec: ParameterSpec): ParameterSpec =
            spec.also { nativeBuilder.addParameter(it) }
    }

    override fun append(format: String, vararg args: Any) {
        nativeBuilder.addCode(format, *args)
    }

    override fun append(code: CodeBlock): CodeBlock =
        code.also { nativeBuilder.addCode(it) }

    override fun beginFlow(flow: String, vararg args: Any) {
        nativeBuilder.beginControlFlow(flow, *args)
    }

    override fun nextFlow(flow: String, vararg args: Any) {
        nativeBuilder.nextControlFlow(flow, *args)
    }

    override fun endFlow() {
        nativeBuilder.endControlFlow()
    }

    override fun appendln(format: String, vararg args: Any) {
        nativeBuilder.addStatement(format, *args)
    }

    /** Add named code. */
    fun addNamedCode(format: String, args: Map<String, *>) {
        nativeBuilder.addNamedCode(format, args)
    }

    /** Add comment like [String.format]. */
    fun addComment(format: String, vararg args: Any) {
        nativeBuilder.addComment(format, *args)
    }

    /** Returns native spec. */
    fun build(): FunSpec =
        nativeBuilder.build()
}
