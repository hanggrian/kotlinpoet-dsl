package com.hendraanggrian.kotlinpoet.dsl

import com.google.common.truth.Truth.assertThat
import com.hendraanggrian.kotlinpoet.emptyConstructorFunSpec
import com.hendraanggrian.kotlinpoet.emptyGetterFunSpec
import com.hendraanggrian.kotlinpoet.emptySetterFunSpec
import com.hendraanggrian.kotlinpoet.funSpecOf
import kotlin.test.Test

class FunSpecHandlerTest {
    private val list = FunSpecHandler(mutableListOf())

    private inline fun container(configuration: FunSpecHandlerScope.() -> Unit) =
        FunSpecHandlerScope(list).configuration()

    @Test
    fun nativeSpec() {
        list += funSpecOf("func")
        list += listOf(emptyConstructorFunSpec())
        assertThat(list).containsExactly(
            funSpecOf("func"),
            emptyConstructorFunSpec()
        )
    }

    @Test
    fun string() {
        list.add("func1")
        container { "func2" { } }
        assertThat(list).containsExactly(
            funSpecOf("func1"),
            funSpecOf("func2")
        )
    }

    @Test
    fun others() {
        list.addConstructor()
        list.addGetter()
        list.addSetter()
        assertThat(list).containsExactly(
            emptyConstructorFunSpec(),
            emptyGetterFunSpec(),
            emptySetterFunSpec()
        )
    }
}