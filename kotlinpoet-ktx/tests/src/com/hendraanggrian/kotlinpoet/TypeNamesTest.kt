package com.hendraanggrian.kotlinpoet

import com.squareup.kotlinpoet.BOOLEAN
import kotlin.test.Test
import kotlin.test.assertEquals

class TypeNamesTest {
    private companion object {
        val CLASS_NAME = "kotlin".classOf("String")
        val LAMBDA_TYPE_NAME = Unit::class.lambdaBy(String::class)
        val PARAMETERIZED_TYPE_NAME = List::class.parameterizedBy(Int::class)
        val TYPE_NAME = BOOLEAN
        val TYPE_VARIABLE_NAME = "T".typeVarOf()
        val WILDCARD_TYPE_NAME = Any::class.wildcardProducerOf()
    }

    @Test
    fun nullability() {
        val nullableClassName = CLASS_NAME.asNullable()
        val nullableLambdaTypeName = LAMBDA_TYPE_NAME.asNullable()
        val nullableParameterizedTypeName = PARAMETERIZED_TYPE_NAME.asNullable()
        val nullableTypeName = TYPE_NAME.asNullable()
        val nullableTypeVariableName = TYPE_VARIABLE_NAME.asNullable()
        val nullableWildcardTypeName = WILDCARD_TYPE_NAME.asNullable()

        assertEquals("kotlin.String?", "$nullableClassName")
        assertEquals("((kotlin.String) -> kotlin.Unit)?", "$nullableLambdaTypeName")
        assertEquals("kotlin.collections.List<kotlin.Int>?", "$nullableParameterizedTypeName")
        assertEquals("kotlin.Boolean?", "$nullableTypeName")
        assertEquals("T?", "$nullableTypeVariableName")
        assertEquals("out kotlin.Any?", "$nullableWildcardTypeName")

        assertEquals("kotlin.String", "${nullableClassName.asNotNull()}")
        assertEquals("(kotlin.String) -> kotlin.Unit", "${nullableLambdaTypeName.asNotNull()}")
        assertEquals("kotlin.collections.List<kotlin.Int>", "${nullableParameterizedTypeName.asNotNull()}")
        assertEquals("kotlin.Boolean", "${nullableTypeName.asNotNull()}")
        assertEquals("T", "${nullableTypeVariableName.asNotNull()}")
        assertEquals("out kotlin.Any", "${nullableWildcardTypeName.asNotNull()}")
    }

    @Test
    fun annotations() {
        val deprecatedClassName = "kotlin".classOf("Deprecated")
        val deprecatedSpec = annotationSpecOf(deprecatedClassName)
        assertEquals(
            "@kotlin.Deprecated kotlin.String",
            listOf(
                "${CLASS_NAME.annotate(deprecatedSpec)}",
                "${CLASS_NAME.annotate(deprecatedClassName)}",
                "${CLASS_NAME.annotate(Deprecated::class.java)}",
                "${CLASS_NAME.annotate(Deprecated::class)}",
                "${CLASS_NAME.annotate<Deprecated>()}"
            ).distinct().single()
        )
        assertEquals(
            "@kotlin.Deprecated (kotlin.String) -> kotlin.Unit",
            listOf(
                "${LAMBDA_TYPE_NAME.annotate(deprecatedSpec)}",
                "${LAMBDA_TYPE_NAME.annotate(deprecatedClassName)}",
                "${LAMBDA_TYPE_NAME.annotate(Deprecated::class.java)}",
                "${LAMBDA_TYPE_NAME.annotate(Deprecated::class)}",
                "${LAMBDA_TYPE_NAME.annotate<Deprecated>()}"
            ).distinct().single()
        )
        assertEquals(
            "@kotlin.Deprecated kotlin.collections.List<kotlin.Int>",
            listOf(
                "${PARAMETERIZED_TYPE_NAME.annotate(deprecatedSpec)}",
                "${PARAMETERIZED_TYPE_NAME.annotate(deprecatedClassName)}",
                "${PARAMETERIZED_TYPE_NAME.annotate(Deprecated::class.java)}",
                "${PARAMETERIZED_TYPE_NAME.annotate(Deprecated::class)}",
                "${PARAMETERIZED_TYPE_NAME.annotate<Deprecated>()}"
            ).distinct().single()
        )
        assertEquals(
            "@kotlin.Deprecated kotlin.Boolean",
            listOf(
                "${TYPE_NAME.annotate(deprecatedSpec)}",
                "${TYPE_NAME.annotate(deprecatedClassName)}",
                "${TYPE_NAME.annotate(Deprecated::class.java)}",
                "${TYPE_NAME.annotate(Deprecated::class)}",
                "${TYPE_NAME.annotate<Deprecated>()}"
            ).distinct().single()
        )
        assertEquals(
            "@kotlin.Deprecated T",
            listOf(
                "${TYPE_VARIABLE_NAME.annotate(deprecatedSpec)}",
                "${TYPE_VARIABLE_NAME.annotate(deprecatedClassName)}",
                "${TYPE_VARIABLE_NAME.annotate(Deprecated::class.java)}",
                "${TYPE_VARIABLE_NAME.annotate(Deprecated::class)}",
                "${TYPE_VARIABLE_NAME.annotate<Deprecated>()}"
            ).distinct().single()
        )
        assertEquals(
            "@kotlin.Deprecated out kotlin.Any",
            listOf(
                "${WILDCARD_TYPE_NAME.annotate(deprecatedSpec)}",
                "${WILDCARD_TYPE_NAME.annotate(deprecatedClassName)}",
                "${WILDCARD_TYPE_NAME.annotate(Deprecated::class.java)}",
                "${WILDCARD_TYPE_NAME.annotate(Deprecated::class)}",
                "${WILDCARD_TYPE_NAME.annotate<Deprecated>()}"
            ).distinct().single()
        )
    }
}