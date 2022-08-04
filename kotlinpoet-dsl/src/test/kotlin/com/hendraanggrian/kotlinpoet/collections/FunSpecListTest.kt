package com.hendraanggrian.kotlinpoet.collections

import com.google.common.truth.Truth.assertThat
import com.squareup.kotlinpoet.FunSpec
import kotlin.test.Test

class FunSpecListTest {
    private val list = FunSpecList(mutableListOf())
    private fun list(configuration: FunSpecListScope.() -> Unit) =
        FunSpecListScope(list).configuration()

    @Test
    fun add() {
        list.add("fun1")
        list.add("fun2") { kdoc.append("text2") }
        list.addConstructor()
        list.addConstructor { kdoc.append("text4") }
        list.addGetter()
        list.addGetter { kdoc.append("text5") }
        list.addSetter()
        list.addSetter { kdoc.append("text6") }
        assertThat(list).containsExactly(
            FunSpec.builder("fun1").build(),
            FunSpec.builder("fun2").addKdoc("text2").build(),
            FunSpec.constructorBuilder().build(),
            FunSpec.constructorBuilder().addKdoc("text4").build(),
            FunSpec.getterBuilder().build(),
            FunSpec.getterBuilder().addKdoc("text5").build(),
            FunSpec.setterBuilder().build(),
            FunSpec.setterBuilder().addKdoc("text6").build()
        )
    }

    @Test
    fun plusAssign() {
        list += "fun1"
        assertThat(list).containsExactly(
            FunSpec.builder("fun1").build()
        )
    }

    @Test
    @Suppress("UNUSED_VARIABLE")
    fun adding() {
        val method1 by list.adding
        val method2 by list.adding { kdoc.append("text2") }
        assertThat(list).containsExactly(
            FunSpec.builder("method1").build(),
            FunSpec.builder("method2").addKdoc("text2").build()
        )
    }

    @Test
    fun invoke() {
        list {
            "fun1" { kdoc.append("text1") }
        }
        assertThat(list).containsExactly(
            FunSpec.builder("fun1").addKdoc("text1").build()
        )
    }
}
