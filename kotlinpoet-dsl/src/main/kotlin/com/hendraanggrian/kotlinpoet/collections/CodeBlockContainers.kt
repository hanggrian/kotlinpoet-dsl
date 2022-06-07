package com.hendraanggrian.kotlinpoet.collections

import com.hendraanggrian.kotlinpoet.SpecDslMarker
import com.squareup.kotlinpoet.CodeBlock

interface CodeBlockAppendable {

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

interface CodeBlockContainer : CodeBlockAppendable {

    /** Add named code to this container. */
    fun appendNamed(format: String, args: Map<String, *>)

    override fun appendLine(): Unit = appendLine("")

    /** Insert code flow with custom initialization [configuration]. */
    fun appendControlFlow(format: String, vararg args: Any, configuration: () -> Unit) {
        beginControlFlow(format, *args)
        configuration()
        endControlFlow()
    }

    /**
     * Manually starts the control flow, as opposed to [appendControlFlow].
     * @see CodeBlock.Builder.beginControlFlow
     */
    fun beginControlFlow(format: String, vararg args: Any)

    /**
     * Continues the control flow.
     * @see CodeBlock.Builder.nextControlFlow
     */
    fun nextControlFlow(format: String, vararg args: Any)

    /**
     * Manually stops the control flow.
     * @see CodeBlock.Builder.endControlFlow
     */
    fun endControlFlow()

    /** Remove all code lines in this container. */
    fun clear()
}

/** A [KdocContainer] is responsible for managing a set of code instances. */
interface KdocContainer : CodeBlockAppendable {

    override fun appendLine(): Unit = append(SystemProperties.LINE_SEPARATOR)

    override fun appendLine(format: String, vararg args: Any) {
        append(format, *args)
        appendLine()
    }

    override fun appendLine(code: CodeBlock) {
        append(code)
        appendLine()
    }

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
@SpecDslMarker
class KdocContainerScope internal constructor(container: KdocContainer) : KdocContainer by container
