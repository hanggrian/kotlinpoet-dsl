package io.github.hendraanggrian.kotlinpoet.dsl

import com.google.common.truth.Truth.assertThat
import com.squareup.kotlinpoet.ClassName
import io.github.hendraanggrian.kotlinpoet.annotationSpecOf
import kotlin.test.Test

class AnnotationSpecHandlerTest {
    private val list = AnnotationSpecHandler(mutableListOf())

    private inline fun container(configuration: AnnotationSpecHandlerScope.() -> Unit) =
        AnnotationSpecHandlerScope(list).configuration()

    @Test fun nativeSpec() {
        list += annotationSpecOf<Annotation1>()
        list += listOf(annotationSpecOf<Annotation2>())
        assertThat(list).containsExactly(
            annotationSpecOf<Annotation1>(),
            annotationSpecOf<Annotation2>()
        )
    }

    @Test fun className() {
        val packageName = "io.github.hendraanggrian.kotlinpoet.dsl.AnnotationSpecHandlerTest"
        list.add(ClassName(packageName, "Annotation1"))
        list += ClassName(packageName, "Annotation2")
        container { (ClassName(packageName, "Annotation3")) { } }
        assertThat(list).containsExactly(
            annotationSpecOf<Annotation1>(),
            annotationSpecOf<Annotation2>(),
            annotationSpecOf<Annotation3>()
        )
    }

    @Test fun javaClass() {
        list.add(Annotation1::class.java)
        list += Annotation2::class.java
        container { (Annotation3::class.java) { } }
        assertThat(list).containsExactly(
            annotationSpecOf<Annotation1>(),
            annotationSpecOf<Annotation2>(),
            annotationSpecOf<Annotation3>()
        )
    }

    @Test fun kotlinClass() {
        list.add(Annotation1::class)
        list += Annotation2::class
        container { Annotation3::class { } }
        assertThat(list).containsExactly(
            annotationSpecOf<Annotation1>(),
            annotationSpecOf<Annotation2>(),
            annotationSpecOf<Annotation3>()
        )
    }

    @Test fun reifiedType() {
        list.add<Annotation1>()
        assertThat(list).containsExactly(annotationSpecOf<Annotation1>())
    }

    annotation class Annotation1
    annotation class Annotation2
    annotation class Annotation3
}