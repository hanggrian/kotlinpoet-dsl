package com.hendraanggrian.kotlinpoet

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.UNIT
import kotlin.test.Test
import kotlin.test.assertEquals

class FunSpecBuilderTest {
    private val expected = FunSpec.builder("main")
        .addKdoc("firstJavadoc")
        .addKdoc(
            CodeBlock.builder()
                .add("secondJavadoc")
                .build()
        )
        .addAnnotation(AnnotationSpec.builder(Deprecated::class).build())
        .addModifiers(KModifier.PUBLIC)
        .returns(UNIT)
        .addParameter(ParameterSpec.builder("param", Array<String>::class).build())
        .addComment("Some comment")
        .addCode("doSomething()")
        .build()

    @Test fun simple() {
        assertEquals(expected, buildFunSpec("main") {
            kdoc.append("firstJavadoc")
            kdoc.append {
                append("secondJavadoc")
            }
            annotations.add<Deprecated>()
            addModifiers(KModifier.PUBLIC)
            returns = UNIT
            parameters.add<Array<String>>("param")
            addComment("Some comment")
            append("doSomething()")
        })
    }

    @Test fun invocation() {
        assertEquals(expected, buildFunSpec("main") {
            kdoc {
                append("firstJavadoc")
                append {
                    append("secondJavadoc")
                }
            }
            annotations {
                add<Deprecated>()
            }
            addModifiers(KModifier.PUBLIC)
            returns = UNIT
            parameters {
                add<Array<String>>("param")
            }
            addComment("Some comment")
            append("doSomething()")
        })
    }
}