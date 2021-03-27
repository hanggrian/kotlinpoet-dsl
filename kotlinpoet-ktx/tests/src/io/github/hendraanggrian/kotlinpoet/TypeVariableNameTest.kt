package io.github.hendraanggrian.kotlinpoet

import com.squareup.kotlinpoet.INT
import kotlin.test.Test
import kotlin.test.assertEquals

class TypeVariableNameTest {

    @Test
    fun noBounds() = assertEquals("T", "${"T".typeVarOf()}")

    @Test
    fun classNameBounds() = assertEquals(
        """
            public fun <T : kotlin.Int> go(): kotlin.Unit {
            }
            
            """.trimIndent(),
        "${buildFunSpec("go") { typeVariables.add("T", INT) }}"
    )

    @Test
    fun classBounds() = assertEquals(
        """
            public fun <T : java.lang.Integer> go(): kotlin.Unit {
            }
            
            """.trimIndent(),
        "${buildFunSpec("go") { typeVariables.add("T", java.lang.Integer::class.java) }}"
    )

    @Test
    fun kclassBounds() = assertEquals(
        """
            public fun <T : kotlin.Int> go(): kotlin.Unit {
            }
            
            """.trimIndent(),
        "${buildFunSpec("go") { typeVariables.add("T", java.lang.Integer::class) }}"
    )
}