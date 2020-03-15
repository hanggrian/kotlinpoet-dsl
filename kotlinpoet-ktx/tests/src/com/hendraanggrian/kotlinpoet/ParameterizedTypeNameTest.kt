package com.hendraanggrian.kotlinpoet

import com.squareup.kotlinpoet.INT
import com.squareup.kotlinpoet.STRING
import com.squareup.kotlinpoet.asClassName
import kotlin.test.Test
import kotlin.test.assertEquals

class ParameterizedTypeNameTest {
    private val className = Pair::class.asClassName()
    private val `class` = Pair::class.java
    private val kclass = Pair::class

    @Test fun vararg() {
        assertEquals(
            "kotlin.Pair<kotlin.Int, kotlin.String>",
            "${className.parameterizedBy(INT, STRING)}"
        )
        assertEquals(
            "kotlin.Pair<kotlin.Int, java.lang.String>",
            "${className.parameterizedBy(Int::class.java, String::class.java)}"
        )
        assertEquals(
            "kotlin.Pair<kotlin.Int, kotlin.String>",
            "${className.parameterizedBy(Int::class, String::class)}"
        )

        assertEquals(
            "kotlin.Pair<kotlin.Int, kotlin.String>",
            "${`class`.parameterizedBy(INT, STRING)}"
        )
        assertEquals(
            "kotlin.Pair<kotlin.Int, java.lang.String>",
            "${`class`.parameterizedBy(Int::class.java, String::class.java)}"
        )
        assertEquals(
            "kotlin.Pair<kotlin.Int, kotlin.String>",
            "${`class`.parameterizedBy(Int::class, String::class)}"
        )

        assertEquals(
            "kotlin.Pair<kotlin.Int, kotlin.String>",
            "${kclass.parameterizedBy(INT, STRING)}"
        )
        assertEquals(
            "kotlin.Pair<kotlin.Int, java.lang.String>",
            "${kclass.parameterizedBy(Int::class.java, String::class.java)}"
        )
        assertEquals(
            "kotlin.Pair<kotlin.Int, kotlin.String>",
            "${kclass.parameterizedBy(Int::class, String::class)}"
        )
    }

    @Test fun list() {
        assertEquals(
            "kotlin.Pair<kotlin.Int, kotlin.String>",
            "${className.parameterizedBy(listOf(INT, STRING))}"
        )

        assertEquals(
            "kotlin.Pair<kotlin.Int, java.lang.String>",
            "${`class`.parameterizedBy(listOf(Int::class.java, String::class.java))}"
        )

        assertEquals(
            "kotlin.Pair<kotlin.Int, kotlin.String>",
            "${kclass.parameterizedBy(listOf(Int::class, String::class))}"
        )
    }
}