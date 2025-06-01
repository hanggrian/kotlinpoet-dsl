package com.hanggrian.kotlinpoet

import com.google.common.truth.Truth.assertThat
import com.squareup.kotlinpoet.asTypeName
import org.jetbrains.annotations.NotNull
import kotlin.test.Test

class TypeNameTest {
    @Test
    fun nullable() = assertThat("${Int::class.name.nullable()}").isEqualTo("kotlin.Int?")

    @Test
    fun annotate() =
        assertThat(
            "${
                Int::class.asTypeName().annotate(annotationSpecOf(NotNull::class.name))
            }",
        ).isEqualTo("@org.jetbrains.annotations.NotNull kotlin.Int")
}
