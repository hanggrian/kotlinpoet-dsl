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
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Spy
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import kotlin.test.Test

class AnnotationSpecCreatorTest {
    @Test
    fun of() {
        assertThat(annotationSpecOf(classNamed("com.example", "MyAnnotation")))
            .isEqualTo(AnnotationSpec.builder(ClassName("com.example", "MyAnnotation")).build())
        assertThat(annotationSpecOf(List::class.name.parameterizedBy<String>()))
            .isEqualTo(AnnotationSpec.builder(List::class.parameterizedBy(String::class)).build())
    }

    @Test
    fun build() {
        assertThat(
            buildAnnotationSpec(classNamed("com.example", "MyAnnotation")) {
                addMember("name1", "value1")
            },
        ).isEqualTo(
            AnnotationSpec
                .builder(ClassName("com.example", "MyAnnotation"))
                .addMember("name1", "value1")
                .build(),
        )
        assertThat(
            buildAnnotationSpec(List::class.name.parameterizedBy<String>()) {
                addMember("name1", "value1")
            },
        ).isEqualTo(
            AnnotationSpec
                .builder(List::class.parameterizedBy(String::class))
                .addMember("name1", "value1")
                .build(),
        )
    }
}

@ExtendWith(MockitoExtension::class)
class AnnotationSpecHandlerTest {
    private val annotationSpecs = mutableListOf<AnnotationSpec>()

    @Spy private val annotations: AnnotationSpecHandler =
        object : AnnotationSpecHandler {
            override fun add(annotation: AnnotationSpec) {
                annotationSpecs += annotation
            }
        }

    private fun annotations(configuration: AnnotationSpecHandlerScope.() -> Unit) =
        AnnotationSpecHandlerScope
            .of(annotations)
            .configuration()

    @Test
    fun add() {
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
        assertThat(annotationSpecs).containsExactly(
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
        verify(annotations, times(9)).add(any<AnnotationSpec>())
    }

    @Test
    fun invoke() {
        annotations {
            Annotation1::class.name { addMember("name1", "value1") }
            (Annotation2::class.name.parameterizedBy(Parameter2::class.name)) {
                addMember("name2", "value2")
            }
            Annotation3::class.java { addMember("name3", "value3") }
            Annotation4::class { addMember("name4", "value4") }
        }
        assertThat(annotationSpecs).containsExactly(
            AnnotationSpec.builder(Annotation1::class).addMember("name1", "value1").build(),
            AnnotationSpec
                .builder(Annotation2::class.name.parameterizedBy(Parameter2::class.name))
                .addMember("name2", "value2")
                .build(),
            AnnotationSpec.builder(Annotation3::class).addMember("name3", "value3").build(),
            AnnotationSpec.builder(Annotation4::class).addMember("name4", "value4").build(),
        )
        verify(annotations, times(4)).add(any<AnnotationSpec>())
    }
}

class AnnotationSpecBuilderTest {
    @Test
    fun addMember() =
        assertThat(
            buildAnnotationSpec(Annotation1::class.name) {
                addMember("member1", "value1")
                assertThat(members.isEmpty()).isFalse()
            },
        ).isEqualTo(
            AnnotationSpec
                .builder(Annotation1::class)
                .addMember("member1", "value1")
                .build(),
        )

    @Test
    fun useSiteTarget() =
        assertThat(
            buildAnnotationSpec(Annotation1::class.name) { useSiteTarget = ANNOTATION_FILE },
        ).isEqualTo(
            AnnotationSpec
                .builder(Annotation1::class)
                .useSiteTarget(AnnotationSpec.UseSiteTarget.FILE)
                .build(),
        )

    @Test
    fun `Rest of properties`() {
        buildAnnotationSpec(Annotation1::class.name) {
            assertThat(tags.isEmpty()).isTrue()
        }
    }
}
