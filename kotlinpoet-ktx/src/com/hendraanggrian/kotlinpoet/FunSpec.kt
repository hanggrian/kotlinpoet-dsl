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

/** Builds new [FunSpec] with name. */
fun funSpecOf(name: String): FunSpec = FunSpecBuilder(FunSpec.builder(name)).build()

/** Builds new constructor [FunSpec]. */
fun emptyConstructorFunSpec(): FunSpec = FunSpecBuilder(FunSpec.constructorBuilder()).build()

/** Builds new getter [FunSpec]. */
fun emptyGetterFunSpec(): FunSpec = FunSpecBuilder(FunSpec.getterBuilder()).build()

/** Builds new setter [FunSpec]. */
fun emptySetterFunSpec(): FunSpec = FunSpecBuilder(FunSpec.setterBuilder()).build()

/**
 * Builds new [FunSpec] with name,
 * by populating newly created [FunSpecBuilder] using provided [builderAction].
 */
inline fun buildFunSpec(
    name: String,
    builderAction: FunSpecBuilder.() -> Unit
): FunSpec = FunSpecBuilder(FunSpec.builder(name)).apply(builderAction).build()

/**
 * Builds new constructor [FunSpec],
 * by populating newly created [FunSpecBuilder] using provided [builderAction].
 */
inline fun buildConstructorFunSpec(
    builderAction: FunSpecBuilder.() -> Unit
): FunSpec = FunSpecBuilder(FunSpec.constructorBuilder()).apply(builderAction).build()

/**
 * Builds new getter [FunSpec],
 * by populating newly created [FunSpecBuilder] using provided [builderAction].
 */
inline fun buildGetterFunSpec(
    builderAction: FunSpecBuilder.() -> Unit
): FunSpec = FunSpecBuilder(FunSpec.getterBuilder()).apply(builderAction).build()

/**
 * Builds new setter [FunSpec],
 * by populating newly created [FunSpecBuilder] using provided [builderAction].
 */
inline fun buildSetterFunSpec(
    builderAction: FunSpecBuilder.() -> Unit
): FunSpec = FunSpecBuilder(FunSpec.setterBuilder()).apply(builderAction).build()

/** Modify existing [FunSpec.Builder] using provided [builderAction]. */
inline fun FunSpec.Builder.edit(
    builderAction: FunSpecBuilder.() -> Unit
): FunSpec.Builder = FunSpecBuilder(this).apply(builderAction).nativeBuilder

/**
 * Wrapper of [FunSpec.Builder], providing DSL support as a replacement to Java builder.
 * @param nativeBuilder source builder.
 */
@SpecDslMarker
class FunSpecBuilder(val nativeBuilder: FunSpec.Builder) : CodeBlockContainer() {

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
    inline fun kdoc(builderAction: KdocContainerScope.() -> Unit): Unit =
        KdocContainerScope(kdoc).builderAction()

    /** Annotations of this function. */
    val annotations: AnnotationSpecList = AnnotationSpecList(nativeBuilder.annotations)

    /** Configures annotations of this function. */
    inline fun annotations(builderAction: AnnotationSpecListScope.() -> Unit): Unit =
        AnnotationSpecListScope(annotations).builderAction()

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

    /** Set receiver to [TypeName] without kdoc. */
    var receiver: TypeName
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            nativeBuilder.receiver(value)
        }

    /** Set receiver to [TypeName] with [CodeBlock] as kdoc. */
    fun receiver(type: TypeName, kdocCode: CodeBlock) {
        nativeBuilder.receiver(type, kdocCode)
    }

    /** Set receiver to [TypeName] with String formatting as kdoc. */
    fun receiver(type: TypeName, kdocFormat: String, vararg kdocArgs: Any) {
        nativeBuilder.receiver(type, codeBlockOf(kdocFormat, kdocArgs))
    }

    /** Set receiver to [TypeName] with custom initialization builder as kdoc. */
    inline fun receiver(type: TypeName, kdocBuilderAction: CodeBlockBuilder.() -> Unit): Unit =
        receiver(type, buildCodeBlock(kdocBuilderAction))

    /** Set receiver to [Type] without kdoc. */
    fun receiver(type: Type) {
        nativeBuilder.receiver(type)
    }

    /** Set receiver to [Type] with [CodeBlock] as kdoc. */
    fun receiver(type: Type, kdocCode: CodeBlock) {
        nativeBuilder.receiver(type, kdocCode)
    }

    /** Set receiver to [Type] with String formatting as kdoc. */
    fun receiver(type: Type, kdocFormat: String, vararg kdocArgs: Any) {
        nativeBuilder.receiver(type, kdocFormat, *kdocArgs)
    }

    /** Set receiver to [Type] with custom initialization builder as kdoc. */
    inline fun receiver(type: Type, kdocBuilderAction: CodeBlockBuilder.() -> Unit): Unit =
        receiver(type, buildCodeBlock(kdocBuilderAction))

    /** Set receiver to [KClass] without kdoc. */
    fun receiver(type: KClass<*>) {
        nativeBuilder.receiver(type)
    }

    /** Set receiver to [KClass] with [CodeBlock] as kdoc. */
    fun receiver(type: KClass<*>, kdocCode: CodeBlock) {
        nativeBuilder.receiver(type, kdocCode)
    }

    /** Set receiver to [KClass] with String formatting as kdoc. */
    fun receiver(type: KClass<*>, kdocFormat: String, vararg kdocArgs: Any) {
        nativeBuilder.receiver(type, kdocFormat, *kdocArgs)
    }

    /** Set receiver to [KClass] with custom initialization builder as kdoc. */
    inline fun receiver(type: KClass<*>, kdocBuilderAction: CodeBlockBuilder.() -> Unit): Unit =
        receiver(type, buildCodeBlock(kdocBuilderAction))

    /** Set receiver to [T] without kdoc. */
    inline fun <reified T> receiver(): Unit = receiver(T::class)

    /** Set receiver to [T] with [CodeBlock] as kdoc. */
    inline fun <reified T> receiver(kdocCode: CodeBlock): Unit = receiver(T::class, kdocCode)

    /** Set receiver to [T] with String formatting as kdoc. */
    inline fun <reified T> receiver(kdocFormat: String, vararg kdocArgs: Any): Unit =
        receiver(T::class, kdocFormat, *kdocArgs)

    /** Set receiver to [T] with custom initialization builder as kdoc. */
    inline fun <reified T> receiver(kdocBuilderAction: CodeBlockBuilder.() -> Unit): Unit =
        receiver(T::class, buildCodeBlock(kdocBuilderAction))

    /** Set return to [TypeName] without kdoc. */
    var returns: TypeName
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            nativeBuilder.returns(value)
        }

    /** Set return to [TypeName] with [CodeBlock] as kdoc. */
    fun returns(type: TypeName, kdocCode: CodeBlock) {
        nativeBuilder.returns(type, kdocCode)
    }

    /** Set return to [TypeName] with String formatting as kdoc. */
    fun returns(type: TypeName, kdocFormat: String, vararg kdocArgs: Any) {
        nativeBuilder.returns(type, codeBlockOf(kdocFormat, kdocArgs))
    }

    /** Set return to [TypeName] with custom initialization builder as kdoc. */
    inline fun returns(type: TypeName, kdocBuilderAction: CodeBlockBuilder.() -> Unit): Unit =
        returns(type, buildCodeBlock(kdocBuilderAction))

    /** Set return to [Type] without kdoc. */
    fun returns(type: Type) {
        nativeBuilder.returns(type)
    }

    /** Set return to [Type] with [CodeBlock] as kdoc. */
    fun returns(type: Type, kdocCode: CodeBlock) {
        nativeBuilder.returns(type, kdocCode)
    }

    /** Set return to [Type] with String formatting as kdoc. */
    fun returns(type: Type, kdocFormat: String, vararg kdocArgs: Any) {
        nativeBuilder.returns(type, codeBlockOf(kdocFormat, kdocArgs))
    }

    /** Set return to [Type] with custom initialization builder as kdoc. */
    inline fun returns(type: Type, kdocBuilderAction: CodeBlockBuilder.() -> Unit): Unit =
        returns(type, buildCodeBlock(kdocBuilderAction))

    /** Set return to [KClass] without kdoc. */
    fun returns(type: KClass<*>) {
        nativeBuilder.returns(type)
    }

    /** Set return to [KClass] with [CodeBlock] as kdoc. */
    fun returns(type: KClass<*>, kdocCode: CodeBlock) {
        nativeBuilder.returns(type, kdocCode)
    }

    /** Set return to [KClass] with String formatting as kdoc. */
    fun returns(type: KClass<*>, kdocFormat: String, vararg kdocArgs: Any) {
        nativeBuilder.returns(type, codeBlockOf(kdocFormat, kdocArgs))
    }

    /** Set return to [KClass] with custom initialization builder as kdoc. */
    inline fun returns(type: KClass<*>, kdocBuilderAction: CodeBlockBuilder.() -> Unit): Unit =
        returns(type, buildCodeBlock(kdocBuilderAction))

    /** Set return to [T] without kdoc. */
    inline fun <reified T> returns(): Unit = returns(T::class)

    /** Set return to [T] with [CodeBlock] as kdoc. */
    inline fun <reified T> returns(kdocCode: CodeBlock): Unit = returns(T::class, kdocCode)

    /** Set return to [T] with String formatting as kdoc. */
    inline fun <reified T> returns(kdocFormat: String, vararg kdocArgs: Any) = returns(T::class, kdocFormat, *kdocArgs)

    /** Set return to [T] with custom initialization builder as kdoc. */
    inline fun <reified T> returns(kdocBuilderAction: CodeBlockBuilder.() -> Unit): Unit =
        returns(T::class, kdocBuilderAction)

    /** Parameters of this function. */
    val parameters: ParameterSpecList = ParameterSpecList(nativeBuilder.parameters)

    /** Configures parameters of this function. */
    inline fun parameters(builderAction: ParameterSpecListScope.() -> Unit): Unit =
        ParameterSpecListScope(parameters).builderAction()

    /** Call this constructor with [String] arguments. */
    fun callThisConstructor(vararg args: String) {
        nativeBuilder.callThisConstructor(*args)
    }

    /** Call this constructor with [CodeBlock] arguments. */
    fun callThisConstructor(vararg args: CodeBlock) {
        nativeBuilder.callThisConstructor(*args)
    }

    /** Call this constructor with code [builderAction]. */
    inline fun callThisConstructor(builderAction: CodeBlockBuilder.() -> Unit): Unit =
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
    inline fun callSuperConstructor(builderAction: CodeBlockBuilder.() -> Unit): Unit =
        callSuperConstructor(buildCodeBlock(builderAction))

    override fun append(format: String, vararg args: Any) {
        nativeBuilder.addCode(format, *args)
    }

    override fun appendNamed(format: String, args: Map<String, *>) {
        nativeBuilder.addNamedCode(format, args)
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

    override fun appendLine(format: String, vararg args: Any) {
        nativeBuilder.addStatement(format, *args)
    }

    /** Clear current code. */
    fun clear() {
        nativeBuilder.clearBody()
    }

    /** Add comment like [String.format]. */
    fun addComment(format: String, vararg args: Any) {
        nativeBuilder.addComment(format, *args)
    }

    /** Returns native spec. */
    fun build(): FunSpec = nativeBuilder.build()
}
