package com.hanggrian.kotlinpoet

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
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ParameterSpecHandlerTest {
    @Test
    fun add() {
        assertThat(
            buildFunSpec("test") {
                parameters.add("parameter1", Parameter1::class.name)
                parameters.add("parameter2", Parameter2::class.java)
                parameters.add("parameter3", Parameter3::class)
                parameters.add<Parameter4>("parameter4")
                parameters {
                    add("parameter5", Parameter5::class.name) { addKdoc("text5") }
                    add("parameter6", Parameter6::class.java) { addKdoc("text6") }
                    add("parameter7", Parameter7::class) { addKdoc("text7") }
                }
            }.parameters,
        ).containsExactly(
            ParameterSpec.builder("parameter1", Parameter1::class).build(),
            ParameterSpec.builder("parameter2", Parameter2::class).build(),
            ParameterSpec.builder("parameter3", Parameter3::class).build(),
            ParameterSpec.builder("parameter4", Parameter4::class).build(),
            ParameterSpec.builder("parameter5", Parameter5::class).addKdoc("text5").build(),
            ParameterSpec.builder("parameter6", Parameter6::class).addKdoc("text6").build(),
            ParameterSpec.builder("parameter7", Parameter7::class).addKdoc("text7").build(),
        )
    }

    @Test
    fun parametering() {
        assertThat(
            buildFunSpec("test") {
                val parameter1 by parameters.adding(Parameter1::class.name)
                val parameter2 by parameters.adding(Parameter2::class.java)
                val parameter3 by parameters.adding(Parameter3::class)
                val parameter4 by parameters.adding(Parameter4::class.name) { addKdoc("text4") }
                val parameter5 by parameters.adding(Parameter5::class.java) { addKdoc("text5") }
                val parameter6 by parameters.adding(Parameter6::class) { addKdoc("text6") }
            }.parameters,
        ).containsExactly(
            ParameterSpec.builder("parameter1", Parameter1::class).build(),
            ParameterSpec.builder("parameter2", Parameter2::class).build(),
            ParameterSpec.builder("parameter3", Parameter3::class).build(),
            ParameterSpec.builder("parameter4", Parameter4::class).addKdoc("text4").build(),
            ParameterSpec.builder("parameter5", Parameter5::class).addKdoc("text5").build(),
            ParameterSpec.builder("parameter6", Parameter6::class).addKdoc("text6").build(),
        )
    }

    @Test
    fun invoke() {
        assertThat(
            buildFunSpec("test") {
                parameters {
                    "parameter1"(Parameter1::class.name) { addKdoc("text1") }
                    "parameter2"(Parameter2::class.java) { addKdoc("text2") }
                    "parameter3"(Parameter3::class) { addKdoc("text3") }
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
    fun addModifiers() {
        assertThat(
            buildParameterSpec("parameter1", Property1::class.name) {
                addModifiers(VARARG)
                modifiers += listOf(NOINLINE)
                assertFalse(modifiers.isEmpty())
            },
        ).isEqualTo(
            ParameterSpec
                .builder("parameter1", Property1::class)
                .addModifiers(KModifier.VARARG)
                .addModifiers(listOf(KModifier.NOINLINE))
                .build(),
        )
    }

    @Test
    fun defaultValue() {
        assertThat(
            buildParameterSpec("parameter1", Property1::class.name) { setDefaultValue("value1") },
        ).isEqualTo(
            ParameterSpec.builder("parameter1", Property1::class).defaultValue("value1").build(),
        )
        assertThat(
            buildParameterSpec("parameter2", Property1::class.name) {
                defaultValue = codeBlockOf("value2")
            },
        ).isEqualTo(
            ParameterSpec
                .builder("parameter2", Property1::class)
                .defaultValue(CodeBlock.of("value2"))
                .build(),
        )
    }

    @Test
    fun addKdoc() {
        assertThat(
            buildParameterSpec("parameter1", Parameter1::class.name) {
                addKdoc("kdoc1")
                addKdoc(codeBlockOf("kdoc2"))
                assertFalse(kdoc.isEmpty())
            },
        ).isEqualTo(
            ParameterSpec
                .builder("parameter1", Parameter1::class)
                .addKdoc("kdoc1")
                .addKdoc(CodeBlock.of("kdoc2"))
                .build(),
        )
    }

    @Test
    fun `Rest of properties`() {
        buildParameterSpec("parameter1", Parameter1::class.name) {
            assertTrue(tags.isEmpty())
        }
    }
}
