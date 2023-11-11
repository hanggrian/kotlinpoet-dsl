@file:OptIn(ExperimentalContracts::class)

package com.hendraanggrian.kotlinpoet

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeVariableName
import java.lang.reflect.Type
import javax.lang.model.element.Element
import javax.lang.model.element.Modifier
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.reflect.KClass

/**
 * Creates new [FunSpec] by populating newly created [FunSpecBuilder] using provided
 * [configuration].
 */
inline fun buildFunSpec(name: String, configuration: FunSpecBuilder.() -> Unit): FunSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return FunSpecBuilder(FunSpec.builder(name)).apply(configuration).build()
}

/**
 * Inserts new [FunSpec] by populating newly created [FunSpecBuilder] using provided
 * [configuration].
 */
inline fun FunSpecHandler.function(
    name: String,
    configuration: FunSpecBuilder.() -> Unit,
): FunSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return buildFunSpec(name, configuration).also(::function)
}

/**
 * Inserts new constructor [FunSpec] by populating newly created [FunSpecBuilder] using provided
 * [configuration].
 */
inline fun FunSpecHandler.constructorFunction(configuration: FunSpecBuilder.() -> Unit): FunSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return FunSpecBuilder(FunSpec.constructorBuilder())
        .apply(configuration)
        .build()
        .also(::function)
}

/**
 * Applies new constructor [FunSpec] by populating newly created [FunSpecBuilder] using provided
 * [configuration].
 */
inline fun TypeSpecBuilder.primaryConstructorFunction(
    configuration: FunSpecBuilder.() -> Unit,
): FunSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return FunSpecBuilder(FunSpec.constructorBuilder())
        .apply(configuration)
        .build()
        .also { primaryConstructor = it }
}

/**
 * Applies new getter [FunSpec] by populating newly created [FunSpecBuilder] using provided
 * [configuration].
 */
inline fun PropertySpecBuilder.getterFunction(configuration: FunSpecBuilder.() -> Unit): FunSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return FunSpecBuilder(FunSpec.getterBuilder())
        .apply(configuration)
        .build()
        .also { getter = it }
}

/**
 * Applies new setter [FunSpec] by populating newly created [FunSpecBuilder] using provided
 * [configuration].
 */
inline fun PropertySpecBuilder.setterFunction(configuration: FunSpecBuilder.() -> Unit): FunSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return FunSpecBuilder(FunSpec.setterBuilder())
        .apply(configuration)
        .build()
        .also { setter = it }
}

/**
 * Property delegate for inserting new [FunSpec] by populating newly created [FunSpecBuilder]
 * using provided [configuration].
 */
fun FunSpecHandler.functioning(
    configuration: FunSpecBuilder.() -> Unit,
): SpecDelegateProvider<FunSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider { buildFunSpec(it, configuration).also(::function) }
}

/** Invokes DSL to configure [FunSpec] collection. */
fun FunSpecHandler.functions(configuration: FunSpecHandlerScope.() -> Unit) {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    FunSpecHandlerScope(this).configuration()
}

/** Responsible for managing a set of [FunSpec] instances. */
sealed interface FunSpecHandler {
    fun function(function: FunSpec)

    fun function(name: String): FunSpec = FunSpec.builder(name).build().also(::function)

    fun functioning(): SpecDelegateProvider<FunSpec> =
        SpecDelegateProvider { FunSpec.builder(it).build().also(::function) }

    fun constructorFunction(): FunSpec = FunSpec.constructorBuilder().build().also(::function)
}

/**
 * Receiver for the `functions` block providing an extended set of operators for the
 * configuration.
 */
@KotlinpoetDsl
class FunSpecHandlerScope internal constructor(
    handler: FunSpecHandler,
) : FunSpecHandler by handler {
    /** @see function */
    operator fun String.invoke(configuration: FunSpecBuilder.() -> Unit): FunSpec =
        buildFunSpec(this, configuration).also(::function)
}

/** Wrapper of [FunSpec.Builder], providing DSL support as a replacement to Java builder. */
@KotlinpoetDsl
class FunSpecBuilder(
    private val nativeBuilder: FunSpec.Builder,
) : AnnotationSpecHandler, ParameterSpecHandler {
    val kdoc: CodeBlock.Builder get() = nativeBuilder.kdoc
    val annotations: MutableList<AnnotationSpec> get() = nativeBuilder.annotations
    val modifiers: MutableList<KModifier> get() = nativeBuilder.modifiers
    val typeVariables: MutableList<TypeVariableName> get() = nativeBuilder.typeVariables
    val parameters: MutableList<ParameterSpec> get() = nativeBuilder.parameters
    val tags: MutableMap<KClass<*>, *> get() = nativeBuilder.tags
    val originatingElements: MutableList<Element> get() = nativeBuilder.originatingElements

    @OptIn(ExperimentalKotlinPoetApi::class)
    val contextReceiverTypes: MutableList<TypeName> get() = nativeBuilder.contextReceiverTypes

    fun modifiers(vararg modifiers: KModifier) {
        nativeBuilder.addModifiers(*modifiers)
    }

    fun modifiers(modifiers: Iterable<KModifier>) {
        nativeBuilder.addModifiers(modifiers)
    }

    fun javaModifiers(modifiers: Iterable<Modifier>) {
        nativeBuilder.jvmModifiers(modifiers)
    }

    fun typeVariables(typeVariables: Iterable<TypeVariableName>) {
        nativeBuilder.addTypeVariables(typeVariables)
    }

    fun typeVariable(typeVariable: TypeVariableName) {
        nativeBuilder.addTypeVariable(typeVariable)
    }

    @OptIn(ExperimentalKotlinPoetApi::class)
    fun contextReceivers(receiverTypes: Iterable<TypeName>) {
        nativeBuilder.contextReceivers(receiverTypes)
    }

    var receiver: TypeName
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
        get() = noGetter()
        set(value) {
            nativeBuilder.receiver(value)
        }

    fun receiver(type: TypeName, kdocCode: CodeBlock) {
        nativeBuilder.receiver(type, kdocCode)
    }

    fun receiver(type: TypeName, kdocFormat: String, vararg kdocArgs: Any) {
        nativeBuilder.receiver(type, codeBlockOf(kdocFormat, kdocArgs))
    }

    fun receiver(type: Type, kdocCode: CodeBlock = CodeBlock.builder().build()) {
        nativeBuilder.receiver(type, kdocCode)
    }

    fun receiver(type: Type, kdocFormat: String, vararg kdocArgs: Any) {
        nativeBuilder.receiver(type, kdocFormat, *kdocArgs)
    }

    fun receiver(type: KClass<*>, kdocCode: CodeBlock = CodeBlock.builder().build()) {
        nativeBuilder.receiver(type, kdocCode)
    }

    fun receiver(type: KClass<*>, kdocFormat: String, vararg kdocArgs: Any) {
        nativeBuilder.receiver(type, kdocFormat, *kdocArgs)
    }

    inline fun <reified T> receiver(kdocCode: CodeBlock = CodeBlock.builder().build()): Unit =
        receiver(T::class, kdocCode)

    inline fun <reified T> receiver(kdocFormat: String, vararg kdocArgs: Any): Unit =
        receiver(T::class, kdocFormat, *kdocArgs)

    var returns: TypeName
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
        get() = noGetter()
        set(value) {
            nativeBuilder.returns(value)
        }

    fun returns(type: TypeName, kdocCode: CodeBlock) {
        nativeBuilder.returns(type, kdocCode)
    }

    fun returns(type: TypeName, kdocFormat: String, vararg kdocArgs: Any) {
        nativeBuilder.returns(type, codeBlockOf(kdocFormat, kdocArgs))
    }

    fun returns(type: Type, kdocCode: CodeBlock = CodeBlock.builder().build()) {
        nativeBuilder.returns(type, kdocCode)
    }

    fun returns(type: Type, kdocFormat: String, vararg kdocArgs: Any) {
        nativeBuilder.returns(type, codeBlockOf(kdocFormat, kdocArgs))
    }

    fun returns(type: KClass<*>, kdocCode: CodeBlock = CodeBlock.builder().build()) {
        nativeBuilder.returns(type, kdocCode)
    }

    fun returns(type: KClass<*>, kdocFormat: String, vararg kdocArgs: Any) {
        nativeBuilder.returns(type, codeBlockOf(kdocFormat, kdocArgs))
    }

    inline fun <reified T> returns(kdocCode: CodeBlock = CodeBlock.builder().build()): Unit =
        returns(T::class, kdocCode)

    inline fun <reified T> returns(kdocFormat: String, vararg kdocArgs: Any): Unit =
        returns(T::class, kdocFormat, *kdocArgs)

    override fun parameter(parameter: ParameterSpec) {
        nativeBuilder.addParameter(parameter)
    }

    fun callThisConstructor(args: Iterable<CodeBlock>) {
        nativeBuilder.callThisConstructor(args)
    }

    fun callThisConstructor(vararg args: String) {
        nativeBuilder.callThisConstructor(*args)
    }

    fun callThisConstructor(vararg args: CodeBlock) {
        nativeBuilder.callThisConstructor(*args)
    }

    fun callSuperConstructor(args: Iterable<CodeBlock>) {
        nativeBuilder.callSuperConstructor(args)
    }

    fun callSuperConstructor(vararg args: String) {
        nativeBuilder.callSuperConstructor(*args)
    }

    fun callSuperConstructor(vararg args: CodeBlock) {
        nativeBuilder.callSuperConstructor(*args)
    }

    fun append(format: String, vararg args: Any) {
        nativeBuilder.addCode(format, *args)
    }

    fun appendNamed(format: String, args: Map<String, *>) {
        nativeBuilder.addNamedCode(format, args)
    }

    fun append(code: CodeBlock) {
        nativeBuilder.addCode(code)
    }

    fun comment(format: String, vararg args: Any) {
        nativeBuilder.addComment(format, *args)
    }

    fun beginControlFlow(format: String, vararg args: Any) {
        nativeBuilder.beginControlFlow(format, *args)
    }

    fun nextControlFlow(format: String, vararg args: Any) {
        nativeBuilder.nextControlFlow(format, *args)
    }

    fun endControlFlow() {
        nativeBuilder.endControlFlow()
    }

    fun appendLine(format: String, vararg args: Any) {
        nativeBuilder.addStatement(format, *args)
    }

    fun clear() {
        nativeBuilder.clearBody()
    }

    override fun annotation(annotation: AnnotationSpec) {
        nativeBuilder.addAnnotation(annotation)
    }

    fun kdoc(format: String, vararg args: Any) {
        nativeBuilder.addKdoc(format, *args)
    }

    fun kdoc(block: CodeBlock) {
        nativeBuilder.addKdoc(block)
    }

    fun build(): FunSpec = nativeBuilder.build()
}
