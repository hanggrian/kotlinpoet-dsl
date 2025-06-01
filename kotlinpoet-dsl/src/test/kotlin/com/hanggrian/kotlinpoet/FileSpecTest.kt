package com.hanggrian.kotlinpoet

import com.example.Annotation1
import com.example.Annotation2
import com.example.Class1
import com.example.Class2
import com.example.Class3
import com.example.Class4
import com.google.common.truth.Truth.assertThat
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.CHAR
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.INT
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asClassName
import org.junit.Assert.assertFalse
import kotlin.test.Test

class FileSpecTest {
    @Test
    fun annotations() =
        assertThat(
            buildFileSpec("com.example", "MyClass") {
                annotations.add(Annotation1::class) {
                    useSiteTarget = AnnotationSpec.UseSiteTarget.FILE
                }
                annotations {
                    add(Annotation2::class) {
                        useSiteTarget = AnnotationSpec.UseSiteTarget.FILE
                    }
                }
            },
        ).isEqualTo(
            FileSpec
                .builder("com.example", "MyClass")
                .addAnnotation(Annotation1::class)
                .addAnnotation(Annotation2::class)
                .build(),
        )

    @Test
    fun properties() =
        assertThat(
            buildFileSpec("com.example", "MyClass") {
                properties.add("field1", INT, PUBLIC)
                properties {
                    add("field2", CHAR, PRIVATE)
                }
            },
        ).isEqualTo(
            FileSpec
                .builder("com.example", "MyClass")
                .addProperty("field1", INT, KModifier.PUBLIC)
                .addProperty("field2", CHAR, KModifier.PRIVATE)
                .build(),
        )

    @Test
    fun functions() =
        assertThat(
            buildFileSpec("com.example", "MyClass") {
                functions.add("function1")
                functions {
                    add("function2")
                }
            },
        ).isEqualTo(
            FileSpec
                .builder("com.example", "MyClass")
                .addFunction(FunSpec.builder("function1").build())
                .addFunction(FunSpec.builder("function2").build())
                .build(),
        )

    @Test
    fun types() =
        assertThat(
            buildFileSpec("com.example", "MyClass") {
                types.addClass(Class1::class.name)
                types {
                    addClass(Class2::class.name)
                }
            },
        ).isEqualTo(
            FileSpec
                .builder("com.example", "MyClass")
                .addType(TypeSpec.classBuilder(Class1::class.asClassName()).build())
                .addType(TypeSpec.classBuilder(Class2::class.asClassName()).build())
                .build(),
        )

    @Test
    fun addComment() =
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

    @Test
    fun addImport() =
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

    @Test
    fun clearImports() {
        buildFileSpec("com.example", "MyClass") {
            addImport(Class1::class.name, "class1")
            clearImports()
            assertThat(imports.isEmpty()).isTrue()
        }
    }

    @Test
    fun addAliasedImport() =
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

    @Test
    fun addKotlinDefaultImports() =
        assertThat(buildFileSpec("com.example", "MyClass") { addKotlinDefaultImports() })
            .isEqualTo(
                FileSpec
                    .builder("com.example", "MyClass")
                    .addKotlinDefaultImports()
                    .build(),
            )

    @Test
    fun append() =
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

    @Test
    fun appendLine() =
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

    @Test
    fun appendNamed() =
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

    @Test
    fun controlFlow() =
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

    @Test
    fun indent() =
        assertThat(
            buildFileSpec("com.example", "MyClass") {
                types.addClass("MyClass") {
                    functions.addConstructor()
                }
                indent = ">"
            }.toString(),
        ).isEqualTo(
            """
            package com.example

            public class MyClass {
            >public constructor()
            }

            """.trimIndent(),
        )

    @Test
    fun indentSize() =
        assertThat(
            buildFileSpec("com.example", "MyClass") {
                types.addClass("MyClass") {
                    functions.addConstructor()
                }
                indentSize = 4
            }.toString(),
        ).isEqualTo(
            """
            package com.example

            public class MyClass {
                public constructor()
            }

            """.trimIndent(),
        )

    @Test
    fun `Rest of properties`() {
        buildFileSpec("com.example", "MyClass") {
            assertThat(packageName).isEqualTo("com.example")
            assertThat(name).isEqualTo("MyClass")
            assertThat(isScript).isFalse()
            assertThat(tags.isEmpty()).isTrue()
            assertThat(defaultImports.isEmpty()).isTrue()
            assertThat(members.isEmpty()).isTrue()
        }
    }
}
