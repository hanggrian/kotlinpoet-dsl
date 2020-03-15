package com.hendraanggrian.kotlinpoet

import com.squareup.kotlinpoet.CodeBlock
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class CodeBlockBuilderTest {
    private val expected = CodeBlock.builder()
        .addStatement("int total = 0")
        .beginControlFlow("for (int i = 0; i < 10; i++)")
        .addStatement("total += i")
        .endControlFlow()
        .build()

    @Test fun simple() {
        assertEquals(expected, buildCodeBlock {
            appendln("int total = 0")
            beginFlow("for (int i = 0; i < 10; i++)")
            appendln("total += i")
            endFlow()
        })
    }

    @Test fun escapeSpecialChar() {
        assertFails { codeBlockOf("100%") }
        assertEquals("100%", "${codeBlockOf("100%%")}")
        assertEquals("100%S", "${codeBlockOf("100%%S")}")
        assertEquals("100%S%java.lang.System", "${codeBlockOf("100%%S%%%T", System::class)}")
    }
}