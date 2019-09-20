package com.hendraanggrian.kotlinpoet

import com.squareup.kotlinpoet.ANY
import com.squareup.kotlinpoet.ClassName
import kotlin.test.Test
import kotlin.test.assertEquals

class TypeNamesTest {

    @Test
    fun staticFields() {
        assertEquals(ANY, ClassName("kotlin", "Any"))
    }

    @Test
    fun test() {
    }
}