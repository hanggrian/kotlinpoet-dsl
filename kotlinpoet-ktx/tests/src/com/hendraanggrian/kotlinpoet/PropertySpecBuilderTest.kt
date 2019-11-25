package com.hendraanggrian.kotlinpoet

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import kotlin.test.Test
import kotlin.test.assertEquals

class PropertySpecBuilderTest {
    private val expected = PropertySpec.builder("name", String::class)
        .addKdoc("firstJavadoc")
        .addKdoc(
            CodeBlock.builder()
                .add("secondJavadoc")
                .build()
        )
        .addAnnotation(AnnotationSpec.builder(Deprecated::class).build())
        .addModifiers(KModifier.PUBLIC, KModifier.FINAL)
        .initializer("value")
        .build()

    @Test fun simple() {
        assertEquals(expected, buildProperty<String>("name") {
            kdoc.append("firstJavadoc")
            kdoc.append {
                append("secondJavadoc")
            }
            annotations.add<Deprecated>()
            addModifiers(KModifier.PUBLIC, KModifier.FINAL)
            initializer("value")
        })
    }

    @Test fun invocation() {
        assertEquals(expected, buildProperty<String>("name") {
            kdoc {
                append("firstJavadoc")
                append {
                    append("secondJavadoc")
                }
            }
            annotations {
                add<Deprecated>()
            }
            addModifiers(KModifier.PUBLIC, KModifier.FINAL)
            initializer("value")
        })
    }
}