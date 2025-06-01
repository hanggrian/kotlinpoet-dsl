package com.hanggrian.kotlinpoet

import com.google.common.truth.Truth.assertThat
import com.squareup.kotlinpoet.STRING
import org.jetbrains.annotations.NotNull
import kotlin.test.Test

class ClassNameTest {
    @Test
    fun nullable() = assertThat("${STRING.nullable()}").isEqualTo("kotlin.String?")

    @Test
    fun annotate() =
        assertThat(
            "${
                STRING.annotate(annotationSpecOf(NotNull::class.name))
            }",
        ).isEqualTo("@org.jetbrains.annotations.NotNull kotlin.String")

    @Test
    fun name() = assertThat("${String::class.name}").isEqualTo("kotlin.String")

    @Test
    fun name2() = assertThat("${String::class.java.name2}").isEqualTo("java.lang.String")

    @Test
    fun javaName() = assertThat("${String::class.javaName}").isEqualTo("java.lang.String")

    @Test
    fun classNamed() {
        assertThat("${classNamed("java.lang.String")}").isEqualTo("java.lang.String")
        assertThat("${classNamed("java.lang", "String")}").isEqualTo("java.lang.String")
    }
}
