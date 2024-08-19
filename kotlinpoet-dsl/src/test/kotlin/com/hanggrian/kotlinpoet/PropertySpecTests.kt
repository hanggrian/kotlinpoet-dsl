package com.hanggrian.kotlinpoet

import com.example.Annotation1
import com.example.Annotation2
import com.example.Class1
import com.example.Property1
import com.example.Property2
import com.example.Property3
import com.example.Property4
import com.example.Property5
import com.example.Property6
import com.example.Property7
import com.google.common.truth.Truth.assertThat
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.INT
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeVariableName
import com.squareup.kotlinpoet.asClassName
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PropertySpecCreatorTest {
    @Test
    fun of() {
        assertThat(propertySpecOf("myField", INT, PUBLIC))
            .isEqualTo(PropertySpec.builder("myField", INT, KModifier.PUBLIC).build())
    }

    @Test
    fun build() {
        assertThat(
            buildPropertySpec("myField", INT, PUBLIC) {
                setInitializer("value1")
            },
        ).isEqualTo(
            PropertySpec
                .builder("myField", INT, KModifier.PUBLIC)
                .initializer("value1")
                .build(),
        )
    }
}

class PropertySpecHandlerTest {
    @Test
    fun add() {
        assertThat(
            buildClassTypeSpec("test") {
                properties.add("property1", Property1::class.name)
                properties.add("property2", Property2::class.java)
                properties.add("property3", Property3::class)
                properties.add<Property4>("property4")
                properties {
                    add("property5", Property5::class.name) { setInitializer("value5") }
                    add("property6", Property6::class.java) { setInitializer("value6") }
                    add("property7", Property7::class) { setInitializer("value7") }
                }
            }.propertySpecs,
        ).containsExactly(
            PropertySpec.builder("property1", Property1::class).build(),
            PropertySpec.builder("property2", Property2::class).build(),
            PropertySpec.builder("property3", Property3::class).build(),
            PropertySpec.builder("property4", Property4::class).build(),
            PropertySpec.builder("property5", Property5::class).initializer("value5").build(),
            PropertySpec.builder("property6", Property6::class).initializer("value6").build(),
            PropertySpec.builder("property7", Property7::class).initializer("value7").build(),
        )
    }

    @Test
    fun adding() {
        assertThat(
            buildClassTypeSpec("test") {
                val property1 by properties.adding(Property1::class.name)
                val property2 by properties.adding(Property2::class.java)
                val property3 by properties.adding(Property3::class)
                val property4 by properties.adding(Property4::class.name) {
                    setInitializer("value4")
                }
                val property5 by properties.adding(Property5::class.java) {
                    setInitializer("value5")
                }
                val property6 by properties.adding(Property6::class) { setInitializer("value6") }
            }.propertySpecs,
        ).containsExactly(
            PropertySpec.builder("property1", Property1::class).build(),
            PropertySpec.builder("property2", Property2::class).build(),
            PropertySpec.builder("property3", Property3::class).build(),
            PropertySpec.builder("property4", Property4::class).initializer("value4").build(),
            PropertySpec.builder("property5", Property5::class).initializer("value5").build(),
            PropertySpec.builder("property6", Property6::class).initializer("value6").build(),
        )
    }

    @Test
    fun invoke() {
        assertThat(
            buildClassTypeSpec("test") {
                properties {
                    "property1"(Property1::class.name) { setInitializer("value1") }
                    "property2"(Property2::class.java) { setInitializer("value2") }
                    "property3"(Property3::class) { setInitializer("value3") }
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
    fun annotations() {
        assertThat(
            buildPropertySpec("myField", INT, PUBLIC) {
                annotations.add(Annotation1::class)
                annotations {
                    add(Annotation2::class)
                }
            },
        ).isEqualTo(
            PropertySpec
                .builder("myField", INT, KModifier.PUBLIC)
                .addAnnotation(Annotation1::class)
                .addAnnotation(Annotation2::class)
                .build(),
        )
    }

    @Test
    @ExperimentalKotlinPoetApi
    fun contextSetReceiverTypes() {
        assertThat(
            buildPropertySpec("property1", Property1::class.name) {
                contextReceiverTypes += Class1::class.name
            },
        ).isEqualTo(
            PropertySpec
                .builder("property1", Property1::class)
                .contextReceivers(Class1::class.name)
                .build(),
        )
    }

    @Test
    fun isMutable() {
        assertThat(
            buildPropertySpec("property1", Property1::class.name) { isMutable = true },
        ).isEqualTo(
            PropertySpec.builder("property1", Property1::class).mutable().build(),
        )
    }

    @Test
    fun addModifiers() {
        assertThat(
            buildPropertySpec("property1", Property1::class.name) {
                addModifiers(PUBLIC)
                modifiers += listOf(FINAL, CONST)
                assertFalse(modifiers.isEmpty())
            },
        ).isEqualTo(
            PropertySpec
                .builder("property1", Property1::class.java)
                .addModifiers(KModifier.PUBLIC)
                .addModifiers(listOf(KModifier.FINAL, KModifier.CONST))
                .build(),
        )
    }

    @Test
    fun addTypeVariables() {
        assertThat(
            buildPropertySpec("property1", Property1::class.name) {
                addTypeVariables(
                    "typeVar1".genericsBy(Annotation1::class),
                    "typeVar2".genericsBy(Annotation2::class),
                )
                assertFalse(typeVariables.isEmpty())
            },
        ).isEqualTo(
            PropertySpec
                .builder("property1", Property1::class)
                .addTypeVariables(
                    listOf(
                        TypeVariableName("typeVar1", Annotation1::class.java),
                        TypeVariableName("typeVar2", Annotation2::class.java),
                    ),
                ).build(),
        )
    }

    @Test
    fun initializer() {
        assertThat(
            buildPropertySpec("property1", Property1::class.name) { setInitializer("value1") },
        ).isEqualTo(
            PropertySpec.builder("property1", Property1::class).initializer("value1").build(),
        )
        assertThat(
            buildPropertySpec("property2", Property2::class.name) {
                initializer = codeBlockOf("value2")
            },
        ).isEqualTo(
            PropertySpec
                .builder("property2", Property2::class)
                .initializer(CodeBlock.of("value2"))
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
            PropertySpec
                .builder("property2", Property2::class)
                .delegate(CodeBlock.of("value2"))
                .build(),
        )
    }

    @Test
    fun getter() {
        assertThat(
            buildPropertySpec("property1", Property1::class.name) {
                setGetter { append("return something") }
            },
        ).isEqualTo(
            PropertySpec
                .builder("property1", Property1::class)
                .getter(FunSpec.getterBuilder().addCode("return something").build())
                .build(),
        )
    }

    @Test
    fun setter() {
        assertThat(
            buildPropertySpec("property1", Property1::class.name) {
                isMutable = true
                setSetter {
                    parameters.add<String>("something")
                    append("set something")
                }
            },
        ).isEqualTo(
            PropertySpec
                .builder("property1", Property1::class)
                .mutable()
                .setter(
                    FunSpec
                        .setterBuilder()
                        .addParameter(ParameterSpec.builder("something", String::class).build())
                        .addCode("set something")
                        .build(),
                ).build(),
        )
    }

    @Test
    fun receiver() {
        assertThat(
            buildPropertySpec("property1", Property1::class.name) {
                receiver = Property1::class.asClassName()
            },
        ).isEqualTo(
            PropertySpec
                .builder("property1", Property1::class)
                .receiver(Property1::class.asClassName())
                .build(),
        )
        assertThat(
            buildPropertySpec("property2", Property2::class.name) {
                setReceiver(Property2::class.java)
            },
        ).isEqualTo(
            PropertySpec
                .builder("property2", Property2::class)
                .receiver(Property2::class.java)
                .build(),
        )
        assertThat(
            buildPropertySpec("property3", Property3::class.name) { setReceiver(Property3::class) },
        ).isEqualTo(
            PropertySpec.builder("property3", Property3::class).receiver(Property3::class).build(),
        )
        assertThat(
            buildPropertySpec("property4", Property4::class.name) { setReceiver<Property4>() },
        ).isEqualTo(
            PropertySpec.builder("property4", Property4::class).receiver(Property4::class).build(),
        )
    }

    @Test
    fun addKdoc() {
        assertThat(
            buildPropertySpec("property1", Property1::class.name) {
                addKdoc("kdoc1")
                addKdoc(codeBlockOf("kdoc2"))
                assertFalse(kdoc.isEmpty())
            },
        ).isEqualTo(
            PropertySpec
                .builder("property1", Property1::class)
                .addKdoc("kdoc1")
                .addKdoc(CodeBlock.of("kdoc2"))
                .build(),
        )
    }

    @Test
    fun `Rest of properties`() {
        buildPropertySpec("property1", Property1::class.name) {
            assertTrue(tags.isEmpty())
            assertTrue(originatingElements.isEmpty())
        }
    }
}
