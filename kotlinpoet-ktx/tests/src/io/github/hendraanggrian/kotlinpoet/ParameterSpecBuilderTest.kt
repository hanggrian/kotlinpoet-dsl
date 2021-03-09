package io.github.hendraanggrian.kotlinpoet

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ParameterSpec
import kotlin.test.Test
import kotlin.test.assertEquals

class ParameterSpecBuilderTest {
    private val expected = ParameterSpec.builder("name", String::class)
        .addAnnotation(AnnotationSpec.builder(Deprecated::class).build())
        .addModifiers(PUBLIC, FINAL)
        .build()

    @Test fun simple() {
        assertEquals(expected, buildParameterSpec<String>("name") {
            annotations.add<Deprecated>()
            addModifiers(PUBLIC, FINAL)
        })
    }

    @Test fun invocation() {
        assertEquals(expected, buildParameterSpec<String>("name") {
            annotations {
                add<Deprecated>()
            }
            addModifiers(PUBLIC, FINAL)
        })
    }
}