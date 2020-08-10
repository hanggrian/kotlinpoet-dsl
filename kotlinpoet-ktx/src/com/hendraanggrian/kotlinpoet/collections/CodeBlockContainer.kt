package com.hendraanggrian.kotlinpoet.collections

import com.hendraanggrian.kotlinpoet.CodeBlockBuilder
import com.hendraanggrian.kotlinpoet.KotlinpoetDslMarker
import com.hendraanggrian.kotlinpoet.buildCodeBlock
import com.squareup.kotlinpoet.CodeBlock

private interface CodeBlockAppendable {

    /** Add code with arguments to this container. */
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

abstract class CodeBlockContainer internal constructor() : CodeBlockAppendable {

    /** Add named code to this container. */
    abstract fun appendNamed(format: String, args: Map<String, *>)

    /** Add code block with custom initialization [builderAction]. */
    inline fun append(builderAction: CodeBlockBuilder.() -> Unit): Unit = append(buildCodeBlock(builderAction))

    override fun appendln(): Unit = appendln("")

    /** Add code block with custom initialization [builderAction] and a new line to this container. */
    inline fun appendln(builderAction: CodeBlockBuilder.() -> Unit): Unit = appendln(buildCodeBlock(builderAction))

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
    inline fun append(builderAction: CodeBlockBuilder.() -> Unit): Unit = append(buildCodeBlock(builderAction))

    override fun appendln(): Unit = append(SystemProperties.LINE_SEPARATOR)

    override fun appendln(format: String, vararg args: Any) {
        append(format, *args)
        appendln()
    }

    /** Add code block with custom initialization [builderAction] and a new line to this container. */
    inline fun appendln(builderAction: CodeBlockBuilder.() -> Unit): Unit = appendln(buildCodeBlock(builderAction))

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
@KotlinpoetDslMarker
class KdocContainerScope(private val container: KdocContainer) :
    KdocContainer(),
    CodeBlockAppendable by container {

    override fun appendln(): Unit = container.appendln()
    override fun appendln(code: CodeBlock): Unit = container.appendln(code)
    override fun appendln(format: String, vararg args: Any): Unit = container.appendln(format, *args)
}
