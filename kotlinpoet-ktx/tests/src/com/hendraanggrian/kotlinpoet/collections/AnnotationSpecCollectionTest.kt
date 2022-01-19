package com.hendraanggrian.kotlinpoet.collections

import com.google.common.truth.Truth.assertThat
import com.hendraanggrian.kotlinpoet.internal.Annotation1
import com.hendraanggrian.kotlinpoet.internal.Annotation2
import com.hendraanggrian.kotlinpoet.internal.Annotation3
import com.hendraanggrian.kotlinpoet.internal.Annotation4
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.asClassName
import com.squareup.kotlinpoet.asTypeName
import kotlin.test.Test

class AnnotationSpecCollectionTest {

    private val list = AnnotationSpecCollection(mutableListOf())
    private fun list(configuration: AnnotationSpecCollectionScope.() -> Unit) =
        AnnotationSpecCollectionScope(list).configuration()

    @Test
    fun add() {
        list.add(Annotation1::class.asTypeName())
        list.add(Annotation2::class.java)
        list.add(Annotation3::class)
        list.add<Annotation4>()
        assertThat(list).containsExactly(
            AnnotationSpec.builder(Annotation1::class).build(),
            AnnotationSpec.builder(Annotation2::class).build(),
            AnnotationSpec.builder(Annotation3::class).build(),
            AnnotationSpec.builder(Annotation4::class).build()
        )
    }

    @Test
    fun plusAssign() {
        list += Annotation1::class.asTypeName()
        list += Annotation2::class.java
        list += Annotation3::class
        assertThat(list).containsExactly(
            AnnotationSpec.builder(Annotation1::class).build(),
            AnnotationSpec.builder(Annotation2::class).build(),
            AnnotationSpec.builder(Annotation3::class).build()
        )
    }

    @Test
    fun invoke() {
        list {
            (Annotation1::class.asTypeName()) { }
            Annotation2::class.java { }
            Annotation3::class { }
        }
        assertThat(list).containsExactly(
            AnnotationSpec.builder(Annotation1::class).build(),
            AnnotationSpec.builder(Annotation2::class).build(),
            AnnotationSpec.builder(Annotation3::class).build()
        )
    }
}