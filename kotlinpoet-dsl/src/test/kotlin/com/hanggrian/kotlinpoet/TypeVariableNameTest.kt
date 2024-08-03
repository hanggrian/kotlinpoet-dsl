package com.hanggrian.kotlinpoet

import com.squareup.kotlinpoet.CHAR_SEQUENCE
import org.jetbrains.annotations.NotNull
import kotlin.test.Test
import kotlin.test.assertEquals

class TypeVariableNameTest {
    @Test
    fun nullable() {
        assertEquals("T?", "${"T".generics.nullable()}")
    }

    @Test
    fun annotate() {
        assertEquals(
            "@org.jetbrains.annotations.NotNull T",
            "${
                "T".generics.annotate(annotationSpecOf(NotNull::class.name))
            }",
        )
    }

    @Test
    fun generics() {
        assertEquals("T", "T".generics.toString())
    }

    @Test
    fun genericsBy() {
        assertEquals(
            """
            public fun <T : kotlin.CharSequence> go() {
            }

            """.trimIndent(),
            "${buildFunSpec("go") { typeVariables.add("T".genericsBy(CHAR_SEQUENCE)) }}",
        )
        assertEquals(
            """
            public fun <T : java.lang.CharSequence> go() {
            }

            """.trimIndent(),
            "${
                buildFunSpec("go") {
                    typeVariables.add("T".genericsBy(CharSequence::class.java))
                }
            }",
        )
        assertEquals(
            """
            public fun <T : kotlin.CharSequence> go() {
            }

            """.trimIndent(),
            "${buildFunSpec("go") { typeVariables.add("T".genericsBy(CharSequence::class)) }}",
        )
    }
}
