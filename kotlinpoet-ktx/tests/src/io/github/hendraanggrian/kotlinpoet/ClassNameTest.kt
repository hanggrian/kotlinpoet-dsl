package io.github.hendraanggrian.kotlinpoet

import com.example.MyClass
import com.squareup.kotlinpoet.asClassName
import kotlin.test.Test
import kotlin.test.assertEquals

class ClassNameTest {
    private companion object {
        const val EXPECTED = "com.example.MyClass"
    }

    @Test fun classOf() {
        assertEquals(EXPECTED, "${"com.example".classOf("MyClass")}")
    }

    @Test fun asClassName() {
        assertEquals(EXPECTED, "${MyClass::class.java.asClassName()}")
        assertEquals(EXPECTED, "${MyClass::class.asClassName()}")
    }
}