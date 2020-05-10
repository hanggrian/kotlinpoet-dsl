package com.hendraanggrian.kotlinpoet.collections

import com.google.common.truth.Truth.assertThat
import com.hendraanggrian.kotlinpoet.annotationSpecOf
import com.squareup.kotlinpoet.ClassName
import kotlin.test.Test

class AnnotationSpecListTest {
    private val container = AnnotationSpecList(mutableListOf())

    private inline fun container(configuration: AnnotationSpecListScope.() -> Unit) =
        AnnotationSpecListScope(container).configuration()

    @Test fun nativeSpec() {
        container += annotationSpecOf<Annotation1>()
        container += listOf(annotationSpecOf<Annotation2>())
        assertThat(container).containsExactly(
            annotationSpecOf<Annotation1>(),
            annotationSpecOf<Annotation2>()
        )
    }

    @Test fun className() {
        val packageName = "com.hendraanggrian.kotlinpoet.collections.AnnotationSpecListTest"
        container.add(ClassName(packageName, "Annotation1"))
        container += ClassName(packageName, "Annotation2")
        container { (ClassName(packageName, "Annotation3")) { } }
        assertThat(container).containsExactly(
            annotationSpecOf<Annotation1>(),
            annotationSpecOf<Annotation2>(),
            annotationSpecOf<Annotation3>()
        )
    }

    @Test fun javaClass() {
        container.add(Annotation1::class.java)
        container += Annotation2::class.java
        container { (Annotation3::class.java) { } }
        assertThat(container).containsExactly(
            annotationSpecOf<Annotation1>(),
            annotationSpecOf<Annotation2>(),
            annotationSpecOf<Annotation3>()
        )
    }

    @Test fun kotlinClass() {
        container.add(Annotation1::class)
        container += Annotation2::class
        container { Annotation3::class { } }
        assertThat(container).containsExactly(
            annotationSpecOf<Annotation1>(),
            annotationSpecOf<Annotation2>(),
            annotationSpecOf<Annotation3>()
        )
    }

    @Test fun reifiedType() {
        container.add<Annotation1>()
        assertThat(container).containsExactly(annotationSpecOf<Annotation1>())
    }

    annotation class Annotation1
    annotation class Annotation2
    annotation class Annotation3
}