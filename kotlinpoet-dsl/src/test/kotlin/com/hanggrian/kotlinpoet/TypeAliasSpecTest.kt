@file:Suppress("ktlint:standard:property-naming")

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
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeAliasSpec
import com.squareup.kotlinpoet.TypeVariableName
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Spy
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import kotlin.test.Test

class TypeAliasSpecCreatorTest {
    @Test
    fun of() =
        assertThat(typeAliasSpecOf("Alias", Class1::class.name))
            .isEqualTo(TypeAliasSpec.builder("Alias", Class1::class).build())

    @Test
    fun build() =
        assertThat(
            buildTypeAliasSpec("Alias", Class1::class.name) {
                addModifiers(PUBLIC)
            },
        ).isEqualTo(
            TypeAliasSpec
                .builder("Alias", Class1::class)
                .addModifiers(KModifier.PUBLIC)
                .build(),
        )
}

@ExtendWith(MockitoExtension::class)
class TypeAliasSpecTest {
    private val typeAliasSpecs = mutableListOf<TypeAliasSpec>()

    @Spy private val typeAliases: TypeAliasSpecHandler =
        object : TypeAliasSpecHandler {
            override fun add(typeAlias: TypeAliasSpec) {
                typeAliasSpecs += typeAlias
            }
        }

    private fun typeAliases(configuration: TypeAliasSpecHandlerScope.() -> Unit) =
        TypeAliasSpecHandlerScope
            .of(typeAliases)
            .configuration()

    @Test
    fun add() {
        typeAliases.add("Class1", Class1::class.name)
        typeAliases.add("Class2", Class2::class.java)
        typeAliases.add("Class3", Class3::class)
        typeAliases.add<Class4>("Class4")
        typeAliases {
            add("Class5", Class5::class.name) { addModifiers(PUBLIC) }
            add("Class6", Class6::class.java) { addModifiers(PUBLIC) }
            add("Class7", Class7::class) { addModifiers(PUBLIC) }
        }
        assertThat(typeAliasSpecs).containsExactly(
            TypeAliasSpec.builder("Class1", Class1::class.name).build(),
            TypeAliasSpec.builder("Class2", Class2::class.name).build(),
            TypeAliasSpec.builder("Class3", Class3::class.name).build(),
            TypeAliasSpec.builder("Class4", Class4::class.name).build(),
            TypeAliasSpec
                .builder("Class5", Class5::class.name)
                .addModifiers(PUBLIC)
                .build(),
            TypeAliasSpec
                .builder("Class6", Class6::class.name)
                .addModifiers(PUBLIC)
                .build(),
            TypeAliasSpec
                .builder("Class7", Class7::class.name)
                .addModifiers(PUBLIC)
                .build(),
        )
        verify(typeAliases, times(7)).add(any<TypeAliasSpec>())
    }

    @Test
    fun adding() {
        val Clazz1 by typeAliases.adding(Class1::class.name)
        val Clazz2 by typeAliases.adding(Class2::class.java)
        val Clazz3 by typeAliases.adding(Class3::class)
        val Clazz4 by typeAliases.adding(Class4::class.name) { addModifiers(PUBLIC) }
        val Clazz5 by typeAliases.adding(Class5::class.java) { addModifiers(PUBLIC) }
        val Clazz6 by typeAliases.adding(Class6::class) { addModifiers(PUBLIC) }
        assertThat(typeAliasSpecs).containsExactly(
            TypeAliasSpec.builder("Clazz1", Class1::class.name).build(),
            TypeAliasSpec.builder("Clazz2", Class2::class.name).build(),
            TypeAliasSpec.builder("Clazz3", Class3::class.name).build(),
            TypeAliasSpec
                .builder("Clazz4", Class4::class.name)
                .addModifiers(PUBLIC)
                .build(),
            TypeAliasSpec
                .builder("Clazz5", Class5::class.name)
                .addModifiers(PUBLIC)
                .build(),
            TypeAliasSpec
                .builder("Clazz6", Class6::class.name)
                .addModifiers(PUBLIC)
                .build(),
        )
        verify(typeAliases, times(6)).add(any<TypeAliasSpec>())
    }

    @Test
    fun invoke() {
        typeAliases {
            "Class1"(Class1::class.name) { addModifiers(PUBLIC) }
            "Class2"(Class2::class.java) { addModifiers(PUBLIC) }
            "Class3"(Class3::class) { addModifiers(PUBLIC) }
        }
        assertThat(typeAliasSpecs).containsExactly(
            TypeAliasSpec
                .builder("Class1", Class1::class.name)
                .addModifiers(PUBLIC)
                .build(),
            TypeAliasSpec
                .builder("Class2", Class2::class.name)
                .addModifiers(PUBLIC)
                .build(),
            TypeAliasSpec
                .builder("Class3", Class3::class.name)
                .addModifiers(PUBLIC)
                .build(),
        )
    }
}

class TypeAliasSpecBuilderTest {
    @Test
    fun annotations() =
        assertThat(
            buildTypeAliasSpec("Alias", Class1::class.name) {
                annotations.add(Annotation1::class)
                annotations {
                    add(Annotation2::class)
                }
            },
        ).isEqualTo(
            TypeAliasSpec
                .builder("Alias", Class1::class)
                .addAnnotation(Annotation1::class)
                .addAnnotation(Annotation2::class)
                .build(),
        )

    @Test
    fun addModifiers() =
        assertThat(
            buildTypeAliasSpec("Annotation1", Annotation1::class.name) {
                addModifiers(PUBLIC)
                assertThat(modifiers.isEmpty()).isFalse()
            },
        ).isEqualTo(
            TypeAliasSpec
                .builder("Annotation1", Annotation1::class.name)
                .addModifiers(KModifier.PUBLIC)
                .build(),
        )

    @Test
    fun addTypeVariables() =
        assertThat(
            buildTypeAliasSpec("Annotation1", Annotation1::class.name) {
                addTypeVariables("A".generics)
                assertThat(typeVariables.isEmpty()).isFalse()
            },
        ).isEqualTo(
            TypeAliasSpec
                .builder("Annotation1", Annotation1::class.name)
                .addTypeVariables(listOf(TypeVariableName("A")))
                .build(),
        )

    @Test
    fun addKdoc() =
        assertThat(
            buildTypeAliasSpec("Annotation1", Annotation1::class.name) {
                addKdoc("text1")
                addKdoc(codeBlockOf("text2"))
                assertThat(kdoc.isEmpty()).isFalse()
            },
        ).isEqualTo(
            TypeAliasSpec
                .builder("Annotation1", Annotation1::class.name)
                .addKdoc("text1")
                .addKdoc(CodeBlock.of("text2"))
                .build(),
        )

    @Test
    fun `Rest of properties`() {
        buildTypeAliasSpec("class1", Class1::class.name) {
            assertThat(tags.isEmpty()).isTrue()
        }
    }
}
