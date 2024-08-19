package com.hanggrian.kotlinpoet

import com.example.Annotation1
import com.example.Annotation2
import com.example.Class1
import com.example.Class10
import com.example.Class2
import com.example.Class3
import com.example.Class4
import com.example.Class5
import com.example.Class6
import com.example.Class7
import com.example.Class8
import com.example.Class9
import com.google.common.truth.Truth.assertThat
import com.squareup.kotlinpoet.CHAR
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.INT
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.MemberName
import com.squareup.kotlinpoet.MemberName.Companion.member
import com.squareup.kotlinpoet.TypeVariableName
import com.squareup.kotlinpoet.asClassName
import javax.lang.model.element.Modifier
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class FunSpecCreatorTest {
    @Test
    fun of() {
        assertThat(funSpecOf(Class1::class.member("Inner")))
            .isEqualTo(FunSpec.builder(MemberName("com.example.Class1", "Inner")).build())
        assertThat(funSpecOf("myMethod"))
            .isEqualTo(FunSpec.builder("myMethod").build())
        assertThat(emptyConstructorFunSpec())
            .isEqualTo(FunSpec.constructorBuilder().build())
    }

    @Test
    fun build() {
        assertThat(
            buildFunSpec(Class1::class.member("Inner")) {
                addKdoc("text1")
            },
        ).isEqualTo(
            FunSpec
                .builder(MemberName("com.example.Class1", "Inner"))
                .addKdoc("text1")
                .build(),
        )
        assertThat(
            buildFunSpec("myMethod") {
                addKdoc("text1")
            },
        ).isEqualTo(
            FunSpec
                .builder("myMethod")
                .addKdoc("text1")
                .build(),
        )
        assertThat(
            buildConstructorFunSpec {
                addKdoc("text1")
            },
        ).isEqualTo(
            FunSpec
                .constructorBuilder()
                .addKdoc("text1")
                .build(),
        )
    }
}

class FunSpecHandlerTest {
    @Test
    fun add() {
        assertThat(
            buildClassTypeSpec("test") {
                functions.add("fun1")
                functions.add(Class2::class.name.member("fun2"))
                functions.addConstructor()
                functions {
                    add("fun4") { addKdoc("text4") }
                    add(Class5::class.name.member("fun5")) { addKdoc("text5") }
                    addConstructor { addKdoc("text6") }
                }
            }.funSpecs,
        ).containsExactly(
            FunSpec.builder("fun1").build(),
            FunSpec.builder(Class2::class.name.member("fun2")).build(),
            FunSpec.constructorBuilder().build(),
            FunSpec.builder("fun4").addKdoc("text4").build(),
            FunSpec.builder(Class5::class.name.member("fun5")).addKdoc("text5").build(),
            FunSpec.constructorBuilder().addKdoc("text6").build(),
        )
    }

    @Test
    fun adding() {
        assertThat(
            buildClassTypeSpec("test") {
                val fun1 by functions.adding()
                val fun2 by functions.adding { addKdoc("text2") }
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
                    "fun1" { addKdoc("text1") }
                }
            }.funSpecs,
        ).containsExactly(
            FunSpec.builder("fun1").addKdoc("text1").build(),
        )
    }
}

class FunSpecBuilderTest {
    @Test
    fun annotations() {
        assertThat(
            buildFunSpec("myMethod") {
                annotations.add(Annotation1::class)
                annotations {
                    add(Annotation2::class)
                }
            },
        ).isEqualTo(
            FunSpec
                .builder("myMethod")
                .addAnnotation(Annotation1::class)
                .addAnnotation(Annotation2::class)
                .build(),
        )
    }

    @Test
    fun parameters() {
        assertThat(
            buildFunSpec("myMethod") {
                parameters.add("param1", INT, NOINLINE)
                parameters {
                    add("param2", CHAR, CROSSINLINE)
                }
            },
        ).isEqualTo(
            FunSpec
                .builder("myMethod")
                .addParameter("param1", INT, KModifier.NOINLINE)
                .addParameter("param2", CHAR, KModifier.CROSSINLINE)
                .build(),
        )
    }

    @Test
    @ExperimentalKotlinPoetApi
    fun contextSetReceiverTypes() {
        assertThat(
            buildFunSpec("fun1") {
                contextReceiverTypes += Class1::class.name
            },
        ).isEqualTo(
            FunSpec
                .builder("fun1")
                .contextReceivers(Class1::class.name)
                .build(),
        )
    }

    @Test
    fun addModifiers() {
        assertThat(
            buildFunSpec("fun1") {
                addModifiers(PUBLIC)
                modifiers += listOf(FINAL, CONST)
                assertFalse(modifiers.isEmpty())
            },
        ).isEqualTo(
            FunSpec
                .builder("fun1")
                .addModifiers(KModifier.PUBLIC)
                .addModifiers(listOf(KModifier.FINAL, KModifier.CONST))
                .build(),
        )
    }

    @Test
    fun addJvmModifiers() {
        assertThat(
            buildFunSpec("fun1") {
                addJvmModifiers(Modifier.PUBLIC, Modifier.FINAL)
            },
        ).isEqualTo(
            FunSpec
                .builder("fun1")
                .apply { jvmModifiers(listOf(Modifier.PUBLIC, Modifier.FINAL)) }
                .build(),
        )
    }

    @Test
    fun addKdoc() {
        assertThat(
            buildFunSpec("fun1") {
                addKdoc("kdoc1")
                addKdoc(codeBlockOf("kdoc2"))
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
    fun addTypeVariable() {
        assertThat(
            buildFunSpec("fun1") {
                addTypeVariables(
                    "typeVar1".genericsBy(Annotation1::class),
                    "typeVar2".genericsBy(Annotation2::class),
                )
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
                ).build(),
        )
    }

    @Test
    fun receiver() {
        assertThat(buildFunSpec("fun1") { receiver = Class1::class.name })
            .isEqualTo(
                FunSpec
                    .builder("fun1")
                    .receiver(Class1::class.asClassName())
                    .build(),
            )
        assertThat(
            buildFunSpec("fun2") { setReceiver(Class2::class.name, codeBlockOf("some code")) },
        ).isEqualTo(
            FunSpec
                .builder("fun2")
                .receiver(Class2::class, CodeBlock.of("some code"))
                .build(),
        )
        assertThat(buildFunSpec("fun3") { setReceiver(Class3::class.name, "format", "arg") })
            .isEqualTo(
                FunSpec
                    .builder("fun3")
                    .receiver(Class3::class, "format", "arg")
                    .build(),
            )
        assertThat(
            buildFunSpec("fun4") { setReceiver(Class4::class.java, codeBlockOf("some code")) },
        ).isEqualTo(
            FunSpec
                .builder("fun4")
                .receiver(Class4::class, CodeBlock.of("some code"))
                .build(),
        )
        assertThat(buildFunSpec("fun5") { setReceiver(Class5::class.java, "format", "arg") })
            .isEqualTo(
                FunSpec
                    .builder("fun5")
                    .receiver(Class5::class, "format", "arg")
                    .build(),
            )
        assertThat(buildFunSpec("fun6") { setReceiver(Class6::class, codeBlockOf("some code")) })
            .isEqualTo(
                FunSpec
                    .builder("fun6")
                    .receiver(Class6::class, CodeBlock.of("some code"))
                    .build(),
            )
        assertThat(buildFunSpec("fun7") { setReceiver(Class7::class, "format", "arg") })
            .isEqualTo(
                FunSpec
                    .builder("fun7")
                    .receiver(Class7::class, "format", "arg")
                    .build(),
            )
        assertThat(buildFunSpec("fun8") { setReceiver(Class8::class, codeBlockOf("some code")) })
            .isEqualTo(
                FunSpec
                    .builder("fun8")
                    .receiver(Class8::class, CodeBlock.of("some code"))
                    .build(),
            )
        assertThat(buildFunSpec("fun9") { setReceiver<Class9>("format", "arg") })
            .isEqualTo(
                FunSpec
                    .builder("fun9")
                    .receiver(Class9::class, "format", "arg")
                    .build(),
            )
        assertThat(buildFunSpec("fun10") { setReceiver<Class10>(codeBlockOf("some code")) })
            .isEqualTo(
                FunSpec
                    .builder("fun10")
                    .receiver(Class10::class, CodeBlock.of("some code"))
                    .build(),
            )
    }

    @Test
    fun returns() {
        assertThat(buildFunSpec("fun1") { returns = Class1::class.asClassName() })
            .isEqualTo(
                FunSpec
                    .builder("fun1")
                    .returns(Class1::class.asClassName())
                    .build(),
            )
        assertThat(buildFunSpec("fun2") { setReturns(Class2::class.name, "format", "arg") })
            .isEqualTo(
                FunSpec
                    .builder("fun2")
                    .returns(Class2::class, "format", "arg")
                    .build(),
            )
        assertThat(
            buildFunSpec("fun2") { setReturns(Class2::class.name, codeBlockOf("some code")) },
        ).isEqualTo(
            FunSpec
                .builder("fun2")
                .returns(Class2::class, CodeBlock.of("some code"))
                .build(),
        )
        assertThat(buildFunSpec("fun3") { setReturns(Class3::class.java, "format", "arg") })
            .isEqualTo(
                FunSpec
                    .builder("fun3")
                    .returns(Class3::class, "format", "arg")
                    .build(),
            )
        assertThat(
            buildFunSpec("fun4") { setReturns(Class4::class.java, codeBlockOf("some code")) },
        ).isEqualTo(
            FunSpec
                .builder("fun4")
                .returns(Class4::class, CodeBlock.of("some code"))
                .build(),
        )
        assertThat(buildFunSpec("fun5") { setReturns(Class5::class, "format", "arg") })
            .isEqualTo(
                FunSpec
                    .builder("fun5")
                    .returns(Class5::class, "format", "arg")
                    .build(),
            )
        assertThat(buildFunSpec("fun6") { setReturns(Class6::class, codeBlockOf("some code")) })
            .isEqualTo(
                FunSpec
                    .builder("fun6")
                    .returns(Class6::class, CodeBlock.of("some code"))
                    .build(),
            )
        assertThat(buildFunSpec("fun7") { setReturns<Class7>("format", "arg") })
            .isEqualTo(
                FunSpec
                    .builder("fun7")
                    .returns(Class7::class, "format", "arg")
                    .build(),
            )
        assertThat(buildFunSpec("fun8") { setReturns<Class8>(codeBlockOf("some code")) })
            .isEqualTo(
                FunSpec
                    .builder("fun8")
                    .returns(Class8::class, CodeBlock.of("some code"))
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
    fun addComment() {
        assertThat(
            buildFunSpec("fun1") { addComment("some comment") },
        ).isEqualTo(
            FunSpec.builder("fun1").addComment("some comment").build(),
        )
    }

    @Test
    fun append() {
        assertThat(
            buildFunSpec("fun1") {
                append("some code")
                append(codeBlockOf("another code"))
            },
        ).isEqualTo(
            FunSpec
                .builder("fun1")
                .addCode("some code")
                .addCode(CodeBlock.of("another code"))
                .build(),
        )
    }

    @Test
    fun appendLine() {
        assertThat(
            buildFunSpec("fun1") {
                appendLine("some code")
                appendLine(codeBlockOf("another code"))
            },
        ).isEqualTo(
            FunSpec
                .builder("fun1")
                .addStatement("some code")
                .addStatement("another code")
                .build(),
        )
    }

    @Test
    fun appendNamed() {
        assertThat(
            buildFunSpec("fun1") {
                appendNamed("format", mapOf("key1" to "value1", "key2" to "value2"))
            },
        ).isEqualTo(
            FunSpec
                .builder("fun1")
                .addNamedCode("format", mapOf("key1" to "value1", "key2" to "value2"))
                .build(),
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

    @Test
    fun `Rest of properties`() {
        buildFunSpec("fun1") {
            assertTrue(tags.isEmpty())
            assertTrue(originatingElements.isEmpty())
        }
    }
}
