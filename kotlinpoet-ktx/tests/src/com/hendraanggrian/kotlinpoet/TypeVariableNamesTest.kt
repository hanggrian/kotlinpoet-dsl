package com.hendraanggrian.kotlinpoet

import com.squareup.kotlinpoet.TypeVariableName
import kotlin.test.Test
import kotlin.test.assertEquals

class TypeVariableNamesTest {

    @Test
    fun typeVariableNameOf() {
        assertEquals(
            TypeVariableName("Hello"),
            typeVariableNameOf("Hello")
        )
        assertEquals(
            TypeVariableName("Hello", asClassName<String>()),
            typeVariableNameOf("Hello", asClassName<String>())
        )
        assertEquals(
            TypeVariableName("Hello", String::class.java),
            typeVariableNameOf("Hello", String::class.java)
        )
        assertEquals(
            TypeVariableName("Hello", String::class),
            typeVariableNameOf("Hello", String::class)
        )
    }
}