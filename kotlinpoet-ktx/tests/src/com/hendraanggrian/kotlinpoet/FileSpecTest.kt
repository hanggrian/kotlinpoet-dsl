package com.hendraanggrian.kotlinpoet

import com.squareup.kotlinpoet.FileSpec
import kotlin.test.Test
import kotlin.test.assertEquals

class FileSpecTest {

    @Test
    fun fileComments() {
        assertEquals(
            FileSpec.builder("com.example", "MyClass")
                .addFileComment("A ")
                .addFileComment("very ")
                .addFileComment("long ")
                .addFileComment("comment")
                .build(),
            buildFileSpec("com.example", "MyClass") {
                addFileComment("A ")
                addFileComment("very ")
                addFileComment("long ")
                addFileComment("comment")
            }
        )
    }
}