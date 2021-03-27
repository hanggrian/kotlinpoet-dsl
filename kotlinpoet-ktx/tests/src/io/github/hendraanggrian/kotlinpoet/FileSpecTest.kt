package io.github.hendraanggrian.kotlinpoet

import com.squareup.kotlinpoet.FileSpec
import kotlin.test.Test
import kotlin.test.assertEquals

class FileSpecTest {

    @Test
    fun comments() {
        assertEquals(
            FileSpec.builder("com.example", "MyClass")
                .addComment("A ")
                .addComment("very ")
                .addComment("long ")
                .addComment("comment")
                .build(),
            buildFileSpec("com.example", "MyClass") {
                addComment("A ")
                addComment("very ")
                addComment("long ")
                addComment("comment")
            }
        )
    }
}