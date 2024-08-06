@file:OptIn(ExperimentalContracts::class)

package com.hanggrian.kotlinpoet

import com.squareup.kotlinpoet.CodeBlock
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * Converts string to [CodeBlock] using formatted [args].
 *
 * @see kotlin.text.format
 */
public inline fun codeBlockOf(format: String, vararg args: Any?): CodeBlock =
    CodeBlock.of(format, *args)

/**
 * Creates new [CodeBlock] by populating newly created [CodeBlockBuilder] using provided
 * [configuration].
 */
public fun buildCodeBlock(configuration: CodeBlockBuilder.() -> Unit): CodeBlock {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return CodeBlockBuilder(CodeBlock.builder()).apply(configuration).build()
}

/** Wrapper of [CodeBlock.Builder], providing DSL support as a replacement to Java builder. */
@KotlinpoetDsl
public class CodeBlockBuilder(private val nativeBuilder: CodeBlock.Builder) {
    /** Returns true if this builder contains no code. */
    public fun isEmpty(): Boolean = nativeBuilder.isEmpty()

    /** Returns true if this builder contains code. */
    public fun isNotEmpty(): Boolean = nativeBuilder.isNotEmpty()

    public fun appendNamed(format: String, args: Map<String, *>) {
        nativeBuilder.addNamed(format, args)
    }

    public fun append(format: String, vararg args: Any) {
        nativeBuilder.add(format, *args)
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

    public fun appendLine() {
        nativeBuilder.addStatement("")
    }

    public fun append(code: CodeBlock) {
        nativeBuilder.add(code)
    }

    public fun indent() {
        nativeBuilder.indent()
    }

    public fun unindent() {
        nativeBuilder.unindent()
    }

    public fun clear() {
        nativeBuilder.clear()
    }

    public fun build(): CodeBlock = nativeBuilder.build()
}
