package io.github.hendraanggrian.kotlinpoet.dsl

import com.squareup.kotlinpoet.CodeBlock
import io.github.hendraanggrian.kotlinpoet.CodeBlockBuilder
import io.github.hendraanggrian.kotlinpoet.buildCodeBlock

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

abstract class CodeBlockHandler internal constructor() : CodeBlockAppendable {

    /** Add named code to this container. */
    abstract fun appendNamed(format: String, args: Map<String, *>)

    /** Add code block with custom initialization [configuration]. */
    inline fun append(configuration: CodeBlockBuilder.() -> Unit): Unit =
        append(buildCodeBlock(configuration))

    override fun appendLine(): Unit = appendLine("")

    /** Add code block with custom initialization [configuration] and a new line to this container. */
    inline fun appendLine(configuration: CodeBlockBuilder.() -> Unit): Unit =
        appendLine(buildCodeBlock(configuration))

    /** Insert code flow with custom initialization [configuration]. */
    fun appendFlow(flow: String, vararg args: Any, configuration: () -> Unit) {
        beginFlow(flow, *args)
        configuration()
        endFlow()
    }

    /**
     * Continues the control flow.
     * @see CodeBlock.Builder.nextControlFlow
     */
    abstract fun nextFlow(flow: String, vararg args: Any)

    /**
     * Manually starts the control flow, as opposed to [appendFlow].
     * @see CodeBlock.Builder.beginControlFlow
     */
    internal abstract fun beginFlow(flow: String, vararg args: Any)

    /**
     * Manually stops the control flow.
     * @see CodeBlock.Builder.endControlFlow
     */
    internal abstract fun endFlow()
}

/** A [KdocHandler] is responsible for managing a set of code instances. */
abstract class KdocHandler internal constructor() : CodeBlockAppendable {

    /** Add code block with custom initialization [configuration]. */
    inline fun append(configuration: CodeBlockBuilder.() -> Unit): Unit =
        append(buildCodeBlock(configuration))

    override fun appendLine(): Unit = append(SystemProperties.LINE_SEPARATOR)

    override fun appendLine(format: String, vararg args: Any) {
        append(format, *args)
        appendLine()
    }

    override fun appendLine(code: CodeBlock) {
        append(code)
        appendLine()
    }

    /** Add code block with custom initialization [configuration] and a new line to this container. */
    inline fun appendLine(configuration: CodeBlockBuilder.() -> Unit): Unit =
        appendLine(buildCodeBlock(configuration))

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
class KdocHandlerScope(private val handler: KdocHandler) :
    KdocHandler(),
    CodeBlockAppendable by handler {

    override fun appendLine(): Unit = handler.appendLine()
    override fun appendLine(code: CodeBlock): Unit = handler.appendLine(code)
    override fun appendLine(format: String, vararg args: Any): Unit = handler.appendLine(format, *args)
}
