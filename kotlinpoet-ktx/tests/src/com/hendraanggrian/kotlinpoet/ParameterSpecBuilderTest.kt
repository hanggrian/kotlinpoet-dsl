package com.hendraanggrian.kotlinpoet

import com.example.Annotation1
import com.example.Property1
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.ParameterSpec
import kotlin.test.Test
import kotlin.test.assertEquals

class ParameterSpecBuilderTest {

    @Test
    fun kdoc() {
        assertEquals(
            buildParameterSpec<Property1>("parameter1") { kdoc.append("some doc") },
            ParameterSpec.builder("parameter1", Property1::class).addKdoc("some doc").build()
        )
    }

    @Test
    fun annotations() {
        assertEquals(
            buildParameterSpec<Property1>("parameter1") { annotations { add<Annotation1>() } },
            ParameterSpec.builder("parameter1", Property1::class).addAnnotation(Annotation1::class).build()
        )
    }

    @Test
    fun addModifiers() {
        assertEquals(
            buildParameterSpec<Property1>("parameter1") { addModifiers(VARARG) },
            ParameterSpec.builder("parameter1", Property1::class).addModifiers(VARARG).build()
        )
    }

    @Test
    fun defaultValue() {
        assertEquals(
            buildParameterSpec<Property1>("parameter1") { defaultValue("value1") },
            ParameterSpec.builder("parameter1", Property1::class).defaultValue("value1").build()
        )
        assertEquals(
            buildParameterSpec<Property1>("parameter2") { defaultValue = codeBlockOf("value2") },
            ParameterSpec.builder("parameter2", Property1::class).defaultValue(CodeBlock.of("value2")).build()
        )
    }
}