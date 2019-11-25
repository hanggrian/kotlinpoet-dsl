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
        assertEquals(expected, buildCode {
            appendln("int total = 0")
            beginFlow("for (int i = 0; i < 10; i++)")
            appendln("total += i")
            endFlow()
        })
    }

    @Test fun escapeSpecialChar() {
        assertFails { "100%".formatCode() }
        assertEquals("100%", "${"100%%".formatCode()}")
        assertEquals("100%S", "${"100%%S".formatCode()}")
        assertEquals("100%S%java.lang.System", "${"100%%S%%%T".formatCode(System::class)}")
    }
}