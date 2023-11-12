package com.hendraanggrian.kotlinpoet

import com.squareup.kotlinpoet.CHAR_SEQUENCE
import org.jetbrains.annotations.NotNull
import kotlin.test.Test
import kotlin.test.assertEquals

class WildcardTypeNameTest {
    @Test
    fun nullable() {
        assertEquals("out kotlin.CharSequence?", "${CHAR_SEQUENCE.producer.nullable()}")
    }

    @Test
    fun annotate() {
        assertEquals(
            "@org.jetbrains.annotations.NotNull out kotlin.CharSequence",
            "${
                CHAR_SEQUENCE.producer.annotate(NotNull::class.name.asAnnotationSpec())
            }",
        )
    }

    @Test
    fun producer() {
        assertEquals("out kotlin.CharSequence", "${CHAR_SEQUENCE.producer}")
    }

    @Test
    fun consumer() {
        assertEquals("in kotlin.CharSequence", "${CHAR_SEQUENCE.consumer}")
    }
}
