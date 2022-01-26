package com.hendraanggrian.kotlinpoet

import com.squareup.kotlinpoet.asClassName
import kotlin.test.Test
import kotlin.test.assertEquals

class TypeVariableNameTest {

    @Test
    fun genericsBy() {
        assertEquals("T", "${"T".genericsBy()}")

        assertEquals(
            """
            public fun <T : kotlin.CharSequence> go(): kotlin.Unit {
            }
            
            """.trimIndent(),
            "${buildFunSpec("go") { typeVariables.add("T", CharSequence::class.asClassName()) }}"
        )
        assertEquals(
            """
            public fun <T : java.lang.CharSequence> go(): kotlin.Unit {
            }
            
            """.trimIndent(),
            "${buildFunSpec("go") { typeVariables.add("T", CharSequence::class.java) }}"
        )
        assertEquals(
            """
            public fun <T : kotlin.CharSequence> go(): kotlin.Unit {
            }
            
            """.trimIndent(),
            "${buildFunSpec("go") { typeVariables.add("T", CharSequence::class) }}"
        )

        assertEquals(
            """
            public fun <T : kotlin.CharSequence> go(): kotlin.Unit {
            }
            
            """.trimIndent(),
            "${buildFunSpec("go") { typeVariables.add("T", listOf(CharSequence::class.asClassName())) }}"
        )
        assertEquals(
            """
            public fun <T : java.lang.CharSequence> go(): kotlin.Unit {
            }
            
            """.trimIndent(),
            "${buildFunSpec("go") { typeVariables.add("T", listOf(CharSequence::class.java)) }}"
        )
        assertEquals(
            """
            public fun <T : kotlin.CharSequence> go(): kotlin.Unit {
            }
            
            """.trimIndent(),
            "${buildFunSpec("go") { typeVariables.add("T", listOf(CharSequence::class)) }}"
        )
    }
}