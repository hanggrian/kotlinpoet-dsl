package com.hanggrian.kotlinpoet

import com.example.Annotation1
import com.example.Annotation2
import com.google.common.truth.Truth.assertThat
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeAliasSpec
import com.squareup.kotlinpoet.TypeVariableName
import kotlin.test.Test
import kotlin.test.assertFalse

@Suppress("ktlint:standard:property-naming")
class TypeAliasSpecTest {
    @Test
    fun typeAlias() {
        assertThat(
            buildFileSpec("com.example", "test") {
                typeAlias("Annotation1", Annotation1::class.name)
                typeAlias("Annotation2", Annotation2::class.java) { modifiers(PUBLIC) }
            },
        ).isEqualTo(
            FileSpec
                .builder("com.example", "test")
                .addTypeAlias(TypeAliasSpec.builder("Annotation1", Annotation1::class.name).build())
                .addTypeAlias(
                    TypeAliasSpec
                        .builder("Annotation2", Annotation2::class.name)
                        .addModifiers(PUBLIC)
                        .build(),
                ).build(),
        )
    }

    @Test
    fun typeAliasing() {
        assertThat(
            buildFileSpec("com.example", "test") {
                val Annotation1 by typeAliasing(Annotation1::class.name)
                val Annotation2 by typeAliasing(Annotation2::class.name) {
                    modifiers(PUBLIC)
                }
            },
        ).isEqualTo(
            FileSpec
                .builder("com.example", "test")
                .addTypeAlias(TypeAliasSpec.builder("Annotation1", Annotation1::class.name).build())
                .addTypeAlias(
                    TypeAliasSpec
                        .builder("Annotation2", Annotation2::class.name)
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
                    "Annotation1"(Annotation1::class) {
                        modifiers(PUBLIC)
                    }
                }
            },
        ).isEqualTo(
            FileSpec
                .builder("com.example", "HelloWorld")
                .addTypeAlias(
                    TypeAliasSpec
                        .builder("Annotation1", Annotation1::class.name)
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
                assertFalse(kdoc.isEmpty())
            },
        ).isEqualTo(
            TypeAliasSpec
                .builder("Annotation1", Annotation1::class.name)
                .addKdoc("text1")
                .build(),
        )
    }
}
