package com.hendraanggrian.kotlinpoet

import com.example.Annotation1
import com.example.Property1
import com.example.Property2
import com.example.Property3
import com.example.Property4
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeVariableName
import com.squareup.kotlinpoet.asClassName
import kotlin.test.Test
import kotlin.test.assertEquals

class FunSpecBuilderTest {

    @Test
    fun kdoc() {
        assertEquals(
            buildFunSpec("fun1") { kdoc.append("some doc") },
            FunSpec.builder("fun1").addKdoc("some doc").build()
        )
    }

    @Test
    fun annotations() {
        assertEquals(
            buildFunSpec("fun1") { annotations { add<Annotation1>() } },
            FunSpec.builder("fun1").addAnnotation(Annotation1::class).build()
        )
    }

    @Test
    fun addModifiers() {
        assertEquals(
            buildFunSpec("fun1") { addModifiers(PUBLIC, FINAL, CONST) },
            FunSpec.builder("fun1").addModifiers(KModifier.PUBLIC, KModifier.FINAL, KModifier.CONST)
                .build()
        )
    }

    @Test
    fun typeVariables() {
        assertEquals(
            buildFunSpec("fun1") { typeVariables.add("typeVar1", Annotation1::class) },
            FunSpec.builder("fun1")
                .addTypeVariable(TypeVariableName("typeVar1", Annotation1::class.java))
                .build()
        )
    }

    @Test
    fun receiver() {
        assertEquals(
            buildFunSpec("fun1") { receiver = Property1::class.asClassName() },
            FunSpec.builder("fun1").receiver(Property1::class.asClassName()).build()
        )
        assertEquals(
            buildFunSpec("fun2") { receiver(Property2::class.java) },
            FunSpec.builder("fun2").receiver(Property2::class.java).build()
        )
        assertEquals(
            buildFunSpec("fun3") { receiver(Property3::class) },
            FunSpec.builder("fun3").receiver(Property3::class.java).build()
        )
        assertEquals(
            buildFunSpec("fun4") { receiver<Property4>() },
            FunSpec.builder("fun4").receiver(Property4::class.java).build()
        )
    }

    @Test
    fun returns() {
        assertEquals(
            buildFunSpec("fun1") { returns = Property1::class.asClassName() },
            FunSpec.builder("fun1").returns(Property1::class.asClassName()).build()
        )
        assertEquals(
            buildFunSpec("fun2") { returns(Property2::class.java) },
            FunSpec.builder("fun2").returns(Property2::class.java).build()
        )
        assertEquals(
            buildFunSpec("fun3") { returns(Property3::class) },
            FunSpec.builder("fun3").returns(Property3::class.java).build()
        )
        assertEquals(
            buildFunSpec("fun4") { returns<Property4>() },
            FunSpec.builder("fun4").returns(Property4::class.java).build()
        )
    }

    @Test
    fun parameters() {
        assertEquals(
            buildFunSpec("fun1") { parameters { add<Property1>("parameter1") } },
            FunSpec.builder("fun1").addParameter("parameter1", Property1::class).build()
        )
    }

    @Test
    fun callThisConstructor() {
        assertEquals(
            buildConstructorFunSpec {
                callThisConstructor(listOf(codeBlockOf("code1"), codeBlockOf("code2")))
            },
            FunSpec.constructorBuilder()
                .callThisConstructor(listOf(codeBlockOf("code1"), codeBlockOf("code2")))
                .build()
        )
        assertEquals(
            buildConstructorFunSpec { callThisConstructor("code1", "code2") },
            FunSpec.constructorBuilder().callThisConstructor("code1", "code2").build()
        )
        assertEquals(
            buildConstructorFunSpec {
                callThisConstructor(codeBlockOf("code1"), codeBlockOf("code2"))
            },
            FunSpec.constructorBuilder()
                .callThisConstructor(codeBlockOf("code1"), codeBlockOf("code2"))
                .build()
        )
    }

    @Test
    fun callSuperConstructor() {
        assertEquals(
            buildConstructorFunSpec {
                callSuperConstructor(listOf(codeBlockOf("code1"), codeBlockOf("code2")))
            },
            FunSpec.constructorBuilder()
                .callSuperConstructor(listOf(codeBlockOf("code1"), codeBlockOf("code2")))
                .build()
        )
        assertEquals(
            buildConstructorFunSpec { callSuperConstructor("code1", "code2") },
            FunSpec.constructorBuilder().callSuperConstructor("code1", "code2").build()
        )
        assertEquals(
            buildConstructorFunSpec {
                callSuperConstructor(codeBlockOf("code1"), codeBlockOf("code2"))
            },
            FunSpec.constructorBuilder()
                .callSuperConstructor(codeBlockOf("code1"), codeBlockOf("code2"))
                .build()
        )
    }

    @Test
    fun addComment() {
        assertEquals(
            buildFunSpec("fun1") { addComment("some comment") },
            FunSpec.builder("fun1").addComment("some comment").build()
        )
    }

    @Test
    fun append() {
        assertEquals(
            buildFunSpec("fun1") { append("some code") },
            FunSpec.builder("fun1").addCode("some code").build()
        )
    }

    @Test
    fun appendLine() {
        assertEquals(
            buildFunSpec("fun1") { appendLine("some code") },
            FunSpec.builder("fun1").addStatement("some code").build()
        )
    }

    @Test
    fun controlFlow() {
        assertEquals(
            buildFunSpec("fun1") {
                beginControlFlow("some code")
                nextControlFlow("another code")
                endControlFlow()
            },
            FunSpec.builder("fun1")
                .beginControlFlow("some code")
                .nextControlFlow("another code")
                .endControlFlow()
                .build()
        )
    }
}
