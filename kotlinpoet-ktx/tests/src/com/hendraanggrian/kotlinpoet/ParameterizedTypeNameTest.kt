package com.hendraanggrian.kotlinpoet

import com.squareup.kotlinpoet.DOUBLE
import com.squareup.kotlinpoet.INT
import com.squareup.kotlinpoet.asClassName
import kotlin.test.Test
import kotlin.test.assertEquals

class ParameterizedTypeNameTest {

    @Test fun parameterizedBy() {
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
}