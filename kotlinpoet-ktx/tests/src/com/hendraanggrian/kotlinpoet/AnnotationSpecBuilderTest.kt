package com.hendraanggrian.kotlinpoet

import com.example.Annotation1
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.CodeBlock
import kotlin.test.Test
import kotlin.test.assertEquals

class AnnotationSpecBuilderTest {

    @Test
    fun addMember() {
        assertEquals(
            buildAnnotationSpec<Annotation1> {
                addMember("member1", "value1")
                addMember("member2", codeBlockOf("value2"))
            },
            AnnotationSpec.builder(Annotation1::class)
                .addMember("member1", "value1")
                .addMember("member2", CodeBlock.of("value2"))
                .build()
        )
    }

    @Test
    fun useSiteTarget() {
        assertEquals(
            buildAnnotationSpec<Annotation1> { useSiteTarget = ANNOTATION_FILE },
            AnnotationSpec.builder(Annotation1::class).useSiteTarget(AnnotationSpec.UseSiteTarget.FILE).build()
        )
    }
}