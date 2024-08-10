package com.hanggrian.kotlinpoet

import com.example.Class1
import com.example.Class2
import com.example.Class3
import com.example.Class4
import com.google.common.truth.Truth.assertThat
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import org.junit.Assert.assertFalse
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class FileSpecTest {
    @Test
    fun addComment() {
        assertThat(
            buildFileSpec("com.example", "MyClass") {
                addComment("A ")
                addComment("very ")
                addComment("long ")
                addComment("comment")
            },
        ).isEqualTo(
            FileSpec
                .builder("com.example", "MyClass")
                .addFileComment("A ")
                .addFileComment("very ")
                .addFileComment("long ")
                .addFileComment("comment")
                .build(),
        )
    }

    @Test
    fun addImport() {
        assertThat(
            buildFileSpec("com.example", "MyClass") {
                addImport(Class1::class.name, "class1")
                addImport(Class2::class.java, "class2")
                addImport(Class3::class, "class3")
                addImport<Class4>("class4")
                addImport("%S", "kotlin.String")
                assertFalse(imports.isEmpty())
            },
        ).isEqualTo(
            FileSpec
                .builder("com.example", "MyClass")
                .addImport(Class1::class, "class1")
                .addImport(Class2::class, "class2")
                .addImport(Class3::class, "class3")
                .addImport(Class4::class, "class4")
                .addImport("%S", "kotlin.String")
                .build(),
        )
    }

    @Test
    fun clearImports() {
        buildFileSpec("com.example", "MyClass") {
            addImport(Class1::class.name, "class1")
            clearImports()
            assertTrue(imports.isEmpty())
        }
    }

    @Test
    fun addAliasedImport() {
        assertThat(
            buildFileSpec("com.example", "MyClass") {
                addAliasedImport(Class1::class.name, "class1")
                addAliasedImport(Class2::class.java, "class2")
                addAliasedImport(Class3::class, "class3")
                addAliasedImport<Class4>("class4")
            },
        ).isEqualTo(
            FileSpec
                .builder("com.example", "MyClass")
                .addAliasedImport(Class1::class, "class1")
                .addAliasedImport(Class2::class, "class2")
                .addAliasedImport(Class3::class, "class3")
                .addAliasedImport(Class4::class, "class4")
                .build(),
        )
    }

    @Test
    fun addKotlinDefaultImports() {
        assertThat(buildFileSpec("com.example", "MyClass") { addKotlinDefaultImports() })
            .isEqualTo(
                FileSpec
                    .builder("com.example", "MyClass")
                    .addKotlinDefaultImports()
                    .build(),
            )
    }

    @Test
    fun append() {
        assertThat(
            buildScriptFileSpec("com.example", "MyClass") {
                append("text")
                append(codeBlockOf("some code"))
            },
        ).isEqualTo(
            FileSpec
                .scriptBuilder("com.example", "MyClass")
                .addCode("text")
                .addCode(CodeBlock.of("some code"))
                .build(),
        )
    }

    @Test
    fun appendLine() {
        assertThat(
            buildScriptFileSpec("com.example", "MyClass") {
                appendLine()
                appendLine("text")
            },
        ).isEqualTo(
            FileSpec
                .scriptBuilder("com.example", "MyClass")
                .addStatement("")
                .addStatement("text")
                .build(),
        )
    }

    @Test
    fun appendNamed() {
        assertThat(
            buildScriptFileSpec("com.example", "MyClass") {
                appendNamed("format", mapOf("key1" to "value1", "key2" to "value2"))
            },
        ).isEqualTo(
            FileSpec
                .scriptBuilder("com.example", "MyClass")
                .addNamedCode("format", mapOf("key1" to "value1", "key2" to "value2"))
                .build(),
        )
    }

    @Test
    fun controlFlow() {
        assertThat(
            buildScriptFileSpec("com.example", "MyClass") {
                beginControlFlow("format", "arg")
                nextControlFlow("format", "arg")
                endControlFlow()
            },
        ).isEqualTo(
            FileSpec
                .scriptBuilder("com.example", "MyClass")
                .beginControlFlow("format", "arg")
                .nextControlFlow("format", "arg")
                .endControlFlow()
                .build(),
        )
    }

    @Test
    fun `Rest of properties`() {
        buildFileSpec("com.example", "MyClass") {
            assertEquals("com.example", packageName)
            assertEquals("MyClass", name)
            assertFalse(isScript)
            assertTrue(tags.isEmpty())
            assertTrue(defaultImports.isEmpty())
            assertTrue(members.isEmpty())
        }
    }
}
