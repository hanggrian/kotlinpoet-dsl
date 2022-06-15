@file:OptIn(ExperimentalContracts::class)

package com.hendraanggrian.kotlinpoet

import com.hendraanggrian.kotlinpoet.collections.AnnotationSpecList
import com.hendraanggrian.kotlinpoet.collections.AnnotationSpecListScope
import com.hendraanggrian.kotlinpoet.collections.CodeBlockContainer
import com.hendraanggrian.kotlinpoet.collections.KdocContainer
import com.hendraanggrian.kotlinpoet.collections.KdocContainerScope
import com.hendraanggrian.kotlinpoet.collections.ParameterSpecList
import com.hendraanggrian.kotlinpoet.collections.ParameterSpecListScope
import com.hendraanggrian.kotlinpoet.collections.TypeVariableNameCollection
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeName
import java.lang.reflect.Type
import javax.lang.model.element.Element
import javax.lang.model.element.Modifier
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.reflect.KClass

/**
 * Builds new [FunSpec] with name,
 * by populating newly created [FunSpecBuilder] using provided [configuration].
 */
inline fun buildFunSpec(name: String, configuration: FunSpecBuilder.() -> Unit): FunSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return FunSpecBuilder(FunSpec.builder(name)).apply(configuration).build()
}

/**
 * Builds new constructor [FunSpec],
 * by populating newly created [FunSpecBuilder] using provided [configuration].
 */
inline fun buildConstructorFunSpec(configuration: FunSpecBuilder.() -> Unit): FunSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return FunSpecBuilder(FunSpec.constructorBuilder()).apply(configuration).build()
}

/**
 * Builds new getter [FunSpec],
 * by populating newly created [FunSpecBuilder] using provided [configuration].
 */
inline fun buildGetterFunSpec(configuration: FunSpecBuilder.() -> Unit): FunSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return FunSpecBuilder(FunSpec.getterBuilder()).apply(configuration).build()
}

/**
 * Builds new setter [FunSpec],
 * by populating newly created [FunSpecBuilder] using provided [configuration].
 */
inline fun buildSetterFunSpec(configuration: FunSpecBuilder.() -> Unit): FunSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return FunSpecBuilder(FunSpec.setterBuilder()).apply(configuration).build()
}

/**
 * Property delegate for building new [FunSpec],
 * by populating newly created [FunSpecBuilder] using provided [configuration].
 */
fun buildingFunSpec(configuration: FunSpecBuilder.() -> Unit): SpecLoader<FunSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return createSpecLoader { buildFunSpec(it, configuration) }
}

/**
 * Wrapper of [FunSpec.Builder], providing DSL support as a replacement to Java builder.
 * @param nativeBuilder source builder.
 */
@KotlinpoetSpecDsl
class FunSpecBuilder(private val nativeBuilder: FunSpec.Builder) : CodeBlockContainer {
    val modifiers: MutableList<KModifier> get() = nativeBuilder.modifiers
    val tags: MutableMap<KClass<*>, *> get() = nativeBuilder.tags
    val originatingElements: MutableList<Element> get() = nativeBuilder.originatingElements

    /** Kdoc of this function. */
    val kdoc: KdocContainer = object : KdocContainer {
        override fun append(format: String, vararg args: Any) {
            nativeBuilder.addKdoc(format, *args)
        }

        override fun append(code: CodeBlock) {
            nativeBuilder.addKdoc(code)
        }
    }

    /** Configures kdoc of this function. */
    fun kdoc(configuration: KdocContainerScope.() -> Unit) {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        KdocContainerScope(kdoc).configuration()
    }

    /** Annotations of this function. */
    val annotations: AnnotationSpecList = AnnotationSpecList(nativeBuilder.annotations)

    /** Configures annotations of this function. */
    fun annotations(configuration: AnnotationSpecListScope.() -> Unit) {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        AnnotationSpecListScope(annotations).configuration()
    }

    /** Add function modifiers. */
    fun addModifiers(vararg modifiers: KModifier) {
        nativeBuilder.addModifiers(*modifiers)
    }

    /** Add function JVM modifiers. */
    fun jvmModifiers(modifiers: Iterable<Modifier>) {
        nativeBuilder.jvmModifiers(modifiers)
    }

    /** Type variables of this function. */
    val typeVariables: TypeVariableNameCollection = TypeVariableNameCollection(nativeBuilder.typeVariables)

    /** Configures type variables of this function. */
    fun typeVariables(configuration: TypeVariableNameCollection.() -> Unit) {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        typeVariables.configuration()
    }

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

    /** Set receiver to [Type] with [CodeBlock] as kdoc. */
    fun receiver(type: Type, kdocCode: CodeBlock = EMPTY_CODEBLOCK) {
        nativeBuilder.receiver(type, kdocCode)
    }

    /** Set receiver to [Type] with String formatting as kdoc. */
    fun receiver(type: Type, kdocFormat: String, vararg kdocArgs: Any) {
        nativeBuilder.receiver(type, kdocFormat, *kdocArgs)
    }

    /** Set receiver to [KClass] with [CodeBlock] as kdoc. */
    fun receiver(type: KClass<*>, kdocCode: CodeBlock = EMPTY_CODEBLOCK) {
        nativeBuilder.receiver(type, kdocCode)
    }

    /** Set receiver to [KClass] with String formatting as kdoc. */
    fun receiver(type: KClass<*>, kdocFormat: String, vararg kdocArgs: Any) {
        nativeBuilder.receiver(type, kdocFormat, *kdocArgs)
    }

    /** Set receiver to [T] with [CodeBlock] as kdoc. */
    inline fun <reified T> receiver(kdocCode: CodeBlock = EMPTY_CODEBLOCK): Unit = receiver(T::class, kdocCode)

    /** Set receiver to [T] with String formatting as kdoc. */
    inline fun <reified T> receiver(kdocFormat: String, vararg kdocArgs: Any): Unit =
        receiver(T::class, kdocFormat, *kdocArgs)

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

    /** Set return to [Type] with [CodeBlock] as kdoc. */
    fun returns(type: Type, kdocCode: CodeBlock = EMPTY_CODEBLOCK) {
        nativeBuilder.returns(type, kdocCode)
    }

    /** Set return to [Type] with String formatting as kdoc. */
    fun returns(type: Type, kdocFormat: String, vararg kdocArgs: Any) {
        nativeBuilder.returns(type, codeBlockOf(kdocFormat, kdocArgs))
    }

    /** Set return to [KClass] with [CodeBlock] as kdoc. */
    fun returns(type: KClass<*>, kdocCode: CodeBlock = EMPTY_CODEBLOCK) {
        nativeBuilder.returns(type, kdocCode)
    }

    /** Set return to [KClass] with String formatting as kdoc. */
    fun returns(type: KClass<*>, kdocFormat: String, vararg kdocArgs: Any) {
        nativeBuilder.returns(type, codeBlockOf(kdocFormat, kdocArgs))
    }

    /** Set return to [T] with [CodeBlock] as kdoc. */
    inline fun <reified T> returns(kdocCode: CodeBlock = EMPTY_CODEBLOCK): Unit = returns(T::class, kdocCode)

    /** Set return to [T] with String formatting as kdoc. */
    inline fun <reified T> returns(kdocFormat: String, vararg kdocArgs: Any) = returns(T::class, kdocFormat, *kdocArgs)

    /** Parameters of this function. */
    val parameters: ParameterSpecList = ParameterSpecList(nativeBuilder.parameters)

    /** Configures parameters of this function. */
    fun parameters(configuration: ParameterSpecListScope.() -> Unit) {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        ParameterSpecListScope(parameters).configuration()
    }

    /** Call this constructor with list of [CodeBlock] arguments. */
    fun callThisConstructor(args: Iterable<CodeBlock>) {
        nativeBuilder.callThisConstructor(args)
    }

    /** Call this constructor with [String] arguments. */
    fun callThisConstructor(vararg args: String) {
        nativeBuilder.callThisConstructor(*args)
    }

    /** Call this constructor with [CodeBlock] arguments. */
    fun callThisConstructor(vararg args: CodeBlock) {
        nativeBuilder.callThisConstructor(*args)
    }

    /** Call super constructor with list of [CodeBlock] arguments. */
    fun callSuperConstructor(args: Iterable<CodeBlock>) {
        nativeBuilder.callSuperConstructor(args)
    }

    /** Call super constructor with [String] arguments. */
    fun callSuperConstructor(vararg args: String) {
        nativeBuilder.callSuperConstructor(*args)
    }

    /** Call super constructor with [CodeBlock] arguments. */
    fun callSuperConstructor(vararg args: CodeBlock) {
        nativeBuilder.callSuperConstructor(*args)
    }

    override fun append(format: String, vararg args: Any) {
        nativeBuilder.addCode(format, *args)
    }

    override fun appendNamed(format: String, args: Map<String, *>) {
        nativeBuilder.addNamedCode(format, args)
    }

    override fun append(code: CodeBlock) {
        nativeBuilder.addCode(code)
    }

    override fun beginControlFlow(format: String, vararg args: Any) {
        nativeBuilder.beginControlFlow(format, *args)
    }

    override fun nextControlFlow(format: String, vararg args: Any) {
        nativeBuilder.nextControlFlow(format, *args)
    }

    override fun endControlFlow() {
        nativeBuilder.endControlFlow()
    }

    override fun appendLine(format: String, vararg args: Any) {
        nativeBuilder.addStatement(format, *args)
    }

    override fun clear() {
        nativeBuilder.clearBody()
    }

    /** Add comment like [String.format]. */
    fun addComment(format: String, vararg args: Any) {
        nativeBuilder.addComment(format, *args)
    }

    /** Returns native spec. */
    fun build(): FunSpec = nativeBuilder.build()
}
