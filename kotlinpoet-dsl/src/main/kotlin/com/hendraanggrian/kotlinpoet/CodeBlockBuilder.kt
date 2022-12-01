@file:OptIn(ExperimentalContracts::class)

package com.hendraanggrian.kotlinpoet

import com.hendraanggrian.kotlinpoet.collections.CodeBlockContainer
import com.squareup.kotlinpoet.CodeBlock
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * Converts string to [CodeBlock] using formatted [args].
 *
 * @see kotlin.text.format
 */
inline fun codeBlockOf(format: String, vararg args: Any?): CodeBlock = CodeBlock.of(format, *args)

/**
 * Builds new [CodeBlock], by populating newly created [CodeBlockBuilder] using
 * provided [configuration].
 */
inline fun buildCodeBlock(configuration: CodeBlockBuilder.() -> Unit): CodeBlock {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return CodeBlockBuilder(CodeBlock.builder()).apply(configuration).build()
}

/**
 * Wrapper of [CodeBlock.Builder], providing DSL support as a replacement to Java builder.
 *
 * @param nativeBuilder source builder.
 */
@KotlinpoetSpecDsl
class CodeBlockBuilder(private val nativeBuilder: CodeBlock.Builder) : CodeBlockContainer {

    /** Returns true if this builder contains no code. */
    fun isEmpty(): Boolean = nativeBuilder.isEmpty()

    /** Returns true if this builder contains code. */
    fun isNotEmpty(): Boolean = nativeBuilder.isNotEmpty()

    override fun appendNamed(format: String, args: Map<String, *>) {
        nativeBuilder.addNamed(format, args)
    }

    override fun append(format: String, vararg args: Any) {
        nativeBuilder.add(format, *args)
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

    override fun append(code: CodeBlock) {
        nativeBuilder.add(code)
    }

    /** Append an indentation. */
    fun indent() {
        nativeBuilder.indent()
    }

    /** Reverse an indentation. */
    fun unindent() {
        nativeBuilder.unindent()
    }

    /** Convenient way to configure code within single indentation. */
    fun indent(configuration: () -> Unit) {
        indent()
        configuration()
        unindent()
    }

    /** Convenient way to configure code within multiple indentation. */
    fun indent(level: Int, configuration: () -> Unit) {
        repeat(level) { indent() }
        configuration()
        repeat(level) { unindent() }
    }

    override fun clear() {
        nativeBuilder.clear()
    }

    /** Returns native spec. */
    fun build(): CodeBlock = nativeBuilder.build()
}
