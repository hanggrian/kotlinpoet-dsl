package com.hendraanggrian.kotlinpoet

import com.squareup.kotlinpoet.CodeBlock
import kotlin.test.Test
import kotlin.test.assertEquals

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
        assertEquals(
            "100%",
            "100%%".formatCode().toString()
        )
        assertEquals(
            "You & me",
            "You & me".formatCode().toString()
        )
    }
}