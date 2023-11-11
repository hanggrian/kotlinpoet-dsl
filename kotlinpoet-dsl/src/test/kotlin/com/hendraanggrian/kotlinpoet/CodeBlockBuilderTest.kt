package com.hendraanggrian.kotlinpoet

import com.google.common.truth.Truth.assertThat
import com.squareup.kotlinpoet.CodeBlock
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class CodeBlockBuilderTest {
    @Test
    fun simple() {
        assertThat(
            buildCodeBlock {
                appendLine("int total = 0")
                beginControlFlow("for (int i = 0; i < 10; i++)")
                appendLine("total += i")
                endControlFlow()
            },
        ).isEqualTo(
            CodeBlock.builder()
                .addStatement("int total = 0")
                .beginControlFlow("for (int i = 0; i < 10; i++)")
                .addStatement("total += i")
                .endControlFlow()
                .build(),
        )
    }

    @Test
    fun escapeSpecialChar() {
        assertFails { codeBlockOf("100%") }
        assertEquals("100%", "${codeBlockOf("100%%")}")
        assertEquals("100%S", "${codeBlockOf("100%%S")}")
        assertEquals("100%S%java.lang.System", "${codeBlockOf("100%%S%%%T", System::class)}")
    }
}
