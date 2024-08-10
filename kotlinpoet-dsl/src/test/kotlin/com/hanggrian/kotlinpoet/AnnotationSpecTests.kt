package com.hanggrian.kotlinpoet

import com.example.Annotation1
import com.example.Annotation2
import com.example.Annotation3
import com.example.Annotation4
import com.example.Annotation5
import com.example.Annotation6
import com.example.Annotation7
import com.example.Annotation8
import com.example.Annotation9
import com.example.Parameter2
import com.example.Parameter7
import com.google.common.truth.Truth.assertThat
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.STRING
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class AnnotationSpecHandlerTest {
    @Test
    fun add() {
        assertThat(
            buildPropertySpec("test", STRING) {
                annotations.add(Annotation1::class.name)
                annotations.add(Annotation2::class.name.parameterizedBy(Parameter2::class.name))
                annotations.add(Annotation3::class.java)
                annotations.add(Annotation4::class)
                annotations.add<Annotation5>()
                annotations {
                    add(Annotation6::class.name) { addMember("name6", "value6") }
                    add(Annotation7::class.name.parameterizedBy(Parameter7::class.name)) {
                        addMember("name7", "value7")
                    }
                    add(Annotation8::class.java) { addMember("name8", "value8") }
                    add(Annotation9::class) { addMember("name9", "value9") }
                }
            }.annotations,
        ).containsExactly(
            AnnotationSpec.builder(Annotation1::class).build(),
            AnnotationSpec
                .builder(Annotation2::class.name.parameterizedBy(Parameter2::class.name))
                .build(),
            AnnotationSpec.builder(Annotation3::class).build(),
            AnnotationSpec.builder(Annotation4::class).build(),
            AnnotationSpec.builder(Annotation5::class).build(),
            AnnotationSpec.builder(Annotation6::class).addMember("name6", "value6").build(),
            AnnotationSpec
                .builder(Annotation7::class.name.parameterizedBy(Parameter7::class.name))
                .addMember("name7", "value7")
                .build(),
            AnnotationSpec.builder(Annotation8::class).addMember("name8", "value8").build(),
            AnnotationSpec.builder(Annotation9::class).addMember("name9", "value9").build(),
        )
    }

    @Test
    fun invoke() {
        assertThat(
            buildParameterSpec("test", STRING) {
                annotations {
                    Annotation1::class.name { addMember("name1", "value1") }
                    (Annotation2::class.name.parameterizedBy(Parameter2::class.name)) {
                        addMember("name2", "value2")
                    }
                    Annotation3::class.java { addMember("name3", "value3") }
                    Annotation4::class { addMember("name4", "value4") }
                }
            }.annotations,
        ).containsExactly(
            AnnotationSpec.builder(Annotation1::class).addMember("name1", "value1").build(),
            AnnotationSpec
                .builder(Annotation2::class.name.parameterizedBy(Parameter2::class.name))
                .addMember("name2", "value2")
                .build(),
            AnnotationSpec.builder(Annotation3::class).addMember("name3", "value3").build(),
            AnnotationSpec.builder(Annotation4::class).addMember("name4", "value4").build(),
        )
    }
}

class AnnotationSpecBuilderTest {
    @Test
    fun addMember() {
        assertThat(
            buildAnnotationSpec(Annotation1::class.name) {
                addMember("member1", "value1")
                assertFalse(members.isEmpty())
            },
        ).isEqualTo(
            AnnotationSpec
                .builder(Annotation1::class)
                .addMember("member1", "value1")
                .build(),
        )
    }

    @Test
    fun useSiteTarget() {
        assertThat(
            buildAnnotationSpec(Annotation1::class.name) { useSiteTarget = ANNOTATION_FILE },
        ).isEqualTo(
            AnnotationSpec
                .builder(Annotation1::class)
                .useSiteTarget(AnnotationSpec.UseSiteTarget.FILE)
                .build(),
        )
    }

    @Test
    fun `Rest of properties`() {
        buildAnnotationSpec(Annotation1::class.name) {
            assertTrue(tags.isEmpty())
        }
    }
}
