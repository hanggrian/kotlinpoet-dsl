package com.hendraanggrian.kotlinpoet

import kotlin.test.Test
import kotlin.test.assertEquals

class TypeVariableNameTest {

    @Test fun test() {
        assertEquals("T", "${"T".typeVariableBy()}")
    }
}