package com.hendraanggrian.kotlinpoet

import com.example.Annotation1
import com.example.Property1
import com.example.Property2
import com.example.Property3
import com.example.Property4
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeVariableName
import com.squareup.kotlinpoet.asClassName
import kotlin.test.Test
import kotlin.test.assertEquals

class PropertySpecBuilderTest {

    @Test
    fun isMutable() {
        assertEquals(
            buildPropertySpec<Property1>("property1") { isMutable = true },
            PropertySpec.builder("property1", Property1::class).mutable().build()
        )
    }

    @Test
    fun kdoc() {
        assertEquals(
            buildPropertySpec<Property1>("property1") { kdoc.append("some doc") },
            PropertySpec.builder("property1", Property1::class).addKdoc("some doc").build()
        )
    }

    @Test
    fun annotations() {
        assertEquals(
            buildPropertySpec<Property1>("property1") { annotations { add<Annotation1>() } },
            PropertySpec.builder("property1", Property1::class).addAnnotation(Annotation1::class).build()
        )
    }

    @Test
    fun addModifiers() {
        assertEquals(
            buildPropertySpec<Property1>("property1") { addModifiers(PUBLIC, FINAL, CONST) },
            PropertySpec.builder("property1", Property1::class)
                .addModifiers(KModifier.PUBLIC, KModifier.FINAL, KModifier.CONST)
                .build()
        )
    }

    @Test
    fun typeVariables() {
        assertEquals(
            buildPropertySpec<Property1>("property1") { typeVariables.add("typeVar1", Annotation1::class) },
            PropertySpec.builder("property1", Property1::class)
                .addTypeVariable(TypeVariableName("typeVar1", Annotation1::class))
                .build()
        )
    }

    @Test
    fun initializer() {
        assertEquals(
            buildPropertySpec<Property1>("property1") { initializer("value1") },
            PropertySpec.builder("property1", Property1::class).initializer("value1").build()
        )
        assertEquals(
            buildPropertySpec<Property2>("property2") { initializer = codeBlockOf("value2") },
            PropertySpec.builder("property2", Property2::class).initializer(CodeBlock.of("value2")).build()
        )
    }

    @Test
    fun delegate() {
        assertEquals(
            buildPropertySpec<Property1>("property1") { delegate("value1") },
            PropertySpec.builder("property1", Property1::class).delegate("value1").build()
        )
        assertEquals(
            buildPropertySpec<Property2>("property2") { delegate = codeBlockOf("value2") },
            PropertySpec.builder("property2", Property2::class).delegate(CodeBlock.of("value2")).build()
        )
    }

    @Test
    fun getter() {
        assertEquals(
            buildPropertySpec<Property1>("property1") { getter { append("return something") } },
            PropertySpec.builder("property1", Property1::class)
                .getter(FunSpec.getterBuilder().addCode("return something").build())
                .build()
        )
    }

    @Test
    fun setter() {
        assertEquals(
            buildPropertySpec<Property1>("property1") {
                isMutable = true
                setter {
                    parameters.add<String>("something")
                    append("set something")
                }
            },
            PropertySpec.builder("property1", Property1::class)
                .mutable()
                .setter(
                    FunSpec.setterBuilder()
                        .addParameter(ParameterSpec.builder("something", String::class).build())
                        .addCode("set something").build()
                )
                .build()
        )
    }

    @Test
    fun receiver() {
        assertEquals(
            buildPropertySpec<Property1>("property1") { receiver = Property1::class.asClassName() },
            PropertySpec.builder("property1", Property1::class).receiver(Property1::class.asClassName()).build()
        )
        assertEquals(
            buildPropertySpec<Property2>("property2") { receiver(Property2::class.java) },
            PropertySpec.builder("property2", Property2::class).receiver(Property2::class.java).build()
        )
        assertEquals(
            buildPropertySpec<Property3>("property3") { receiver(Property3::class) },
            PropertySpec.builder("property3", Property3::class).receiver(Property3::class).build()
        )
        assertEquals(
            buildPropertySpec<Property4>("property4") { receiver<Property4>() },
            PropertySpec.builder("property4", Property4::class).receiver(Property4::class).build()
        )
    }
}