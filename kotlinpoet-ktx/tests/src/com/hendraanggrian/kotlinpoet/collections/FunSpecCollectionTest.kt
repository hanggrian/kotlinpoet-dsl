package com.hendraanggrian.kotlinpoet.collections

import com.google.common.truth.Truth.assertThat
import com.squareup.kotlinpoet.FunSpec
import kotlin.test.Test

class FunSpecCollectionTest {

    private val list = FunSpecCollection(mutableListOf())
    private fun list(configuration: FunSpecCollectionScope.() -> Unit) =
        FunSpecCollectionScope(list).configuration()

    @Test
    fun add() {
        list.add("method")
        list.addConstructor()
        list.addGetter()
        list.addSetter()
        assertThat(list).containsExactly(
            FunSpec.builder("method").build(),
            FunSpec.constructorBuilder().build(),
            FunSpec.getterBuilder().build(),
            FunSpec.setterBuilder().build()
        )
    }

    @Test
    fun plusAssign() {
        list += "method"
        assertThat(list).containsExactly(
            FunSpec.builder("method").build()
        )
    }

    @Test
    fun invoke() {
        list {
            "method" { }
        }
        assertThat(list).containsExactly(
            FunSpec.builder("method").build()
        )
    }
}