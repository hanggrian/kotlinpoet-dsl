package com.hendraanggrian.kotlinpoet.dsl

import com.hendraanggrian.kotlinpoet.CodeBlockBuilder
import com.hendraanggrian.kotlinpoet.KotlinpoetDslMarker
import com.hendraanggrian.kotlinpoet.buildCode
import com.squareup.kotlinpoet.CodeBlock

private interface CodeAppendable {

    /** Add code block to this container. */
    fun append(format: String, vararg args: Any)

    /** Add code block to this container. */
    fun append(code: CodeBlock)

    /** Add empty new line to this container. */
    fun appendln()

    /** Add code block with a new line to this container. */
    fun appendln(format: String, vararg args: Any)

    /** Add code block with a new line to this container. */
    fun appendln(code: CodeBlock) {
        append(code)
        appendln()
    }
}

abstract class CodeCollection internal constructor() : CodeAppendable {

    /** Add code block with custom initialization [builderAction], returning the block added. */
    inline fun append(builderAction: CodeBlockBuilder.() -> Unit): CodeBlock =
        buildCode(builderAction).also { append(it) }

    override fun appendln() =
        appendln("")

    /** Add code block with custom initialization [builderAction] and a new line to this container, returning the block added. */
    inline fun appendln(builderAction: CodeBlockBuilder.() -> Unit) =
        appendln(buildCode(builderAction))

    /** Starts the control flow. */
    abstract fun beginFlow(flow: String, vararg args: Any)

    /** Continues the control flow. */
    abstract fun nextFlow(flow: String, vararg args: Any)

    /** Stops the control flow. */
    abstract fun endFlow()
}

/** A [KdocContainer] is responsible for managing a set of code instances. */
abstract class KdocContainer internal constructor() : CodeAppendable {

    /** Add code block with custom initialization [builderAction], returning the block added. */
    inline fun append(builderAction: CodeBlockBuilder.() -> Unit): CodeBlock =
        buildCode(builderAction).also { append(it) }

    override fun appendln(): Unit =
        append("\n")

    override fun appendln(format: String, vararg args: Any) =
        append("$format\n", *args)

    /** Add code block with custom initialization [builderAction] and a new line to this container, returning the block added. */
    inline fun appendln(builderAction: CodeBlockBuilder.() -> Unit) =
        appendln(buildCode(builderAction))

    /** Convenient method to add code block with operator function. */
    operator fun plusAssign(value: String) {
        append(value)
    }

    /** Convenient method to add code block with operator function. */
    operator fun plusAssign(code: CodeBlock) {
        append(code)
    }

    /** Configure this container with DSL. */
    inline operator fun invoke(configuration: KdocContainerScope.() -> Unit) =
        KdocContainerScope(this).configuration()
}

/** Receiver for the `kdoc` block providing an extended set of operators for the configuration. */
@KotlinpoetDslMarker
class KdocContainerScope @PublishedApi internal constructor(private val container: KdocContainer) :
    KdocContainer(), CodeAppendable by container {

    override fun appendln(code: CodeBlock): Unit = container.appendln(code)
    override fun appendln(): Unit = container.appendln()
    override fun appendln(format: String, vararg args: Any): Unit = container.appendln(format, *args)
}
