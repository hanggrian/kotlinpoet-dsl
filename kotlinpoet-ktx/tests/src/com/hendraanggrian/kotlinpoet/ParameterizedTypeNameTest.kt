package com.hendraanggrian.kotlinpoet

import com.squareup.kotlinpoet.DOUBLE
import com.squareup.kotlinpoet.INT
import com.squareup.kotlinpoet.asClassName
import kotlin.test.Test
import kotlin.test.assertEquals

class ParameterizedTypeNameTest {

    @Test fun multiple() {
        assertEquals(
            "kotlin.Pair<kotlin.Int, kotlin.Double>",
            "${Pair::class.java.asClassName().parameterizedBy(INT, DOUBLE)}"
        )
        assertEquals(
            "kotlin.Pair<kotlin.Int, kotlin.Double>",
            "${Pair::class.java.parameterizedBy(Int::class.java, Double::class.java)}"
        )
        assertEquals(
            "kotlin.Pair<kotlin.Int, kotlin.Double>",
            "${Pair::class.parameterizedBy(Int::class, Double::class)}"
        )
    }

    @Test fun single() {
        assertEquals(
            "kotlin.collections.List<kotlin.String>",
            "${List::class.asClassName().parameterizedBy<String>()}"
        )
        assertEquals(
            "java.util.List<java.lang.String>",
            "${List::class.java.parameterizedBy<String>()}"
        )
        assertEquals(
            "kotlin.collections.List<kotlin.String>",
            "${List::class.parameterizedBy<String>()}"
        )
    }
}