package com.hendraanggrian.kotlinpoet

import org.jetbrains.annotations.NotNull
import kotlin.test.Test
import kotlin.test.assertEquals

class ParameterizedTypeNameTest {
    @Test
    fun nullable() {
        assertEquals(
            "kotlin.collections.List<kotlin.String>?",
            "${List::class.name.parameterizedBy<String>().nullable()}",
        )
    }

    @Test
    fun annotate() {
        assertEquals(
            "@org.jetbrains.annotations.NotNull kotlin.collections.List<kotlin.String>",
            "${
                List::class.name.parameterizedBy<String>()
                    .annotate(NotNull::class.name.asAnnotationSpec())
            }",
        )
    }

    @Test
    fun parameterizedBy() {
        assertEquals(
            "kotlin.collections.List<kotlin.String>",
            "${List::class.name.parameterizedBy<String>()}",
        )
    }
}
