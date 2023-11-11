package com.hendraanggrian.kotlinpoet

import com.example.Annotation1
import com.example.Annotation2
import com.example.Annotation3
import com.example.Parameter1
import com.example.Parameter2
import com.example.Parameter3
import com.example.Parameter4
import com.example.Parameter5
import com.example.Parameter6
import com.example.Parameter7
import com.example.Property1
import com.google.common.truth.Truth.assertThat
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.asTypeName
import kotlin.test.Test
import kotlin.test.assertFalse

class ParameterSpecHandlerTest {
    @Test
    fun parameter() {
        assertThat(
            buildFunSpec("test") {
                parameter("parameter1", Parameter1::class.name)
                parameter("parameter2", Parameter2::class.name) { kdoc("text2") }
                parameter("parameter3", Parameter3::class.java)
                parameter("parameter4", Parameter4::class.java) { kdoc("text4") }
                parameter("parameter5", Parameter5::class)
                parameter("parameter6", Parameter6::class) { kdoc("text6") }
                parameter<Parameter7>("parameter7")
            }.parameters,
        ).containsExactly(
            ParameterSpec.builder("parameter1", Parameter1::class).build(),
            ParameterSpec.builder("parameter2", Parameter2::class).addKdoc("text2").build(),
            ParameterSpec.builder("parameter3", Parameter3::class).build(),
            ParameterSpec.builder("parameter4", Parameter4::class).addKdoc("text4").build(),
            ParameterSpec.builder("parameter5", Parameter5::class).build(),
            ParameterSpec.builder("parameter6", Parameter6::class).addKdoc("text6").build(),
            ParameterSpec.builder("parameter7", Parameter7::class).build(),
        )
    }

    @Test
    fun parametering() {
        assertThat(
            buildFunSpec("test") {
                val parameter1 by parametering(Parameter1::class.name)
                val parameter2 by parametering(Parameter2::class.name) { kdoc("text2") }
                val parameter3 by parametering(Parameter3::class.java)
                val parameter4 by parametering(Parameter4::class.java) { kdoc("text4") }
                val parameter5 by parametering(Parameter5::class)
                val parameter6 by parametering(Parameter6::class) { kdoc("text6") }
            }.parameters,
        ).containsExactly(
            ParameterSpec.builder("parameter1", Parameter1::class).build(),
            ParameterSpec.builder("parameter2", Parameter2::class).addKdoc("text2").build(),
            ParameterSpec.builder("parameter3", Parameter3::class).build(),
            ParameterSpec.builder("parameter4", Parameter4::class).addKdoc("text4").build(),
            ParameterSpec.builder("parameter5", Parameter5::class).build(),
            ParameterSpec.builder("parameter6", Parameter6::class).addKdoc("text6").build(),
        )
    }

    @Test
    fun invoke() {
        assertThat(
            buildFunSpec("test") {
                parameters {
                    "parameter1"(Parameter1::class.asTypeName()) { kdoc("text1") }
                    "parameter2"(Parameter2::class.java) { kdoc("text2") }
                    "parameter3"(Parameter3::class) { kdoc("text3") }
                }
            }.parameters,
        ).containsExactly(
            ParameterSpec.builder("parameter1", Parameter1::class).addKdoc("text1").build(),
            ParameterSpec.builder("parameter2", Parameter2::class).addKdoc("text2").build(),
            ParameterSpec.builder("parameter3", Parameter3::class).addKdoc("text3").build(),
        )
    }
}

class ParameterSpecBuilderTest {
    @Test
    fun kdoc() {
        assertThat(
            buildParameterSpec("parameter1", Property1::class.name) { kdoc("some doc") },
        ).isEqualTo(
            ParameterSpec.builder("parameter1", Property1::class).addKdoc("some doc").build(),
        )
    }

    @Test
    fun annotation() {
        assertThat(
            buildParameterSpec("parameter1", Property1::class.name) {
                annotation(Annotation1::class.name)
                annotation(Annotation2::class)
                annotation<Annotation3>()
                assertFalse(annotations.isEmpty())
            },
        ).isEqualTo(
            ParameterSpec.builder("parameter1", Property1::class)
                .addAnnotation(Annotation1::class)
                .addAnnotation(Annotation2::class)
                .addAnnotation(Annotation3::class)
                .build(),
        )
    }

    @Test
    fun modifiers() {
        assertThat(
            buildParameterSpec("parameter1", Property1::class.name) {
                modifiers(VARARG)
                assertFalse(modifiers.isEmpty())
            },
        ).isEqualTo(
            ParameterSpec.builder("parameter1", Property1::class).addModifiers(VARARG).build(),
        )
    }

    @Test
    fun defaultValue() {
        assertThat(
            buildParameterSpec("parameter1", Property1::class.name) { defaultValue("value1") },
        ).isEqualTo(
            ParameterSpec.builder("parameter1", Property1::class).defaultValue("value1").build(),
        )
        assertThat(
            buildParameterSpec("parameter2", Property1::class.name) {
                defaultValue = codeBlockOf("value2")
            },
        ).isEqualTo(
            ParameterSpec.builder("parameter2", Property1::class)
                .defaultValue(CodeBlock.of("value2"))
                .build(),
        )
    }
}
