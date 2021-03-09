package io.github.hendraanggrian.kotlinpoet

import com.squareup.kotlinpoet.CodeBlock
import io.github.hendraanggrian.kotlinpoet.collections.CodeBlockContainer

/**
 * Converts string to [CodeBlock] using formatted [args].
 *
 * @see kotlin.text.format
 */
fun codeBlockOf(format: String, vararg args: Any): CodeBlock = CodeBlock.of(format, *args)

/**
 * Builds new [CodeBlock],
 * by populating newly created [CodeBlockBuilder] using provided [builderAction].
 */
inline fun buildCodeBlock(
    builderAction: CodeBlockBuilder.() -> Unit
): CodeBlock = CodeBlockBuilder(CodeBlock.builder()).apply(builderAction).build()

/** Modify existing [CodeBlock.Builder] using provided [builderAction]. */
inline fun CodeBlock.Builder.edit(
    builderAction: CodeBlockBuilder.() -> Unit
): CodeBlock.Builder = CodeBlockBuilder(this).apply(builderAction).nativeBuilder

/**
 * Wrapper of [CodeBlock.Builder], providing DSL support as a replacement to Java builder.
 * @param nativeBuilder source builder.
 */
class CodeBlockBuilder(val nativeBuilder: CodeBlock.Builder) : CodeBlockContainer() {

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
    inline fun indent(configuration: () -> Unit) {
        indent()
        configuration()
        unindent()
    }

    /** Convenient way to configure code within multiple indentation. */
    inline fun indent(level: Int, configuration: () -> Unit) {
        repeat(level) { indent() }
        configuration()
        repeat(level) { unindent() }
    }

    /** Clear current code. */
    fun clear() {
        nativeBuilder.clear()
    }

    /** Returns native spec. */
    fun build(): CodeBlock = nativeBuilder.build()
}