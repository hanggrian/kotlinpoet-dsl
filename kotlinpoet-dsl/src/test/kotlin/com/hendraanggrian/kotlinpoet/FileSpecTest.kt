package com.hendraanggrian.kotlinpoet

import com.example.Annotation1
import com.example.Annotation2
import com.example.Annotation3
import com.google.common.truth.Truth.assertThat
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.FileSpec
import org.junit.Assert.assertFalse
import kotlin.test.Test
import kotlin.test.assertEquals

class FileSpecTest {
    @Test
    fun properties() {
        buildFileSpec("com.example", "MyClass") {
            assertEquals("com.example", packageName)
            assertEquals("MyClass", name)
            assertFalse(isScript)
        }
    }

    @Test
    fun annotation() {
        assertThat(
            buildFileSpec("com.example", "MyClass") {
                annotation(Annotation1::class.name)
                annotation(Annotation2::class)
                annotation<Annotation3>()
                kotlin.test.assertFalse(annotations.isEmpty())
            }.annotations.map { "$it" },
        ).containsExactly(
            "@file:${AnnotationSpec.builder(Annotation1::class).build().toString().drop(1)}",
            "@file:${AnnotationSpec.builder(Annotation2::class).build().toString().drop(1)}",
            "@file:${AnnotationSpec.builder(Annotation3::class).build().toString().drop(1)}",
        )
    }

    @Test
    fun comment() {
        assertThat(
            buildFileSpec("com.example", "MyClass") {
                comment("A ")
                clearComment()
                comment("very ")
                comment("long ")
                comment("comment")
            },
        ).isEqualTo(
            FileSpec.builder("com.example", "MyClass")
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
                import("%S", "kotlin.String")
                assertFalse(imports.isEmpty())
            },
        ).isEqualTo(
            FileSpec.builder("com.example", "MyClass")
                .addImport("%S", "kotlin.String")
                .build(),
        )
    }
}
