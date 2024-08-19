@file:OptIn(ExperimentalContracts::class)

package com.hanggrian.kotlinpoet

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

/** Creates new [FunSpec] using parameters. */
public inline fun funSpecOf(name: MemberName): FunSpec =
    FunSpec
        .builder(name)
        .build()

/** Creates new [FunSpec] using parameters. */
public inline fun funSpecOf(name: String): FunSpec =
    FunSpec
        .builder(name)
        .build()

/** Creates new constructor [FunSpec]. */
public inline fun emptyConstructorFunSpec(): FunSpec =
    FunSpec
        .constructorBuilder()
        .build()

/**
 * Builds new [FunSpec] by populating newly created [FunSpecBuilder] using provided
 * [configuration].
 */
public inline fun buildFunSpec(name: String, configuration: FunSpecBuilder.() -> Unit): FunSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return FunSpecBuilder(FunSpec.builder(name))
        .apply(configuration)
        .build()
}

/**
 * Builds new [FunSpec] by populating newly created [FunSpecBuilder] using provided
 * [configuration].
 */
public inline fun buildFunSpec(
    name: MemberName,
    configuration: FunSpecBuilder.() -> Unit,
): FunSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return FunSpecBuilder(FunSpec.builder(name))
        .apply(configuration)
        .build()
}

/**
 * Builds new constructor [FunSpec] by populating newly created [FunSpecBuilder] using provided
 * [configuration].
 */
public inline fun buildConstructorFunSpec(configuration: FunSpecBuilder.() -> Unit): FunSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return FunSpecBuilder(FunSpec.constructorBuilder())
        .apply(configuration)
        .build()
}

/**
 * Inserts new [FunSpec] by populating newly created [FunSpecBuilder] using provided
 * [configuration].
 */
public inline fun FunSpecHandler.add(
    name: String,
    configuration: FunSpecBuilder.() -> Unit,
): FunSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return FunSpecBuilder(FunSpec.builder(name))
        .apply(configuration)
        .build()
        .also(::add)
}

/**
 * Inserts new [FunSpec] by populating newly created [FunSpecBuilder] using provided
 * [configuration].
 */
public inline fun FunSpecHandler.add(
    name: MemberName,
    configuration: FunSpecBuilder.() -> Unit,
): FunSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return FunSpecBuilder(FunSpec.builder(name))
        .apply(configuration)
        .build()
        .also(::add)
}

/**
 * Inserts new constructor [FunSpec] by populating newly created [FunSpecBuilder] using provided
 * [configuration].
 */
public inline fun FunSpecHandler.addConstructor(configuration: FunSpecBuilder.() -> Unit): FunSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return FunSpecBuilder(FunSpec.constructorBuilder())
        .apply(configuration)
        .build()
        .also(::add)
}

/**
 * Applies new constructor [FunSpec] by populating newly created [FunSpecBuilder] using provided
 * [configuration].
 */
public inline fun TypeSpecBuilder.setPrimaryConstructor(
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
public inline fun PropertySpecBuilder.setGetter(configuration: FunSpecBuilder.() -> Unit): FunSpec {
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
public inline fun PropertySpecBuilder.setSetter(configuration: FunSpecBuilder.() -> Unit): FunSpec {
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
public fun FunSpecHandler.adding(
    configuration: FunSpecBuilder.() -> Unit,
): SpecDelegateProvider<FunSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider {
        FunSpecBuilder(FunSpec.builder(it))
            .apply(configuration)
            .build()
            .also(::add)
    }
}

/** Responsible for managing a set of [FunSpec] instances. */
public interface FunSpecHandler {
    public fun add(function: FunSpec)

    public fun add(name: String): FunSpec = funSpecOf(name).also(::add)

    public fun add(name: MemberName): FunSpec = funSpecOf(name).also(::add)

    public fun adding(): SpecDelegateProvider<FunSpec> =
        SpecDelegateProvider { funSpecOf(it).also(::add) }

    public fun addConstructor(): FunSpec = emptyConstructorFunSpec().also(::add)
}

/**
 * Receiver for the `functions` block providing an extended set of operators for the
 * configuration.
 */
@KotlinPoetDsl
public open class FunSpecHandlerScope private constructor(handler: FunSpecHandler) :
    FunSpecHandler by handler {
        public inline operator fun String.invoke(
            configuration: FunSpecBuilder.() -> Unit,
        ): FunSpec = add(this, configuration)

        public companion object {
            public fun of(handler: FunSpecHandler): FunSpecHandlerScope =
                FunSpecHandlerScope(handler)
        }
    }

/** Wrapper of [FunSpec.Builder], providing DSL support as a replacement to Java builder. */
@KotlinPoetDsl
public class FunSpecBuilder(private val nativeBuilder: FunSpec.Builder) {
    public val annotations: AnnotationSpecHandler =
        object : AnnotationSpecHandler {
            override fun add(annotation: AnnotationSpec) {
                annotationSpecs += annotation
            }
        }

    public val parameters: ParameterSpecHandler =
        object : ParameterSpecHandler {
            override fun add(parameter: ParameterSpec) {
                parameterSpecs += parameter
            }
        }

    /** Invokes DSL to configure [AnnotationSpec] collection. */
    public inline fun annotations(configuration: AnnotationSpecHandlerScope.() -> Unit) {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        AnnotationSpecHandlerScope
            .of(annotations)
            .configuration()
    }

    /** Invokes DSL to configure [ParameterSpec] collection. */
    public inline fun parameters(configuration: ParameterSpecHandlerScope.() -> Unit) {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        ParameterSpecHandlerScope
            .of(parameters)
            .configuration()
    }

    public val kdoc: CodeBlock.Builder get() = nativeBuilder.kdoc
    public val annotationSpecs: MutableList<AnnotationSpec> get() = nativeBuilder.annotations
    public val modifiers: MutableList<KModifier> get() = nativeBuilder.modifiers
    public val typeVariables: MutableList<TypeVariableName> get() = nativeBuilder.typeVariables
    public val parameterSpecs: MutableList<ParameterSpec> get() = nativeBuilder.parameters
    public val tags: MutableMap<KClass<*>, *> get() = nativeBuilder.tags
    public val originatingElements: MutableList<Element> get() = nativeBuilder.originatingElements

    @OptIn(ExperimentalKotlinPoetApi::class)
    public val contextReceiverTypes: MutableList<TypeName>
        get() = nativeBuilder.contextReceiverTypes

    public fun addModifiers(vararg modifiers: KModifier) {
        this.modifiers += modifiers
    }

    public fun addJvmModifiers(vararg modifiers: Modifier) {
        nativeBuilder.jvmModifiers(modifiers.toList())
    }

    public fun addTypeVariables(vararg typeVariables: TypeVariableName) {
        this.typeVariables += typeVariables
    }

    public var receiver: TypeName
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
        get() = noGetter()
        set(value) {
            nativeBuilder.receiver(value)
        }

    public fun setReceiver(type: TypeName, kdocCode: CodeBlock) {
        nativeBuilder.receiver(type, kdocCode)
    }

    public fun setReceiver(type: TypeName, kdocFormat: String, vararg kdocArgs: Any) {
        nativeBuilder.receiver(type, codeBlockOf(kdocFormat, kdocArgs))
    }

    public fun setReceiver(type: Type, kdocCode: CodeBlock = CodeBlock.builder().build()) {
        nativeBuilder.receiver(type, kdocCode)
    }

    public fun setReceiver(type: Type, kdocFormat: String, vararg kdocArgs: Any) {
        nativeBuilder.receiver(type, kdocFormat, *kdocArgs)
    }

    public fun setReceiver(type: KClass<*>, kdocCode: CodeBlock = CodeBlock.builder().build()) {
        nativeBuilder.receiver(type, kdocCode)
    }

    public fun setReceiver(type: KClass<*>, kdocFormat: String, vararg kdocArgs: Any) {
        nativeBuilder.receiver(type, kdocFormat, *kdocArgs)
    }

    public inline fun <reified T> setReceiver(
        kdocCode: CodeBlock = CodeBlock.builder().build(),
    ): Unit = setReceiver(T::class, kdocCode)

    public inline fun <reified T> setReceiver(kdocFormat: String, vararg kdocArgs: Any): Unit =
        setReceiver(T::class, kdocFormat, *kdocArgs)

    public var returns: TypeName
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
        get() = noGetter()
        set(value) {
            nativeBuilder.returns(value)
        }

    public fun setReturns(type: TypeName, kdocCode: CodeBlock) {
        nativeBuilder.returns(type, kdocCode)
    }

    public fun setReturns(type: TypeName, kdocFormat: String, vararg kdocArgs: Any) {
        nativeBuilder.returns(type, codeBlockOf(kdocFormat, kdocArgs))
    }

    public fun setReturns(type: Type, kdocCode: CodeBlock = CodeBlock.builder().build()) {
        nativeBuilder.returns(type, kdocCode)
    }

    public fun setReturns(type: Type, kdocFormat: String, vararg kdocArgs: Any) {
        nativeBuilder.returns(type, codeBlockOf(kdocFormat, kdocArgs))
    }

    public fun setReturns(type: KClass<*>, kdocCode: CodeBlock = CodeBlock.builder().build()) {
        nativeBuilder.returns(type, kdocCode)
    }

    public fun setReturns(type: KClass<*>, kdocFormat: String, vararg kdocArgs: Any) {
        nativeBuilder.returns(type, codeBlockOf(kdocFormat, kdocArgs))
    }

    public inline fun <reified T> setReturns(
        kdocCode: CodeBlock = CodeBlock.builder().build(),
    ): Unit = setReturns(T::class, kdocCode)

    public inline fun <reified T> setReturns(kdocFormat: String, vararg kdocArgs: Any): Unit =
        setReturns(T::class, kdocFormat, *kdocArgs)

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

    public fun addComment(format: String, vararg args: Any) {
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

    public fun addKdoc(format: String, vararg args: Any) {
        nativeBuilder.addKdoc(format, *args)
    }

    public fun addKdoc(block: CodeBlock) {
        nativeBuilder.addKdoc(block)
    }

    public fun build(): FunSpec = nativeBuilder.build()
}
