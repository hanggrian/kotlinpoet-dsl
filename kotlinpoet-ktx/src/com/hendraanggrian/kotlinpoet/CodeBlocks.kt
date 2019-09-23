package com.hendraanggrian.kotlinpoet

import com.hendraanggrian.kotlinpoet.dsl.CodeBlockCollection
import com.squareup.kotlinpoet.CodeBlock

/** Converts string to [CodeBlock] using formatted [args]. */
fun String.toCode(vararg args: Any): CodeBlock =
    CodeBlock.of(this, *args)

/**
 * Builds a new [CodeBlock],
 * by populating newly created [CodeBlockBlockBuilder] using provided [builderAction] and then building it.
 */
inline fun buildCode(builderAction: CodeBlockBlockBuilder.() -> Unit): CodeBlock =
    CodeBlockBlockBuilder(CodeBlock.builder()).apply(builderAction).build()

/** Wrapper of [CodeBlock.Builder], providing DSL support as a replacement to Java builder. */
@KotlinpoetDslMarker
class CodeBlockBlockBuilder @PublishedApi internal constructor(private val nativeBuilder: CodeBlock.Builder) :
    CodeBlockCollection() {

    /** Returns true if this builder contains no code. */
    fun isEmpty(): Boolean =
        nativeBuilder.isEmpty()

    /** Returns true if this builder contains code. */
    fun isNotEmpty(): Boolean =
        nativeBuilder.isNotEmpty()

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
    fun build(): CodeBlock =
        nativeBuilder.build()
}
