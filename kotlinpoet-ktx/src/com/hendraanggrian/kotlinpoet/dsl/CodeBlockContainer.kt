package com.hendraanggrian.kotlinpoet.dsl

import com.hendraanggrian.kotlinpoet.CodeBlockBuilder
import com.hendraanggrian.kotlinpoet.KotlinpoetDslMarker
import com.hendraanggrian.kotlinpoet.buildCode
import com.squareup.kotlinpoet.CodeBlock

private interface CodeBlockAppendable {

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

abstract class CodeBlockContainer : CodeBlockAppendable {

    /** Add code block with custom initialization [builderAction], returning the block added. */
    inline fun append(builderAction: CodeBlockBuilder.() -> Unit): CodeBlock =
        buildCode(builderAction).also { append(it) }

    override fun appendln() = appendln("")

    /** Add code block with custom initialization [builderAction] and a new line to this container, returning the block added. */
    inline fun appendln(builderAction: CodeBlockBuilder.() -> Unit) = appendln(buildCode(builderAction))

    /** Starts the control flow. */
    abstract fun beginFlow(flow: String, vararg args: Any)

    /** Continues the control flow. */
    abstract fun nextFlow(flow: String, vararg args: Any)

    /** Stops the control flow. */
    abstract fun endFlow()
}

/** A [KdocContainer] is responsible for managing a set of code instances. */
abstract class KdocContainer : CodeBlockAppendable {

    /** Add code block with custom initialization [builderAction], returning the block added. */
    inline fun append(builderAction: CodeBlockBuilder.() -> Unit): CodeBlock =
        buildCode(builderAction).also { append(it) }

    override fun appendln(): Unit = append(SystemProperties.LINE_SEPARATOR)

    override fun appendln(format: String, vararg args: Any) {
        append(format, *args)
        appendln()
    }

    /** Add code block with custom initialization [builderAction] and a new line to this container, returning the block added. */
    inline fun appendln(builderAction: CodeBlockBuilder.() -> Unit) = appendln(buildCode(builderAction))

    /** Convenient method to add code block with operator function. */
    operator fun plusAssign(value: String) {
        append(value)
    }

    /** Convenient method to add code block with operator function. */
    operator fun plusAssign(code: CodeBlock) {
        append(code)
    }

    /**
     * @see kotlin.text.SystemProperties
     */
    private object SystemProperties {
        /** Line separator for current system. */
        @JvmField
        val LINE_SEPARATOR =
            checkNotNull(System.getProperty("line.separator")) { "Unable to obtain separator character." }
    }
}

/** Receiver for the `kdoc` block providing an extended set of operators for the configuration. */
@KotlinpoetDslMarker
class KdocContainerScope(private val container: KdocContainer) : KdocContainer(), CodeBlockAppendable by container {

    override fun appendln(code: CodeBlock): Unit = container.appendln(code)
    override fun appendln(): Unit = container.appendln()
    override fun appendln(format: String, vararg args: Any): Unit = container.appendln(format, *args)
}
