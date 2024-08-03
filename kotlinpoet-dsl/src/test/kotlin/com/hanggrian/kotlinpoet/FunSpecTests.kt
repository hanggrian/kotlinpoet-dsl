package com.hanggrian.kotlinpoet

import com.example.Annotation1
import com.example.Annotation2
import com.example.Annotation3
import com.example.Annotation4
import com.example.Property1
import com.example.Property2
import com.example.Property3
import com.example.Property4
import com.google.common.truth.Truth.assertThat
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeVariableName
import com.squareup.kotlinpoet.asClassName
import kotlin.test.Test
import kotlin.test.assertFalse

class FunSpecHandlerTest {
    @Test
    fun function() {
        assertThat(
            buildClassTypeSpec("test") {
                function("fun1")
                function("fun2") { kdoc("text2") }
                constructorFunction()
                constructorFunction { kdoc("text4") }
            }.funSpecs,
        ).containsExactly(
            FunSpec.builder("fun1").build(),
            FunSpec.builder("fun2").addKdoc("text2").build(),
            FunSpec.constructorBuilder().build(),
            FunSpec.constructorBuilder().addKdoc("text4").build(),
        )
    }

    @Test
    fun functioning() {
        assertThat(
            buildClassTypeSpec("test") {
                val fun1 by functioning()
                val fun2 by functioning { kdoc("text2") }
            }.funSpecs,
        ).containsExactly(
            FunSpec.builder("fun1").build(),
            FunSpec.builder("fun2").addKdoc("text2").build(),
        )
    }

    @Test
    fun invoke() {
        assertThat(
            buildClassTypeSpec("test") {
                functions {
                    "fun1" { kdoc("text1") }
                }
            }.funSpecs,
        ).containsExactly(
            FunSpec.builder("fun1").addKdoc("text1").build(),
        )
    }
}

class FunSpecBuilderTest {
    @Test
    fun kdoc() {
        assertThat(
            buildFunSpec("fun1") {
                kdoc("kdoc1")
                kdoc(codeBlockOf("kdoc2"))
                assertFalse(kdoc.isEmpty())
            },
        ).isEqualTo(
            FunSpec
                .builder("fun1")
                .addKdoc("kdoc1")
                .addKdoc("kdoc2")
                .build(),
        )
    }

    @Test
    fun annotation() {
        assertThat(
            buildFunSpec("fun1") {
                annotation(Annotation1::class.name)
                annotation(Annotation2::class.name)
                annotation(Annotation3::class.name)
                annotation<Annotation4>()
                assertFalse(annotations.isEmpty())
            },
        ).isEqualTo(
            FunSpec
                .builder("fun1")
                .addAnnotation(Annotation1::class)
                .addAnnotation(Annotation2::class)
                .addAnnotation(Annotation3::class)
                .addAnnotation(Annotation4::class)
                .build(),
        )
    }

    @Test
    fun modifiers() {
        assertThat(
            buildFunSpec("fun1") {
                modifiers(PUBLIC, FINAL, CONST)
                assertFalse(modifiers.isEmpty())
            },
        ).isEqualTo(
            FunSpec
                .builder("fun1")
                .addModifiers(KModifier.PUBLIC, KModifier.FINAL, KModifier.CONST)
                .build(),
        )
    }

    @Test
    fun typeVariable() {
        assertThat(
            buildFunSpec("fun1") {
                typeVariables(
                    listOf(
                        "typeVar1".genericsBy(Annotation1::class),
                        "typeVar2".genericsBy(Annotation2::class),
                    ),
                )
                typeVariable("typeVar3".genericsBy(Annotation3::class))
                assertFalse(typeVariables.isEmpty())
            },
        ).isEqualTo(
            FunSpec
                .builder("fun1")
                .addTypeVariables(
                    listOf(
                        TypeVariableName("typeVar1", Annotation1::class.java),
                        TypeVariableName("typeVar2", Annotation2::class.java),
                    ),
                ).addTypeVariable(TypeVariableName("typeVar3", Annotation3::class.java))
                .build(),
        )
    }

    @Test
    fun receiver() {
        assertThat(
            buildFunSpec("fun1") { receiver = Property1::class.asClassName() },
        ).isEqualTo(
            FunSpec.builder("fun1").receiver(Property1::class.asClassName()).build(),
        )
        assertThat(
            buildFunSpec("fun2") { receiver(Property2::class.java) },
        ).isEqualTo(
            FunSpec.builder("fun2").receiver(Property2::class.java).build(),
        )
        assertThat(
            buildFunSpec("fun3") { receiver(Property3::class) },
        ).isEqualTo(
            FunSpec.builder("fun3").receiver(Property3::class.java).build(),
        )
        assertThat(
            buildFunSpec("fun4") { receiver<Property4>() },
        ).isEqualTo(
            FunSpec.builder("fun4").receiver(Property4::class.java).build(),
        )
    }

    @Test
    fun returns() {
        assertThat(
            buildFunSpec("fun1") { returns = Property1::class.asClassName() },
        ).isEqualTo(
            FunSpec.builder("fun1").returns(Property1::class.asClassName()).build(),
        )
        assertThat(
            buildFunSpec("fun2") { returns(Property2::class.java) },
        ).isEqualTo(
            FunSpec.builder("fun2").returns(Property2::class.java).build(),
        )
        assertThat(
            buildFunSpec("fun3") { returns(Property3::class) },
        ).isEqualTo(
            FunSpec.builder("fun3").returns(Property3::class.java).build(),
        )
        assertThat(
            buildFunSpec("fun4") { returns<Property4>() },
        ).isEqualTo(
            FunSpec.builder("fun4").returns(Property4::class.java).build(),
        )
    }

    @Test
    fun parameter() {
        assertThat(
            buildFunSpec("fun1") {
                parameter("parameter1", Property1::class.name)
                parameter("parameter2", Property2::class)
                parameter<Property3>("parameter3")
                assertFalse(parameters.isEmpty())
            },
        ).isEqualTo(
            FunSpec
                .builder("fun1")
                .addParameter("parameter1", Property1::class)
                .addParameter("parameter2", Property2::class)
                .addParameter("parameter3", Property3::class)
                .build(),
        )
    }

    @Test
    fun callThisConstructor() {
        assertThat(
            FunSpecBuilder(FunSpec.constructorBuilder())
                .apply { callThisConstructor(listOf(codeBlockOf("code1"), codeBlockOf("code2"))) }
                .build(),
        ).isEqualTo(
            FunSpec
                .constructorBuilder()
                .callThisConstructor(listOf(codeBlockOf("code1"), codeBlockOf("code2")))
                .build(),
        )
        assertThat(
            FunSpecBuilder(FunSpec.constructorBuilder())
                .apply { callThisConstructor("code1", "code2") }
                .build(),
        ).isEqualTo(
            FunSpec
                .constructorBuilder()
                .callThisConstructor("code1", "code2")
                .build(),
        )
        assertThat(
            FunSpecBuilder(FunSpec.constructorBuilder())
                .apply { callThisConstructor(codeBlockOf("code1"), codeBlockOf("code2")) }
                .build(),
        ).isEqualTo(
            FunSpec
                .constructorBuilder()
                .callThisConstructor(codeBlockOf("code1"), codeBlockOf("code2"))
                .build(),
        )
    }

    @Test
    fun callSuperConstructor() {
        assertThat(
            FunSpecBuilder(FunSpec.constructorBuilder())
                .apply { callSuperConstructor(listOf(codeBlockOf("code1"), codeBlockOf("code2"))) }
                .build(),
        ).isEqualTo(
            FunSpec
                .constructorBuilder()
                .callSuperConstructor(listOf(codeBlockOf("code1"), codeBlockOf("code2")))
                .build(),
        )
        assertThat(
            FunSpecBuilder(FunSpec.constructorBuilder())
                .apply { callSuperConstructor("code1", "code2") }
                .build(),
        ).isEqualTo(
            FunSpec
                .constructorBuilder()
                .callSuperConstructor("code1", "code2")
                .build(),
        )
        assertThat(
            FunSpecBuilder(FunSpec.constructorBuilder())
                .apply { callSuperConstructor(codeBlockOf("code1"), codeBlockOf("code2")) }
                .build(),
        ).isEqualTo(
            FunSpec
                .constructorBuilder()
                .callSuperConstructor(codeBlockOf("code1"), codeBlockOf("code2"))
                .build(),
        )
    }

    @Test
    fun comment() {
        assertThat(
            buildFunSpec("fun1") { comment("some comment") },
        ).isEqualTo(
            FunSpec.builder("fun1").addComment("some comment").build(),
        )
    }

    @Test
    fun append() {
        assertThat(
            buildFunSpec("fun1") { append("some code") },
        ).isEqualTo(
            FunSpec.builder("fun1").addCode("some code").build(),
        )
    }

    @Test
    fun appendLine() {
        assertThat(
            buildFunSpec("fun1") { appendLine("some code") },
        ).isEqualTo(
            FunSpec.builder("fun1").addStatement("some code").build(),
        )
    }

    @Test
    fun controlFlow() {
        assertThat(
            buildFunSpec("fun1") {
                beginControlFlow("some code")
                nextControlFlow("another code")
                endControlFlow()
            },
        ).isEqualTo(
            FunSpec
                .builder("fun1")
                .beginControlFlow("some code")
                .nextControlFlow("another code")
                .endControlFlow()
                .build(),
        )
    }
}
