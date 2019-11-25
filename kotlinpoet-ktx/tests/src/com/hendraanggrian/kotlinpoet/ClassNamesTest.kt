package com.hendraanggrian.kotlinpoet

import com.squareup.kotlinpoet.asClassName
import kotlin.test.Test
import kotlin.test.assertEquals

class ClassNamesTest {

    @Test fun asClassName() {
        assertEquals(String::class.asClassName(), asClassName<String>())
    }
}