package com.hendraanggrian.kotlinpoet

import com.squareup.kotlinpoet.DOUBLE
import com.squareup.kotlinpoet.INT
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.STRING
import com.squareup.kotlinpoet.UNIT
import kotlin.test.Test
import kotlin.test.assertEquals

class LambdaTypeNameTest {

    @Test
    fun parameterSpecListParameters() {
        val int = ParameterSpec.builder("int", Int::class).build()
        val string = ParameterSpec.builder("string", String::class).build()
        assertEquals(
            "kotlin.Double.() -> kotlin.Unit",
            "${UNIT.lambdaBy(emptyList(), receiver = DOUBLE)}"
        )
        assertEquals(
            "kotlin.Double.(int: kotlin.Int, string: kotlin.String) -> kotlin.Unit",
            "${UNIT.lambdaBy(listOf(int, string), receiver = DOUBLE)}"
        )
        assertEquals(
            "kotlin.Double.(int: kotlin.Int, string: kotlin.String) -> kotlin.Unit",
            "${Unit::class.java.lambdaBy(listOf(int, string), receiver = Double::class.java)}"
        )
        assertEquals(
            "kotlin.Double.(int: kotlin.Int, string: kotlin.String) -> kotlin.Unit",
            "${Unit::class.lambdaBy(listOf(int, string), receiver = Double::class)}"
        )
    }

    @Test
    fun typeVarargParameters() {
        assertEquals(
            "kotlin.Double.() -> kotlin.Unit",
            "${UNIT.lambdaBy(receiver = DOUBLE)}"
        )
        assertEquals(
            "kotlin.Double.(kotlin.Int, kotlin.String) -> kotlin.Unit",
            "${UNIT.lambdaBy(INT, STRING, receiver = DOUBLE)}"
        )
        assertEquals(
            "kotlin.Double.(kotlin.Int, java.lang.String) -> kotlin.Unit",
            "${Unit::class.java.lambdaBy(Int::class.java, String::class.java, receiver = Double::class.java)}"
        )
        assertEquals(
            "kotlin.Double.(kotlin.Int, kotlin.String) -> kotlin.Unit",
            "${Unit::class.lambdaBy(Int::class, String::class, receiver = Double::class)}"
        )
    }

    @Test
    fun parameterSpecVarargParameters() {
        val int = ParameterSpec.builder("int", Int::class).build()
        val string = ParameterSpec.builder("string", String::class).build()
        assertEquals(
            "kotlin.Double.() -> kotlin.Unit",
            "${UNIT.lambdaBy(*emptyArray<ParameterSpec>(), receiver = DOUBLE)}"
        )
        assertEquals(
            "kotlin.Double.(int: kotlin.Int, string: kotlin.String) -> kotlin.Unit",
            "${UNIT.lambdaBy(int, string, receiver = DOUBLE)}"
        )
        assertEquals(
            "kotlin.Double.(int: kotlin.Int, string: kotlin.String) -> kotlin.Unit",
            "${Unit::class.java.lambdaBy(int, string, receiver = Double::class.java)}"
        )
        assertEquals(
            "kotlin.Double.(int: kotlin.Int, string: kotlin.String) -> kotlin.Unit",
            "${Unit::class.lambdaBy(int, string, receiver = Double::class)}"
        )
    }
}
