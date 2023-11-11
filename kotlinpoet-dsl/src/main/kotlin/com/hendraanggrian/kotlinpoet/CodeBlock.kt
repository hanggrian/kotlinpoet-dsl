@file:OptIn(ExperimentalContracts::class)

package com.hendraanggrian.kotlinpoet

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
 * Creates new [CodeBlock] by populating newly created [CodeBlockBuilder] using provided
 * [configuration].
 */
inline fun buildCodeBlock(configuration: CodeBlockBuilder.() -> Unit): CodeBlock {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return CodeBlockBuilder(CodeBlock.builder()).apply(configuration).build()
}

/** Wrapper of [CodeBlock.Builder], providing DSL support as a replacement to Java builder. */
@KotlinpoetDsl
class CodeBlockBuilder(private val nativeBuilder: CodeBlock.Builder) {
    /** Returns true if this builder contains no code. */
    fun isEmpty(): Boolean = nativeBuilder.isEmpty()

    /** Returns true if this builder contains code. */
    fun isNotEmpty(): Boolean = nativeBuilder.isNotEmpty()

    fun appendNamed(format: String, args: Map<String, *>) {
        nativeBuilder.addNamed(format, args)
    }

    fun append(format: String, vararg args: Any) {
        nativeBuilder.add(format, *args)
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

    fun append(code: CodeBlock) {
        nativeBuilder.add(code)
    }

    fun indent() {
        nativeBuilder.indent()
    }

    fun unindent() {
        nativeBuilder.unindent()
    }

    fun clear() {
        nativeBuilder.clear()
    }

    fun build(): CodeBlock = nativeBuilder.build()
}
