package com.hendraanggrian.kotlinpoet

import com.example.MyClass
import com.squareup.kotlinpoet.asTypeName
import kotlin.test.Test
import kotlin.test.assertEquals

class WildcardNameTest {
    private companion object {
        const val EXPECTED_SUBTYPE = "out com.example.MyClass"
        const val EXPECTED_SUPERTYPE = "in com.example.MyClass"
    }

    @Test fun producer() {
        assertEquals(EXPECTED_SUBTYPE, "${MyClass::class.asTypeName().wildcardProducerOf()}")
        assertEquals(EXPECTED_SUBTYPE, "${MyClass::class.java.wildcardProducerOf()}")
        assertEquals(EXPECTED_SUBTYPE, "${MyClass::class.wildcardProducerOf()}")
    }

    @Test fun consumer() {
        assertEquals(EXPECTED_SUPERTYPE, "${MyClass::class.asTypeName().wildcardConsumerOf()}")
        assertEquals(EXPECTED_SUPERTYPE, "${MyClass::class.java.wildcardConsumerOf()}")
        assertEquals(EXPECTED_SUPERTYPE, "${MyClass::class.wildcardConsumerOf()}")
    }
}