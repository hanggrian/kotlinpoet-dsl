package com.hendraanggrian.kotlinpoet

import com.squareup.kotlinpoet.STRING
import com.squareup.kotlinpoet.UNIT
import kotlin.test.Test
import kotlin.test.assertEquals

class LambdaTypeNameTest {

    @Test fun lambdaBy() {
        assertEquals("(kotlin.String) -> kotlin.Unit", "${null.lambdaBy(STRING, returnType = UNIT)}")
        assertEquals(
            "(java.lang.String) -> kotlin.Unit",
            "${null.lambdaBy(String::class.java, returnType = Unit::class.java)}"
        )
        assertEquals("(kotlin.String) -> kotlin.Unit", "${null.lambdaBy(String::class, returnType = Unit::class)}")
    }
}