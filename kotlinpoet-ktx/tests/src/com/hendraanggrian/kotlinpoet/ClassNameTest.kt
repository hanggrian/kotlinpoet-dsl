package com.hendraanggrian.kotlinpoet

import kotlin.test.Test
import kotlin.test.assertEquals

class ClassNameTest {
    private companion object {
        const val EXPECTED = "com.hendraanggrian.kotlinpoet.ClassNameTest.MyClass"
    }

    @Test fun test() {
        assertEquals(EXPECTED, "${"com.hendraanggrian.kotlinpoet".classOf("ClassNameTest", "MyClass")}")
        assertEquals(EXPECTED, "${classNameOf<MyClass>()}")
    }

    class MyClass
}