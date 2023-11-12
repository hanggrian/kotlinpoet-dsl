package com.hendraanggrian.kotlinpoet

import com.squareup.kotlinpoet.asTypeName
import org.jetbrains.annotations.NotNull
import kotlin.test.Test
import kotlin.test.assertEquals

class TypeNameTest {
    @Test
    fun nullable() {
        assertEquals("kotlin.Int?", "${Int::class.asTypeName().nullable()}")
    }

    @Test
    fun annotate() {
        assertEquals(
            "@org.jetbrains.annotations.NotNull kotlin.Int",
            "${
                Int::class.asTypeName().annotate(NotNull::class.name.asAnnotationSpec())
            }",
        )
    }
}
