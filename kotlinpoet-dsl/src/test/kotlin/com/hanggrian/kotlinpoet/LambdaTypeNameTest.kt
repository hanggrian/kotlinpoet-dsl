package com.hanggrian.kotlinpoet

import com.google.common.truth.Truth.assertThat
import com.squareup.kotlinpoet.INT
import com.squareup.kotlinpoet.STRING
import com.squareup.kotlinpoet.UNIT
import org.jetbrains.annotations.NotNull
import kotlin.test.Test

class LambdaTypeNameTest {
    @Test
    fun nullable() =
        assertThat("${lambdaTypeNamed(returns = UNIT).nullable()}")
            .isEqualTo("(() -> kotlin.Unit)?")

    @Test
    fun suspending() =
        assertThat("${lambdaTypeNamed(returns = UNIT).suspending()}")
            .isEqualTo("suspend () -> kotlin.Unit")

    @Test
    fun annotate() =
        assertThat(
            "${
                lambdaTypeNamed(returns = UNIT).annotate(annotationSpecOf(NotNull::class.name))
            }",
        ).isEqualTo(
            "@org.jetbrains.annotations.NotNull () -> kotlin.Unit",
        )

    @Test
    fun lambdaTypeNamed() {
        assertThat("${lambdaTypeNamed(returns = UNIT)}")
            .isEqualTo("() -> kotlin.Unit")
        assertThat(
            "${
                lambdaTypeNamed(buildParameterSpec("data", STRING) {}, returns = UNIT)
            }",
        ).isEqualTo("(`data`: kotlin.String) -> kotlin.Unit")
    }

    @Test
    fun lambdaBy() {
        assertThat("${INT.lambdaBy(returns = UNIT)}")
            .isEqualTo("kotlin.Int.() -> kotlin.Unit")
        assertThat("${INT.lambdaBy(buildParameterSpec("data", STRING) {}, returns = UNIT)}")
            .isEqualTo("kotlin.Int.(`data`: kotlin.String) -> kotlin.Unit")
    }
}
