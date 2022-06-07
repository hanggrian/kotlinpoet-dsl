package com.hendraanggrian.kotlinpoet

import kotlin.test.Test
import kotlin.test.assertEquals

class ClassNameTest {

    @Test
    fun classNameOf() {
        assertEquals("java.lang.String", "${classNameOf("java.lang.String")}")
        assertEquals("java.lang.String", "${classNameOf("java.lang", "String")}")
        assertEquals("kotlin.String", "${classNameOf<String>()}")
    }
}
