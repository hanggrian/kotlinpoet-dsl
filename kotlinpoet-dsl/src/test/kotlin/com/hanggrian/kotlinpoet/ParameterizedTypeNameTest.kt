package com.hanggrian.kotlinpoet

import com.google.common.truth.Truth.assertThat
import org.jetbrains.annotations.NotNull
import kotlin.test.Test

class ParameterizedTypeNameTest {
    @Test
    fun nullable() =
        assertThat("${List::class.name.parameterizedBy<String>().nullable()}")
            .isEqualTo("kotlin.collections.List<kotlin.String>?")

    @Test
    fun annotate() =
        assertThat(
            "${
                List::class.name.parameterizedBy<String>()
                    .annotate(annotationSpecOf(NotNull::class.name))
            }",
        ).isEqualTo("@org.jetbrains.annotations.NotNull kotlin.collections.List<kotlin.String>")

    @Test
    fun parameterizedBy() =
        assertThat("${List::class.name.parameterizedBy<String>()}")
            .isEqualTo("kotlin.collections.List<kotlin.String>")
}
