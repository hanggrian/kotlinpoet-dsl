package com.hendraanggrian.kotlinpoet

import com.squareup.kotlinpoet.asClassName
import kotlin.test.Test
import kotlin.test.assertEquals

class ParameterizedTypeNameTest {

    @Test
    fun plusParameter() {
        assertEquals(
            "kotlin.collections.List<kotlin.String>",
            "${List::class.asClassName().plusParameter<String>()}",
        )
        assertEquals(
            "java.util.List<java.lang.String>",
            "${List::class.java.plusParameter<String>()}"
        )
        assertEquals(
            "kotlin.collections.List<kotlin.String>",
            "${List::class.plusParameter<String>()}"
        )
    }
}
