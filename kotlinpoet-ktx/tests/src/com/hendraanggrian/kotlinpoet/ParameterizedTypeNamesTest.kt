package com.hendraanggrian.kotlinpoet

import com.squareup.kotlinpoet.ANY
import com.squareup.kotlinpoet.asClassName
import kotlin.test.Test
import kotlin.test.assertEquals

class ParameterizedTypeNamesTest {

    @Test
    fun parameterizedBy() {
        assertEquals(
            List::class.java.asClassName() + ANY,
            List::class.java.asClassName().parameterizedBy(ANY)
        )
        assertEquals(
            List::class.java + String::class.java,
            List::class.java.parameterizedBy(String::class.java)
        )
        assertEquals(
            List::class + String::class,
            List::class.parameterizedBy(String::class)
        )
    }
}