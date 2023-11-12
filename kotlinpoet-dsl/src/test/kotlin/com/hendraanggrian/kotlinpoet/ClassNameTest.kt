package com.hendraanggrian.kotlinpoet

import com.squareup.kotlinpoet.STRING
import org.jetbrains.annotations.NotNull
import kotlin.test.Test
import kotlin.test.assertEquals

class ClassNameTest {
    @Test
    fun nullable() {
        assertEquals("kotlin.String?", "${STRING.nullable()}")
    }

    @Test
    fun annotate() {
        assertEquals(
            "@org.jetbrains.annotations.NotNull kotlin.String",
            "${
                STRING.annotate(NotNull::class.name.asAnnotationSpec())
            }",
        )
    }

    @Test
    fun name() {
        assertEquals("kotlin.String", "${String::class.name}")
    }

    @Test
    fun name2() {
        assertEquals("java.lang.String", "${String::class.java.name2}")
    }

    @Test
    fun javaName() {
        assertEquals("java.lang.String", "${String::class.javaName}")
    }

    @Test
    fun classNamed() {
        assertEquals("java.lang.String", "${classNamed("java.lang.String")}")
        assertEquals("java.lang.String", "${classNamed("java.lang", "String")}")
    }
}
