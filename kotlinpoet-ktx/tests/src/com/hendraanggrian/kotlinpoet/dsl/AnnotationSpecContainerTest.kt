package com.hendraanggrian.kotlinpoet.dsl

import com.google.common.truth.Truth.assertThat
import com.hendraanggrian.kotlinpoet.annotationSpecOf
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import kotlin.test.Test

class AnnotationSpecContainerTest {
    private val specs = mutableListOf<AnnotationSpec>()
    private val container = object : AnnotationSpecContainer() {
        override fun add(spec: AnnotationSpec) {
            specs += spec
        }
    }

    private inline fun container(configuration: AnnotationSpecContainerScope.() -> Unit) =
        AnnotationSpecContainerScope(container).configuration()

    @Test fun nativeSpec() {
        container.add(annotationSpecOf<Annotation1>())
        container += annotationSpecOf<Annotation2>()
        assertThat(specs).containsExactly(
            annotationSpecOf<Annotation1>(),
            annotationSpecOf<Annotation2>()
        )
    }

    @Test fun className() {
        val packageName = "com.hendraanggrian.kotlinpoet.dsl.AnnotationSpecContainerTest"
        container.add(ClassName(packageName, "Annotation1"))
        container += ClassName(packageName, "Annotation2")
        container { (ClassName(packageName, "Annotation3")) { } }
        assertThat(specs).containsExactly(
            annotationSpecOf<Annotation1>(),
            annotationSpecOf<Annotation2>(),
            annotationSpecOf<Annotation3>()
        )
    }

    @Test fun javaClass() {
        container.add(Annotation1::class.java)
        container += Annotation2::class.java
        container { (Annotation3::class.java) { } }
        assertThat(specs).containsExactly(
            annotationSpecOf<Annotation1>(),
            annotationSpecOf<Annotation2>(),
            annotationSpecOf<Annotation3>()
        )
    }

    @Test fun kotlinClass() {
        container.add(Annotation1::class)
        container += Annotation2::class
        container { Annotation3::class { } }
        assertThat(specs).containsExactly(
            annotationSpecOf<Annotation1>(),
            annotationSpecOf<Annotation2>(),
            annotationSpecOf<Annotation3>()
        )
    }

    @Test fun reifiedType() {
        container.add<Annotation1>()
        assertThat(specs).containsExactly(annotationSpecOf<Annotation1>())
    }

    annotation class Annotation1
    annotation class Annotation2
    annotation class Annotation3
}