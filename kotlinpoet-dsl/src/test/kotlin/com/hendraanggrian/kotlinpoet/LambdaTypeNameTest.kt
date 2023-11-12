package com.hendraanggrian.kotlinpoet

import com.squareup.kotlinpoet.INT
import com.squareup.kotlinpoet.STRING
import com.squareup.kotlinpoet.UNIT
import org.jetbrains.annotations.NotNull
import kotlin.test.Test
import kotlin.test.assertEquals

class LambdaTypeNameTest {
    @Test
    fun nullable() {
        assertEquals("(() -> kotlin.Unit)?", "${lambdaTypeNamed(returns = UNIT).nullable()}")
    }

    @Test
    fun suspending() {
        assertEquals("suspend () -> kotlin.Unit", "${lambdaTypeNamed(returns = UNIT).suspending()}")
    }

    @Test
    fun annotate() {
        assertEquals(
            "@org.jetbrains.annotations.NotNull () -> kotlin.Unit",
            "${
                lambdaTypeNamed(returns = UNIT).annotate(NotNull::class.name.asAnnotationSpec())
            }",
        )
    }

    @Test
    fun lambdaTypeNamed() {
        assertEquals(
            "() -> kotlin.Unit",
            "${lambdaTypeNamed(returns = UNIT)}",
        )
        assertEquals(
            "(`data`: kotlin.String) -> kotlin.Unit",
            "${
                lambdaTypeNamed(buildParameterSpec("data", STRING) {}, returns = UNIT)
            }",
        )
    }

    @Test
    fun lambdaBy() {
        assertEquals(
            "kotlin.Int.() -> kotlin.Unit",
            "${INT.lambdaBy(returns = UNIT)}",
        )
        assertEquals(
            "kotlin.Int.(`data`: kotlin.String) -> kotlin.Unit",
            "${INT.lambdaBy(buildParameterSpec("data", STRING) {}, returns = UNIT)}",
        )
    }
}
