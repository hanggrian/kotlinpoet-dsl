package com.hendraanggrian.kotlinpoet

import com.hendraanggrian.kotlinpoet.collections.AnnotationSpecList
import com.hendraanggrian.kotlinpoet.collections.AnnotationSpecListScope
import com.hendraanggrian.kotlinpoet.collections.CodeBlockContainer
import com.hendraanggrian.kotlinpoet.collections.KdocContainer
import com.hendraanggrian.kotlinpoet.collections.KdocContainerScope
import com.hendraanggrian.kotlinpoet.collections.ParameterSpecList
import com.hendraanggrian.kotlinpoet.collections.ParameterSpecListScope
import com.hendraanggrian.kotlinpoet.collections.TypeVariableNameList
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeName
import java.lang.reflect.Type
import javax.lang.model.element.Element
import kotlin.reflect.KClass

/** Builds a new [FunSpec] from [name]. */
fun funSpecOf(name: String): FunSpec =
    FunSpecBuilder(FunSpec.builder(name)).build()

/**
 * Builds a new [FunSpec] from [name],
 * by populating newly created [FunSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildFunSpec(name: String, builderAction: FunSpecBuilder.() -> Unit): FunSpec =
    FunSpec.builder(name).build(builderAction)

/** Builds a new constructor [FunSpec]. */
fun constructorFunSpecOf(): FunSpec =
    FunSpecBuilder(FunSpec.constructorBuilder()).build()

/**
 * Builds a new constructor [FunSpec],
 * by populating newly created [FunSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildConstructorFunSpec(builderAction: FunSpecBuilder.() -> Unit): FunSpec =
    FunSpecBuilder(FunSpec.constructorBuilder()).apply(builderAction).build()

/** Builds a new getter [FunSpec]. */
fun getterFunSpecOf(): FunSpec =
    FunSpecBuilder(FunSpec.getterBuilder()).build()

/**
 * Builds a new getter [FunSpec],
 * by populating newly created [FunSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildGetterFunSpec(builderAction: FunSpecBuilder.() -> Unit): FunSpec =
    FunSpec.getterBuilder().build(builderAction)

/** Builds a new setter [FunSpec]. */
fun setterFunSpecOf(): FunSpec =
    FunSpecBuilder(FunSpec.setterBuilder()).build()

/**
 * Builds a new setter [FunSpec],
 * by populating newly created [FunSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildSetterFunSpec(builderAction: FunSpecBuilder.() -> Unit): FunSpec =
    FunSpec.setterBuilder().build(builderAction)

/** Modify existing [FunSpec.Builder] using provided [builderAction] and then building it. */
inline fun FunSpec.Builder.build(builderAction: FunSpecBuilder.() -> Unit): FunSpec =
    FunSpecBuilder(this).apply(builderAction).build()

/** Wrapper of [FunSpec.Builder], providing DSL support as a replacement to Java builder. */
@KotlinpoetDslMarker
class FunSpecBuilder(private val nativeBuilder: FunSpec.Builder) :
    CodeBlockContainer() {

    /** Modifiers of this function. */
    val modifiers: MutableList<KModifier> get() = nativeBuilder.modifiers

    /** Tags variables of this function. */
    val tags: MutableMap<KClass<*>, *> get() = nativeBuilder.tags

    /** Originating elements of this function. */
    val originatingElements: MutableList<Element> get() = nativeBuilder.originatingElements

    /** Kdoc of this function. */
    val kdoc: KdocContainer = object : KdocContainer() {
        override fun append(format: String, vararg args: Any) {
            nativeBuilder.addKdoc(format, *args)
        }

        override fun append(code: CodeBlock) {
            nativeBuilder.addKdoc(code)
        }
    }

    /** Configures kdoc of this function. */
    inline fun kdoc(configuration: KdocContainerScope.() -> Unit) =
        KdocContainerScope(kdoc).configuration()

    /** Annotations of this function. */
    val annotations: AnnotationSpecList = AnnotationSpecList(nativeBuilder.annotations)

    /** Configures annotations of this function. */
    inline fun annotations(configuration: AnnotationSpecListScope.() -> Unit) =
        AnnotationSpecListScope(annotations).configuration()

    /** Add function modifiers. */
    fun addModifiers(vararg modifiers: KModifier) {
        nativeBuilder.addModifiers(*modifiers)
    }

    /** Add function modifiers. */
    fun addModifiers(modifiers: Iterable<KModifier>) {
        nativeBuilder.addModifiers(modifiers)
    }

    /** Type variables of this function. */
    val typeVariables: TypeVariableNameList = TypeVariableNameList(nativeBuilder.typeVariables)

    /** Set receiver to type. */
    var receiver: TypeName
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            nativeBuilder.receiver(value)
        }

    /** Set receiver [type] with [code] as kdoc. */
    fun receiver(type: TypeName, code: CodeBlock) {
        nativeBuilder.receiver(type, code)
    }

    /** Set receiver [type] with custom initialization builder as kdoc. */
    inline fun receiver(type: TypeName, builderAction: CodeBlockBuilder.() -> Unit) =
        receiver(type, buildCodeBlock(builderAction))

    /** Set receiver [type] without kdoc. */
    fun receiver(type: Type) {
        nativeBuilder.receiver(type)
    }

    /** Set receiver [type] with kdoc initialized like [String.format]. */
    fun receiver(type: Type, format: String, vararg args: Any) {
        nativeBuilder.receiver(type, format, *args)
    }

    /** Set receiver [type] with [code] as kdoc. */
    fun receiver(type: Type, code: CodeBlock) {
        nativeBuilder.receiver(type, code)
    }

    /** Set receiver [type] with custom initialization builder as kdoc. */
    inline fun receiver(type: Type, builderAction: CodeBlockBuilder.() -> Unit) =
        receiver(type, buildCodeBlock(builderAction))

    /** Set receiver [type] without kdoc. */
    fun receiver(type: KClass<*>) {
        nativeBuilder.receiver(type)
    }

    /** Set receiver [type] with kdoc initialized like [String.format]. */
    fun receiver(type: KClass<*>, format: String, vararg args: Any) {
        nativeBuilder.receiver(type, format, *args)
    }

    /** Set receiver [type] with [code] as kdoc. */
    fun receiver(type: KClass<*>, code: CodeBlock) {
        nativeBuilder.receiver(type, code)
    }

    /** Set receiver [type] with custom initialization builder as kdoc. */
    inline fun receiver(type: KClass<*>, builderAction: CodeBlockBuilder.() -> Unit) =
        receiver(type, buildCodeBlock(builderAction))

    /** Set receiver [T] without kdoc. */
    inline fun <reified T> receiver() = receiver(T::class)

    /** Set receiver [T] with kdoc initialized like [String.format]. */
    inline fun <reified T> receiver(format: String, vararg args: Any) = receiver(T::class, format, *args)

    /** Set receiver [T] with [code] as kdoc. */
    inline fun <reified T> receiver(code: CodeBlock) = receiver(T::class, code)

    /** Set receiver [T] with custom initialization builder as kdoc. */
    inline fun <reified T> receiver(builderAction: CodeBlockBuilder.() -> Unit) =
        receiver(T::class, buildCodeBlock(builderAction))

    /** Add return line to type name. */
    var returns: TypeName
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            nativeBuilder.returns(value)
        }

    /** Add return line to [type]. */
    fun returns(type: Type) {
        nativeBuilder.returns(type)
    }

    /** Add return line to [type]. */
    fun returns(type: KClass<*>) {
        nativeBuilder.returns(type)
    }

    /** Add return line to [T]. */
    inline fun <reified T> returns() = returns(T::class)

    /** Parameters of this function. */
    val parameters: ParameterSpecList = ParameterSpecList(nativeBuilder.parameters)

    /** Configures parameters of this function. */
    inline fun parameters(configuration: ParameterSpecListScope.() -> Unit) =
        ParameterSpecListScope(parameters).configuration()

    /** Call this constructor with [String] arguments. */
    fun callThisConstructor(vararg args: String) {
        nativeBuilder.callThisConstructor(*args)
    }

    /** Call this constructor with [CodeBlock] arguments. */
    fun callThisConstructor(vararg args: CodeBlock) {
        nativeBuilder.callThisConstructor(*args)
    }

    /** Call this constructor with code [builderAction]. */
    inline fun callThisConstructor(builderAction: CodeBlockBuilder.() -> Unit) =
        callThisConstructor(buildCodeBlock(builderAction))

    /** Call super constructor with [String] arguments. */
    fun callSuperConstructor(vararg args: String) {
        nativeBuilder.callSuperConstructor(*args)
    }

    /** Call super constructor with [CodeBlock] arguments. */
    fun callSuperConstructor(vararg args: CodeBlock) {
        nativeBuilder.callSuperConstructor(*args)
    }

    /** Call super constructor with code [builderAction]. */
    inline fun callSuperConstructor(builderAction: CodeBlockBuilder.() -> Unit) =
        callSuperConstructor(buildCodeBlock(builderAction))

    override fun append(format: String, vararg args: Any) {
        nativeBuilder.addCode(format, *args)
    }

    override fun append(code: CodeBlock) {
        nativeBuilder.addCode(code)
    }

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
    fun build(): FunSpec = nativeBuilder.build()
}
