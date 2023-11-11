package com.hendraanggrian.kotlinpoet

import com.squareup.kotlinpoet.CHAR_SEQUENCE
import com.squareup.kotlinpoet.INT
import com.squareup.kotlinpoet.STRING
import com.squareup.kotlinpoet.UNIT
import kotlin.test.Test
import kotlin.test.assertEquals

class TypeNamesTest {
    @Test
    fun nullable() {
        assertEquals("kotlin.String?", "${STRING.nullable}")
    }

    @Test
    fun name() {
        assertEquals("kotlin.String", "${String::class.name}")
    }

    @Test
    fun javaName() {
        assertEquals("java.lang.String", "${String::class.javaName}")
    }

    @Test
    fun classNamed() {
        assertEquals("java.lang.String", "${classNamed("java.lang.String")}")
        assertEquals("java.lang.String", "${classNamed("java.lang", "String")}")
    }

    @Test
    fun lambdaTypeNamed() {
        assertEquals(
            "() -> kotlin.Unit",
            "${lambdaTypeNamed(returns = UNIT)}",
        )
        assertEquals(
            "(`data`: kotlin.String) -> kotlin.Unit",
            "${
                lambdaTypeNamed(buildParameterSpec("data", STRING) {}, returns = UNIT)
            }",
        )
    }

    @Test
    fun lambdaBy() {
        assertEquals(
            "kotlin.Int.() -> kotlin.Unit",
            "${INT.lambdaBy(returns = UNIT)}",
        )
        assertEquals(
            "kotlin.Int.(`data`: kotlin.String) -> kotlin.Unit",
            "${INT.lambdaBy(buildParameterSpec("data", STRING) {}, returns = UNIT)}",
        )
    }

    @Test
    fun parameterizedBy() {
        assertEquals(
            "kotlin.collections.List<kotlin.String>",
            "${List::class.name.parameterizedBy<String>()}",
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

    @Test
    fun producer() {
        assertEquals("out kotlin.CharSequence", "${CHAR_SEQUENCE.producer}")
    }

    @Test
    fun consumer() {
        assertEquals("in kotlin.CharSequence", "${CHAR_SEQUENCE.consumer}")
    }
}
