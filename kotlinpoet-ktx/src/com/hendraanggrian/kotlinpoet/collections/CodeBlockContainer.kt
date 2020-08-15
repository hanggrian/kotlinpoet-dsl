package com.hendraanggrian.kotlinpoet.collections

import com.hendraanggrian.kotlinpoet.CodeBlockBuilder
import com.hendraanggrian.kotlinpoet.buildCodeBlock
import com.squareup.kotlinpoet.CodeBlock

private interface CodeBlockAppendable {

    /** Add code with arguments to this container. */
    fun append(format: String, vararg args: Any)

    /** Add code block to this container. */
    fun append(code: CodeBlock)

    /** Add empty new line to this container. */
    fun appendLine()

    /** Add code block with a new line to this container. */
    fun appendLine(format: String, vararg args: Any)

    /** Add code block with a new line to this container. */
    fun appendLine(code: CodeBlock) {
        // Kotlinpoet's CodeBlock doesn't support adding CodeBlock as statement
        // so copy from `com.squareup.javapoet.CodeBlock.addStatement`
        appendLine("%L", code)
    }
}

abstract class CodeBlockContainer internal constructor() : CodeBlockAppendable {

    /** Add named code to this container. */
    abstract fun appendNamed(format: String, args: Map<String, *>)

    /** Add code block with custom initialization [builderAction]. */
    inline fun append(builderAction: CodeBlockBuilder.() -> Unit): Unit =
        append(buildCodeBlock(builderAction))

    override fun appendLine(): Unit = appendLine("")

    /** Add code block with custom initialization [builderAction] and a new line to this container. */
    inline fun appendLine(builderAction: CodeBlockBuilder.() -> Unit): Unit =
        appendLine(buildCodeBlock(builderAction))

    /** Starts the control flow. */
    abstract fun beginFlow(flow: String, vararg args: Any)

    /** Continues the control flow. */
    abstract fun nextFlow(flow: String, vararg args: Any)

    /** Stops the control flow. */
    abstract fun endFlow()
}

/** A [KdocContainer] is responsible for managing a set of code instances. */
abstract class KdocContainer internal constructor() : CodeBlockAppendable {

    /** Add code block with custom initialization [builderAction]. */
    inline fun append(builderAction: CodeBlockBuilder.() -> Unit): Unit =
        append(buildCodeBlock(builderAction))

    override fun appendLine(): Unit = append(SystemProperties.LINE_SEPARATOR)

    override fun appendLine(format: String, vararg args: Any) {
        append(format, *args)
        appendLine()
    }

    override fun appendLine(code: CodeBlock) {
        append(code)
        appendLine()
    }

    /** Add code block with custom initialization [builderAction] and a new line to this container. */
    inline fun appendLine(builderAction: CodeBlockBuilder.() -> Unit): Unit =
        appendLine(buildCodeBlock(builderAction))

    /** Convenient method to add code block with operator function. */
    operator fun plusAssign(value: String): Unit = append(value)

    /** Convenient method to add code block with operator function. */
    operator fun plusAssign(code: CodeBlock): Unit = append(code)

    /**
     * @see kotlin.text.SystemProperties
     */
    private object SystemProperties {
        /** Line separator for current system. */
        @JvmField
        val LINE_SEPARATOR = System.getProperty("line.separator")!!
    }
}

/** Receiver for the `kdoc` block providing an extended set of operators for the configuration. */
class KdocContainerScope(private val container: KdocContainer) :
    KdocContainer(),
    CodeBlockAppendable by container {

    override fun appendLine(): Unit = container.appendLine()
    override fun appendLine(code: CodeBlock): Unit = container.appendLine(code)
    override fun appendLine(format: String, vararg args: Any): Unit = container.appendLine(format, *args)
}
