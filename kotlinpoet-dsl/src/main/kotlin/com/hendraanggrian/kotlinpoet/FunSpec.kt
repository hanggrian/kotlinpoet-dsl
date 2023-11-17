@file:OptIn(ExperimentalContracts::class)

package com.hendraanggrian.kotlinpoet

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.MemberName
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

public inline fun MemberName.asFunSpec(): FunSpec = FunSpec.builder(this).build()

/**
 * Creates new [FunSpec] by populating newly created [FunSpecBuilder] using provided
 * [configuration].
 */
public inline fun buildFunSpec(name: String, configuration: FunSpecBuilder.() -> Unit): FunSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return FunSpecBuilder(FunSpec.builder(name)).apply(configuration).build()
}

/**
 * Creates new [FunSpec] by populating newly created [FunSpecBuilder] using provided
 * [configuration].
 */
public inline fun buildFunSpec(
    name: MemberName,
    configuration: FunSpecBuilder.() -> Unit,
): FunSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return FunSpecBuilder(FunSpec.builder(name)).apply(configuration).build()
}

/**
 * Inserts new [FunSpec] by populating newly created [FunSpecBuilder] using provided
 * [configuration].
 */
public inline fun FunSpecHandler.function(
    name: String,
    configuration: FunSpecBuilder.() -> Unit,
): FunSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return buildFunSpec(name, configuration).also(::function)
}

/**
 * Inserts new [FunSpec] by populating newly created [FunSpecBuilder] using provided
 * [configuration].
 */
public inline fun FunSpecHandler.function(
    name: MemberName,
    configuration: FunSpecBuilder.() -> Unit,
): FunSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return buildFunSpec(name, configuration).also(::function)
}

/**
 * Inserts new constructor [FunSpec] by populating newly created [FunSpecBuilder] using provided
 * [configuration].
 */
public inline fun FunSpecHandler.constructorFunction(
    configuration: FunSpecBuilder.() -> Unit,
): FunSpec {
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
public inline fun TypeSpecBuilder.primaryConstructorFunction(
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
public inline fun PropertySpecBuilder.getterFunction(
    configuration: FunSpecBuilder.() -> Unit,
): FunSpec {
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
public inline fun PropertySpecBuilder.setterFunction(
    configuration: FunSpecBuilder.() -> Unit,
): FunSpec {
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
public fun FunSpecHandler.functioning(
    configuration: FunSpecBuilder.() -> Unit,
): SpecDelegateProvider<FunSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider { buildFunSpec(it, configuration).also(::function) }
}

/** Invokes DSL to configure [FunSpec] collection. */
public fun FunSpecHandler.functions(configuration: FunSpecHandlerScope.() -> Unit) {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    FunSpecHandlerScope.of(this).configuration()
}

/** Responsible for managing a set of [FunSpec] instances. */
public interface FunSpecHandler {
    public fun function(function: FunSpec)

    public fun function(name: String): FunSpec = FunSpec.builder(name).build().also(::function)

    public fun function(name: MemberName): FunSpec = name.asFunSpec().also(::function)

    public fun functioning(): SpecDelegateProvider<FunSpec> =
        SpecDelegateProvider { FunSpec.builder(it).build().also(::function) }

    public fun constructorFunction(): FunSpec =
        FunSpec.constructorBuilder().build().also(::function)
}

/**
 * Receiver for the `functions` block providing an extended set of operators for the
 * configuration.
 */
@KotlinpoetDsl
public open class FunSpecHandlerScope private constructor(
    handler: FunSpecHandler,
) : FunSpecHandler by handler {
    public companion object {
        public fun of(handler: FunSpecHandler): FunSpecHandlerScope = FunSpecHandlerScope(handler)
    }

    /** @see function */
    public operator fun String.invoke(configuration: FunSpecBuilder.() -> Unit): FunSpec =
        buildFunSpec(this, configuration).also(::function)
}

/** Wrapper of [FunSpec.Builder], providing DSL support as a replacement to Java builder. */
@KotlinpoetDsl
public class FunSpecBuilder(
    private val nativeBuilder: FunSpec.Builder,
) : AnnotationSpecHandler, ParameterSpecHandler {
    public val kdoc: CodeBlock.Builder get() = nativeBuilder.kdoc
    public val annotations: MutableList<AnnotationSpec> get() = nativeBuilder.annotations
    public val modifiers: MutableList<KModifier> get() = nativeBuilder.modifiers
    public val typeVariables: MutableList<TypeVariableName> get() = nativeBuilder.typeVariables
    public val parameters: MutableList<ParameterSpec> get() = nativeBuilder.parameters
    public val tags: MutableMap<KClass<*>, *> get() = nativeBuilder.tags
    public val originatingElements: MutableList<Element> get() = nativeBuilder.originatingElements

    @OptIn(ExperimentalKotlinPoetApi::class)
    public val contextReceiverTypes: MutableList<TypeName>
        get() = nativeBuilder.contextReceiverTypes

    public fun modifiers(vararg modifiers: KModifier) {
        nativeBuilder.addModifiers(*modifiers)
    }

    public fun modifiers(modifiers: Iterable<KModifier>) {
        nativeBuilder.addModifiers(modifiers)
    }

    public fun javaModifiers(modifiers: Iterable<Modifier>) {
        nativeBuilder.jvmModifiers(modifiers)
    }

    public fun typeVariables(typeVariables: Iterable<TypeVariableName>) {
        nativeBuilder.addTypeVariables(typeVariables)
    }

    public fun typeVariable(typeVariable: TypeVariableName) {
        nativeBuilder.addTypeVariable(typeVariable)
    }

    @OptIn(ExperimentalKotlinPoetApi::class)
    public fun contextReceivers(receiverTypes: Iterable<TypeName>) {
        nativeBuilder.contextReceivers(receiverTypes)
    }

    public var receiver: TypeName
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
        get() = noGetter()
        set(value) {
            nativeBuilder.receiver(value)
        }

    public fun receiver(type: TypeName, kdocCode: CodeBlock) {
        nativeBuilder.receiver(type, kdocCode)
    }

    public fun receiver(type: TypeName, kdocFormat: String, vararg kdocArgs: Any) {
        nativeBuilder.receiver(type, codeBlockOf(kdocFormat, kdocArgs))
    }

    public fun receiver(type: Type, kdocCode: CodeBlock = CodeBlock.builder().build()) {
        nativeBuilder.receiver(type, kdocCode)
    }

    public fun receiver(type: Type, kdocFormat: String, vararg kdocArgs: Any) {
        nativeBuilder.receiver(type, kdocFormat, *kdocArgs)
    }

    public fun receiver(type: KClass<*>, kdocCode: CodeBlock = CodeBlock.builder().build()) {
        nativeBuilder.receiver(type, kdocCode)
    }

    public fun receiver(type: KClass<*>, kdocFormat: String, vararg kdocArgs: Any) {
        nativeBuilder.receiver(type, kdocFormat, *kdocArgs)
    }

    public inline fun <reified T> receiver(
        kdocCode: CodeBlock = CodeBlock.builder().build(),
    ): Unit = receiver(T::class, kdocCode)

    public inline fun <reified T> receiver(kdocFormat: String, vararg kdocArgs: Any): Unit =
        receiver(T::class, kdocFormat, *kdocArgs)

    public var returns: TypeName
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
        get() = noGetter()
        set(value) {
            nativeBuilder.returns(value)
        }

    public fun returns(type: TypeName, kdocCode: CodeBlock) {
        nativeBuilder.returns(type, kdocCode)
    }

    public fun returns(type: TypeName, kdocFormat: String, vararg kdocArgs: Any) {
        nativeBuilder.returns(type, codeBlockOf(kdocFormat, kdocArgs))
    }

    public fun returns(type: Type, kdocCode: CodeBlock = CodeBlock.builder().build()) {
        nativeBuilder.returns(type, kdocCode)
    }

    public fun returns(type: Type, kdocFormat: String, vararg kdocArgs: Any) {
        nativeBuilder.returns(type, codeBlockOf(kdocFormat, kdocArgs))
    }

    public fun returns(type: KClass<*>, kdocCode: CodeBlock = CodeBlock.builder().build()) {
        nativeBuilder.returns(type, kdocCode)
    }

    public fun returns(type: KClass<*>, kdocFormat: String, vararg kdocArgs: Any) {
        nativeBuilder.returns(type, codeBlockOf(kdocFormat, kdocArgs))
    }

    public inline fun <reified T> returns(kdocCode: CodeBlock = CodeBlock.builder().build()): Unit =
        returns(T::class, kdocCode)

    public inline fun <reified T> returns(kdocFormat: String, vararg kdocArgs: Any): Unit =
        returns(T::class, kdocFormat, *kdocArgs)

    public override fun parameter(parameter: ParameterSpec) {
        nativeBuilder.addParameter(parameter)
    }

    public fun callThisConstructor(args: Iterable<CodeBlock>) {
        nativeBuilder.callThisConstructor(args)
    }

    public fun callThisConstructor(vararg args: String) {
        nativeBuilder.callThisConstructor(*args)
    }

    public fun callThisConstructor(vararg args: CodeBlock) {
        nativeBuilder.callThisConstructor(*args)
    }

    public fun callSuperConstructor(args: Iterable<CodeBlock>) {
        nativeBuilder.callSuperConstructor(args)
    }

    public fun callSuperConstructor(vararg args: String) {
        nativeBuilder.callSuperConstructor(*args)
    }

    public fun callSuperConstructor(vararg args: CodeBlock) {
        nativeBuilder.callSuperConstructor(*args)
    }

    public fun append(format: String, vararg args: Any) {
        nativeBuilder.addCode(format, *args)
    }

    public fun appendNamed(format: String, args: Map<String, *>) {
        nativeBuilder.addNamedCode(format, args)
    }

    public fun append(code: CodeBlock) {
        nativeBuilder.addCode(code)
    }

    public fun comment(format: String, vararg args: Any) {
        nativeBuilder.addComment(format, *args)
    }

    public fun beginControlFlow(format: String, vararg args: Any) {
        nativeBuilder.beginControlFlow(format, *args)
    }

    public fun nextControlFlow(format: String, vararg args: Any) {
        nativeBuilder.nextControlFlow(format, *args)
    }

    public fun endControlFlow() {
        nativeBuilder.endControlFlow()
    }

    public fun appendLine(format: String, vararg args: Any) {
        nativeBuilder.addStatement(format, *args)
    }

    // Taken from com.squareup.javapoet.CodeBlock.addStatement.
    public fun appendLine(code: CodeBlock) {
        appendLine("%L", code)
    }

    public fun clear() {
        nativeBuilder.clearBody()
    }

    public override fun annotation(annotation: AnnotationSpec) {
        nativeBuilder.addAnnotation(annotation)
    }

    public fun kdoc(format: String, vararg args: Any) {
        nativeBuilder.addKdoc(format, *args)
    }

    public fun kdoc(block: CodeBlock) {
        nativeBuilder.addKdoc(block)
    }

    public fun build(): FunSpec = nativeBuilder.build()
}
