package com.hanggrian.kotlinpoet

import com.google.common.truth.Truth.assertThat
import com.squareup.kotlinpoet.CodeBlock
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class CodeBlockBuilderTest {
    @Test
    fun isEmpty() {
        buildCodeBlock {
            assertTrue(isEmpty())
            append("text")
            assertFalse(isEmpty())
        }
    }

    @Test
    fun isNotEmpty() {
        buildCodeBlock {
            assertFalse(isNotEmpty())
            append("text")
            assertTrue(isNotEmpty())
        }
    }

    @Test
    fun controlFlow() {
        assertThat(
            buildCodeBlock {
                beginControlFlow("format", "arg")
                nextControlFlow("format", "arg")
                endControlFlow()
            },
        ).isEqualTo(
            CodeBlock
                .builder()
                .beginControlFlow("format", "arg")
                .nextControlFlow("format", "arg")
                .endControlFlow()
                .build(),
        )
    }

    @Test
    fun append() {
        assertThat(
            buildCodeBlock {
                append("text")
                append(codeBlockOf("some code"))
            },
        ).isEqualTo(
            CodeBlock
                .builder()
                .add("text")
                .add(CodeBlock.of("some code"))
                .build(),
        )
    }

    @Test
    fun appendLine() {
        assertThat(
            buildCodeBlock {
                appendLine()
                appendLine("text")
                appendLine(codeBlockOf("some code"))
            },
        ).isEqualTo(
            CodeBlock
                .builder()
                .addStatement("")
                .addStatement("text")
                .addStatement("some code")
                .build(),
        )
    }

    @Test
    fun clear() {
        buildCodeBlock {
            assertFalse(isNotEmpty())
            append("text")
            clear()
            assertTrue(isEmpty())
        }
    }

    @Test
    fun `Simple example`() {
        assertThat(
            buildCodeBlock {
                appendLine("int total = 0")
                beginControlFlow("for (int i = 0; i < 10; i++)")
                appendLine("total += i")
                endControlFlow()
            },
        ).isEqualTo(
            CodeBlock
                .builder()
                .addStatement("int total = 0")
                .beginControlFlow("for (int i = 0; i < 10; i++)")
                .addStatement("total += i")
                .endControlFlow()
                .build(),
        )
    }

    @Test
    fun `Escape special characters`() {
        assertFails { codeBlockOf("100%") }
        assertEquals("100%", "${codeBlockOf("100%%")}")
        assertEquals("100%S", "${codeBlockOf("100%%S")}")
        assertEquals("100%S%java.lang.System", "${codeBlockOf("100%%S%%%T", System::class)}")
    }
}
