package com.hendraanggrian.kotlinpoet

import com.squareup.kotlinpoet.INT
import com.squareup.kotlinpoet.STRING
import com.squareup.kotlinpoet.asClassName
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class ParameterizedTypeNameTest {

    @Test fun classNameReceiver() {
        val className = Pair::class.asClassName()
        assertFails { className.parameterizedBy() }
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
    }

    @Test fun classReceiver() {
        val `class` = Pair::class.java
        assertFails { `class`.parameterizedBy() }
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
    }

    @Test fun kclassReceiver() {
        val kclass = Pair::class
        assertFails { kclass.parameterizedBy() }
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
}