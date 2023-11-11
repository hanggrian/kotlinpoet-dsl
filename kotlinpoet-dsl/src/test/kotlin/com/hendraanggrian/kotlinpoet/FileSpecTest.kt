package com.hendraanggrian.kotlinpoet

import com.google.common.truth.Truth.assertThat
import com.squareup.kotlinpoet.FileSpec
import kotlin.test.Test

class FileSpecTest {
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
            FileSpec.builder("com.example", "MyClass")
                .addFileComment("A ")
                .addFileComment("very ")
                .addFileComment("long ")
                .addFileComment("comment")
                .build(),
        )
    }
}
