package io.github.hendraanggrian.kotlinpoet

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.CodeBlock
import kotlin.test.Test
import kotlin.test.assertEquals

class AnnotationSpecBuilderTest {
    private val expected = AnnotationSpec.builder(Deprecated::class)
        .addMember("message", "Old stuff")
        .addMember(
            CodeBlock.builder()
                .add("codeValue")
                .build()
        )
        .build()

    @Test
    fun simple() {
        assertEquals(expected, buildAnnotationSpec<Deprecated> {
            addMember("message", "Old stuff")
            addMember {
                append("codeValue")
            }
        })
    }

    @Test
    fun invocation() {
        assertEquals(expected, buildAnnotationSpec<Deprecated> {
            addMember("message", "Old stuff")
            addMember {
                append("codeValue")
            }
        })
    }
}