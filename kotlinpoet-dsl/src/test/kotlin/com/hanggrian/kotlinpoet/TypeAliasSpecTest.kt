package com.hanggrian.kotlinpoet

import com.example.Annotation1
import com.example.Annotation2
import com.example.Class1
import com.example.Class2
import com.example.Class3
import com.example.Class4
import com.example.Class5
import com.example.Class6
import com.example.Class7
import com.google.common.truth.Truth.assertThat
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeAliasSpec
import com.squareup.kotlinpoet.TypeVariableName
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@Suppress("ktlint:standard:property-naming")
class TypeAliasSpecTest {
    @Test
    fun typeAlias() {
        assertThat(
            buildFileSpec("com.example", "test") {
                typeAlias("Class1", Class1::class.name)
                typeAlias("Class2", Class2::class.java)
                typeAlias("Class3", Class3::class)
                typeAlias<Class4>("Class4")
                typeAlias("Class5", Class5::class.name) { modifiers(PUBLIC) }
                typeAlias("Class6", Class6::class.java) { modifiers(PUBLIC) }
                typeAlias("Class7", Class7::class) { modifiers(PUBLIC) }
            },
        ).isEqualTo(
            FileSpec
                .builder("com.example", "test")
                .addTypeAlias(TypeAliasSpec.builder("Class1", Class1::class.name).build())
                .addTypeAlias(TypeAliasSpec.builder("Class2", Class2::class.name).build())
                .addTypeAlias(TypeAliasSpec.builder("Class3", Class3::class.name).build())
                .addTypeAlias(TypeAliasSpec.builder("Class4", Class4::class.name).build())
                .addTypeAlias(
                    TypeAliasSpec
                        .builder("Class5", Class5::class.name)
                        .addModifiers(PUBLIC)
                        .build(),
                ).addTypeAlias(
                    TypeAliasSpec
                        .builder("Class6", Class6::class.name)
                        .addModifiers(PUBLIC)
                        .build(),
                ).addTypeAlias(
                    TypeAliasSpec
                        .builder("Class7", Class7::class.name)
                        .addModifiers(PUBLIC)
                        .build(),
                ).build(),
        )
    }

    @Test
    fun typeAliasing() {
        assertThat(
            buildFileSpec("com.example", "test") {
                val Class1 by typeAliasing(Class1::class.name)
                val Class2 by typeAliasing(Class2::class.java)
                val Class3 by typeAliasing(Class3::class)
                val Class4 by typeAliasing(Class4::class.name) { modifiers(PUBLIC) }
                val Class5 by typeAliasing(Class5::class.java) { modifiers(PUBLIC) }
                val Class6 by typeAliasing(Class6::class) { modifiers(PUBLIC) }
            },
        ).isEqualTo(
            FileSpec
                .builder("com.example", "test")
                .addTypeAlias(TypeAliasSpec.builder("Class1", Class1::class.name).build())
                .addTypeAlias(TypeAliasSpec.builder("Class2", Class2::class.name).build())
                .addTypeAlias(TypeAliasSpec.builder("Class3", Class3::class.name).build())
                .addTypeAlias(
                    TypeAliasSpec
                        .builder("Class4", Class4::class.name)
                        .addModifiers(PUBLIC)
                        .build(),
                ).addTypeAlias(
                    TypeAliasSpec
                        .builder("Class5", Class5::class.name)
                        .addModifiers(PUBLIC)
                        .build(),
                ).addTypeAlias(
                    TypeAliasSpec
                        .builder("Class6", Class6::class.name)
                        .addModifiers(PUBLIC)
                        .build(),
                ).build(),
        )
    }

    @Test
    fun invoke() {
        assertThat(
            buildFileSpec("com.example", "HelloWorld") {
                typeAliases {
                    "Class1"(Class1::class.name) { modifiers(PUBLIC) }
                    "Class2"(Class2::class.java) { modifiers(PUBLIC) }
                    "Class3"(Class3::class) { modifiers(PUBLIC) }
                }
            },
        ).isEqualTo(
            FileSpec
                .builder("com.example", "HelloWorld")
                .addTypeAlias(
                    TypeAliasSpec
                        .builder("Class1", Class1::class.name)
                        .addModifiers(PUBLIC)
                        .build(),
                ).addTypeAlias(
                    TypeAliasSpec
                        .builder("Class2", Class2::class.name)
                        .addModifiers(PUBLIC)
                        .build(),
                ).addTypeAlias(
                    TypeAliasSpec
                        .builder("Class3", Class3::class.name)
                        .addModifiers(PUBLIC)
                        .build(),
                ).build(),
        )
    }
}

class TypeAliasSpecBuilderTest {
    @Test
    fun modifiers() {
        assertThat(
            buildTypeAliasSpec("Annotation1", Annotation1::class.name) {
                modifiers(PUBLIC)
                assertFalse(modifiers.isEmpty())
            },
        ).isEqualTo(
            TypeAliasSpec
                .builder("Annotation1", Annotation1::class.name)
                .addModifiers(KModifier.PUBLIC)
                .build(),
        )
    }

    @Test
    fun typeVariables() {
        assertThat(
            buildTypeAliasSpec("Annotation1", Annotation1::class.name) {
                typeVariables(listOf("A".generics))
                assertFalse(typeVariables.isEmpty())
            },
        ).isEqualTo(
            TypeAliasSpec
                .builder("Annotation1", Annotation1::class.name)
                .addTypeVariables(listOf(TypeVariableName("A")))
                .build(),
        )
    }

    @Test
    fun typeVariable() {
        assertThat(
            buildTypeAliasSpec("Annotation1", Annotation1::class.name) {
                typeVariable("A".generics)
                assertFalse(typeVariables.isEmpty())
            },
        ).isEqualTo(
            TypeAliasSpec
                .builder("Annotation1", Annotation1::class.name)
                .addTypeVariable(TypeVariableName("A"))
                .build(),
        )
    }

    @Test
    fun annotation() {
        assertThat(
            buildTypeAliasSpec("Annotation1", Annotation1::class.name) {
                annotation(Annotation2::class)
                assertFalse(annotations.isEmpty())
            },
        ).isEqualTo(
            TypeAliasSpec
                .builder("Annotation1", Annotation1::class.name)
                .addAnnotation(Annotation2::class)
                .build(),
        )
    }

    @Test
    fun kdoc() {
        assertThat(
            buildTypeAliasSpec("Annotation1", Annotation1::class.name) {
                kdoc("text1")
                kdoc(codeBlockOf("text2"))
                assertFalse(kdoc.isEmpty())
            },
        ).isEqualTo(
            TypeAliasSpec
                .builder("Annotation1", Annotation1::class.name)
                .addKdoc("text1")
                .addKdoc(CodeBlock.of("text2"))
                .build(),
        )
    }

    @Test
    fun `Rest of properties`() {
        buildTypeAliasSpec("class1", Class1::class.name) {
            assertTrue(tags.isEmpty())
        }
    }
}
