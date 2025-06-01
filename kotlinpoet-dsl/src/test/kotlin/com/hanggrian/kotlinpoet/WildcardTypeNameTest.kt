package com.hanggrian.kotlinpoet

import com.google.common.truth.Truth.assertThat
import com.squareup.kotlinpoet.CHAR_SEQUENCE
import org.jetbrains.annotations.NotNull
import kotlin.test.Test

class WildcardTypeNameTest {
    @Test
    fun nullable() =
        assertThat("${CHAR_SEQUENCE.producer.nullable()}").isEqualTo("out kotlin.CharSequence?")

    @Test
    fun annotate() =
        assertThat(
            "${
                CHAR_SEQUENCE.producer.annotate(annotationSpecOf(NotNull::class.name))
            }",
        ).isEqualTo("@org.jetbrains.annotations.NotNull out kotlin.CharSequence")

    @Test
    fun producer() = assertThat("${CHAR_SEQUENCE.producer}").isEqualTo("out kotlin.CharSequence")

    @Test
    fun consumer() = assertThat("${CHAR_SEQUENCE.consumer}").isEqualTo("in kotlin.CharSequence")
}
