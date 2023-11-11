package com.hendraanggrian.kotlinpoet

import com.example.Annotation1
import com.example.Annotation2
import com.example.Annotation3
import com.example.Annotation4
import com.example.Annotation5
import com.example.Annotation6
import com.example.Annotation7
import com.google.common.truth.Truth.assertThat
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.STRING
import kotlin.test.Test
import kotlin.test.assertFalse

class AnnotationSpecHandlerTest {
    @Test
    fun annotation() {
        assertThat(
            buildPropertySpec("test", STRING) {
                annotation(Annotation1::class.name)
                annotation(Annotation2::class.java)
                annotation(Annotation3::class)
                annotation<Annotation4>()
                annotation(Annotation5::class.name) { member("name5", "value5") }
                annotation(Annotation6::class.java) { member("name6", "value6") }
                annotation(Annotation7::class) { member("name7", "value7") }
            }.annotations,
        ).containsExactly(
            AnnotationSpec.builder(Annotation1::class).build(),
            AnnotationSpec.builder(Annotation2::class).build(),
            AnnotationSpec.builder(Annotation3::class).build(),
            AnnotationSpec.builder(Annotation4::class).build(),
            AnnotationSpec.builder(Annotation5::class).addMember("name5", "value5").build(),
            AnnotationSpec.builder(Annotation6::class).addMember("name6", "value6").build(),
            AnnotationSpec.builder(Annotation7::class).addMember("name7", "value7").build(),
        )
    }

    @Test
    fun invoke() {
        assertThat(
            buildParameterSpec("test", STRING) {
                annotations {
                    Annotation1::class.name { member("name1", "value1") }
                    Annotation2::class.java { member("name2", "value2") }
                    Annotation3::class { member("name3", "value3") }
                }
            }.annotations,
        ).containsExactly(
            AnnotationSpec.builder(Annotation1::class).addMember("name1", "value1").build(),
            AnnotationSpec.builder(Annotation2::class).addMember("name2", "value2").build(),
            AnnotationSpec.builder(Annotation3::class).addMember("name3", "value3").build(),
        )
    }
}

class AnnotationSpecBuilderTest {
    @Test
    fun member() {
        assertThat(
            buildAnnotationSpec(Annotation1::class.name) {
                member("member1", "value1")
                member("member2", codeBlockOf("value2"))
                assertFalse(members.isEmpty())
            },
        ).isEqualTo(
            AnnotationSpec.builder(Annotation1::class)
                .addMember("member1", "value1")
                .addMember("member2", CodeBlock.of("value2"))
                .build(),
        )
    }

    @Test
    fun useSiteTarget() {
        assertThat(
            buildAnnotationSpec(Annotation1::class.name) { useSiteTarget = ANNOTATION_FILE },
        ).isEqualTo(
            AnnotationSpec.builder(Annotation1::class)
                .useSiteTarget(AnnotationSpec.UseSiteTarget.FILE)
                .build(),
        )
    }
}
