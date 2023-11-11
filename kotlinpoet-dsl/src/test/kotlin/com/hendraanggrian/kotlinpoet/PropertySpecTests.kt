package com.hendraanggrian.kotlinpoet

import com.example.Annotation1
import com.example.Annotation2
import com.example.Annotation3
import com.example.Property1
import com.example.Property2
import com.example.Property3
import com.example.Property4
import com.example.Property5
import com.example.Property6
import com.example.Property7
import com.google.common.truth.Truth.assertThat
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeVariableName
import com.squareup.kotlinpoet.asClassName
import com.squareup.kotlinpoet.asTypeName
import kotlin.test.Test
import kotlin.test.assertFalse

class PropertySpecHandlerTest {
    @Test
    fun property() {
        assertThat(
            buildClassTypeSpec("test") {
                property("property1", Property1::class.asTypeName())
                property("property2", Property2::class.asTypeName()) { initializer("value2") }
                property("property3", Property3::class.java)
                property("property4", Property4::class.java) { initializer("value4") }
                property("property5", Property5::class)
                property("property6", Property6::class) { initializer("value6") }
                property<Property7>("property7")
            }.propertySpecs,
        ).containsExactly(
            PropertySpec.builder("property1", Property1::class).build(),
            PropertySpec.builder("property2", Property2::class).initializer("value2").build(),
            PropertySpec.builder("property3", Property3::class).build(),
            PropertySpec.builder("property4", Property4::class).initializer("value4").build(),
            PropertySpec.builder("property5", Property5::class).build(),
            PropertySpec.builder("property6", Property6::class).initializer("value6").build(),
            PropertySpec.builder("property7", Property7::class).build(),
        )
    }

    @Test
    fun parametering() {
        assertThat(
            buildClassTypeSpec("test") {
                val property1 by propertying(Property1::class.name)
                val property2 by propertying(Property2::class.name) {
                    initializer("value2")
                }
                val property3 by propertying(Property3::class.java)
                val property4 by propertying(Property4::class.java) { initializer("value4") }
                val property5 by propertying(Property5::class)
                val property6 by propertying(Property6::class) { initializer("value6") }
            }.propertySpecs,
        ).containsExactly(
            PropertySpec.builder("property1", Property1::class).build(),
            PropertySpec.builder("property2", Property2::class).initializer("value2").build(),
            PropertySpec.builder("property3", Property3::class).build(),
            PropertySpec.builder("property4", Property4::class).initializer("value4").build(),
            PropertySpec.builder("property5", Property5::class).build(),
            PropertySpec.builder("property6", Property6::class).initializer("value6").build(),
        )
    }

    @Test
    fun invoke() {
        assertThat(
            buildClassTypeSpec("test") {
                properties {
                    "property1"(Property1::class.name) { initializer("value1") }
                    "property2"(Property2::class.java) { initializer("value2") }
                    "property3"(Property3::class) { initializer("value3") }
                }
            }.propertySpecs,
        ).containsExactly(
            PropertySpec.builder("property1", Property1::class).initializer("value1").build(),
            PropertySpec.builder("property2", Property2::class).initializer("value2").build(),
            PropertySpec.builder("property3", Property3::class).initializer("value3").build(),
        )
    }
}

class PropertySpecBuilderTest {
    @Test
    fun isMutable() {
        assertThat(
            buildPropertySpec("property1", Property1::class.name) { isMutable = true },
        ).isEqualTo(
            PropertySpec.builder("property1", Property1::class).mutable().build(),
        )
    }

    @Test
    fun kdoc() {
        assertThat(
            buildPropertySpec("property1", Property1::class.name) {
                kdoc("kdoc1")
                kdoc(codeBlockOf("kdoc2"))
                assertFalse(kdoc.isEmpty())
            },
        ).isEqualTo(
            PropertySpec.builder("property1", Property1::class)
                .addKdoc("kdoc1")
                .addKdoc(codeBlockOf("kdoc2"))
                .build(),
        )
    }

    @Test
    fun annotation() {
        assertThat(
            buildPropertySpec("property1", Property1::class.name) {
                annotation(Annotation1::class.name)
                annotation(Annotation2::class)
                annotation<Annotation3>()
                assertFalse(annotations.isEmpty())
            },
        ).isEqualTo(
            PropertySpec.builder("property1", Property1::class)
                .addAnnotation(Annotation1::class)
                .addAnnotation(Annotation2::class)
                .addAnnotation(Annotation3::class)
                .build(),
        )
    }

    @Test
    fun modifiers() {
        assertThat(
            buildPropertySpec("property1", Property1::class.name) {
                modifiers(PUBLIC, FINAL, CONST)
                assertFalse(modifiers.isEmpty())
            },
        ).isEqualTo(
            PropertySpec.builder("property1", Property1::class)
                .addModifiers(KModifier.PUBLIC, KModifier.FINAL, KModifier.CONST)
                .build(),
        )
    }

    @Test
    fun typeVariables() {
        assertThat(
            buildPropertySpec("property1", Property1::class.name) {
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
            PropertySpec.builder("property1", Property1::class)
                .addTypeVariables(
                    listOf(
                        TypeVariableName("typeVar1", Annotation1::class.java),
                        TypeVariableName("typeVar2", Annotation2::class.java),
                    ),
                )
                .addTypeVariable(TypeVariableName("typeVar3", Annotation3::class.java))
                .build(),
        )
    }

    @Test
    fun initializer() {
        assertThat(
            buildPropertySpec("property1", Property1::class.name) { initializer("value1") },
        ).isEqualTo(
            PropertySpec.builder("property1", Property1::class).initializer("value1").build(),
        )
        assertThat(
            buildPropertySpec("property2", Property2::class.name) {
                initializer = codeBlockOf("value2")
            },
        ).isEqualTo(
            PropertySpec.builder("property2", Property2::class).initializer(CodeBlock.of("value2"))
                .build(),
        )
    }

    @Test
    fun delegate() {
        assertThat(
            buildPropertySpec("property1", Property1::class.name) { delegate("value1") },
        ).isEqualTo(
            PropertySpec.builder("property1", Property1::class).delegate("value1").build(),
        )
        assertThat(
            buildPropertySpec("property2", Property2::class.name) {
                delegate = codeBlockOf("value2")
            },
        ).isEqualTo(
            PropertySpec.builder("property2", Property2::class).delegate(CodeBlock.of("value2"))
                .build(),
        )
    }

    @Test
    fun getterFunction() {
        assertThat(
            buildPropertySpec("property1", Property1::class.name) {
                getterFunction { append("return something") }
            },
        ).isEqualTo(
            PropertySpec.builder("property1", Property1::class)
                .getter(FunSpec.getterBuilder().addCode("return something").build())
                .build(),
        )
    }

    @Test
    fun setterFunction() {
        assertThat(
            buildPropertySpec("property1", Property1::class.name) {
                isMutable = true
                setterFunction {
                    parameter<String>("something")
                    append("set something")
                }
            },
        ).isEqualTo(
            PropertySpec.builder("property1", Property1::class)
                .mutable()
                .setter(
                    FunSpec.setterBuilder()
                        .addParameter(ParameterSpec.builder("something", String::class).build())
                        .addCode("set something").build(),
                )
                .build(),
        )
    }

    @Test
    fun receiver() {
        assertThat(
            buildPropertySpec("property1", Property1::class.name) {
                receiver = Property1::class.asClassName()
            },
        ).isEqualTo(
            PropertySpec.builder("property1", Property1::class)
                .receiver(Property1::class.asClassName()).build(),
        )
        assertThat(
            buildPropertySpec("property2", Property2::class.name) {
                receiver(Property2::class.java)
            },
        ).isEqualTo(
            PropertySpec.builder("property2", Property2::class).receiver(Property2::class.java)
                .build(),
        )
        assertThat(
            buildPropertySpec("property3", Property3::class.name) { receiver(Property3::class) },
        ).isEqualTo(
            PropertySpec.builder("property3", Property3::class).receiver(Property3::class).build(),
        )
        assertThat(
            buildPropertySpec("property4", Property4::class.name) { receiver<Property4>() },
        ).isEqualTo(
            PropertySpec.builder("property4", Property4::class).receiver(Property4::class).build(),
        )
    }
}
