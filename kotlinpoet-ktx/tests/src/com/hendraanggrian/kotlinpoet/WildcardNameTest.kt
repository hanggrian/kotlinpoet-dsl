package com.hendraanggrian.kotlinpoet

import com.squareup.kotlinpoet.asTypeName
import kotlin.test.Test
import kotlin.test.assertEquals

class WildcardNameTest {

    @Test
    fun toUpperWildcardTypeName() {
        assertEquals(
            "out kotlin.CharSequence",
            "${CharSequence::class.asTypeName().toUpperWildcardTypeName()}"
        )
        assertEquals(
            "out java.lang.CharSequence",
            "${CharSequence::class.java.toUpperWildcardTypeName()}"
        )
        assertEquals(
            "out kotlin.CharSequence",
            "${CharSequence::class.toUpperWildcardTypeName()}"
        )
    }

    @Test
    fun wildcardTypeNameUpperOf() {
        assertEquals("out kotlin.CharSequence", "${wildcardTypeNameUpperOf<CharSequence>()}")
    }

    @Test
    fun toLowerWildcardTypeName() {
        assertEquals(
            "in kotlin.CharSequence",
            "${CharSequence::class.asTypeName().toLowerWildcardTypeName()}"
        )
        assertEquals(
            "in java.lang.CharSequence",
            "${CharSequence::class.java.toLowerWildcardTypeName()}"
        )
        assertEquals(
            "in kotlin.CharSequence",
            "${CharSequence::class.toLowerWildcardTypeName()}"
        )
    }

    @Test
    fun wildcardTypeNameLowerOf() {
        assertEquals("in kotlin.CharSequence", "${wildcardTypeNameLowerOf<CharSequence>()}")
    }
}