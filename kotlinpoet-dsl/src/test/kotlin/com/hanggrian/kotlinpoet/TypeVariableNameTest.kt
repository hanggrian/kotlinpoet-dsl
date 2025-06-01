package com.hanggrian.kotlinpoet

import com.google.common.truth.Truth.assertThat
import com.squareup.kotlinpoet.CHAR_SEQUENCE
import org.jetbrains.annotations.NotNull
import kotlin.test.Test

class TypeVariableNameTest {
    @Test
    fun nullable() = assertThat("${"T".generics.nullable()}").isEqualTo("T?")

    @Test
    fun annotate() =
        assertThat(
            "${
                "T".generics.annotate(annotationSpecOf(NotNull::class.name))
            }",
        ).isEqualTo("@org.jetbrains.annotations.NotNull T")

    @Test
    fun generics() = assertThat("T".generics.toString()).isEqualTo("T")

    @Test
    fun genericsBy() {
        assertThat("${buildFunSpec("go") { typeVariables.add("T".genericsBy(CHAR_SEQUENCE)) }}")
            .isEqualTo(
                """
                public fun <T : kotlin.CharSequence> go() {
                }

                """.trimIndent(),
            )
        assertThat(
            "${
                buildFunSpec("go") {
                    typeVariables.add("T".genericsBy(CharSequence::class.java))
                }
            }",
        ).isEqualTo(
            """
            public fun <T : java.lang.CharSequence> go() {
            }

            """.trimIndent(),
        )
        assertThat(
            "${buildFunSpec("go") { typeVariables.add("T".genericsBy(CharSequence::class)) }}",
        ).isEqualTo(
            """
            public fun <T : kotlin.CharSequence> go() {
            }

            """.trimIndent(),
        )
    }
}
