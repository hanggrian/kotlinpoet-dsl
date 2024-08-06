package com.hanggrian.kotlinpoet

import com.example.Annotation1
import com.example.Class1
import com.example.Class2
import com.example.Class3
import com.example.Class4
import com.example.Property1
import com.google.common.truth.Truth.assertThat
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeAliasSpec
import org.junit.Assert.assertFalse
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class FileSpecTest {
    @Test
    fun annotation() {
        assertThat(
            buildFileSpec("com.example", "MyClass") {
                annotation(annotationSpecOf(Annotation1::class.name))
                assertFalse(annotations.isEmpty())
            }.annotations.map { "$it" },
        ).containsExactly(
            "@file:${
                AnnotationSpec
                    .builder(Annotation1::class)
                    .build()
                    .toString()
                    .drop(1)
            }",
        )
    }

    @Test
    fun function() {
        assertThat(
            buildFileSpec("com.example", "MyClass") {
                function(funSpecOf("fun1"))
            }.funSpecs,
        ).containsExactly(
            FunSpec
                .builder("fun1")
                .build(),
        )
    }

    @Test
    fun property() {
        assertThat(
            buildFileSpec("com.example", "MyClass") {
                property(propertySpecOf("property1", Property1::class.name))
            }.propertySpecs,
        ).containsExactly(
            PropertySpec
                .builder("property1", Property1::class)
                .build(),
        )
    }

    @Test
    fun typeAlias() {
        assertThat(
            buildFileSpec("com.example", "MyClass") {
                typeAlias(typeAliasSpecOf("Class1", Class1::class.name))
            },
        ).isEqualTo(
            FileSpec
                .builder("com.example", "MyClass")
                .addTypeAlias(TypeAliasSpec.builder("Class1", Class1::class).build())
                .build(),
        )
    }

    @Test
    fun comment() {
        assertThat(
            buildFileSpec("com.example", "MyClass") {
                comment("A ")
                comment("very ")
                comment("long ")
                comment("comment")
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
    fun import() {
        assertThat(
            buildFileSpec("com.example", "MyClass") {
                import(Class1::class.name, "class1")
                import(Class2::class.java, "class2")
                import(Class3::class, "class3")
                import<Class4>("class4")
                import("%S", "kotlin.String")
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
            import(Class1::class.name, "class1")
            clearImports()
            assertTrue(imports.isEmpty())
        }
    }

    @Test
    fun aliasedImport() {
        assertThat(
            buildFileSpec("com.example", "MyClass") {
                aliasedImport(Class1::class.name, "class1")
                aliasedImport(Class2::class.java, "class2")
                aliasedImport(Class3::class, "class3")
                aliasedImport<Class4>("class4")
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
    fun kotlinDefaultImports() {
        assertThat(buildFileSpec("com.example", "MyClass") { kotlinDefaultImports() })
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
