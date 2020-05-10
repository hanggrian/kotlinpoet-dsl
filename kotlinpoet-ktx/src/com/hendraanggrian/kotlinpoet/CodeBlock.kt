package com.hendraanggrian.kotlinpoet

import com.hendraanggrian.kotlinpoet.collections.CodeBlockContainer
import com.squareup.kotlinpoet.CodeBlock

/**
 * Converts string to [CodeBlock] using formatted [args].
 *
 * @see kotlin.text.format
 */
fun codeBlockOf(format: String, vararg args: Any): CodeBlock =
    CodeBlock.of(format, *args)

/**
 * Builds a new [CodeBlock],
 * by populating newly created [CodeBlockBuilder] using provided [builderAction] and then building it.
 */
inline fun buildCodeBlock(builderAction: CodeBlockBuilder.() -> Unit): CodeBlock =
    CodeBlock.builder().build(builderAction)

/** Modify existing [CodeBlock.Builder] using provided [builderAction] and then building it. */
inline fun CodeBlock.Builder.build(builderAction: CodeBlockBuilder.() -> Unit): CodeBlock =
    CodeBlockBuilder(this).apply(builderAction).build()

/** Wrapper of [CodeBlock.Builder], providing DSL support as a replacement to Java builder. */
@KotlinpoetDslMarker
class CodeBlockBuilder(private val nativeBuilder: CodeBlock.Builder) :
    CodeBlockContainer() {

    /** Returns true if this builder contains no code. */
    fun isEmpty(): Boolean = nativeBuilder.isEmpty()

    /** Returns true if this builder contains code. */
    fun isNotEmpty(): Boolean = nativeBuilder.isNotEmpty()

    /** Adds code using named arguments. */
    fun addNamed(format: String, arguments: Map<String, *>) {
        nativeBuilder.addNamed(format, arguments)
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

    override fun appendln(format: String, vararg args: Any) {
        nativeBuilder.addStatement(format, *args)
    }

    override fun append(code: CodeBlock) {
        nativeBuilder.add(code)
    }

    /** Indent current code. */
    fun indent() {
        nativeBuilder.indent()
    }

    /** Unindent current code. */
    fun unindent() {
        nativeBuilder.unindent()
    }

    /** Returns native spec. */
    fun build(): CodeBlock = nativeBuilder.build()
}
